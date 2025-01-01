package Steps.factory;

import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

//Factory Design Pattern
public class RequestFactory {

    private static RequestSpecification currentRequest;

    public static RequestSpecification adminRequest() {
        return SerenityRest.given()
                .auth()
                .preemptive()
                .basic("admin", "password")
                .contentType("application/json");
    }

    public static RequestSpecification userRequest() {
        return SerenityRest.given()
                .auth()
                .preemptive()
                .basic("user", "password")
                .contentType("application/json");
    }
    public static RequestSpecification adminGetRequest() {
        return SerenityRest.given()
                .auth()
                .preemptive()
                .basic("admin", "password");
    }

    public static RequestSpecification userGetRequest() {
        return SerenityRest.given()
                .auth()
                .preemptive()
                .basic("user", "password");
    }

    public static void setCurrentRequest(RequestSpecification request) {
        currentRequest = request;
    }
    public static RequestSpecification getCurrentRequest() {
        if (currentRequest == null) {
            throw new IllegalStateException("Authorization context not set. Ensure you call a @Given step to authorize.");
        }
        return currentRequest;
    }

    public static RequestSpecification adminDeleteRequest() {
        return SerenityRest.given()
                .auth()
                .preemptive()
                .basic("admin", "password");
    }

    public static RequestSpecification invalidAdminDeleteRequest() {
        return SerenityRest.given()
                .auth()
                .preemptive()
                .basic("admin", "wrong-password");
    }

    public static RequestSpecification userDeleteRequest() {
        return SerenityRest.given()
                .auth()
                .preemptive()
                .basic("user", "password");
    }
}
