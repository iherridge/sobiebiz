package biz.sobie.web.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

public class UserDashboardWidgetViewModel {

	private String userDashboardOpen;
	
	@Init
	public void init() {
		userDashboardOpen = null;
 	}
	
	/**
	 * When a user clicks on the User Dashboard Button in the east widget
	 */
	@Command @NotifyChange("userDashboardOpen")
	public void openUserDashboard(){
		if(getUserDashboardOpen() == null) {
			setUserDashboardOpen("/secure/widgets/userDashboardWidget/userDashboardWidget.zul");
		} else {
			setUserDashboardOpen(null);
		}
	}

	public void setUserDashboardOpen(String userDashboardOpen) {
		this.userDashboardOpen = userDashboardOpen;
	}

	public String getUserDashboardOpen() {
		return userDashboardOpen;
	}

}
