package biz.sobie.web.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SobieUtils {

	public String createCustomerNumber() {
		
		String customerNumber = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
		customerNumber = "SBE" + dateFormat.format(new Date()) + "001";
		return customerNumber;
	}
	
	public String createImageId() {
		
		String imageId = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
		imageId = dateFormat.format(new Date()) + "0001";
		return imageId;
	}
	
	public String createMsgbxId() {
		
		String msgbxId = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
		msgbxId = dateFormat.format(new Date()) + "0001";
		return msgbxId;
	}
	
	public String createMsgId() {
		
		String msgId = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
		msgId = dateFormat.format(new Date()) + "0001";
		return msgId;
	}

	public String createProductId() {
		String prodId = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
		prodId = dateFormat.format(new Date()) + "01";
		return prodId;
	}
	
	public String createServiceId() {
		String servId = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
		servId = dateFormat.format(new Date()) + "01";
		return servId;
	}
	
	public String createAccNo() {
		String accNo = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
		accNo = "SBENAM" + dateFormat.format(new Date()) + "001";
		return accNo;
	}
	
	public String createStoreNo() {
		String storeNo = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
		storeNo = "STORE" + dateFormat.format(new Date()) + "01";
		return storeNo;
	}
	
	public String createShoppingCartNo() {
		String shoppingCartNo = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
		shoppingCartNo = dateFormat.format(new Date()) + "01";
		return shoppingCartNo;
	}

	public String createOrderNo() {
		String orderNo = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
		orderNo = dateFormat.format(new Date()) + "01";
		return orderNo;
	}

	public String createOrderInventoryNo() {
		String orderInventoryNo = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
		orderInventoryNo = dateFormat.format(new Date()) + "01";
		return orderInventoryNo;
	}

	public String createProdHistoryId() {
		String prodHistId = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
		prodHistId = dateFormat.format(new Date()) + "01";
		return prodHistId;
	}

	public String createStoreContentPageId() {
		String storeContentPageId = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
		storeContentPageId = dateFormat.format(new Date()) + "01";
		return storeContentPageId;
	}

}
