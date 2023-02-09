import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class LoginUserTest {
    private UserResponse userResponse;
    private UserCreateJson userCreateJson;
    private UserLoginJson userLoginJson;
    private String accessToken;


    //Создание пользователем перед тестом, так как это необходимое условие для авторизации.
    @Before
    public void setUp(){
        userResponse = new UserResponse();
        userCreateJson = UserGenerator.getDefaultCreateUser();
        userResponse.create(userCreateJson);

    }

    //Удаление пользователя с проверкой. Если пользователь не создан, то и запрос не будет отправлен.
    @After
    public void cleanUp(){
        if (accessToken != null){
            userResponse.delete(accessToken);
        }
    }

    //Логин под существующим пользователем
    @Test
    public void loginWithCreatedUser(){
        userLoginJson = UserGenerator.getDefaultLoginUser();
        ValidatableResponse response = userResponse.login(userLoginJson);
        Assert.assertEquals(SC_OK, response.extract().statusCode());

        this.accessToken = response.extract().path("accessToken");
    }

    //Логин с неверным логином и паролем
    @Test
    public void loginWithCreatedUserAndBadPassword(){
        userLoginJson = UserGenerator.getBadDefaultLoginUser();
        ValidatableResponse badResponse = userResponse.login(userLoginJson);
        Assert.assertEquals(SC_UNAUTHORIZED, badResponse.extract().statusCode());

        ValidatableResponse response = userResponse.login(UserGenerator.getDefaultLoginUser());
        this.accessToken = response.extract().path("accessToken");
    }
}
