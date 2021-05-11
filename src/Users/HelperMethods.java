package Users;

import java.io.IOException;
import java.util.Scanner;
import org.json.JSONException;
import FileIO.Writer;
import Product.Assembly;
import Product.IProduct;
import Product.Part;
import Product.Product;
import State.Status;
import Login.Login;

public class HelperMethods {
	
	static Scanner scanner = new Scanner(System.in);

	public boolean isNumeric(String number)	// isNumeric control function
	{
		return number != null && number.matches("[-+]?\\d*\\.?\\d+");
	}
	public void addProduct(User user) throws JSONException, IOException {
		boolean prdctID = false;
		boolean prdctName = false;
		boolean usrName = false;
		System.out.println("Please enter the Product information");
		System.out.println("Product id = ");
		String productID = scanner.next();
		while (!isNumeric(productID)){
			System.out.println("Product ID must be numeric");
			System.out.println("Product id = ");
			productID = scanner.next();
		}
		for(Product prdct : Admin.getProductList())
		{
			while(true) {
				if (Integer.parseInt(productID) == prdct.getID()) {	// check the product id if the same
					prdctID = true;
					System.out.println("There cannot be same id!");
					System.out.println("Please enter the Product information");
					System.out.println("Product id = ");
					productID = scanner.next();
					while (!isNumeric(productID)){
						System.out.println("Product ID must be numeric");
						System.out.println("Product id = ");
						productID = scanner.next();
					}
				} else {
					prdctID = false;
					break;
				}
			}
		}
		if(!prdctID)
		{
			System.out.println("Product Name = ");
			String productName = scanner.next();
			for(Product prdct : Admin.getProductList()) {	// check the product name if the same
				while(true) {
					if(prdct.getName().equals(productName))
					{
						prdctName = true;
						System.out.println("There is a product such that.");
						System.out.println("Product Name = ");
						productName = scanner.next();
					}
					else {
						prdctName = false;
						break;
					}
				}
			}
			if(!prdctName) {
				System.out.println("You have to add a manager that you created product");
				String userType = "Manager";
				System.out.println("Please enter manager information");
				System.out.println("User Name = ");
				String userName = scanner.next();
				for(Product prdct: Admin.getProductList())
				{
					while(true) {
						if(prdct.getManager().getUserName().equals(userName))
						{
							usrName = true;
							System.out.println("This manager is already manager of another product");
							System.out.println("User Name = ");
							userName = scanner.next();
						}
						else {
							usrName = false;
							break;
						}
					}
				}
				if(!usrName) {
					System.out.println("Password = ");
					String password = scanner.next();
					Product product = new Product(Integer.parseInt(productID), productName); // product is created
					Manager manager=new Manager(userType, userName, password, product);	// manager is created
					product.setManager(manager);
					Login.addUserList(manager); // manager is added to userList
					Admin.getProductList().add(product); // product is added to productList
					Writer.jsonProductWriter(Admin.getProductList()); // product is written to product.json file
					Writer.jsonUserWriter(userType,userName,password,product,null); // user is written to user.json file
				}
			}
		}
	}
	
	public boolean checkPartID(IProduct product, int productID) {
		// this function is helper function to check that if there exist same partID
		for (IProduct prd : product.getProduct()) {
			if("class Product.Assembly".equalsIgnoreCase(prd.getClass().toString())) {
				product = prd;
				checkPartID(product, productID);
			}
			else if("class Product.Part".equalsIgnoreCase(prd.getClass().toString()))
			{
				if(prd.getId() == productID)
					return true;
			}
			else return false;	
		}	
		return false;
	}
	
	
	public boolean checkAssemblyID(IProduct product, int productID) {
		// this function is helper function to check that if there exist same AssemblyID
		for(IProduct prd : product.getProduct()) {
			if(product.getId() != productID) {
				if("class Product.Assembly".equalsIgnoreCase(prd.getClass().toString())) {
					product = prd;
					checkAssemblyID(product, productID);
				}
				return false;
			}
			else return true;
		}
		return false;
	}
	
	public boolean checkEmployee(IProduct product, String userName) {
		// this function is helper function to check that if there exist same Employee
		for (IProduct prd : product.getProduct()) {
			if("class Product.Assembly".equalsIgnoreCase(prd.getClass().toString())) {
				product = prd;
				checkProductName(product, userName);
			}
			else if("class Product.Part".equalsIgnoreCase(prd.getClass().toString()))
			{
				if(prd.getEmployee().getUserName().equals(userName))
					return true;
			}
			else return false;	
		}	
		return false;
	}
	
