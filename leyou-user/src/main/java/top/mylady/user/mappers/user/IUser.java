package top.mylady.user.mappers.user;


import org.apache.ibatis.annotations.Param;
import top.mylady.user.pojos.User;

public interface IUser {
    void test();

    User checkUser(@Param("user") User user);

}
