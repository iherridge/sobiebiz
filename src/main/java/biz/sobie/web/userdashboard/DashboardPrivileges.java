package biz.sobie.web.userdashboard;

public class DashboardPrivileges {

	private boolean dashboardStoreVisible;
	private boolean dashboardNetworkAffiliatesVisible;
	private boolean dashboardInventoryCategoriesVisible;
	private boolean dashboardInventoryProductsVisible;
	private boolean dashboardInventoryNetworkProductsVisible;
	private boolean dashboardInventoryServicesVisible;
	private boolean dashboardInventoryAttributesVisible;
	private boolean dashboardSalesManageQuotationsVisible;
	private boolean dashboardSalesShipmentsVisible;
	private boolean dashboardSalesReturnsVisible;
	private boolean dashboardSalesBillingAgreementsVisible;
	private boolean dashboardSalesTermsAndConditionsVisible;
	private boolean dashboardSalesTaxVisible;
	private boolean dashboardMarketingBannersVisible;
	private boolean dashboardMarketingCouponsVisible;
	private boolean dashboardMarketingNewslettersVisible;
	private boolean dashboardReportsVisible;
	
	public DashboardPrivileges(String ppId) {
		if(ppId.startsWith("BU")){
			this.dashboardStoreVisible = false;
			this.dashboardNetworkAffiliatesVisible = false;
			this.dashboardInventoryCategoriesVisible = false;
			this.dashboardInventoryProductsVisible = false;
			this.dashboardInventoryNetworkProductsVisible = false;
			this.dashboardInventoryServicesVisible = false;
			this.dashboardInventoryAttributesVisible = false;
			this.dashboardSalesManageQuotationsVisible = false;
			this.dashboardSalesShipmentsVisible = false;
			this.dashboardSalesReturnsVisible = false;
			this.dashboardSalesBillingAgreementsVisible = false;
			this.dashboardSalesTermsAndConditionsVisible = false;
			this.dashboardSalesTaxVisible = false;
			this.dashboardMarketingBannersVisible = false;
			this.dashboardMarketingCouponsVisible = false;
			this.dashboardMarketingNewslettersVisible = false;
			this.dashboardReportsVisible = false;
		} else if(ppId.startsWith("S")) {
			this.dashboardStoreVisible = true;
			this.dashboardNetworkAffiliatesVisible = true;
			this.dashboardInventoryCategoriesVisible = true;
			this.dashboardInventoryProductsVisible = true;
			this.dashboardInventoryNetworkProductsVisible = true;
			this.dashboardInventoryServicesVisible = true;
			this.dashboardInventoryAttributesVisible = true;
			this.dashboardSalesManageQuotationsVisible = true;
			this.dashboardSalesShipmentsVisible = true;
			this.dashboardSalesReturnsVisible = true;
			this.dashboardSalesBillingAgreementsVisible = true;
			this.dashboardSalesTermsAndConditionsVisible = true;
			this.dashboardSalesTaxVisible = true;
			this.dashboardMarketingBannersVisible = true;
			this.dashboardMarketingCouponsVisible = true;
			this.dashboardMarketingNewslettersVisible = true;
			this.dashboardReportsVisible = true;
		}
	}

	public boolean isDashboardStoreVisible() {
		return dashboardStoreVisible;
	}

	public void setDashboardStoreVisible(boolean dashboardStoreVisible) {
		this.dashboardStoreVisible = dashboardStoreVisible;
	}

	public boolean isDashboardNetworkAffiliatesVisible() {
		return dashboardNetworkAffiliatesVisible;
	}

	public void setDashboardNetworkAffiliatesVisible(
			boolean dashboardNetworkAffiliatesVisible) {
		this.dashboardNetworkAffiliatesVisible = dashboardNetworkAffiliatesVisible;
	}

	public boolean isDashboardInventoryCategoriesVisible() {
		return dashboardInventoryCategoriesVisible;
	}

	public void setDashboardInventoryCategoriesVisible(
			boolean dashboardInventoryCategoriesVisible) {
		this.dashboardInventoryCategoriesVisible = dashboardInventoryCategoriesVisible;
	}

	public boolean isDashboardInventoryProductsVisible() {
		return dashboardInventoryProductsVisible;
	}

	public void setDashboardInventoryProductsVisible(
			boolean dashboardInventoryProductsVisible) {
		this.dashboardInventoryProductsVisible = dashboardInventoryProductsVisible;
	}

