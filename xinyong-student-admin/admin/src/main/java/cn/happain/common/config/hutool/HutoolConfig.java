package cn.happain.common.config.hutool;

import cn.happain.common.config.hutool.conver.LocalDateTimeToStringStringConverter;
import cn.hutool.core.convert.ConverterRegistry;
import org.springframework.stereotype.Component;


/**
 * hutool 配置
 *
 * @author happain
 * @date 2025/11/17
 */
@Component
public class HutoolConfig {

    public HutoolConfig() {
        // 1. 获取 Hutool 全局转换器注册表
        ConverterRegistry registry = ConverterRegistry.getInstance();
        // 2. 注册自定义转换器：
        //    第一个参数：目标类型（要转换到的类型，这里是 String.class）
        //    第二个参数：自定义转换器的类（我们写的 LocalDateTimeToStringStringConverter）
        registry.putCustom(String.class, LocalDateTimeToStringStringConverter.class);
    }
}
