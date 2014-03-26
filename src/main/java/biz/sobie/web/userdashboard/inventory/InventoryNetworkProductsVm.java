package biz.sobie.web.userdashboard.inventory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import biz.sobie.web.beans.Account;
import biz.sobie.web.beans.Customer;
import biz.sobie.web.beans.MessageBean;
import biz.sobie.web.beans.Product;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.services.CustomerAccountService;
import biz.sobie.web.services.MessageService;
import biz.sobie.web.services.ProductService;
import biz.sobie.web.utils.SobieUtils;


public class InventoryNetworkProductsVm {

	SobieProfile sobieProfile;
	List<Product> networkProducts;
	Product selectedProduct;
	
	@WireVariable
	ProductService productService;
	@WireVariable
	MessageService messageService;
	@WireVariable
	CustomerAccountService customerAccountService;
	
	boolean editProductOpen = false;
	
	@Init
	public void init() {
		sobieProfile = (SobieProfile) Executions.getCurrent().getDesktop().getSession().getAttribute("sobieProfile");
		if(sobieProfile != null) {
			networkProducts = (List<Product>)productService.retrieveInventoryNetworkProducts(sobieProfile.getAccount().getAccNo());
		}
		selectedProduct = new Product();
	}
	
	@Command
	public void addProductToSellerShop(@BindingParam("product") Product product){
		productService.addProductToSellerShop(product, sobieProfile.getAccount().getAccNo());
	}
	
	@Command
	@NotifyChange({"selectedProduct","editProductOpen"})
	public void editProduct(@BindingParam("product") Product product){
		setSelectedProduct(product);
		setEditProductOpen(true);
	}
	
	@Command
	@NotifyChange({"selectedProduct","editProductOpen"})
	public void closeEditScreen(){
		setSelectedProduct(null);
		setEditProductOpen(false);
	}
	
