package archapplication;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
*
* The class {@code Main} is the main class of the application.
*
**/
public class Main extends Application {
	private static Stage stg;

	@Override
	public void start(Stage primaryStage) {
		ConnectSql cs = new ConnectSql();
		try {
			stg = primaryStage;
			primaryStage.setResizable(false);
			FXMLLoader loader= new FXMLLoader(getClass().getResource("LoginPage.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root,300,400);
			LoginPageController loginpagecontroller = loader.getController();
			loginpagecontroller.setModel(cs);
			loginpagecontroller.setOldStage(stg);
			primaryStage.setTitle("DAppointments");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
