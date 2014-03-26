package biz.sobie.web.threads;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zkoss.lang.Threads;

import biz.sobie.web.beans.Product;
import biz.sobie.web.services.ProductService;

@Deprecated
public class TopSellingProductsThread extends Thread {

	private List<Product> _products;
	private boolean _ceased;

	public TopSellingProductsThread() {
		set_products(null);
		run();
	}

	public void run() {
		try {
			while (!_ceased) {
				Threads.sleep(10000); // Update each 10 seconds
				try {
					ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
					ProductService service = (ProductService)appContext.getBean("productService");
					//set_products((List<Product>)service.browseProducts(sobieProfile));
					
				} catch (RuntimeException ex) {
					throw ex;
				} catch (Error ex) {
					throw ex;
				} finally {
					
				}
			}
		} finally{
			
		}
	}

	public void setDone() {
		_ceased = true;
	}

	public void set_products(List<Product> _products) {
		this._products = _products;
	}

	public List<Product> get_products() {
		return _products;
	}
	
}