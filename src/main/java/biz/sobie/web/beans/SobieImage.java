package biz.sobie.web.beans;

import java.sql.Blob;

import org.zkoss.image.AImage;

public class SobieImage {
	
	private String imgId;
	private String imgFilename;
	private String imgType;
	private String imgNotes;
	private Blob image;
	private String primaryImage;
	private String imageAlbum;
	private byte[] imageInBytes;
	
	private String prodId;
	private String storeNo;
	private String servId;
	private String storeContentPageId;
	
	private boolean updateImage;

	public SobieImage() {
		this.updateImage = false;
	}
	
	public AImage getAImage() {
		try {
			AImage aImage;
			if(getImage() == null){
				aImage = new AImage(getImgFilename(), getImageInBytes());
			} else {
				aImage = new AImage(getImgId(), getImage().getBinaryStream());
			}
			return aImage;

		} catch (Exception e) {
			return null;
		
		}
	}
	
	public Blob getImage() {
		return image;
	}

	public String getImageAlbum() {
		return imageAlbum;
	}

	public String getImgFilename() {
		return imgFilename;
	}

	public String getImgId() {
		return imgId;
	}
	
	public String getImgNotes() {
		return imgNotes;
	}

	public String getImgType() {
		return imgType;
	}

	public String getPrimaryImage() {
		return primaryImage;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public void setImageAlbum(String imageAlbum) {
		this.imageAlbum = imageAlbum;
	}

	public void setImgFilename(String imgFilename) {
		this.imgFilename = imgFilename;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public void setImgNotes(String imgNotes) {
		this.imgNotes = imgNotes;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public void setPrimaryImage(String primaryImage) {
		this.primaryImage = primaryImage;
	}

	public void setImageInBytes(byte[] imageInBytes) {
		this.imageInBytes = imageInBytes;
	}

	public byte[] getImageInBytes() {
		return imageInBytes;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdId() {
		return prodId;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getStoreContentPageId() {
		return storeContentPageId;
	}

	public void setStoreContentPageId(String storeContentPageId) {
		this.storeContentPageId = storeContentPageId;
	}

	public boolean isUpdateImage() {
		return updateImage;
	}

	public void setUpdateImage(boolean updateImage) {
		this.updateImage = updateImage;
	}

	public String getServId() {
		return servId;
	}

	public void setServId(String servId) {
		this.servId = servId;
	}
}
