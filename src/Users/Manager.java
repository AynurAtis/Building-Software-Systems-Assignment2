package Users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONException;
import Login.Login;
import Product.IProduct;
import Product.Product;


public class Manager extends User{ // Inheritance - Manager is an user
	//A manager is responsible from a product and each product consist of parts and assemblies
	//A manager is responsible from a product
	//Manager creates parts and assemblies of the product
	private List<Employee> employeeList = new ArrayList<Employee>();
	public List<IProduct> productList = new ArrayList<IProduct>();
	
	public Manager(String userType, String userName, String password,Product product) { // parameterized constructor
		super(userType, userName, password,product);
		this.setEmployeeList(employeeList);
		this.setProductList(productList);
	}
	
	public Manager() {	// non-parameterized constructor
		this.setEmployeeList(employeeList);
		this.setProductList(productList);
	}

	static Scanner scanner = new Scanner(System.in);

	
	public static void ManagerPage(User user) throws JSONException, IOException {
		HelperMethods hp = new HelperMethods();
			System.out.println("Welcome " + user.getUserName());
			while(true) {
				System.out.println("Please select an option: ");
				System.out.println("1- List all assemblies, parts and employees");
				System.out.println("2- Create an assembly");
				System.out.println("3- Create a part");
				System.out.println("4- Logout");
				System.out.println("5- System exit");
				String option = scanner.next();
				switch(option) {
				case "1":
					listAllProduct(user);
					break;
				case "2":
					hp.createAssembly(user);
					break;
				case "3":
					hp.createPart(user);
					break;
				case "4":
					System.out.println("Logout is successful");
					Login.LoginPage();
					break;
				case "5":
					System.out.println("Good bye!");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid option");
					break;
				}
				if(option.equals("4"))
					break;
			}	
		}

	public static void helpProductList(IProduct product){
		for(IProduct prd: product.getProduct()){
			if("class Product.Part".equals(prd.getClass().toString()))
			{
				System.out.println(product.getName() + "'s product list: ID= " + prd.getId() + " title= " +
						prd.getName() + " status= " + prd.getContext() + " userType= " + prd.getEmployee().getUserType()
						+ " userName= " + prd.getEmployee().getUserName());
			}
			else if ("class Product.Assembly".equals(prd.getClass().toString())) {
				System.out.println(product.getName() + "'s product list: ID= " + prd.getId()
						+ " title= " + prd.getName() + " status= " + prd.getContext());
				if (prd.getProduct().size() > 0) {
					product = prd;
					helpProductList(product);
				}
			}
		}
	}
	public static void listAllProduct(User user){
		Manager manager = null;
		Product product = null;
		for(Product pr: Admin.getProductList())
		{
			if(user.getUserName().equals(pr.getManager().getUserName())) {
				manager = pr.getManager();
				product = pr;
			}
		}
		for(IProduct prd: manager.getProductList()) {
			System.out.println(product.getName() + "'s product list: ID= " + prd.getId() + " title= " + prd.getName()
					+ " status= " + prd.getContext());
			if ("class Product.Assembly".equals(prd.getClass().toString())){
				if (prd.getProduct().size() > 0) {
					helpProductList(prd);
				}
			}
			if("class Product.Part".equals(prd.getClass().toString()))
				System.out.println(product.getName() + "'s product list: ID= " + prd.getId() + " title= " + prd.getName()
						+ " status= " + prd.getContext() + " userType= " + prd.getEmployee().getUserType() +
						" userName= " + prd.getEmployee().getUserName());
			System.out.println();
		}
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	
	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}


	public void addProduct(IProduct product) {
		productList.add(product);
	}
	
	public List<IProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<IProduct> productList) {
		this.productList = productList;
	}

	@Override
	public String toString() {
		return ""  + getUserType() + "," + getUserName() + ","  + productList + "";
				
	}

	
	
}
