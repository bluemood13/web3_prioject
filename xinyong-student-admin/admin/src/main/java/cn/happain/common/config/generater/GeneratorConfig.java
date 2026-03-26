package cn.happain.common.config.generater;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


/**
 * 代码生成器配置
 *
 * @author
 * @date 2024/10/01
 */
@Configuration
public class GeneratorConfig {
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(){
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding("UTF-8");
        configurer.setTemplateLoaderPath("classpath:/generaterTemplates");
        return configurer;
    }
}
