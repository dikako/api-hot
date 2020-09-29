package testcases;

import java.io.IOException;
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

public class TestSearch extends Base {

	public String path = "../api-hot/src/test/java/com/test/api/datatest/data.xlsx";

	@DataProvider
	public String[][] SearchUser() throws InvalidFormatException, IOException {
		ReadExcel readExcel = new ReadExcel();
		return readExcel.getCellData(path, "SearchUser");
	}

	@DataProvider
	public String[][] SearchVideo() throws InvalidFormatException, IOException {
		ReadExcel readExcel = new ReadExcel();
		return readExcel.getCellData(path, "SearchVideo");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Search User")
	@Test(priority = 0, testName = "Get Search User", dataProvider = "SearchUser")
	public void user(String keyword) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		rs.baseUri(baseUrl.urlUgcVote("/v1/search/users")).param("q", keyword).log().all()
				.headers("Authorization", token.visitor()).log().all().when().get().then().statusCode(200).log().all();

		validation.GetNotNull("data.user_id", rs);
		validation.GetNotNullOr("data.email", "data.phone", rs);
		validation.GetNotNull("data.role", rs);
		validation.GetNotNull("data.total_follower", rs);
		validation.getBodyContains("data.nickname", rs, keyword);
		validation.getBodyContains("data.display_name", rs, keyword);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Search User Not Found")
	@Test(priority = 1, testName = "Get Search User Not Found")
	public void userNotFound() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		rs.baseUri(baseUrl.urlUgcVote("/v1/search/users"))
				.param("q", "hgjhgj8996976976969769769fhweguehferhbcdhfbsdjhfb").log().all()
				.headers("Authorization", token.visitor()).log().all().when().get().then().log().all().statusCode(200)
				.log().all();

		validation.getBody("meta.pagination.total", rs, "0");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Search Video")
	@Test(priority = 2, testName = "Get Search Video", dataProvider = "SearchVideo")
	public void video(String keyword) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		rs.baseUri(baseUrl.urlUgcVote("/v1/search/videos")).param("q", keyword).log().all()
				.headers("Authorization", token.visitor()).log().all().when().get().then().statusCode(200).log().all();

		validation.getMessageClientSuccess(rs);
		validation.GetNotNull("data.video_id", rs);
		validation.GetNotNull("data.url", rs);
		validation.GetNotNull("data.thumbnail", rs);
		validation.GetNotNull("data.author.user_id", rs);
		validation.GetNotNull("data.author.following", rs);
		validation.GetNotNull("data.author.display_name", rs);
		validation.GetNotNull("data.title", rs);
		validation.GetNotNull("data.is_liked", rs);
		validation.GetNotNull("data.competition.competition_id", rs);
		validation.GetNotNull("data.competition.title", rs);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Search Video")
	@Test(priority = 2, testName = "Get Search Video")
	public void videoNotFound() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		rs.baseUri(baseUrl.urlUgcVote("/v1/search/videos")).param("q", "epfhweguehferhbcdhfbsdjhfb").log().all()
				.headers("Authorization", token.visitor()).log().all().when().get().then().statusCode(404).log().all();

		validation.getBody("status.message_client", rs, "no videos found");

	}
}