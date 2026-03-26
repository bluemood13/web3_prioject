package cn.happain.common.modules.generater.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author happain
 * @创建人 happain
 * @创建时间 2023/2/3
 * @描述
 */

@Data
@Accessors(chain = true)
public class TableVo {

    /**
     * 包名
     */
    private String packageName;

    /**数据库名*/
    private String tableSchema;

    /**表名*/
    private String tableName;
    /**
     * 对象名称，首字母大写
     */
    private String modelName;

    /**
     * 对象名称 首字母小写
     * */
    private String lowerModelName;
    /**表注释*/
    private String tableComment;

    /**表编码*/
    private String tableCollation;
    /**
     * 表字段信息
     */
    private List<TableFieldVo> tableFields;

    /**
     * 表前缀
     */
    private String tablePrefix;




}
