package rw.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class for the Robot Wars World Editor application.
 *
 * <p>This is the entry point for the application. It sets up and launches the JavaFX
 * application, loading the FXML layout and displaying the main stage.</p>
 *
 * @author Simrandeep Kaur
 * @version 1.0
 * @email simrandeep.simrandee@ucalgary.ca
 * @tutorial T06
 * @date April 07, 2025
 */

public class Main extends Application {

    /**
     * The version of the application.
     */
    public static final String version = "1.0";

    /**
     * The main entry point of the JavaFX application.
     *
     * <p>This method launches the JavaFX application by calling the launch() method.</p>
     *
     * @param args Command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    /**
     * Sets up the main stage for the application.
     *
     * <p>This method loads the FXML file for the UI layout, creates a scene,
     * and displays it in the main application window.</p>
     *
     * @param stage The primary stage for the application
     * @throws IOException If there is an issue loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {

        // Load the FXML layout file for the main UI
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));

        // Create a scene with the loaded layout and set the dimensions
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("Robot Wars World Editor v1.0"); // Set the title
        stage.setScene(scene); // Set the scene for the primary stage
        stage.show(); // Display the stage
    }
}