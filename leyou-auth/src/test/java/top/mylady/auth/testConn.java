package top.mylady.auth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.auth.entity.UserInfo;
import top.mylady.auth.utils.JwtUtils;
import top.mylady.auth.utils.RsaUtils;

import java.security.PrivateKey;
import java.security.PublicKey;


@SpringBootTest(classes = AuthApp.class)
@RunWith(SpringRunner.class)
public class testConn {

    private static final Logger logger = LoggerFactory.getLogger(testConn.class);

    private static final String pubKeyPath = "H:\\myPprogramming\\javaCode\\leyou_springBoot\\libs\\rsa.pub";

    private static final String privateKeyPath = "H:\\myPprogramming\\javaCode\\leyou_springBoot\\libs\\rsa.pri";

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Before
    public void testGetRsa() throws Exception{
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(privateKeyPath);
    }

    /**
     * 运行这段代码生成密钥对, 注意要屏蔽Before
     */
    @Test
    public void testRsa() throws Exception{
        System.out.println("ok");
        RsaUtils.generateKey(pubKeyPath, privateKeyPath, "24");
    }

    /**
     * 生成token
     */
    @Test
    public void testGenerateToken() throws Exception{
        UserInfo rawUser = new UserInfo();
        rawUser.setId(30L);
        rawUser.setUsername("Rick");

        String token = JwtUtils.generateToken(rawUser, privateKey, 50);
        logger.info("打印测试生成的token: "+ token);

        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        logger.info("打印解析的user: "+ user);
    }

    /**
     * 解析token
     */
    @Test
    public void testAny() throws Exception{
        String token = "token";
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("打印通过token解析到的user: "+ user);
    }


}
