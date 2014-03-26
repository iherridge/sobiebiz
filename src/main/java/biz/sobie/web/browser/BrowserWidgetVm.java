package biz.sobie.web.browser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import biz.sobie.web.beans.Product;
import biz.sobie.web.beans.Service;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.beans.Store;
import biz.sobie.web.likes.LikeService;
import biz.sobie.web.services.ProductService;
import biz.sobie.web.services.ServiceProviderService;
import biz.sobie.web.services.StoreService;

public class BrowserWidgetVm {

	private SobieProfile sobieProfile;
	private Product selectedProduct;
	private List<Product> productList;
	private List<Store> storeList;
	private List<Service> serviceList;
	private List<String> categoryList;
	private String selectedProductView;
	private String selectedStoreView;
	private String selectedServiceView;
	private String productListView;
	
	private List<Product> productGridRow1;
	private List<Product> productGridRow2;
	private List<Product> productGridRow3;
	private List<Product> productGridRow4;
	private List<Product> productGridRow5;
	
	private List<Store> storeGridRow1;
	private List<Store> storeGridRow2;
	private List<Store> storeGridRow3;
	private List<Store> storeGridRow4;
	private List<Store> storeGridRow5;
	
	private boolean selectedStoreViewVisible;
	private boolean selectedClassifiedsViewVisible;
	private String browserLeftWidgetColumnSrc;
	
	@WireVariable ProductService productService;
	@WireVariable StoreService storeService;
	@WireVariable ServiceProviderService serviceProviderService;
	@WireVariable LikeService likeService;

	@Init
	public void init() {
		sobieProfile = (SobieProfile)Executions.getCurrent().getSession().getAttribute("sobieProfile");
		BrowserWidget browserWidget = (BrowserWidget)Executions.getCurrent().getSession().getAttribute("browserWidget");
		if(browserWidget == null){
			productList = productService.retrieveTopSellingProductList(sobieProfile);
	    	storeList = storeService.retrieveTopSellingStoreList(sobieProfile);
	    	serviceList = serviceProviderService.retrieveTopSellingServiceList(sobieProfile);
	    	setCategoryList(productService.getProductCategories());
	    	setSelectedStoreViewVisible(true);
	    	setSelectedClassifiedsViewVisible(true);
	    	setBrowserLeftWidgetColumnSrc("/unsecure/widgets/browserWidget/categories.zul");
		} else {
			if(browserWidget.getStoreNo() != null){
				setSelectedStoreViewVisible(false);
				setSelectedClassifiedsViewVisible(false);
				productList = productService.retrieveStoreProducts(browserWidget.getStoreAccNo());
				setBrowserLeftWidgetColumnSrc("/unsecure/widgets/storeMainLayoutWidget/storeBrowserWidget/storeBrowserLeftWidgetColumn.zul");
			}
		}
    	
    	selectedProductView = "/unsecure/widgets/browserWidget/productList.zul";
    	selectedStoreView = "/unsecure/widgets/browserWidget/storeList.zul";
    	selectedServiceView = "/unsecure/widgets/browserWidget/serviceList.zul";
    	productListView = "/resources/img/browserWidget/List_view.png";
    	productGridRow1 = null;
    	productGridRow2 = null;
    	productGridRow3 = null;
    	productGridRow4 = null;
    	productGridRow5 = null;
    	setStoreGridRow1(null);
    	setStoreGridRow2(null);
    	setStoreGridRow3(null);
    	setStoreGridRow4(null);
    	setStoreGridRow5(null);
 	}
	
	@Command
	@NotifyChange("productList")
	public void likeProduct(@BindingParam("product") Product product){
		try{
			likeService.like(product.getProdId(), product.getOwnerProdId(), "", getSobieProfile().getAccount().getAccNo());
			for(int x = 0; x < getProductList().size();x++){
				if(product.getProdId().equals(getProductList().get(x).getProdId())){
					int likes = getProductList().get(x).getProdLikes();
					likes++;
					getProductList().get(x).setProdLikes(likes);
					break;
				}
			}
		} catch (DuplicateKeyException e) {
			Clients.showNotification("You have already Liked this product!");
		}
	}
	
