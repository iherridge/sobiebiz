package biz.sobie.web.gmaps;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.event.MapMouseEvent;

public class SobieGmapsVm {

	@Init
	public void init(){
		
	}
	
	@Command
    public void mapMouseEvent(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {
            MapMouseEvent event = (MapMouseEvent)ctx.getTriggerEvent();
            if ("onMapClick".equals(event.getName()) && (event.getReference() instanceof Gmarker)) {
                   
            }
    }
}
