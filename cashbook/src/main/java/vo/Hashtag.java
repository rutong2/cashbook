package vo;

public class Hashtag {
	private int cashbookNo;
	private String tag;
	private String createDate;
	
	public int getCashbookNo() {
		return cashbookNo;
	}
	public void setCashbookNo(int cashbookNo) {
		this.cashbookNo = cashbookNo;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public String toString() {
		return "Hashtag [cashbookNo=" + cashbookNo + ", tag=" + tag + ", createDate=" + createDate + "]";
	}
	
}