	public boolean checkProductName(IProduct product, String productName) {
		// this function is helper function to check that if there exist same productName
		for (IProduct prd : product.getProduct()) {
			if("class Product.Assembly".equalsIgnoreCase(prd.getClass().toString())) {
				product = prd;
				checkProductName(product, productName);
			}
			else if("class Product.Part".equalsIgnoreCase(prd.getClass().toString()))
			{
				if(prd.getName().equals(productName))
					return true;
			}
			else return false;	
		}	
		return false;
	}
	public void printAllAssemblies(IProduct prdct) {
		// this function is helper function to print all assemblies
		System.out.println(prdct.getName());
		if(prdct.getProduct().size() >0) {
			for(IProduct product: prdct.getProduct())
			{
				if("class Product.Assembly".equalsIgnoreCase(product.getClass().toString())) {
					prdct = product;
					printAllAssemblies(prdct);
				}
			}
		}
	}
	public boolean addProductToAssembly(IProduct findingAssembly, String option, IProduct product) {
		// this function adds the created product to assembly product
		if(!findingAssembly.getName().equalsIgnoreCase(option))
		{
			for(IProduct prodct: findingAssembly.getProduct())
			{
				if("class Product.Assembly".equalsIgnoreCase(prodct.getClass().toString()))
				{
					findingAssembly = prodct;
					addProductToAssembly(findingAssembly, option, product);
				}
			}
			return false;
		}
		else {
			findingAssembly.add(product);
			return true;
		}
	}
	
	public void createAssembly(User user) throws JSONException, IOException {
		Product product = null;
		for(Product prd: Admin.getProductList()) {	// get manager's product
			if(prd.getManager().getUserName().equals(user.getUserName())) {
				product = prd;
				break;
			}
		}
		Manager manager = product.getManager();
		System.out.println("Please select the place that you want to add an assembly.");
		System.out.println(product.getName());
		for(IProduct prdct : manager.getProductList())
		{
			if("class Product.Assembly".equalsIgnoreCase(prdct.getClass().toString()))
				printAllAssemblies(prdct);	// print all assemblies to select
		}
		boolean checkProductID = false;
		boolean checkOption = false;
		String option = scanner.next();

		System.out.println("Please enter the assembly information.");
		System.out.println("ID: ");
		String ID = scanner.next();
		while (!isNumeric(ID)){ // check the ID is numeric or not
			System.out.println("ID must be a numeric");
			System.out.println("ID = ");
			ID = scanner.next();
		}
		for(IProduct checkAssembly : manager.getProductList())
		{
			if("class Product.Assembly".equalsIgnoreCase(checkAssembly.getClass().toString()))
			{
				while(true) {
					if(checkAssembly.getId() == Integer.parseInt(ID))	// check the assembly id if it is already exist
						checkProductID = true;
					else checkProductID = false;
					if(checkAssembly.getProduct().size() > 0)
						checkProductID = checkAssemblyID(checkAssembly, Integer.parseInt(ID));
					if(checkProductID) {
						System.out.println("This ID already exist! Please enter the new one!");
						System.out.println("ID: ");
						ID = scanner.next();
						while (!isNumeric(ID)){
							System.out.println("ID must be a numeric");
							System.out.println("ID = ");
							ID = scanner.next();
						}
					}
					if(!checkProductID) break;
				}

			}
		}
		boolean checkProductName = false;
		if(!checkProductID) {
			System.out.println("Title: ");
			String title = scanner.next();
			for(IProduct checkAssembly : manager.getProductList())
			{
				if("class Product.Assembly".equalsIgnoreCase(checkAssembly.getClass().toString())) {
					while(true) {
						if(checkAssembly.getName().equals(title)) // check the assembly name if there is already exist
						{
							checkProductName = true;
						}
						else
							checkProductName = false;
						if(checkAssembly.getProduct().size() > 0)
							checkProductName = checkProductName(checkAssembly, title);
						if(checkProductName) {
							System.out.println("This name already exist! Please enter the new one!");
							System.out.println("Title: ");
							title = scanner.next();
						}
						if(!checkProductName) break;
					}

				}
			}
			if(!checkProductName) {
				IProduct assembly = new Assembly(Integer.parseInt(ID), title);
				boolean isTrue = false;
				if(option.equalsIgnoreCase(product.getName()))
					manager.addProduct(assembly);
				else {
					for(IProduct findingAssembly : manager.getProductList())
					{
						if("class Product.Assembly".equalsIgnoreCase(findingAssembly.getClass().toString()))
						{
							isTrue = addProductToAssembly(findingAssembly, option, assembly);
						}
						if(isTrue)
							break;
					}
				}
			}
			product.setManager(manager);
			Writer.jsonProductWriter(Admin.getProductList());	// write the product to user.json
			}
	}
	
