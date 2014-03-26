package biz.sobie.web.userdashboard;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;

import biz.sobie.web.beans.SobieProfile;

public class UserDashboardCtrl extends GenericForwardComposer<Include> {

	private static final long serialVersionUID = 1L;
	Include contentArea;
	Div userDashboardDiv;
	Button storeBtn;
	
	public void onCreate$userDashboardDiv(Event event){
		SobieProfile sobieProfile = (SobieProfile) Executions.getCurrent().getDesktop().getSession().getAttribute("sobieProfile");
		if(sobieProfile.getAccount().getPpId().startsWith("BU")){
			storeBtn.setVisible(false);
		}
	}
	
	public void onClick$networkBtn(Event event){
		contentArea.setSrc("/secure/widgets/userDashboardWidget/network/networkMessages.zul");
	}
	
	public void onClick$calendarBtn(Event event){
		contentArea.setSrc("/secure/widgets/userDashboardWidget/calendar/calendar.zul");
	}
	
	public void onClick$ordersBtn(Event event){
		contentArea.setSrc("/secure/widgets/userDashboardWidget/orders/orders.zul");
	}
	
	public void onClick$inventoryBtn(Event event){
		contentArea.setSrc("/secure/widgets/userDashboardWidget/inventory/inventory.zul");
	}
	
	public void onClick$marketingBtn(Event event){
		contentArea.setSrc("/secure/widgets/userDashboardWidget/marketing/marketing.zul");
	}
	
	public void onClick$storeBtn(Event event){
		contentArea.setSrc("/secure/widgets/userDashboardWidget/store/store.zul");
	}
	
	public void onClick$applicationsBtn(Event event){
		contentArea.setSrc("/secure/widgets/userDashboardWidget/applications/applications.zul");
	}
	
	public void onClick$settingsBtn(Event event){
		contentArea.setSrc("/secure/widgets/userDashboardWidget/settings/settings.zul");
	}
	
}
