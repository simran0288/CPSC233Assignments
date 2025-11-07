# Battle Editor Application

This is a Battle Editor application where you can create, edit, load, and save a battle grid featuring two types of
entities: **PredaCons** and **Maximals**. The entities can be placed, moved, and managed within a grid, with various
properties like health, weapon strength, and armor strength. You can also interact with the grid by adding entities,
removing them, or placing walls.

---

## Features

- **Battle Grid Creation**: Create a grid with specified rows and columns.
- **Entity Addition**: Add **PredaCon** and **Maximal** entities to the grid.
- **Entity Removal**: Remove entities and place them on the grid using the context menu.
- **Wall Placement**: Place walls on the grid.
- **Entity Details**: Hover over grid elements to view detailed information about entities.
- **File Operations**: Load and save battles to and from text files.
- **Help Section**: View the about section for author and version details.

---

## Prerequisites

Ensure you have the following installed before running the application:

- **Java JDK** (Java 23 or later recommended)
- **JavaFX SDK** (for the GUI)

## Running the Application

### Running the JavaFX GUI in IntelliJ (or your preferred IDE)

To run the GUI in your IDE (e.g., IntelliJ IDEA), simply run the `Main.java` class located in the `rw.app` package as a
JavaFX application. If you're using IntelliJ, it should automatically recognize the JavaFX dependencies and execute the
application.

### Running Without an IDE (Using Command Line)

If you prefer to run the JavaFX application from the command line, follow these steps:

1. **Build your project** in your IDE (IntelliJ, Eclipse, etc.) to generate the class files.
2. Open a terminal (or command prompt) and navigate to the `target/classes` folder of your project.

   Example:
   cd /path/to/your/project/target/classes

3. Run the following command to execute the application:

**For Windows (make sure to update the path to your JavaFX SDK location):**
java --module-path "C:\Program Files\Java\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml rw.app.Main

Replace the module path with the location where you downloaded and extracted the JavaFX SDK (you can download it
from [here](https://gluonhq.com/products/javafx/)).

### Packaging the Project into a `.jar` File

Once you are done with your project, you should create a `.jar` file to package your application. This `.jar` file will
contain your compiled `.class` files and other resources. Here's how to do it:

1. **Create the `.jar` file** using your IDE (IntelliJ, etc.). You can typically do this by selecting "Build Artifacts"
   or a similar option.
2. Once the `.jar` file is created (e.g., `CPSC233W25A3.jar`), you can run it using the following command:

**For Windows (make sure to update the path to your JavaFX SDK location):**
java --module-path "C:\Program Files\Java\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml -jar
CPSC233W25A3.jar

### Important Notes

- Ensure that your environment is set up with JavaFX before attempting to run the program, especially if running from
  the command line.
- If you encounter issues, double-check the paths for your JavaFX SDK installation and ensure your project is built
  correctly.

---

## Features Explained

### 1. Battle Grid Creation

The grid can be created by entering the number of rows and columns in the text fields. It supports values between `1`
and `24` for both rows and columns. Upon entering valid values, the grid will be drawn in the `viewPane`.

### 2. Adding Entities

You can add two types of entities to the grid:

- **PredaCon**: These entities have a symbol, name, health, and weapon type (Teeth, Laser, or Claws).
- **Maximal**: These entities also have a symbol, name, health, weapon strength, and armor strength.

To add an entity:

- Enter the desired attributes (symbol, name, health, etc.) in the respective fields.
- Select the type of entity to add (PredaCon or Maximal).
- Left-Click on any empty grid cell to place the entity there.

### 3. Entity Removal

To remove an entity from the grid:

- Right-click on an entity in the grid, and it will be removed from that position.

### 4. Wall Placement

To place a wall:

- Middle-click on any empty grid cell, and a wall will be placed.

### 5. Entity Details

When you hover your mouse over a grid cell, the `details` text area will display the type of entity, its symbol, name,
health, weapon, and other relevant information.

### 6. File Operations

You can **load** a battle from a file or **save** the current battle grid to a file using the **File Chooser** dialog.
The supported file format is `.txt`.

- **Load Battle**: Open a `.txt` file to load an existing battle grid.
- **Save Battle**: Save the current battle state to a `.txt` file.

### 7. Help Section

You can view the "About" section with details about the author, version, and a brief description of the application.


---

## Project Structure

src/  
├── main/  
│ └── java/  
│ └──── rw/  
│ ├────── app/                
│ │ ├────── Main.java  
│ │ └────── MainController.java   
│ ├────── battle/             
│ │ ├────── Battle.java     
│ │ ├────── Entity.java     
│ │ ├────── Maximal.java    
│ │ ├────── PredaCon.java   
│ │ ├────── Robot.java      
│ │ └────── Wall.java       
│ ├─────── enums/              
│ │ ├────── Direction.java  
│ │ ├────── Symbol.java     
│ │ └────── WeaponType.java  
│ ├────── shell/              
│ │ ├────── Main.java       
│ │ └────── Menu.java       
│ └────── util/               
│ ├────── Logger.java   
│ ├────── Reader.java    
│ └────── Writer.java

## Explanation of Components:

1. **`Main.java` (in `rw/app/` and `rw/shell/`)**:
    - The entry point for the application and shell respectively.

2. **`MainController.java`**:
    - Controls the application logic and interacts with other components.

3. **`Battle.java`**:
    - Handles the main battle logic and manages entities.

4. **`Entity.java`**:
    - A base class for battle entities, including shared properties and methods.

5. **`Maximal.java`, `PredaCon.java`, `Robot.java`**:
    - Different entity types in the battle system. `Maximal` and `PredaCon` extend `Robot`.

6. **`Wall.java`**:
    - Represents the Wall entity, used in the battle grid.

7. **`Direction.java`, `Symbol.java`, `WeaponType.java`**:
    - Enumerations used to define movement directions, symbols for entities, and weapon types.

8. **`Menu.java`**:
    - Handles the shell-based user interface for the application.

9. **`Logger.java`, `Reader.java`, `Writer.java`**:
    - Utility classes for logging, reading, and writing files to handle battle data.

---

## Contact

For questions or feedback, feel free to contact the author:

- **Name**: Simrandeep Kaur
- **Email**: simrandeep.simrandee@ucalgary.ca