	public boolean isDashboardInventoryNetworkProductsVisible() {
		return dashboardInventoryNetworkProductsVisible;
	}

	public void setDashboardInventoryNetworkProductsVisible(
			boolean dashboardInventoryNetworkProductsVisible) {
		this.dashboardInventoryNetworkProductsVisible = dashboardInventoryNetworkProductsVisible;
	}

	public boolean isDashboardInventoryServicesVisible() {
		return dashboardInventoryServicesVisible;
	}

	public void setDashboardInventoryServicesVisible(
			boolean dashboardInventoryServicesVisible) {
		this.dashboardInventoryServicesVisible = dashboardInventoryServicesVisible;
	}

	public boolean isDashboardInventoryAttributesVisible() {
		return dashboardInventoryAttributesVisible;
	}

	public void setDashboardInventoryAttributesVisible(
			boolean dashboardInventoryAttributesVisible) {
		this.dashboardInventoryAttributesVisible = dashboardInventoryAttributesVisible;
	}

	public boolean isDashboardSalesManageQuotationsVisible() {
		return dashboardSalesManageQuotationsVisible;
	}

	public void setDashboardSalesManageQuotationsVisible(
			boolean dashboardSalesManageQuotationsVisible) {
		this.dashboardSalesManageQuotationsVisible = dashboardSalesManageQuotationsVisible;
	}

	public boolean isDashboardSalesShipmentsVisible() {
		return dashboardSalesShipmentsVisible;
	}

	public void setDashboardSalesShipmentsVisible(
			boolean dashboardSalesShipmentsVisible) {
		this.dashboardSalesShipmentsVisible = dashboardSalesShipmentsVisible;
	}

	public boolean isDashboardSalesReturnsVisible() {
		return dashboardSalesReturnsVisible;
	}

	public void setDashboardSalesReturnsVisible(boolean dashboardSalesReturnsVisible) {
		this.dashboardSalesReturnsVisible = dashboardSalesReturnsVisible;
	}

	public boolean isDashboardSalesBillingAgreementsVisible() {
		return dashboardSalesBillingAgreementsVisible;
	}

	public void setDashboardSalesBillingAgreementsVisible(
			boolean dashboardSalesBillingAgreementsVisible) {
		this.dashboardSalesBillingAgreementsVisible = dashboardSalesBillingAgreementsVisible;
	}

	public boolean isDashboardSalesTermsAndConditionsVisible() {
		return dashboardSalesTermsAndConditionsVisible;
	}

	public void setDashboardSalesTermsAndConditionsVisible(
			boolean dashboardSalesTermsAndConditionsVisible) {
		this.dashboardSalesTermsAndConditionsVisible = dashboardSalesTermsAndConditionsVisible;
	}

	public boolean isDashboardSalesTaxVisible() {
		return dashboardSalesTaxVisible;
	}

	public void setDashboardSalesTaxVisible(boolean dashboardSalesTaxVisible) {
		this.dashboardSalesTaxVisible = dashboardSalesTaxVisible;
	}

	public boolean isDashboardMarketingBannersVisible() {
		return dashboardMarketingBannersVisible;
	}

	public void setDashboardMarketingBannersVisible(
			boolean dashboardMarketingBannersVisible) {
		this.dashboardMarketingBannersVisible = dashboardMarketingBannersVisible;
	}

	public boolean isDashboardMarketingCouponsVisible() {
		return dashboardMarketingCouponsVisible;
	}

	public void setDashboardMarketingCouponsVisible(
			boolean dashboardMarketingCouponsVisible) {
		this.dashboardMarketingCouponsVisible = dashboardMarketingCouponsVisible;
	}

	public boolean isDashboardMarketingNewslettersVisible() {
		return dashboardMarketingNewslettersVisible;
	}

	public void setDashboardMarketingNewslettersVisible(
			boolean dashboardMarketingNewslettersVisible) {
		this.dashboardMarketingNewslettersVisible = dashboardMarketingNewslettersVisible;
	}

	public boolean isDashboardReportsVisible() {
		return dashboardReportsVisible;
	}

	public void setDashboardReportsVisible(boolean dashboardReportsVisible) {
		this.dashboardReportsVisible = dashboardReportsVisible;
	}

	
}
