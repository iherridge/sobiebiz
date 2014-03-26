package biz.sobie.web.beans;

public class IpAddress {

	private String IpAddress;
	private String custNo;
	private String defaultIp;

	public String getIpAddress() {
		return IpAddress;
	}

	public void setIpAddress(String ipAddress) {
		IpAddress = ipAddress;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getDefaultIp() {
		return defaultIp;
	}

	public void setDefaultIp(String string) {
		this.defaultIp = string;
	}
}
