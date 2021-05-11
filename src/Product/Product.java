package Product;

import State.Context;
import Users.Manager;


public class Product {

	// Product have a manager

	private int ID ;
	private String name;
	Manager manager = new Manager();
	private Context context = new Context(); 
	
	public Product(int iD, String name) {
		ID = iD;
		this.name = name;
		this.setManager(manager);
	}
	
	public Product() {
		this.setID(0);
		this.setName("");;
		this.setManager(manager);
	}
	
	public Context getContext() {
		return context;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ID + "," + name + "," + context + "," + manager;
	}
	
}
