package com.oxygen.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oxygen.shortlink.project.common.constants.RedisKeyConstant;
import com.oxygen.shortlink.project.common.constants.ShortLinkConstant;
import com.oxygen.shortlink.project.common.convention.exception.ClientException;
import com.oxygen.shortlink.project.common.convention.exception.ServiceException;
import com.oxygen.shortlink.project.common.enums.VailDateTypeEnum;
import com.oxygen.shortlink.project.dao.entity.*;
import com.oxygen.shortlink.project.dao.mapper.*;
import com.oxygen.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.oxygen.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.oxygen.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.oxygen.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.oxygen.shortlink.project.dto.resp.ShortLinkGroupCountRespDTO;
import com.oxygen.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.oxygen.shortlink.project.service.ShortLinkService;
import com.oxygen.shortlink.project.toolkit.HashUtil;
import com.oxygen.shortlink.project.toolkit.LinkUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author LiJinLong
 * @description 短链接接口实现层
 * @create 2024-02-19 17:14
 * @date 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    private final RBloomFilter<String> shortUriCreateCachePenetrationBloomFilter;

    private final ShortLinkGotoMapper shortLinkGotoMapper;

    private final StringRedisTemplate stringRedisTemplate;

    private final RedissonClient redissonClient;

    private final LinkAccessStatsMapper linkAccessStatsMapper;

    private final LinkLocaleStatsMapper linkLocaleStatsMapper;

    private final LinkOsStatsMapper linkOsStatsMapper;

    private final LinkBrowserStatsMapper linkBrowserStatsMapper;

    @Value("${short-link.stats.locale.amap-key}")
    private String  ipKey;


    /**
     * 创建短链接
     *
     * @param requestParam
     * @return
     */
    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam) {
        String suffix = generatorSuffix(requestParam);

        // String fullShortUrl = StrBuilder.create(requestParam.getDomain())
        String fullShortUrl = StrBuilder.create("nurl.ink:8001")
                .append("/")
                .append(suffix)
                .toString();
        ShortLinkDO shortLink = ShortLinkDO.builder()
                // .domain(requestParam.getDomain())
                .domain("nurl.ink:8001")
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid())
                .createdType(requestParam.getCreatedType())
                .validDateType(requestParam.getValidDateType())
                .validDate(requestParam.getValidDate())
                .describe(requestParam.getDescribe())
                .enableStatus(0)
                .favicon(getFavicon(requestParam.getOriginUrl()))
                // 设置hash码与短链接
                .shortUri(suffix)
                .fullShortUrl(fullShortUrl)
                .build();

        // 缓存预热 过期时间为请求中的有效期的毫秒值，未传有效期的为永久，设置1个月的过期时间
        stringRedisTemplate.opsForValue()
                .set(String.format(RedisKeyConstant.GOTO_SHORT_LINK_KEY, fullShortUrl),
                        requestParam.getOriginUrl(),
                        LinkUtil.getLinkCacheValidTime(requestParam.getValidDate()),
                        TimeUnit.MILLISECONDS);

        // 加入布隆过滤器
        shortUriCreateCachePenetrationBloomFilter.add(fullShortUrl);

        ShortLinkGotoDO linkGotoDO = ShortLinkGotoDO.builder()
                .gid(requestParam.getGid())
                .fullShortUrl(fullShortUrl)
                .build();
        // 出现唯一索引冲突，可能是多并发场景生成多个重复短链接，只有一个能插入成功，其他的都不成功，或者由于进程挂掉等原因导致布隆过滤器没有数据库有，也会导致后续索引冲突
        try {
            baseMapper.insert(shortLink);
            shortLinkGotoMapper.insert(linkGotoDO);
        } catch (DuplicateKeyException e) {
            log.info("短链接重复入库：{}", fullShortUrl);
            throw new ServiceException("短链接生成重复");
        }
        return ShortLinkCreateRespDTO.builder()
                .gid(shortLink.getGid())
                .originUrl(shortLink.getOriginUrl())
                .fullShortUrl("http://" + shortLink.getFullShortUrl())
                .build();
    }

    /**
     * 短链接分页查询
     *
     * @param requestParam
     * @return
     */
    @Override
    public IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestParam) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, requestParam.getGid())
                .eq(ShortLinkDO::getEnableStatus, 0)
                .eq(ShortLinkDO::getDelFlag, 0)
                .orderByDesc(ShortLinkDO::getCreateTime);
        IPage<ShortLinkDO> resultPage = baseMapper.selectPage(requestParam, queryWrapper);
        return resultPage.convert(each -> BeanUtil.toBean(each, ShortLinkPageRespDTO.class));
    }

    /**
     * 查询分组下短链接数量
     *
     * @param requestParam
     * @return
     */
    @Override
    public List<ShortLinkGroupCountRespDTO> listGroupShortLinkCount(List<String> requestParam) {
        QueryWrapper<ShortLinkDO> queryWrapper = Wrappers.query(new ShortLinkDO())
                .select("gid as gid, count(*) as shortLinkCount")
                .eq("enable_status", 0)
                .in("gid", requestParam)
                .groupBy("gid");
        List<Map<String, Object>> maps = baseMapper.selectMaps(queryWrapper);
        return BeanUtil.copyToList(maps, ShortLinkGroupCountRespDTO.class);
    }

    /**
     * 修改短链接
     *
     * @param requestParam
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateShortLink(ShortLinkUpdateReqDTO requestParam) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, requestParam.getGid())
                .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 0);
        ShortLinkDO hasShortLink = baseMapper.selectOne(queryWrapper);
        if (hasShortLink == null) {
            throw new ClientException("短链接不存在");
        }
        ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid())
                .describe(requestParam.getDescribe())
                .validDateType(requestParam.getValidDateType())
                .validDate(requestParam.getValidDate()).build();
        LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, requestParam.getGid())
                .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 0)
                .set(Objects.equals(requestParam.getValidDateType(), VailDateTypeEnum.PERMANENT.getType()), ShortLinkDO::getValidDate, null);
        baseMapper.update(shortLinkDO, updateWrapper);
    }

    /**
     * 短链接跳转原始链接
     *
     * @param shortUri 短链接后缀
     * @param request  HTTP 请求
     * @param response HTTP 响应
     */
    @SneakyThrows
    @Override
    public void restoreUrl(String shortUri, ServletRequest request, ServletResponse response) {
        String serverName = request.getServerName();
        String serverPort = Optional.of(request.getServerPort())
                .filter(each -> !Objects.equals(each, 80))
                .map(String::valueOf)
                .map(each -> ":" + each)
                .orElse("");
        String fullShortUrl = serverName + serverPort + "/" + shortUri;

        // 使用双检加锁的策略构造双层判定锁  双层判定锁只能保障缓存击穿，对于缓存穿透我们会加入布隆过滤器
        String originalLink = stringRedisTemplate.opsForValue().get(String.format(RedisKeyConstant.GOTO_SHORT_LINK_KEY, fullShortUrl));
        if (StrUtil.isNotBlank(originalLink)) {
            shortLinkStats(fullShortUrl, null, request, response);
            ((HttpServletResponse) response).sendRedirect(originalLink);
            return;
        }

        // 判断是否存在于布隆过滤器  在创建短链接之后就把短链接加入了布隆过滤器
        boolean contains = shortUriCreateCachePenetrationBloomFilter.contains(fullShortUrl);
        if (!contains) {
            ((HttpServletResponse) response).sendRedirect("/page/notfound");
            return;
        }

        // 判断key是否缓存了无效对象 "-"
        String invalidObject = stringRedisTemplate.opsForValue().get(String.format(RedisKeyConstant.GOTO_IS_NULL_SHORT_LINK_KEY, fullShortUrl));
        if (StrUtil.isNotBlank(invalidObject)) {
            ((HttpServletResponse) response).sendRedirect("/page/notfound");
            return;
        }

        RLock lock = redissonClient.getLock(String.format(RedisKeyConstant.LOCK_GOTO_SHORT_LINK_KEY, fullShortUrl));
        lock.lock();
        try {
            originalLink = stringRedisTemplate.opsForValue().get(String.format(RedisKeyConstant.GOTO_SHORT_LINK_KEY, fullShortUrl));
            if (StrUtil.isNotBlank(originalLink)) {
                shortLinkStats(fullShortUrl, null, request, response);
                ((HttpServletResponse) response).sendRedirect(originalLink);
                return;
            }
            LambdaQueryWrapper<ShortLinkGotoDO> gotoQueryWrapper = Wrappers.lambdaQuery(ShortLinkGotoDO.class).eq(ShortLinkGotoDO::getFullShortUrl, fullShortUrl);
            ShortLinkGotoDO shortLinkGotoDO = shortLinkGotoMapper.selectOne(gotoQueryWrapper);
            if (shortLinkGotoDO == null) {
                // 应对缓存击穿，缓存空对象
                stringRedisTemplate.opsForValue().set(String.format(RedisKeyConstant.GOTO_IS_NULL_SHORT_LINK_KEY, fullShortUrl), "-", 30, TimeUnit.MINUTES);
                ((HttpServletResponse) response).sendRedirect("/page/notfound");
                return;
            }
            LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getGid, shortLinkGotoDO.getGid())
                    .eq(ShortLinkDO::getFullShortUrl, fullShortUrl)
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0);
            ShortLinkDO shortLinkDO = baseMapper.selectOne(queryWrapper);
            if (shortLinkDO == null || shortLinkDO.getValidDate() != null && shortLinkDO.getValidDate().before(new Date())) {
                // 数据库中过期的短链接要在缓存中设置无效对象
                stringRedisTemplate.opsForValue().set(String.format(RedisKeyConstant.GOTO_IS_NULL_SHORT_LINK_KEY, fullShortUrl), "-", 30, TimeUnit.MINUTES);
                ((HttpServletResponse) response).sendRedirect("/page/notfound");
                return;
            }
            shortLinkStats(fullShortUrl, shortLinkDO.getGid(), request, response);
            stringRedisTemplate.opsForValue().set(
                    String.format(RedisKeyConstant.GOTO_SHORT_LINK_KEY, fullShortUrl),
                    shortLinkDO.getOriginUrl(),
                    LinkUtil.getLinkCacheValidTime(shortLinkDO.getValidDate()), TimeUnit.MILLISECONDS);
            ((HttpServletResponse) response).sendRedirect(shortLinkDO.getOriginUrl());
        }  finally {
            lock.unlock();
        }


    }


    private void shortLinkStats(String fullShortLink, String gid, ServletRequest request, ServletResponse response) {
        AtomicBoolean uvFirstFlag = new AtomicBoolean();
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();

        try {

            Runnable addResponseCookieTask = () -> {
                String uv = UUID.fastUUID().toString();
                Cookie uvCookie = new Cookie("uv", uv);
                uvCookie.setMaxAge(60 * 60 * 24 * 30);
                uvCookie.setPath(StrUtil.sub(fullShortLink, fullShortLink.indexOf("/"), fullShortLink.length()));
                ((HttpServletResponse) response).addCookie(uvCookie);
                uvFirstFlag.set(Boolean.TRUE);
                stringRedisTemplate.opsForSet().add("short-link:stats:uv" + fullShortLink, uv);
            };

            if (ArrayUtil.isNotEmpty(cookies)) {
                Arrays.stream(cookies)
                        .filter(each -> Objects.equals(each.getName(), "uv"))
                        .findFirst()
                        .map(Cookie::getValue)
                        .ifPresentOrElse(each ->{
                            // 看能否set进集合，如果不能则说明Redis集合中已经存了，不是第一次访问，赋值false
                            Long add = stringRedisTemplate.opsForSet().add("short-link:stats:uv" + fullShortLink, each);
                            uvFirstFlag.set(add != null && add > 0L);
                        }, addResponseCookieTask);
            }else {
                // 首次访问该链接
                addResponseCookieTask.run();
            }
            // ip可以添加进去说明是第一次访问短链接
            String actualIp = LinkUtil.getActualIp((HttpServletRequest) request);
            Long add = stringRedisTemplate.opsForSet().add("short-link:stats:uip" + fullShortLink, actualIp);
            boolean uipFlag = add != null && add > 0L;

            if(StrUtil.isBlank(gid)) {
                LambdaQueryWrapper<ShortLinkGotoDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkGotoDO.class).eq(ShortLinkGotoDO::getFullShortUrl, fullShortLink);
                ShortLinkGotoDO shortLinkGotoDO = shortLinkGotoMapper.selectOne(queryWrapper);
                gid = shortLinkGotoDO.getGid();
            }
            int hour = DateUtil.hour(new Date(), true);
            Week week = DateUtil.dayOfWeekEnum(new Date());
            int weekValue = week.getIso8601Value();
            // pv、uv、ip 是增量更新，即 pv = pv + 1
            LinkAccessStatsDO linkAccessStatsDO = LinkAccessStatsDO.builder()
                    .pv(1)
                    .uv(uvFirstFlag.get() ? 1 : 0)
                    .uip(uipFlag ? 1 : 0)
                    .hour(hour)
                    .weekday(weekValue)
                    .fullShortUrl(fullShortLink)
                    .gid(gid)
                    .date(new Date())
                    .build();
            linkAccessStatsMapper.shortLinkStats(linkAccessStatsDO);

            // 调用高德IP定位解析api接口获取地区信息
            Map<String, Object> map = new HashMap<>();
            map.put("ip", actualIp);
            map.put("key", ipKey);
            String resultArea = HttpUtil.get(ShortLinkConstant.AMAP_REMOTE_URL, map);
            JSONObject localeJsonObject = JSON.parseObject(resultArea);
            String infocode = localeJsonObject.getString("infocode");
            if (StrUtil.isNotBlank(infocode) && "10000".equals(infocode)) {
                String province = localeJsonObject.getString("province");
                String city = localeJsonObject.getString("city");
                String adcode = localeJsonObject.getString("adcode");
                boolean unKnowFlag = Objects.equals("[]", province);
                LinkLocaleStatsDO linkLocaleStatsDO = LinkLocaleStatsDO.builder()
                        .province(unKnowFlag ? "未知" : province)
                        .city(unKnowFlag ? "未知" : city)
                        .adcode(unKnowFlag ? "未知" : adcode)
                        .country("中国")
                        .cnt(1)
                        .fullShortUrl(fullShortLink)
                        .gid(gid)
                        .date(new Date())
                        .build();
                linkLocaleStatsMapper.shortLinkLocaleState(linkLocaleStatsDO);
            }

            // 监控访问来源的操作系统
            String os = LinkUtil.getOs((HttpServletRequest) request);
            LinkOsStatsDO linkOsStatsDO = LinkOsStatsDO.builder()
                    .os(os)
                    .cnt(1)
                    .fullShortUrl(fullShortLink)
                    .gid(gid)
                    .date(new Date())
                    .build();
            linkOsStatsMapper.shortLinkOsState(linkOsStatsDO);

            // 监控访问来源的浏览器
            String browser = LinkUtil.getBrowser((HttpServletRequest) request);
            LinkBrowserStatsDO linkBrowserStatsDO = LinkBrowserStatsDO.builder()
                    .browser(browser)
                    .cnt(1)
                    .fullShortUrl(fullShortLink)
                    .gid(gid)
                    .date(new Date())
                    .build();
            linkBrowserStatsMapper.shortLinkBrowserState(linkBrowserStatsDO);

        } catch (Exception e) {
            log.error("短链接访问量统计异常", e);
        }
    }



    String generatorSuffix(ShortLinkCreateReqDTO requestParam) {
        int customGenerateNum = 0;
        String shortUrl = null;
        while (true) {
            if (customGenerateNum > 10) {
                throw new ServiceException("短链接生成频繁，请稍后再试");
            }
            // 每次重试给原始链接拼接当前毫秒数避免重复生成，字符串不变每次hash自然一样
            String originUrl = requestParam.getOriginUrl() + System.currentTimeMillis();
            shortUrl = HashUtil.hashToBase62(originUrl);
            if (!shortUriCreateCachePenetrationBloomFilter.contains("nurl.ink:8001" + "/" + shortUrl)) {
                break;
            }
            customGenerateNum++;
        }
        return shortUrl;
    }


    @SneakyThrows
    private String getFavicon(String url) {
        URL targetUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (HttpURLConnection.HTTP_OK == responseCode) {
            Document document = Jsoup.connect(url).get();
            Element faviconLink = document.select("link[rel~=(?i)^(shortcut )?icon]").first();
            if (faviconLink != null) {
                return faviconLink.attr("abs:href");
            }
        }
        return null;
    }
}
