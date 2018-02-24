package uicomponent;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.shape.Rectangle;

public class TransactionNode {
	private Label name;
	private TextField node;
	public TransactionNode(String LableName,double xPoint, double yPoint){
		node = new TextField();
        name = new Label();
        name.setLayoutX(xPoint);
        name.setLayoutY(yPoint);
        
        node.setLayoutX(xPoint);
        node.setLayoutY(yPoint+26);
        name.setText(LableName);
	}
	public void addToPane(AnchorPane pane){
	       pane.getChildren().add(node);
	       pane.getChildren().add(name);
	      
	}
	public TextField getNode(){
		return node;
	}
}
