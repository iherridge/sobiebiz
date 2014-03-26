package biz.sobie.web.store;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import biz.sobie.web.beans.Store;
import biz.sobie.web.services.ProductService;
import biz.sobie.web.services.StoreService;

public class StoreMainLayoutVm {

	private Store storeMainLayout;
	private boolean storeMapOpen;
	private String showHideMapText;
	private String storeContentArea;
	private boolean featuredProductsVisible;
	@WireVariable StoreService storeService;
	@WireVariable ProductService productService;
	
	@Init
	public void init() {
		storeMainLayout = (Store) storeService.retrieveStoreMainLayout((String)Executions.getCurrent().getDesktop().getSession().getAttribute("storeMainLayoutStoreNo"));
		getStoreMainLayout().setFeaturedProductList(productService.retrieveStoreFeaturedProducts((String)Executions.getCurrent().getDesktop().getSession().getAttribute("storeMainLayoutStoreNo")));
		storeMapOpen = false;
		showHideMapText = "Show map";
		storeContentArea = "/unsecure/widgets/browserWidget/browserWidget.zul";
		featuredProductsVisible = true;
	}
	
	@GlobalCommand
	@NotifyChange({"storeContentArea","featuredProductsVisible"})
	public void openMenuItem(@BindingParam("menuItem") StoreMenuItem menuItem){
		
		if(menuItem.getMenuItem().equals("Home")){
			setFeaturedProductsVisible(true);
			setStoreContentArea("/unsecure/widgets/browserWidget/browserWidget.zul");
		} else if(menuItem.getStoreContentPage() != null){
			setFeaturedProductsVisible(false);
			setStoreContentArea("/unsecure/widgets/storeMainLayoutWidget/storeContentPageViewer.zul");
			Executions.getCurrent().getDesktop().getSession().setAttribute("addUpdateNewContentPage", "3");
			Executions.getCurrent().getDesktop().getSession().setAttribute("storeContentPage", menuItem.getStoreContentPage());
		}
	}

	@Command
	@NotifyChange({"storeMapOpen","showHideMapText"})
	public void openCloseStoreMap(){
		if(isStoreMapOpen()){
			setStoreMapOpen(false);
			showHideMapText = "Show map";
		} else {
			setStoreMapOpen(true);
			showHideMapText = "Hide map";
		}
	}
	
	public Store getStoreMainLayout() {
		return storeMainLayout;
	}

	public void setStoreMainLayout(Store storeMainLayout) {
		this.storeMainLayout = storeMainLayout;
	}

	public boolean isStoreMapOpen() {
		return storeMapOpen;
	}

	public void setStoreMapOpen(boolean storeMapOpen) {
		this.storeMapOpen = storeMapOpen;
	}

	public String getShowHideMapText() {
		return showHideMapText;
	}

	public void setShowHideMapText(String showHideMapText) {
		this.showHideMapText = showHideMapText;
	}

	public String getStoreContentArea() {
		return storeContentArea;
	}

	public void setStoreContentArea(String storeContentArea) {
		this.storeContentArea = storeContentArea;
	}

	public boolean isFeaturedProductsVisible() {
		return featuredProductsVisible;
	}

	public void setFeaturedProductsVisible(boolean featuredProductsVisible) {
		this.featuredProductsVisible = featuredProductsVisible;
	}

}
