package javaFXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import security.Hash;
import security.KeyService;
import tinyBlackBox.Attendee;
import tinyBlackBox.TimeKeeper;
import tinyBlackBox.TinyBlock;
import tinyBlackBox.TinyChain;
import tinyBlackBox.Wallet;
import uicomponent.BlockNode;
import uicomponent.Locate;
import uicomponent.MerkleNode;
import uicomponent.TransactionNode;
import uicomponent.User;

public class SampleController implements Initializable{
  
	
	@FXML private TextField noncefield;
	@FXML private TextField priviousfield;
	@FXML private TextField merklerootfield;
	
	@FXML private AnchorPane mekleTreePane;
	@FXML private ScrollPane merkleScrollPane;
	@FXML private TitledPane blcokPane;

	@FXML private AnchorPane walletBlcokPane;
	private Stage primaryStage;
    private TinyBlock myBlock = new TinyBlock("MD5");
	private byte[] buffer = new byte[16];
	private Random doRand = new Random();
	
	/*------Transaction component---------*/
	@FXML AnchorPane transectionPane; 
	private double transactionXpoint =14;
	private double transactionYpoint =14;
	private int transactionNumber =0;
	private int walletNumber =0;
	
	ArrayList <TextField> transactionFieldList = new ArrayList<TextField>();
	ArrayList <String> transactionList = new ArrayList<String>();
	/*------MerkleTree component---------*/
	private double merkleTreeNodeXpoint =25;
	private double merkleTreeNodeYpoint =50;
	private ArrayList<MerkleNode> mekleNodeList;
	private ArrayList<BlockNode> blockChainNode = new ArrayList<BlockNode>();
	private ArrayList<BlockNode> walletChainNode = new ArrayList<BlockNode>();
	
	/*-----Wallet component-------*/
	@FXML AnchorPane walletPane1;
	@FXML AnchorPane walletPane2;
	@FXML AnchorPane walletPane3;
	private double wallet1Xpoint =14;
	private double wallet2Xpoint =14;
	private double wallet3Xpoint =14;
	private double wallet1Ypoint =14;
	private double wallet2Ypoint =14;
	private double wallet3Ypoint =14;
	
	TinyChain[] member;
	Wallet wallet1;
	Wallet wallet2;
	Wallet wallet3;
	
	ArrayList <TextField> allWalletFieldList = new ArrayList<TextField>();
	ArrayList<String> allWalletFieldWallet = new ArrayList<String>();
	/*------BlockChain component---------*/
	@FXML AnchorPane blockchainPane;
	private boolean aLive = true;
	ArrayList<Attendee> list = new ArrayList<Attendee>();
	@FXML private TitledPane blcokchainblockPane;

	@FXML private TextField blockchainblocknoncefield;
	@FXML private TextField blockchainblocpriviousfield;
	@FXML private TextField blockchainblocmerklerootfield;
	@FXML private TextArea blockchainblockTransactionArea;

	private ObservableList<User> userData = FXCollections.observableArrayList();
	@FXML private TableView<User> userTable;
	@FXML private TableColumn<User, String> userNameColumn;
	@FXML private TitledPane chainPane;
	@FXML private Label blockWalletLabel;
	private static int blockIndex =0;
	private double blockXpoint = 20;
	private double blockYpoint = 20;
	/*------Hash component------------*/
	@FXML private TextField dataField;
	
	@FXML private TextField hashField;
	/*-----Public/Private component---------*/
	@FXML private TextField publicEnField;
	@FXML private TextField publicDeField;
	@FXML private TextField privateEnField;
	@FXML private TextField privateDeField;
	@FXML private Label publicKeyLabel;
	@FXML private Label privateKeyLabel;
	
	@FXML private TextField publcEnDataField;
	@FXML private TextField publcDeDataField;
	@FXML private TextField privateEnDataField;
	@FXML private TextField privateDeDataField;
	
