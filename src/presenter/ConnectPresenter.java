package presenter;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

import javax.swing.text.BadLocationException;

import socket.Connection;

public class ConnectPresenter implements Initializable
{
	@FXML private Tab root;
	@FXML private Button joinButton;
	@FXML private TextField txfNick, txfChannel;
	@FXML private ComboBox<String> cmbServer;
	private MainPresenter mainPresenter;

	
	public ComboBox<String> getCmbServer() {
		return cmbServer;
	}

	public void setCmbServer(ComboBox<String> cmbServer) {
		this.cmbServer = cmbServer;
	}

	public TextField getTxfNick() {
		return txfNick;
	}

	public void setTxfNick(TextField txfNick) {
		this.txfNick = txfNick;
	}

	public TextField getTxfChannel() {
		return txfChannel;
	}

	public void setTxfChannel(TextField txfChannel) {
		this.txfChannel = txfChannel;
	}

	
	public void setMainPresenter(MainPresenter presenter)
	{
		mainPresenter = presenter;
	}
	
	public Tab getView()
	{
		return root;
	}
	
	@FXML
	private void join() throws UnknownHostException, IOException, BadLocationException {
		mainPresenter.showChannel(getTxfChannel().getText());
		String channel = getTxfChannel().getText();
		String nick = getTxfNick().getText();
		String server = getCmbServer().getValue() + "";
		Connection con = new Connection(channel, nick, server);
		new Thread(con).start();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	
	
}
