package Product;


import java.util.List;

import State.Context;
import Users.User;

public class Part implements IProduct{	//Leaf.java
	private int id;
	private String title;
	private Context context = new Context();
	private User employee;


	public Part(int id, String title, User employee) {
		this.id = id;
		this.title = title;
		this.employee = employee;
	}

	@Override
	public int getId() {
		return id;
	}	// Polymorphism


	@Override
	public String getName() {
		return title;
	}


	@Override
	public Context getContext() {
		return context;
	}


	@Override
	public void add(IProduct product) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<IProduct> getProduct() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public User getEmployee() {
		return employee;
	}


	@Override
	public String toString() {
		return id + "," + title + "," + context + "," + employee;
	}




}
