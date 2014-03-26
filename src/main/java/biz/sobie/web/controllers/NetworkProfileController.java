package biz.sobie.web.controllers;

import java.io.IOException;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import biz.sobie.web.beans.Account;
import biz.sobie.web.beans.Customer;
import biz.sobie.web.beans.ProfilePicture;
import biz.sobie.web.beans.SobieProfile;

public class NetworkProfileController extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;
	Window mySobieWin;
	Image profileImage;
	Label fullNameLbl,
		  emailLbl, 
		  telephoneLbl, 
		  accNoLbl, 
		  accountOpenDate, 
		  countryLbl, 
		  townLbl, 
		  SuburbLbl; 
	Include mySobieInc;
	
	public void onCreate$mySobieInc(Event event) throws IOException {
		SobieProfile sobieProfile = (SobieProfile) execution.getDesktop().getSession().getAttribute("sobieProfile");
		Customer customer = sobieProfile.getCustomer();
		Account account = sobieProfile.getAccount();
		ProfilePicture profilePicture = sobieProfile.getProfilePicture();
		if(profilePicture != null){
			AImage aimage = new AImage("profilePicture", profilePicture.getImgInBytes());
			profileImage.setContent(aimage);
		}
		fullNameLbl.setValue(customer.getCustFirstName() + " " + customer.getCustLastName());
		emailLbl.setValue(customer.getCustEmail());
		//telephoneLbl .setValue(customer);
		accNoLbl.setValue(account.getAccNo());
		accountOpenDate.setValue(account.getAccNo().substring(6, 10) + "/" + account.getAccNo().substring(10, 12) + "/" + account.getAccNo().substring(12, 14)); 
		//countryLbl.setValue(customer);
		//townLbl.setValue(customer);
		//SuburbLbl.setValue(customer); 
	}
}
