package Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import State.Context;
import Users.User;

public class Assembly implements IProduct {//Composite.java
	 private int id;
	 private String name;
	 private Context context = new Context();

	 public Assembly(int id,String name)
	 {
	  this.id=id;	 
	  this.name = name;
	 }
	 
	 ArrayList<IProduct> productList = new ArrayList<IProduct>();

	@Override
	 public void add(IProduct product) 
	 {
		productList.add(product);
	 }	// Polymorphism

	 
	 @Override
	 public int getId() 
	 {
	  return id;
	 }
	 
	 @Override
	 public String getName() 
	 {
	  return name;
	 }
	 
    @Override
	 public Context getContext()
	 {
	  return context;
	 }


	@Override
	public List<IProduct> getProduct() {
		return productList;
		
	}
	
	@Override
	public String toString() {
		return id + "," + name + "," + context + "," + productList;
	}

	@Override
	public User getEmployee() {
		// TODO Auto-generated method stub
		return null;
	}

}
