import io.qameta.allure.Description;
import user.UserGenerator;
import user.UserResponse;
import user.json.UserCreateJson;
import io.qameta.allure.junit4.DisplayName;
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

    @After
    public void cleanUp(){
        if (accessToken != null){
            userResponse.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Create new user with full information. Endpoint: api/auth/register")
    @Description("Create new user with full information and check status code. Status code must be 200")
    public void createNewUserWithFullInformation(){
        userCreateJson = UserGenerator.getDefaultCreateUser();
        ValidatableResponse response = userResponse.create(userCreateJson);
        Assert.assertEquals(SC_OK, response.extract().statusCode());

        this.accessToken = response.extract().path("accessToken");
    }

    @Test
    @DisplayName("Double create once user. Endpoint: api/auth/register")
    @Description("Create user twice. When we create him second time, we will get 403 status code")
    public void doubleCreateUser(){
        userCreateJson = UserGenerator.getDefaultCreateUser();
        ValidatableResponse response = userResponse.create(userCreateJson);
        ValidatableResponse response1 = userResponse.create(userCreateJson);
        Assert.assertEquals(SC_FORBIDDEN, response1.extract().statusCode());

        this.accessToken = response.extract().path("accessToken");
    }

    @Test
    @DisplayName("Create new user with not full information. Endpoint: api/auth/register")
    @Description("Create new user with not full information and check status code. Status code must be 403")
    public void  createUserWithNotFullInformation(){
        userCreateJson = UserGenerator.getBadDefaultCreateUser();
        ValidatableResponse response = userResponse.create(userCreateJson);
        Assert.assertEquals(SC_FORBIDDEN, response.extract().statusCode());
    }

}