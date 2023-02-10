import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
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

    @Test
    @DisplayName("Edit information with authorization user. Endpoint: api/auth/user")
    @Description("Edit email user and check status code. Status code must be 200")
    public void changeUserInformationWithAuthorization(){
        userEditJson = UserGenerator.getDefaultEditUser();
        ValidatableResponse response = userResponse.editUserInformation(userEditJson, accessToken);
        Assert.assertEquals(SC_OK, response.extract().statusCode());
    }

    @Test
    @DisplayName("Edit information with no authorization user. Endpoint: api/auth/user")
    @Description("Edit email and check status code. Status code must be 401")
    public void changeUserInformationWithNotAuthorization(){
        userEditJson = UserGenerator.getDefaultEditUser();
        ValidatableResponse response= userResponse.editUserWithNotAuthorization(userEditJson);
        Assert.assertEquals(SC_UNAUTHORIZED, response.extract().statusCode());
    }
}