	@Command
	@NotifyChange({"selectedProductView","selectedStoreView","productListView"})
	public void changeBrowserViewLayout(){
		if(getSelectedProductView().equals("/unsecure/widgets/browserWidget/productList.zul")){
			setSelectedProductView("/unsecure/widgets/browserWidget/productGrid.zul");
			setSelectedStoreView("/unsecure/widgets/browserWidget/storeGrid.zul");
			setProductListView("/resources/img/browserWidget/Grid_view.png");
			/**
			 * Product Grid
			 */
			ArrayList<Product> tempProdGridRow = new ArrayList<Product>();
			if(getProductList().size() >= 1){
				tempProdGridRow.add(productList.get(0));
				setProductGridRow1(tempProdGridRow);
			}
			if(getProductList().size() >= 2){
				tempProdGridRow.add(productList.get(1));
				setProductGridRow1(tempProdGridRow);
			}
			if(getProductList().size() >= 3){
				tempProdGridRow = new ArrayList<Product>();
				tempProdGridRow.add(productList.get(2));
				setProductGridRow2(tempProdGridRow);
			}
			if(getProductList().size() >= 4){
				tempProdGridRow.add(productList.get(3));
				setProductGridRow2(tempProdGridRow);
			}
			if(getProductList().size() >= 5){
				tempProdGridRow = new ArrayList<Product>();
				tempProdGridRow.add(productList.get(4));
				setProductGridRow3(tempProdGridRow);
			}
			if(getProductList().size() >= 6){
				tempProdGridRow.add(productList.get(5));
				setProductGridRow3(tempProdGridRow);
			}
			if(getProductList().size() >= 7){
				tempProdGridRow = new ArrayList<Product>();
				tempProdGridRow.add(productList.get(6));
				setProductGridRow4(tempProdGridRow);
			}
			if(getProductList().size() >= 8){
				tempProdGridRow.add(productList.get(7));
				setProductGridRow4(tempProdGridRow);
			}
			if(getProductList().size() >= 9){
				tempProdGridRow = new ArrayList<Product>();
				tempProdGridRow.add(productList.get(8));
				setProductGridRow5(tempProdGridRow);
			}
			if(getProductList().size() >= 10){
				tempProdGridRow.add(productList.get(9));
				setProductGridRow5(tempProdGridRow);
			}
			/**
			 * Store Grid
			 */
			ArrayList<Store> tempStoreGridRow = new ArrayList<Store>();
			if(getStoreList().size() >= 1){
				tempStoreGridRow.add(storeList.get(0));
				setStoreGridRow1(tempStoreGridRow);
			}
			if(getStoreList().size() >= 2){
				tempStoreGridRow.add(storeList.get(1));
				setStoreGridRow1(tempStoreGridRow);
			}
			if(getStoreList().size() >= 3){
				tempStoreGridRow = new ArrayList<Store>();
				tempStoreGridRow.add(storeList.get(2));
				setStoreGridRow2(tempStoreGridRow);
			}
			if(getStoreList().size() >= 4){
				tempStoreGridRow.add(storeList.get(3));
				setStoreGridRow2(tempStoreGridRow);
			}
			if(getStoreList().size() >= 5){
				tempStoreGridRow = new ArrayList<Store>();
				tempStoreGridRow.add(storeList.get(4));
				setStoreGridRow3(tempStoreGridRow);
			}
			if(getStoreList().size() >= 6){
				tempStoreGridRow.add(storeList.get(5));
				setStoreGridRow3(tempStoreGridRow);
			}
			if(getStoreList().size() >= 7){
				tempStoreGridRow = new ArrayList<Store>();
				tempStoreGridRow.add(storeList.get(6));
				setStoreGridRow4(tempStoreGridRow);
			}
			if(getStoreList().size() >= 8){
				tempStoreGridRow.add(storeList.get(7));
				setStoreGridRow4(tempStoreGridRow);
			}
			if(getStoreList().size() >= 9){
				tempStoreGridRow = new ArrayList<Store>();
				tempStoreGridRow.add(storeList.get(8));
				setStoreGridRow5(tempStoreGridRow);
			}
			if(getStoreList().size() >= 10){
				tempStoreGridRow.add(storeList.get(9));
				setStoreGridRow5(tempStoreGridRow);
			}
		} else {
			setSelectedProductView("/unsecure/widgets/browserWidget/productList.zul");
			setSelectedStoreView("/unsecure/widgets/browserWidget/storeList.zul");
			setProductListView("/resources/img/browserWidget/List_view.png");
		}
	}
	
