package cn.happain.module.sys.module.file.domain.model.dto;

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
public class FileDto extends PageDto  {
    /**
    * 
    */
    private Integer id;


    private String fileOriginName;

    /**
    * 文件名
    */
    private String fileName;

    /**
    * 文件大小字节
    */
    private Integer fileSize;

    /**
    * 文件目录
    */
    private String filePath;



    private String fileUrl;

}
