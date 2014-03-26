package biz.sobie.web.viewmodel;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import biz.sobie.web.beans.Customer;
import biz.sobie.web.beans.MultipleSobieProfiles;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.services.CustomerAccountService;

public class WelcomeWidgetViewModel {

	private Customer customer;
	
	@WireVariable
	CustomerAccountService customerAccountService;
	
	@Init
	public void init() {
		customer = new Customer();
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}
	
	@Command
	public void registerBuyerFree() {
		getCustomer().setCustProductType("BU1");
	}
	
	@Command
	public void registerBuyerBasic() {
		getCustomer().setCustProductType("BU2");
	}
	
	@Command
	public void registerBuyerNovice() {
		getCustomer().setCustProductType("BU3");
	}
	
	@Command
	public void registerBuyerPro() {
		getCustomer().setCustProductType("BU4");
	}
	
	@Command
	public void registerSellerFree() {
		getCustomer().setCustProductType("SE1");
	}
	
	@Command
	public void registerSellerNovice() {
		getCustomer().setCustProductType("SE2");
	}

	@Command
	public void registerSellerPro() {
		getCustomer().setCustProductType("SE3");
	}

	@Command
	public void registerSellerEnterprise() {
		getCustomer().setCustProductType("SE4");
	}
	
	@Command
	public void registerSupplierBasic() {
		getCustomer().setCustProductType("SU1");
	}
	
	@Command
	public void registerSupplierNovice() {
		getCustomer().setCustProductType("SU2");
	}
	
	@Command
	public void registerSupplierPro() {
		getCustomer().setCustProductType("SU3");
	}
	
	@Command
	public void registerNewAccount() {
		
		/**
		 * Gets the anonymousBuyers customer number and ip address and create new profile based on entered register details
		 */
    	MultipleSobieProfiles multipleSobieProfiles = (MultipleSobieProfiles)Executions.getCurrent().getDesktop().getSession().getAttribute("multipleSobieProfiles");
    	boolean existingCustomer = false;
    	int anonymousBuyerIndex = 0; 
    	for(int x = 0;x < multipleSobieProfiles.getSobieProfiles().size();x++){
    		if(multipleSobieProfiles.getSobieProfiles().get(x).getCustomer().getCustEmail() != null && multipleSobieProfiles.getSobieProfiles().get(x).getCustomer().getCustEmail().equalsIgnoreCase(getCustomer().getCustEmail())){
    			//TODO: Add extra account to customer profile or dislay error message to user to notify them that they need to login before they can add more accounts to the same profile
    			existingCustomer = true;
    		}
    		if(multipleSobieProfiles.getSobieProfiles().get(x).getAccount().getPpId().equals("BU0")){
    			anonymousBuyerIndex = x;
    		}
    	}
    	if(!existingCustomer){
    		SobieProfile sobieProfile = (SobieProfile) multipleSobieProfiles.getSobieProfiles().get(anonymousBuyerIndex);
    		getCustomer().setCustNo(sobieProfile.getCustomer().getCustNo());
	    	sobieProfile.setCustomer(getCustomer());
	    	if(customer.validatePassword() == false) {
	    		//TODO: return password doesn't match error back to the front-end
	    		customer = null;
	    	}
	    	sobieProfile.getCustomer().setCustRetypePassword(null);
	    	try{
	    		sobieProfile = customerAccountService.createNewCustomerAccount(sobieProfile);
	    		Executions.getCurrent().getDesktop().getSession().setAttribute("sobieProfile", sobieProfile);
	
	    		
	    	} catch (Exception e) {
	    		customer = null;
	    	}
	    	Executions.sendRedirect(null);
    	}
	}
	
	public Validator getRequiredFieldValidator(){
	    return new AbstractValidator() {
			
			@Override
			public void validate(ValidationContext ctx) {
	            String field = (String)ctx.getProperty().getValue();
	            if(field==null){
	                addInvalidMessage(ctx, "This field is required");
	            }
				
			}
		};
	}
}
