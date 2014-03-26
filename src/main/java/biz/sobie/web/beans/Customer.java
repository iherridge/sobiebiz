package biz.sobie.web.beans;

import java.io.Serializable;

import org.springframework.context.annotation.DependsOn;

public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	private String custNo;
	private String custEmail;
	private String custFirstName;
	private String custGender;
	private String custIdNo;
	private String custLastName;
	private String custPassword;
	@Deprecated
	private String custProductType;
	private String custRetypePassword;
	private int custDOB;
	private SobieImage profileImage;
	
	public Customer() {
		// TODO Auto-generated constructor stub
		profileImage = new SobieImage();
	}
	
	/**
	 * Create Customer object. Maintly used when retieving customer information from the database
	 * @param custDateOfBirth
	 * @param custEmail
	 * @param custFirstName
	 * @param custGender
	 * @param custIdNo
	 * @param custLastName
	 * @param custNumber
	 */
	public Customer(int custDOB, String custEmail, String custFirstName, String custGender,
			 String custIdNo, String custLastName, String custNo) {
		 
		this.custDOB = custDOB;
		this.custEmail = custEmail;
		this.custFirstName = custFirstName;
		this.custGender = custGender;
		this.custIdNo = custIdNo;
		this.custLastName = custLastName;
		this.custNo = custNo;
	}
	
	@SuppressWarnings("deprecation")
	public int getCustDOB() {
		return custDOB;
	}

	public String getCustEmail() {
		return custEmail;
	}

	public String getCustFirstName() {
		return custFirstName;
	}

	public String getCustGender() {
		return custGender;
	}


	public String getCustIdNo() {
		return custIdNo;
	}


	public String getCustLastName() {
		return custLastName;
	}

	public String getCustNo() {
		return custNo;
	}

	public String getCustPassword() {
		return custPassword;
	}

	public String getCustProductType() {
		return custProductType;
	}

	public String getCustRetypePassword() {
		return custRetypePassword;
	}

	public void setCustDOB(int custDOB) {
		this.custDOB = custDOB;
	}

	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}

	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public void setCustGender(String custGender) {
		this.custGender = custGender;
	}

	public void setCustIdNo(String custIdNo) {
		this.custIdNo = custIdNo;
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public void setCustPassword(String custPassword) {
		this.custPassword = custPassword;
	}

	public void setCustProductType(String custProductType) {
		this.custProductType = custProductType;
	}

	public void setCustRetypePassword(String custRetypePassword) {
		this.custRetypePassword = custRetypePassword;
	}

	public boolean validatePassword() {
		if(getCustPassword().equals(getCustRetypePassword())) {
			return true;
		} else return false;
	}

    @DependsOn({ "custFirstName", "custLastName" })
    public String getFullName() {
        return getCustFirstName() + " " + getCustLastName();
    }

	public SobieImage getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(SobieImage profileImage) {
		this.profileImage = profileImage;
	}
}
