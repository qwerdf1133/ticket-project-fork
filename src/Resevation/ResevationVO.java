package Resevation;

public class ResevationVO {

	private String name;
	private String chars;
	private String price;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public ResevationVO(String name, String chars, String price) {
		this.name = name;
		this.chars = chars;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getChars() {
		return chars;
	}


	public void setChars(String chars) {
		this.chars = chars;
	}


	@Override
	public String toString() {
		return "ResevationVO [name=" + name + ", chars=" + chars + ", price=" + price + "]";
	}
	
	
}
