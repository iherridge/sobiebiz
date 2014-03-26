package biz.sobie.web.store;

import biz.sobie.web.beans.SobieImage;

public class StoreContentPage {

	private String pageId;
	private String pageName;
	private String pageTittle;
	private String pageParentId;
	private boolean pageEnabled;
	private String pageEnabledImage;
	private String pageContent;
	private String storeNo;
	private SobieImage storeContentPageHeaderImage;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageTittle() {
		return pageTittle;
	}

	public void setPageTittle(String pageTittle) {
		this.pageTittle = pageTittle;
	}

	public String getPageParentId() {
		return pageParentId;
	}

	public void setPageParentId(String pageParentId) {
		this.pageParentId = pageParentId;
	}

	public boolean isPageEnabled() {
		return pageEnabled;
	}

	public void setPageEnabled(boolean pageEnabled) {
		if(pageEnabled){
			pageEnabledImage = "resources/img/defaults/active.png";
		} else {
			pageEnabledImage = "resources/img/defaults/inactive.png";
		}
		this.pageEnabled = pageEnabled;
	}

	public String getPageContent() {
		return pageContent;
	}

	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public SobieImage getStoreContentPageHeaderImage() {
		return storeContentPageHeaderImage;
	}

	public void setStoreContentPageHeaderImage(
			SobieImage storeContentPageHeaderImage) {
		this.storeContentPageHeaderImage = storeContentPageHeaderImage;
	}
	
	public String getContentPageStatusImage() {
		
		
		return pageEnabledImage;
	}

	public String getPageEnabledImage() {
		return pageEnabledImage;
	}

	public void setPageEnabledImage(String pageEnabledImage) {
		this.pageEnabledImage = pageEnabledImage;
	}
}
