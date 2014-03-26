package biz.sobie.web.beans;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import org.zkoss.image.AImage;

public class Service{

	private String servId;
	private String servName;
	private String servRef;
	private String servCategory;
	private boolean servEnabled;
	private boolean servFeatured;
	private double servFixedPrice;
	private String servFixedRateType;
	private double servFixedRateUnit;
	private String servTaxClassId;
	private boolean servQuatationReqEnabled;
	private boolean servOhDaysMon;
	private boolean servOhDaysTue;
	private boolean servOhDaysWed;
	private boolean servOhDaysThu;
	private boolean servOhDaysFri;
	private boolean servOhDaysSat;
	private boolean servOhDaysSun;
	private Time servStartTime;
	private Time servEndTime;
	private String servYoutubeUrl;
	private String servAltText;
	private boolean servCommentsEnabled;
	private boolean servQnaEnabled;
	private String servDesc;
	private String servFeatures;
	private String servSpecification;
	private SobieImage servImage;
	
	private ArrayList<SobieImage> imageCatalog;
	private SobieImage serviceImage;
	
	@SuppressWarnings("deprecation")
	public Service() {
		this.servStartTime = new Time(0, 0, 0);
		this.servEndTime = new Time(0, 0, 0);
		this.imageCatalog = new ArrayList<SobieImage>();
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

	public boolean isServOhDaysTue() {
		return servOhDaysTue;
	}

	public void setServOhDaysTue(boolean servOhDaysTue) {
		this.servOhDaysTue = servOhDaysTue;
	}

	public boolean isServOhDaysWed() {
		return servOhDaysWed;
	}

	public void setServOhDaysWed(boolean servOhDaysWed) {
		this.servOhDaysWed = servOhDaysWed;
	}

	public boolean isServOhDaysThu() {
		return servOhDaysThu;
	}

	public void setServOhDaysThu(boolean servOhDaysThu) {
		this.servOhDaysThu = servOhDaysThu;
	}

	public boolean isServOhDaysFri() {
		return servOhDaysFri;
	}

	public void setServOhDaysFri(boolean servOhDaysFri) {
		this.servOhDaysFri = servOhDaysFri;
	}

	public boolean isServOhDaysSat() {
		return servOhDaysSat;
	}

	public void setServOhDaysSat(boolean servOhDaysSat) {
		this.servOhDaysSat = servOhDaysSat;
	}

	public boolean isServOhDaysSun() {
		return servOhDaysSun;
	}

	public void setServOhDaysSun(boolean servOhDaysSun) {
		this.servOhDaysSun = servOhDaysSun;
	}

	public String getServId() {
		return servId;
	}

	public void setServId(String servId) {
		this.servId = servId;
	}

	public String getServName() {
		return servName;
	}

	public void setServName(String servName) {
		this.servName = servName;
	}

	public String getServRef() {
		return servRef;
	}

	public void setServRef(String servRef) {
		this.servRef = servRef;
	}

	public String getServCategory() {
		return servCategory;
	}

	public void setServCategory(String servCategory) {
		this.servCategory = servCategory;
	}

	public boolean isServEnabled() {
		return servEnabled;
	}

	public void setServEnabled(boolean servEnabled) {
		this.servEnabled = servEnabled;
	}

	public boolean isServFeatured() {
		return servFeatured;
	}

	public void setServFeatured(boolean servFeatured) {
		this.servFeatured = servFeatured;
	}

	public double getServFixedPrice() {
		return servFixedPrice;
	}

	public void setServFixedPrice(double servFixedPrice) {
		this.servFixedPrice = servFixedPrice;
	}

	public String getServFixedRateType() {
		return servFixedRateType;
	}

	public void setServFixedRateType(String servFixedRateType) {
		this.servFixedRateType = servFixedRateType;
	}

	public String getServTaxClassId() {
		return servTaxClassId;
	}

	public void setServTaxClassId(String servTaxClassId) {
		this.servTaxClassId = servTaxClassId;
	}

	public boolean isServQuatationReqEnabled() {
		return servQuatationReqEnabled;
	}

	public void setServQuatationReqEnabled(boolean servQuatationReqEnabled) {
		this.servQuatationReqEnabled = servQuatationReqEnabled;
	}

	public boolean isServOhDaysMon() {
		return servOhDaysMon;
	}

	public void setServOhDaysMon(boolean servOhDaysMon) {
		this.servOhDaysMon = servOhDaysMon;
	}

	public String getServYoutubeUrl() {
		return servYoutubeUrl;
	}

	public void setServYoutubeUrl(String servYoutubeUrl) {
		this.servYoutubeUrl = servYoutubeUrl;
	}

	public String getServAltText() {
		return servAltText;
	}

	public void setServAltText(String servAltText) {
		this.servAltText = servAltText;
	}

	public String getServDesc() {
		return servDesc;
	}

	public void setServDesc(String servDesc) {
		this.servDesc = servDesc;
	}

	public String getServFeatures() {
		return servFeatures;
	}

	public void setServFeatures(String servFeatures) {
		this.servFeatures = servFeatures;
	}

	public String getServSpecification() {
		return servSpecification;
	}

	public void setServSpecification(String servSpecification) {
		this.servSpecification = servSpecification;
	}

	public SobieImage getServImage() {
		return servImage;
	}

	public void setServImage(SobieImage servImage) {
		this.servImage = servImage;
	}

	public double getServFixedRateUnit() {
		return servFixedRateUnit;
	}

	public void setServFixedRateUnit(double servFixedRateUnit) {
		this.servFixedRateUnit = servFixedRateUnit;
	}

	public Time getServStartTime() {
		return servStartTime;
	}

	public void setServStartTime(Time servStartTime) {
		this.servStartTime = servStartTime;
	}

	public Time getServEndTime() {
		return servEndTime;
	}

	public void setServEndTime(Time servEndTime) {
		this.servEndTime = servEndTime;
	}

	public boolean isServCommentsEnabled() {
		return servCommentsEnabled;
	}

	public void setServCommentsEnabled(boolean servCommentsEnabled) {
		this.servCommentsEnabled = servCommentsEnabled;
	}

	public boolean isServQnaEnabled() {
		return servQnaEnabled;
	}

	public void setServQnaEnabled(boolean servQnaEnabled) {
		this.servQnaEnabled = servQnaEnabled;
	}

	public ArrayList<SobieImage> getImageCatalog() {
		return imageCatalog;
	}

	public void setImageCatalog(ArrayList<SobieImage> imageCatalog) {
		this.imageCatalog = imageCatalog;
	}

	public SobieImage getServiceImage() {
		return serviceImage;
	}

	public void setServiceImage(SobieImage serviceImage) {
		this.serviceImage = serviceImage;
	}

}
