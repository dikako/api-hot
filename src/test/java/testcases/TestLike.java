package testcases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import config.Base;
import config.BaseUrl;
import function.Token;
import function.Validation;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;

public class TestLike extends Base {

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Like")
	@Test(priority = 0, testName = "Post Like")
	public void like() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.videoId());
		System.out.println(id);

		Map<String, Object> like = new HashMap<String, Object>();
		like.put("type", "like");
		like.put("video_id", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(like);

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition/likes")).contentType(ContentType.JSON)
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android")).body(like)
				.log().all().then().statusCode(200).log().all();

		validation.postMessageClientSuccess(rs);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Already Like")
	@Test(priority = 1, testName = "Post Already Like")
	public void likeAlready() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.videoId());
		System.out.println(id);

		Map<String, Object> like = new HashMap<String, Object>();
		like.put("type", "like");
		like.put("video_id", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(like);

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition/likes")).contentType(ContentType.JSON)
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android")).body(like)
				.log().all().then().statusCode(422).log().all();

		validation.postBody("status.message_client", rs, "You Can not Like for 2 times");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post UnLike")
	@Test(priority = 2, testName = "Post UnLike")
	public void unlike() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.videoId());
		System.out.println(id);

		Map<String, Object> like = new HashMap<String, Object>();
		like.put("type", "unlike");
		like.put("video_id", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(like);

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition/likes")).contentType(ContentType.JSON)
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android")).body(like)
				.log().all().then().statusCode(200).log().all();

		validation.postMessageClientSuccess(rs);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Already UnLike")
	@Test(priority = 3, testName = "Post Already UnLike")
	public void unlikeAlready() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.videoId());
		System.out.println(id);

		Map<String, Object> like = new HashMap<String, Object>();
		like.put("type", "unlike");
		like.put("video_id", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(like);

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition/likes")).contentType(ContentType.JSON)
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android")).body(like)
				.log().all().then().statusCode(200).log().all();

		validation.postMessageClientSuccess(rs);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Like Without Token")
	@Test(priority = 4, testName = "Post Like WithouT Token")
	public void likeWithoutToken() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.videoId());
		System.out.println(id);

		Map<String, Object> like = new HashMap<String, Object>();
		like.put("type", "like");
		like.put("video_id", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(like);

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition/likes")).contentType(ContentType.JSON).headers("", "").body(like)
				.log().all().then().statusCode(401).log().all();

		validation.postBody("status.message_client", rs, "U must add header Auth");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Like With Token Visitor")
	@Test(priority = 5, testName = "Post Like With Token Visitor")
	public void likeWithTokenVisitor() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.videoId());
		System.out.println(id);

		Map<String, Object> like = new HashMap<String, Object>();
		like.put("type", "like");
		like.put("video_id", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(like);

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition/likes")).contentType(ContentType.JSON)
				.headers("Authorization", token.visitor()).body(like).log().all().then().statusCode(401).log().all();

		validation.postBody("message", rs,
				"Please Sign In \n Woops! Gonna sign in first\n Only a click away and you can continue to enjoy");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post UnLike Without Token")
	@Test(priority = 6, testName = "Post UnLike WithouT Token")
	public void unlikeWithoutToken() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.videoId());
		System.out.println(id);

		Map<String, Object> like = new HashMap<String, Object>();
		like.put("type", "unlike");
		like.put("video_id", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(like);

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition/likes")).contentType(ContentType.JSON).headers("", "").body(like)
				.log().all().then().statusCode(401).log().all();

		validation.postBody("status.message_client", rs, "U must add header Auth");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post UnLike With Token Visitor")
	@Test(priority = 7, testName = "Post UnLike With Token Visitor")
	public void unlikeWithTokenVisitor() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.videoId());
		System.out.println(id);

		Map<String, Object> like = new HashMap<String, Object>();
		like.put("type", "unlike");
		like.put("video_id", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(like);

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition/likes")).contentType(ContentType.JSON)
				.headers("Authorization", token.visitor()).body(like).log().all().then().statusCode(401).log().all();

		validation.postBody("message", rs,
				"Please Sign In \n Woops! Gonna sign in first\n Only a click away and you can continue to enjoy");
	}
}