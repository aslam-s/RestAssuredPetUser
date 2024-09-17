package api.endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ResourceBundle;

import api.payload.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints2 {
//method created for getting URL's from properties files
	static ResourceBundle getuser() {
		ResourceBundle routes = ResourceBundle.getBundle("routes"); // load properties files
		return routes;
	}

	public static Response userpoints(UserPojo payload) {
		// get url from properties file
		String posturl = getuser().getString("post_url");
		//
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload).when()
				.post(posturl);
		return response;

	}

	public static Response readUsername(String username) {
		String gurl = getuser().getString("get_url");
		//
		Response response = given().pathParam("username", username).when().get(gurl);
		return response;

	}

	public static Response updateuser(String username, UserPojo payload) {
		String purl = getuser().getString("put_url");
		//
		Response response = given().contentType(ContentType.JSON).pathParam("username", username).body(payload).when()
				.put(purl);
		return response;

	}

	public static Response deleteUser(String username) {
		String durl = getuser().getString("delete_url");
		//
		Response response = given().pathParam("username", username).when().delete(durl);
		return response;

	}
}
