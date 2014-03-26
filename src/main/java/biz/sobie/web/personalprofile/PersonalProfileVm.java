package biz.sobie.web.personalprofile;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import biz.sobie.web.beans.Customer;
import biz.sobie.web.beans.SobieImage;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.services.CustomerAccountService;
import biz.sobie.web.utils.SobieUtils;

public class PersonalProfileVm {

	private SobieProfile sobieProfile;
	private Customer customer;
	
	private boolean personalProfile;
	private String changeImagePopup;
	private String displayEditNonEdit;
	private String displayEditNonEditButton;
	@WireVariable CustomerAccountService customerAccountService;
	
	@Init
	public void init() {
		sobieProfile = (SobieProfile) Executions.getCurrent().getSession().getAttribute("sobieProfile");
		customer = sobieProfile.getCustomer();
		changeImagePopup = "changeImagePopup";
		setPersonalProfile(true);
		displayEditNonEdit = "/secure/widgets/userDashboardWidget/system/systemMyAccountNonEditable.zul";
		displayEditNonEditButton = "Edit";
	}
	
	@GlobalCommand
	@NotifyChange({"customer","personalProfile","changeImagePopup"})
	public void openSearchedAccount(@BindingParam("customer") Customer searchedCustomer){
			customer = searchedCustomer;
			changeImagePopup = "null";
			setPersonalProfile(false);
			if(customer.getCustNo().equals(getSobieProfile().getCustomer().getCustNo())){
				openMyAccount();
			}
	}
	
	@GlobalCommand
	@NotifyChange({"customer","personalProfile","changeImagePopup"})
	public void openMyAccount(){
			customer = sobieProfile.getCustomer();
			changeImagePopup = "changeImagePopup";
			setPersonalProfile(true);
	}
	
	/**
	 * Used to upload a new or change an existing profile picture
	 * @param ctx
	 */
	@Command
	@NotifyChange("sobieProfile")
	public void uploadChangeProfileImage(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx){
		UploadEvent event = (UploadEvent)ctx.getTriggerEvent();
		Media media = event.getMedia();
		if (media != null) {
            if (media instanceof org.zkoss.image.Image) {
            	SobieImage profileImage = new SobieImage();
            	profileImage.setImageInBytes(media.getByteData());
            	profileImage.setImgFilename(media.getName());
            	profileImage.setImgType(media.getFormat());
            	profileImage.setPrimaryImage("Y");
            	profileImage.setImageAlbum("PROFILE_PICTURE_CATALOG");
            	
            	SobieUtils sobieUtils = new SobieUtils();
            	String oldImgId = null;
            	if(getSobieProfile().getCustomer().getProfileImage().getImgId().equals("0000000000000000003")){
                	profileImage.setImgId(sobieUtils.createImageId());	
        		} else {
        			oldImgId = getSobieProfile().getCustomer().getProfileImage().getImgId();
        			profileImage.setImgId(sobieUtils.createImageId());
        		}
            	getSobieProfile().getCustomer().setProfileImage(profileImage);
            	customerAccountService.updatePersonalProfilePicture(profileImage, getSobieProfile().getCustomer().getCustNo(), getSobieProfile().getAccount().getAccNo(), oldImgId);
            	Executions.getCurrent().getSession().setAttribute("sobieProfile", getSobieProfile());
            } else {
            	Messagebox.show("Not an image: " + media, "Error",
                        Messagebox.OK, Messagebox.ERROR);
            }
        }
	}
	
	@Command
	@NotifyChange("displayEditNonEdit")
	public void editMyAccountDetails(){
		if(getDisplayEditNonEdit().equals("/secure/widgets/userDashboardWidget/system/systemMyAccountNonEditable.zul")){
			setDisplayEditNonEdit("/secure/widgets/userDashboardWidget/system/systemMyAccountEditable.zul");
			setDisplayEditNonEditButton("Save / Update");
		} else {
			setDisplayEditNonEdit("/secure/widgets/userDashboardWidget/system/systemMyAccountNonEditable.zul");
			setDisplayEditNonEditButton("Edit");
		}
	}
	
	@GlobalCommand
	@NotifyChange("sobieProfile")
	public void refreshPersonalProfile(){
		sobieProfile = (SobieProfile) Executions.getCurrent().getSession().getAttribute("sobieProfile");
	}

	public SobieProfile getSobieProfile() {
		return sobieProfile;
	}

	public void setSobieProfile(SobieProfile sobieProfile) {
		this.sobieProfile = sobieProfile;
	}

	public String getDisplayEditNonEdit() {
		return displayEditNonEdit;
	}

	public void setDisplayEditNonEdit(String displayEditNonEdit) {
		this.displayEditNonEdit = displayEditNonEdit;
	}

	public String getDisplayEditNonEditButton() {
		return displayEditNonEditButton;
	}

	public void setDisplayEditNonEditButton(String displayEditNonEditButton) {
		this.displayEditNonEditButton = displayEditNonEditButton;
	}

	public boolean isPersonalProfile() {
		return personalProfile;
	}

	public void setPersonalProfile(boolean personalProfile) {
		this.personalProfile = personalProfile;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getChangeImagePopup() {
		return changeImagePopup;
	}

	public void setChangeImagePopup(String changeImagePopup) {
		this.changeImagePopup = changeImagePopup;
	}
}
