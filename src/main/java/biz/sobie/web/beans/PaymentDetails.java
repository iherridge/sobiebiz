package biz.sobie.web.beans;

public class PaymentDetails {
	
	private boolean paymentDetailsEntered;
	private String paymentOption;
	private String accountHolderName;
	private String paymentType;
	private String cardNumber;
	private String csv;
	private String cardExpiryMonth;
	private String cardExpiryYear;
	
	
	public String getAccountHolderName() {
		return accountHolderName;
	}
	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCsv() {
		return csv;
	}
	public void setCsv(String csv) {
		this.csv = csv;
	}
	public String getCardExpiryMonth() {
		return cardExpiryMonth;
	}
	public void setCardExpiryMonth(String cardExpiryMonth) {
		this.cardExpiryMonth = cardExpiryMonth;
	}
	public String getCardExpiryYear() {
		return cardExpiryYear;
	}
	public void setCardExpiryYear(String cardExpiryYear) {
		this.cardExpiryYear = cardExpiryYear;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentDetailsEntered(boolean paymentDetailsEntered) {
		this.paymentDetailsEntered = paymentDetailsEntered;
	}
	public boolean isPaymentDetailsEntered() {
		return paymentDetailsEntered;
	}
	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}
	public String getPaymentOption() {
		return paymentOption;
	}
	
	
}
