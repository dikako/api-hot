package testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import config.Base;
import config.BaseUrl;
import function.Token;
import function.Validation;
import utility.ReadExcel;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;

public class TestFollow extends Base {

	public String path = "../api-hot/src/test/java/datatest/data.xlsx";

	@DataProvider
	public String[][] userLogin() throws InvalidFormatException, IOException {
		ReadExcel readExcel = new ReadExcel();
		return readExcel.getCellData(path, "UserLogin");
	}

	@DataProvider
	public String[][] UserFollowUnfollow() throws InvalidFormatException, IOException {
		ReadExcel readExcel = new ReadExcel();
		return readExcel.getCellData(path, "UserFollowUnfollow");
	}

	@DataProvider
	public String[][] TargetUserFollowUnfollow() throws InvalidFormatException, IOException {
		ReadExcel readExcel = new ReadExcel();
		return readExcel.getCellData(path, "TargetUserFollowUnfollow");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Before Follow Status")
	@Test(priority = 0, testName = "Before Follow Status", dataProvider = "TargetUserFollowUnfollow")
	public void beforeFollowStatus(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		String id = token.id(username, password, "1234", "android");

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/user-profile")).param("user_id", id)
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android")).log()
				.all().when().get().then().statusCode(200).log().all();

		validation.getMessageClientSuccess(rs);
		validation.GetNotNull("data.role", rs);
		validation.GetNotNull("data.user_id", rs);
		validation.GetNotNull("data.following", rs);
		validation.getBody("data.following", rs, "false");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Follow")
	@Test(priority = 1, testName = "Post Follow", dataProvider = "TargetUserFollowUnfollow")
	public void follow(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.id(username, password, "1234", "android"));

		Map<String, Object> follow = new HashMap<String, Object>();
		follow.put("follow_to", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(follow);

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/follow")).contentType(ContentType.JSON)
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android"))
				.body(follow).log().all().then().statusCode(201).log().all();

		validation.postMessageClientSuccess(rs);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Before Follow Status")
	@Test(priority = 2, testName = "Before Follow Status", dataProvider = "TargetUserFollowUnfollow")
	public void afterFollowStatus(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		String id = token.id(username, password, "1234", "android");

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/user-profile")).param("user_id", id)
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android")).log()
				.all().when().get().then().statusCode(200).log().all();

		validation.getMessageClientSuccess(rs);
		validation.GetNotNull("data.role", rs);
		validation.GetNotNull("data.user_id", rs);
		validation.GetNotNull("data.following", rs);
		validation.getBody("data.following", rs, "true");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Already Follow")
	@Test(priority = 3, testName = "Post Already Follow", dataProvider = "TargetUserFollowUnfollow")
	public void alreadyFollow(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.id(username, password, "1234", "android"));

		Map<String, Object> follow = new HashMap<String, Object>();
		follow.put("follow_to", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(follow);

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/follow")).contentType(ContentType.JSON)
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android"))
				.body(follow).log().all().then().statusCode(422);

		validation.postBody("status.message_client", rs, "You followed it already");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Following")
	@Test(priority = 4, testName = "Get Following", dataProvider = "TargetUserFollowUnfollow")
	public void following(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/following"))
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android")).when()
				.get().then().statusCode(200).log().all();

		String id = token.id(username, password, "1234", "android");

		validation.getBodyContains("data.user_id", rs, id);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Follower")
	@Test(priority = 5, testName = "Get Follower", dataProvider = "TargetUserFollowUnfollow")
	public void follower(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/my-followers"))
				.headers("Authorization", token.login(username, password, "1234", "android")).log().all().when().get()
				.then().log().all().statusCode(200).log().all();

		String id = token.id("dikakoko04@gmail.com", "dikakoko", "1234", "android");

		validation.getBodyContains("data.user_id", rs, id);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Follower Other User With Token Login User")
	@Test(priority = 5, testName = "Get Follower Other User With Token Login User", dataProvider = "TargetUserFollowUnfollow")
	public void followerTokenUser(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		String id = token.id(username, password, "1234", "android");

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/user-followers"))
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android"))
				.param("user_id", id).log().all().when().get().then().log().all().statusCode(200);

		String idTokenUsr = token.id("dikakoko04@gmail.com", "dikakoko", "1234", "android");

		validation.getBodyContains("data.user_id", rs, idTokenUsr);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Follower Other User with Visitor Token")
	@Test(priority = 5, testName = "Get Follower Other User with Visitor Token", dataProvider = "TargetUserFollowUnfollow")
	public void followerTokenVisitor(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		String id = token.id(username, password, "1234", "android");

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/user-followers")).headers("Authorization", token.visitor())
				.param("user_id", id).log().all().when().get().then().log().all().statusCode(200);

		String idTokenUsr = token.id("dikakoko04@gmail.com", "dikakoko", "1234", "android");

		validation.getBodyContains("data.user_id", rs, idTokenUsr);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post UnFollow")
	@Test(priority = 6, testName = "Post UnFollow", dataProvider = "TargetUserFollowUnfollow")
	public void Unfollow(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.id(username, password, "1234", "android"));

		Map<String, Object> unFollow = new HashMap<String, Object>();
		unFollow.put("follow_to", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(unFollow);

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/unfollow")).contentType(ContentType.JSON)
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android"))
				.body(unFollow).log().all().then().statusCode(200).log().all();

		validation.postMessageClientSuccess(rs);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Already UnFollow")
	@Test(priority = 7, testName = "Post Already UnFollow", dataProvider = "TargetUserFollowUnfollow")
	public void alreadyUnfollow(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.id(username, password, "1234", "android"));

		Map<String, Object> unFollow = new HashMap<String, Object>();
		unFollow.put("follow_to", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(unFollow);

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/unfollow")).contentType(ContentType.JSON)
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android"))
				.body(unFollow).log().all().then().statusCode(404).log().all();

		validation.postBody("status.message_client", rs, "You do not follow this user");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post FollowSelf")
	@Test(priority = 8, testName = "Post FollowSelf", dataProvider = "TargetUserFollowUnfollow")
	public void followSelf(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.id(username, password, "1234", "android"));

		Map<String, Object> follow = new HashMap<String, Object>();
		follow.put("follow_to", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(follow);

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/follow")).contentType(ContentType.JSON)
				.headers("Authorization", token.login(username, password, "1234", "android")).body(follow).log().all();

		validation.postBody("status.message_client", rs, "You can't follow your self!!");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Follow Without Token")
	@Test(priority = 9, testName = "Post Follow Without Token", dataProvider = "TargetUserFollowUnfollow")
	public void followWithoutToken(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.id(username, password, "1234", "android"));

		Map<String, Object> follow = new HashMap<String, Object>();
		follow.put("follow_to", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(follow);

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/follow")).contentType(ContentType.JSON).headers("", "").body(follow)
				.log().all().then().statusCode(401).log().all();

		validation.postBody("status.message_client", rs, "U must add header Auth");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Follow With Visitor Token")
	@Test(priority = 10, testName = "Post Follow With Visitor Token", dataProvider = "TargetUserFollowUnfollow")
	public void followWithVisitorToken(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.id(username, password, "1234", "android"));

		Map<String, Object> follow = new HashMap<String, Object>();
		follow.put("follow_to", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(follow);

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/follow")).contentType(ContentType.JSON)
				.headers("Authorization", token.visitor()).body(follow).log().all().then().statusCode(401).log().all();

		validation.postBody("message", rs,
				"Please Sign In \n Woops! Gonna sign in first\n Only a click away and you can continue to enjoy");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post UNFollow Without Token")
	@Test(priority = 11, testName = "Post UNFollow Without Token", dataProvider = "TargetUserFollowUnfollow")
	public void unfollowWithoutToken(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.id(username, password, "1234", "android"));

		Map<String, Object> follow = new HashMap<String, Object>();
		follow.put("follow_to", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(follow);

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/follow")).contentType(ContentType.JSON).headers("", "").body(follow)
				.log().all().then().statusCode(401).log().all();

		validation.postBody("status.message_client", rs, "U must add header Auth");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post UnFollow With Visitor Token")
	@Test(priority = 12, testName = "Post UnFollow With Visitor Token", dataProvider = "TargetUserFollowUnfollow")
	public void unfollowWithVisitorToken(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		Integer id = Integer.valueOf(token.id(username, password, "1234", "android"));

		Map<String, Object> follow = new HashMap<String, Object>();
		follow.put("follow_to", id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(follow);

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/unfollow")).contentType(ContentType.JSON)
				.headers("Authorization", token.visitor()).body(follow).log().all().then().statusCode(401).log().all();

		validation.postBody("message", rs,
				"Please Sign In \n Woops! Gonna sign in first\n Only a click away and you can continue to enjoy");
	}
}