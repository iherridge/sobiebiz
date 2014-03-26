package biz.sobie.web.beans;

import java.util.List;

public class SobieProfile {

	private Customer customer;
	private Account account;
	private ProfilePicture profilePicture;
	private DeliveryDetails deliveryDetails;
	private PaymentDetails paymentDetails;
	private List<Order> order;
	private List<IpAddress> ipAdresses;
	private boolean loggedIn;

	public ProfilePicture getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(ProfilePicture profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setDeliveryDetails(DeliveryDetails deliveryDetails) {
		this.deliveryDetails = deliveryDetails;
	}

	public DeliveryDetails getDeliveryDetails() {
		return deliveryDetails;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setIpAdresses(List<IpAddress> ipAdresses) {
		this.ipAdresses = ipAdresses;
	}

	public List<IpAddress> getIpAdresses() {
		return ipAdresses;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

}
