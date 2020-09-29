package config;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.restassured.RestAssured;
import io.restassured.config.ConnectionConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;

public class Base {
	protected RequestSpecification rs;

	@BeforeMethod
	public void set() {
		rs = RestAssured.given();
	}

	@AfterMethod
	public void down() {
		new RestAssuredConfig();
		RestAssuredConfig assuredConfig = RestAssuredConfig.newConfig()
				.connectionConfig(ConnectionConfig.connectionConfig().closeIdleConnectionsAfterEachResponse());
		rs.config(assuredConfig);
	}
}