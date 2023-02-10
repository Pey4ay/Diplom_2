import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import user.UserGenerator;
import user.UserResponse;
import user.json.UserCreateJson;

import static org.apache.http.HttpStatus.*;

public class OrderCreateTest {
    private UserResponse userResponse;
    private UserCreateJson userCreateJson;
    private OrderResponse orderResponse;
    private String accessToken;
    private String ingredients = "{\n" + "\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\",\"61c0c5a71d1f82001bdaaa6f\"]\n" + "}\n";
    private String withoutIngredient = "{\"ingredients\": []}";
    private String incorrectIngredient = "{\n" + "\"ingredients\": [\"incor5a7100082001incor6d\",\"incor5a71d1f820011000a6f\"]\n" + "}\n";

    @Before
    public void setUp(){
        userResponse = new UserResponse();
        orderResponse = new OrderResponse();
        userCreateJson = UserGenerator.getDefaultCreateUser();
        ValidatableResponse response = userResponse.create(userCreateJson);
        this.accessToken = response.extract().path("accessToken");
    }

    @After
    public void cleanUp(){
        if(accessToken != null){
            userResponse.delete(accessToken);
        }
    }

    //Создание заказа с авторизацией
    @Test
    @DisplayName("Create order with authorization user. Endpoint: api/orders")
    @Description("Create order with authorization user and check status code. Status code must be 200")
    public void createOrderWithAuth(){
        ValidatableResponse response = orderResponse.createWithAuth(ingredients, accessToken);
        Assert.assertEquals(SC_OK, response.extract().statusCode());
    }

    //Создание заказа без авторизации
    @Test
    @DisplayName("Create order with no authorization user. Endpoint: api/orders")
    @Description("Create order with no authorization user and check status code. Status code must be 200")
    public void creteOrderWithNoAuth(){
        ValidatableResponse response = orderResponse.createWithNotAuth(ingredients);
        Assert.assertEquals(SC_OK, response.extract().statusCode());
    }

    //Создание заказа с ингредиентами
    @Test
    @DisplayName("Create order with full ingredients. Endpoint: api/orders")
    @Description("Create order with full ingredients and check status code. Status code must be 200")
    public void createOrderWithFullIngredients(){
        ValidatableResponse response = orderResponse.createWithAuth(ingredients, accessToken);
        Assert.assertEquals(SC_OK, response.extract().statusCode());
    }

    //Создание заказа без ингредиентов
    @Test
    @DisplayName("Create order with without ingredients. Endpoint: api/orders")
    @Description("Create order with without ingredients and check status code. Status code must be 400")
    public void createOrderWithNullIngredients(){
        ValidatableResponse response = orderResponse.createWithAuth(withoutIngredient, accessToken);
        Assert.assertEquals(SC_BAD_REQUEST, response.extract().statusCode());
    }

    //Создание заказа с неверным хешем ингредиентов
    @Test
    @DisplayName("Create order with incorrect ingredients. Endpoint: api/orders")
    @Description("Create order with incorrect ingredients and check status code. Status code must be 500")
    public void createOrderWithInCorrectIngredients(){
        ValidatableResponse response = orderResponse.createWithAuth(incorrectIngredient, accessToken);
        Assert.assertEquals(SC_INTERNAL_SERVER_ERROR, response.extract().statusCode());
    }

    //Получение заказов авторизованного пользователя
    @Test
    @DisplayName("Get user order with authorization. Endpoint: api/orders")
    @Description("Get user order with authorization and and check status code. Status code must be 200")
    public void getAllOrdersWithAuth(){
        ValidatableResponse response = orderResponse.createWithAuth(ingredients, accessToken);
        ValidatableResponse response1 = orderResponse.getOrdersWithAuth(accessToken);
        Assert.assertEquals(SC_OK, response1.extract().statusCode());
    }

    //Получение заказов неавторизованного пользователя
    @Test
    @DisplayName("Get user order with no authorization. Endpoint: api/orders")
    @Description("Get user order with no authorization and and check status code. Status code must be 401")
    public void getAllOrdersWithNoAuth(){
        ValidatableResponse response = orderResponse.createWithAuth(ingredients, accessToken);
        ValidatableResponse response1 = orderResponse.getOrdersWithNoAuth();
        Assert.assertEquals(SC_UNAUTHORIZED, response1.extract().statusCode());
    }

}