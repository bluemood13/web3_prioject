package cn.happain.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Objects;

/**
 * 通用HTTP请求工具类（修复版）
 * 修复点：超时配置、异常处理、空值校验、重试机制、资源释放
 *
 * @author happain
 * @date 2023/2/12
 * @desc 基于Hutool封装的HTTP工具，支持GET/POST/下载，增强稳定性
 */
@Slf4j
public class HappainRequestUtil {
    // 1. 延长默认超时时间（适配海外API，解决连接超时）
    private static final Integer DEFAULT_TIMEOUT = 20000;
    // 2. 默认重试次数（解决偶发网络波动）
    private static final int DEFAULT_RETRY_TIMES = 3;
    // 3. 重试间隔（毫秒）
    private static final int RETRY_INTERVAL = 1000;

    /**
     * 发送GET请求（带重试、超时优化）
     *
     * @param url 请求地址
     * @return HttpResponse 响应对象
     */
    public static HttpResponse get(String url) {
        return get(url, DEFAULT_TIMEOUT);
    }

    /**
     * 发送GET请求（自定义超时）
     *
     * @param url     请求地址
     * @param timeout 超时时间（毫秒）
     * @return HttpResponse 响应对象
     */
    public static HttpResponse get(String url, int timeout) {
        // 空值校验
        if (url == null || url.trim().isEmpty()) {
            log.error("GET请求URL为空");
            throw new IllegalArgumentException("请求URL不能为空");
        }

        Exception lastException = null;
        // 重试机制
        for (int i = 0; i < DEFAULT_RETRY_TIMES; i++) {
            try {
                log.debug("第{}次发送GET请求：{}", i + 1, url);
                HttpRequest request = HttpRequest.get(url)
                        .timeout(timeout)
                        // 添加通用请求头，模拟浏览器（防反爬）
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                HttpResponse response = request.execute();
                // 确保响应正常，非200也返回（由调用方处理）
                return response;
            } catch (HttpException e) {
                lastException = e;
                log.warn("第{}次GET请求失败：{}，原因：{}", i + 1, url, e.getMessage());
                // 最后一次重试失败则抛出异常
                if (i == DEFAULT_RETRY_TIMES - 1) {
                    log.error("GET请求重试{}次后仍失败：{}", DEFAULT_RETRY_TIMES, url, e);
                    throw e;
                }
                // 重试间隔
                try {
                    Thread.sleep(RETRY_INTERVAL);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        // 理论上不会走到这里，兜底抛出异常
        throw new HttpException("GET请求失败：" + url, lastException);
    }

    /**
     * 【自定义代理】GET请求（支持代理认证）
     *
     * @param url  请求URL
     * @param ip   知识产权
     * @param port 港口
     * @return HttpResponse 响应对象
     */
    public static HttpResponse get(String url,String ip,Integer port) {
        Exception lastException = null;
        // 重试机制
        for (int i = 0; i < DEFAULT_RETRY_TIMES; i++) {
            try {
                HttpRequest request = HttpRequest.get(url)
                        // 添加通用请求头，模拟浏览器（防反爬）
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

                // 配置代理（如果代理信息不为空）
                // 代理地址
                request.setHttpProxy(ip, port);
                HttpResponse response = request.execute();
                // 确保响应正常，非200也返回（由调用方处理）
                return response;
            } catch (HttpException e) {
                lastException = e;
                // 最后一次重试失败则抛出异常
                if (i == DEFAULT_RETRY_TIMES - 1) {
                    throw e;
                }
                // 重试间隔
                try {
                    Thread.sleep(RETRY_INTERVAL);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return null;
    }

    /**
     * 发送POST请求（JSON格式、带重试、超时优化）
     *
     * @param url     请求地址
     * @param body    请求体（JSON字符串）
     * @param headers 请求头（可空）
     * @return HttpResponse 响应对象
     * @throws HttpException HTTP异常
     */
    public static HttpResponse post(String url, String body, HashMap<String, String> headers) throws HttpException {
        return post(url, body, headers, DEFAULT_TIMEOUT);
    }

    /**
     * 发送POST请求（自定义超时）
     *
     * @param url     请求地址
     * @param body    请求体（JSON字符串）
     * @param headers 请求头（可空）
     * @param timeout 超时时间（毫秒）
     * @return HttpResponse 响应对象
     * @throws HttpException HTTP异常
     */
    public static HttpResponse post(String url, String body, HashMap<String, String> headers, int timeout) throws HttpException {
        // 空值校验
        if (url == null || url.trim().isEmpty()) {
            log.error("POST请求URL为空");
            throw new IllegalArgumentException("请求URL不能为空");
        }
        if (body == null) {
            body = ""; // 兼容空请求体
        }

        Exception lastException = null;
        // 重试机制
        for (int i = 0; i < DEFAULT_RETRY_TIMES; i++) {
            try {
                log.debug("第{}次发送POST请求：{}，请求体：{}", i + 1, url, body);
                HttpRequest request = HttpRequest.post(url)
                        .header("Content-Type", "application/json;charset=UTF-8")
                        // 添加通用请求头
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                        .body(body)
                        .timeout(timeout);

                // 处理请求头（非空时添加）
                if (headers != null && !headers.isEmpty()) {
                    request.headerMap(headers, true);
                }

                HttpResponse response = request.execute();
                return response;
            } catch (HttpException e) {
                lastException = e;
                log.warn("第{}次POST请求失败：{}，原因：{}", i + 1, url, e.getMessage());
                if (i == DEFAULT_RETRY_TIMES - 1) {
                    log.error("POST请求重试{}次后仍失败：{}", DEFAULT_RETRY_TIMES, url, e);
                    throw e;
                }
                // 重试间隔
                try {
                    Thread.sleep(RETRY_INTERVAL);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new HttpException("POST请求失败：" + url, lastException);
    }

    /**
     * 下载文件（增强异常处理、资源释放）
     *
     * @param url  下载地址
     * @param path 保存路径（目录）
     * @return File 下载后的文件对象
     */
    public static File download(String url, String path) {
        // 空值校验
        if (url == null || url.trim().isEmpty()) {
            log.error("下载URL为空");
            throw new IllegalArgumentException("下载URL不能为空");
        }
        if (path == null || path.trim().isEmpty()) {
            log.error("保存路径为空");
            throw new IllegalArgumentException("保存路径不能为空");
        }

        // 确保目录存在
        File saveDir = FileUtil.file(path);
        if (!saveDir.exists()) {
            boolean mkdirs = saveDir.mkdirs();
            if (!mkdirs) {
                log.error("创建保存目录失败：{}", path);
                throw new RuntimeException("创建保存目录失败：" + path);
            }
        }

        try {
            log.info("开始下载文件：{} -> {}", url, path);
            File file = HttpUtil.downloadFileFromUrl(url, saveDir);
            log.info("文件下载完成：{}，文件名：{}", path, Objects.requireNonNull(file).getName());
            return file;
        } catch (Exception e) {
            log.error("文件下载失败：{}", url, e);
            throw new RuntimeException("文件下载失败：" + url, e);
        }
    }

    /**
     * 关闭HttpResponse（释放资源，避免内存泄漏）
     *
     * @param response 响应对象
     */
    public static void closeResponse(HttpResponse response) {
        if (response != null) {
            try {
                response.close();
            } catch (Exception e) {
                log.warn("关闭HttpResponse失败", e);
            }
        }
    }

    // 测试主方法
    public static void main(String[] args) {
        try {
//            // 测试下载功能
//            File downloadFile = HappainRequestUtil.download(
//                    "https://oaidalleapiprodscus.blob.core.windows.net/private/org-KqOzf2HHV0kGRqCz4cYTu5aT/user-YzlBH9Hk5MV85GmyImr2QyOh/img-NcAUjzIbsdZPNTmuZWmmDvai.png?st=2023-02-27T21%3A10%3A42Z&se=2023-02-27T23%3A10%3A42Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-02-27T21%3A41%3A53Z&ske=2023-02-28T21%3A41%3A53Z&sks=b&skv=2021-08-06&sig=UI7bj3A0a8Se5opXv6YXx7xcY9SkCaSQnSXsux5IoM4%3D",
//                    "D:\\my_project\\happain-one-springboot-cli\\file"
//            );
//            System.out.println("下载文件名称：" + downloadFile.getName());

            // 测试GET请求（Solana数据API，国内可访问）
            HttpResponse getResp = HappainRequestUtil.get("https://api.coingecko.com/api/v3/coins/solana");
            System.out.println("GET请求响应状态码：" + getResp.getStatus());
            System.out.println("GET请求响应内容：" + getResp.body().substring(0, 200) + "...");
            closeResponse(getResp); // 释放资源

        } catch (Exception e) {
            log.error("测试失败", e);
        }
    }

    private static String formatProxy(String proxyHost, int proxyPort) {
        String host = proxyHost == null ? "" : proxyHost.trim();
        if (host.isEmpty()) {
            return "无代理";
        }
        return host + ":" + proxyPort;
    }
}
