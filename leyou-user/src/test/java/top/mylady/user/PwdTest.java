package top.mylady.user;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.user.utils.CodecUtils;


/**
 * 密码测试类
 */
@SpringBootTest(classes=UserApp.class)
@RunWith(SpringRunner.class)
public class PwdTest {


    @Test
    public void testCheckPwd(){

        //加密对象
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String raw_pwd = "12312312";
        String username = "zhangmazi";
        String salt = "isOk";
        String encode = CodecUtils.passwordBcryptEncode(raw_pwd, username);
        System.out.println("encode: "+ encode);  //$2a$10$AWDoc6f291tjVlB6eJquZOYYFjrOwvtkJ5J5.Bg3qIksG0ExeY7Se

        Boolean bool = CodecUtils.passwordConfirm(raw_pwd+ username, encode);
        System.out.println("是否为原密码: "+ bool);  //true
    }



}
