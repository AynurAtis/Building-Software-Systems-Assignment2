package Users;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONException;

import Login.Login;
import Product.IProduct;
import Product.Product;
import State.Complete;
import State.In_Progress;
import State.Not_Started;

public class Employee extends User{	// Inheritance - Employee is an user

	private Manager manager;

	public Employee(String userType, String userName, String password,Product product) { // parameterized constructor
		super(userType, userName, password,product);
		this.setManager(manager);
	}

	public Employee() {//default constructor
		super("Unknown", "Unknown", "Unknown",null);
	} // non-parameterized constructor
	
	static Scanner scanner = new Scanner(System.in);
	
	public  void toNotStarted(IProduct part) {
		Not_Started notStarted = new Not_Started();//only employee can do this
    	notStarted.doAction(part.getContext());//only employee can do this
	}
	public  void toinProgress(IProduct part) {
		In_Progress inProgress = new In_Progress();//only employee can do this
		inProgress.doAction(part.getContext());//only employee can do this
		
	}	
	public  void toCompleted(IProduct part) {
		Complete completed = new Complete();//only employee can do this
		completed.doAction(part.getContext());//only employee can do this
	}
	
	public void helperStatus(Product product, IProduct part) throws JSONException, IOException {
		HelperMethods hp = new HelperMethods();
		System.out.println("To change the status as In progress, enter 1 or to change the status as Completed, enter 2");
		String option = scanner.next();
		switch(option) {
		case "1":
			toinProgress(part); // changes the status of part
			hp.changeTheAssemblyOfStatusAutomatically(product, part);
			System.out.println(part.getName() + "'s status updated as In_Progress.");
			break;
		case "2":
			toCompleted(part);	// changes the status of part
			hp.changeTheAssemblyOfStatusAutomaticallyCompleted(product,part);
			System.out.println(part.getName() + "'s status updated as Completed.");
			break;
		}
	}
	
	public void EmployeePage(User user) throws JSONException, IOException {
		Product product = new Product();
		IProduct part = user.getPart();
		System.out.println("Welcome " + user.getUserName());
		while(true) {
			System.out.println("Please select an option: ");
			System.out.println("1- List own part");
			System.out.println("2- Change status");
			System.out.println("3- Logout");
			System.out.println("4- Program Exit");
			String option = scanner.next();
			switch(option) {
			case "1":
				System.out.println("ID= " + part.getId() + " title= " + part.getName() + " status= " + part.getContext());
				break;
			case "2":
				for(Product prd : Admin.getProductList())
				{
					if(user.getProduct().getName().equals(prd.getName()))
					{
						product = prd;

					}
				}
				helperStatus(product, part);
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
				System.out.println("Invalid option!");
				break;
			}

			if(option.equals("3"))
				break;
		}
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return getUserType() + "," + getUserName();
	}

}
