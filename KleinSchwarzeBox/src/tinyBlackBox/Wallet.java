package tinyBlackBox;

import java.util.ArrayList;

public class Wallet {
	private TinyChain myhost;
	private ArrayList<GoldBar> barList = new ArrayList<GoldBar>();

	public Wallet(TinyChain host) {
		this.myhost = host;
	}

	public int addTransaction(String message) {
		GoldBar myBar = new GoldBar(message);
		myBar.setBelong(this);
		myBar.setTransactionID(myhost.addTransaction(myBar));
		return myBar.getTransactionID();
	}
	
	public void putIn(GoldBar yourBar) {
		barList.add(yourBar);
	}

	public String toString() {
		String bars = String.format("%s %h %s %h %s", "\n<< Wallet", this, "belongs to", this.myhost,">>\n");
		for(GoldBar bar : barList) {
			bars += String.format("%h %d %s\n", bar.getFixed(), bar.getTransactionID(), bar.getMessage());
		}
		return bars;
	}
}
