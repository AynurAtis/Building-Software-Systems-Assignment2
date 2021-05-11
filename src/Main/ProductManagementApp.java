package Main;

import Login.Login;
import org.json.JSONException;
import FileIO.Writer;
import java.io.IOException;


public class ProductManagementApp {
	public static void main(String args[]) throws JSONException, IOException {
		Writer.jsonWriter();
		System.out.println(" ********WELCOME PRODUCT MANAGEMENT APPLICATION!********\n");
		Login.LoginPage();
	}
}
