package biz.sobie.web.inventoryservices;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import biz.sobie.web.beans.Service;
import biz.sobie.web.beans.SobieImage;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.services.ServiceProviderService;
import biz.sobie.web.utils.SobieUtils;

public class InventoryServicesVm {

	private SobieProfile sobieProfile;
	private List<Service> storeServices;
	private Service selectedService = new Service();
	private boolean addUpdateService = false;
	private boolean editService = false;
	@WireVariable ServiceProviderService serviceProviderService;
	
	@Init
	public void init() {
		sobieProfile = (SobieProfile)Executions.getCurrent().getSession().getAttribute("sobieProfile");
		storeServices = serviceProviderService.retrieveSellerServices(sobieProfile.getAccount().getStoreNo());
	}
	
	@Command
	@NotifyChange("selectedService")
	public void uploadServiceImage(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx){
		UploadEvent event = (UploadEvent)ctx.getTriggerEvent();
		ArrayList<SobieImage> imageCatalog = selectedService.getImageCatalog();
		Media media = event.getMedia();
		if (media != null) {
            if (media instanceof org.zkoss.image.Image) {
            	SobieImage serviceImage = new SobieImage();
            	serviceImage.setImageInBytes(media.getByteData());
            	serviceImage.setImgFilename(media.getName());
            	serviceImage.setImgType(media.getFormat());
            	if(imageCatalog.size() == 0){
            		serviceImage.setPrimaryImage("Y");
            	} else {
            		serviceImage.setPrimaryImage("N");
            	}
            	SobieUtils sobieUtils = new SobieUtils();
            	serviceImage.setImgId(sobieUtils.createImageId());
            	serviceImage.setUpdateImage(true);
            	imageCatalog.add(serviceImage);
            	selectedService.setImageCatalog(imageCatalog);
            } else {
            	Messagebox.show("Not an image: " + media, "Error",
                        Messagebox.OK, Messagebox.ERROR);
            }
        }
	}
	
	@Command
	@NotifyChange({"addUpdateService","storeServices"})
	public void saveUpdateService(){
		if(!isEditService()){
			serviceProviderService.addServiceToSellerShop(selectedService, sobieProfile.getAccount().getStoreNo(), sobieProfile.getAccount().getAccNo());
		} else {
			serviceProviderService.updateServiceInSellerShop(selectedService, sobieProfile.getAccount().getStoreNo(), sobieProfile.getAccount().getAccNo());
		}
		setAddUpdateService(false);
		storeServices = serviceProviderService.retrieveSellerServices(sobieProfile.getAccount().getStoreNo());
	}
	
	@Command
	@NotifyChange("storeServices")
	public void deleteService(@BindingParam("service") Service service){
		serviceProviderService.deleteService(service.getServId(), getSobieProfile().getAccount().getStoreNo());
		storeServices = serviceProviderService.retrieveSellerServices(sobieProfile.getAccount().getStoreNo());
	}
	
	@Command
	@NotifyChange({"addUpdateService","selectedService"})
	public void addNewService(){
		setAddUpdateService(true);
		selectedService = new Service();
	}
	
	@Command
	@NotifyChange({"addUpdateService","selectedService"})
	public void editService(@BindingParam("service") Service service){
		selectedService = service;
		setAddUpdateService(true);
		setEditService(true);
	}
	
	@Command
	@NotifyChange("addUpdateService")
	public void cancelAddNewService(){
		setAddUpdateService(false);
	}

	public Service getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(Service selectedService) {
		this.selectedService = selectedService;
	}

	public boolean isAddUpdateService() {
		return addUpdateService;
	}

	public void setAddUpdateService(boolean addUpdateService) {
		this.addUpdateService = addUpdateService;
	}

	public boolean isEditService() {
		return editService;
	}

	public void setEditService(boolean editService) {
		this.editService = editService;
	}

	public List<Service> getStoreServices() {
		return storeServices;
	}

	public void setStoreServices(List<Service> storeServices) {
		this.storeServices = storeServices;
	}

	public SobieProfile getSobieProfile() {
		return sobieProfile;
	}

	public void setSobieProfile(SobieProfile sobieProfile) {
		this.sobieProfile = sobieProfile;
	}
	
	
	
}
