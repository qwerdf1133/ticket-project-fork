package pay;

import javafx.scene.control.TextField;

public class PayVO {
	private String name;
	private String price;
	private String date;
	private String musical;
	private String seat;
	
	public PayVO(TextField price, TextField date, TextField musical, TextField seat) {}
	
	public PayVO(String name, String price, String date, String musical, String seat) {
		super();
		this.name = name;
		this.price = price;
		this.date = date;
		this.musical = musical;
		this.seat = seat;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	
	public String getMusical() {
		return musical;
	}


	public void setMusical(String musical) {
		this.musical = musical;
	}


	public String getSeat() {
		return seat;
	}


	public void setSeat(String seat) {
		this.seat = seat;
	}

	@Override
	public String toString() {
		return "PayVO [name=" + name + ", price=" + price + ", date=" + date + ", musical=" + musical + ", seat=" + seat
				+ "]";
	}

}
