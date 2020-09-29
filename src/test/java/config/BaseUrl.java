package config;

public class BaseUrl {
	String baseUrlCore = "https://rc-api.rctiplus.com/api";
	String baseUrlUgcVote = "https://rc-api.rctiplus.com/ugc-vote/api";
	String baseUrlComment = "https://rc-api.rctiplus.com/ugc-comment/api";

	public String urlCore(String slash) {
		return baseUrlCore + slash;
	}

	public String urlUgcVote(String slash) {
		return baseUrlUgcVote + slash;
	}

	public String urlComment(String slash) {
		return baseUrlComment + slash;
	}

}