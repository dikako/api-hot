package testcases;

import config.Base;
import config.BaseUrl;
import function.Token;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class TestCompetition extends Base {

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Competition Now")
	@Test(priority = 0, testName = "Get Competition Now")
	public void competitionNow() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition/now-competition")).headers("Authorization", token.visitor())
				.log().all().when().get().then().statusCode(200).log().all();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Competition Soon")
	@Test(priority = 1, testName = "Get Competition Soon")
	public void competitionSoon() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition/soon-competition")).headers("Authorization", token.visitor())
				.log().all().when().get().then().statusCode(200).log().all();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get Competition Expired")
	@Test(priority = 2, testName = "Get Competition Expired")
	public void competitionExpired() {
		Token token = new Token();
		BaseUrl baseUrl = new BaseUrl();

		rs.baseUri(baseUrl.urlUgcVote("/v1/competition/expired-competition")).headers("Authorization", token.visitor())
				.log().all().when().get().then().statusCode(200).log().all();
	}
}