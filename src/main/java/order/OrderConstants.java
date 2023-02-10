package order;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class OrderConstants {
    public static final String URL_HOMEPAGE = "https://stellarburgers.nomoreparties.site/";
    public static final String API_ORDERS = "api/orders";

    protected RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .addHeader("Content-type", "application/json")
                .setBaseUri(URL_HOMEPAGE)
                .build();
    }
}
