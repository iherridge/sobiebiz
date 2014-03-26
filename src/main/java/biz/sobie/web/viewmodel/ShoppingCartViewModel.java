package biz.sobie.web.viewmodel;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import biz.sobie.web.beans.Product;
import biz.sobie.web.beans.ShoppingCart;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.services.CustomerAccountService;
import biz.sobie.web.services.ProductService;

public class ShoppingCartViewModel {

	ShoppingCart shoppingCart;
	
	@WireVariable
	ProductService productService;
	@WireVariable
	CustomerAccountService customerAccountService;
	
	@Init
	public void init() {
		SobieProfile sobieProfile = null;
		shoppingCart = new ShoppingCart();
		if(Executions.getCurrent().getSession().getAttribute("sobieProfile") == null) {
	    	/*try {
				sobieProfile = (SobieProfile)customerAccountService.getCustomerAccountDetails(SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getName(), sobieProfile.getAccount().getPpId());
			} catch (Exception e) {
				//Anonymous user
			}*/
	    	Executions.getCurrent().getSession().setAttribute("sobieProfile", null);
		} else {
			sobieProfile = (SobieProfile)Executions.getCurrent().getSession().getAttribute("sobieProfile");
		}
		if(sobieProfile != null){
			shoppingCart.setShoppingCartProducts((List<Product>)productService.retrieveShoppingCart(sobieProfile));
		}
		
		shoppingCart.setGrandTotal(0);
		if(shoppingCart.getShoppingCartProducts() != null) {
			shoppingCart.setTotalItems(shoppingCart.getShoppingCartProducts().size());
			for(int x = 0; x < shoppingCart.getShoppingCartProducts().size(); x++) {
				shoppingCart.setGrandTotal(shoppingCart.getGrandTotal() + (shoppingCart.getShoppingCartProducts().get(x).getProdRetailPrice()));
			}
		}
 	}
	
	@Command @NotifyChange("shoppingCart")
	public void addProductToShoppingCart(@BindingParam("product") Product product) {
		
		SobieProfile sobieProfile = (SobieProfile) Executions.getCurrent().getDesktop().getSession().getAttribute("sobieProfile");
		String sellerStoreNo = product.getSellerStoreNo();
		String prodId = product.getProdId();
		//TODO: This must later be changed to also cater for multiple accounts belonging to a customer
		String accNo = sobieProfile.getAccount().getAccNo();
		String shoppingCartNo = sobieProfile.getAccount().getShoppingCartNo();
		productService.addProductToShoppingCart(prodId, accNo, sellerStoreNo, shoppingCartNo);
		
		shoppingCart.setShoppingCartProducts((List<Product>)productService.retrieveShoppingCart(sobieProfile));
		shoppingCart.setGrandTotal(0);
		if(shoppingCart.getShoppingCartProducts() != null) {
			shoppingCart.setTotalItems(shoppingCart.getShoppingCartProducts().size());
			for(int x = 0; x < shoppingCart.getShoppingCartProducts().size(); x++) {
				shoppingCart.setGrandTotal(shoppingCart.getGrandTotal() + (shoppingCart.getShoppingCartProducts().get(x).getProdRetailPrice()));
			}
		}
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
}