	private KeyService key ;
	/*-----Signature component---------*/
	@FXML private TextField dataEnField;
	@FXML private TextField signatureEnField;
	@FXML private TextField dataHashEnField;
	@FXML private TextField keydataField;
	@FXML private TextField signatureDeField;
	/*------Hashing component------------*/
	@FXML private TextField nonceField;
	@FXML private TextField hasingDataField;
	@FXML private TextField noncePlusDataField;
	@FXML private TextField hasinghashField;
	
	@FXML private TextField hash1Field;
	@FXML private TextField hash2Field;
	@FXML private TextField hashResultField;
	
	@FXML private TextField dataField2;
	@FXML private TextField hashField2;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		long timeStamp = System.currentTimeMillis();
		int numOfChain = 3;		
		member = new TinyChain[numOfChain];
		for(int i=0; i<numOfChain; i++) {
			member[i] = new TinyChain(i, "MD5", timeStamp, numOfChain);
		}
	
		for(int i=0; i<numOfChain; i++) {
			addAttendee(member[i]);
		}
		wallet1 = new Wallet(member[0]);
		wallet2 = new Wallet(member[1]);
		wallet3 = new Wallet(member[2]);
		
		userData.add(new User("Node1"));
		userData.add(new User("Node2"));
		userData.add(new User("Node3"));
		userNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
		userTable.setItems(userData);
		merkleScrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                	mekleTreePane.setScaleX(mekleTreePane.getScaleX()* 1.1);
                	mekleTreePane.setScaleY(mekleTreePane.getScaleY() * 1.1);
                } else if (event.getDeltaY() < 0) {
                	mekleTreePane.setScaleX(mekleTreePane.getScaleX() / 1.1);
                	mekleTreePane.setScaleY(mekleTreePane.getScaleY() / 1.1);
                }
            }
        });
		mekleTreePane.setOnMouseDragged(event->{
			mekleTreePane.setManaged(false);
			mekleTreePane.setTranslateX(event.getX() + mekleTreePane.getTranslateX());
			mekleTreePane.setTranslateY(event.getY() + mekleTreePane.getTranslateY());
			event.consume();
		});
		doRand.nextBytes(buffer);
		myBlock.setPreviousBlockHash(buffer);
		
	   blockchainPane.addEventHandler(MouseEvent.MOUSE_PRESSED,
	            new EventHandler<MouseEvent>(){

	   public void handle(MouseEvent e) {
	        // TODO Auto-generated method stub
		   System.out.println(e.getX()+","+e.getY());
		   for(int i=0; i<blockChainNode.size(); i++){
			   Locate startPoint = blockChainNode.get(i).getStartPoint();
			   Locate endPoint = blockChainNode.get(i).getEndPoint();
			   if((e.getX() >=startPoint.getX() && e.getY() >= startPoint.getY()) && (e.getX() <=endPoint.getX() && e.getY() <= endPoint.getY())){
				   String blockName = blockChainNode.get(i).getBlockNameProperty().get();
				   System.out.println("mouse point in the"+blockName);
				   setBlockInfo(blockChainNode.get(i));
				   blockWalletLabel.setText(blockName + " 's information");
				   break;
			   }
			   else{
				  
			   }
		   }
	    }});
	   nonceField.textProperty().addListener((observable, oldValue, newValue) -> {
		   
		    String data = hasingDataField.getText();
		    String nonce = newValue;
		    String predata = nonce+data;
		    noncePlusDataField.setText(predata);
		    String hash = generatHashfunction(predata);
		    hasinghashField.setText(hash);
		});
		
	   
	 
	}
	public void clickItem(MouseEvent event) {
		userTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	          String username = userTable.getSelectionModel().getSelectedItem().getUserName();
	          chainPane.setText("BlockChain - "+username);
	          blockWalletLabel.setText(username+"' Wallet");
	          if(username.equals("Node1")){
	        	  blockchainblockTransactionArea.setText(wallet1.toString());
	          }
	          else if(username.equals("Node2")){
	        	  blockchainblockTransactionArea.setText(wallet2.toString());
	          }
	          else if(username.equals("Node3")){
	        	  blockchainblockTransactionArea.setText(wallet3.toString());
              }
	        }
	    });
	}
	public void setStage(Stage stage){
		this.primaryStage = stage;
	}
	
	@FXML
	public void makehashTree(){
		mekleNodeList = new ArrayList<MerkleNode>();
		System.out.println("Start Making block");
		TextField tmpText;
		for(int i=0; i<transactionFieldList.size(); i++){
			tmpText = transactionFieldList.get(i);
			String tmpTranc = tmpText.getText();
			transactionList.add(tmpTranc);
			
		}
		String[] transactions = new String[transactionList.size()];
		int merkleCount = 2;
		int merkleLayer = 0;
		transactionList.toArray(transactions);
		
	    myBlock.setMessages(transactions);
		
		
		while(merkleCount<transactionList.size()){
			merkleCount = merkleCount*2;
		}
		merkleCount = merkleCount*2;
		int layerMerkle = merkleCount;
		buffer = new byte[merkleCount];  
		doRand.nextBytes(buffer);
		myBlock.setNonce(buffer);
		
		myBlock.buildBlock();
		for(int i=0; layerMerkle>=1; i++){
			
			 layerMerkle = layerMerkle/2;
		     
		     merkleLayer = i;
		     if(merkleCount !=0){
		    	 
		     }
		}
		byte[][] merkleTree = myBlock.getMerkleTree();
		int merkleSize = merkleCount;
		
		layerMerkle = merkleCount/2;
		merkleCount = merkleCount/2;
		int relationLayer = merkleLayer;
		
		double layerX = 0;
		
		double termofXpoint = 400;
		int termofXLayer = 1;
		for(int i=1; i<=merkleLayer; merkleLayer--){
	
			double currentMerkleYpoint = merkleTreeNodeYpoint+(125*(merkleLayer-1));
			double currentMerkleXpoint = merkleTreeNodeXpoint+layerX;
			for(int j=0; j<merkleCount; j++){
				MerkleNode mNode = new MerkleNode(currentMerkleXpoint,currentMerkleYpoint,myBlock.bytesToString(merkleTree[merkleCount+j]));
				mekleNodeList.add(mNode);
				
				merkleSize--;
				currentMerkleXpoint+=termofXpoint;
			}
		
			merkleTreeNodeXpoint +=(200*termofXLayer);
			merkleCount = merkleCount/2;
			termofXpoint +=(400*termofXLayer);
			termofXLayer= termofXLayer*2;
		 }
	

		
		int nodeCounter = 0;
		int upperNode = layerMerkle;
		for(int i=1; i<=relationLayer; relationLayer--){
			
				
				for(int j=0; j<layerMerkle; j++){
					if(layerMerkle ==1){
						System.out.println("root merklenode "+nodeCounter);
						MerkleNode mNode = mekleNodeList.get(nodeCounter);
						mNode.addToPane(mekleTreePane);
					}
					else{
						System.out.println("merklenode "+nodeCounter+" belong to upperNode "+upperNode);
						MerkleNode mNode = mekleNodeList.get(nodeCounter);
						mNode.hasRelation(mekleNodeList.get(upperNode));
						mNode.addToPane(mekleTreePane);
						nodeCounter++;
						if(j%2==1){
							System.out.println("");
							upperNode++;
						}
					}
					
					
				}
			    layerMerkle = layerMerkle/2;
			 }
		mekleTreePane.setPrefWidth(merkleTreeNodeXpoint+350);
		mekleTreePane.setPrefHeight(merkleTreeNodeYpoint+500);
		
		mekleTreePane.layout();
		merkleScrollPane.layout();
		merkleScrollPane.setVvalue(0.3);
		merkleScrollPane.setHvalue(0.4);
		
	
	
	    transactionFieldList.clear();
		initTransactionPosition();
	}
	@FXML
	public void rockToChain(){
		mekleNodeList = new ArrayList<MerkleNode>();
		System.out.println("Start Making block");
		TextField tmpText;
		
		for(int i=0; i<allWalletFieldList.size(); i++){
			String wallet = allWalletFieldWallet.get(i);
			tmpText = allWalletFieldList.get(i);
			String tmpTranc = tmpText.getText();
			if(wallet.equals("wallet1")){
				wallet1.addTransaction(tmpTranc);
			}
			else if(wallet.equals("wallet2")){
				wallet2.addTransaction(tmpTranc);
			}
			else if(wallet.equals("wallet3")){
				wallet3.addTransaction(tmpTranc);
			}
		}
		notifyAllAttendees();
		System.out.println(wallet1.toString());
		System.out.println("*-------first user's blockchain's first block");
		System.out.println(member[0].getBlock(0).toString());
		
		if(blockIndex==0){
			BlockNode bnode = new BlockNode("block"+blockIndex,member[0].getBlock(blockIndex),blockXpoint,80);
			BlockNode walletBnode = new BlockNode("block"+blockIndex,member[0].getBlock(blockIndex),185,blockYpoint);
			bnode.addToPane(blockchainPane);
			walletBnode.addToPane(walletBlcokPane);
			blockChainNode.add(bnode);
			walletChainNode.add(walletBnode);
		}
		else{
			BlockNode bnode = new BlockNode("block"+blockIndex,member[0].getBlock(blockIndex),blockXpoint,80);
			
			bnode.hasRelation(blockChainNode.get(blockIndex-1));
			bnode.addToPane(blockchainPane);
			
			BlockNode walletBnode = new BlockNode("block"+blockIndex,member[0].getBlock(blockIndex),185,blockYpoint);
			
			walletBnode.hasVerticalRelation(walletChainNode.get(blockIndex-1));
			walletBnode.addToPane(walletBlcokPane);
			blockChainNode.add(bnode);
			walletChainNode.add(walletBnode);
		}
		
		blockIndex++;
		blockXpoint+=180;
		blockYpoint+=120;
		walletNumber =0;
		walletPane1.getChildren().clear();
		walletPane2.getChildren().clear();
		walletPane3.getChildren().clear();
		
	
		initWalletPosition();
		
		allWalletFieldList.clear();
		allWalletFieldWallet.clear();
	}
	@FXML
	public void addTransactionToTransaction(){
		addTransaction(transectionPane,"transaction");
	}
	@FXML
	public void addTransactionToWallet1(){
		addTransaction(walletPane1,"wallet1");
	}
	@FXML
	public void addTransactionToWallet2(){
		addTransaction(walletPane2,"wallet2");
	}
	@FXML
	public void addTransactionToWallet3(){
		addTransaction(walletPane3,"wallet3");
	}
	/*MerkleTree block methods*/
	@FXML
	public void rockmakleTree(){
		byte[][]head = myBlock.getHead();
		byte[][]merkleTree = myBlock.getMerkleTree();
		
		merklerootfield.setText(myBlock.bytesToString(head[2]));	
	}
	@FXML
	public void clearMakleTree(){
		transectionPane.getChildren().clear();
		mekleTreePane.getChildren().clear();
	}
	@FXML
	public void getPreviousBlockHash(){
		byte[][]head = myBlock.getHead();
		byte[][]merkleTree = myBlock.getMerkleTree();
		priviousfield.setText(myBlock.bytesToString(head[1]));
	}
	@FXML
	public void getNonce(){
		byte[][]head = myBlock.getHead();
		byte[][]merkleTree = myBlock.getMerkleTree();
		noncefield.setText(myBlock.bytesToString(head[0]));
		
		blcokPane.setText("Block hash - "+ myBlock.bytesToString(merkleTree[0]));
	}
	/*BlockChain methods*/
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
	
	/*-----------hash methods-----------------------------*/
	@FXML
	private void generateHash(){
		String data = dataField.getText();
		data = generatHashfunction(data);
		hashField.setText(data);
	}
	@FXML
	private void generateHash2(){
		String data = dataField2.getText();
		data = generatHashfunction(data);
		hashField2.setText(data);
	}
	/*------------public/private key methods------------------*/
	@FXML
	private void generateKey(){
		   
		   key = new KeyService();
		   Hash hashfun = new Hash("MD5");
		   String pubLabel = hashfun.bytesToString(key.getPublicKey().getEncoded());
		   StringBuffer pubBuffer = new StringBuffer(pubLabel);
		   System.out.println(pubLabel);
		   for(int i =0; i<pubBuffer.length(); i++){
			   if(i%130 == 0){
				   pubBuffer.insert(i, "\n");
			   }
		   }
		   pubLabel = pubBuffer.toString();
		
		   String priLabel = hashfun.bytesToString(key.getPrivateKey().getEncoded());
		   System.out.println(priLabel);
		   StringBuffer priBuffer = new StringBuffer(priLabel);
		   for(int i =0; i<priBuffer.length(); i++){
			   if(i%130== 0){
				   priBuffer.insert(i, "\n");
			   }
		   }
		   priLabel = priBuffer.toString();
		   publicKeyLabel.setText("PublicKey: "+pubLabel);
		   privateKeyLabel.setText("PrivateKey: "+priLabel);
	}
	@FXML
	private void publicEncryption(){
		try{
			String hashData = publcEnDataField.getText();
			String publicKey= key.encryptTextToPublic(hashData);
			publicEnField.setText(publicKey);
		}
		catch(Exception e){
			encryptoAleart();
		}
	}
	@FXML
	private void publicDecryption(){
		try{
			String privateKey = publcDeDataField.getText();
			String hashData= key.decryptTextToPublic(privateKey);
			publicDeField.setText(hashData);
		}catch(Exception e){
			decryptionAleart();
		}
	}
	@FXML
	private void privateEncryption(){
		try{
			String hashData = privateEnDataField.getText();
			String privateKey= key.encryptTextToPrivate(hashData);
			privateEnField.setText(privateKey);
		}catch(Exception e){
			encryptoAleart();
		}
	}
	@FXML
	private void privateDecryption(){
		try{
			String publicKey = privateDeDataField.getText();
			String hashData= key.decryptTextToPrivate(publicKey);
			privateDeField.setText(hashData);
		}catch(Exception e){
			decryptionAleart();
		}
	}
	/*------------Signature methods------------------*/
	
	@FXML
	private void deAndHashing(){
		String privatePassword = dataEnField.getText();
		
		
		Hash hashfun = new Hash("MD5");
		byte[] hash = hashfun.hashFn(privatePassword);
		keydataField.setText(privatePassword);
		String hashString = hashfun.bytesToString(hash);
		dataHashEnField.setText(hashString);
	}
	@FXML
	private void signatureDe(){
		try{
		String signature = signatureEnField.getText();
		String deHash = key.decryptTextToPublic(signature);
		
		signatureDeField.setText(deHash);
		}catch(Exception e){
			decryptionAleart();
		}
	}
	/*------------Hashing methods------------------*/
	@FXML
	private void hashPlusHashGenerate(){
		String data = hash1Field.getText() + hash2Field.getText();
		data = generatHashfunction(data);
		hashResultField.setText(data);
	}
	@FXML
	private void setBlockInfo(BlockNode blockNode){
		TinyBlock block = blockNode.getBlock();
		byte[][] head = block.getHead();
		byte[][] merkleTree = block.getMerkleTree();
		String[] message = block.getMessages();
		String blockMessage="";
		blockchainblocknoncefield.setText(block.bytesToString(head[0]));
		blockchainblocpriviousfield.setText(block.bytesToString(head[1]));
		blockchainblocmerklerootfield.setText(block.bytesToString(head[2]));
		blcokchainblockPane.setText("Block hash - "+ block.bytesToString(merkleTree[0]));
		for(int i=0; i<message.length; i++) {
			blockMessage += (i + ": " + message[i] + "\n");
		}
		blockchainblockTransactionArea.setText(blockMessage);
	}
	private void addTransaction(AnchorPane tranPane, String paneName){
		
		if(paneName.equals("transaction")){
			TransactionNode tn = new TransactionNode("Transaction"+transactionNumber,transactionXpoint,transactionYpoint);
			tn.addToPane(tranPane);
			transactionFieldList.add(tn.getNode());
			
			if(transactionXpoint>200){
				transactionYpoint +=61;
				transactionXpoint =14;
			}
			else{
			    transactionXpoint +=200;
			    
			}
			transactionNumber++;
			tn.getNode().requestFocus();
		}
		else if(paneName.equals("wallet1")){
			TransactionNode tn = new TransactionNode("Transaction"+walletNumber,wallet1Xpoint,wallet1Ypoint);
			tn.addToPane(tranPane);
			//wallet1FieldList.add(tn.getNode());
			allWalletFieldList.add(tn.getNode());
			allWalletFieldWallet.add("wallet1");
			if(wallet1Xpoint>400){
				wallet1Ypoint +=61;
				wallet1Xpoint =14;
			}
			else{
				wallet1Xpoint +=200;
			    
			}
			walletNumber++;
			tn.getNode().requestFocus();
		}
		else if(paneName.equals("wallet2")){
			TransactionNode tn = new TransactionNode("Transaction"+walletNumber,wallet2Xpoint,wallet2Ypoint);
			tn.addToPane(tranPane);
			//wallet2FieldList.add(tn.getNode());
			allWalletFieldList.add(tn.getNode());
			allWalletFieldWallet.add("wallet2");
			if(wallet2Xpoint>400){
				wallet2Ypoint +=61;
				wallet2Xpoint =14;
			}
			else{
				wallet2Xpoint +=200;
			    
			}
			walletNumber++;
			tn.getNode().requestFocus();
		}
		else if(paneName.equals("wallet3")){
			TransactionNode tn = new TransactionNode("Transaction"+walletNumber,wallet3Xpoint,wallet3Ypoint);
			tn.addToPane(tranPane);
			//wallet3FieldList.add(tn.getNode());
			allWalletFieldList.add(tn.getNode());
			allWalletFieldWallet.add("wallet3");
			if(wallet3Xpoint>400){
				wallet3Ypoint +=61;
				wallet3Xpoint =14;
			}
			else{
				wallet3Xpoint +=200;
			    
			}
			walletNumber++;
			tn.getNode().requestFocus();
		}
		
		
	}
	private void initTransactionPosition(){
		transactionXpoint =14;
		transactionYpoint =14;
	}
	private void initWalletPosition(){

		wallet1Xpoint =14;
	    wallet2Xpoint =14;
		wallet3Xpoint =14;
		wallet1Ypoint =14;
		wallet2Ypoint =14;
		wallet3Ypoint =14;
	}
	private String generatHashfunction(String data){
		
		Hash hashfun = new Hash("MD5");
		byte[] hash = hashfun.hashFn(data);
		data= hashfun.bytesToString(hash);
		
		
		return data;
	}
	private void encryptoAleart(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Encryption Error!");
		alert.setHeaderText(null);
		alert.setContentText("Generate key first!");

		alert.showAndWait();
	}
	private void decryptionAleart(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Decryption Error");
		alert.setHeaderText(null);
		alert.setContentText("decrypt correct key!");

		alert.showAndWait();
	}
}
