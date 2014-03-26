package biz.sobie.web.beans;

public class DeliveryDetails {
	
	private boolean deliveryDetailsEntered;
	private int deliveryOption;
	private String addressLine1;
	private String addressLine2;
	private String suburb;
	private String city;
	private String country;
	private int code;
	
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getSuburb() {
		return suburb;
	}
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	public void setDeliveryDetailsEntered(boolean deliveryDetailsEntered) {
		this.deliveryDetailsEntered = deliveryDetailsEntered;
	}
	public boolean isDeliveryDetailsEntered() {
		return deliveryDetailsEntered;
	}
	public void setDeliveryOption(int deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	public int getDeliveryOption() {
		return deliveryOption;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}
}