	@Command
	public void saveProductChanges(){
		Product supplierProduct = productService.retrieveSupplierProduct(getSelectedProduct().getProdId());
		ProductHistory history = new ProductHistory();
		history.setProdId(selectedProduct.getProdId());
		Date date = new Date(System.currentTimeMillis());
		history.setDateTime(date);
		if(!(supplierProduct.getProdBrand() == null ? "".equals(selectedProduct.getProdBrand() == null ? "" : selectedProduct.getProdBrand()) 
				: supplierProduct.getProdBrand().equals(selectedProduct.getProdBrand() == null ? "" : selectedProduct.getProdBrand()))){
			history.setFieldName("Product Brand");
			history.setFieldValueFrom(supplierProduct.getProdBrand());
			history.setFieldValueTo(selectedProduct.getProdBrand());
			productService.addProductHistoryRecord(history);
		}
		if(!(supplierProduct.getProdCategory() == null ? "".equals(selectedProduct.getProdCategory() == null ? "" : selectedProduct.getProdCategory()) 
				: supplierProduct.getProdCategory().equals(selectedProduct.getProdCategory() == null ? "" : selectedProduct.getProdCategory()))){
			history.setFieldName("Product Category");
			history.setFieldValueFrom(supplierProduct.getProdCategory());
			history.setFieldValueTo(selectedProduct.getProdCategory());
			productService.addProductHistoryRecord(history);
		}
		if(!(supplierProduct.getProdDesc() == null ? "".equals(selectedProduct.getProdDesc() == null ? "" : selectedProduct.getProdDesc()) 
				: supplierProduct.getProdDesc().equals(selectedProduct.getProdDesc() == null ? "" : selectedProduct.getProdDesc()))){
			history.setFieldName("Product Description");
			history.setFieldValueFrom(supplierProduct.getProdDesc());
			history.setFieldValueTo(selectedProduct.getProdDesc());
			productService.addProductHistoryRecord(history);
		}
		if(!(supplierProduct.getProdFeatures() == null ? "".equals(selectedProduct.getProdFeatures() == null ? "" : selectedProduct.getProdFeatures()) 
				: supplierProduct.getProdFeatures().equals(selectedProduct.getProdFeatures() == null ? "" : selectedProduct.getProdFeatures()))){
			history.setFieldName("Product Features");
			history.setFieldValueFrom(supplierProduct.getProdFeatures());
			history.setFieldValueTo(selectedProduct.getProdFeatures());
			productService.addProductHistoryRecord(history);
		}
		if(!(supplierProduct.getProdName() == null ? "".equals(selectedProduct.getProdName() == null ? "" : selectedProduct.getProdName()) 
				: supplierProduct.getProdName().equals(selectedProduct.getProdName() == null ? "" : selectedProduct.getProdName()))){
			history.setFieldName("Product Name");
			history.setFieldValueFrom(supplierProduct.getProdName());
			history.setFieldValueTo(selectedProduct.getProdName());
			productService.addProductHistoryRecord(history);
		}
		if(!(supplierProduct.getProdSkuPartNo() == null ? "".equals(selectedProduct.getProdSkuPartNo() == null ? "" : selectedProduct.getProdSkuPartNo()) 
				: supplierProduct.getProdSkuPartNo().equals(selectedProduct.getProdSkuPartNo() == null ? "" : selectedProduct.getProdSkuPartNo()))){
			history.setFieldName("Product SKU Part No");
			history.setFieldValueFrom(supplierProduct.getProdSkuPartNo());
			history.setFieldValueTo(selectedProduct.getProdSkuPartNo());
			productService.addProductHistoryRecord(history);
		}
		if(!(supplierProduct.getTaxClassId() == null ? "".equals(selectedProduct.getTaxClassId() == null ? "" : selectedProduct.getTaxClassId()) 
				: supplierProduct.getTaxClassId().equals(selectedProduct.getTaxClassId() == null ? "" : selectedProduct.getTaxClassId()))){
			history.setFieldName("Tax Class");
			history.setFieldValueFrom(supplierProduct.getTaxClassId());
			history.setFieldValueTo(selectedProduct.getTaxClassId());
			productService.addProductHistoryRecord(history);
		}
		if(!(supplierProduct.getProdShippingTypeId() == null ? "".equals(selectedProduct.getProdShippingTypeId() == null ? "" : selectedProduct.getProdShippingTypeId()) 
				: supplierProduct.getProdShippingTypeId().equals(selectedProduct.getProdShippingTypeId() == null ? "" : selectedProduct.getProdShippingTypeId()))){
			history.setFieldName("Product Shipping Type");
			history.setFieldValueFrom(supplierProduct.getProdShippingTypeId());
			history.setFieldValueTo(selectedProduct.getProdShippingTypeId());
			productService.addProductHistoryRecord(history);
		}
		if(!(supplierProduct.getProdSpecification() == null ? "".equals(selectedProduct.getProdSpecification() == null ? "" : selectedProduct.getProdSpecification()) 
				: supplierProduct.getProdSpecification().equals(selectedProduct.getProdSpecification() == null ? "" : selectedProduct.getProdSpecification()))){
			history.setFieldName("Product Specification");
			history.setFieldValueFrom(supplierProduct.getProdSpecification());
			history.setFieldValueTo(selectedProduct.getProdSpecification());
			productService.addProductHistoryRecord(history);
		}
		if(!(supplierProduct.getProdShippingInfo() == null ? "".equals(selectedProduct.getProdShippingInfo() == null ? "" : selectedProduct.getProdShippingInfo()) 
				: supplierProduct.getProdShippingInfo().equals(selectedProduct.getProdShippingInfo() == null ? "" : selectedProduct.getProdShippingInfo()))){
			history.setFieldName("Product Shipping Info	");
			history.setFieldValueFrom(supplierProduct.getProdShippingInfo());
			history.setFieldValueTo(selectedProduct.getProdShippingInfo());
			productService.addProductHistoryRecord(history);
		}
		if(!(supplierProduct.getProdYouTubeUrl() == null ? "".equals(selectedProduct.getProdYouTubeUrl() == null ? "" : selectedProduct.getProdYouTubeUrl()) 
				: supplierProduct.getProdYouTubeUrl().equals(selectedProduct.getProdYouTubeUrl() == null ? "" : selectedProduct.getProdYouTubeUrl()))){
			history.setFieldName("Product YouTube URL");
			history.setFieldValueFrom(supplierProduct.getProdYouTubeUrl());
			history.setFieldValueTo(selectedProduct.getProdYouTubeUrl());
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.getProdSalePrice() != selectedProduct.getProdSalePrice() ){
			history.setFieldName("Product Sale Price");
			history.setFieldValueFrom(String.valueOf(supplierProduct.getProdSalePrice()));
			history.setFieldValueTo(String.valueOf(selectedProduct.getProdSalePrice()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.getProdWholesalePrice() != selectedProduct.getProdWholesalePrice() ){
			history.setFieldName("Product Wholesale Price");
			history.setFieldValueFrom(String.valueOf(supplierProduct.getProdWholesalePrice()));
			history.setFieldValueTo(String.valueOf(selectedProduct.getProdWholesalePrice()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.getProdQtyImedAvail() != selectedProduct.getProdQtyImedAvail() ){
			history.setFieldName("Product Quantity Immediately Available");
			history.setFieldValueFrom(String.valueOf(supplierProduct.getProdQtyImedAvail()));
			history.setFieldValueTo(String.valueOf(selectedProduct.getProdQtyImedAvail()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.getProdMinPurchaseQty() != selectedProduct.getProdMinPurchaseQty() ){
			history.setFieldName("Product Minimum Purchase Quantity");
			history.setFieldValueFrom(String.valueOf(supplierProduct.getProdMinPurchaseQty()));
			history.setFieldValueTo(String.valueOf(selectedProduct.getProdMinPurchaseQty()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.getProdMaxPurchaseQty() != selectedProduct.getProdMaxPurchaseQty() ){
			history.setFieldName("Product Maximum Purchase Quantity");
			history.setFieldValueFrom(String.valueOf(supplierProduct.getProdMaxPurchaseQty()));
			history.setFieldValueTo(String.valueOf(selectedProduct.getProdMaxPurchaseQty()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.getProdHeight() != selectedProduct.getProdHeight() ){
			history.setFieldName("Product Height");
			history.setFieldValueFrom(String.valueOf(supplierProduct.getProdHeight()));
			history.setFieldValueTo(String.valueOf(selectedProduct.getProdHeight()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.getProdHeight() != selectedProduct.getProdHeight() ){
			history.setFieldName("Product Height");
			history.setFieldValueFrom(String.valueOf(supplierProduct.getProdHeight()));
			history.setFieldValueTo(String.valueOf(selectedProduct.getProdHeight()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.getProdWidth() != selectedProduct.getProdWidth() ){
			history.setFieldName("Product Width");
			history.setFieldValueFrom(String.valueOf(supplierProduct.getProdHeight()));
			history.setFieldValueTo(String.valueOf(selectedProduct.getProdHeight()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.getProdLenght() != selectedProduct.getProdLenght() ){
			history.setFieldName("Product Lenght");
			history.setFieldValueFrom(String.valueOf(supplierProduct.getProdLenght()));
			history.setFieldValueTo(String.valueOf(selectedProduct.getProdLenght()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.getProdWeight() != selectedProduct.getProdWeight() ){
			history.setFieldName("Product Weight");
			history.setFieldValueFrom(String.valueOf(supplierProduct.getProdWeight()));
			history.setFieldValueTo(String.valueOf(selectedProduct.getProdWeight()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.isProdAddToCart() != selectedProduct.isProdAddToCart() ){
			history.setFieldName("Add to Cart");
			history.setFieldValueFrom(String.valueOf(supplierProduct.isProdAddToCart()));
			history.setFieldValueTo(String.valueOf(selectedProduct.isProdAddToCart()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.isProdFreeShipping() != selectedProduct.isProdFreeShipping() ){
			history.setFieldName("Free Shipping");
			history.setFieldValueFrom(String.valueOf(supplierProduct.isProdFreeShipping()));
			history.setFieldValueTo(String.valueOf(selectedProduct.isProdFreeShipping()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.isProdShipSeperate() != selectedProduct.isProdShipSeperate() ){
			history.setFieldName("Ship Seperate");
			history.setFieldValueFrom(String.valueOf(supplierProduct.isProdShipSeperate()));
			history.setFieldValueTo(String.valueOf(selectedProduct.isProdShipSeperate()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.isProdShippingEnabled() != selectedProduct.isProdShippingEnabled() ){
			history.setFieldName("Shipping Enabled");
			history.setFieldValueFrom(String.valueOf(supplierProduct.isProdShippingEnabled()));
			history.setFieldValueTo(String.valueOf(selectedProduct.isProdShippingEnabled()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.isProdEnabled() != selectedProduct.isProdEnabled() ){
			history.setFieldName("Product Enabled");
			history.setFieldValueFrom(String.valueOf(supplierProduct.isProdEnabled()));
			history.setFieldValueTo(String.valueOf(selectedProduct.isProdEnabled()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.isProdCommentsEnabled() != selectedProduct.isProdCommentsEnabled() ){
			history.setFieldName("Comments Enabled");
			history.setFieldValueFrom(String.valueOf(supplierProduct.isProdCommentsEnabled()));
			history.setFieldValueTo(String.valueOf(selectedProduct.isProdCommentsEnabled()));
			productService.addProductHistoryRecord(history);
		}
		if(supplierProduct.isProdQnaEnabled() != selectedProduct.isProdQnaEnabled() ){
			history.setFieldName("Q&A Enabled");
			history.setFieldValueFrom(String.valueOf(supplierProduct.isProdQnaEnabled()));
			history.setFieldValueTo(String.valueOf(selectedProduct.isProdQnaEnabled()));
			productService.addProductHistoryRecord(history);
		}
		productService.updateProduct(getSelectedProduct(), getSelectedProduct().getOwnerAccNo());
	}
	
	@Command
	@NotifyChange("networkProducts")
	public void activateProduct(@BindingParam("product") Product product){
		if(product.getProdStatus().equals("1")){ //Deactivate Product
			productService.deactivateProduct(product.getProdId(), sobieProfile.getAccount().getStoreNo());
			/*for(int x = 0; x < getNetworkProducts().size(); x++){
				if(getNetworkProducts().get(x).getProdId().equals(product.getProdId())){
					if(product.getProdStatus().equals("1")){
						getNetworkProducts().get(x).setProdStatus("0");
					}
				}
			}*/
			networkProducts = (List<Product>)productService.retrieveInventoryNetworkProducts(sobieProfile.getAccount().getAccNo());
		} else if(product.getProdStatus().equals("0")){ //Send Product for approval
			
			/**
			 * Get product owner details
			 */
			String ownerCustNo = customerAccountService.getProductOwnerCustNo(product.getOwnerAccNo());
			
			Customer customer = sobieProfile.getCustomer();
			Account account = sobieProfile.getAccount();
			SobieUtils sobieUtils = new SobieUtils();
			
			MessageBean msgBean = new MessageBean();
			msgBean.setFromCustNo(customer.getCustNo());
			msgBean.setFromAccNo(account.getAccNo());
			msgBean.setMsgbxId(sobieUtils.createMsgbxId());
			msgBean.setMsgId(sobieUtils.createMsgId());
			msgBean.setToCustNos(ownerCustNo);
			msgBean.setToAccNos(product.getOwnerAccNo());
			msgBean.setMsgSubject("Request to resell product");
			msgBean.setMsgType("RP");
			msgBean.setMsgRpProdId(product.getProdId());
			msgBean.setMsgRpStoreNo(sobieProfile.getAccount().getStoreNo());
			msgBean.setMsgRpAccNo(sobieProfile.getAccount().getAccNo());
						
	    	if(messageService.createNewMessage(sobieProfile, msgBean).equals("Successful")){
	    		productService.requestForApprovalProduct(product.getProdId(), sobieProfile.getAccount().getStoreNo());
	    	} else {
	    		
	    	}
			
			
		}
		for(int x = 0; x < getNetworkProducts().size(); x++){
			if(getNetworkProducts().get(x).getProdId().equals(product.getProdId())){
				if(product.getProdStatus().equals("0")){
					getNetworkProducts().get(x).setProdStatus("2");
				}
			}
		}
	}
	
	@Command
	@NotifyChange("networkProducts")
	public void deleteProduct(@BindingParam("product") Product product){
		String storeNo = sobieProfile.getAccount().getStoreNo();
		String prodId = product.getProdId();
		productService.deleteProduct(prodId, storeNo);
		networkProducts = productService.retrieveInventoryNetworkProducts(sobieProfile.getAccount().getAccNo());
	}
	
	public boolean isEditProductOpen() {
		return editProductOpen;
	}

	public void setEditProductOpen(boolean editProductOpen) {
		this.editProductOpen = editProductOpen;
	}

	public List<Product> getNetworkProducts() {
		return networkProducts;
	}

	public void setNetworkProducts(List<Product> networkProducts) {
		this.networkProducts = networkProducts;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}
	
	
}
