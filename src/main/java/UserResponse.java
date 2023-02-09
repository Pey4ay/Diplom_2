import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class UserResponse extends Constants{

    public ValidatableResponse create (UserCreateJson userCreateJson){
        return given()
                .spec(getSpec())
                .body(userCreateJson)
                .when()
                .post(API_CREATE_USER)
                .then();
    }

    public ValidatableResponse delete (String accessToken){
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .when()
                .delete(API_DELETE_USER)
                .then();
    }

    public ValidatableResponse login (UserLoginJson userLoginJson){
        return given()
                .spec(getSpec())
                .body(userLoginJson)
                .when()
                .post(API_LOGIN_USER)
                .then();
    }
}
