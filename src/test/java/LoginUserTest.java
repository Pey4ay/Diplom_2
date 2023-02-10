import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import user.UserGenerator;
import user.UserResponse;
import user.json.UserCreateJson;
import user.json.UserLoginJson;
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

    @Before
    public void setUp(){
        userResponse = new UserResponse();
        userCreateJson = UserGenerator.getDefaultCreateUser();
        userResponse.create(userCreateJson);
    }

    @After
    public void cleanUp(){
        userResponse.delete(accessToken);
    }

    @Test
    @DisplayName("Authorization with created user. Endpoint: api/auth/login")
    @Description("Authorization with created user and check status code. Status code must be 200")
    public void loginWithCreatedUser(){
        userLoginJson = UserGenerator.getDefaultLoginUser();
        ValidatableResponse response = userResponse.login(userLoginJson);
        Assert.assertEquals(SC_OK, response.extract().statusCode());
        this.accessToken = response.extract().path("accessToken");
    }

    @Test
    @DisplayName("Authorization with incorrect information. Endpoint: api/auth/login")
    @Description("Authorization with incorrect login and password. Check status code. Status code must be 401")
    public void loginWithCreatedUserAndBadPassword(){
        userLoginJson = UserGenerator.getBadDefaultLoginUser();
        ValidatableResponse badResponse = userResponse.login(userLoginJson);
        Assert.assertEquals(SC_UNAUTHORIZED, badResponse.extract().statusCode());
        ValidatableResponse response = userResponse.login(UserGenerator.getDefaultLoginUser());
        this.accessToken = response.extract().path("accessToken");
    }
}
