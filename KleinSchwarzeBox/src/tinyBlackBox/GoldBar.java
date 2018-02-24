package tinyBlackBox;

public class GoldBar {
	private byte[] blockFixed = null;
	private Wallet belongTo = null;
	private int transactionID = -1; 
	private String message;

	GoldBar(String message) {
		this.message = message;
	}
	public byte[] getFixed() {
		return blockFixed;
	}
	public Wallet getBelong() {
		return belongTo;
	}
	public int getTransactionID() {
		return transactionID;
	}
	public String getMessage() {
		return message;
	}
	public void setFixed(byte[] fixed) {
		this.blockFixed = fixed;
	}
	public void setBelong(Wallet belong) {
		this.belongTo = belong;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
}
