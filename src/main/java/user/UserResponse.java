package user;

import io.qameta.allure.Step;
import user.json.UserCreateJson;
import user.json.UserEditJson;
import user.json.UserLoginJson;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class UserResponse extends UserConstants {

    @Step("Create user")
    public ValidatableResponse create (UserCreateJson userCreateJson){
        return given()
                .spec(getSpec())
                .body(userCreateJson)
                .when()
                .post(UserConstants.API_CREATE_USER)
                .then();
    }

    @Step("Delete user")
    public ValidatableResponse delete (String accessToken){
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .when()
                .delete(UserConstants.API_CHANGE_USER)
                .then();
    }

    @Step("Login user")
    public ValidatableResponse login (UserLoginJson userLoginJson){
        return given()
                .spec(getSpec())
                .body(userLoginJson)
                .when()
                .post(UserConstants.API_LOGIN_USER)
                .then();
    }

    @Step("Edit user information with authorization")
    public ValidatableResponse editUserInformation(UserEditJson userEditJson, String accessToken){
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .body(userEditJson)
                .when()
                .patch(UserConstants.API_CHANGE_USER)
                .then();
    }

    @Step("Edit user information with no authorization")
    public ValidatableResponse editUserWithNotAuthorization(UserEditJson userEditJson){
        return given()
                .spec(getSpec())
                .body(userEditJson)
                .when()
                .patch(UserConstants.API_CHANGE_USER)
                .then();
    }
}
