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

public class TestComment extends Base {

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Comment")
	@Test(priority = 0, testName = "Post Comment")
	public void postComment() {
		BaseUrl baseUrl = new BaseUrl();
		Token token = new Token();
		Validation validation = new Validation();

		Integer vid_id = Integer.valueOf(token.videoId());

		Map<String, Object> comment = new HashMap<String, Object>();
		comment.put("video_id", vid_id);
		comment.put("message", "Hay Cihuy Disini");

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(comment);

		rs.baseUri(baseUrl.urlComment("/v1/comments/user")).contentType(ContentType.JSON)
				.headers("Authorization", token.login("6282278843303", "dikakoko", "647464", "android")).body(comment)
				.log().all().then().log().all().statusCode(201).log().all();
		validation.postMessageClientSuccess(rs);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Comment")
	@Test(priority = 1, testName = "Get Comment")
	public void getComment() {

		BaseUrl baseUrl = new BaseUrl();
		Token token = new Token();
		Validation validation = new Validation();

		rs.baseUri(baseUrl.urlComment("/v1/comments/user")).param("video_id", token.videoId())
				.headers("Authorization", token.login("6282278843303", "dikakoko", "647464", "android")).log().all()
				.when().get().then().log().all().statusCode(200).log().all();

		validation.getMessageClientSuccess(rs);
		validation.getBody("data.comments[0].nickname", rs, "dikah");
		validation.getBody("data.comments[0].message", rs, "Hay Cihuy Disini");
	}

}