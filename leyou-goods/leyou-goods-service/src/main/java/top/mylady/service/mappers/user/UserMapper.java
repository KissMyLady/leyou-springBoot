package top.mylady.service.mappers.user;
import top.mylady.service.pojo.User;


public interface UserMapper {

    User queryUserById(Long id);

    User feignQueryUserById(Long id);
}
