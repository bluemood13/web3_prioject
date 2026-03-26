package ${packageName}.domain.dto;
import cn.happain.common.domain.dto.PageDto;
import cn.happain.common.domain.dto.valid.BaseGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import java.sql.Date;
import java.sql.Time;
import com.fasterxml.jackson.annotation.JsonFormat;


@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ${modelName}Dto extends PageDto  {
<#list tableFields as item>
    /**
    * ${item.columnComment}
    */
    <#if item.columnName != 'create_time' && item.columnName != 'create_user' && item.columnName != 'update_time' && item.columnName != 'update_user'>
    <#--判断主键-->
    <#if item.columnKey == 'PRI' >
    @NotNull(message = "${item.columnName}不能为空",groups = {BaseGroup.del.class,BaseGroup.detail.class,BaseGroup.upd.class})
    <#elseif item.dataType == 'varchar' || item.dataType == 'text'>
    @NotBlank(message="${item.columnComment}不能为空",groups = {})
    <#else>
    @NotNull(message="${item.columnComment}不能为空",groups = {})
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ${item.javaName};
    <#elseif item.dataType == 'time' >
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Time ${item.javaName};
    <#elseif item.dataType == 'datetime' >
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime ${item.javaName};
    </#if>
    </#if>

</#list>
}
