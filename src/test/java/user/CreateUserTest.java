package user;

import user.json.UserCreateJson;
import user.UserGenerator;
import user.UserResponse;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;


public class CreateUserTest {
    private UserResponse userResponse;
    private UserCreateJson userCreateJson;
    private String accessToken;


    @Before
    public void setUp(){
        userResponse = new UserResponse();
    }

    //Удаление пользователя с проверкой. Если пользователь не создан, то и запрос не будет отправлен.
    @After
    public void cleanUp(){
        if (accessToken != null){
            userResponse.delete(accessToken);
        }
    }

    //Создание пользователя с полным набором данных
    @Test
    public void createNewUserWithFullInformation(){
        userCreateJson = UserGenerator.getDefaultCreateUser();
        ValidatableResponse response = userResponse.create(userCreateJson);
        Assert.assertEquals(SC_OK, response.extract().statusCode());

        this.accessToken = response.extract().path("accessToken");
    }

    //Создание пользователя с даннымми уже зарегистрированного пользователя
    @Test
    public void doubleCreateUser(){
        userCreateJson = UserGenerator.getDefaultCreateUser();
        ValidatableResponse response = userResponse.create(userCreateJson);
        ValidatableResponse response1 = userResponse.create(userCreateJson);
        Assert.assertEquals(SC_FORBIDDEN, response1.extract().statusCode());

        this.accessToken = response.extract().path("accessToken");
    }

    //Создание пользователя без одного обязательного поля
    @Test
    public void  createUserWithNotFullInformation(){
        userCreateJson = UserGenerator.getBadDefaultCreateUser();
        ValidatableResponse response = userResponse.create(userCreateJson);
        Assert.assertEquals(SC_FORBIDDEN, response.extract().statusCode());
    }

}