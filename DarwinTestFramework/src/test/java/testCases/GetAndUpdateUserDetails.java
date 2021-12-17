package testCases;

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.google.gson.Gson;
import baseClass.BaseClass;
import io.restassured.RestAssured;
import testUtils.TestDataProvider;
import testUtils.XLUtils;
import userDetails.pojo.Address;
import userDetails.pojo.Company;
import userDetails.pojo.Geo;
import userDetails.pojo.User;
import static io.restassured.RestAssured.*;
import java.io.IOException;

public class GetAndUpdateUserDetails extends BaseClass {

	User userData;

	/*
	 * Pass the User record ID from Test Data to the path Params of GET Request
	 */
	@Test(dataProvider = "GetUserDetails", dataProviderClass = TestDataProvider.class)
	public void getUserDetails(String rowNo, String userRecord) throws NumberFormatException, IOException {

		try {
			logger.info("***Started GetUserDetails Test Case***");
			test = ReportHandler.getExtent().createTest(rowNo + " : GetUserDetails");
			String responseData;
			String id = userRecord;
			test.log(Status.PASS, "***Start GetUserDetails  Test Case***");
			RestAssured.baseURI = BaseClass.getUserBaseURI();
			test.log(Status.PASS, "Request URL is " + RestAssured.baseURI);
			test.log(Status.PASS, "Request Method is GET the User Details of : " + userRecord);
			response = given().pathParam("id", id).when().get("/{id}").then().extract().response();

			System.out.println("Response is: " + response.asPrettyString());

			User userData = new Gson().fromJson(response.asPrettyString(), User.class);
			responseData = "Username :" + userData.getUsername() + "\n" + "Email :" + userData.getEmail() + "\n"
					+ "Address : " + userData.getAddress().toString() + "\n" + "Phone : " + userData.getPhone() + "\n"
					+ "Website : " + userData.getWebsite();

			String responseBody = response.getBody().asPrettyString();
			String responseCode = String.valueOf(response.statusCode());
			System.out.println("Response body is: " + responseBody);
			
			if (responseCode.equalsIgnoreCase("200")) {
				test.log(Status.PASS, "User Info:" + responseData);
				logger.info("***  GetUser Details Test Case execution is completed ***");
				test.log(Status.PASS, "***  GetUser Details Test Case execution is completed ***");
				XLUtils.updateExcel(rowNo, responseData, responseCode,"PASSED");
				Assert.assertTrue(true);

			} else {
				test.log(Status.FAIL, responseBody);
				logger.info("***  GetUser Details Test Case execution is completed ***");
				test.log(Status.PASS, "***  GetUser Details Test Case execution is completed ***");
				XLUtils.updateExcel(rowNo, responseData, responseCode,"FAILED");
				Assert.assertTrue(false, "Response code is" + responseCode);

			}

		} catch (Exception ex) {
			test.log(Status.FAIL, ex.getMessage());
			Assert.assertTrue(false);

		}
	}

	/*
	 * Get the Existing User Record Covert them to POJO Update the POJO with Data
	 * from the Excel Send the PUT Request with Updated POJO Data Convert the PUT
	 * Response to POJO Compare the PUT Request body and Response Body
	 */

