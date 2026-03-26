package cn.happain.module.sys.module.user.domain.dto;

import cn.happain.common.domain.dto.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author happain
 * @date 2023/2/13
 * @desc
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
public class UserDto extends PageDto {

    private Integer id;


    private String username;

    private Double money;

    private String password;

    private String avatar;

    private String newPassword;

    private String name;

    private Integer sex;

    private String phone;

    private String email;

    private String content;

    private List<Integer> roleIds;

    private String token;

}
