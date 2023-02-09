package order;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderResponse extends OrderConstants {
    public ValidatableResponse createWithAuth(String ingredients, String accessToken){
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .body(ingredients)
                .when()
                .post(API_ORDERS)
                .then();
    }
    public ValidatableResponse createWithNotAuth(String ingredients){
        return given()
                .spec(getSpec())
                .body(ingredients)
                .when()
                .post(API_ORDERS)
                .then();
    }

    public  ValidatableResponse getOrdersWithAuth(String accessToken){
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .when()
                .get(API_ORDERS)
                .then();
    }

    public ValidatableResponse getOrdersWithNoAuth(){
        return given()
                .spec(getSpec())
                .when()
                .get(API_ORDERS)
                .then();
    }
}
