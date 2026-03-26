package cn.happain.common.modules.generater;

import cn.happain.common.modules.generater.domain.vo.TableVo;
import cn.happain.common.modules.generater.util.GeneraterDbUtil;
import cn.happain.common.modules.generater.util.GeneraterTemplateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;


/**
 * Generater 服务
 *
 * @author
 * @date 2024/10/01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GeneraterService {

    @Resource
    private GeneraterDbUtil generaterDbUtil;
    @Resource
    private GeneraterTemplateUtil generaterTemplateUtil;


    public String createPath(String tableName, String parentPath, String prefix) {
        return FileUtil.normalize(parentPath.replace(".",File.separator)+ File.separator+ StrUtil.toCamelCase(tableName.substring(prefix.length()))+File.separator);
    }

    public String createVuePathApi(String tableName,String prefix) {
        return FileUtil.normalize("vue/api"+ File.separator+ StrUtil.toCamelCase(tableName.substring(prefix.length()))+File.separator);
    }

    public String createVuePathView(String tableName,String prefix) {
        return FileUtil.normalize("vue/views"+ File.separator+ StrUtil.toCamelCase(tableName.substring(prefix.length()))+File.separator);
    }

    /**
     * 创建代码
     * 生成代码
     *
     * @param tableName  表名
     * @param parentPath 父路径 cn.happain.generater.module
     * @throws TemplateException 模板异常
     * @throws IOException       ioexception
     */
    public void createCode(String db,String tableName,String parentPath,String prefix) throws TemplateException, IOException {
        String removePrefix = StrUtil.upperFirst(StrUtil.toCamelCase(tableName.substring(prefix.length())));
        // TODO: 2023/2/24 获取表信息
        TableVo vo = generaterDbUtil.getTableInfo(db,tableName,parentPath,prefix);
        System.err.println(vo);
        // TODO: 2023/2/24  生成代码模板
        String modelCode = generaterTemplateUtil.parseFtlTemplate("model.ftl", vo);
        generaterTemplateUtil.createFile(modelCode, createPath(tableName, parentPath, prefix) + StrUtil.format("domain/model/{}Model.java", removePrefix));

        String dtoCode = generaterTemplateUtil.parseFtlTemplate("dto.ftl", vo);
        generaterTemplateUtil.createFile(dtoCode, createPath(tableName, parentPath, prefix) + StrUtil.format("domain/model/dto/{}Dto.java", removePrefix));

        String dtoGroupCode = generaterTemplateUtil.parseFtlTemplate("dtoGroup.ftl", vo);
        generaterTemplateUtil.createFile(dtoGroupCode, createPath(tableName, parentPath, prefix) + StrUtil.format("domain/model/dto/group/{}Group.java", removePrefix));

        String mapperXmlCode = generaterTemplateUtil.parseFtlTemplate("mapperXml.ftl", vo);
        generaterTemplateUtil.createFile(mapperXmlCode, createPath(tableName, parentPath, prefix) + StrUtil.format("mapper/{}Mapper.xml", removePrefix));


        String voCode = generaterTemplateUtil.parseFtlTemplate("vo.ftl", vo);
        generaterTemplateUtil.createFile(voCode, createPath(tableName, parentPath, prefix) + StrUtil.format("domain/model/vo/{}Vo.java", removePrefix));

        String mapperCode = generaterTemplateUtil.parseFtlTemplate("mapper.ftl", vo);
        generaterTemplateUtil.createFile(mapperCode, createPath(tableName, parentPath, prefix) + StrUtil.format("mapper/{}Mapper.java", removePrefix));

        String serviceCode = generaterTemplateUtil.parseFtlTemplate("service.ftl", vo);
        generaterTemplateUtil.createFile(serviceCode, createPath(tableName,parentPath,prefix) + StrUtil.format("service/I{}Service.java",removePrefix));

        String serviceImplCode = generaterTemplateUtil.parseFtlTemplate("serviceImpl.ftl", vo);
        generaterTemplateUtil.createFile(serviceImplCode, createPath(tableName,parentPath,prefix) + StrUtil.format("service/impl/{}ServiceImpl.java",removePrefix));

        String controllerCode = generaterTemplateUtil.parseFtlTemplate("controller.ftl", vo);
        generaterTemplateUtil.createFile(controllerCode, createPath(tableName,parentPath,prefix) + StrUtil.format("controller/{}Controller.java",removePrefix));

        // TODO: 2023/12/10 vue文件
        /*index.vue*/
        String viewCode = generaterTemplateUtil.parseFtlTemplate("/vue/index.ftl", vo);
        generaterTemplateUtil.createFile(viewCode, createVuePathView(tableName,prefix) + "index.vue");

        String addCode = generaterTemplateUtil.parseFtlTemplate("/vue/add.ftl", vo);
        generaterTemplateUtil.createFile(addCode, createVuePathView(tableName,prefix) +"components/add.vue");

        String updCode = generaterTemplateUtil.parseFtlTemplate("/vue/upd.ftl", vo);
        generaterTemplateUtil.createFile(updCode, createVuePathView(tableName,prefix) + "components/upd.vue");

        String searchCode = generaterTemplateUtil.parseFtlTemplate("/vue/search.ftl", vo);
        generaterTemplateUtil.createFile(searchCode, createVuePathView(tableName,prefix) + "components/search.vue");

        String apiCode = generaterTemplateUtil.parseFtlTemplate("/vue/api.ftl", vo);
        generaterTemplateUtil.createFile(apiCode, createVuePathApi(tableName,prefix) + "index.ts");
    }
}