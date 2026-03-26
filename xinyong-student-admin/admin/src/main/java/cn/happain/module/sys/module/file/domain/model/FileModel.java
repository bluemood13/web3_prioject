package cn.happain.module.sys.module.file.domain.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "sys_file")
public class FileModel  {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "file_name")
    private String fileName;

    @TableField(value = "file_origin_name")
    private String fileOriginName;

    @TableField(value = "file_size")
    private Integer fileSize;

    @TableField(value = "file_path")
    private String filePath;

    private String fileUrl;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Integer createUser;

}
