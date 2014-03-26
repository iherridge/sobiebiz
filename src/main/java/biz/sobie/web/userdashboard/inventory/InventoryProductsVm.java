package biz.sobie.web.userdashboard.inventory;

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

import biz.sobie.web.beans.Account;
import biz.sobie.web.beans.Customer;
import biz.sobie.web.beans.MessageBean;
import biz.sobie.web.beans.Product;
import biz.sobie.web.beans.SobieImage;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.services.MessageService;
import biz.sobie.web.services.ProductService;
import biz.sobie.web.utils.SobieUtils;


public class InventoryProductsVm {

	SobieProfile sobieProfile;
	List<Product> ownedProducts;
	
	Product addUpdateProduct;
	Product productToBeDeleted;
	
	private boolean addProductOpen;
	private boolean deleteProductPopupVisible;
	private boolean editProduct;

	@WireVariable
	ProductService productService;
	@WireVariable
	MessageService messageService;
	
	@Init
	public void init() {
		addProductOpen = false;
		deleteProductPopupVisible = false;
		editProduct = false;
		sobieProfile = (SobieProfile) Executions.getCurrent().getDesktop().getSession().getAttribute("sobieProfile");
		addUpdateProduct = new Product();
		addUpdateProduct.setProdStatus("0");
		ownedProducts = (List<Product>)productService.retrieveStoreProducts(sobieProfile.getAccount().getAccNo());
		
	}
	
	@Command
	@NotifyChange({"addUpdateProduct","addProductOpen"})
	public void editProduct(@BindingParam("product") Product product){
		setAddUpdateProduct(product);
		setAddProductOpen(true);
		setEditProduct(true);
	}
	
	@Command
	@NotifyChange({"addUpdateProduct","addProductOpen"})
	public void openAddNewProduct(){
		setAddUpdateProduct(new Product());
		setAddProductOpen(true);
	}
	
	@Command
	@NotifyChange({"addUpdateProduct","addProductOpen"})
	public void closeAddNewProduct(){
		setAddProductOpen(false);
		setEditProduct(false);
	}
	
	@Command
	@NotifyChange({"ownedProducts","addUpdateProduct","addProductOpen"})
	public void saveNewProduct(){
		if(!isEditProduct()){
			addUpdateProduct.setProdStatus("0");
			productService.saveNewProduct(addUpdateProduct, sobieProfile.getAccount().getAccNo());
			ArrayList<Product> tempProducts = new ArrayList<Product>();
			if(ownedProducts != null){
				tempProducts = (ArrayList<Product>) ownedProducts;
				
			} else {
				tempProducts = new ArrayList<Product>();
			}
			tempProducts.add(addUpdateProduct);
			setOwnedProducts(tempProducts);
			setAddUpdateProduct(new Product());
		} else {
			productService.updateProduct(getAddUpdateProduct(), sobieProfile.getAccount().getAccNo());
		}
		setAddProductOpen(false);
	}
	
	@Command
	@NotifyChange("addUpdateProduct")
	public void uploadProductImage(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx){
		UploadEvent event = (UploadEvent)ctx.getTriggerEvent();
		ArrayList<SobieImage> imageCatalog = addUpdateProduct.getImageCatalog();
		Media media = event.getMedia();
		if (media != null) {
            if (media instanceof org.zkoss.image.Image) {
            	SobieImage productImage = new SobieImage();
            	productImage.setImageInBytes(media.getByteData());
            	productImage.setImgFilename(media.getName());
            	productImage.setImgType(media.getFormat());
            	if(imageCatalog.size() == 0){
            		productImage.setPrimaryImage("Y");
            	} else {
            		productImage.setPrimaryImage("N");
            	}
            	SobieUtils sobieUtils = new SobieUtils();
            	productImage.setImgId(sobieUtils.createImageId());
            	imageCatalog.add(productImage);
            	addUpdateProduct.setImageCatalog(imageCatalog);
            	productImage.setUpdateImage(true);
            } else {
            	Messagebox.show("Not an image: " + media, "Error",
                        Messagebox.OK, Messagebox.ERROR);
            }
        }
	}
	
