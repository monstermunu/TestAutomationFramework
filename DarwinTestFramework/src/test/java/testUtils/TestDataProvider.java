package testUtils;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import baseClass.BaseClass;

public class TestDataProvider extends BaseClass {

	@DataProvider(name = "CreateUser")
	static Object[][] createUserData() throws IOException {
		String path = getTestDatapath();
		int rownum = XLUtils.getRowCount(path, "Createuser");
		int colcount = XLUtils.getColCount(path, "Createuser", 0);
		Object[][] createuser = new Object[rownum][colcount];
		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < colcount; j++) {
				createuser[i - 1][j] = XLUtils.getCellData(path, "Createuser", i, j);
				System.out.println("Cell Data :" + XLUtils.getCellData(path, "Createuser", i, j));
			}
		}
		return createuser;
	}

	@DataProvider(name = "GetUserDetails")
	static String[][] getUserDetailsData() throws IOException {
		String path = getTestDatapath();
		int rownum = XLUtils.getRowCount(path, "GetUser");
		int colcount = XLUtils.getColCount(path, "GetUser", 0);
		String[][] GetUser = new String[rownum][colcount];
		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < colcount; j++) {
				GetUser[i - 1][j] = XLUtils.getCellData(path, "GetUser", i, j);
				System.out.println("Cell Data :" + XLUtils.getCellData(path, "GetUser", i, j));
			}
		}
		return GetUser;
	}

	@DataProvider(name = "UpdateUserDetails")
	static Object[][] updateUserDetails() throws IOException {
		String path = getTestDatapath();
		int rownum = XLUtils.getRowCount(path, "Updateuser");
		int colcount = XLUtils.getColCount(path, "Updateuser", 0);
		Object[][] updateuser = new Object[rownum][colcount];
		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < colcount; j++) {
				updateuser[i - 1][j] = XLUtils.getCellData(path, "Updateuser", i, j);
				System.out.println("Cell Data :" + XLUtils.getCellData(path, "Updateuser", i, j));
			}
		}
		return updateuser;
	}

	@DataProvider(name = "SearchUserEmail")
	static String[][] SearchUserEmail() throws IOException {

		String path = getTestDatapath();
		int rownum = XLUtils.getRowCount(path, "SearchUser");
		int colcount = XLUtils.getColCount(path, "SearchUser", 0);
		String[][] SearchUser = new String[rownum][colcount];
		for (int i = 1; i <= rownum; i++) {
			for (int j = 0; j < colcount; j++) {
				SearchUser[i - 1][j] = XLUtils.getCellData(path, "SearchUser", i, j);
				System.out.println("Cell Data :" + XLUtils.getCellData(path, "SearchUser", i, j));
			}
		}
		return SearchUser;
	}
}
