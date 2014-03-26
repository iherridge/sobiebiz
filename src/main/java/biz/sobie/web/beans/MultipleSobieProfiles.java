package biz.sobie.web.beans;

import java.util.List;

public class MultipleSobieProfiles {
	
	private List<SobieProfile> sobieProfiles;
	private boolean profileSelected; 
	
	public void setSobieProfiles(List<SobieProfile> sobieProfiles) {
		this.sobieProfiles = sobieProfiles;
	}

	public List<SobieProfile> getSobieProfiles() {
		return sobieProfiles;
	}

	public void setProfileSelected(boolean profileSelected) {
		this.profileSelected = profileSelected;
	}

	public boolean isProfileSelected() {
		return profileSelected;
	}

}
