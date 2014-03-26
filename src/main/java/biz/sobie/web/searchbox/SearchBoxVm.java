package biz.sobie.web.searchbox;

import java.io.IOException;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import biz.sobie.web.beans.Customer;
import biz.sobie.web.beans.Product;
import biz.sobie.web.services.CustomerAccountService;
import biz.sobie.web.services.ProductService;

public class SearchBoxVm {

	private String searchBox = "Search";
	private List<Customer> searchedCustomers = null;
	private boolean showPeopleList = false;
	private List<Product> searchedProducts = null;
	private boolean showProductList = false;
	@WireVariable CustomerAccountService customerAccountService;
	@WireVariable ProductService productService;
	
	@Init
	public void init() {
		
	}
	
	@Command
	@NotifyChange({"searchedCustomers","searchedProducts","showPeopleList","showProductList"})
	public void onChanging(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) throws IOException{
		InputEvent event = (InputEvent)ctx.getTriggerEvent();
		if(event.getValue().length() >= 3) {
			
			setSearchedCustomers(customerAccountService.customerSearch(event.getValue()));
			if(searchedCustomers.size() == 0){
				setShowPeopleList(false);
			} else {
				setShowPeopleList(true);
			}
			
			setSearchedProducts(productService.searchProduct(event.getValue()));
			if(searchedProducts.size() == 0){
				setShowProductList(false);
			} else {
				setShowProductList(true);
			}
		}
		
	}
	
	public String getSearchBox() {
		return searchBox;
	}

	public void setSearchBox(String searchBox) {
		this.searchBox = searchBox;
	}

	public List<Customer> getSearchedCustomers() {
		return searchedCustomers;
	}

	public void setSearchedCustomers(List<Customer> searchedCustomers) {
		this.searchedCustomers = searchedCustomers;
	}

	public List<Product> getSearchedProducts() {
		return searchedProducts;
	}

	public void setSearchedProducts(List<Product> searchedProducts) {
		this.searchedProducts = searchedProducts;
	}

	public boolean isShowPeopleList() {
		return showPeopleList;
	}

	public void setShowPeopleList(boolean showPeopleList) {
		this.showPeopleList = showPeopleList;
	}

	public boolean isShowProductList() {
		return showProductList;
	}

	public void setShowProductList(boolean showProductList) {
		this.showProductList = showProductList;
	}
	
}
