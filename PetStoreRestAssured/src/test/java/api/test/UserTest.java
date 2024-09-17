package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.UserPojo;
import io.restassured.response.Response;

public class UserTest {
	Faker f;
	UserPojo userpayload;
	public Logger logger;

	@BeforeClass
	void setupdata() {
		f = new Faker();
		userpayload = new UserPojo();
		userpayload.setId(f.idNumber().hashCode());
		userpayload.setUsername(f.name().username());
		userpayload.setFirstName(f.name().firstName());
		userpayload.setLastName(f.name().lastName());
		userpayload.setEmail(f.internet().emailAddress());
		userpayload.setPassword(f.internet().password());
		userpayload.setPhone(f.phoneNumber().cellPhone());

		// logs
		logger = LogManager.getLogger(this.getClass());
	}

	@Test(priority = 1)
	void testPostuser() {
		logger.info("************** creating user ********");
		Response rescreate = UserEndPoints.userpoints(userpayload);
		rescreate.then().log().all();
		Assert.assertEquals(rescreate.getStatusCode(), 200);
		logger.info("************** created user ********");
	}

	@Test(priority = 2)
	void testgetusername() {
		logger.info("************** Reading  user info ********");
		Response resget = UserEndPoints.readUsername(this.userpayload.getUsername());
		resget.then().log().all();
		Assert.assertEquals(resget.getStatusCode(), 200);
		logger.info("************** Reading  user info displayed********");
	}

	@Test(priority = 3)
	void testPutuser() {
		logger.info("************** updating  user info ********");
		// update data using payload
		userpayload.setFirstName(f.name().firstName());
		userpayload.setLastName(f.name().lastName());
		userpayload.setEmail(f.internet().emailAddress());
		//
		Response resput = UserEndPoints.updateuser(this.userpayload.getUsername(), userpayload);
		resput.then().log().all();
		Assert.assertEquals(resput.getStatusCode(), 200);
		logger.info("************** updated  user info ********");
		logger.info("************** Reading  user info ********");
		// check data after update same getmethod use here
		Response resgetaf = UserEndPoints.readUsername(this.userpayload.getUsername());
		resgetaf.then().log().all();
		logger.info("************** Reading  user info displayed********");
		// Assert.assertEquals(resgetaf.getStatusCode(), 200);

	}

	@Test(priority = 4)
	void testdeleteusername() {
		logger.info("************** deleting  user info ********");
		Response resdel = UserEndPoints.deleteUser(this.userpayload.getUsername());
		resdel.then().log().all();
		// .then().log().body().statuscode(204);
		// Assert.assertEquals(resdel.getStatusCode(), 204);
		logger.info("************** deleted  user info ********");

	}

}
