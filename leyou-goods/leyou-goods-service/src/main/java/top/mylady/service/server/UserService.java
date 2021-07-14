package top.mylady.service.server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.service.mappers.user.UserMapper;
import top.mylady.service.pojo.User;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据id查询用户
     */
    public User queryById(Long id){
        return this.userMapper.queryUserById(id);
    }
}
