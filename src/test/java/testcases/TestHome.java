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

public class TestHome extends Base {

	public String path = "../api-hot/src/test/java/datatest/data.xlsx";

	@DataProvider
	public String[][] pathHomeCategory() throws InvalidFormatException, IOException {
		ReadExcel readExcel = new ReadExcel();
		return readExcel.getCellData(path, "PathHomeCategory");
	}

	@DataProvider
	public String[][] pathListCompetition() throws InvalidFormatException, IOException {
		ReadExcel readExcel = new ReadExcel();
		return readExcel.getCellData(path, "pathListCompetition");
	}

	@DataProvider
	public String[][] ListTitleCompetition() throws InvalidFormatException, IOException {
		ReadExcel readExcel = new ReadExcel();
		return readExcel.getCellData(path, "ListTitleCompetition");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get List Category in Home")
	@Test(priority = 0, testName = "Get Category")
	public void getHomeCategory() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		rs.baseUri(baseUrl.urlUgcVote("/v1/home")).headers("Authorization", token.visitor()).when().get().then()
				.statusCode(200).log().all();

		validation.GetNotNull("data.id", rs);
		validation.GetNotNull("data.title", rs);
		validation.GetNotNull("data.sorting", rs);
		validation.getMessageClientSuccess(rs);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Home By Competition in Home")
	@Test(priority = 1, testName = "Get Category")
	public void getHomeByCompetition() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		rs.baseUri(baseUrl.urlUgcVote("/v1/home/" + token.competitionId())).headers("Authorization", token.visitor())
				.when().get().then().statusCode(200).log().all();
		validation.getMessageClientSuccess(rs);
		validation.getMessageServerSuccess(rs);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get List Competition")
	@Test(priority = 2, testName = "Get Competition")
	public void getListCompetition() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();
		Validation validation = new Validation();

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition-list")).headers("Authorization", token.visitor()).when().get()
				.then().statusCode(200).log().all();

		validation.GetNotNull("data.id", rs);
		validation.GetNotNull("data.api", rs);
		validation.GetBodyList("data.title", rs, "[" + "Now," + "Soon," + "Expired" + "]");
		validation.getMessageClientSuccess(rs);
		validation.getMessageServerSuccess(rs);
	}

	// @Severity(SeverityLevel.CRITICAL)
	// @Description("Get Competition Video Category")
	// @Test(priority = 3, testName = "Get Competition Video Category")
	// public void getCompetitionVideoCategory() {
	// Token token = new Token();
	// BaseUrl baseUrl = new BaseUrl();
	// Validation validation = new Validation();

	// rs.baseUri(baseUrl.urlUgcVote("/v1/home/competition" + token.competitionId()
	// + "/" + token.videoId())).headers("Authorization",
	// token.visitor()).when().get().then()
	// .statusCode(200).log().all();

	// validation.GetNotNull("data.id", rs);
	// validation.GetNotNull("data.api", rs);
	// validation.GetBodyList("data.title", rs, "[" + "Now," + "Soon," + "Expired" +
	// "]");
	// validation.getMessageClientSuccess(rs);
	// validation.getMessageServerSuccess(rs);
	// }
}