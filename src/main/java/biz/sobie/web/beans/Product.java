package biz.sobie.web.beans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.image.AImage;


public class Product {

	/**
	 * TODO: Check whether this is required, since we have a sobieProfile object that is
	 * carried inside the session.
	 * DB: Customer 
	 */
	private String custNumber;
	
	/**
	 * DB: Store_inventory
	 */
	private String sellerStoreNo;
	private String sellerAccNo;
	private char ownedItem;
	private String ownerStoreNo;
	private String ownerAccNo;

	/**
	 * DB: Product
	 */
	private String prodId;
	private String prodName;
	private String prodSkuPartNo;
	private String prodCategory;
	private boolean prodEnabled;
	private double prodRetailPrice;
	private double prodSalePrice;
	private double prodWholesalePrice;
	private String taxClassId;
	private boolean prodAddToCart;
	private int prodQtyImedAvail;
	private int prodMinPurchaseQty;
	private int prodMaxPurchaseQty;
	private boolean prodFreeShipping;
	private boolean prodShipSeperate;
	private String prodShippingTypeId;
	private double prodWeight;
	private double prodHeight;
	private double prodWidth;
	private double prodLenght;
	private boolean prodShippingEnabled;
	private String prodDesc;
	private String prodFeatures;
	private String prodSpecification;
	private String prodShippingInfo;
	private String prodYouTubeUrl;
	private boolean prodCommentsEnabled;
	private boolean prodQnaEnabled;
	private String prodBrand;
	
	/**
	 * Store_inventory
	 */
	private String prodStatus;
	private String ownerProdId;
	private boolean prodFeatured;
	
	
	/**
	 * Derived fields
	 */
	private String prodStatusName;
	private String prodStatusImage;
	private String prodStatusLinkName; //Inventory Products
	private String prodStatusNetworkLinkName; //Inventory Network Products
	private String prodInStockImage;
	private int prodLikes;
	
	private ArrayList<SobieImage> imageCatalog;
	private SobieImage productImage;
	
	public double getProdSize(){
		double l, w, h, s;
		if(getProdLenght() == 0){
			l = 1;
		} else {
			l = getProdLenght();
		}
		if(getProdWidth() == 0){
			w = 1;
		} else {
			w = getProdWidth();
		}
		if(getProdHeight() == 0){
			h = 1;
		} else {
			h = getProdHeight();
		}
		s =  l * h * w;
		return s;
	}
	
