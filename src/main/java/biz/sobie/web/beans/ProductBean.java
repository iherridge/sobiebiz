package biz.sobie.web.beans;

import java.io.InputStream;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.sobie.web.services.ProductService;

@Deprecated
public class ProductBean{

	private String[] name;
	private Double[] price;
	private InputStream[] image;
	List<Product> productList;
	
	public ProductBean() {

	}
	
	@Deprecated
	public ProductBean(final String id) {

		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
		ProductService service = (ProductService)appContext.getBean("productService");
		List<Product> products = null;// = (List<Product>)service.retrieveProduct(id);
		this.productList = products;
    }

	public List<Product> getProductList() {
		return productList;
	}
	
	public String[] getName() {
		return this.name;
	}

	public Double[] getPrice() {
		return price;
	}
	
	public InputStream[] getImage() {
		return this.image;
	}
}
