package model;

public class CustomMessage {
	
	private User author;
	private String message;
	private User reciever;
	
	public CustomMessage() {
		super();
	}
	public CustomMessage(User author, String message, User reciever) {
		super();
		this.author = author;
		this.message = message;
		this.reciever=reciever;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User getReciever() {
		return reciever;
	}
	public void setReciever(User reciever) {
		this.reciever = reciever;
	}
	
	

}
