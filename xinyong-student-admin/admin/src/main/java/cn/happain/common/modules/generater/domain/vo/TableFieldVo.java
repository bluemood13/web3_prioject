package cn.happain.common.modules.generater.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 表字段签证官
 *
 * @author happain
 * @创建人 happain
 * @创建时间 2023/2/3
 * @描述
 * @date 2023/02/24
 */
@Data
@Accessors(chain = true)
public class TableFieldVo {
    /**列名*/
    private String columnName;
    /**驼峰字段名*/
    private String javaName;
    /**主键标识*/
    private String columnKey;
    /**额外信息*/
    private String extra;
    /**列标识符 在表中的顺序*/
    private Integer ordinalPosition;
    /**列的默认值*/
    private String columnDefault;
    /**是否为空*/
    private Boolean isNullable;
    /**字段的备注*/
    private String columnComment;
    /**表的注释*/
    private String tableComment;
    /**表在数据库中的数据类型*/
    private String dataType;
    /**表中的类型*/
    private String columnType;
    /**以字符为长度*/
    private Integer characterMaximumLength;
    /**以字节为长度*/
    private Integer characterOctetLength;


}
