package user;

import user.json.UserCreateJson;
import user.json.UserEditJson;
import user.UserGenerator;
import user.UserResponse;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class ChangeInformationTest {
    private UserResponse userResponse;
    private UserCreateJson userCreateJson;
    private UserEditJson userEditJson;
    private String accessToken;

    @Before
    public void setUp(){
        userResponse = new UserResponse();
        userCreateJson = UserGenerator.getDefaultCreateUser();
        ValidatableResponse response = userResponse.create(userCreateJson);
        this.accessToken = response.extract().path("accessToken");
    }

    @After
    public void cleanUp(){
        userResponse.delete(accessToken);
    }

    //Тест на изменение данных с авторизацией
    @Test
    public void changeUserInformationWithAuthorization(){
        userEditJson = UserGenerator.getDefaultEditUser();
        ValidatableResponse response = userResponse.editUserInformation(userEditJson, accessToken);
        Assert.assertEquals(SC_OK, response.extract().statusCode());
    }

    //Тест на изменение данных без авторизации
    @Test
    public void changeUserInformationWithNotAuthorization(){
        userEditJson = UserGenerator.getDefaultEditUser();
        ValidatableResponse response= userResponse.editUserWithNotAuthorization(userEditJson);
        Assert.assertEquals(SC_UNAUTHORIZED, response.extract().statusCode());
    }
}
