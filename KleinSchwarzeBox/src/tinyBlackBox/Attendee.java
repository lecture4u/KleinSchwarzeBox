package tinyBlackBox;

public interface Attendee {
	public void update(int senderID, String topic, int data);
	public void addAttendee(Attendee attendee);
	public void removeAttendee(Attendee attendee);
	public void notifyAllAttendees(String topic, int data);
	public void vote(long timeStamp);
}