	public IProduct helperCreatingPart(Manager manager, Product product) throws JSONException, IOException {
		IProduct part = null;
		System.out.println("Please enter the part information");
		System.out.println("Part ID = ");
		String ID = scanner.next();
		while (!isNumeric(ID)){	// check the ID is numeric or not
			System.out.println("ID must be a numeric");
			System.out.println("ID = ");
			ID = scanner.next();
		}
		boolean checkPartID = false;
		boolean checkPartName = false;
		for(IProduct prduct : manager.getProductList())
		{
			while(true) {
				if("class Product.Part".equalsIgnoreCase(prduct.getClass().toString()))
				{
					if(prduct.getId() == Integer.parseInt(ID))	// check the part id if there is already exist
						checkPartID = true;
					else checkPartID = false;
				}
				else if("class Product.Assembly".equalsIgnoreCase(prduct.getClass().toString())) {
					if(prduct.getProduct().size() > 0)
						checkPartID = checkPartID(prduct, Integer.parseInt(ID));
				}
				if(checkPartID) {
					System.out.println("This ID already exist! Please enter the new one!");
					System.out.println("ID: ");
					ID = scanner.next();
					while (!isNumeric(ID)){
						System.out.println("ID must be a numeric");
						System.out.println("ID = ");
						ID = scanner.next();
					}
				}
				else break;
			}
		}
		if(!checkPartID) {
			System.out.println("Part title = ");
			String title = scanner.next();
			for(IProduct prduct : manager.getProductList())
			{
				while(true) {
					if("class Product.Part".equalsIgnoreCase(prduct.getClass().toString()))
					{
						if(prduct.getName().equals(title))	// check the partName if there is already exist
							checkPartName = true;
						else checkPartName = false;
					}
					else if("class Product.Assembly".equalsIgnoreCase(prduct.getClass().toString())) {
						if(prduct.getProduct().size() > 0)
							checkPartName = checkProductName(prduct, title);
					}
					if(checkPartName) {
						System.out.println("This name already exist! Please enter the new one!");
						System.out.println("Title: ");
						title = scanner.next();
					}
					else break;
				}
			}
			if(!checkPartName) {
				boolean checkEmployeeName = false;
				//Status status = Status.Not_Started;
				System.out.println("Please enter the employee information");
				String userType = "Employee";
				System.out.println("Employee name = ");
				String userName = scanner.next();
				for(IProduct prd: manager.getProductList()) {
					while(true) {
						if("class Product.Part".equalsIgnoreCase(prd.getClass().toString()))
						{
							if(prd.getEmployee().getUserName().equals(userName)) {
								checkEmployeeName = true;
							}
						}
						else if("class Product.Assembly".equalsIgnoreCase(prd.getClass().toString()))
						{
							checkEmployeeName = checkEmployee(prd, userName);
						}
						if(checkEmployeeName) {
							System.out.println("This name already exist! Please enter the new one!");
							System.out.println("Employee Name: ");
							userName = scanner.next();
						}
						else break;
					}
					if(!checkEmployeeName) break;
				}
				if(!checkEmployeeName) {
					System.out.println("Password = ");
					String password = scanner.next();
					User employee = new Employee(userType, userName, password, product);	// user(Employee) is created
					part = new Part(Integer.parseInt(ID), title, employee);	// part is created
					employee.setPart(part);
					Login.addUserList(employee);	// employee is added to userList
					Writer.jsonUserWriter(userType, userName, password,product, part);	// employee is written to user.json file
				}
			}
		}
		return part;
	}
	
	public void createPart(User user) throws JSONException, IOException {
		Product product = new Product();
		for(Product prd: Admin.getProductList()) {	// find the manager's product
			if(prd.getManager().getUserName().equals(user.getUserName())) {
				product = prd;
				break;
			}
		}
		Manager manager = product.getManager();
		System.out.println("Please enter the product name that you want to add a Part.");
		System.out.println(product.getName());
		for(IProduct prdct : manager.getProductList())
		{
			if("class Product.Assembly".equalsIgnoreCase(prdct.getClass().toString()))
				printAllAssemblies(prdct);
		}
		String choice  = scanner.next();
		boolean isTrue = false;
		if(choice.equalsIgnoreCase(product.getName()))
		{
			IProduct part = helperCreatingPart(manager, product);
			manager.addProduct(part);
			
		}
		else {
				IProduct part = helperCreatingPart(manager, product);
				
				for(IProduct findingAssembly : manager.getProductList())
				{
					if("class Product.Assembly".equalsIgnoreCase(findingAssembly.getClass().toString()))
					{
						isTrue = addProductToAssembly(findingAssembly, choice, part);
					}
					if(isTrue)
						break;
				}
			}
		product.setManager(manager);
		Writer.jsonProductWriter(Admin.getProductList());	// product is written to product.json file
		}
	
