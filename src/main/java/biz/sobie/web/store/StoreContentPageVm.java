package biz.sobie.web.store;

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

import biz.sobie.web.beans.SobieImage;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.services.StoreService;
import biz.sobie.web.utils.SobieUtils;

public class StoreContentPageVm {

	private List<StoreContentPage> storeContentPages;
	private StoreContentPage addUpdateStoreContentPage;
	private boolean previewContentPageEnabled;
	private boolean contentPageEditorEnabled;
	private boolean previewContentPageButton;
	private boolean contentPageEditorButton;
	private boolean contentPageHeaderUpdated;
	private String addUpdateNewContentPage;
	private String loadedStoreContentHeaderImageId;
	private List<StoreMenuItem> storeMenuItems;
	private SobieProfile sobieProfile;
	
	@WireVariable StoreService storeService;
	
	@Init
	public void init() {
		sobieProfile = (SobieProfile) Executions.getCurrent().getDesktop().getSession().getAttribute("sobieProfile");
		addUpdateNewContentPage = (String) Executions.getCurrent().getDesktop().getSession().getAttribute("addUpdateNewContentPage");
		loadedStoreContentHeaderImageId = (String) Executions.getCurrent().getDesktop().getSession().getAttribute("loadedStoreContentHeaderImageId");
		/**
		 * Checks whether the user is viewing the list of content pages, or are busy adding a new content page
		 */
		if(getAddUpdateNewContentPage().equals("0") || getAddUpdateNewContentPage().equals("1")){
			if(getAddUpdateNewContentPage().equals("1")){
				storeContentPages = (List<StoreContentPage>) storeService.retrieveStoreContentPages(sobieProfile.getAccount().getStoreNo());
			} else if(getAddUpdateNewContentPage().equals("0")){
				
				storeContentPages = (List<StoreContentPage>) storeService.retrieveStoreContentPages((String) Executions.getCurrent().getDesktop().getSession().getAttribute("storeMainLayoutStoreNo"));
				
				ArrayList<StoreMenuItem> tempMenuItems = new ArrayList<StoreMenuItem>();
				
				StoreMenuItem storeMenuItem = new StoreMenuItem();
				storeMenuItem.setMenuItem("Home");
				tempMenuItems.add(storeMenuItem);
				
				storeMenuItem = new StoreMenuItem();
				storeMenuItem.setMenuItem("About Us");
				tempMenuItems.add(storeMenuItem);
				
				for(int x = 0; x < storeContentPages.size(); x++){
					storeMenuItem = new StoreMenuItem();
					storeMenuItem.setMenuItem(storeContentPages.get(x).getPageTittle());
					storeMenuItem.setStoreContentPage(storeContentPages.get(x));
					tempMenuItems.add(storeMenuItem);
				}
				storeMenuItem = new StoreMenuItem();
				storeMenuItem.setMenuItem("Contact Us");
				tempMenuItems.add(storeMenuItem);
				storeMenuItems = tempMenuItems;
		}
			
		} else if(getAddUpdateNewContentPage().equals("2")){
			addUpdateStoreContentPage = new StoreContentPage();
			addUpdateStoreContentPage.setStoreContentPageHeaderImage(storeService.retrieveDefaultHeaderImage());
		} else if(getAddUpdateNewContentPage().equals("3")){
			addUpdateStoreContentPage = (StoreContentPage) Executions.getCurrent().getDesktop().getSession().getAttribute("storeContentPage");
		}
		previewContentPageEnabled = false;
		contentPageEditorEnabled = true;
		previewContentPageButton = true;
		contentPageEditorButton = false;
		Executions.getCurrent().getDesktop().getSession().setAttribute("addUpdateNewContentPage", "0");
	}
	
	@Command
	@NotifyChange({"previewContentPageEnabled","contentPageEditorEnabled","previewContentPageButton","contentPageEditorButton"})
	public void switchToPreviewContentPage(){
		setPreviewContentPageEnabled(true);
		setContentPageEditorEnabled(false);
		setPreviewContentPageButton(false);
		setContentPageEditorButton(true);
	}
	
	@Command
	@NotifyChange({"previewContentPageEnabled","contentPageEditorEnabled","previewContentPageButton","contentPageEditorButton"})
	public void switchToEditContentPage(@BindingParam("contentPage") StoreContentPage storeContentPage) throws Exception{
		setPreviewContentPageEnabled(false);
		setContentPageEditorEnabled(true);
		setPreviewContentPageButton(true);
		setContentPageEditorButton(false);
	}
	
	@Command
	@NotifyChange("storeContentPages")
	public void deleteContentPage(@BindingParam("storeContentPageId") String storeContentPageId){
		storeService.deleteContentPage(storeContentPageId);
		storeContentPages = (List<StoreContentPage>) storeService.retrieveStoreContentPages(sobieProfile.getAccount().getStoreNo());
	}
	
