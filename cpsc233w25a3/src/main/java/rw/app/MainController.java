package rw.app;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rw.battle.Battle;
import rw.battle.Entity;
import rw.battle.Maximal;
import rw.battle.PredaCon;
import rw.enums.Symbol;
import rw.enums.WeaponType;
import rw.util.Reader;
import rw.util.Writer;

import java.io.File;
import java.util.Optional;

/**
 * MainController is responsible for handling the user interface and interaction with the battle grid.
 * It manages the creation, modification, and loading of battlefields and entities (PredaCons and Maximals).
 *
 * @author Simrandeep Kaur
 * @version 1.0
 * @email simrandeep.simrandee@ucalgary.ca
 * @tutorial T06
 * @date April 07, 2025
 */
public class MainController {

    // Constant for invalid input value
    private final int INVALID = -1;
    @FXML
    public MenuItem Help;

    @FXML
    public GridPane gridPane;

    @FXML
    public TextField rows;

    @FXML
    public TextField columns;

    @FXML
    public AnchorPane viewPane;

    @FXML
    public Label viewLabel;
    @FXML
    String[] weapons = {"TEETH(4)", "LASER(3)", "CLAWS(2)"};
    @FXML
    ComboBox<String> comboBox;
    //Store the data of editor
    private Battle battle;
    @FXML
    private RadioButton maximal;
    @FXML
    private RadioButton predaCon;
    @FXML
    private TextField pSymbol;
    @FXML
    private TextField pName;
    @FXML
    private TextField pHealth;
    @FXML
    private TextField mSymbol;
    @FXML
    private TextField mName;
    @FXML
    private TextField mHealth;
    @FXML
    private TextField mWeapon;
    @FXML
    private TextField mArmor;
    @FXML
    private Label leftStatus;
    @FXML
    private Label rightStatus;
    @FXML
    private TextArea details;

    /**
     * Validates if a string can be parsed into an integer.
     *
     * @param input The string to be validated.
     * @return True if the string is numeric, otherwise false.
     */
    @FXML
    public static boolean isNumeric(String input) {
        try {
            // Attempt to parse the input as an integer
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            // Return false if input is not a valid integer
            return false;
        }
    }

    /**
     * Initializes the UI components and sets the default weapon in the combo box.
     */
    @FXML
    public void initialize() {
        comboBox.setItems(FXCollections.observableArrayList(weapons));
        comboBox.setValue(weapons[2]);
    }

    /**
     * Displays the battle grid based on user input for rows and columns.
     */
    @FXML
    public void showGrid() {

        // Check if both rows and columns are numeric
        if (isNumeric(rows.getText()) && isNumeric(columns.getText())) {

            // Parse the values for rows and columns
            int rowsTotal = Integer.parseInt(rows.getText());
            int columnsTotal = Integer.parseInt(columns.getText());

            // Check if rows and columns are within valid range (greater than 0 and less than 25)
            if (rowsTotal > 0 && rowsTotal < 25 && columnsTotal > 0 && columnsTotal < 25) {

                // Call makeGrid to create the grid with the parsed dimensions
                makeGrid(Integer.parseInt(rows.getText()), Integer.parseInt(columns.getText()));

                // Clear the existing view and add the newly created grid
                viewPane.getChildren().clear();
                viewPane.getChildren().addFirst(viewLabel);
                viewPane.getChildren().addLast(gridPane);

                // Update status messages to inform the user the grid was created
                rightStatus.setText("Battle Drawn");
                leftStatus.setText("Battle created successfully!");
                leftStatus.setTextFill(Color.GREEN);
            } else {
                // If rows or columns are out of range, show error messages
                leftStatus.setText("Rows and columns should be greater than 0 and less than 25");
                leftStatus.setTextFill(Color.RED);
                rightStatus.setText("Error drawing battle!");
            }
        }
        // Case where both rows and columns are not numeric
        else if (!isNumeric(rows.getText()) && !isNumeric(columns.getText())) {
            // Inform the user that neither rows nor columns can be parsed as integers
            leftStatus.setText("Can't parse " + rows.getText() + " and " + columns.getText() + " to integer.");
            leftStatus.setTextFill(Color.RED);
            rightStatus.setText("Error drawing battle!");
        }
        // Case where only rows can't be parsed to an integer
        else if (!isNumeric(rows.getText())) {
            // Inform the user that rows value cannot be parsed
            leftStatus.setText("Can't parse " + rows.getText() + " to integer.");
            leftStatus.setTextFill(Color.RED);
            rightStatus.setText("Error drawing battle!");
        }
        // Case where only columns can't be parsed to an integer
        else {
            // Inform the user that columns value cannot be parsed
            leftStatus.setText("Can't parse " + columns.getText() + " to integer.");
            leftStatus.setTextFill(Color.RED);
            rightStatus.setText("Error drawing battle!");
        }
    }

