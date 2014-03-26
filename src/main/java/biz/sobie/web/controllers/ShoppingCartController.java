package biz.sobie.web.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import biz.sobie.web.beans.DeliveryDetails;
import biz.sobie.web.beans.Order;
import biz.sobie.web.beans.PaymentDetails;
import biz.sobie.web.beans.Product;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.services.CustomerAccountService;
import biz.sobie.web.services.OrderService;
import biz.sobie.web.services.ProductService;
import biz.sobie.web.utils.SobieUtils;

public class ShoppingCartController extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;
	Button	shoppingCartBtn,
			proceedToCheckoutBtn,
			placeOrderBtn;
	Include shoppingCartInc;
	Window shoppingCartWin,
		   placeOrderWin;
	Grid shoppingCartGrid;
	Label grandTotalLbl;
	Checkbox selectAllCheckbox,
			 confirmCheckoutCheckbox,
			 confirmOrderCheckbox;
	Textbox accountHolderNameTextbox,
			cardNumberTextbox,
			csvTextbox,
			addressLine1Textbox,
			addressLine2Textbox,
			suburbTextbox,
			cityTextbox,
			countryTextbox,
			codeTextbox;
	Combobox cardExpiryMonthCombobox,
			 cardExpiryYearCombobox;
	
	@WireVariable
	ProductService productService;
	@WireVariable
	CustomerAccountService customerAccountService;
	@WireVariable
	OrderService orderService;
			
	
	/*PRIVATE BOOLEAN SHOPPINGCARTSELECTED;*/
	
/*	*//**
	 * Buyer ads a product from a seller to his/her shopping cart
	 *//*
	public void onDrop$shoppingCartBtn(DropEvent event) {

		Row droppedProduct = (Row) event.getDragged();
		Product product = (Product) droppedProduct.getAttribute("product");
		SobieProfile sobieProfile = (SobieProfile) execution.getDesktop().getSession().getAttribute("sobieProfile");
		
		String sellerStoreNo = product.getSellerStoreNo();
		String prodId = product.getProdId();
		//TODO: This must later be changed to also cater for multiple accounts belonging to a customer
		String accNo = sobieProfile.getAccount().getAccNo();
		String shoppingCartNo = sobieProfile.getAccount().getShoppingCartNo();
		productService.addProductToShoppingCart(prodId, accNo, sellerStoreNo, shoppingCartNo);
	}*/
/*	
	public boolean isShoppingCartSelected() {
		return shoppingCartSelected;
	}

	public void setShoppingCartSelected(boolean shoppingCartSelected) {
		this.shoppingCartSelected = shoppingCartSelected;
	}*/
	
/*	public void onClick$shoppingCartBtn(Event event) {
		if(isShoppingCartSelected() == false){
			shoppingCartBtn.setStyle("background:#FFD645");
			shoppingCartInc.setSrc("/unsecure/tabSliders/shoppingCartTabSlider/shoppingCart.zul");
			setShoppingCartSelected(true);
		} else if(isShoppingCartSelected() == true) {
			shoppingCartBtn.setStyle(null);
			shoppingCartInc.setSrc(null);
			setShoppingCartSelected(false);
		}
	}*/
	
