package biz.sobie.web.controllers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

public class TextboxSearchController extends GenericForwardComposer<Component> {

	private static final long serialVersionUID = 1L;
	Window userSearchWin;
	Include popupInc;
	Button userSearchBtn;
	Combobox userSearchComboBox,
			 searchComboBox;
	Label toLbl;
	
	/*public void onSelect$userSearchComboBox(SelectEvent event){
		Comboitem selectedItem = (Comboitem)event.getReference();
		String custNo = (String)selectedItem.getAttribute("custNo");
		String labelTxt = toLbl.getValue();
		String custNos = (String) toLbl.getAttribute("custNos");
		if(labelTxt.equals("")){
			labelTxt = selectedItem.getLabel() + ";";
			toLbl.setValue(labelTxt);
		} else {
			labelTxt = labelTxt + " " + selectedItem.getLabel() + ";";
			toLbl.setValue(labelTxt);
		}
		if(custNos == null){
			custNos = custNo + ";";
			toLbl.setAttribute("custNos", custNos);
		} else {
			custNos = custNos + " " + custNo + ";";
			toLbl.setAttribute("custNos", custNos);
		}
		userSearchComboBox.setValue("");
		userSearchComboBox.setFocus(true);
	}*/
	
	/*public void onChanging$userSearchComboBox(InputEvent event) throws Exception{
		
		if(event.getValue().length() != 0) {
					
			userSearchComboBox.getItems().clear();
			userSearchComboBox.setOpen(false);
			
			if(event.getValue().length() >= 3) {
			
				userSearchComboBox.setOpen(true);
			
				ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
				CustomerAccountService customerAccountService = (CustomerAccountService)appContext.getBean("customerAccountService");
				List<SearchResultsBean> customers;
				Comboitem comboItem = null;
				AImage aImage = null;
				userSearchComboBox.getItems().clear();
				
				try {
					*//**
					 * Search for customers
					 *//*
					customers = (List<SearchResultsBean>)customerAccountService.customerSearch(event.getValue());
					for(int x = 0; x < customers.size(); x++){
						comboItem = new Comboitem();
						aImage = new AImage("image", customers.get(x).getImgInBytes().getBinaryStream());
						comboItem.setImageContent(aImage);
						comboItem.setWidth("20px");
						comboItem.setHeight("2px");	 
						comboItem.setLabel(customers.get(x).getLabel());
						comboItem.setAttribute("custNo", customers.get(x).getId());
						userSearchComboBox.appendChild(comboItem);
					}
				} catch (WrongValueException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				userSearchComboBox.setOpen(false);
				userSearchComboBox.getItems().clear();
			}
		}
	}*/
	
	/*public void onChanging$searchComboBox(InputEvent event) throws Exception{
		
		if(event.getValue().length() != 0) {
			
			searchComboBox.getItems().clear();
			searchComboBox.setOpen(false);
			
			if(event.getValue().length() >= 3) {
			
				searchComboBox.setOpen(true);
			
				ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
				CustomerAccountService customerAccountService = (CustomerAccountService)appContext.getBean("customerAccountService");
				ProductService productService = (ProductService)appContext.getBean("productService");
				List<SearchResultsBean> customers;
				List<SearchResultsBean> products;
				Comboitem comboItem = null;
				AImage aImage = null;
				searchComboBox.getItems().clear();
				
				try {
					*//**
					 * Search for customers
					 *//*
					customers = (List<SearchResultsBean>)customerAccountService.customerSearch(event.getValue());
					for(int x = 0; x < customers.size(); x++){
						comboItem = new Comboitem();
						aImage = new AImage("image", customers.get(x).getImgInBytes().getBinaryStream());
						comboItem.setImageContent(aImage);
						comboItem.setWidth("20px");
						comboItem.setHeight("2px");	 
						comboItem.setLabel(customers.get(x).getLabel());
						comboItem.setAttribute("custNo", customers.get(x).getId());
						searchComboBox.appendChild(comboItem);
					}
					*//**
					 * Search for products
					 *//*
					products = (List<SearchResultsBean>)productService.productSearch(event.getValue());
					for(int x = 0; x < products.size(); x++){
						comboItem = new Comboitem();
						aImage = new AImage("image", products.get(x).getImgInBytes().getBinaryStream());
						comboItem.setImageContent(aImage);
						comboItem.setWidth("20px");
						comboItem.setHeight("2px");	 
						comboItem.setLabel(products.get(x).getLabel());
						comboItem.setAttribute("custNo", products.get(x).getId());
						searchComboBox.appendChild(comboItem);
					}
				} catch (WrongValueException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				searchComboBox.setOpen(false);
				searchComboBox.getItems().clear();
			}
		}
	}*/
}