	@Command
	@NotifyChange("ownedProducts")
	public void activateProduct(@BindingParam("product") Product product){
		if(product.getProdStatus().equals("1")){ //Active
			productService.deactivateProduct(product.getProdId(), sobieProfile.getAccount().getStoreNo());
		} else { //Inactive
			productService.activateProduct(product.getProdId(), sobieProfile.getAccount().getStoreNo());
		}
		for(int x = 0; x < getOwnedProducts().size(); x++){
			if(getOwnedProducts().get(x).getProdId().equals(product.getProdId())){
				if(product.getProdStatus().equals("1")){ //Active
					getOwnedProducts().get(x).setProdStatus("0");	
				} else { //Inactive
					getOwnedProducts().get(x).setProdStatus("1");
				}
			}
		}
	}
	
	@Command
	@NotifyChange({"networkProducts","deleteProductPopupVisible","ownedProducts"})
	public void deleteProduct(@BindingParam("product") Product product){
		String storeNo = sobieProfile.getAccount().getStoreNo();
		String prodId = product.getProdId();
		List<MerchantDetails> merchantStoreNos = (List<MerchantDetails>) productService.retrieveProductMerchants(prodId);
		if(merchantStoreNos.size() != 0){
			setDeleteProductPopupVisible(true);
			setProductToBeDeleted(product);
		} else {
			productService.deleteProduct(prodId, storeNo);
			ownedProducts = productService.retrieveStoreProducts(sobieProfile.getAccount().getAccNo());
			setDeleteProductPopupVisible(false);
		}
	}
	
	@Command
	@NotifyChange({"networkProducyts","deletedProductPopupVisible"})
	public void continueAndDeleteProduct(){
		String storeNo = sobieProfile.getAccount().getStoreNo();
		
		ownedProducts = productService.retrieveStoreProducts(sobieProfile.getAccount().getAccNo());
		
		List<MerchantDetails> merchantStoreNos = (List<MerchantDetails>) productService.retrieveProductMerchants(getProductToBeDeleted().getProdId());
		Customer customer = sobieProfile.getCustomer();
		Account account = sobieProfile.getAccount();
		SobieUtils sobieUtils = new SobieUtils();
		for(int x = 0; x < merchantStoreNos.size(); x++){
		
			MessageBean msgBean = new MessageBean();
			msgBean.setFromCustNo(customer.getCustNo());
			msgBean.setFromAccNo(account.getAccNo());
			msgBean.setMsgbxId(sobieUtils.createMsgbxId());
			msgBean.setMsgId(sobieUtils.createMsgId());
			msgBean.setToCustNos(null);
			msgBean.setToAccNos(merchantStoreNos.get(x).getAccNo());
			msgBean.setMsgSubject("Notification: Supplier removed [" + getProductToBeDeleted().getProdId() + " : " + getProductToBeDeleted().getProdName() + "] from their shop");
			msgBean.setMsgType("NSRP");
			msgBean.setMsgRpProdId(getProductToBeDeleted().getProdId());
			msgBean.setMsgRpStoreNo(sobieProfile.getAccount().getStoreNo());
			msgBean.setMsgRpAccNo(sobieProfile.getAccount().getAccNo());
			
	    	if(messageService.createNewMessage(sobieProfile, msgBean).equals("Successful")){
	    		productService.deleteProduct(getProductToBeDeleted().getProdId(), storeNo);
	    	} else {
	    		
	    	}
		}
		setDeleteProductPopupVisible(false);
	}
	
	@Command
	@NotifyChange("deleteProductPopupVisible")
	public void cancelDeleteProduct(){
		setDeleteProductPopupVisible(false);
	}
	
	public Product getAddUpdateProduct() {
		return addUpdateProduct;
	}

	public void setAddUpdateProduct(Product addUpdateProduct) {
		this.addUpdateProduct = addUpdateProduct;
	}
	
	public List<Product> getOwnedProducts() {
		return ownedProducts;
	}

	public void setOwnedProducts(List<Product> ownedProducts) {
		this.ownedProducts = ownedProducts;
	}
	
	public boolean isAddProductOpen() {
		return addProductOpen;
	}

	public void setAddProductOpen(boolean addProductOpen) {
		this.addProductOpen = addProductOpen;
	}

	public boolean isDeleteProductPopupVisible() {
		return deleteProductPopupVisible;
	}

	public void setDeleteProductPopupVisible(boolean deleteProductPopupVisible) {
		this.deleteProductPopupVisible = deleteProductPopupVisible;
	}

	public Product getProductToBeDeleted() {
		return productToBeDeleted;
	}

	public void setProductToBeDeleted(Product productToBeDeleted) {
		this.productToBeDeleted = productToBeDeleted;
	}

	public boolean isEditProduct() {
		return editProduct;
	}

	public void setEditProduct(boolean editProduct) {
		this.editProduct = editProduct;
	}
	
	
}
