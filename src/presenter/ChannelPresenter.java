package presenter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.text.BadLocationException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;
import model.UserMessage;
import service.Service;

public class ChannelPresenter implements Initializable {

	private Service service;
	
	@FXML
	private Tab root;
	@FXML
	private TableView<UserMessage> tblMessages;
	@FXML
	private TableColumn<UserMessage, String> tbrUser;
	@FXML
	private TableColumn<UserMessage, String> tbrMessage;
	@FXML
	private ListView<User> lsUsers;
	@FXML
	private TextField txfMessage;

	private ObservableList<UserMessage> messages;
	private ObservableList<User> users;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbrUser.setCellValueFactory(new PropertyValueFactory<UserMessage, String>("from"));
		tbrUser.setText("");
		tbrMessage.setCellValueFactory(new PropertyValueFactory<UserMessage, String>("message"));
		tbrMessage.setText("");
		users = FXCollections.observableArrayList();
		lsUsers.setItems(users);
		messages = FXCollections.observableArrayList(); // create the data
		tblMessages.setItems(messages); // assign the data to the table
		
		tbrUser.prefWidthProperty().bind(tblMessages.widthProperty().divide(5));
		tbrMessage.prefWidthProperty().bind(tblMessages.widthProperty().divide(1.249)); // w * 1/4
		
		tbrUser.setId("cellUser");
	}

	public TableView<UserMessage> getTblMessages() {
		return tblMessages;
	}

	public void setTblMessages(TableView<UserMessage> tblMessages) {
		this.tblMessages = tblMessages;
	}

	public ListView<User> getLsUsers() {
		return lsUsers;
	}

	public void setLsUsers(ListView<User> lsUsers) {
		this.lsUsers = lsUsers;
	}

	public TableColumn<UserMessage, String> getTbrUser() {
		return tbrUser;
	}

	public void setTbrUser(TableColumn<UserMessage, String> tbrUser) {
		this.tbrUser = tbrUser;
	}

	public TableColumn<UserMessage, String> getTbrMessage() {
		return tbrMessage;
	}

	public void setTbrMessage(TableColumn<UserMessage, String> tbrMessage) {
		this.tbrMessage = tbrMessage;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Tab getView() {
		return root;
	}

	@FXML
	public void handleButtonAction() {
		// SimpleStringProperty s = new SimpleStringProperty();
		// s.set("Hej alle sammen!");
		// SimpleStringProperty ss = new SimpleStringProperty();
		// ss.set("Marcus");

		String s = "Hej alle sammen Hej alle sammenHej alle sammenHej alle sammenHej alle sammenHej \n alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammenHej alle sammen";
		String ss = "Marcus";
		// Message ms = new Message("", "", null, s, ss);
		// generateMessage(ms);
	}

	@FXML
	public void sendMessage(ActionEvent event) throws IOException, BadLocationException{
		service.sendPublicMessage(txfMessage.getText());
		UserMessage msg = new UserMessage();
		msg.setFrom("You");
		msg.setMessage(txfMessage.getText());
		addMessage(msg);
		txfMessage.setText("");
	}

	public void addUser(User user) {
		user.setNick(user.getNick());
		users.add(user);
	}
	
	public void removeUser(User user){
		for(User u : users){
			if(u.getNick().equalsIgnoreCase(user.getNick())){
				users.remove(u);
				break;
			}
		}
	}
	
	public void addMessage(UserMessage msg) {
		msg.setMessage(msg.getMessage());
		msg.setFrom(msg.getFrom());
		messages.add(msg);
	}

}
