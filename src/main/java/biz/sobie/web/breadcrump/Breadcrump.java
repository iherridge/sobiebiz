package biz.sobie.web.breadcrump;

public class Breadcrump {

	private boolean breadcrumpShoppingCartVisible;
	private boolean breadcrumpStoreSettingsVisible;
	private boolean breadcrumpSystemUsersVisible;

	public Breadcrump() {
		breadcrumpShoppingCartVisible = false;
		setBreadcrumpStoreSettingsVisible(false);
	}
	
	public boolean isBreadcrumpShoppingCartVisible() {
		return breadcrumpShoppingCartVisible;
	}

	public void setBreadcrumpShoppingCartVisible(
			boolean breadcrumpShoppingCartVisible) {
		this.breadcrumpShoppingCartVisible = breadcrumpShoppingCartVisible;
	}

	public boolean isBreadcrumpStoreSettingsVisible() {
		return breadcrumpStoreSettingsVisible;
	}

	public void setBreadcrumpStoreSettingsVisible(
			boolean breadcrumpStoreSettingsVisible) {
		this.breadcrumpStoreSettingsVisible = breadcrumpStoreSettingsVisible;
	}

	public boolean isBreadcrumpSystemUsersVisible() {
		return breadcrumpSystemUsersVisible;
	}

	public void setBreadcrumpSystemUsersVisible(boolean breadcrumpSystemUsersVisible) {
		this.breadcrumpSystemUsersVisible = breadcrumpSystemUsersVisible;
	}
	
	public void resetBreadcrump(){
		setBreadcrumpSystemUsersVisible(false);
		setBreadcrumpStoreSettingsVisible(false);
		setBreadcrumpShoppingCartVisible(false);
	}
	
}
