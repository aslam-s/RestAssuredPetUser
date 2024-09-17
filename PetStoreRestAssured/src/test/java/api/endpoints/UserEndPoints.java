package api.endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import api.payload.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints {
	public static Response userpoints(UserPojo payload) {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload).when()
				.post(RoutesURLs.post_url);
		return response;

	}

	public static Response readUsername(String username) {
		Response response = given().pathParam("username", username).when().get(RoutesURLs.get_url);
		return response;

	}

	public static Response updateuser(String username, UserPojo payload) {
		Response response = given().contentType(ContentType.JSON).pathParam("username", username).body(payload).when()
				.put(RoutesURLs.put_url);
		return response;

	}

	public static Response deleteUser(String username) {
		Response response = given().pathParam("username", username).when().delete(RoutesURLs.delete_url);
		return response;

	}
}
