package biz.sobie.web.userdashboard.network;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import biz.sobie.web.beans.Account;
import biz.sobie.web.beans.Customer;
import biz.sobie.web.beans.MessageBean;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.services.MessageService;
import biz.sobie.web.utils.SobieUtils;

public class NetworkMessagesCtrl extends GenericForwardComposer<Include> {

	private static final long serialVersionUID = 1L;
	Window createNewMessageWin;
	Include popupInc,
			allMessageInboxInc,
			networkMessagesInc;
	Button createNewMessageBtn,
		   sendBtn;
	Label toLbl,
		  ccLbl,
		  bccLbl;
	Textbox contentTextbx,
			subjectTextbx;
	Grid messageBoxGrid;
	Tab networkMessagesTab;
	Tabbox networkMessagesTabbox;
	

	public void onCreate$allMessageInboxInc(Event event) {
		allMessageInboxInc.setSrc("/secure/widgets/userDashboardWidget/network/messageInbox.zul");
	}
	
	public void onClick$createNewMessageBtn(Event event) {
		popupInc.setSrc("/secure/widgets/userDashboardWidget/network/createNewMessage.zul");
	}
	
	public void onCreate$createNewMessageWin(Event event) {
		createNewMessageWin.doOverlapped();
	}

	public void onClose$createNewMessageWin(Event event) {
		popupInc.setSrc(null);
	}
	
	public void onClick$sendBtn(Event event) {
		
		SobieProfile sobieProfile = (SobieProfile) execution.getDesktop().getSession().getAttribute("sobieProfile");
		Customer customer = sobieProfile.getCustomer();
		Account account = sobieProfile.getAccount();
		SobieUtils sobieUtils = new SobieUtils();
		
		MessageBean msgBean = new MessageBean();
		msgBean.setFromCustNo(customer.getCustNo());
		msgBean.setFromAccNo(account.getAccNo());
		msgBean.setMsgbxId(sobieUtils.createMsgbxId());
		msgBean.setMsgId(sobieUtils.createMsgId());
		msgBean.setToCustNos((String) toLbl.getAttribute("custNos"));
		msgBean.setToAccNos((String) toLbl.getAttribute("AccNos"));
		msgBean.setCcCustNos((String) ccLbl.getAttribute("custNos"));
		msgBean.setCcAccNos((String) ccLbl.getAttribute("AccNos"));
		msgBean.setBccCustNos((String) bccLbl.getAttribute("custNos"));
		msgBean.setBssAccNos((String) bccLbl.getAttribute("AccNos"));
		msgBean.setMsgSubject(subjectTextbx.getValue());
		msgBean.setMsgContent(contentTextbx.getValue());
		msgBean.setMsgType("IM");
		
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
    	MessageService service = (MessageService)appContext.getBean("messageService");
    	if(service.createNewMessage(sobieProfile, msgBean).equals("Successful")){
    		popupInc.setSrc(null);
    	} else {
    		
    	}
	}
	
	/**
	 * Retrieves all messages that belongs to logged in user
	 * @param event
	 * @throws IOException 
	 */
	public void onCreate$messageBoxGrid(Event event) throws IOException {
		 
		SobieProfile sobieProfile = (SobieProfile) execution.getDesktop().getSession().getAttribute("sobieProfile");
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
    	MessageService service = (MessageService)appContext.getBean("messageService");
    	List<MessageBean> messages =  (List<MessageBean>)service.getMessages(sobieProfile);
    	
		Rows rows = new Rows();
		for(int x = 0; x < messages.size(); x++) {
			Row row = new Row();
			Checkbox selectChbx = new Checkbox();
            Image iconImg = new Image();
            iconImg.setStyle("padding: 0px 10px");
            iconImg.setSrc("/resources/img/tabs/mySobie/networkMessages/EnvelopeOpen-16x16.png");
			Label fromLbl = new Label();
			Label subjectLbl = new Label();
			Label receivedLbl = new Label();
			
			fromLbl.setValue(messages.get(x).getFromCustFullName());
			subjectLbl.setValue(messages.get(x).getMsgSubject());
			receivedLbl.setValue(messages.get(x).getMsgDate() + " " + messages.get(x).getMsgTime());

			selectChbx.setParent(row);
			iconImg.setParent(row);
			fromLbl.setParent(row);
			subjectLbl.setParent(row);
			receivedLbl.setParent(row);
			
			row.setParent(rows);
		}
		rows.setParent(messageBoxGrid);
	}
}

