package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presenter.MainPresenter;
import socket.Connection;

public class App extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AppFactory factory = new AppFactory();
			MainPresenter mainPresenter = factory.getMainPresenter();
			Scene scene = new Scene(mainPresenter.getView(), 800, 600);
			scene.getStylesheets().add("view/style.css");
			primaryStage.setScene(scene);
			primaryStage.show();
			mainPresenter.showNewTab();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
