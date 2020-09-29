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
import io.restassured.response.Response;

public class TestViews extends Base {

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post View Test")
	@Test(priority = 0, testName = "Post View Test")
	public void viewWithLogin() {
		BaseUrl baseUrl = new BaseUrl();
		Token token = new Token();

		Integer videoId = Integer.valueOf(token.videoId());

		Map<String, Object> view = new HashMap<String, Object>();
		view.put("video_id", videoId);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(view);

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition/views")).contentType(ContentType.JSON)
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android")).body(view)
				.log().all().then().log().all().statusCode(200).log().all();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get View Test")
	@Test(priority = 1, testName = "Get View Test")
	public void getViewWithLogin() {
		BaseUrl baseUrl = new BaseUrl();
		Token token = new Token();
		Validation validation = new Validation();

		Integer videoId = Integer.valueOf(token.videoId());

		rs.baseUri(baseUrl.urlUgcVote("/v1/play")).param("video_id", videoId)
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "647464", "android")).log()
				.all().when().get().then().log().all().statusCode(200).log().all();

		validation.getMessageClientSuccess(rs);
		validation.GetNotNull("data.views", rs);
	}

	public String returnView() {
		BaseUrl baseUrl = new BaseUrl();
		Token token = new Token();
		Validation validation = new Validation();

		Integer videoId = Integer.valueOf(token.videoId());

		rs.baseUri(baseUrl.urlUgcVote("/v1/play")).param("video_id", videoId)
				.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "647464", "android")).log()
				.all().when().get().then().log().all().statusCode(200).log().all();

		Response res = rs.get();

		String a = res.getBody().path("data.views");
		System.out.println("xxxxxxxxxxxxxxxxxxxxx" + a);
		validation.getMessageClientSuccess(rs);
		return a;
	}

}