package biz.sobie.web.beans;

import java.sql.Blob;
import java.util.List;

import org.zkoss.image.AImage;

public class Store implements Cloneable{

	/**
	 * DB: Retrieves store details
	 */
	private String storeName;
	private String storeAccNo;
	private String storeNo;
	private String storeDesc;
	private String storeTel;
	private String storeFax;
	private String storeAdminEmail;
	private String storeSalesDepEmail;
	private String storeDepTel;
	private String storeCustServEmail;
	private String storeCustServTel;
	private String storeCustCommentStatus;
	private boolean storeIncludeTaxInProductPrice;
	private boolean storeEnableAddressValidation; 
	private boolean storeEnableFriendlyUrls;
	private String storeFacebookUrl;
	private String storeTwitterUrl;
	private String storeSkypeName;
	private String storeGoogleUrl;
	private String storeStumbleUponUrl;
	private String storeAboutUs;
	
	/**
	 * Location
	 */
	
	private double longitude;
	private double latitude;
	private String country;
	private String cityTown;
	private String street;
	private boolean locationEnabled;
	
	/**
	 * Store Logo
	 */
	
	private Blob storeImage;
	private SobieImage storeLogo;
	
	/**
	 * Store Index Main Header
	 */
	private SobieImage storeIndexMainHeaderImage;
	
	/**
	 * Strore products
	 */
	private List<Product> productList;
	private List<Product> featuredProductList;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public AImage getAImage() {
		try {
			AImage aImage = new AImage(getStoreName(), getStoreImage().getBinaryStream());
			return aImage;

		} catch (Exception e) {
			return null;
		
		}
	}

	public List<Product> getProductList() {
		return productList;
	}

	public String getStoreAboutUs() {
		return storeAboutUs;
	}

	public String getStoreAccNo() {
		return storeAccNo;
	}

	public String getStoreAdminEmail() {
		return storeAdminEmail;
	}

	public String getStoreCustCommentStatus() {
		return storeCustCommentStatus;
	}

	public String getStoreCustServEmail() {
		return storeCustServEmail;
	}

	public String getStoreCustServTel() {
		return storeCustServTel;
	}

	public String getStoreDepTel() {
		return storeDepTel;
	}

	public String getStoreDesc() {
		return storeDesc;
	}

	public String getStoreFacebookUrl() {
		return storeFacebookUrl;
	}

	public String getStoreFax() {
		return storeFax;
	}

	public String getStoreGoogleUrl() {
		return storeGoogleUrl;
	}

	public SobieImage getStoreLogo() {
		return storeLogo;
	}

	public String getStoreName() {
		return storeName;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public String getStoreSalesDepEmail() {
		return storeSalesDepEmail;
	}

	public String getStoreSkypeName() {
		return storeSkypeName;
	}

	public String getStoreStumbleUponUrl() {
		return storeStumbleUponUrl;
	}

	public String getStoreTel() {
		return storeTel;
	}

	public String getStoreTwitterUrl() {
		return storeTwitterUrl;
	}

	public boolean isStoreEnableAddressValidation() {
		return storeEnableAddressValidation;
	}

	public boolean isStoreEnableFriendlyUrls() {
		return storeEnableFriendlyUrls;
	}

	public boolean isStoreIncludeTaxInProductPrice() {
		return storeIncludeTaxInProductPrice;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public void setStoreAboutUs(String storeAboutUs) {
		this.storeAboutUs = storeAboutUs;
	}

	public void setStoreAccNo(String storeAccNo) {
		this.storeAccNo = storeAccNo;
	}

	public void setStoreAdminEmail(String storeAdminEmail) {
		this.storeAdminEmail = storeAdminEmail;
	}

	public void setStoreCustCommentStatus(String storeCustCommentStatus) {
		this.storeCustCommentStatus = storeCustCommentStatus;
	}

	public void setStoreCustServEmail(String storeCustServEmail) {
		this.storeCustServEmail = storeCustServEmail;
	}

	public void setStoreCustServTel(String storeCustServTel) {
		this.storeCustServTel = storeCustServTel;
	}

	public void setStoreDepTel(String storeDepTel) {
		this.storeDepTel = storeDepTel;
	}

	public void setStoreDesc(String storeDesc) {
		this.storeDesc = storeDesc;
	}

	public void setStoreEnableAddressValidation(boolean storeEnableAddressValidation) {
		this.storeEnableAddressValidation = storeEnableAddressValidation;
	}

	public void setStoreEnableFriendlyUrls(boolean storeEnableFriendlyUrls) {
		this.storeEnableFriendlyUrls = storeEnableFriendlyUrls;
	}

	public void setStoreFacebookUrl(String storeFacebookUrl) {
		this.storeFacebookUrl = storeFacebookUrl;
	}

	public void setStoreFax(String storeFax) {
		this.storeFax = storeFax;
	}

	public void setStoreGoogleUrl(String storeGoogleUrl) {
		this.storeGoogleUrl = storeGoogleUrl;
	}

	public void setStoreIncludeTaxInProductPrice(
			boolean storeIncludeTaxInProductPrice) {
		this.storeIncludeTaxInProductPrice = storeIncludeTaxInProductPrice;
	}

	public void setStoreLogo(SobieImage storeLogo) {
		this.storeLogo = storeLogo;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public void setStoreImage(Blob storeImage) {
		this.storeImage= storeImage;
	}

	public Blob getStoreImage() {
		return storeImage;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public void setStoreSalesDepEmail(String storeSalesDepEmail) {
		this.storeSalesDepEmail = storeSalesDepEmail;
	}

	public void setStoreSkypeName(String storeSkypeName) {
		this.storeSkypeName = storeSkypeName;
	}

	public void setStoreStumbleUponUrl(String storeStumbleUponUrl) {
		this.storeStumbleUponUrl = storeStumbleUponUrl;
	}

	public void setStoreTel(String storeTel) {
		this.storeTel = storeTel;
	}

	public void setStoreTwitterUrl(String storeTwitterUrl) {
		this.storeTwitterUrl = storeTwitterUrl;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCityTown() {
		return cityTown;
	}

	public void setCityTown(String cityTown) {
		this.cityTown = cityTown;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public boolean isLocationEnabled() {
		return locationEnabled;
	}

	public void setLocationEnabled(boolean locationEnabled) {
		this.locationEnabled = locationEnabled;
	}

	public SobieImage getStoreIndexMainHeaderImage() {
		return storeIndexMainHeaderImage;
	}

	public void setStoreIndexMainHeaderImage(SobieImage storeIndexMainHeaderImage) {
		this.storeIndexMainHeaderImage = storeIndexMainHeaderImage;
	}

	public List<Product> getFeaturedProductList() {
		return featuredProductList;
	}

	public void setFeaturedProductList(List<Product> featuredProductList) {
		this.featuredProductList = featuredProductList;
	}
}
