package biz.sobie.web.index;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

public class IndexCtrl extends GenericForwardComposer<Component> {

	private static final long serialVersionUID = 1L;
	
/*	public void onClientInfo(ClientInfoEvent event) throws IOException{
			UserWorkspace userWorkspace;
		if(Executions.getCurrent().getSession().getAttribute("userWorkspace") == null) {
			userWorkspace = new UserWorkspace();
			userWorkspace.setBrowserHeight(event.getDesktopHeight());
			userWorkspace.setBrowserWidth(event.getDesktopWidth());
			Executions.getCurrent().getSession().setAttribute("userWorkspace", userWorkspace);
		} else {
			userWorkspace = (UserWorkspace)Executions.getCurrent().getSession().getAttribute("userWorkspace");
			userWorkspace.setBrowserHeight(event.getDesktopHeight());
			userWorkspace.setBrowserWidth(event.getDesktopWidth());
			Executions.getCurrent().getSession().setAttribute("userWorkspace", userWorkspace);
		}
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
			//chatInc.setSrc(null);
	    	
		} else {
			//chatInc.setSrc("/secure/widgets/chatWidget/chatWidget.zul");
			
		}
	}*/
}
