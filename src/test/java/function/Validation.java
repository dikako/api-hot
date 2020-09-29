package function;

import org.testng.Assert;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Validation {

	public void GetNotNull(String path, RequestSpecification rs) {
		Response res = rs.get();
		Assert.assertNotNull(res.getBody().path(path));
		System.out.println("Response Body: " + res.getBody().path(path));
	}

	public void PostNotNull(String path, RequestSpecification rs) {
		Response res = rs.post();
		Assert.assertNotNull(res.getBody().path(path));
		System.out.println("Response Body: " + res.getBody().path(path));
	}

	public void GetNotNullOr(String path, String path2, RequestSpecification rs) {
		Response res = rs.get();
		Assert.assertTrue(res.getBody().path(path) != null || res.getBody().path(path2) != null);
		System.out.println("Response Body: " + res.getBody().path(path));
	}

	public void getBody(String path, RequestSpecification rs, String body) {
		Response res = rs.get();
		Assert.assertEquals(
				res.getBody().path(path).toString().replace(" ", "").toLowerCase().replace("[", "").replace("]", ""),
				body.replace(" ", "").toLowerCase().replace("[", "").replace("]", ""));
		System.out.println("Response Body: " + res.getBody().path(path).toString());
	}

	public void getBodyContains(String path, RequestSpecification rs, String body) {
		Response res = rs.get();
		res.getBody().path(path).toString().replace(" ", "").toLowerCase().replace("[", "").replace("]", "")
				.contains(body.replace(" ", "").toLowerCase().replace("[", "").replace("]", ""));
		System.out.println("Response Body: " + res.getBody().path(path).toString());
	}

	public void getBodyContainsOr(String path, String path2, RequestSpecification rs, String body, String body2) {
		Response res = rs.get();

		Assert.assertTrue(res.getBody().path(path).toString().replace(" ", "").toLowerCase().replace("[", "")
				.replace("]", "") == body.replace(" ", "").toLowerCase().replace("[", "").replace("]", "")
				|| res.getBody().path(path2).toString().replace(" ", "").toLowerCase().replace("[", "").replace("]",
						"") == body2.replace(" ", "").toLowerCase().replace("[", "").replace("]", ""));
		System.out.println("Response Body: " + res.getBody().path(path).toString());
		System.out.println("Response Body: " + res.getBody().path(path2).toString());
	}

	public String getBodyReturn(String path, RequestSpecification rs) {
		Response res = rs.get();
		return res.getBody().path(path).toString();
	}

	public void postBody(String path, RequestSpecification rs, String body) {
		Response res = rs.post();
		Assert.assertEquals(res.getBody().path(path).toString().replace(" ", "").toLowerCase(),
				body.replace(" ", "").toLowerCase());
		System.out.println("Response Body: " + res.getBody().path(path).toString());
	}

	public void getMessageClientSuccess(RequestSpecification rs) {
		Response res = rs.get();
		Assert.assertEquals(res.getBody().path("status.message_client").toString().replace(" ", "").toLowerCase(),
				"Success".replace(" ", "").toLowerCase());
		System.out.println("Response Body: " + res.getBody().path("status.message_client").toString());
	}

	public void postMessageClientSuccess(RequestSpecification rs) {
		Response res = rs.post();
		Assert.assertEquals(res.getBody().path("status.message_client").toString().replace(" ", "").toLowerCase(),
				"Success".replace(" ", "").toLowerCase());
		System.out.println("Response Body: " + res.getBody().path("status.message_client").toString());
	}

	public void getMessageServerSuccess(RequestSpecification rs) {
		Response res = rs.get();
		Assert.assertEquals(res.getBody().path("status.message_server").toString().replace(" ", "").toLowerCase(),
				"Success".replace(" ", "").toLowerCase());
		System.out.println("Response Body: " + res.getBody().path("status.message_server").toString());
	}

	public void postMessageServerSuccess(RequestSpecification rs) {
		Response res = rs.post();
		Assert.assertEquals(res.getBody().path("status.message_server").toString().replace(" ", "").toLowerCase(),
				"Success".replace(" ", "").toLowerCase());
		System.out.println("Response Body: " + res.getBody().path("status.message_server").toString());
	}

	public void GetBodyList(String path, RequestSpecification rs, String responseBody) {
		Response res = rs.get();
		Assert.assertEquals(res.getBody().path(path).toString().replace(" ", "").toLowerCase(),
				responseBody.toString().replace(" ", "").toLowerCase());
		System.out.println("Response Body: " + res.getBody().path(path));
	}

	public String returnGetBody(RequestSpecification rs, String path) {
		Response res = rs.get();
		String body = res.getBody().path(path).toString();
		return body;
	}
}