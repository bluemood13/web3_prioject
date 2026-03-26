package cn.happain.common.modules.generater.util;


import cn.happain.common.modules.generater.domain.vo.TableVo;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * The type Template util.
 *
 * @创建人 happain
 * @创建时间 2023 /2/4
 * @描述
 */
@Component
public class GeneraterTemplateUtil {

    @Resource
    private  FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 解析ftl模板
     *
     * @param templateName 模板名称
     * @param tableVo      表签证官
     * @return {@link String}
     * @throws IOException       ioexception
     * @throws TemplateException 模板异常
     */
    public String parseFtlTemplate(String templateName, TableVo tableVo) throws IOException, TemplateException {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate(templateName);
        /*结果*/
        StringWriter stringWriter = new StringWriter();
        /*填充模板*/
        template.process(tableVo,stringWriter);
        return stringWriter.toString();
    }
    /**
     * 创建文件
     *
     * @param templateContent 模板内容
     * @param path            在公共路径下的完整路径
     */
    public void createFile(String templateContent,String path) throws FileNotFoundException {
        String s = Paths.get("").toAbsolutePath().toString();
        File file = FileUtil.file(s+"\\code",path);
        FileWriter fileWriter = FileWriter.create(file);
        fileWriter.write(templateContent);
    }

}
