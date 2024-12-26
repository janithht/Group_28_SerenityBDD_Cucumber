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
    public static void setCurrentRequest(RequestSpecification request) {
        currentRequest = request;
    }
    public static RequestSpecification getCurrentRequest() {
        if (currentRequest == null) {
            throw new IllegalStateException("Authorization context not set. Ensure you call a @Given step to authorize.");
        }
        return currentRequest;
    }
}
