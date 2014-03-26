package biz.sobie.web.viewmodel;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import biz.sobie.web.beans.Order;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.services.OrderService;

public class OrderViewModel {

	private Order selectedOrder;
	private List<Order> orderList;

	@Init
	public void init() {
		SobieProfile sobieProfile = (SobieProfile) Executions.getCurrent().getDesktop().getSession().getAttribute("sobieProfile");
		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
    	OrderService orderService = (OrderService)appContext.getBean("orderService");
    	orderList = orderService.retrieveOrderList(sobieProfile);
    	if(orderList.size() != 0) {
    		selectedOrder = orderList.get(0); //Selected the first order in the list
    	}
    	
	}
	
	public Order getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(Order selectedOrder) {
		this.selectedOrder = selectedOrder;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrders(List<Order> orderList) {
		this.orderList = orderList;
	}
}
