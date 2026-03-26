package ${packageName}.domain.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.sql.Date;
import java.sql.Time;
import com.fasterxml.jackson.annotation.JsonFormat;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ${modelName}Vo {
<#list tableFields as item>
    /**
    * ${item.columnComment}
    */
    <#--判断整数类型-->
    <#if item.dataType == 'int'>
    private Integer ${item.javaName};
    <#--判断字符串类型-->
    <#elseif item.dataType == 'varchar' || item.dataType == 'text'>
    private String ${item.javaName};
    <#--判断浮点数类型-->
    <#elseif item.dataType == 'double'>
    private Double ${item.javaName};
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

</#list>
}
