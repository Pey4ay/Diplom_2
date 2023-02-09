import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Constants {
    public static final String URL_HOMEPAGE = "https://stellarburgers.nomoreparties.site/";
    public static final String API_CREATE_USER = "api/auth/register";
    public static final String API_DELETE_USER = "api/auth/user";
    public static final String API_LOGIN_USER = "api/auth/login";

    protected RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .addHeader("Content-type", "application/json")
                .setBaseUri(URL_HOMEPAGE)
                .build();
    }
}