	@Command
	public void addUpdateContentPage() throws Exception{
		
		if(getAddUpdateNewContentPage().equals("2")){
			/**
			 * Add New Content Page
			 */
			SobieUtils sobieUtils = new SobieUtils();
			addUpdateStoreContentPage.setPageId(sobieUtils.createStoreContentPageId());
			addUpdateStoreContentPage.setStoreNo(sobieProfile.getAccount().getStoreNo());
			storeService.addNewContentPage(addUpdateStoreContentPage);
			if(isContentPageHeaderUpdated()){
				if(!getAddUpdateStoreContentPage().getStoreContentPageHeaderImage().getImgId().equals(getLoadedStoreContentHeaderImageId())){
					getAddUpdateStoreContentPage().getStoreContentPageHeaderImage().setStoreContentPageId(addUpdateStoreContentPage.getPageId());
					storeService.insertStoreContentHeader(getAddUpdateStoreContentPage().getStoreContentPageHeaderImage(), sobieProfile);		
				} else {
					storeService.updateStoreImage(getAddUpdateStoreContentPage().getStoreContentPageHeaderImage(), sobieProfile);
				}
			}
		} else if(getAddUpdateNewContentPage().equals("3")){
			/**
			 * Update Existing Content Page
			 */
			storeService.updateContentPage(addUpdateStoreContentPage);
			if(isContentPageHeaderUpdated()){
				if(!getAddUpdateStoreContentPage().getStoreContentPageHeaderImage().getImgId().equals(getLoadedStoreContentHeaderImageId())){
					getAddUpdateStoreContentPage().getStoreContentPageHeaderImage().setStoreContentPageId(addUpdateStoreContentPage.getPageId());
					storeService.insertStoreContentHeader(getAddUpdateStoreContentPage().getStoreContentPageHeaderImage(), sobieProfile);		
				} else {
					storeService.updateStoreImage(getAddUpdateStoreContentPage().getStoreContentPageHeaderImage(), sobieProfile);
				}
			}
		}
		setContentPageHeaderUpdated(false);
	}
	
	/**
	 * Used to upload an image as a new store content page header
	 * @param ctx
	 */
	@Command
	@NotifyChange({"addUpdateStoreContentPage","contentPageHeaderUpdated"})
	public void uploadStoreContentPageHeader(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx){
		UploadEvent event = (UploadEvent)ctx.getTriggerEvent();
		Media media = event.getMedia();
		if (media != null) {
            if (media instanceof org.zkoss.image.Image) {
            	SobieImage storeContentPageHeader = new SobieImage();
            	storeContentPageHeader.setImageInBytes(media.getByteData());
            	storeContentPageHeader.setImgFilename(media.getName());
            	storeContentPageHeader.setImgType(media.getFormat());
            	storeContentPageHeader.setPrimaryImage("Y");
            	storeContentPageHeader.setImageAlbum("STORE_CONTENT_PAGE_HEADER_CATALOG");
            	if(getAddUpdateStoreContentPage().getStoreContentPageHeaderImage().getImgId().equals("0000000000000000002")){
            		SobieUtils sobieUtils = new SobieUtils();
            		storeContentPageHeader.setImgId(sobieUtils.createImageId());	
        		} else {
        			storeContentPageHeader.setImgId(getAddUpdateStoreContentPage().getStoreContentPageHeaderImage().getImgId());
        		}
            	getAddUpdateStoreContentPage().setStoreContentPageHeaderImage(storeContentPageHeader);
            } else {
            	Messagebox.show("Not an image: " + media, "Error",
                        Messagebox.OK, Messagebox.ERROR);
            }
        }
		setContentPageHeaderUpdated(true);
	}

	public List<StoreContentPage> getStoreContentPages() {
		return storeContentPages;
	}

	public void setStoreContentPages(List<StoreContentPage> storeContentPages) {
		this.storeContentPages = storeContentPages;
	}

	public StoreContentPage getAddUpdateStoreContentPage() {
		return addUpdateStoreContentPage;
	}

	public void setAddUpdateStoreContentPage(
			StoreContentPage addUpdateStoreContentPage) {
		this.addUpdateStoreContentPage = addUpdateStoreContentPage;
	}

	public boolean isPreviewContentPageEnabled() {
		return previewContentPageEnabled;
	}

	public void setPreviewContentPageEnabled(boolean previewContentPageEnabled) {
		this.previewContentPageEnabled = previewContentPageEnabled;
	}

	public boolean isContentPageEditorEnabled() {
		return contentPageEditorEnabled;
	}

	public void setContentPageEditorEnabled(boolean contentPageEditorEnabled) {
		this.contentPageEditorEnabled = contentPageEditorEnabled;
	}

	public boolean isPreviewContentPageButton() {
		return previewContentPageButton;
	}

	public void setPreviewContentPageButton(boolean previewContentPageButton) {
		this.previewContentPageButton = previewContentPageButton;
	}

	public boolean isContentPageEditorButton() {
		return contentPageEditorButton;
	}

	public void setContentPageEditorButton(boolean contentPageEditorButton) {
		this.contentPageEditorButton = contentPageEditorButton;
	}

	public boolean isContentPageHeaderUpdated() {
		return contentPageHeaderUpdated;
	}

	public void setContentPageHeaderUpdated(boolean contentPageHeaderUpdated) {
		this.contentPageHeaderUpdated = contentPageHeaderUpdated;
	}

	public String getAddUpdateNewContentPage() {
		return addUpdateNewContentPage;
	}

	public void setAddUpdateNewContentPage(String addUpdateNewContentPage) {
		this.addUpdateNewContentPage = addUpdateNewContentPage;
	}

	public String getLoadedStoreContentHeaderImageId() {
		return loadedStoreContentHeaderImageId;
	}

	public void setLoadedStoreContentHeaderImageId(
			String loadedStoreContentHeaderImageId) {
		this.loadedStoreContentHeaderImageId = loadedStoreContentHeaderImageId;
	}

	public List<StoreMenuItem> getStoreMenuItems() {
		return storeMenuItems;
	}

	public void setStoreMenuItems(List<StoreMenuItem> storeMenuItems) {
		this.storeMenuItems = storeMenuItems;
	}

	public SobieProfile getSobieProfile() {
		return sobieProfile;
	}

	public void setSobieProfile(SobieProfile sobieProfile) {
		this.sobieProfile = sobieProfile;
	}
	
	
}