	public void checkAssemblyStatusInProgress(IProduct product) {
		// this function is helper function that if above of one part's assembly status is change, it changes all assemblies
		// status of that above of that assembly
		for(IProduct prodc: product.getProduct()){
			if("class Product.Assembly".equalsIgnoreCase(prodc.getClass().toString())){
				if(prodc.getContext().getStatus().equals(Status.In_Progress)){
					product.getContext().setStatus(Status.In_Progress);
				}
				else{
					if(prodc.getProduct().size() > 0){
						product = prodc;
						checkAssemblyStatusInProgress(product);
					}
				}
			}
		}
	}
	public void checkPartStatusHelper(IProduct product, IProduct part){
		// helper function of checkPartStatusInProgress
		for(IProduct prd : product.getProduct()) {
			if("class Product.Part".equalsIgnoreCase(prd.getClass().toString())) {
				if(prd.getName().equals(part.getName())){
					product.getContext().setStatus(Status.In_Progress);
					break;
				}
				else{
					break;
				}
			}
			else if("class Product.Assembly".equalsIgnoreCase(prd.getClass().toString())) {
				if(prd.getProduct().size() > 0) {
					checkPartStatusInProgress(prd, part);
				}
			}
		}
	}
	public void checkPartStatusInProgress(IProduct product, IProduct part) {
		// this function is helper function that if one part's status changes, automatically above of it's assembly changes
		for(IProduct prd : product.getProduct()) {
			if("class Product.Part".equalsIgnoreCase(prd.getClass().toString())) {
				if(prd.getName().equals(part.getName())){
					product.getContext().setStatus(Status.In_Progress);
					break;
				}
				else{
					break;
				}
			}
			else if("class Product.Assembly".equalsIgnoreCase(prd.getClass().toString())) {
				if(prd.getProduct().size() > 0) {
					checkPartStatusHelper(prd, part);
				}
			}
		}
	}	
	
	public void changeTheAssemblyOfStatusAutomatically(Product product, IProduct part) throws JSONException, IOException {// tabi burasi henuz hazir degil. assembly classi da gelince tam olacak merak etme ;)
		// if one part's status changes, automatically connected assemblies status change
		Manager manager = product.getManager();
		for(IProduct prdct: manager.getProductList()) {
			if ("class Product.Assembly".equals(prdct.getClass().toString())) {
				if (prdct.getProduct().size() > 0)
					checkPartStatusInProgress(prdct, part);
			}
			else if("class Product.Part".equals(prdct.getClass().toString())){
				if(prdct.getName().equals(part.getName()))
					product.getContext().setStatus(Status.In_Progress);
			}
		}
		product.getContext().setStatus(Status.Not_Started);
		while (true){
			if(product.getContext().getStatus().equals(Status.In_Progress))
				break;
			for(IProduct prd : manager.getProductList()) {
				if ("class Product.Assembly".equals(prd.getClass().toString())) {
					if (prd.getProduct().size() > 0) {
						if (prd.getContext().getStatus().equals(Status.In_Progress)) {
							product.getContext().setStatus(Status.In_Progress);
						}
						else {
							checkAssemblyStatusInProgress(prd);
						}
					}
				}
				else if("class Product.Part".equals(prd.getClass().toString()))
				{
					break;
				}
			}
		}
		Writer.jsonProductWriter(Admin.getProductList());	// updated product is updated from product.json file

	}		

	public void check(IProduct product){
		// this function is helper function that if all child products completed, automatically changes the connected product status
		int size = product.getProduct().size();
		int counter = 0;
		for(IProduct prd: product.getProduct()){
			if(!prd.getContext().getStatus().equals(Status.Complete)) {
				if ("class Product.Assembly".equals(prd.getClass().toString())) {
					if (prd.getProduct().size() > 0) {
						product = prd;
						check(product);
					}
					else break;
				}
			}
			else
				counter ++;
		}
		if(counter == size)
			product.getContext().setStatus(Status.Complete);
	}

	public void changeTheAssemblyOfStatusAutomaticallyCompleted(Product product, IProduct part) throws JSONException, IOException {
		// this function is that if all child products completed, automatically changes the connected product status
		Manager manager = product.getManager();
		int size = manager.getProductList().size();
		int counter = 0;
		for(IProduct prd: manager.getProductList()){
			if(!prd.getContext().getStatus().equals(Status.Complete)){
				if("class Product.Assembly".equals(prd.getClass().toString())){
					if(prd.getProduct().size() > 0)
					{
						check(prd);
					}
				}
			}
		}
		for(IProduct prd: manager.getProductList()){
			if(prd.getContext().getStatus().equals(Status.Complete))
				counter++;
		}
		if(counter == size) {
			product.getContext().setStatus(Status.Complete);
			System.out.println(product.getName() + " product is completed.");
		}
		Writer.jsonProductWriter(Admin.getProductList());

	}
}