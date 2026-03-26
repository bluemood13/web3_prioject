package ${packageName}.domain.model;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.sql.Date;
import java.sql.Time;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "${tableName}")
public class ${modelName}Model  {
<#list tableFields as item>
    <#--判断主键-->
    <#if item.columnKey == 'PRI' && item.extra ==  'auto_increment' >
    @TableId(value="${item.columnName}",type = IdType.AUTO)
    <#elseif item.columnKey == 'PRI'>
    @TableId(value="${item.columnName}")
    <#else>
        <#if item.columnName == 'create_time'>
    @TableField(value = "create_time", fill = FieldFill.INSERT)
        <#elseif item.columnName == 'create_user'>
    @TableField(value = "create_user", fill = FieldFill.INSERT)
        <#elseif item.columnName == 'update_time'>
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
        <#elseif item.columnName == 'update_user'>
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
        <#else>
    @TableField(value = "${item.columnName}")
        </#if>
    </#if>
    <#--判断整数类型-->
    <#if item.dataType == 'int'>
    private Integer ${item.javaName};
    <#--判断浮点数类型-->
    <#elseif item.dataType == 'double'>
    private Double ${item.javaName};
    <#--判断字符串类型-->
    <#elseif item.dataType == 'varchar' || item.dataType == 'text'>
    private String ${item.javaName};
    <#--判断布尔类型-->
    <#elseif item.dataType == 'bit' >
    private Boolean ${item.javaName};
    <#elseif item.dataType == 'date' >
    private Date ${item.javaName};
    <#elseif item.dataType == 'time' >
    private Time ${item.javaName};
    <#elseif item.dataType == 'datetime' >
    private LocalDateTime ${item.javaName};
    </#if>

</#list>
}
