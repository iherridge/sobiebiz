package biz.sobie.web.index;

import java.util.ArrayList;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import biz.sobie.web.beans.Customer;
import biz.sobie.web.beans.MultipleSobieProfiles;
import biz.sobie.web.beans.Product;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.beans.Store;
import biz.sobie.web.breadcrump.Breadcrump;
import biz.sobie.web.browser.BrowserWidget;
import biz.sobie.web.services.CustomerAccountService;
import biz.sobie.web.store.StoreContentPage;
import biz.sobie.web.userdashboard.DashboardPrivileges;

public class IndexVm {

	private String centerPageArea;
	private String multipleSobieProfilesPopup;
	private String menuPopupPosition;
	private boolean homeAVisible;
	private boolean logoutAVisible;
	private boolean loginDivVisible;
	private boolean profileHeaderVisible;
	private boolean anonymousMenuBarVisible;
	private boolean sobieUserMenuBarVisible;
	private boolean showModal = true;
	private boolean showEastPanel = false;
	private boolean showMenuPopup;
	private boolean storeMenuItemOpen = false;
	private boolean networkMenuItemOpen = false;
	private boolean inventoryMenuItemOpen = false;
	private boolean salesMenuItemOpen = false;
	private boolean purchasesMenuItemOpen = false;
	private boolean marketingMenuItemOpen = false;
	private boolean applicationsMenuItemOpen = false;
	private boolean reportsMenuItemOpen = false;
	private boolean purchasesOrdersMenuItemOpen = false;
	private boolean systemMenuItemOpen = false;
	private SobieProfile sobieProfile;
	private DashboardPrivileges dashboardPrivileges;
	@WireVariable CustomerAccountService customerAccountService;
	
