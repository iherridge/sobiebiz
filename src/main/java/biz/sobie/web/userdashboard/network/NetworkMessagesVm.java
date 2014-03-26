package biz.sobie.web.userdashboard.network;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import biz.sobie.web.beans.MessageBean;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.services.MessageService;
import biz.sobie.web.services.ProductService;
import biz.sobie.web.userdashboard.inventory.ProductHistory;
import biz.sobie.web.utils.SobieUtils;


public class NetworkMessagesVm {

	SobieProfile sobieProfile;
	boolean createMessageOpen;
	List<MessageBean> messages;
	boolean inboxOpen;
	boolean requestForApprovalPopupVisble;
	boolean notificationSupplierRemovedProductVisble;
	boolean rpApproveVisible;
	boolean rpDeclineVisible;
	MessageBean openedMessage;
	List<ProductHistory> productHistory;
	String replyMessageContent;
	
	
	@WireVariable
	MessageService messageService;
	@WireVariable
	ProductService productService;
	
	@Init
	public void init() {
		sobieProfile = (SobieProfile) Executions.getCurrent().getDesktop().getSession().getAttribute("sobieProfile");
		createMessageOpen = false;
		inboxOpen = true;
		requestForApprovalPopupVisble = false;
		notificationSupplierRemovedProductVisble = false;
		productHistory = null;
		replyMessageContent = "";
		rpApproveVisible = false;
		rpDeclineVisible = false;
	}
	
	@Command
	@NotifyChange({"createMessageOpen","inboxOpen"})
	public void createNewMessage(){
		setCreateMessageOpen(true);
		setInboxOpen(false);
	}
	
	@Command
	@NotifyChange({"createMessageOpen","inboxOpen"})
	public void cancelCreateMessage(){
		setCreateMessageOpen(false);
		setInboxOpen(true);
	}
	
	@Command
	@NotifyChange("messages")
	public void loadProductsInbox() throws IOException{
		messages = messageService.getMessages(sobieProfile);
	}
	
	@Command
	@NotifyChange({"requestForApprovalPopupVisble","notificationSupplierRemovedProductVisble","productHistory","openedMessage","rpApproveVisible","rpDeclineVisible"})
	public void openMessage(@BindingParam("message") MessageBean message){
		setProductHistory(productService.getProductHistory(message.getMsgRpProdId()));
		setOpenedMessage(message);
		if(message.getMsgType().equals("RP")){
			setRequestForApprovalPopupVisble(true);
			String prodStatus = productService.getProductStatus(message.getMsgRpProdId());
			if(prodStatus.equals("1")){
				setRpApproveVisible(false);
				setRpDeclineVisible(false);
			} else {
				if(sobieProfile.getAccount().getPpId().startsWith("SU")){
				setRpApproveVisible(true);
				setRpDeclineVisible(true);
				} else if(sobieProfile.getAccount().getPpId().startsWith("SE")){
					setRpApproveVisible(false);
					setRpDeclineVisible(false);
				}
			}
		} else if(message.getMsgType().equals("NSRP")){
			setNotificationSupplierRemovedProductVisble(true);
		}
	}
	
	@Command
	@NotifyChange("requestForApprovalPopupVisble")
	public void approveProductRequest(){
		productService.activateProduct(getOpenedMessage().getMsgRpProdId(), getOpenedMessage().getMsgRpStoreNo());
		setRequestForApprovalPopupVisble(false);
		SobieUtils sobieUtils = new SobieUtils();
		MessageBean message = getOpenedMessage();
		message.setMsgbxId(sobieUtils.createMsgbxId());
		message.setMsgId(sobieUtils.createMsgId());
		message.setToAccNos(getOpenedMessage().getMsgRpAccNo());
		message.setToCustNos(null);
		message.setFromAccNo(sobieProfile.getAccount().getAccNo());
		message.setFromCustNo(sobieProfile.getCustomer().getCustNo());
		message.setMsgSubject("Your product was approved (" + getOpenedMessage().getMsgRpProdId() + ")");
		message.setMsgContent(message.getMsgContent() == null ? getReplyMessageContent() : message.getMsgContent() + "\n" + getReplyMessageContent());

		if(messageService.createNewMessage(sobieProfile, message).equals("Successful")){
    		
    	} 
	}
	
