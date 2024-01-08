package pay;

public class PayVO {
	private String user;
	private String price;
	private String date;
	private String musical;
	private String seat;
	
	public PayVO(String user, String price, String date, String musical, String seat) {
		super();
		this.user = user;
		this.price = price;
		this.date = date;
		this.musical = musical;
		this.seat = seat;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
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
		return "PayVO [user=" + user + ", price=" + price + ", date=" + date + ", musical=" + musical + ", seat=" + seat
				+ "]";
	}
	
	
}
