package testCases;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.util.List;
import com.aventstack.extentreports.Status;

import baseClass.BaseClass;
import io.restassured.RestAssured;
import testUtils.TestDataProvider;
import testUtils.XLUtils;

public class SearchUserByEmail extends BaseClass {

	/*
	 * Retrieve the user details through GET Request Parse the Json Response and
	 * Retrieve the Email attribute to a List Compare the Email list with the Email
	 * from the Test Data
	 */
	@Test(dataProvider = "SearchUserEmail", dataProviderClass = TestDataProvider.class)
	public void searchEmail(String rowNo, String userEmail) throws NumberFormatException, IOException {

		test = ReportHandler.getExtent().createTest(rowNo + " : searchEmail");
		logger.info("***Started searchEmail Test Case***");
		test.log(Status.PASS, "***Start searchEmailTest Case***");

		RestAssured.baseURI = BaseClass.getUserBaseURI();
		test.log(Status.PASS, "Request URL is " + RestAssured.baseURI);
		test.log(Status.PASS, "Request Method is GET");

		response = given().when().get().then().extract().response();

		int statusCode = response.getStatusCode();
		if (statusCode != 200) {
			test.log(Status.FAIL, "statusCode" + statusCode);
			Assert.assertEquals(statusCode, 200, " status code returned");
		}
		List<String> email = response.jsonPath().getList("email");
		boolean emailFound = false;
		for (int tmp = 0; tmp < email.size(); tmp++) {
			if (email.get(tmp).equalsIgnoreCase(userEmail)) {

				AssertJUnit.assertTrue(true);
				XLUtils.updateExcel(rowNo, userEmail, String.valueOf(response.statusCode()),"PASSED");
				test.log(Status.PASS, "User email " + userEmail + " is found ");
				emailFound = true;
			}
		}

		if (emailFound == false) {
			XLUtils.updateExcel(rowNo, userEmail, String.valueOf(response.statusCode()),"FAILED");
			test.log(Status.FAIL, "User email" + userEmail + "is not found");
			AssertJUnit.assertTrue(false);
		}

	}

}
