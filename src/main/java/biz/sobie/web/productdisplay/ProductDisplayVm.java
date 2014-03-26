package biz.sobie.web.productdisplay;

import java.util.ArrayList;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import biz.sobie.web.beans.Product;
import biz.sobie.web.beans.SobieImage;
import biz.sobie.web.services.ProductService;


public class ProductDisplayVm {

	private Product selectedProduct;

	@WireVariable ProductService productService;
	
	@Init
	public void init() {
		selectedProduct = (Product) Executions.getCurrent().getDesktop().getSession().getAttribute("productPageDataset");
		if(selectedProduct.getImageCatalog().size() == 0){
			selectedProduct.setImageCatalog((ArrayList<SobieImage>) productService.retrieveImageCatalog(selectedProduct.getProdId()));
		}
	}
	
	@GlobalCommand
	@NotifyChange("selectedProduct")
	public void displayProductPage(@BindingParam("product") Product product){
		selectedProduct = product;
		if(product.getImageCatalog().size() == 0){
			selectedProduct.setImageCatalog((ArrayList<SobieImage>) productService.retrieveImageCatalog(selectedProduct.getProdId()));
		}
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}
}
