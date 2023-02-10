package user;

import user.json.UserCreateJson;
import user.json.UserEditJson;
import user.json.UserLoginJson;

public class UserGenerator {
   public static UserCreateJson getDefaultCreateUser(){
       return new UserCreateJson("dima-test@yandex.ru", "123456", "DimaM");
   }

    public static UserCreateJson getBadDefaultCreateUser(){
        return new UserCreateJson("dima-test@yandex.ru", "", "DimaM");
    }

    public static UserLoginJson getDefaultLoginUser(){
        return new UserLoginJson("dima-test@yandex.ru", "123456");
    }

    public static UserLoginJson getBadDefaultLoginUser(){
        return new UserLoginJson("dima-test@yandex.ru", "654321");
    }

    public static UserEditJson getDefaultEditUser(){
        return new UserEditJson("dima123@yandex.ru");
    }
}