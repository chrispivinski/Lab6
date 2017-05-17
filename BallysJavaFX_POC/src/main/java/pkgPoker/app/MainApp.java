package pkgPoker.app;

import java.io.IOException;


import java.io.Serializable;
import java.io.StringWriter;
import java.util.function.Consumer;

import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pkgPoker.app.controller.PokerTableController;
import pkgPoker.app.controller.RootLayoutController;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	private PokerTableController pokerController = null;
	private RootLayoutController rootController = null;

	private double dRootLayoutHeight;
	
	private boolean isServer = false;

	public static void main(String[] args) {
		launch(args);
	}

	public double getdRootLayoutHeight() {
		return dRootLayoutHeight;
	}

	@Override
	public void init() throws Exception {
		// INIT is executed by the Application framework FIRST
		//connection.startConnection();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Poker");
		
		initRootLayout();

		showPokerTable();	
		
		
/*		// START is executed by the Application framework after INIT
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 500, 500);



		// Set the application icon.
		//this.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/26.png")));

		this.primaryStage.setScene(scene);
		this.primaryStage.show();

		showClientServer();*/
	}



	public void initRootLayout() {
		try {
					
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/poker/app/view/RootLayout.fxml"));
			
			rootLayout = (BorderPane) loader.load();			
			rootLayout.setMaxWidth(600);
			
			dRootLayoutHeight = rootLayout.getMaxHeight();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);	
			
			
			
			scene.getStylesheets().add("include/css/DarkTheme.css");				
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			// RootLayoutController controller = loader.getController();
			rootController = loader.getController();

			rootController.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showPokerTable() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/poker/app/view/PokerTable.fxml"));
			
			
			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();
/*
			primaryStage.setX(bounds.getMinX());
			primaryStage.setY(bounds.getMinY());
			primaryStage.setWidth(bounds.getWidth() / 3);
			primaryStage.setHeight(bounds.getHeight());*/
			
			
			AnchorPane pokerOverview = (AnchorPane) loader.load();

			pokerOverview.setMaxWidth(bounds.getWidth());
			pokerOverview.setMaxHeight(bounds.getHeight());					
			
			// Set person overview into the center of root layout.
			rootLayout.setCenter(pokerOverview);

			// Give the controller access to the main app.
			//PokerTableController controller = loader.getController();
			pokerController = loader.getController();
			pokerController.setMainApp(this);
			
 

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void EndPoker() {
		
		System.out.println("Player quit");
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	@Override
	public void stop() throws Exception {
		System.out.println("Stopped");
	}

	
	public String getRuleName()
	{
		return rootController.getRuleName();
	}
	
}