	@Test(dataProvider = "UpdateUserDetails", dataProviderClass = TestDataProvider.class)
	public void update(String rowNo, String userRecord, String name, String username, String email,
			String addressStreet, String addressSuite, String addresscity, String addressZipcode, String geoCodeLat,
			String geoCodeLan, String phone, String website, String companyName, String companyCatchPharse, String bs)
					throws NumberFormatException, IOException {

		try {
			logger.info("***Started Update User Details Test Case***");
			test = ReportHandler.getExtent().createTest(rowNo + " : Update User Details");
			test.log(Status.PASS, "***Start Update User Details  Test Case***");
			String userId = userRecord;
			RestAssured.baseURI = BaseClass.getUserBaseURI();
			test.log(Status.PASS, "Request URL is " + RestAssured.baseURI);
			test.log(Status.PASS, "Request Method is GET");

			// Get the Existing User Record
			response = given().pathParam("userId", userId).when().get("/{userId}").then().extract().response();

			int statusCode = response.getStatusCode();
			String originalUserinfo = response.asPrettyString();
			if (statusCode != 200) {

				test.log(Status.FAIL, "original Userinfo of :" + userId + originalUserinfo);
				test.log(Status.FAIL, "Cannot Progrss with Test steps due to Status Code" + statusCode);
				Assert.assertEquals(statusCode, 200, " status code returned");
			}
			test.log(Status.PASS, "original Userinfo of :" + userId + originalUserinfo);

			// Covert Response to POJO
			User newUser = new Gson().fromJson(response.asPrettyString(), User.class);

			// Update the POJO with New values from Test Data

			Address newAddress = newUser.getAddress();
			Company newCompany = newUser.getCompany();

			Geo newGeo = newAddress.getGeo();

			if (geoCodeLat != "") {
				newGeo.setLat(geoCodeLat);
			}
			if (geoCodeLan != "") {
				newGeo.setLng(geoCodeLan);
			}
			if (addresscity != "") {
				newAddress.setCity(addresscity);
			}
			if (addressStreet != "") {
				newAddress.setStreet(addressStreet);
			}
			if (addressSuite != "") {
				newAddress.setSuite(addressSuite);
			}
			if (addressZipcode != "") {
				newAddress.setZipcode(addressZipcode);
			}
			if (geoCodeLan != "") {
				newAddress.geo.setLng(geoCodeLan);
			}
			if (bs != "") {
				newCompany.setBs(bs);
			}
			if (companyCatchPharse != "") {
				newCompany.setCatchPhrase(companyCatchPharse);
			}
			if (geoCodeLan != "") {
				newAddress.geo.setLng(geoCodeLan);
			}

			if (name != "") {
				newUser.setName(name);
			}
			if (username != "") {
				newUser.setUsername(username);
			}
			if (email != "") {
				newUser.setEmail(email);
			}
			if (geoCodeLat != "") {
				newGeo.setLat(geoCodeLat);
			}
			if (geoCodeLan != "") {
				newGeo.setLng(geoCodeLan);
			}
			if (website != "") {
				newUser.setWebsite(website);
			}
			if (phone != "") {
				newUser.setPhone(phone);
			}

			if (newAddress != null) {
				newUser.setAddress(newAddress);
			}
			if (newCompany != null) {
				newUser.setCompany(newCompany);
			}
			if (newGeo != null) {
				newAddress.setGeo(newGeo);
			}

			test.log(Status.PASS, "Request Method is PUT , modify the UserInfo of :" + userId);
			test.log(Status.PASS, "PUT Request Body  " + newUser.toString());

			// Send the PUT request with the updated Message body
			response = given().contentType("application/json").body(newUser).when().put(userId).then().extract()
					.response();

			String responseBody = response.getBody().asPrettyString();
			String responseCode = String.valueOf(response.statusCode());
			System.out.println("Response body is: " + responseBody);

			// Convert the PUT response to POJO
			User updatednewUser = new Gson().fromJson(response.asPrettyString(), User.class);
			

			// Verify if the Server is updated with new values based on Response
			if (responseCode.equalsIgnoreCase("200")) {
				Assert.assertTrue(true);
				test.log(Status.PASS, "PUT Response Body:" + responseBody);
				logger.info("***  GetUser Details Test Case execution is completed ***");
				test.log(Status.PASS, "***  GetUser Details Test Case execution is completed ***");
				if (updatednewUser.toString().equalsIgnoreCase(newUser.toString())) {
					test.log(Status.PASS, "User Attributes are updated successfuly in the server");
					logger.info("***  User Attributes Test Case execution is completed ***");
					XLUtils.updateExcel(rowNo, responseBody, responseCode,"PASSED");
					Assert.assertTrue(true);
				} else {
					test.log(Status.FAIL, "User Attributes are not updated  in the server");
					logger.info("***  User Attributes Test Case execution is completed ***");
					XLUtils.updateExcel(rowNo, responseBody, responseCode,"FAILED");
					Assert.assertTrue(false);
				}
			} else {
				test.log(Status.FAIL, responseBody);
				Assert.assertTrue(false, "Response code is" + responseCode);

			}
		} catch (Exception ex) {
			test.log(Status.FAIL, ex.getMessage());
			Assert.assertTrue(false);
		}
	}

}
