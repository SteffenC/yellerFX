package app;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import presenter.ChannelPresenter;
import presenter.ConnectPresenter;
import presenter.MainPresenter;
import presenter.NewTabPresenter;
import service.Service;

public class AppFactory {

	private MainPresenter mainPresenter;
	private ConnectPresenter connectPresenter;
	private ChannelPresenter channelPresenter;
	private NewTabPresenter newTabPresenter;
	private Service service;

	public MainPresenter getMainPresenter() {
		if (mainPresenter == null) {
			try {

				FXMLLoader loader = new FXMLLoader();
				loader.load(getClass().getResourceAsStream("/view/Main.fxml"));
				// loader.load(getClass().getResourceAsStream("view/Main.fxml"));
				mainPresenter = (MainPresenter) loader.getController();
				mainPresenter.setConnectPresenter(getConnectPresenter());
				mainPresenter.setChannelPresenter(getChannelPresenter());
				mainPresenter.setNewTabPresenter(getNewTabPresenter());
			} catch (IOException e) {

				throw new RuntimeException("Unable to load Main.fxml", e);
			}

		}

		return mainPresenter;
	}

	public ConnectPresenter getConnectPresenter() {
		if (connectPresenter == null) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.load(getClass().getResourceAsStream("/view/Connect.fxml"));
				// loader.load(getClass().getResourceAsStream("view/Connect.fxml"));
				connectPresenter = (ConnectPresenter) loader.getController();
				connectPresenter.setMainPresenter(getMainPresenter());
			} catch (IOException e) {

				throw new RuntimeException("Unable to load Connect.fxml", e);
			}

		}

		return connectPresenter;
	}

	public ChannelPresenter getChannelPresenter() {
		if (channelPresenter == null) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.load(getClass().getResourceAsStream("/view/Channel.fxml"));
				// loader.load(getClass().getResourceAsStream("view/Channel.fxml"));
				channelPresenter = (ChannelPresenter) loader.getController();
				channelPresenter.setService(getService());

			} catch (IOException e) {

				throw new RuntimeException("Unable to load Channel.fxml", e);
			}

		}

		return channelPresenter;
	}
	
	public NewTabPresenter getNewTabPresenter() {
		if (newTabPresenter == null) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.load(getClass().getResourceAsStream("/view/NewTab.fxml"));
				// loader.load(getClass().getResourceAsStream("view/NewTab.fxml"));
				newTabPresenter = (NewTabPresenter) loader.getController();
				newTabPresenter.setMainPresenter(getMainPresenter());
			} catch (IOException e) {

				throw new RuntimeException("Unable to load NewTab.fxml", e);
			}

		}

		return newTabPresenter;
	}

	public void setService() {

	}

	public Service getService() {
		if (service == null) {
			service = new Service();
			service.setChannelPresenter(getChannelPresenter());
		}

		return service;
	}
}