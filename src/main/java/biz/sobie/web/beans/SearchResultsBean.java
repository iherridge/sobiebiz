package biz.sobie.web.beans;

import java.io.IOException;
import java.sql.SQLException;

import com.mysql.jdbc.Blob;

public class SearchResultsBean {

	private String id;
	private String accNo;
	private Blob imgInBytes;
	private String imgType;
	private String label;
	
	public SearchResultsBean() {}
	
	public SearchResultsBean(String id, String accNo, Blob imgInBlob, String imgType, String label) throws IOException, SQLException {
		this.id = id;
		this.accNo = accNo;
		this.imgInBytes = imgInBlob;
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public Blob getImgInBytes() {
		return imgInBytes;
	}

	public void setImgInBytes(Blob imgInBytes) {
		this.imgInBytes = imgInBytes;
	}
}
