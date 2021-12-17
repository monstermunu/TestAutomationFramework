package testCases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.google.gson.Gson;

import baseClass.BaseClass;

import java.io.IOException;
import org.testng.Assert;

import io.restassured.RestAssured;
import testUtils.TestDataProvider;
import testUtils.XLUtils;
import userDetails.pojo.Address;
import userDetails.pojo.Company;
import userDetails.pojo.Geo;
import userDetails.pojo.User;

import static io.restassured.RestAssured.*;

public class CreateUser extends BaseClass {

	/*
	 * Build the User attributes from the given Test data Send user object as body
	 * of the POST request Verify the Original user Object with Response body of
	 * POST request
	 */

	@Test(dataProvider = "CreateUser", dataProviderClass = TestDataProvider.class)
	public void createUser(String rowNo, String id, String name, String username, String email, String addressStreet,
			String addressSuite, String addresscity, String addressZipcode, String geoCodeLat, String geoCodeLan,
			String phone, String website, String companyName, String companyCatchPharse, String bs) throws IOException {

		try {

			test = ReportHandler.getExtent().createTest(rowNo + " : createUser");
			logger.info("***Start Create user  Test Case***");
			test.log(Status.PASS, "***Start Create user  Test Case***");
			RestAssured.baseURI = BaseClass.getUserBaseURI();
			test.log(Status.PASS, "Request URL is " + RestAssured.baseURI);
			test.log(Status.PASS, "Request Method is POST");
			User newUser = new User();
			Address newAddress = new Address();
			Company newCompany = new Company();
			Geo newGeo = new Geo();
			newGeo.setLat(geoCodeLat);
			newGeo.setLng(geoCodeLan);
			newAddress.setCity(addresscity);
			newAddress.setStreet(addressStreet);
			newAddress.setSuite(addressSuite);
			newAddress.setZipcode(addressZipcode);
			newAddress.setGeo(newGeo);
			newCompany.setBs(bs);
			newCompany.setCatchPhrase(companyCatchPharse);
			newCompany.setName(companyName);
			if (id != "") {
				newUser.setId(Integer.parseInt(id));
			}
			newUser.setName(name);
			newUser.setUsername(username);
			newUser.setEmail(email);
			newGeo.setLat(geoCodeLat);
			newGeo.setLng(geoCodeLan);
			newUser.setWebsite(website);
			newUser.setPhone(phone);
			newUser.setAddress(newAddress);
			newUser.setCompany(newCompany);

			response = given().contentType("application/json").body(newUser).when().post().then().extract().response();

			User newUserCreated = new Gson().fromJson(response.asPrettyString(), User.class);
			String responseBody = response.getBody().asPrettyString();
			String responseCode = String.valueOf(response.statusCode());
			System.out.println("Response body is: " + responseBody);
			if (responseCode.equalsIgnoreCase("201")) {
				Assert.assertTrue(true);
				test.log(Status.PASS, "Response Body:" + responseBody);
				if (newUserCreated.toString().equalsIgnoreCase(newUser.toString())) {
					test.log(Status.PASS, "User Attributes are Created successfuly in the server");
					logger.info("***  User Attributes Creation Test Case execution is completed ***");
					XLUtils.updateExcel(rowNo, responseBody, responseCode ,"PASSED");
					Assert.assertTrue(true);
				} else {
					test.log(Status.FAIL, "User Attributes are not Created  in the server");
					logger.info("***  User Attributes Creation Test Case execution is completed ***");
					XLUtils.updateExcel(rowNo, responseBody, responseCode , "FAILED");
					Assert.assertTrue(false);
				}
			} else {
				Assert.assertTrue(false, "Response code is" + responseCode);
				test.log(Status.FAIL, responseBody);
			}
		} catch (Exception ex) {

			test.log(Status.FAIL, ex.getMessage());
			Assert.assertTrue(false);
		}
	}

}
