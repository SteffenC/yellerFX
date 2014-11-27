package presenter;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;

public class NewTabPresenter implements Initializable {

	@FXML
	private Button btnNewTab;
	@FXML
	private Tab root;
	private MainPresenter mainPresenter;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// TODO Auto-generated method stub

	}

	public Tab getView() {
		return root;
	}

	public MainPresenter getMainPresenter() {
		return mainPresenter;
	}

	public void setMainPresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
	}

	public Button getBtnNewTab() {
		return btnNewTab;
	}

	public void setBtnNewTab(Button btnNewTab) {
		this.btnNewTab = btnNewTab;
	}
	
	@FXML
	public void handleNewTab(ActionEvent event){
		mainPresenter.showConnect();
	}
}