	@Init
	@NotifyChange("index")
	public void init() {
		Executions.getCurrent().getDesktop().getSession().setAttribute("addUpdateNewContentPage", "0");
		Executions.getCurrent().getDesktop().getSession().setAttribute("browserWidget", null);
		//centerPageArea = "/unsecure/widgets/browserWidget/browserWidget.zul";
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
			homeAVisible = false;
			logoutAVisible = false;
			profileHeaderVisible = false;
			loginDivVisible = true;
			anonymousMenuBarVisible = true;
			sobieUserMenuBarVisible = false;
			
			if(Executions.getCurrent().getSession().getAttribute("sobieProfile") == null) {
				/**
				 * Checks if known IP address, if IP address exists then display relevant information about related IP, 
				 * such as Wishlist and Shopping cart. If the IP address does not exist, then create anonymous profile
				 * with relevant wishlist and shopping cart details to keep track of.
				 */
		    	MultipleSobieProfiles multipleSobieProfiles = customerAccountService.checkMultipleProfilesExist(Executions.getCurrent().getRemoteAddr());
		    	
		    	if (multipleSobieProfiles.getSobieProfiles().size() == 1) {
		    		SobieProfile sobieProfile = multipleSobieProfiles.getSobieProfiles().get(0);
		    		Executions.getCurrent().getDesktop().getSession().setAttribute("sobieProfile", sobieProfile);
		    		multipleSobieProfiles.setProfileSelected(false);
		    	} else if(multipleSobieProfiles.getSobieProfiles().size() == 0){
		    		SobieProfile sobieProfile = customerAccountService.createAnonymousBuyer(Executions.getCurrent().getRemoteAddr());
		    		ArrayList<SobieProfile> tempSobieProfile = new ArrayList<SobieProfile>(1);
		    		tempSobieProfile.add(0, sobieProfile);
		    		multipleSobieProfiles.setSobieProfiles(tempSobieProfile);
		    		Executions.getCurrent().getDesktop().getSession().setAttribute("sobieProfile", sobieProfile);
		    		multipleSobieProfiles.setProfileSelected(true);
		    	} else if (multipleSobieProfiles.getSobieProfiles().size() == 1 && !multipleSobieProfiles.getSobieProfiles().get(0).getAccount().getPpId().equals("BU0")){
		    		SobieProfile sobieProfile = customerAccountService.createAnonymousBuyer(Executions.getCurrent().getRemoteAddr());
		    		ArrayList<SobieProfile> tempSobieProfile = new ArrayList<SobieProfile>(2);
		    		tempSobieProfile.add(0, sobieProfile);
		    		tempSobieProfile.add(1, multipleSobieProfiles.getSobieProfiles().get(0));
		    		multipleSobieProfiles.setSobieProfiles(tempSobieProfile);
		    		Executions.getCurrent().getDesktop().getSession().setAttribute("sobieProfile", multipleSobieProfiles.getSobieProfiles().get(0));
		    		multipleSobieProfiles.setProfileSelected(true);
		    	} else if(multipleSobieProfiles.getSobieProfiles().size() >= 2) {
		    		int registeredAccountIndex = 0;
		    		boolean anonymousBuyerExist = false;
		    		for(int x = 0; x < multipleSobieProfiles.getSobieProfiles().size();x++){
		    			if(multipleSobieProfiles.getSobieProfiles().get(x).getAccount().getPpId().equals("BU0")){
		    				registeredAccountIndex = x;
		    				anonymousBuyerExist = true;
		    			}
		    		}
		    		if(anonymousBuyerExist){
		    			Executions.getCurrent().getDesktop().getSession().setAttribute("sobieProfile", multipleSobieProfiles.getSobieProfiles().get(registeredAccountIndex));
		    			multipleSobieProfiles.setProfileSelected(false);
		    		} else {
		    			SobieProfile sobieProfile = customerAccountService.createAnonymousBuyer(Executions.getCurrent().getRemoteAddr());
			    		ArrayList<SobieProfile> tempSobieProfile = new ArrayList<SobieProfile>();
			    		tempSobieProfile = (ArrayList<SobieProfile>) multipleSobieProfiles.getSobieProfiles();
			    		tempSobieProfile.add(sobieProfile);
			    		multipleSobieProfiles.setSobieProfiles(tempSobieProfile);
			    		Executions.getCurrent().getDesktop().getSession().setAttribute("sobieProfile", sobieProfile);
			    		multipleSobieProfiles.setProfileSelected(false);
		    		}
		    	}
		    	Executions.getCurrent().getDesktop().getSession().setAttribute("multipleSobieProfiles", multipleSobieProfiles);
			}
			if(Executions.getCurrent().getSession().getAttribute("multipleSobieProfiles") != null ){
	    		MultipleSobieProfiles multipleSobieProfiles = (MultipleSobieProfiles)Executions.getCurrent().getSession().getAttribute("multipleSobieProfiles");
	    		if(multipleSobieProfiles.getSobieProfiles().size() > 1 && !multipleSobieProfiles.isProfileSelected()){
	    			setMultipleSobieProfilesPopup("/unsecure/multipleSobieProfiles.zul");
	    		}
	    	}
		} else {
			homeAVisible = true;
			logoutAVisible = true;
			loginDivVisible = false;
			profileHeaderVisible = true;
			anonymousMenuBarVisible = false;
			sobieUserMenuBarVisible = true;
			SobieProfile sobieProfile = (SobieProfile) Executions.getCurrent().getDesktop().getSession().getAttribute("sobieProfile");
			if(sobieProfile == null || sobieProfile.isLoggedIn() == false) {
				/**
				 * Only called when the user logs in
				 */
		    	SobieProfile sobieProfileResp = (SobieProfile)customerAccountService.getCustomerAccountDetails(SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getName());
		    	sobieProfileResp.setLoggedIn(true);
		    	Executions.getCurrent().getSession().setAttribute("sobieProfile", sobieProfileResp);
			}
		}
		sobieProfile = (SobieProfile) Executions.getCurrent().getDesktop().getSession().getAttribute("sobieProfile");
		dashboardPrivileges = new DashboardPrivileges(sobieProfile.getAccount().getPpId());
	}
	

