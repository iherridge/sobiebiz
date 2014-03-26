package biz.sobie.web.userdashboard.inventory;

import java.sql.Date;


public class ProductHistory {

	private String prodId;
	private Date dateTime;
	private String fieldName;
	private String fieldValueFrom;
	private String fieldValueTo;

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValueFrom() {
		return fieldValueFrom;
	}

	public void setFieldValueFrom(String fieldValueFrom) {
		this.fieldValueFrom = fieldValueFrom;
	}

	public String getFieldValueTo() {
		return fieldValueTo;
	}

	public void setFieldValueTo(String fieldValueTo) {
		this.fieldValueTo = fieldValueTo;
	}

}
