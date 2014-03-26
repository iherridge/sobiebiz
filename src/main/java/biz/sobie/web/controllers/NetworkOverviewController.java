package biz.sobie.web.controllers;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Tab;

public class NetworkOverviewController extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;
	Tab networkProfileTab;
	
	public void onClick$myMessageCentreLbl(Event event){
		networkProfileTab.setSelected(true);
	}
}
