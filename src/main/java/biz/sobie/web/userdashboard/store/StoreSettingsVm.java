package biz.sobie.web.userdashboard.store;

import java.io.UnsupportedEncodingException;

import org.gglgeo.ggl.GeoInfo;
import org.gglgeo.ggl.GeoLatLng;
import org.gglgeo.ggl.GeoResult;
import org.gglgeo.ggl.Geocoder;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.event.MapDropEvent;
import org.zkoss.gmaps.event.MapMouseEvent;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import biz.sobie.web.beans.SobieImage;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.beans.Store;
import biz.sobie.web.services.StoreService;
import biz.sobie.web.utils.SobieUtils;

public class StoreSettingsVm {

	private Store storeSettings;
	private Store loadedStoreSettings;
	private boolean storeLogoUpdated;
	private boolean storeIndexMainHeaderUpdated;
	@WireVariable StoreService storeService;
	
	@Init
	public void init() throws CloneNotSupportedException {
		SobieProfile sobieProfile = (SobieProfile)Executions.getCurrent().getDesktop().getSession().getAttribute("sobieProfile");
		storeSettings = (Store) storeService.retrieveStoreSettings(sobieProfile);
		loadedStoreSettings =  (Store) storeSettings.clone();
		storeLogoUpdated = false;
		storeIndexMainHeaderUpdated = false;
		if(storeSettings.getStoreCustCommentStatus() == null){
			getStoreSettings().setStoreCustCommentStatus("Require Admin Approval"); 
		}
	}

