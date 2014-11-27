package presenter;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MainPresenter implements Initializable {
	
	@FXML private Parent root;
	
	private ConnectPresenter connectPresenter;
	private ChannelPresenter channelPresenter;
	private NewTabPresenter newTabPresenter;
	
	public Parent getView() {
		return root;
	}
	
	public void setConnectPresenter(ConnectPresenter presenter)
	{
		connectPresenter = presenter;
	}
	
	public void setChannelPresenter(ChannelPresenter presenter)
	{
		channelPresenter = presenter;
	}
	
	public void setNewTabPresenter(NewTabPresenter presenter){
		newTabPresenter = presenter;
	}
	
	public void showNewTab()
	{
		BorderPane bp = (BorderPane) root;
		TabPane tp = (TabPane) bp.getCenter();
		
		Tab tab = newTabPresenter.getView();
		tp.getTabs().add(tab);
	}
	
	public void showConnect()
	{
		BorderPane bp = (BorderPane) root;
		TabPane tp = (TabPane) bp.getCenter();
		
		Tab tab = connectPresenter.getView();
		tp.getTabs().add(tab);
	}
	
	public void showChannel(String title)
	{
		BorderPane bp = (BorderPane) root;
		TabPane tp = (TabPane) bp.getCenter();
		tp.getTabs().remove(1);
		Tab tab = channelPresenter.getView();
		tab.setText(title);
		tp.getTabs().add(tab);
		tp.getSelectionModel().select(tab);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
	}
}
