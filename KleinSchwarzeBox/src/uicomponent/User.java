package uicomponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	private final StringProperty userName;
	
	public User(String userName) {
        this.userName = new SimpleStringProperty(userName);
       
    }
	public String getUserName() {
        return userName.get();
    }

    public void setUirstName(String userName) {
        this.userName.set(userName);
    }
    public StringProperty userNameProperty() {
        return userName;
    }


}