    /**
     * Creates a grid with the specified number of rows and columns, and sets up the grid UI.
     * Each cell in the grid can be interacted with to add or remove entities.
     *
     * @param rows    The number of rows in the grid.
     * @param columns The number of columns in the grid.
     */
    @FXML
    public void makeGrid(int rows, int columns) {
        battle = new Battle(rows, columns);
        gridPane = new GridPane(rows + 2, columns + 2); // Adding extra rows/columns for walls
        gridPane.setLayoutX(10);
        gridPane.setLayoutY(50);
        gridPane.setVgap(0);
        gridPane.setHgap(0);

        // Iterate over the grid and add TextField elements representing the cells
        for (int i = 0; i < rows + 2; i++) {
            for (int j = 0; j < columns + 2; j++) {
                TextField element = new TextField();
                element.setEditable(false);
                element.setPrefColumnCount(1);
                element.setOnContextMenuRequested(Event::consume); // Disable right-click context menu
                final int finalI = i;
                final int finalJ = j;

                // Set the mouse move behavior to show entity details in the TextArea
                element.setOnMouseMoved(_ -> {
                    if (!battle.invalid(finalI - 1, finalJ - 1)) {
                        Entity entity = battle.getEntity(finalI - 1, finalJ - 1);

                        if (entity == null) {
                            details.setText("Floor");
                        } else if (entity.getSymbol() == Symbol.WALL.getSymbol()) {
                            details.setText("Wall");
                        } else if (entity.getSymbol() == Symbol.DEAD.getSymbol()) {
                            details.setText("Dead Entity");
                        } else if (entity instanceof PredaCon predaCon1) {
                            String detail = "Type: PredaCon\nSymbol: " + predaCon1.getSymbol() + "\nName: " + predaCon1.getName() + "\nHealth: " + predaCon1.getHealth()
                                    + "\nWeapon: " + predaCon1.getWeaponType() + "\nWeapon Strength: " + predaCon1.weaponStrength();
                            details.setText(detail);
                        } else if (entity instanceof Maximal maximal) {
                            String detail = "Type: Maximal\nSymbol: " + maximal.getSymbol() + "\nName: " + maximal.getName() + "\nHealth: " + maximal.getHealth()
                                    + "\nWeapon Strength: " + maximal.weaponStrength() + "\nArmor Strength: " + maximal.armorStrength();
                            details.setText(detail);
                        }
                    } else
                        details.setText("Wall");

                });

                // Add walls around the grid
                if (i == 0 || i == rows + 1) {
                    element.setText(Character.toString(Symbol.WALL.getSymbol()));
                    gridPane.add(element, j, i);
                } else if (j == 0 || j == columns + 1) {
                    element.setText(Character.toString(Symbol.WALL.getSymbol()));
                    gridPane.add(element, j, i);
                } else {
                    element.setText(Character.toString(Symbol.FLOOR.getSymbol()));
                    gridPane.add(element, j, i);

                    // Set the mouse click behavior for adding/removing entities on the grid
                    element.setOnMouseClicked(e -> {
                        if (e.getButton() == MouseButton.PRIMARY) {
                            if (predaCon.isSelected()) {
                                if (makePredaCon() != null) {
                                    battle.addEntity(finalI - 1, finalJ - 1, makePredaCon());
                                    element.setText(getPSymbol());
                                    leftStatus.setText("PredaCon added successfully");
                                    leftStatus.setTextFill(Color.GREEN);
                                }
                            } else if (maximal.isSelected()) {
                                if (makeMaximal() != null) {
                                    battle.addEntity(finalI - 1, finalJ - 1, makeMaximal());
                                    element.setText(getMSymbol());
                                    leftStatus.setText("Maximal added successfully");
                                    leftStatus.setTextFill(Color.GREEN);
                                }
                            }
                        } else if (e.getButton() == MouseButton.SECONDARY) {
                            battle.addEntity(finalI - 1, finalJ - 1, null);
                            element.setText(Character.toString(Symbol.FLOOR.getSymbol()));
                            leftStatus.setText("Entity deleted");
                            leftStatus.setTextFill(Color.GREEN);
                        } else if (e.getButton() == MouseButton.MIDDLE) {
                            battle.addEntity(finalI - 1, finalJ - 1, new Entity(Symbol.WALL.getSymbol()) {
                                @Override
                                public boolean canMoveOnTopOf() {
                                    return false;
                                }

                                @Override
                                public boolean canBeAttacked() {
                                    return false;
                                }
                            });
                            element.setText(Character.toString(Symbol.WALL.getSymbol()));
                            leftStatus.setText("Wall added");
                            leftStatus.setTextFill(Color.GREEN);
                        }
                    });
                }
            }
        }
    }

