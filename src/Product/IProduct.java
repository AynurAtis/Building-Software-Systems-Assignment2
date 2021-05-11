package Product;


import java.util.List;

import State.Context;
import Users.User;

public interface IProduct {// Component.java


	public  int getId();	
	public String getName();	
	public Context getContext();
	public List<IProduct> getProduct();
	public void add(IProduct product);
    public User getEmployee();

}