	@Command
	public void replyOnProductRequest(){
		
		SobieUtils sobieUtils = new SobieUtils();
		MessageBean message = getOpenedMessage();
		message.setMsgbxId(sobieUtils.createMsgbxId());
		message.setMsgId(sobieUtils.createMsgId());
		if(sobieProfile.getAccount().getPpId().startsWith("SU")){
			message.setToAccNos(getOpenedMessage().getMsgRpAccNo());
		} else if(sobieProfile.getAccount().getPpId().startsWith("SE")){
			message.setToAccNos(getOpenedMessage().getFromAccNo());
		}
		message.setFromAccNo(sobieProfile.getAccount().getAccNo());
		message.setFromCustNo(sobieProfile.getCustomer().getCustNo());
		message.setToCustNos(null);
		message.setMsgSubject("RE: Product sent for Approval (" + getOpenedMessage().getMsgRpProdId() + ")");
		message.setMsgContent(message.getMsgContent() == null ? getReplyMessageContent() : message.getMsgContent() + "\n" + getReplyMessageContent());

		if(messageService.createNewMessage(sobieProfile, message).equals("Successful")){
    		
    	} 
	}
	
	@Command
	@NotifyChange("requestForApprovalPopupVisble")
	public void closeProductRequestMsg(){
		setRequestForApprovalPopupVisble(false);
	}
	
	public boolean isCreateMessageOpen() {
		return createMessageOpen;
	}

	public void setCreateMessageOpen(boolean createMessageOpen) {
		this.createMessageOpen = createMessageOpen;
	}

	public boolean isInboxOpen() {
		return inboxOpen;
	}

	public void setInboxOpen(boolean inboxOpen) {
		this.inboxOpen = inboxOpen;
	}

	public List<MessageBean> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageBean> messages) {
		this.messages = messages;
	}

	public boolean isRequestForApprovalPopupVisble() {
		return requestForApprovalPopupVisble;
	}

	public void setRequestForApprovalPopupVisble(
			boolean requestForApprovalPopupVisble) {
		this.requestForApprovalPopupVisble = requestForApprovalPopupVisble;
	}

	public MessageBean getOpenedMessage() {
		return openedMessage;
	}

	public void setOpenedMessage(MessageBean openedMessage) {
		this.openedMessage = openedMessage;
	}

	public boolean isNotificationSupplierRemovedProductVisble() {
		return notificationSupplierRemovedProductVisble;
	}

	public void setNotificationSupplierRemovedProductVisble(
			boolean notificationSupplierRemovedProductVisble) {
		this.notificationSupplierRemovedProductVisble = notificationSupplierRemovedProductVisble;
	}

	public List<ProductHistory> getProductHistory() {
		return productHistory;
	}

	public void setProductHistory(List<ProductHistory> productHistory) {
		this.productHistory = productHistory;
	}

	public String getReplyMessageContent() {
		return replyMessageContent;
	}

	public void setReplyMessageContent(String replyMessageContent) {
		this.replyMessageContent = replyMessageContent;
	}

	public boolean isRpApproveVisible() {
		return rpApproveVisible;
	}

	public void setRpApproveVisible(boolean rpApproveVisible) {
		this.rpApproveVisible = rpApproveVisible;
	}

	public boolean isRpDeclineVisible() {
		return rpDeclineVisible;
	}

	public void setRpDeclineVisible(boolean rpDeclineVisible) {
		this.rpDeclineVisible = rpDeclineVisible;
	}	
	
	
}
