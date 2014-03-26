package biz.sobie.web.store;

public class StoreMenuItem {

	private String menuItem;
	private StoreContentPage storeContentPage;
	
	public String getMenuItem() {
		return menuItem;
	}
	
	public void setMenuItem(String menuItem) {
		this.menuItem = menuItem;
	}

	public StoreContentPage getStoreContentPage() {
		return storeContentPage;
	}

	public void setStoreContentPage(StoreContentPage storeContentPage) {
		this.storeContentPage = storeContentPage;
	}
}
