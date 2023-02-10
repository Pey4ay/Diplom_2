package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderResponse extends OrderConstants {

    @Step("Create order with authorization user")
    public ValidatableResponse createWithAuth(String ingredients, String accessToken){
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .body(ingredients)
                .when()
                .post(API_ORDERS)
                .then();
    }

    @Step("Create order with no authorization user")
    public ValidatableResponse createWithNotAuth(String ingredients){
        return given()
                .spec(getSpec())
                .body(ingredients)
                .when()
                .post(API_ORDERS)
                .then();
    }

    @Step("Get order with authorization user")
    public  ValidatableResponse getOrdersWithAuth(String accessToken){
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .when()
                .get(API_ORDERS)
                .then();
    }

    @Step("Get order with no authorization user")
    public ValidatableResponse getOrdersWithNoAuth(){
        return given()
                .spec(getSpec())
                .when()
                .get(API_ORDERS)
                .then();
    }
}
