package cn.happain.common.config.fisco.utils;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class IOUtil {
    /**
     * 读取字符串
     *
     * @param path
     * @return {@link String }
     */
    public static String readString(String path) {
        return FileUtil.readString(path, Charset.defaultCharset());
    }
}
