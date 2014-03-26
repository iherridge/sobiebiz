package biz.sobie.web.multiplesobieprofiles;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import biz.sobie.web.beans.MultipleSobieProfiles;

public class MultipleSobieProfilesVm {

	MultipleSobieProfiles multipleSobieProfiles;

	@Init
	public void init() {
		multipleSobieProfiles = (MultipleSobieProfiles) Executions.getCurrent().getDesktop().getSession().getAttribute("multipleSobieProfiles");
	}
	
	public MultipleSobieProfiles getMultipleSobieProfiles() {
		return multipleSobieProfiles;
	}

	public void setMultipleSobieProfiles(MultipleSobieProfiles multipleSobieProfiles) {
		this.multipleSobieProfiles = multipleSobieProfiles;
	}
}
