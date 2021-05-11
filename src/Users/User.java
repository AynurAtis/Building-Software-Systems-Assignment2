package Users;

import Product.IProduct;
import Product.Product;

public class User {	// parent class

	private String userType;
	private String userName;
	private String password;
	Product product;
	IProduct part;
	
	public User(String userType, String userName, String password, Product product) {
		this.userType = userType;
		this.userName = userName;
		this.password = password;
		this.product=product;
	}
	public User() {
		this.setUserType("");
		this.setUserName("");
		this.setPassword("");
	}
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public IProduct getPart() {
		return part;
	}
	public void setPart(IProduct part) {
		this.part = part;
	}
	@Override
	public String toString() {
		return userType + "," + userName;
	}
	
	
}
