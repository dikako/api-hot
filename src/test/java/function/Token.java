package function;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import config.BaseUrl;
import org.testng.annotations.DataProvider;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utility.ReadExcel;

public class Token {
	public String pathDataTest = "../datatest/data.xlsx";

	@DataProvider
	public String[][] userLogin() throws InvalidFormatException, IOException {
		ReadExcel readExcel = new ReadExcel();
		return readExcel.getCellData(pathDataTest, "UserLogin");
	}

	public String visitor() {
		BaseUrl baseUrl = new BaseUrl();
		StatusCode statusCode = new StatusCode();
		RestAssured.baseURI = baseUrl.urlCore("");
		Response response = null;
		try {
			response = RestAssured.given().when().get("/v1/visitor?platform=android&device_id=0089821");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		statusCode.zoo(response);
		return response.body().path("data.access_token").toString();
	}

	public String login(String username, String password, String deviceId, String platform) {
		BaseUrl baseUrl = new BaseUrl();
		RequestSpecification rs = RestAssured.given();

		Map<String, Object> login = new HashMap<String, Object>();
		login.put("username", username);
		login.put("password", password);
		login.put("device_id", deviceId);
		login.put("platform", platform);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(login);

		rs.baseUri(baseUrl.urlCore("/v3/login")).contentType(ContentType.JSON).header("Authorization", visitor())
				.body(login).log().all().when().post().then().assertThat().statusCode(200).log().all();

		Response res = rs.post();
		String tokens = res.getBody().path("data.access_token").toString();
		return tokens;
	}

	public String id(String username, String password, String deviceId, String platform) {
		BaseUrl baseUrl = new BaseUrl();
		RequestSpecification rs = RestAssured.given();

		Map<String, Object> login = new HashMap<String, Object>();
		login.put("username", username);
		login.put("password", password);
		login.put("device_id", deviceId);
		login.put("platform", platform);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(login);

		rs.baseUri(baseUrl.urlCore("/v3/login")).contentType(ContentType.JSON).header("Authorization", visitor())
				.body(login).log().all().when().post().then().assertThat().statusCode(200).log().all();

		Response res = rs.post();
		String tokens = res.getBody().path("data.id").toString();
		return tokens;
	}

	public String competition_id() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		RequestSpecification rs = RestAssured.given();

		rs.baseUri(baseUrl.urlUgcVote("/v1/home/" + competitionId())).headers("Authorization", token.visitor()).when()
				.get().then().statusCode(200);

		Response res = rs.get();
		return res.getBody().path("data[0].id").toString();
	}

	public String contestatnt_id() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		RequestSpecification rs = RestAssured.given();

		rs.baseUri(baseUrl.urlUgcVote("/v1/home/" + competitionId())).headers("Authorization", token.visitor()).when()
				.get().then().statusCode(200);

		Response res = rs.get();
		return res.getBody().path("data[0].videos[0].author.user_id").toString();
	}

	public String vote_video__id() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		RequestSpecification rs = RestAssured.given();

		rs.baseUri(baseUrl.urlUgcVote("/v1/home/" + competitionId())).headers("Authorization", token.visitor()).when()
				.get().then().statusCode(200);

		Response res = rs.get();
		return res.getBody().path("data[0].videos[0].video_id").toString();
	}

	public String competitionId() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();
		RequestSpecification rs = RestAssured.given();

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition-list")).headers("Authorization", token.visitor()).when().get()
				.then().statusCode(200);

		validation.GetNotNull("data.id", rs);
		System.out.println(validation.returnGetBody(rs, "data[0].id"));
		return validation.returnGetBody(rs, "data[0].id");
	}

	public String videoId() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();
		RequestSpecification rs = RestAssured.given();

		rs.baseUri(baseUrl.urlUgcVote("/v1/home/" + token.competitionId())).headers("Authorization", token.visitor())
				.when().get().then().statusCode(200);

		validation.GetNotNull("data.videos.video_id", rs);
		System.out.println(validation.returnGetBody(rs, "data[0].videos[0].video_id"));
		return validation.returnGetBody(rs, "data[0].videos[0].video_id");
	}

	public String quotaVoteDefault() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();
		RequestSpecification rs = RestAssured.given();

		rs.baseUri(baseUrl.urlUgcVote("/v1/vote/competition")).param("competition_id", competition_id())
				.headers("Authorization", token.login("paijo@mailinator.com", "dikakoko", "1234", "android")).log()
				.all().when().get().then().statusCode(200).log().all();

		validation.getMessageClientSuccess(rs);
		validation.GetNotNull("data.user_id", rs);
		// validation.getBodyContains("data.max_vote_quota", rs, "3");
		// validation.getBody("data.remaining_quota", rs, "3");

		return validation.getBodyReturn("data.remaining_quota", rs);
	}

}