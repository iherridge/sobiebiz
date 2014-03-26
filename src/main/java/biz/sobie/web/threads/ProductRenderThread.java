package biz.sobie.web.threads;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.zkoss.lang.Threads;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Vbox;

import biz.sobie.web.beans.Product;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.beans.UserWorkspace;
import biz.sobie.web.services.ProductService;

@Deprecated
public class ProductRenderThread extends Thread {

	private final Desktop _desktop;
	private final Columns _productGridColumns;
	private final Rows _productGridRows;
	private List<Product> products = null;
	private int browserWidth = 0;
	private boolean init = false;
	private boolean _ceased;
	private int timer = 0;

	public ProductRenderThread(Columns productGridColumns, Rows productGridRows) {
		_desktop = productGridColumns.getDesktop();
		_productGridColumns = productGridColumns;
		_productGridRows = productGridRows;
	}

	public void run() {
		try {
			while (!_ceased) {
				if(init == true) {
					//TODO: this needs to be optimized. Have a thread in the background which this thread uses to get its data from
					Threads.sleep(10); // Update each 10 seconds
					timer = timer + 10;
				}
				init = true;
				if(_desktop.isAlive() == false){
					setDone();
				}
				Executions.activate(_desktop);
				try {
					if(timer == 0 || timer == 60000) {
						ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
						ProductService service = (ProductService)appContext.getBean("productService");
						SobieProfile sobieProfile = (SobieProfile)Executions.getCurrent().getSession().getAttribute("sobieProfile");
						//products = (List<Product>)service.browseProducts(sobieProfile);
					}
					Row row = new Row();
					
					UserWorkspace userWorkspace = (UserWorkspace)Executions.getCurrent().getSession().getAttribute("userWorkspace");
					if(userWorkspace != null && browserWidth != userWorkspace.getBrowserWidth()){
						browserWidth = userWorkspace.getBrowserWidth();
						int maxColumns = (int)((browserWidth-(browserWidth*0.2)-200-130)/200);
						if(browserWidth < 976) {
							maxColumns = 1;
						} else if(browserWidth < 1226) {
							maxColumns = 2;
						} else if(browserWidth < 1476) {
							maxColumns = 3;
						} else if(browserWidth < 1726) {
							maxColumns = 4;
						} else if(browserWidth < 1976) {
							maxColumns = 5;
						} else if(browserWidth < 2226) {
							maxColumns = 6;
						} else if(browserWidth < 2476) {
							maxColumns = 7;
						} else if(browserWidth < 2726) {
							maxColumns = 8;
						} else if(browserWidth < 2976) {
							maxColumns = 9;
						} else if(browserWidth < 3226) {
							maxColumns = 10;
						} else if(browserWidth < 3476) {
							maxColumns = 11;
						} else if(browserWidth < 3726) {
							maxColumns = 12;
						}
						if(maxColumns > products.size()){
							maxColumns = products.size();
						}
						
						_productGridColumns.getChildren().clear();
						_productGridRows.getChildren().clear();
						for(int x = 0; x < maxColumns; x++) {
							Column column = new Column();
							column.setWidth("200px");
							column.setHeight("250px");
							column.setParent(_productGridColumns);
							
							Vbox vbox = new Vbox();
							vbox.setParent(row);
							
							Image image = new Image();
							image.setContent(products.get(x).getImageCatalog().get(0).getAImage());
							image.setTooltiptext(products.get(x).getProdName());
							image.setDraggable("true");
							image.setWidth("200px");
							image.setHeight("200px");
							image.setParent(vbox);
							image.setAttribute("product", products.get(x));
						
							Label productName = new Label(products.get(x).getProdName());
							productName.setParent(vbox);
						
							Label description = new Label(products.get(x).getProdDesc());
							description.setParent(vbox);
						
							Label price = new Label();
							//price.setValue(((Double)products.get(x).getProdPrice()).toString());
							price.setParent(vbox);
						}
						row.setParent(_productGridRows);
					}
				} catch (RuntimeException ex) {
					throw ex;
				} catch (Error ex) {
					throw ex;
				} finally {
					Executions.deactivate(_desktop);
				}
			}
		} catch (InterruptedException ex) {
		}
	}

	public void setDone() {
		_ceased = true;
	}
	
}