/*	public void onCreate$shoppingCartWin(Event event) throws IOException {
		shoppingCartWin.doOverlapped();
		
		SobieProfile sobieProfile = (SobieProfile) execution.getDesktop().getSession().getAttribute("sobieProfile");
		List<Product> shoppingCartproducts = (List<Product>)productService.retrieveShoppingCart(sobieProfile);
		
		Rows rows = new Rows();
		double grandTotal = 0;
		for(int x = 0; x < shoppingCartproducts.size(); x++) {
			Row row = new Row();
			Checkbox selectChbx = new Checkbox();

			Label itemLbl = new Label();
			Label optionLbl = new Label();
			Spinner qtySpinner = new Spinner();
			Label amountLbl = new Label();
			Label shippingCostLbl = new Label();
			
			itemLbl.setValue(shoppingCartproducts.get(x).getProdName());
			optionLbl.setValue("Unknown Option");
			qtySpinner.setValue(1);
			qtySpinner.setWidth("40px");
			amountLbl.setValue("NAD " + String.valueOf(shoppingCartproducts.get(x).getProdPrice()) + "0");
			shippingCostLbl.setValue("NAD 0.00");

			selectChbx.setParent(row);
			itemLbl.setParent(row);
			optionLbl.setParent(row);
			qtySpinner.setParent(row);
			amountLbl.setParent(row);
			shippingCostLbl.setParent(row);
			
			Popup popup = new Popup();
			popup.setParent(row);
            Image productImage = new Image();
            //iconImg.setStyle("padding: 0px 10px");
            productImage.setContent(shoppingCartproducts.get(x).getAImage());
            productImage.setParent(popup);
            
			row.setTooltip(popup);
			
			row.setAttribute("product", shoppingCartproducts.get(x));
			row.setAttribute("checkoutQty", qtySpinner.getValue());
			row.setParent(rows);
			
			grandTotal = grandTotal + (shoppingCartproducts.get(x).getProdPrice() * qtySpinner.getValue());
		}
		grandTotalLbl.setValue("NAD " + String.valueOf(grandTotal) + "0");
		rows.setParent(shoppingCartGrid);
		shoppingCartInc.setAttribute("shoppingCartRows", rows);
	}*/
	
	/**
	 * Used to make all checkboxes either checked or unchecked
	 * @param event
	 */
	public void onClick$selectAllCheckbox(Event event) {
		Rows rows = shoppingCartGrid.getRows();
		Row row;
		if(selectAllCheckbox.isChecked() == true) {
			for(int x = 0; x < rows.getChildren().size(); x++){
				row = (Row)rows.getChildren().get(x);
				Checkbox checkbox = (Checkbox)row.getFirstChild();
				checkbox.setChecked(true);
			}
		} else {
			for(int x = 0; x < rows.getChildren().size(); x++){
				row = (Row)rows.getChildren().get(x);
				Checkbox checkbox = (Checkbox)row.getFirstChild();
				checkbox.setChecked(false);
			}
		}
	}
	
	public void onClick$confirmCheckoutCheckbox(Event event) {
		if(confirmCheckoutCheckbox.isChecked()) {
			proceedToCheckoutBtn.setDisabled(false);
		} else if(!confirmCheckoutCheckbox.isChecked()){
			proceedToCheckoutBtn.setDisabled(true);
		}
	}
	
	public void onClick$proceedToCheckoutBtn(Event event) {
		shoppingCartInc.setSrc("/unsecure/tabSliders/shoppingCartTabSlider/placeOrder.zul");
	}
	
	public void onCreate$placeOrderWin(Event event) {
		placeOrderWin.doOverlapped();
		
		SobieProfile sobieProfile = (SobieProfile) execution.getDesktop().getSession().getAttribute("sobieProfile");
		
		PaymentDetails paymentDetails = sobieProfile.getPaymentDetails();
		//paymentDetails.setPaymentOption("1");
		accountHolderNameTextbox.setValue(paymentDetails.getAccountHolderName());
		//paymentDetails.setPaymentType("M");
		cardNumberTextbox.setValue(paymentDetails.getCardNumber());
		csvTextbox.setValue(paymentDetails.getCsv());
		cardExpiryMonthCombobox.setValue(paymentDetails.getCardExpiryMonth());
		cardExpiryYearCombobox.setValue(paymentDetails.getCardExpiryYear());
		
		DeliveryDetails deliveryDetails = sobieProfile.getDeliveryDetails();
		//deliveryDetails.setDeliveryOption("1");
		addressLine1Textbox.setValue(deliveryDetails.getAddressLine1());
		addressLine2Textbox.setValue(deliveryDetails.getAddressLine2());
		suburbTextbox.setValue(deliveryDetails.getSuburb());
		cityTextbox.setValue(deliveryDetails.getCity());
		countryTextbox.setValue(deliveryDetails.getCountry());
		codeTextbox.setValue(String.valueOf(deliveryDetails.getCode()));
	}

	public void onClick$confirmOrderCheckbox(Event event) {
		if(confirmOrderCheckbox.isChecked()) {
			placeOrderBtn.setDisabled(false);
		} else if(!confirmOrderCheckbox.isChecked()){
			placeOrderBtn.setDisabled(true);
		}
	}
	
	public void onClick$placeOrderBtn(Event event) {

		SobieProfile sobieProfile = (SobieProfile) execution.getDesktop().getSession().getAttribute("sobieProfile");
		
		PaymentDetails paymentDetails = sobieProfile.getPaymentDetails();
		paymentDetails.setPaymentOption("1");
		paymentDetails.setAccountHolderName(accountHolderNameTextbox.getValue());
		paymentDetails.setPaymentType("M");
		paymentDetails.setCardNumber(cardNumberTextbox.getValue());
		paymentDetails.setCsv(csvTextbox.getValue());
		paymentDetails.setCardExpiryMonth(cardExpiryMonthCombobox.getValue());
		paymentDetails.setCardExpiryYear(cardExpiryYearCombobox.getValue());
		customerAccountService.updatePaymentDetails(sobieProfile);
		
		DeliveryDetails deliveryDetails = sobieProfile.getDeliveryDetails();
		deliveryDetails.setDeliveryOption(1);
		deliveryDetails.setAddressLine1(addressLine1Textbox.getValue());
		deliveryDetails.setAddressLine2(addressLine2Textbox.getValue());
		deliveryDetails.setSuburb(suburbTextbox.getValue());
		deliveryDetails.setCity(cityTextbox.getValue());
		deliveryDetails.setCountry(countryTextbox.getValue());
		deliveryDetails.setCode(Integer.valueOf(codeTextbox.getValue()));
		customerAccountService.updateDeliveryDetails(sobieProfile);

		DateFormat date = new SimpleDateFormat("yyyyMMdd");
		int storeDate = Integer.valueOf(date.format(new Date()));
		DateFormat time = new SimpleDateFormat("HHmmsss");
		int storetime = Integer.valueOf(time.format(new Date()));
		
    	SobieUtils sobieUtils = new SobieUtils();
    	final String orderNo = sobieUtils.createOrderNo();
		Order order;
		Rows shoppingCartRows = (Rows)shoppingCartInc.getAttribute("shoppingCartRows");
		for(int x = 0; x < shoppingCartRows.getChildren().size();x++) {
			Product product = (Product)shoppingCartRows.getChildren().get(x).getAttribute("product");
			int checkoutQty = (Integer)shoppingCartRows.getChildren().get(x).getAttribute("checkoutQty");
			order = new Order();
			order.setOrderNo(orderNo);
			order.setBuyerAccNo(sobieProfile.getAccount().getAccNo());
			order.setSellerAccNo(product.getSellerAccNo());
			//TODO: Check why the suppliers account number is not populated in the shopping cart products object?
			order.setSupplierAccNo(product.getOwnerAccNo());
			order.setShipperAccNo("Still to be coded");
			order.setProdId(product.getProdId());
			order.setProdQty(checkoutQty);
			order.setOrderDate(storeDate);
			order.setOrderTime(storetime);
			orderService.createNewOrder(sobieProfile, order);
		}		
        /**
         * TODO: Remove all products from shopping cart on successful order creation
         */
	}
	
}
