package biz.sobie.web.beans;

import java.io.IOException;
import java.sql.SQLException;

import org.zkoss.image.AImage;

import com.mysql.jdbc.Blob;

public class ProfilePicture {

	private String imgId;
	private String custNo;
	private String accNo;
	private String imgAlbum;
	private byte[] imgInBytes;
	private String imgFilename;
	private String imgType;
	private String imgNotes;
	
	public ProfilePicture() {
		// TODO Auto-generated constructor stub
	}
	
	public ProfilePicture(String imgId, String custNo, String accNo, String imgAlbum, Blob imgInBlob, String imgFilename, String imgType, String imgNotes) throws IOException, SQLException {
		this.imgId = imgId;
		this.custNo = custNo;
		this.accNo = accNo;
		this.imgAlbum = imgAlbum; 
		AImage aImage =  new AImage("profilePicture", imgInBlob.getBinaryStream());
		this.imgInBytes = aImage.getByteData();
		this.imgFilename = imgFilename;
		this.imgType = imgType;
		this.imgNotes = imgNotes;
	}
	
	public String getImgNotes() {
		return imgNotes;
	}

	public void setImgNotes(String imgNotes) {
		this.imgNotes = imgNotes;
	}

	public String getImgAlbum() {
		return imgAlbum;
	}

	public void setImgAlbum(String imgAlbum) {
		this.imgAlbum = imgAlbum;
	}

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
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

	public byte[] getImgInBytes() {
		return imgInBytes;
	}

	public void setImgInBytes(byte[] imgInBytes) {
		this.imgInBytes = imgInBytes;
	}
	
	public String getImgFilename() {
		return imgFilename;
	}

	public void setImgFilename(String imgFilename) {
		this.imgFilename = imgFilename;
	}
	
}
