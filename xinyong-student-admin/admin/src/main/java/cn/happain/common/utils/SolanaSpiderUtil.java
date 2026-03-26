package cn.happain.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
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
public class SolanaSpiderUtil {


    public void getSolanaData() {
        HttpResponse httpResponse = HappainRequestUtil.get("https://api.coingecko.com/api/v3/coins/solana","127.0.0.1",7890);
        String body = httpResponse.body();
        JSONObject entries = JSONUtil.parseObj(body);
        System.out.println(entries);
    }
    // 测试主方法
    public static void main(String[] args) {
    }

}
