package biz.sobie.web.notifications;

import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.services.CustomerAccountService;
import biz.sobie.web.services.ProductService;

public class NotificationWidgetVm {

	private String shoppingCartTotalItems;
	private String shoppingCartNo;
	
	@WireVariable
	ProductService productService;
	@WireVariable
	CustomerAccountService customerAccountService;
	
	@Init
	public void init() {
		SobieProfile sobieProfile = (SobieProfile)Executions.getCurrent().getSession().getAttribute("sobieProfile");
		shoppingCartNo = sobieProfile.getAccount().getShoppingCartNo();
		shoppingCartTotalItems = productService.getShoppingCartNotificationInfo(shoppingCartNo);
 	}

	@GlobalCommand
	@NotifyChange("shoppingCartTotalItems")
	public void refreshShoppingCartNotification(){
		shoppingCartTotalItems = productService.getShoppingCartNotificationInfo(shoppingCartNo);
	}
	
	public String getShoppingCartTotalItems() {
		return shoppingCartTotalItems;
	}

	public void setShoppingCartTotalItems(String shoppingCartTotalItems) {
		this.shoppingCartTotalItems = shoppingCartTotalItems;
	}

	public String getShoppingCartNo() {
		return shoppingCartNo;
	}

	public void setShoppingCartNo(String shoppingCartNo) {
		this.shoppingCartNo = shoppingCartNo;
	}

}
