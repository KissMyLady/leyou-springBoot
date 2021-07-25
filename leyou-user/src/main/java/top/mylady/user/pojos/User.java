package top.mylady.user.pojos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;


@Data
public class User implements Serializable {

    private Long id;

    /**
     * 用户名
     */
    //@Length(min = 4,max = 15,message = "用户名只能在4~15位之间")
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    //@Length(min = 6,max = 25,message = "密码只能在6~25位之间")
    private String password;

    /**
     * 电话
     */
    //@Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @JsonIgnore
    private String salt;
}
