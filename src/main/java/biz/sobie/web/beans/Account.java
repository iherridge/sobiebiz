package biz.sobie.web.beans;

public class Account {

	/**
	 * DB: Account
	 */
	private String accNo;
	private String accName;
	private String ppId;
	
	/**
	 * DB: Store
	 */
	private String storeNo;
	
	/**
	 * DB: Shopping_cart
	 */
	private String shoppingCartNo;
	
	public Account(String accNo,  String ppId, String accName, String shoppingCartNo, String storeNo) {
		this.accNo = accNo;
		this.accName = accName;
		this.ppId = ppId;
		this.shoppingCartNo = shoppingCartNo;
		this.storeNo = storeNo;
	}
	
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}

	public String getShoppingCartNo() {
		return shoppingCartNo;
	}

	public void setShoppingCartNo(String shoppingCartNo) {
		this.shoppingCartNo = shoppingCartNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getStoreNo() {
		return storeNo;
	}
	
	public String getAccountType(){
		String accountType = getPpId();
		if(accountType.startsWith("BU")){
			accountType = "Buyer";
		} else if(accountType.startsWith("SE")){
			accountType = "Seller";
		} else if(accountType.startsWith("SU")){
			accountType = "Shipper";
		}
		return accountType;
	}
	
	public String getAccountPackage(){
		String accountType = getPpId();
		if(accountType.equals("BU0")){
			accountType = "Anonymous";
		} else if(accountType.equals("BU1")){
			accountType = "Free";
		} else if(accountType.equals("BU2")){
			accountType = "Basic";
		} else if(accountType.equals("BU3")){
			accountType = "Novice";
		} else if(accountType.equals("BU4")){
			accountType = "Pro";
		} else if(accountType.equals("SE1")){
			accountType = "Free";
		} else if(accountType.equals("SE2")){
			accountType = "Novice";
		} else if(accountType.equals("SE3")){
			accountType = "Pro";
		} else if(accountType.equals("SU1")){
			accountType = "Basic";
		} else if(accountType.equals("SU2")){
			accountType = "Novice";
		} else if(accountType.equals("SU3")){
			accountType = "Pro";
		}
		return accountType;
	}
}
