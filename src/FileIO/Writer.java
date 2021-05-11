package FileIO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import Product.Product;
import Product.IProduct;
import java.util.List;

public class Writer {
	

	
	static JSONArray userList = new JSONArray();
	
	public static JSONArray jsonWriter() throws IOException, JSONException {
		
		JSONObject admin = new JSONObject();
		
		admin.put("Type", "Admin");
		admin.put("userName", "Admin");
		admin.put("Password", "Admin1234");
		admin.put("Product", "");
		admin.put("Part", "");
		FileWriter writer = new FileWriter("users.json");
		userList.put(admin);
        writer.write(userList.toString());
        writer.flush();
        writer.close();	
        return userList;
	}
	
	public static void jsonUserWriter(String userType, String userName, String password, Product product, IProduct part) throws JSONException, IOException {
		
		JSONObject user = new JSONObject();
		user.put("Type", userType);
		user.put("userName", userName);
		user.put("Password", password);
		user.put("Product", product.getName());
		if(part == null)
			user.put("Part", "");
		else
			user.put("Part", part.getName());

		FileWriter writer = new FileWriter("users.json");
		userList.put(user);
        writer.write(userList.toString());
        writer.flush();
        writer.close();	

	}
	
	public static void jsonProductWriter(List<Product> products) throws JSONException, IOException {
		JSONObject productObject = new JSONObject();
		for(Product product: products){
			productObject.put(product.getName(), product);
			FileWriter writer = new FileWriter("product.json", false);
			//productList.put(productObject);
			writer.write(productObject.toString(5));
			writer.flush();
			writer.close();
		}


	}

	public static JSONArray getUserList() {
		return userList;
	}

	public static void setUserList(JSONArray userList) {
		Writer.userList = userList;
	}
	
	
	
}
