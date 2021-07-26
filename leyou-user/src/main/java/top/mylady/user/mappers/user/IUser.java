package top.mylady.user.mappers.user;


import org.apache.ibatis.annotations.Param;
import top.mylady.user.pojos.User;

public interface IUser {
    void test();

    User checkUser(@Param("user") User user);

    /**
     * 注册, 插入数据
     */
    int register(@Param("user") User user);

    int insertSelective(@Param("user") User user);
}
