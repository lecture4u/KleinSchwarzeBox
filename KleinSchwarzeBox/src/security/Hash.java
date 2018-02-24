package security;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import java.util.Random;
public class Hash {
	private String hashAlgorithm;
	public Hash(String hashAlgorithm){
		this.hashAlgorithm = hashAlgorithm;
	}
	public String bytesToString(byte[] data) {
		
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < data.length; i++) {
			buffer.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
		}
		return buffer.toString();
	}
	public byte[] hashFn(String data) {
		byte[] buffer =  data.getBytes();
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
}
