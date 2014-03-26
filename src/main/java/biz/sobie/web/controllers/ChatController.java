package biz.sobie.web.controllers;

import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import biz.sobie.web.beans.Account;
import biz.sobie.web.beans.Customer;
import biz.sobie.web.beans.SobieProfile;

public class ChatController extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;
	Button	sendBtn, 
			chatBtn;
	Textbox messagesTextbox,
			msgTextbox;
	Window chatWin;
	Include chatInc;	
	private EventQueue eq;
	
	private boolean chatOpen = false;
     
    public boolean isChatOpen() {
		return chatOpen;
	}

	public void setChatOpen(boolean chatOpen) {
		this.chatOpen = chatOpen;
	}

	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        eq = EventQueues.lookup("interactive", EventQueues.APPLICATION, true);
        eq.subscribe(new EventListener() {
            public void onEvent(Event event) throws Exception {
            	
            	if(messagesTextbox != null) {
            		String prevMsgs = messagesTextbox.getValue();
            		Date date = new Date();
            		if(prevMsgs != "") {
	            		messagesTextbox.setValue(prevMsgs + "\n" + event.getData());
            		} else {
            			messagesTextbox.setValue(date + "\n" + event.getData());
            		}
            	}
            }
        });
    }
    
    public void onClick$sendBtn() {
    	SobieProfile sobieProfile = (SobieProfile) execution.getDesktop().getSession().getAttribute("sobieProfile");
		Customer customer = sobieProfile.getCustomer();
    	String message = customer.getCustFirstName() + " " + customer.getCustLastName() + ": " + msgTextbox.getValue();
        eq = EventQueues.lookup("interactive", EventQueues.APPLICATION, true);
        eq.publish(new Event("onButtonClick", sendBtn, message));
        msgTextbox.setValue("");
    }
    
    public void onCreate$chatWin(Event event) {
    	chatWin.doOverlapped();
    	chatWin.setPosition("bottom;right");
    }
    
    public void onClick$chatWin(Event event) {
    	if(isChatOpen() == false) {
    		chatWin.setHeight("250px");
    		chatWin.setPosition("bottom;right");
    		setChatOpen(true);
    		chatBtn.setVisible(true);
    	}
    }
    
    public void onClick$chatBtn(Event event) {
     		chatWin.setHeight("25px");
     		chatWin.setPosition("bottom;right");
     		setChatOpen(false);
     		chatBtn.setVisible(false);
    }
}
