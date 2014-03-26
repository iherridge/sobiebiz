package biz.sobie.web.multiplesobieprofiles;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

import biz.sobie.web.beans.MultipleSobieProfiles;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.services.CustomerAccountService;

public class MultipleSobieProfilesCtrl extends GenericForwardComposer<Window> {

	private static final long serialVersionUID = 1L;

	Window multipleSobieProfilesWin;
	Button selectBtn, skipBtn;
	
	public void onCreate$multipleSobieProfilesWin(Event event){
		multipleSobieProfilesWin.doModal();
	}
	
	public void onClick$skipBtn(Event event){
		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
    	CustomerAccountService customerAccountService = (CustomerAccountService)appContext.getBean("customerAccountService");
    	
		MultipleSobieProfiles multipleSobieProfiles = (MultipleSobieProfiles)Executions.getCurrent().getDesktop().getSession().getAttribute("multipleSobieProfiles");
		int anonymousBuyerIndex = 0;
		boolean anonymousBuyerExist = false;
		for(int x = 0; x < multipleSobieProfiles.getSobieProfiles().size();x++){
			if(multipleSobieProfiles.getSobieProfiles().get(x).getAccount().getPpId().equals("BU0")){
				anonymousBuyerExist = true;
				anonymousBuyerIndex = x;
			}
		}
		SobieProfile sobieProfile;
		if(anonymousBuyerExist){
			sobieProfile = multipleSobieProfiles.getSobieProfiles().get(anonymousBuyerIndex);
		} else {
			sobieProfile = customerAccountService.createAnonymousBuyer(Executions.getCurrent().getRemoteAddr());
			ArrayList<SobieProfile> tempSobieProfile = new ArrayList<SobieProfile>(multipleSobieProfiles.getSobieProfiles().size()+1);
			tempSobieProfile.add(0, sobieProfile);
			for(int x = 0; x < multipleSobieProfiles.getSobieProfiles().size();x++){
				tempSobieProfile.add(x+1, multipleSobieProfiles.getSobieProfiles().get(x));
			}
			multipleSobieProfiles.setSobieProfiles(tempSobieProfile);
		}
		Executions.getCurrent().getDesktop().getSession().setAttribute("sobieProfile", sobieProfile);		
		multipleSobieProfiles.setProfileSelected(true);
		
		Include parentInc = (Include) multipleSobieProfilesWin.getParent();
		parentInc.setSrc(null);
	}
}
