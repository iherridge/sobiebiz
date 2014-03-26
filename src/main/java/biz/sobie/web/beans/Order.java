package biz.sobie.web.beans;

public class Order {

	private String orderNo;
	private String buyerAccNo;
	private String sellerAccNo;
	private String supplierAccNo;
	private String shipperAccNo;
	private String tranType;
	private int orderDate;
	private int orderTime;
	private String prodId;
	private int prodQty;
	private Product product;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public int getProdQty() {
		return prodQty;
	}

	public void setProdQty(int prodQty) {
		this.prodQty = prodQty;
	}

	public String getBuyerAccNo() {
		return buyerAccNo;
	}

	public void setBuyerAccNo(String buyerAccNo) {
		this.buyerAccNo = buyerAccNo;
	}

	public String getSellerAccNo() {
		return sellerAccNo;
	}

	public void setSellerAccNo(String sellerAccNo) {
		this.sellerAccNo = sellerAccNo;
	}

	public String getSupplierAccNo() {
		return supplierAccNo;
	}

	public void setSupplierAccNo(String supplierAccNo) {
		this.supplierAccNo = supplierAccNo;
	}

	public String getShipperAccNo() {
		return shipperAccNo;
	}

	public void setShipperAccNo(String shipperAccNo) {
		this.shipperAccNo = shipperAccNo;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public int getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(int orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(int orderTime) {
		this.orderTime = orderTime;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

}