    /**
     * Shows an informational alert with details about the application.
     */
    @FXML
    public void viewAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setContentText("""
                Author: Simrandeep Kaur
                Email: simrandeep.simrandee@ucalgary.ca
                Version: 1.0
                This is a Battle editor for PredaCons versus Maximals""");
        alert.show();
    }

    /**
     * Displays the status message when the user is adding a PredaCon entity.
     */
    @FXML
    public void addingPredaConLabel() {
        leftStatus.setText("Adding PredaCon");
        leftStatus.setTextFill(Color.BLACK);
    }

    /**
     * Displays the status message when the user is adding a Maximal entity.
     */
    @FXML
    public void addingMaximalLabel() {
        leftStatus.setText("Adding Maximal");
        leftStatus.setTextFill(Color.BLACK);

    }

    /**
     * Creates a new PredaCon object based on user input.
     * Validates the user input and returns a new PredaCon if the input is valid.
     * Otherwise, it shows an error message for the invalid field.
     *
     * @return A PredaCon object or null if the input is invalid.
     */
    @FXML
    public PredaCon makePredaCon() {
        if (getPSymbol() != null && getPName() != null && getPHealth() != INVALID) {
            return new PredaCon(getPSymbol().charAt(0), getPName(), getPHealth(), getPWeapon());
        } else {
            // Display appropriate error message for invalid fields
            if (getPSymbol() == null) {
                leftStatus.setText("Invalid Symbol. Enter a letter for PredaCon Symbol");
                leftStatus.setTextFill(Color.RED);
            } else if (getPName() == null) {
                leftStatus.setText("PredaCon name can't be empty or contain any numbers/symbols.");
                leftStatus.setTextFill(Color.RED);
            } else if (getPHealth() == INVALID) {
                leftStatus.setText("PredaCon health should be between 1 and 10 inclusive.");
                leftStatus.setTextFill(Color.RED);
            }
            return null;
        }
    }

    /**
     * Creates a new Maximal object based on user input.
     * Validates the user input and returns a new Maximal if the input is valid.
     * Otherwise, it shows an error message for the invalid field.
     *
     * @return A Maximal object or null if the input is invalid.
     */
    @FXML
    public Maximal makeMaximal() {
        if (getMSymbol() != null && getMName() != null && getMHealth() != INVALID && getMWeapon() != INVALID && getMArmor() != INVALID) {
            return new Maximal(getMSymbol().charAt(0), getMName(), getMHealth(), getMWeapon(), getMArmor());
        } else {
            // Display appropriate error message for invalid fields
            if (getMSymbol() == null) {
                leftStatus.setText("Invalid Symbol. Enter a letter for Maximal Symbol");
                leftStatus.setTextFill(Color.RED);
            } else if (getMName() == null) {
                leftStatus.setText("Maximal name can't be empty or contain any numbers/symbols.");
                leftStatus.setTextFill(Color.RED);
            } else if (getMHealth() == INVALID) {
                leftStatus.setText("Maximal health should be between 1 and 10 inclusive.");
                leftStatus.setTextFill(Color.RED);
            } else if (getMWeapon() == INVALID) {
                leftStatus.setText("Maximal weapon strength should be between 0 and 10 inclusive.");
                leftStatus.setTextFill(Color.RED);
            } else if (getMArmor() == INVALID) {
                leftStatus.setText("Maximal armor strength should be between 0 and 10 inclusive.");
                leftStatus.setTextFill(Color.RED);
            }
            return null;
        }

    }

    /**
     * Retrieves the PredaCon symbol from the input field if valid.
     * A valid symbol is a single alphabetic character.
     *
     * @return The PredaCon symbol or null if the input is invalid.
     */
    @FXML
    public String getPSymbol() {
        if (pSymbol.getText().length() == 1 && Character.isAlphabetic(pSymbol.getText().charAt(0))) {
            return pSymbol.getText();
        } else {
            return null;
        }
    }

    /**
     * Retrieves the PredaCon name from the input field if valid.
     * The name must not be empty and must only contain alphabetic characters and spaces.
     *
     * @return The PredaCon name or null if the input is invalid.
     */
    @FXML
    public String getPName() {
        if (!pName.getText().isEmpty() && pName.getText().matches("[A-Za-z ]+")) {
            return pName.getText();
        } else {
            return null;
        }
    }

    /**
     * Retrieves the PredaCon health from the input field if valid.
     * The health must be a number between 1 and 10 inclusive.
     *
     * @return The PredaCon health or INVALID if the input is invalid.
     */
    @FXML
    public int getPHealth() {
        if (!isNumeric(pHealth.getText())) {
            return INVALID;
        } else {
            int health = Integer.parseInt(pHealth.getText());
            if (health > 0 && health <= 10) {
                return health;
            } else {
                return INVALID;
            }
        }
    }

    /**
     * Retrieves the PredaCon weapon type based on the user's selection in the combo box.
     *
     * @return The weapon type for the PredaCon.
     */
    @FXML
    public WeaponType getPWeapon() {
        if (comboBox.getValue().equals(weapons[2])) {
            return WeaponType.CLAWS;
        } else if (comboBox.getValue().equals(weapons[1])) {
            comboBox.setValue(weapons[1]);
            return WeaponType.LASER;
        }
        comboBox.setValue(weapons[0]);
        return WeaponType.TEETH;
    }

    /**
     * Retrieves the Maximal symbol from the input field if valid.
     * A valid symbol is a single alphabetic character.
     *
     * @return The Maximal symbol or null if the input is invalid.
     */
    @FXML
    public String getMSymbol() {
        if (mSymbol.getText().length() == 1 && Character.isAlphabetic(mSymbol.getText().charAt(0))) {
            return mSymbol.getText();
        } else {
            return null;
        }
    }

    /**
     * Retrieves the Maximal name from the input field if valid.
     * The name must not be empty and must only contain alphabetic characters and spaces.
     *
     * @return The Maximal name or null if the input is invalid.
     */
    @FXML
    public String getMName() {
        if (!mName.getText().isEmpty() && mName.getText().matches("[A-Za-z ]+")) {
            return mName.getText();
        } else {
            return null;
        }
    }

    /**
     * Retrieves the Maximal health from the input field if valid.
     * The health must be a number between 1 and 10 inclusive.
     *
     * @return The Maximal health or INVALID if the input is invalid.
     */
    @FXML
    public int getMHealth() {
        if (!isNumeric(mHealth.getText())) {
            return INVALID;
        } else {
            int health = Integer.parseInt(mHealth.getText());
            if (health > 0 && health <= 10) {
                return health;
            } else {
                return INVALID;
            }
        }
    }

    /**
     * Retrieves the Maximal weapon strength from the input field if valid.
     * The weapon strength must be a number between 0 and 10 inclusive.
     *
     * @return The Maximal weapon strength or INVALID if the input is invalid.
     */
    @FXML
    public int getMWeapon() {
        if (!isNumeric(mWeapon.getText())) {
            return INVALID;
        } else {
            int weaponStrength = Integer.parseInt(mWeapon.getText());
            if (weaponStrength >= 0 && weaponStrength <= 10) {
                return weaponStrength;
            } else {
                return INVALID;
            }
        }
    }

    /**
     * Retrieves the Maximal armor strength from the input field if valid.
     * The armor strength must be a number between 0 and 10 inclusive.
     *
     * @return The Maximal armor strength or INVALID if the input is invalid.
     */
    @FXML
    public int getMArmor() {
        if (!isNumeric(mArmor.getText())) {
            return INVALID;
        } else {
            int armorStrength = Integer.parseInt(mArmor.getText());
            if (armorStrength >= 0 && armorStrength <= 10) {
                return armorStrength;
            } else {
                return INVALID;
            }
        }
    }

    /**
     * Loads a saved battle from a file.
     * Displays an appropriate message indicating the success or failure of loading the battle.
     */
    @FXML
    public void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Find file to load from");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(new Stage());
        battle = Reader.loadBattle(file);
        if (battle != null) {
            loadGrid(battle);
            viewPane.getChildren().clear();
            viewPane.getChildren().addFirst(viewLabel);
            viewPane.getChildren().addLast(gridPane);
            rightStatus.setText("Battle Drawn");
            rightStatus.setTextFill(Color.GREEN);
            leftStatus.setText("Battle Loaded successfully!");
            leftStatus.setTextFill(Color.GREEN);
        } else {
            rightStatus.setText("Unable to draw battle");
            rightStatus.setTextFill(Color.RED);
            leftStatus.setText("Error loading battle!");
            leftStatus.setTextFill(Color.RED);
        }
    }

    /**
     * Loads and displays the grid representing the current state of the battle.
     * Each cell of the grid may contain an entity (PredaCon, Maximal, or Wall).
     *
     * @param battle The battle whose grid will be loaded.
     */
    @FXML
    public void loadGrid(Battle battle) {

        // Loop through the rows and columns of the battle grid to create editable cells
        int rows = battle.getRows();
        int columns = battle.getColumns();
        gridPane = new GridPane(rows + 2, columns + 2);
        gridPane.setLayoutX(10);
        gridPane.setLayoutY(50);
        gridPane.setVgap(0);
        gridPane.setHgap(0);

        // Add cells to the grid and set up event listeners for mouse interaction
        for (int i = 0; i < rows + 2; i++) {
            for (int j = 0; j < columns + 2; j++) {
                TextField element = new TextField();
                element.setEditable(false);
                element.setPrefColumnCount(1);
                element.setOnContextMenuRequested(Event::consume);
                final int finalI = i;
                final int finalJ = j;

                // Mouse hover event to show entity details
                element.setOnMouseMoved(_ -> {
                    if (!battle.invalid(finalI - 1, finalJ - 1)) {
                        Entity entity = battle.getEntity(finalI - 1, finalJ - 1);

                        if (entity == null) {
                            details.setText("Floor");
                        } else if (entity.getSymbol() == Symbol.WALL.getSymbol()) {
                            details.setText("Wall");
                        } else if (entity.getSymbol() == Symbol.DEAD.getSymbol()) {
                            details.setText("Dead Entity");
                        } else if (entity instanceof PredaCon predaCon1) {
                            String detail = "Type: PredaCon\nSymbol: " + predaCon1.getSymbol() + "\nName: " + predaCon1.getName() + "\nHealth: " + predaCon1.getHealth()
                                    + "\nWeapon: " + predaCon1.getWeaponType() + "\nWeapon Strength: " + predaCon1.weaponStrength();
                            details.setText(detail);
                        } else if (entity instanceof Maximal maximal) {
                            String detail = "Type: Maximal\nSymbol: " + maximal.getSymbol() + "\nName: " + maximal.getName() + "\nHealth: " + maximal.getHealth()
                                    + "\nWeapon Strength: " + maximal.weaponStrength() + "\nArmor Strength: " + maximal.armorStrength();
                            details.setText(detail);
                        }
                    } else
                        details.setText("Wall");
                });

                // Set up grid boundaries and entity placements
                if (i == 0 || i == rows + 1) {
                    element.setText(Character.toString(Symbol.WALL.getSymbol()));
                    gridPane.add(element, j, i);
                } else if (j == 0 || j == columns + 1) {
                    element.setText(Character.toString(Symbol.WALL.getSymbol()));
                    gridPane.add(element, j, i);
                } else {
                    // If an entity exists, display its symbol in the cell
                    Entity entity = battle.getEntity(i - 1, j - 1);
                    if (entity != null) {
                        element.setText(Character.toString(entity.getSymbol()));
                    } else {
                        element.setText(Character.toString(Symbol.FLOOR.getSymbol()));
                    }
                    gridPane.add(element, j, i);

                    // Mouse click event to add or remove entities from the grid
                    element.setOnMouseClicked(e -> {
                        if (e.getButton() == MouseButton.PRIMARY) {
                            if (predaCon.isSelected()) {
                                if (makePredaCon() != null) {
                                    battle.addEntity(finalI - 1, finalJ - 1, makePredaCon());
                                    element.setText(getPSymbol());
                                    leftStatus.setText("PredaCon added successfully");
                                    leftStatus.setTextFill(Color.GREEN);
                                }
                            } else if (maximal.isSelected()) {
                                if (makeMaximal() != null) {
                                    battle.addEntity(finalI - 1, finalJ - 1, makeMaximal());
                                    element.setText(getMSymbol());
                                    leftStatus.setText("Maximal added successfully");
                                    leftStatus.setTextFill(Color.GREEN);
                                }
                            }
                        } else if (e.getButton() == MouseButton.SECONDARY) {
                            battle.addEntity(finalI - 1, finalJ - 1, null);
                            element.setText(Character.toString(Symbol.FLOOR.getSymbol()));
                            leftStatus.setText("Entity deleted");
                            leftStatus.setTextFill(Color.GREEN);
                        } else if (e.getButton() == MouseButton.MIDDLE) {
                            battle.addEntity(finalI - 1, finalJ - 1, new Entity(Symbol.WALL.getSymbol()) {
                                @Override
                                public boolean canMoveOnTopOf() {
                                    return false;
                                }

                                @Override
                                public boolean canBeAttacked() {
                                    return false;
                                }
                            });
                            element.setText(Character.toString(Symbol.WALL.getSymbol()));
                            leftStatus.setText("Wall added");
                            leftStatus.setTextFill(Color.GREEN);
                        }
                    });
                }
            }
        }
    }

    /**
     * Quits the application.
     */
    @FXML
    public void quit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        // Check if the user pressed "OK"
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0); // Exit the application
        }
    }


    /**
     * Saves the current battle to a file.
     * Displays an appropriate message indicating the success or failure of saving the battle.
     */
    @FXML
    public void saveBattle() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Find file to save to");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setInitialFileName("battleField.txt");
        File file = fileChooser.showSaveDialog(new Stage());
        boolean success = Writer.saveBattle(battle, file);
        if (success) {
            leftStatus.setText("Battle saved!");
            leftStatus.setTextFill(Color.GREEN);
            rightStatus.setText("Battle saved successfully");
            rightStatus.setTextFill(Color.GREEN);
        } else {
            leftStatus.setText("Failed to save battle!");
            leftStatus.setTextFill(Color.RED);
            rightStatus.setText("Error saving battle");
            rightStatus.setTextFill(Color.RED);
        }
    }
}

