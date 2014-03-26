package biz.sobie.web.viewmodel;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import biz.sobie.web.beans.UserWorkspace;
import biz.sobie.web.services.CustomerAccountService;

public class UserWorkspaceViewModel {

	private UserWorkspace workspace;
	
	@WireVariable
	CustomerAccountService customerAccountService;
	
	@Init(superclass=true)
	public void init(@ContextParam(ContextType.PAGE) Page page, @ContextParam(ContextType.APPLICATION) WebApp application, @ContextParam(ContextType.VIEW) Component view,
			@ContextParam(ContextType.EXECUTION) Execution execution,  @ContextParam(ContextType.DESKTOP) Desktop desktop) {
		if(Executions.getCurrent().getSession().getAttribute("userWorkspace") == null) {
			UserWorkspace userWorkspace = new UserWorkspace();
			Executions.getCurrent().getSession().setAttribute("userWorkspace", userWorkspace);
			setWorkspace(userWorkspace);
		} else {
			UserWorkspace userWorkspace = (UserWorkspace)Executions.getCurrent().getSession().getAttribute("userWorkspace");
			setWorkspace(userWorkspace);
		}
	}

	public void setWorkspace(UserWorkspace workspace) {
		this.workspace = workspace;
	}

	@NotifyChange("workspace")
	public UserWorkspace getWorkspace() {
		return workspace;
	}
	
	@Command
	public void onClientInfo(@BindingParam("targetEvent") ClientInfoEvent event) {
		event.getScreenWidth();
	}
}