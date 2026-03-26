package cn.happain.common.config.fisco.config;


import lombok.Data;

@Data
public class FiscoBaseConfig {

    private String peers;

    private int groupId;

    private String certPath;
}