	public AImage getPrimaryImage(){
		for(int x = 0; x < getImageCatalog().size(); x++){
			if(getImageCatalog().get(x).getPrimaryImage().equals("Y")){
				try {
					if(getImageCatalog().get(x).getImageInBytes() == null){
						try {
							return new AImage(getImageCatalog().get(x).getImgFilename(), getImageCatalog().get(x).getImage().getBinaryStream());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						return new AImage(getImageCatalog().get(x).getImgFilename(), getImageCatalog().get(x).getImageInBytes());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
		 
	}
	
	public Product() {
		imageCatalog = new ArrayList<SobieImage>();
	}

	public String getCustNumber() {
		return custNumber;
	}

	public ArrayList<SobieImage> getImageCatalog() {
		return imageCatalog;
	}

	public char getOwnedItem() {
		return ownedItem;
	}

	public String getOwnerAccNo() {
		return ownerAccNo;
	}
	
	public String getOwnerStoreNo() {
		return ownerStoreNo;
	}

	public String getProdBrand() {
		return prodBrand;
	}

	public String getProdCategory() {
		return prodCategory;
	}

	public String getProdDesc() {
		return prodDesc;
	}

	public String getProdFeatures() {
		return prodFeatures;
	}

	public double getProdHeight() {
		return prodHeight;
	}

	public String getProdId() {
		return prodId;
	}

	public double getProdLenght() {
		return prodLenght;
	}

	public int getProdMaxPurchaseQty() {
		return prodMaxPurchaseQty;
	}

	public int getProdMinPurchaseQty() {
		return prodMinPurchaseQty;
	}

	public String getProdName() {
		return prodName;
	}

	public int getProdQtyImedAvail() {
		return prodQtyImedAvail;
	}

	public double getProdRetailPrice() {
		return prodRetailPrice;
	}

	public double getProdSalePrice() {
		return prodSalePrice;
	}

	public String getProdShippingInfo() {
		return prodShippingInfo;
	}
	
	public String getProdShippingTypeId() {
		return prodShippingTypeId;
	}

	public String getProdSkuPartNo() {
		return prodSkuPartNo;
	}

	public String getProdSpecification() {
		return prodSpecification;
	}

	public double getProdWholesalePrice() {
		return prodWholesalePrice;
	}

	public double getProdWidth() {
		return prodWidth;
	}

	public double getProdWeight() {
		return prodWeight;
	}

	public String getProdYouTubeUrl() {
		return prodYouTubeUrl;
	}

	public String getSellerAccNo() {
		return sellerAccNo;
	}

	public String getSellerStoreNo() {
		return sellerStoreNo;
	}

	public String getTaxClassId() {
		return taxClassId;
	}

	public boolean isProdAddToCart() {
		return prodAddToCart;
	}

	public boolean isProdCommentsEnabled() {
		return prodCommentsEnabled;
	}

	public boolean isProdFreeShipping() {
		return prodFreeShipping;
	}

	public boolean isProdQnaEnabled() {
		return prodQnaEnabled;
	}

	public boolean isProdShippingEnabled() {
		return prodShippingEnabled;
	}

	public boolean isProdShipSeperate() {
		return prodShipSeperate;
	}

	public void setCustNumber(String custNumber) {
		this.custNumber = custNumber;
	}

	public void setImageCatalog(ArrayList<SobieImage> imageCatalog) {
		this.imageCatalog = imageCatalog;
	}

	public void setOwnedItem(char ownedItem) {
		this.ownedItem = ownedItem;
	}

	public void setOwnerAccNo(String ownerAccNo) {
		this.ownerAccNo = ownerAccNo;
	}

	public void setOwnerStoreNo(String ownerStoreNo) {
		this.ownerStoreNo = ownerStoreNo;
	}

	public void setProdAddToCart(boolean prodAddToCart) {
		this.prodAddToCart = prodAddToCart;
	}

	public void setProdBrand(String prodBrand) {
		this.prodBrand = prodBrand;
	}

	public void setProdCategory(String prodCategory) {
		this.prodCategory = prodCategory;
	}

	public void setProdCommentsEnabled(boolean prodCommentsEnabled) {
		this.prodCommentsEnabled = prodCommentsEnabled;
	}

	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}

	public void setProdFeatures(String prodFeatures) {
		this.prodFeatures = prodFeatures;
	}

	public void setProdFreeShipping(boolean prodFreeShipping) {
		this.prodFreeShipping = prodFreeShipping;
	}

	public void setProdHeight(double prodHeight) {
		this.prodHeight = prodHeight;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public void setProdLenght(double prodLenght) {
		this.prodLenght = prodLenght;
	}

	public void setProdMaxPurchaseQty(int prodMaxPurchaseQty) {
		this.prodMaxPurchaseQty = prodMaxPurchaseQty;
	}

	public void setProdMinPurchaseQty(int prodMinPurchaseQty) {
		this.prodMinPurchaseQty = prodMinPurchaseQty;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public void setProdQnaEnabled(boolean prodQnaEnabled) {
		this.prodQnaEnabled = prodQnaEnabled;
	}

	public void setProdQtyImedAvail(int prodQtyImedAvail) {
		this.prodQtyImedAvail = prodQtyImedAvail;
	}

	public void setProdRetailPrice(double prodRetailPrice) {
		this.prodRetailPrice = prodRetailPrice;
	}

	public void setProdSalePrice(double prodSalePrice) {
		this.prodSalePrice = prodSalePrice;
	}

	public void setProdShippingEnabled(boolean prodShippingEnabled) {
		this.prodShippingEnabled = prodShippingEnabled;
	}

	public void setProdShippingInfo(String prodShippingInfo) {
		this.prodShippingInfo = prodShippingInfo;
	}

	public void setProdShippingTypeId(String prodShippingTypeId) {
		this.prodShippingTypeId = prodShippingTypeId;
	}

	public void setProdShipSeperate(boolean prodShipSeperate) {
		this.prodShipSeperate = prodShipSeperate;
	}

	public void setProdSkuPartNo(String prodSkuPartNo) {
		this.prodSkuPartNo = prodSkuPartNo;
	}

	public void setProdSpecification(String prodSpecification) {
		this.prodSpecification = prodSpecification;
	}

	public void setProdWholesalePrice(double prodWholesalePrice) {
		this.prodWholesalePrice = prodWholesalePrice;
	}

	public void setProdWidth(double prodWidth) {
		this.prodWidth = prodWidth;
	}

	public void setProdWeight(double prodWeight) {
		this.prodWeight = prodWeight;
	}

	public void setProdYouTubeUrl(String prodYouTubeUrl) {
		this.prodYouTubeUrl = prodYouTubeUrl;
	}

	public void setSellerAccNo(String sellerAccNo) {
		this.sellerAccNo = sellerAccNo;
	}

	public void setSellerStoreNo(String sellerStoreNo) {
		this.sellerStoreNo = sellerStoreNo;
	}

	public void setTaxClassId(String taxClassId) {
		this.taxClassId = taxClassId;
	}

	public void setProdEnabled(boolean prodEnabled) {
		this.prodEnabled = prodEnabled;
	}

	public boolean isProdEnabled() {
		return prodEnabled;
	}

	public String getInStock(){
		if(getProdQtyImedAvail() > 0){
			return "Yes";
		} else {
			return "No";
		}
		
	}

	public String getProdStatus() {
		return prodStatus;
	}

	public void setProdStatus(String prodStatus) {
		this.prodStatus = prodStatus;
	}

	public String getProdStatusImage() {
		if(getProdStatus().equals("0")){ //Red: Inactive
			prodStatusImage = "resources/img/defaults/inactive.png";
		} else if(getProdStatus().equals("1")){	//Green: Active
			prodStatusImage = "resources/img/defaults/active.png";
		} else if(getProdStatus().equals("2")){	//Yellow: Pending Approval
			prodStatusImage = "resources/img/defaults/pendingApproval.png";
		} else if(getProdStatus().equals("3")){ //Blue: Request for more Information
			prodStatusImage = "resources/img/defaults/requestInformation.png";
		} else if(getProdStatus().equals("4")){ //Orange: Change Request
			prodStatusImage = "resources/img/defaults/changeRequest.png";
		}
		return prodStatusImage;
	}

	public String getProdStatusNetworkLinkName() {
		if(getProdStatus().equals("1")){ //Active
			prodStatusNetworkLinkName = "deactivate";
		} else if(getProdStatus().equals("0")){ //Inactive
			prodStatusNetworkLinkName = "activate"; //Pending Approval state
		} else if(getProdStatus().equals("2")){ //Pending Approval
			prodStatusNetworkLinkName = "";
		} else if(getProdStatus().equals("3")){ //Request for more Information
			prodStatusNetworkLinkName = "";
		} else if(getProdStatus().equals("4")){ //Change Request
			prodStatusNetworkLinkName = "";
			}
		return prodStatusNetworkLinkName;
	}

	public String getProdInStockImage() {
		if(getProdQtyImedAvail() > 0){
			prodInStockImage = "resources/img/defaults/active.png";
		} else {
			prodInStockImage = "resources/img/defaults/inactive.png";
		}
		
		return prodInStockImage;
	}

	public String getProdStatusName() {
		
		if(getProdStatus().equals("0")){ //Red: Inactive
			prodStatusName = "Product is Inactive";
		} else if(getProdStatus().equals("1")){	//Green: Active
			prodStatusName = "Product is Approved and Active";
		} else if(getProdStatus().equals("2")){	//Yellow: Pending Approval
			prodStatusName = "Product has been send for Approval";
		} else if(getProdStatus().equals("3")){ //Blue: Request for more Information
			prodStatusName = "Supplier requested for more information";
		} else if(getProdStatus().equals("4")){ //Orange: Change Request
			prodStatusName = "Supplier requesting a change";
		}
		return prodStatusName;
	}

	public String getProdStatusLinkName() {
		/**
		 * Switch the status from active to deactive or vice versa
		 */
		if(getProdStatus().equals("1")){ //Active
			prodStatusLinkName = "deactivate";
		} else { //Inactive
			prodStatusLinkName = "activate";
		} 
		return prodStatusLinkName;
	}

	public String getOwnerProdId() {
		return ownerProdId;
	}

	public void setOwnerProdId(String ownerProdId) {
		this.ownerProdId = ownerProdId;
	}

	public boolean isProdFeatured() {
		return prodFeatured;
	}

	public void setProdFeatured(boolean prodFeatured) {
		this.prodFeatured = prodFeatured;
	}

	public SobieImage getProductImage() {
		return productImage;
	}

	public void setProductImage(SobieImage productImage) {
		this.productImage = productImage;
	}

	public int getProdLikes() {
		return prodLikes;
	}

	public void setProdLikes(int prodLikes) {
		this.prodLikes = prodLikes;
	}

}
