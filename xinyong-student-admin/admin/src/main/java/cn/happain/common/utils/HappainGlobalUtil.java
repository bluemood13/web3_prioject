package cn.happain.common.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author happain
 * @desc 全局工具类
 * @since 2023/2/18
 */

@Slf4j
public class HappainGlobalUtil {

    /**
     * 拦截器返回自定义响应请求
     *
     * @param response 响应
     * @param message  消息
     * @return {@link String}
     */
    public static void interceptorResponse(HttpServletResponse response, String message) throws IOException {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.getWriter().write(message);
    }

    /**
     * 获取登陆的用户id
     *
     * @return {@link Integer}
     */
    public static Integer getUserId() {
        return Integer.parseInt(StpUtil.getLoginId().toString());
    }


    /**
     * 获取时间戳
     *
     * @return {@link String}
     */
    public static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }


    /**
     * 类对象转为json字符串
     *
     * @param obj OBJ
     * @return {@link String}
     */
    public static String beanToJsonStr(Object obj) {
        return JSONUtil.toJsonStr(JSONUtil.parse(obj));
    }

    /**
     * json字符串转json对象
     *
     * @param data 数据
     * @return {@link String}
     */
    public static  <T> T jsonStrToBean(String data,Class<T> beanClass) {
        return JSONUtil.toBean(JSONUtil.parseObj(data),beanClass);
    }
    /**
     * 计算文件的MD5值,特征指纹计算
     *
     * @param file 要计算MD5值的文件对象
     * @return 文件的MD5值字符串，如果计算失败返回null
     */
    public static String calculateFileMD5(File file) {
        MessageDigest md5Digest;
        try {
            md5Digest = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                md5Digest.update(buffer, 0, length);
            }
            fis.close();
            byte[] md5Bytes = md5Digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : md5Bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