	@GlobalCommand @NotifyChange({"productList", "storeList"})
	public void searchProducts(@BindingParam("searchText") String searchText){
		setProductList(productService.searchProduct(searchText));
		setStoreList(storeService.searchProductStoreOwner(searchText));
	}

	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}

	public List<String> getCategoryList() {
		return categoryList;
	}

	public void setSelectedStoreView(String selectedStoreView) {
		this.selectedStoreView = selectedStoreView;
	}

	public String getSelectedStoreView() {
		return selectedStoreView;
	}

	public void setSelectedProductView(String selectedProductView) {
		this.selectedProductView = selectedProductView;
	}

	public String getSelectedProductView() {
		return selectedProductView;
	}

	public String getProductListView() {
		return productListView;
	}

	public void setProductListView(String productListView) {
		this.productListView = productListView;
	}


	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}

	public List<Store> getStoreList() {
		return storeList;
	}
	
	public List<Product> getProductGridRow1() {
		return productGridRow1;
	}

	public void setProductGridRow1(List<Product> productGridRow1) {
		this.productGridRow1 = productGridRow1;
	}

	public List<Product> getProductGridRow2() {
		return productGridRow2;
	}

	public void setProductGridRow2(List<Product> productGridRow2) {
		this.productGridRow2 = productGridRow2;
	}

	public List<Product> getProductGridRow3() {
		return productGridRow3;
	}

	public void setProductGridRow3(List<Product> productGridRow3) {
		this.productGridRow3 = productGridRow3;
	}

	public List<Product> getProductGridRow4() {
		return productGridRow4;
	}

	public void setProductGridRow4(List<Product> productGridRow4) {
		this.productGridRow4 = productGridRow4;
	}

	public List<Product> getProductGridRow5() {
		return productGridRow5;
	}

	public void setProductGridRow5(List<Product> productGridRow5) {
		this.productGridRow5 = productGridRow5;
	}

	public List<Store> getStoreGridRow1() {
		return storeGridRow1;
	}

	public void setStoreGridRow1(List<Store> storeGridRow1) {
		this.storeGridRow1 = storeGridRow1;
	}

	public List<Store> getStoreGridRow2() {
		return storeGridRow2;
	}

	public void setStoreGridRow2(List<Store> storeGridRow2) {
		this.storeGridRow2 = storeGridRow2;
	}

	public List<Store> getStoreGridRow3() {
		return storeGridRow3;
	}

	public void setStoreGridRow3(List<Store> storeGridRow3) {
		this.storeGridRow3 = storeGridRow3;
	}

	public List<Store> getStoreGridRow4() {
		return storeGridRow4;
	}

	public void setStoreGridRow4(List<Store> storeGridRow4) {
		this.storeGridRow4 = storeGridRow4;
	}

	public List<Store> getStoreGridRow5() {
		return storeGridRow5;
	}

	public void setStoreGridRow5(List<Store> storeGridRow5) {
		this.storeGridRow5 = storeGridRow5;
	}

	public boolean isSelectedStoreViewVisible() {
		return selectedStoreViewVisible;
	}

	public void setSelectedStoreViewVisible(boolean selectedStoreViewVisible) {
		this.selectedStoreViewVisible = selectedStoreViewVisible;
	}

	public boolean isSelectedClassifiedsViewVisible() {
		return selectedClassifiedsViewVisible;
	}

	public void setSelectedClassifiedsViewVisible(
			boolean selectedClassifiedsViewVisible) {
		this.selectedClassifiedsViewVisible = selectedClassifiedsViewVisible;
	}

	public String getBrowserLeftWidgetColumnSrc() {
		return browserLeftWidgetColumnSrc;
	}

	public void setBrowserLeftWidgetColumnSrc(String browserLeftWidgetColumnSrc) {
		this.browserLeftWidgetColumnSrc = browserLeftWidgetColumnSrc;
	}

	public SobieProfile getSobieProfile() {
		return sobieProfile;
	}

	public void setSobieProfile(SobieProfile sobieProfile) {
		this.sobieProfile = sobieProfile;
	}

	public String getSelectedServiceView() {
		return selectedServiceView;
	}

	public void setSelectedServiceView(String selectedServiceView) {
		this.selectedServiceView = selectedServiceView;
	}

	public List<Service> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<Service> serviceList) {
		this.serviceList = serviceList;
	}

}
