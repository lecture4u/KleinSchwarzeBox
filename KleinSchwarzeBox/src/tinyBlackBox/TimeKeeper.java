package tinyBlackBox;

import java.util.ArrayList;

public class TimeKeeper implements Runnable {
	private boolean aLive = true;
	ArrayList<Attendee> list = new ArrayList<Attendee>();
	
	public void addAttendee(Attendee attendee) {
		for(Attendee oldBoy : list) {
			attendee.addAttendee(oldBoy);
			oldBoy.addAttendee(attendee);
		}
		list.add(attendee);
	}
	public void removeAttendee(Attendee attendee) {
		list.remove(attendee);
		for(Attendee oldBoy : list) {
			oldBoy.removeAttendee(attendee);
		}
	}
	public void notifyAllAttendees() {
		long timeStamp = System.currentTimeMillis();
		for(Attendee attendee : list) {
			attendee.vote(timeStamp);
		}
	}

	@Override
	public void run() {
		System.out.println("start");
		while(aLive) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	//		System.out.println("Tick");
			notifyAllAttendees();
	//		System.out.println("Tack");
		}
	}
	
	public void stop() {
		aLive = false;
		System.out.println("stop");
	}

	public static void main(String[] args) {
		long timeStamp = System.currentTimeMillis();
		int numOfChain = 3;		
		TinyChain[] member = new TinyChain[numOfChain];
		for(int i=0; i<numOfChain; i++) {
			member[i] = new TinyChain(i, "MD5", timeStamp, numOfChain);
		}

		TimeKeeper me = new TimeKeeper();
		for(int i=0; i<numOfChain; i++) {
			me.addAttendee(member[i]);
		}
		
		Wallet first1 = new Wallet(member[0]);
//		Wallet first2 = new Wallet(member[0]);
		Wallet second1 = new Wallet(member[1]);
//		Wallet second2 = new Wallet(second);
//		Wallet second3 = new Wallet(second);
		Wallet third1 = new Wallet(member[2]);
		Wallet third2 = new Wallet(member[2]);
//		Wallet third3 = new Wallet(third);

		Thread thread = new Thread(me);
		thread.start();
				
		first1.addTransaction("Help me");
		third1.addTransaction("count one");
		first1.addTransaction("Please");
		third1.addTransaction("count two");
		third2.addTransaction(">>");
		third2.addTransaction(">>>>");
		second1.addTransaction("Try me");
		third1.addTransaction("count three");
		second1.addTransaction("If you can");
		third1.addTransaction("count four");
		third2.addTransaction(">>>>>>");
		third2.addTransaction(">>>>>>>>");
	
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		first1.addTransaction("Help yourself");
		second1.addTransaction("Try yourself");
		third2.addTransaction(">>>>>>>>>>");
		third2.addTransaction(">>>>>>>>>>>>");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		me.stop();
		
	    System.out.println("*--------first user's blockchain--------*");

		System.out.println(member[0].toString());
		System.out.println("*-------first user's blockchain's first block");
		System.out.println(member[0].getBlock(0).toString());
		System.out.println("*-------first user's blockchain's second block");
		System.out.println(member[0].getBlock(1).toString());
	}
}
