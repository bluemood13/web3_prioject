package cn.happain.common.modules.generater.util;

import cn.happain.common.modules.generater.domain.vo.TableFieldVo;
import cn.happain.common.modules.generater.domain.vo.TableVo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 代码生成工具
 *
 * @author
 * @date 2024/10/01
 */
@Component
@Slf4j
public class GeneraterDbUtil {

    /**
     * 得到表信息
     *
     * @param tableName 表名
     * @return {@link TableVo}
     */
    public TableVo getTableInfo(String db, String tableName, String parnetPath, String prefix) {
        // TODO: 2023/2/24 获取数据库表信息
        // TODO: 2023/2/24 查询表信息
        Map<String, Object> tableinfo = SqlRunner.db().selectOne(StrUtil.format("select table_schema,table_name,table_comment,table_collation,table_comment from information_schema.tables where table_name='{}' and table_schema='{}';", tableName,db));
        TableVo tableVo = BeanUtil.toBean(tableinfo, TableVo.class, CopyOptions.create());
        // TODO: 2023/2/24 查询表字段信息
        List<Map<String, Object>> maps = SqlRunner.db().selectList(StrUtil.format("select * from  information_schema.columns where table_name = '{}' and table_schema='{}'", tableName,db));
        List<TableFieldVo> tableFieldVos = BeanUtil.copyToList(maps, TableFieldVo.class,CopyOptions.create().ignoreCase()).stream().map(x -> x.setJavaName(StrUtil.toCamelCase(x.getColumnName()))).collect(Collectors.toList());
        //去掉前缀的表名
        String modelname = tableName.startsWith(prefix) ? tableName.substring(prefix.length()): tableName;
        //将表名设置为驼峰的格式
        tableVo.setModelName(StrUtil.upperFirst(StrUtil.toCamelCase(modelname)));
        //设置表前缀
        tableVo.setTablePrefix(prefix.replace("_",""));
        //设置为首字母小写
        tableVo.setLowerModelName(StrUtil.lowerFirst(StrUtil.toCamelCase(modelname)));
        tableVo.setTableFields(tableFieldVos);
        //设置包名
        tableVo.setPackageName(parnetPath+"."+StrUtil.lowerFirst((StrUtil.toCamelCase(modelname))));
        System.out.println(tableVo);
        log.error("生成表信息："+tableVo.toString());

        return tableVo;
    }

}
