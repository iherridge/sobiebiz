package biz.sobie.web.userdashboard.store;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Vbox;

public class StoreCtrl extends GenericForwardComposer<Include> {

	private static final long serialVersionUID = 1L;
	A overviewBtn,
	  settingsBtn,
	  storeLocatorBtn,
	  contentPagesBtn,
	  storeLayoutBtn,
	  stylesBtn,
	  storeWidgetsBtn,
	  storeContentArea$settingsBtn,
	  storeContentArea$storeLocatorBtn,
	  storeContentArea$contentPagesBtn,
	  storeContentArea$storeLayoutBtn,
	  storeContentArea$stylesBtn,
	  storeContentArea$storeWidgetsBtn;
	Include storeContentArea;
	
	public void onClick$overviewBtn(Event event) {
		storeContentArea.setSrc("/secure/widgets/userDashboardWidget/store/storeOverview.zul");
	}
	
	public void onClick$settingsBtn(Event event) {
		storeContentArea.setSrc("/secure/widgets/userDashboardWidget/store/storeSettings.zul");
	}
	
	public void onClick$storeLocatorBtn(Event event) {
		storeContentArea.setSrc("/secure/widgets/userDashboardWidget/store/storeLocator.zul");
	}
	
	public void onClick$contentPagesBtn(Event event) {
		storeContentArea.setSrc("/secure/widgets/userDashboardWidget/store/contentPages.zul");
	}
	
	public void onClick$stylesBtn(Event event) {
		storeContentArea.setSrc("/secure/widgets/userDashboardWidget/store/storeStyles.zul");
	}
	
	public void onClick$storeWidgetsBtn(Event event) {
		storeContentArea.setSrc("/secure/widgets/userDashboardWidget/store/storeWidgets.zul");
	}
	
	public void onClick$storeContentArea$settingsBtn(Event event) {
		storeContentArea.setSrc("/secure/widgets/userDashboardWidget/store/storeSettings.zul");
	}
	
	public void onClick$storeContentArea$storeLocatorBtn(Event event) {
		storeContentArea.setSrc("/secure/widgets/userDashboardWidget/store/storeLocator.zul");
	}
	
	public void onClick$storeContentArea$contentPagesBtn(Event event) {
		storeContentArea.setSrc("/secure/widgets/userDashboardWidget/store/contentPages.zul");
	}
	
	public void onClick$storeContentArea$stylesBtn(Event event) {
		storeContentArea.setSrc("/secure/widgets/userDashboardWidget/store/storeStyles.zul");
	}
	
	public void onClick$storeContentArea$storeWidgetsBtn(Event event) {
		storeContentArea.setSrc("/secure/widgets/userDashboardWidget/store/storeWidgets.zul");
	}
}


