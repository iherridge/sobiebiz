package biz.sobie.web.controllers;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

public class SignInController extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;
	Window signinWin;
	Include signinInc;
	
	public void onCreate$signinWin(Event event) {
		signinWin.doOverlapped();
	}
	
	public void onClose$signinWin(Event event) {
		signinInc.setSrc(null);
	}
}
