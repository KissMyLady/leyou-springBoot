package top.mylady.user.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.user.mappers.user.IUser;
import top.mylady.user.pojos.User;
import top.mylady.utils.dtos.AppHttpCodeEnum;
import top.mylady.utils.dtos.ResponseResult;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IUser iUser;

    /**
     * 校验数据
     */
    public ResponseResult checkData(String data, Integer type){
        User user = new User();
        logger.info("user:开始对用户信息进行校验");

        //校验类型
        switch (type){
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        //查询
        User toBe_notToBe = null;
        try {
            logger.info("打印recode: "+ user);
            toBe_notToBe = this.iUser.checkUser(user);
        }
        catch (Exception e){
            System.out.println("");
        }

        if (toBe_notToBe == null) {
            return ResponseResult.okResult(0, "查询, 没有当前用户");
        } else {
            return ResponseResult.okResult(1, "查询, 存在用户");
        }

    }

}
