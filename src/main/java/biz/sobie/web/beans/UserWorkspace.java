package biz.sobie.web.beans;

public class UserWorkspace {

	private int browserWidth;
	private int browserHeight;
	private int maxProductColumns;
	private int indexWestWidth;
	private int indexEastWidth;
	private String indexCenterWidth = "960px";
	

	public int getBrowserWidth() {
		return browserWidth;
	}

	public int getOusideIndexPaddingWidth() {
		return (browserWidth / 2) - 960;
	}
	
	public void setBrowserWidth(int browserWidth) {
		setIndexWestWidth((browserWidth - 1000) / 2);
		setIndexEastWidth(((browserWidth - 1000) / 2) - 20);
		this.browserWidth = browserWidth;
	}

	public int getBrowserHeight() {
		return browserHeight;
	}

	public void setBrowserHeight(int browserHeight) {
		this.browserHeight = browserHeight;
	}

	public int getMaxProductColumns() {
		return maxProductColumns;
	}

	public void setMaxProductColumns(int maxProductColumns) {
		this.maxProductColumns = maxProductColumns;
	}

	public int getIndexWestWidth() {
		return indexWestWidth;
	}

	public void setIndexWestWidth(int indexWestWidth) {
		this.indexWestWidth = indexWestWidth;
	}

	public int getIndexEastWidth() {
		return indexEastWidth;
	}

	public void setIndexEastWidth(int indexEastWidth) {
		this.indexEastWidth = indexEastWidth;
	}

	public String getIndexCenterWidth() {
		return indexCenterWidth;
	}

	public void setIndexCenterWidth(String indexCenterWidth) {
		this.indexCenterWidth = indexCenterWidth;
	}

}
