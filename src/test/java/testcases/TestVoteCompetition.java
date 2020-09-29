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

public class TestVoteCompetition extends Base {

	@Severity(SeverityLevel.CRITICAL)
	@Description("Quota Vote Default")
	@Test(priority = 0, testName = "Quota Vote Default")
	public void getQuota() {

		BaseUrl baseUrl = new BaseUrl();
		Token token = new Token();
		Validation validation = new Validation();

		rs.baseUri(baseUrl.urlUgcVote("/v1/vote/competition")).param("competition_id", token.competition_id())
				.headers("Authorization", token.login("6282278843303", "dikakoko", "647464", "android")).log().all()
				.when().get().then().log().all().statusCode(200).log().all();

		validation.getMessageClientSuccess(rs);
		validation.GetNotNull("data.remaining_quota", rs);
		validation.getBody("data.max_vote_quota", rs, "3");
		validation.getBody("data.remaining_quota", rs, "3");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Vote")
	@Test(priority = 1, testName = "Post Vote")
	public void postVote() {
		BaseUrl baseUrl = new BaseUrl();
		Token token = new Token();
		Validation validation = new Validation();

		Integer comp_id = Integer.valueOf(token.competition_id());
		Integer const_id = Integer.valueOf(token.contestatnt_id());
		Integer vid_id = Integer.valueOf(token.vote_video__id());

		Map<String, Object> vote = new HashMap<String, Object>();
		vote.put("competition_id", comp_id);
		vote.put("contestant_id", const_id);
		vote.put("vote_number", 1);
		vote.put("video_id", vid_id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(vote);

		rs.baseUri(baseUrl.urlUgcVote("/v1/vote/competition")).contentType(ContentType.JSON)
				.headers("Authorization", token.login("6282278843303", "dikakoko", "647464", "android")).body(vote)
				.log().all().then().log().all().statusCode(200).log().all();

		validation.postMessageClientSuccess(rs);

	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Vote Melebihi Quota")
	@Test(priority = 2, testName = "Post Vote Melebihi Quota")
	public void postVoteLebih() {
		BaseUrl baseUrl = new BaseUrl();
		Token token = new Token();
		Validation validation = new Validation();

		Integer comp_id = Integer.valueOf(token.competition_id());
		Integer const_id = Integer.valueOf(token.contestatnt_id());
		Integer vid_id = Integer.valueOf(token.vote_video__id());

		Map<String, Object> vote = new HashMap<String, Object>();
		vote.put("competition_id", comp_id);
		vote.put("contestant_id", const_id);
		vote.put("vote_number", 10);
		vote.put("video_id", vid_id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(vote);

		rs.baseUri(baseUrl.urlUgcVote("/v1/vote/competition")).contentType(ContentType.JSON)
				.headers("Authorization", token.login("6282278843303", "dikakoko", "647464", "android")).body(vote)
				.log().all().then().log().all().statusCode(422).log().all();

		validation.postBody("status.message_client", rs, "don't have a enaugh quota number");
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Vote Quota Habis")
	@Test(priority = 2, testName = "Post Vote Quota Habis")
	public void postVoteNullQuota() {
		BaseUrl baseUrl = new BaseUrl();
		Token token = new Token();
		Validation validation = new Validation();

		Integer comp_id = Integer.valueOf(token.competition_id());
		Integer const_id = Integer.valueOf(token.contestatnt_id());
		Integer vid_id = Integer.valueOf(token.vote_video__id());

		Map<String, Object> vote = new HashMap<String, Object>();
		vote.put("competition_id", comp_id);
		vote.put("contestant_id", const_id);
		vote.put("vote_number", 1);
		vote.put("video_id", vid_id);

		List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
		json.add(vote);

		for (int i = 3; i <= 3; i++) {
			rs.baseUri(baseUrl.urlUgcVote("/v1/vote/competition")).contentType(ContentType.JSON)
					.headers("Authorization", token.login("dikakoko04@gmail.com", "dikakoko", "1234", "android"))
					.body(vote).log().all().then().log().all().statusCode(200).log().all();
		}

		validation.postBody("status.message_client", rs, "don't have a enaugh quota number");
	}
}