package testUtils;

public class RestUtils {

	public static String getRowID(String row) {
		String split[] = row.split("C");
		return split[1];
	}
}
