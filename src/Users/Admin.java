package Users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;
import Login.Login;
import Product.Product;
import Product.IProduct;


// Admin creates Products and manager of the products

public class Admin extends User{

	private static List<Product> productList = new ArrayList<Product>();	// low coupling. Admin stores all products to productList
	
	public Admin(String userType, String userName, String password, Product product) {
		super(userType, userName, password,product);
	}
	
	static Scanner scanner = new Scanner(System.in);
	
	public static void AdminPage(User user) throws JSONException, IOException {
		HelperMethods hp = new HelperMethods();
		System.out.println("Welcome " + user.getUserName());
		while(true) {
			System.out.println("Please select an option:");
			System.out.println("1- Add a product");
			System.out.println("2- List all product");
			System.out.println("3- Logout");
			System.out.println("4- Program exit");
			String option = scanner.next();
			switch(option) {
			case "1":
				hp.addProduct(user);
				break;
			case "2":
				listAllProduct();
				break;
			case "3":
				System.out.println("Logout is successful.");
				Login.LoginPage();
				break;
			case "4":
				System.out.println("Good bye!");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid option");
				break;
			}
		if(option.equals("3"))
			break;
		}	
	}

	public static void helpProductList(IProduct product){
		for(IProduct prd: product.getProduct()){
			if("class Product.Part".equals(prd.getClass().toString()))
			{
				System.out.println(product.getName() + "'s product list: ID= " + prd.getId() + " title= " +
						prd.getName() + " status= " + prd.getContext() + " userType= " +
						prd.getEmployee().getUserType() + " userName= " + prd.getEmployee().getUserName());
			}
			else if ("class Product.Assembly".equals(prd.getClass().toString())) {
				System.out.println(product.getName() + "'s product list: ID= " + prd.getId() + " title= " + prd.getName()
						+ " status= " + prd.getContext());
				if (prd.getProduct().size() > 0) {
					product = prd;
					helpProductList(product);
				}
			}
		}
	}
	public static void listAllProduct(){
		Manager manager = new Manager();
		for(Product allproducts: Admin.getProductList()){
			manager = allproducts.getManager();
			for(IProduct prd: manager.getProductList()) {
				System.out.println(allproducts.getName() + "'s product list: ID= " + prd.getId() +
						" title= " + prd.getName() + " status= " + prd.getContext());
				if ("class Product.Assembly".equals(prd.getClass().toString())){
					if (prd.getProduct().size() > 0) {
						helpProductList(prd);
					}
				}
				if("class Product.Part".equals(prd.getClass().toString()))
					System.out.println(allproducts.getName() + "'s product list: ID= " + prd.getId() +
							" title= " + prd.getName() + " status= " + prd.getContext() + " userType= " +
							prd.getEmployee().getUserType() + " userName= " + prd.getEmployee().getUserName());
				System.out.println();
			}
		}
	}
	public static List<Product> getProductList() {
		return productList;
	}

	public static void setProductList(List<Product> productList) {
		Admin.productList = productList;
	}

	public static void getProducts() {
		for(Product prd: productList)
		{
			System.out.println(prd);
		}	
	}
}
