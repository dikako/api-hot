package function;

import org.testng.Assert;
import io.restassured.response.Response;

public class StatusCode {
	public void zoo(Response response) {
		Assert.assertEquals(response.statusCode(), 200);
	}
}