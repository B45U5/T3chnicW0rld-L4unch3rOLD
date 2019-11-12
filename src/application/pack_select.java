package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class pack_select extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("pack.fxml"));
        
        Scene scene = new Scene(root, 790, 440);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.setResizable(false);
        stage.setTitle("TechnicWorld | Paczki");
        stage.setScene(scene);
        stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	@FXML
	private Button skyb;
	@FXML
	private Button survi;
	 @FXML
	  private void skyb(ActionEvent event) throws IOException {
		 settings.setPack("skyblock");
		 settings.saveSettings();
		Stage primaryStage = new Stage();
		loginpanel guimain = new loginpanel();
		guimain.start(primaryStage);
		Stage stage = (Stage) skyb.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	  }
	 @FXML
	  private void survi(ActionEvent event) throws IOException {
		 settings.setPack("survival");
		 settings.saveSettings();
		Stage primaryStage = new Stage();
		loginpanel guimain = new loginpanel();
		guimain.start(primaryStage);
		Stage stage = (Stage) skyb.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	  }

}
