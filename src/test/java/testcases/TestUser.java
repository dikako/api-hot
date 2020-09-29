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

public class TestUser extends Base {

	public String path = "../api-hot/src/test/java/com/test/api/datatest/data.xlsx";

	@DataProvider
	public String[][] User() throws InvalidFormatException, IOException {
		ReadExcel readExcel = new ReadExcel();
		return readExcel.getCellData(path, "User");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get My Profile")
	@Test(priority = 0, testName = "Get My Profile", dataProvider = "User")
	public void myProfile(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/my-profile"))
				.headers("Authorization", token.login(username, password, "1234", "android")).when().get().then()
				.statusCode(200).log().all();

		validation.getMessageClientSuccess(rs);
		validation.GetNotNull("data.user_id", rs);
		validation.GetNotNull("data.role", rs);
		validation.GetNotNull("data.total_followers", rs);
		validation.GetNotNullOr("data.email", "data.phone", rs);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Other User")
	@Test(priority = 1, testName = "Get Other User", dataProvider = "User")
	public void otherUserVisitorToken(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		String id = token.id(username, password, "65657", "android");

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/user-profile")).param("user_id", id)
				.headers("Authorization", token.visitor()).when().get().then().log().all().statusCode(200).log().all();

		validation.getMessageClientSuccess(rs);
		validation.GetNotNull("data.user_id", rs);
		validation.GetNotNull("data.role", rs);
		validation.GetNotNull("data.total_followers", rs);
		validation.GetNotNullOr("data.nickname", "data.display_name", rs);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Other User")
	@Test(priority = 2, testName = "Get Other User", dataProvider = "User")
	public void otherUserTokenUser(String username, String password) {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		String id = token.id(username, password, "65657", "android");

		rs.baseUri(baseUrl.urlUgcVote("/v1/user/user-profile")).param("user_id", id)
				.headers("Authorization", token.login("paijo@mailinator.com", "dikakoko", "5363", "android")).when()
				.get().then().log().all().statusCode(200).log().all();

		validation.getMessageClientSuccess(rs);
		validation.GetNotNull("data.user_id", rs);
		validation.GetNotNull("data.role", rs);
		validation.GetNotNull("data.total_followers", rs);
		validation.GetNotNullOr("data.nickname", "data.display_name", rs);
	}

}