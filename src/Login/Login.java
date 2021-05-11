package Login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONException;
import Users.Admin;
import Users.Employee;
import Users.Manager;
import Users.User;

public class Login {
	
	static Scanner scanner = new Scanner(System.in);
	static List<User> userList = new ArrayList<User>();	// low coupling

	public static void addUserList(User user){
		 userList.add(user);
	}

	public static void LoginPage() throws JSONException, IOException {
		Employee em = new Employee();
		Admin admin = new Admin("Admin", "Admin", "Admin123", null);
		userList.add(admin);
		boolean isExist = false;
		boolean passwordIsTrue = false;
		System.out.println("To enter the system, you have to enter your user name and password.");
		while(true) {
			System.out.println("User Name = ");
			String userName = scanner.next();
			for(User usr: userList) {
				if(userName.equals(usr.getUserName()))
				{
					isExist = true;
					System.out.println("Password = ");
					String password = scanner.next();
					if(usr.getPassword().equals(password)) {
						passwordIsTrue = true;
						if(usr.getUserType().equals("Admin"))
						{
							Admin.AdminPage(usr);
							break;
						}
						else if(usr.getUserType().equals("Manager")) {
							Manager.ManagerPage(usr);
							break;
						}
						else if(usr.getUserType().equals("Employee"))
						{
							em.EmployeePage(usr);
							break;
						}
					}
					if(!passwordIsTrue)
						System.out.println("Wrong Password!");
				}
			}
			if(isExist && passwordIsTrue)
				break;
			if(!isExist)
				System.out.println("There is not such an User. Try again...");
		}
	}
}
