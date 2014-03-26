package biz.sobie.web.controllers;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

import biz.sobie.web.beans.SobieProfile;

public class MySobieController extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;
	Button	networkBtn,
			calendarBtn,
			ordersBtn,
			inventoryBtn,
			marketingBtn,
			storeBtn,
			applicationsBtn,
			settingsBtn;
	Window mySobieWin,
		   createNewMessageWin;
	Include mySobieInc,
			popupInc;
	

	
	public void onCreate$mySobieWin(Event event) {
		mySobieWin.doOverlapped();
		networkBtn.setStyle("background:#FFD645");
		
		
		SobieProfile sobieProfile = (SobieProfile) execution.getDesktop().getSession().getAttribute("sobieProfile");
		
		/**
		 * Buyer must not be able to see the Inventory button
		 */
		if(sobieProfile.getAccount().getPpId().startsWith("BU")) {
			inventoryBtn.setVisible(false);
		}
	}
	
	public void onClick$networkBtn(Event event) {
		mySobieInc.setSrc("/secure/tabSliders/mySobie/network.zul");
		networkBtn.setStyle("background:#FFD645");
		storeBtn.setStyle("background:#808080");
		inventoryBtn.setStyle("background:#808080");
		ordersBtn.setStyle("background:#808080");
	}

	public void onClick$ordersBtn(Event event) {
		mySobieInc.setSrc("/secure/tabSliders/mySobie/orders.zul");
		ordersBtn.setStyle("background:#FFD645");
		networkBtn.setStyle("background:#808080");
		inventoryBtn.setStyle("background:#808080");
		storeBtn.setStyle("background:#808080");
		
	}
	
	public void onClick$inventoryBtn(Event event) {
		mySobieInc.setSrc("/secure/tabSliders/mySobie/inventory.zul");
		inventoryBtn.setStyle("background:#FFD645");
		storeBtn.setStyle("background:#808080");
		networkBtn.setStyle("background:#808080");
		ordersBtn.setStyle("background:#808080");
	}
	
	public void onClick$storeBtn(Event event) {
		mySobieInc.setSrc("/secure/tabSliders/mySobie/store.zul");
		storeBtn.setStyle("background:#FFD645");
		networkBtn.setStyle("background:#808080");
		inventoryBtn.setStyle("background:#808080");
		ordersBtn.setStyle("background:#808080");
		
	}
	
}
