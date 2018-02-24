package tinyBlackBox;

import java.util.ArrayList;
import java.util.Random;

public class TinyChain implements Attendee {
	ArrayList<Attendee> list = new ArrayList<Attendee>();
	
	@Override
	public void addAttendee(Attendee attendee) {
		list.add(attendee);
	}
	@Override
	public void removeAttendee(Attendee attendee) {
		list.remove(attendee);
	}
	@Override
	public void notifyAllAttendees(String topic, int data) {
		for(Attendee attendee : list) {
			attendee.update(myID, topic, data);
		}
	}

	private String hashAlgorithm; // choice of hash algorithm: "hashAlg-256", "MD5"

	private int myID;
	private ArrayList<byte[]> hashChain = new ArrayList<byte[]>();
	private ArrayList<TinyBlock> blockChain = new ArrayList<TinyBlock>();
	private TinyBlock currentBlock;
	private ArrayList<GoldBar> barList = new ArrayList<GoldBar>();
	private int[] votedFor;
	private int countVote = 0;

	public TinyChain(int givenID, String algorithm, long seed, int numOfMember) {
		this.myID = givenID;
		this.votedFor = new int[numOfMember];
		hashAlgorithm = algorithm;
		currentBlock = new TinyBlock(hashAlgorithm);
		String cTime = String.format("%d", seed);
		byte[] buffer = cTime.getBytes();
		currentBlock.setPreviousBlockHash(currentBlock.hashFn(buffer));
	}

	private boolean addBlock(byte[] key) {
		if(hashChain.contains(key)) return false; // check uniqueness
		hashChain.add(key);
		blockChain.add(currentBlock);
		if(hashChain.indexOf(key) != blockChain.indexOf(currentBlock)) return false; // phantom key:value
		currentBlock = new TinyBlock(hashAlgorithm);
		currentBlock.setPreviousBlockHash(key);		
		return true;
	}

	public void buildBlock(long consensus) {
		if(this.barList.size() > 0) {
			String[] messages = new String[this.barList.size()];
			for(int i=0; i<this.barList.size(); i++) {
				messages[i] = this.barList.get(i).getMessage();
			}
			this.currentBlock.setMessages(messages);
			this.currentBlock.setNonce(this.currentBlock.hashFn(String.format("Young 0k %d", consensus).getBytes()));
			byte[] key = currentBlock.buildBlock();   // <<-
			for(GoldBar bar: barList) {
				bar.setFixed(key); // bar.setFixed(this.currentBlock); // 
				bar.setTransactionID(barList.indexOf(bar));
				if(bar.getBelong() != null) bar.getBelong().putIn(bar);
			}		
			this.barList.clear();
			if(addBlock(key)) {
				this.currentBlock = new TinyBlock(this.hashAlgorithm);
				this.currentBlock.setPreviousBlockHash(key);
			}
			else System.err.println("Block Collision");
		}
	}
	
	public int addTransaction(GoldBar goldBar) {
		this.notifyAllAttendees(goldBar.getMessage(), 0);
		barList.add(goldBar);
		return barList.indexOf(goldBar);
	}
	
	@Override
	public void update(int senderID, String topic, int data) {
		if(topic.compareTo("consensus") == 0) {
			this.votedFor[senderID] = data;
			this.election();
		}
		else/*if(topic.compareTo("transaction") == 0)*/{
			this.barList.add(new GoldBar(topic));
		}
	}

	public void election() {
		if(this.countVote == list.size()) {
			int sum = 0;
			for(int i=0; i<this.votedFor.length; i++) {
				sum += this.votedFor[i] % 1024;
			}
			this.countVote = 0;
			buildBlock((long)sum);
		}
		else this.countVote++;
	}
	
	@Override
	public void vote(long timeStamp) {
		Random doRand = new Random();
		this.votedFor[myID] = doRand.nextInt() % 1024 + 1024;
		this.notifyAllAttendees("consensus", this.votedFor[myID]);
		election();
	}
	
	public String toString() {
		String chain = String.format("%s %h%s", "\n|[Blockchain Member ", this.myID,"]|\n");
		for(TinyBlock block : this.blockChain) {
			chain += block.toString();
		}
		return chain;
	}
	public TinyBlock getBlock(int index){
		TinyBlock block = blockChain.get(index);
		return block;
	}
	
}
