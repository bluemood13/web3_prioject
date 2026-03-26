package cn.happain.common.config.hutool.conver;

import cn.hutool.core.convert.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 本地日期时间到字符串字符串转换器
 *
 * @author happain
 * @date 2025/11/17
 */
public class LocalDateTimeToStringStringConverter implements Converter<String> {
    @Override
    public String convert(Object value, String defaultValue) throws IllegalArgumentException {
        if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        // 2.1 如果value为null，返回defaultValue（符合Hutool默认逻辑）
        if (value == null) {
            return defaultValue;
        }

        // 2.2 否则返回value自身的字符串形式（比如value是String/Integer等，直接转String）
        return value.toString();
    }
}