	@Command
	@NotifyChange("storeSettings")
    public void mapMouseEvent(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {
            MapMouseEvent event = (MapMouseEvent)ctx.getTriggerEvent();
            if ("onMapClick".equals(event.getName()) && (event.getReference() instanceof Gmaps)) {
            	getStoreSettings().setLatitude(event.getLat());
            	getStoreSettings().setLongitude(event.getLng());
            }
    }
	
	@Command
	@NotifyChange("storeSettings")
    public void mapDropEvent(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) throws UnsupportedEncodingException {
            MapDropEvent event = (MapDropEvent)ctx.getTriggerEvent();
            if (event.getDragged() instanceof Gmarker) {
            	getStoreSettings().setLatitude(event.getLat());
            	getStoreSettings().setLongitude(event.getLng());
            	
            }
    }
	
	@Command
	@NotifyChange("storeSettings")
    public void getLatLngFromAddress() throws UnsupportedEncodingException{
		GeoInfo info = new GeoInfo();
		String street = getStoreSettings().getStreet();
		for(int x = 0;x < street.length(); x++){
			
		}
        info.setAddress(getStoreSettings().getStreet().replace(" ", "+") + "+" + getStoreSettings().getCityTown().replace(" ", "+") + "+" + getStoreSettings().getCountry().replace(" ", "+"));
        Geocoder gcoder = new Geocoder();
        GeoResult gresult = gcoder.get(info);
		/*According to Google Geocoding API, "results" contains an array of geocoded address information and geometry information, 
		getResult will return array of result objects rather than single one.*/
        GeoLatLng latlng = gresult.getResults().get(0).getGeometry().getLocation();
       /* mymap.panTo(latlng.getLat(), latlng.getLng());
        alert(gresult.getResults().get(0).getFormattedAddress());   */
        getStoreSettings().setLatitude(latlng.getLat());
    	getStoreSettings().setLongitude(latlng.getLng());
	}
	
	/**
	 * Updates all information on the store settings page
	 * @throws Exception
	 */
	@Command
	public void updateStoreSettings() throws Exception{
		SobieProfile sobieProfile = (SobieProfile)Executions.getCurrent().getDesktop().getSession().getAttribute("sobieProfile");
		storeService.updateStoreSettings(getStoreSettings());
		
		if(isStoreLogoUpdated()){
			if(!getStoreSettings().getStoreLogo().getImgId().equals(getLoadedStoreSettings().getStoreLogo().getImgId())){
				storeService.insertStoreImage(getStoreSettings().getStoreLogo(), sobieProfile);		
			} else {
				storeService.updateStoreImage(getStoreSettings().getStoreLogo(), sobieProfile);
			}
			setStoreLogoUpdated(false);
		}
		
		if(isStoreIndexMainHeaderUpdated()){
			if(!getStoreSettings().getStoreIndexMainHeaderImage().getImgId().equals(getLoadedStoreSettings().getStoreIndexMainHeaderImage().getImgId())){
				storeService.insertStoreImage(getStoreSettings().getStoreIndexMainHeaderImage(), sobieProfile);	
			} else {
				storeService.updateStoreImage(getStoreSettings().getStoreIndexMainHeaderImage(), sobieProfile);
			}
			setStoreIndexMainHeaderUpdated(false);
		}
	}
	
	/**
	 * Used to upload an image as a new store logo
	 * @param ctx
	 */
	@Command
	@NotifyChange({"storeSettings","storeLogoExist","storeLogoNotExist"})
	public void uploadStoreLogo(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx){
		UploadEvent event = (UploadEvent)ctx.getTriggerEvent();
		Media media = event.getMedia();
		if (media != null) {
            if (media instanceof org.zkoss.image.Image) {
            	SobieImage storeLogo = new SobieImage();
            	storeLogo.setImageInBytes(media.getByteData());
            	storeLogo.setImgFilename(media.getName());
            	storeLogo.setImgType(media.getFormat());
            	storeLogo.setPrimaryImage("Y");
            	storeLogo.setImageAlbum("STORE_LOGO_CATALOG");
            	if(getStoreSettings().getStoreLogo().getImgId().equals("0000000000000000001")){
            		SobieUtils sobieUtils = new SobieUtils();
                	storeLogo.setImgId(sobieUtils.createImageId());	
        		} else {
        			storeLogo.setImgId(getStoreSettings().getStoreLogo().getImgId());
        		}
            	getStoreSettings().setStoreLogo(storeLogo);
            } else {
            	Messagebox.show("Not an image: " + media, "Error",
                        Messagebox.OK, Messagebox.ERROR);
            }
        }
		setStoreLogoUpdated(true);
	}
	
	/**
	 * Used to upload an image as a new store logo
	 * @param ctx
	 */
	@Command
	@NotifyChange({"storeSettings","storeIndexMainHeaderExist","storeIndexMainHeaderNotExist"})
	public void uploadStoreIndexMainHeader(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx){
		UploadEvent event = (UploadEvent)ctx.getTriggerEvent();
		Media media = event.getMedia();
		if (media != null) {
            if (media instanceof org.zkoss.image.Image) {
            	SobieImage storeIndexMainHeaderImage = new SobieImage();
            	storeIndexMainHeaderImage.setImageInBytes(media.getByteData());
            	storeIndexMainHeaderImage.setImgFilename(media.getName());
            	storeIndexMainHeaderImage.setImgType(media.getFormat());
            	storeIndexMainHeaderImage.setPrimaryImage("Y");
            	storeIndexMainHeaderImage.setImageAlbum("STORE_INDEX_MAIN_HEADER_CATALOG");
            	if(getStoreSettings().getStoreIndexMainHeaderImage().getImgId().equals("0000000000000000002")){
            		SobieUtils sobieUtils = new SobieUtils();
                	storeIndexMainHeaderImage.setImgId(sobieUtils.createImageId());	
        		} else {
        			storeIndexMainHeaderImage.setImgId(getStoreSettings().getStoreIndexMainHeaderImage().getImgId());
        		}
            	getStoreSettings().setStoreIndexMainHeaderImage(storeIndexMainHeaderImage);
            } else {
            	Messagebox.show("Not an image: " + media, "Error",
                        Messagebox.OK, Messagebox.ERROR);
            }
            setStoreIndexMainHeaderUpdated(true);
        }
	}
	
	public Store getStoreSettings() {
		return storeSettings;
	}

	public void setStoreSettings(Store storeSettings) {
		this.storeSettings = storeSettings;
	}

	public Store getLoadedStoreSettings() {
		return loadedStoreSettings;
	}

	public void setLoadedStoreSettings(Store loadedStoreSettings) {
		this.loadedStoreSettings = loadedStoreSettings;
	}

	public boolean isStoreLogoUpdated() {
		return storeLogoUpdated;
	}

	public void setStoreLogoUpdated(boolean storeLogoUpdated) {
		this.storeLogoUpdated = storeLogoUpdated;
	}

	public boolean isStoreIndexMainHeaderUpdated() {
		return storeIndexMainHeaderUpdated;
	}

	public void setStoreIndexMainHeaderUpdated(boolean storeIndexMainHeaderUpdated) {
		this.storeIndexMainHeaderUpdated = storeIndexMainHeaderUpdated;
	}
}
