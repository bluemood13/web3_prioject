package cn.happain.module.sys.module.info.domain.model.dto;

import cn.happain.common.domain.dto.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class InfoDto extends PageDto  {
    /**
    * 
    */
    private Integer id;

    /**
    * 
    */
    private String info;

}
