package biz.sobie.web.userdashboard.inventory;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;

import biz.sobie.web.beans.Product;

public class InventoryCtrl extends GenericForwardComposer<Include> {

	private static final long serialVersionUID = 1L;
	A overviewBtn,
	  categoriesBtn,
	  productsBtn,
	  networkProductsBtn,
	  servicesBtn,
	  classifiedsBtn;
	Include inventoryContentArea;
	
	public void onClick$overviewBtn(Event event) {
		inventoryContentArea.setSrc("/secure/widgets/userDashboardWidget/inventory/inventoryOverview.zul");
	}

	public void onClick$categoriesBtn(Event event) {
		inventoryContentArea.setSrc("/secure/widgets/userDashboardWidget/inventory/inventoryCategories.zul");
	}
	
	public void onClick$productsBtn(Event event) {
		inventoryContentArea.setSrc("/secure/widgets/userDashboardWidget/inventory/inventoryProducts.zul");
	}
	
	public void onClick$networkProductsBtn(Event event) {
		inventoryContentArea.setSrc("/secure/widgets/userDashboardWidget/inventory/inventoryNetworkProducts.zul");
	}
	
	public void onClick$servicesBtn(Event event) {
		inventoryContentArea.setSrc("/secure/widgets/userDashboardWidget/inventory/inventoryServices.zul");
	}
	
	public void onClick$classifiedsBtn(Event event) {
		inventoryContentArea.setSrc("/secure/widgets/userDashboardWidget/inventory/inventoryClassifieds.zul");
	}

}


