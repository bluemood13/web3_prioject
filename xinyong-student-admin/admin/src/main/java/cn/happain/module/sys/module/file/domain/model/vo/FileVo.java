package cn.happain.module.sys.module.file.domain.model.vo;

import cn.happain.module.sys.module.user.domain.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FileVo {
    /**
    * 
    */
    private Integer id;

    /**
    * 文件名
    */
    private String fileName;


    private String fileOriginName;
    /**
    * 文件大小字节
    */
    private Integer fileSize;

    /**
    * 文件目录
    */
    private String filePath;


    private String fileUrl;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;


    /**
    * 
    */
    private Integer createUser;

    private UserVo createUserVo;

}
