package tinyBlackBox;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class TinyBlock {
	private String hashAlgorithm; // choice of hash algorithm: "hashAlg-256", "MD5"

	private byte[][] head = new byte[3][]; //Nonce, PreviousBlockHash, MerkleTreeRoot
	// body part
	private byte[][] merkleTree;
	private String[] messages;

	public TinyBlock(String algorithm) {
		hashAlgorithm = algorithm;

	}
	

	public byte[] hashFn(byte[] buffer) {
		byte[] byteData = null;
		
		try {
			MessageDigest message = MessageDigest.getInstance(hashAlgorithm);
			message.update(buffer);
			byteData = message.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	
		return byteData;
	}

	private byte[] combined(byte[] one, byte[] two) {
		byte[] result = new byte[one.length + two.length];
		for (int i = 0; i < one.length; i++) {
			result[i] = one[i];
		}
		for (int i = one.length; i < result.length; i++) {
			result[i] = two[i - one.length];
		}
		return result;
	}

	public byte[][] buildTree(String[] args) {
		int numNode = args.length;
		if(numNode > 1) {
			int treeHeight = (int)(Math.log((double)numNode)/Math.log(2.0));
			if(numNode > (int) Math.pow(2, treeHeight)) {
				treeHeight++;
				numNode = (int) Math.pow(2, treeHeight);
			}
			byte[][] mTree;
			mTree = new byte[2*numNode][];
			for(int i=0; i<args.length; i++) {
				mTree[numNode+i] = hashFn(hashFn(args[i].getBytes()));
			}
			
			for(int i=args.length; i<numNode; i++) {
				mTree[numNode+i] = hashFn(String.format("@10%d@30@", i).getBytes());
			}
			
			for(int level=treeHeight; level>0; level--) {
				int offset = (int) Math.pow(2, level);
				for(int index=0; index<offset; index+=2) {
					mTree[(offset+index)/2] = hashFn(combined(mTree[offset + index],mTree[offset + index + 1]));
				}
			}
			return mTree;
		}
		else {
			byte[][] mTree = {hashFn(args[0].getBytes()), hashFn(hashFn(args[0].getBytes()))};
			return mTree;
		}
	}

	public String bytesToString(byte[] data) {
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < data.length; i++) {
			buffer.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
		}
		return buffer.toString();
	}

	public void setPreviousBlockHash(byte[] cryptoHash) {
		head[1] = cryptoHash;
	}
	
	public void setMessages(String[] transactions) {
		messages = transactions;
	}
	
	public void setNonce(byte[] consensus) {
		head[0] = consensus;
	}
	
	public byte[] buildBlock() {
		merkleTree = buildTree(messages);
		head[2] = merkleTree[1];
		merkleTree[0] = hashFn(combined(head[0], combined(head[1], head[2])));
		return merkleTree[0];
	}
	
	public String toString() {
		String block = "\nBlock Hash: " + bytesToString(merkleTree[0]);
		block += "\nHead - Nonce(with consensus): ";
		block += bytesToString(head[0]);
		block += "\nHead -   Previous Block Hash: ";
		block += bytesToString(head[1]);
		block += "\nHead -      Merkel Tree Root: ";
		block += bytesToString(head[2]);
		block += "\n\nBody - Merkel Tree\n";
		for(int i=1; i<merkleTree.length; i++) {
			block += bytesToString(merkleTree[i]);
			block += "\n";
		}
		block += "\nBody - Contents\n";
		for(int i=0; i<messages.length; i++) {
			block += (i + ": " + messages[i] + "\n");
		}
		return block;
	}
	
	public static void main(String[] args) {
		String[] transactions = {"First transaction", "Second transaction",
									"Third transaction", "Forth transaction", "Fifth", "Six"};
		Random doRand = new Random();
		byte[] buffer = new byte[16];

		TinyBlock myBlock = new TinyBlock("MD5");
		doRand.nextBytes(buffer);
		myBlock.setPreviousBlockHash(buffer);
		
		myBlock.setMessages(transactions);
		
		buffer = new byte[16];
		doRand.nextBytes(buffer);
		myBlock.setNonce(buffer);
		
		myBlock.buildBlock();
		
		System.out.println(myBlock.toString());
	}
	
	public byte[][] getHead(){
		return head;
	}
	public byte[][] getMerkleTree(){
		return merkleTree;
	}
	public String[] getMessages(){
		return messages;
	}
}
