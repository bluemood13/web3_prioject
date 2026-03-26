package cn.happain.common.config.fisco.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(
    prefix = "fisco-local"
)
public class FiscoLocalConfig extends FiscoBaseConfig {
}