/*	*//**
	 * Used to upload a new or change an existing profile picture
	 * @param ctx
	 *//*
	@GlobalCommand
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
            	profileImage.setImgId(sobieUtils.createImageId());	
            	if(getStoreSettings().getStoreLogo().getImgId().equals("0000000000000000001")){
            		SobieUtils sobieUtils = new SobieUtils();
                	storeLogo.setImgId(sobieUtils.createImageId());	
        		} else {
        			storeLogo.setImgId(getStoreSettings().getStoreLogo().getImgId());
        		}
            	getStoreSettings().setStoreLogo(storeLogo);
            	getSobieProfile().getCustomer().setProfileImage(profileImage);
            	customerAccountService.updatePersonalProfilePicture(profileImage, getSobieProfile().getCustomer().getCustNo(), getSobieProfile().getAccount().getAccNo());
            	Executions.getCurrent().getSession().setAttribute("sobieProfile", getSobieProfile());
            } else {
            	Messagebox.show("Not an image: " + media, "Error",
                        Messagebox.OK, Messagebox.ERROR);
            }
        }
	}*/
	
	@GlobalCommand
	public void refreshPage(){
		Executions.sendRedirect(null);
	}
	
	@GlobalCommand
	@NotifyChange("centerPageArea")
	public void openShoppingCart(){
		setCenterPageArea("/unsecure/widgets/shoppingCartWidget/shoppingCartWidget.zul");
	}
	
	@GlobalCommand
	@NotifyChange("centerPageArea")
	public void displayStoreMainLayoutPage(@BindingParam("store") Store store){
		setCenterPageArea("/unsecure/widgets/storeMainLayoutWidget/storeIndexLayoutWidget.zul");
		Executions.getCurrent().getDesktop().getSession().setAttribute("storeMainLayoutStoreNo", store.getStoreNo());
		BrowserWidget browserWidget = new BrowserWidget();
		browserWidget.setStoreNo(store.getStoreNo());
		browserWidget.setStoreAccNo(store.getStoreAccNo());
		Executions.getCurrent().getDesktop().getSession().setAttribute("browserWidget", browserWidget);
		Executions.getCurrent().getDesktop().getSession().setAttribute("addUpdateNewContentPage", "0");
	}
	
	@GlobalCommand
	@NotifyChange("centerPageArea")
	public void displayBrowserWidget(){
		setCenterPageArea("/unsecure/widgets/browserWidget/browserWidget.zul");
		Executions.getCurrent().getDesktop().getSession().setAttribute("browserWidget", null);
	}
	
	
	@GlobalCommand
	@NotifyChange("centerPageArea")
	public void displayProductPage(@BindingParam("product") Product product){
		setCenterPageArea("/unsecure/widgets/productDisplayWidget/productDisplayWidget.zul");
		Executions.getCurrent().getDesktop().getSession().setAttribute("productPageDataset", product);
	}
	
	/**
	 * Panel Controls ***************************************************************************************************
	 */
	
	@GlobalCommand
	@NotifyChange("showModal")
	public void showModel(){
		if(isShowModal()){
			setShowModal(false);
		} else {
			setShowModal(true);
		}
	}
	
	@GlobalCommand
	@NotifyChange("showEastPanel")
	public void showEastPanel(){
		if(isShowEastPanel()){
			setShowEastPanel(false);
		} else {
			setShowEastPanel(true);
		}
	}
	
	/**
	 ******************************************************************************************************************** 
	 */
	/**
	 * User DashBoard Controls ******************************************************************************************
	 */
		/**
		 * System Menu Controls
		 */
		
	
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openMyAccount(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/system/systemMyAccount.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openSearchedAccount(@BindingParam("customer") Customer searchedCustomer){
			setCenterPageArea("/secure/widgets/userDashboardWidget/system/systemMyAccount.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openSystemMessages(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/system/systemMessages.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openTaskNotifications(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/system/systemTaskNotifications.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openUsers(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/system/systemUsers.zul");
		}
		
		
		@Command
		@NotifyChange("systemMenuItemOpen")
		public void openSystemMenuItem(){
			if(isSystemMenuItemOpen()){
				setSystemMenuItemOpen(false);
			} else {
				setSystemMenuItemOpen(true);
			}
		}
		
		
		
	
		/**
		 * Inventory Menu Item Controls
		 */
		
		@Command
		@NotifyChange("inventoryMenuItemOpen")
		public void openInventoryMenuItem(){
			if(isInventoryMenuItemOpen()){
				setInventoryMenuItemOpen(false);
			} else {
				setInventoryMenuItemOpen(true);
			}
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openInventoryCategories(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/inventory/inventoryCategories.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openInventoryProduct(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/inventory/inventoryProducts.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openInventoryNetworkProduct(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/inventory/inventoryNetworkProducts.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openInventoryServices(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/inventory/inventoryServices.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openInventoryClassifieds(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/inventory/inventoryClassifieds.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openInventoryAttributes(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/inventory/inventoryAttributes.zul");
		}
		
		/**
		 * Network Menu Item Controls
		 */
		@Command
		@NotifyChange("networkMenuItemOpen")
		public void openNetworkMenuItem(){
			if(isNetworkMenuItemOpen()){
				setNetworkMenuItemOpen(false);
			} else {
				setNetworkMenuItemOpen(true);
			}
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openNetworkMessages(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/network/networkMessages.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openContactUs(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/network/networkMessages.zul");
		}
				
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openNetworkCustomers(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/network/customers/customerPage.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openNetworkAffiliates(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/network/affiliates/affiliatesManage.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openNetworkNetwork(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/network/myNetwork/myNetworkManage.zul");
		}
		
			/**
			 * NETWORK CUSTOMER CONTROLS
			 */
		
				@GlobalCommand
				@NotifyChange("centerPageArea")
				public void openCreateNewCustomer(){
					setCenterPageArea("/secure/widgets/userDashboardWidget/network/customers/customerNewPage.zul");
				}
				
			/**
			 * 
			 */
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openNetworkSellers(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/network/sellers/sellerList.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openNetworkSuppliers(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/network/suppliers/supplierList.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openNetworkShippers(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/network/shippers/shipperList.zul");
		}
		
		/**
		 * Store Menu Item Controls
		 */
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openStoreSettings(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/store/storeSettings/storeSettingsTabbedPanel.zul");
		}		
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openStoreContentPages(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/store/contentPages.zul");
			Executions.getCurrent().getDesktop().getSession().setAttribute("addUpdateNewContentPage", "1");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openAddNewContentPage(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/store/contentPagesEditPage.zul");
			Executions.getCurrent().getDesktop().getSession().setAttribute("addUpdateNewContentPage", "2");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void editContentPage(@BindingParam("contentPage") StoreContentPage storeContentPage){
			setCenterPageArea("/secure/widgets/userDashboardWidget/store/contentPagesEditPage.zul");
			Executions.getCurrent().getDesktop().getSession().setAttribute("addUpdateNewContentPage", "3");
			Executions.getCurrent().getDesktop().getSession().setAttribute("storeContentPage", storeContentPage);
			Executions.getCurrent().getDesktop().getSession().setAttribute("loadedStoreContentHeaderImageId", storeContentPage.getStoreContentPageHeaderImage().getImgId());
		}
		
		@Command
		@NotifyChange("storeMenuItemOpen")
		public void openStoreMenuItem(){
			if(isStoreMenuItemOpen()){
				setStoreMenuItemOpen(false);
			} else {
				setStoreMenuItemOpen(true);
			}
		}
		
		/**
		 * Sales Menu Item Controls
		 */
		
		@Command
		@NotifyChange("salesMenuItemOpen")
		public void openSalesMenuItem(){
			if(isSalesMenuItemOpen()){
				setSalesMenuItemOpen(false);
			} else {
				setSalesMenuItemOpen(true);
			}
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openSalesOrders(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/sales/salesOrdersList.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openViewSalesInvoices(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/sales/salesOrdersViewInvoices.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openViewSalesQuotations(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/sales/salesOrdersViewQuotations.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openViewSalesShipment(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/sales/salesOrdersViewShipment.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openViewSalesReturns(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/sales/salesOrdersViewCreditMemo.zul");
		}
		
		/**
		 * Purchasing Menu Item Controls
		 */
		
		@Command
		@NotifyChange("purchasesMenuItemOpen")
		public void openPurchasesMenuItem(){
			if(isPurchasesMenuItemOpen()){
				setPurchasesMenuItemOpen(false);
			} else {
				setPurchasesMenuItemOpen(true);
			}
		}
		
		@Command
		@NotifyChange("purchasesOrdersMenuItemOpen")
		public void openPurchasesOrdersMenuItem(){
			if(isPurchasesOrdersMenuItemOpen()){
				setPurchasesOrdersMenuItemOpen(false);
			} else {
				setPurchasesOrdersMenuItemOpen(true);
			}
		}
		
		/**
		 * Marketing Menu Item Controls
		 */
		
		@Command
		@NotifyChange("marketingMenuItemOpen")
		public void openMarketingMenuItem(){
			if(isMarketingMenuItemOpen()){
				setMarketingMenuItemOpen(false);
			} else {
				setMarketingMenuItemOpen(true);
			}
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openMarketingBanners(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/marketing/bannerAds/bannerAds.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openMarketingCoupons(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/marketing/coupons/coupons.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openMarketingNewsletters(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/marketing/newsletters/newsletters.zul");
		}
		
		@GlobalCommand
		@NotifyChange("centerPageArea")
		public void openMarketingCommentsRatings(){
			setCenterPageArea("/secure/widgets/userDashboardWidget/marketing/commentsRatings/commentsRatings.zul");
		}
		
		/**
		 * Applications Menu Item Controls
		 */
		
		@Command
		@NotifyChange("applicationsMenuItemOpen")
		public void openApplicationsMenuItem(){
			if(isApplicationsMenuItemOpen()){
				setApplicationsMenuItemOpen(false);
			} else {
				setApplicationsMenuItemOpen(true);
			}
		}
		
		/**
		 * Reports Menu Item Controls
		 */
		
		@Command
		@NotifyChange("reportsMenuItemOpen")
		public void openReportsMenuItem(){
			if(isReportsMenuItemOpen()){
				setReportsMenuItemOpen(false);
			} else {
				setReportsMenuItemOpen(true);
			}
		}
		
	/**
	 ******************************************************************************************************************** 
	 */
	
	/*@Command
	@NotifyChange({"menuPopupPosition","showMenuPopup"})
	public void showMessage(@ContextParam(ContextType.COMPONENT) Component comp, @BindingParam("pos") String pos) {
		String msg = null;

		if (comp instanceof Listbox) {
			Listitem item = ((Listbox) comp).getSelectedItem();

			if (item instanceof MenuItem)
				msg = ((MenuItem) item).getTitle();
				setMenuPopupPosition(((MenuItem) item).getWidth());
			((Listbox) comp).setSelectedItem(null);
			comp = item;
		} else
			return;
		
		
		setShowMenuPopup(true);
		*//**
		 * TODO: How to make the page busy and send notifications
		 *//*
		//Clients.showBusy("kaas");
		//Clients.showNotification(msg, Clients.NOTIFICATION_TYPE_INFO, comp, pos, 0);
	}*/
		
	public String getCenterPageArea() {
		return centerPageArea;
	}

	public void setCenterPageArea(String centerPageArea) {
		this.centerPageArea = centerPageArea;
	}

	public String getMultipleSobieProfilesPopup() {
		return multipleSobieProfilesPopup;
	}

	public void setMultipleSobieProfilesPopup(String multipleSobieProfilesPopup) {
		this.multipleSobieProfilesPopup = multipleSobieProfilesPopup;
	}

	public boolean isHomeAVisible() {
		return homeAVisible;
	}

	public void setHomeAVisible(boolean homeAVisible) {
		this.homeAVisible = homeAVisible;
	}

	public boolean isLogoutAVisible() {
		return logoutAVisible;
	}

	public void setLogoutAVisible(boolean logoutAVisible) {
		this.logoutAVisible = logoutAVisible;
	}

	public boolean isLoginDivVisible() {
		return loginDivVisible;
	}

	public void setLoginDivVisible(boolean loginDivVisible) {
		this.loginDivVisible = loginDivVisible;
	}

	public boolean isAnonymousMenuBarVisible() {
		return anonymousMenuBarVisible;
	}

	public void setAnonymousMenuBarVisible(boolean anonymousMenuBarVisible) {
		this.anonymousMenuBarVisible = anonymousMenuBarVisible;
	}

	public boolean isSobieUserMenuBarVisible() {
		return sobieUserMenuBarVisible;
	}

	public void setSobieUserMenuBarVisible(boolean sobieUserMenuBarVisible) {
		this.sobieUserMenuBarVisible = sobieUserMenuBarVisible;
	}

	/*public Breadcrump getBreadcrump() {
		return breadcrump;
	}

	public void setBreadcrump(Breadcrump breadcrump) {
		this.breadcrump = breadcrump;
	}*/

	public boolean isShowModal() {
		return showModal;
	}

	public void setShowModal(boolean showModal) {
		this.showModal = showModal;
	}

	public String getMenuPopupPosition() {
		return menuPopupPosition;
	}

	public void setMenuPopupPosition(String menuPopupPosition) {
		this.menuPopupPosition = menuPopupPosition;
	}

	public boolean isShowMenuPopup() {
		return showMenuPopup;
	}

	public void setShowMenuPopup(boolean showMenuPopup) {
		this.showMenuPopup = showMenuPopup;
	}

	public boolean isStoreMenuItemOpen() {
		return storeMenuItemOpen;
	}

	public void setStoreMenuItemOpen(boolean storeMenuItemOpen) {
		this.storeMenuItemOpen = storeMenuItemOpen;
	}

	public boolean isNetworkMenuItemOpen() {
		return networkMenuItemOpen;
	}

	public void setNetworkMenuItemOpen(boolean networkMenuItemOpen) {
		this.networkMenuItemOpen = networkMenuItemOpen;
	}

	public boolean isInventoryMenuItemOpen() {
		return inventoryMenuItemOpen;
	}

	public void setInventoryMenuItemOpen(boolean inventoryMenuItemOpen) {
		this.inventoryMenuItemOpen = inventoryMenuItemOpen;
	}

	public boolean isSalesMenuItemOpen() {
		return salesMenuItemOpen;
	}

	public void setSalesMenuItemOpen(boolean salesMenuItemOpen) {
		this.salesMenuItemOpen = salesMenuItemOpen;
	}

	public boolean isPurchasesMenuItemOpen() {
		return purchasesMenuItemOpen;
	}

	public void setPurchasesMenuItemOpen(boolean purchasesMenuItemOpen) {
		this.purchasesMenuItemOpen = purchasesMenuItemOpen;
	}

	public boolean isMarketingMenuItemOpen() {
		return marketingMenuItemOpen;
	}

	public void setMarketingMenuItemOpen(boolean marketingMenuItemOpen) {
		this.marketingMenuItemOpen = marketingMenuItemOpen;
	}

	public boolean isApplicationsMenuItemOpen() {
		return applicationsMenuItemOpen;
	}

	public void setApplicationsMenuItemOpen(boolean applicationsMenuItemOpen) {
		this.applicationsMenuItemOpen = applicationsMenuItemOpen;
	}

	public boolean isReportsMenuItemOpen() {
		return reportsMenuItemOpen;
	}

	public void setReportsMenuItemOpen(boolean reportsMenuItemOpen) {
		this.reportsMenuItemOpen = reportsMenuItemOpen;
	}

	public boolean isPurchasesOrdersMenuItemOpen() {
		return purchasesOrdersMenuItemOpen;
	}

	public void setPurchasesOrdersMenuItemOpen(boolean purchasesOrdersMenuItemOpen) {
		this.purchasesOrdersMenuItemOpen = purchasesOrdersMenuItemOpen;
	}

	public SobieProfile getSobieProfile() {
		return sobieProfile;
	}

	public void setSobieProfile(SobieProfile sobieProfile) {
		this.sobieProfile = sobieProfile;
	}

	public boolean isProfileHeaderVisible() {
		return profileHeaderVisible;
	}

	public void setProfileHeaderVisible(boolean profileHeaderVisible) {
		this.profileHeaderVisible = profileHeaderVisible;
	}

	public boolean isShowEastPanel() {
		return showEastPanel;
	}

	public void setShowEastPanel(boolean showEastPanel) {
		this.showEastPanel = showEastPanel;
	}

	public boolean isSystemMenuItemOpen() {
		return systemMenuItemOpen;
	}

	public void setSystemMenuItemOpen(boolean systemMenuItemOpen) {
		this.systemMenuItemOpen = systemMenuItemOpen;
	}


	public DashboardPrivileges getDashboardPrivileges() {
		return dashboardPrivileges;
	}


	public void setDashboardPrivileges(DashboardPrivileges dashboardPrivileges) {
		this.dashboardPrivileges = dashboardPrivileges;
	}

}