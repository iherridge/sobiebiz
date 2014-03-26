package biz.sobie.web.services;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.sobie.web.beans.Product;
import biz.sobie.web.beans.SobieProfile;

@Deprecated
public class ProductServiceBean{

	List<Product> productList;
	
	/**
	 * Retrieve list of all products to display
	 */
	@Deprecated
	public ProductServiceBean() {

		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
		ProductService service = (ProductService)appContext.getBean("productService");
		List<Product> products = null;//(List<Product>)service.retrieveProduct();
		this.productList = products;
    }

	/**
	 * Add New Product
	 * @param product
	 */
	@Deprecated
	public ProductServiceBean(Product product, String accNo) {

		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
		ProductService service = (ProductService)appContext.getBean("productService");
		service.saveNewProduct(product, accNo);
    }
	
	/**
	 * Add New Product
	 * @param product
	 */
	@Deprecated
	public ProductServiceBean(String prodId, String storeNo, String accNo, String command) {

		if(command.equals("SELLER_SHOP")) {
		
			/**
			 * Seller ads a product from a supplier to his/her shop
			 */
			ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
			ProductService service = (ProductService)appContext.getBean("productService");
			//service.addProductToSellerShop(prodId, accNo);
		}/* else if(command.equals("SHOPPING_CART")) {
			*//**
			 * Buyer ads a product from a seller to his/her shopping cart
			 *//*
			ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
			ProductService service = (ProductService)appContext.getBean("productService");
			String sellerStoreNo = storeNo;
			service.addProductToShoppingCart(prodId, accNo, sellerStoreNo);
		}*/
    }

/*	*//**
	 * Display Seller or Suppliers Products in Shop
	 * @param accountType
	 * @throws IOException 
	 *//*
	@Deprecated
	public ProductServiceBean(String accountType) throws IOException {

		if(accountType.equals("anonymous") || accountType.startsWith("BU")) {
		
			ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
			ProductService service = (ProductService)appContext.getBean("productService");
			//List<Product> products = (List<Product>)service.retrieveProductForBuyers();
			this.productList = null;
			
		} else if (accountType.startsWith("SE")){
		
			ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
			ProductService service = (ProductService)appContext.getBean("productService");
			List<Product> products = null;//(List<Product>)service.retrieveProductForSellers();
			this.productList = products;
			
		} else if (accountType.equals("seller")) {
		
			ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
			ProductService service = (ProductService)appContext.getBean("productService");
			HomeTabBean homeTabBean = new HomeTabBean();
			List<Product> products = (List<Product>)service.retrieveStoreProducts(homeTabBean.getSobieProfile().getAccount().getAccNo());
			this.productList = products;
			
		}
    }*/
	
	/**
	 * Display Buyers Shopping Cart
	 * @param accountType
	 */
	@Deprecated
	public ProductServiceBean(String command, SobieProfile sobieProfile) {

		if(command.equals("SHOPPING_CART")) {
		
			ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
			ProductService service = (ProductService)appContext.getBean("productService");
			List<Product> products = (List<Product>)service.retrieveShoppingCart(sobieProfile);
			this.productList = products;
			
		}
	}
	
	@Deprecated
	public List<Product> getProductList() {
		return productList;
	}

}
