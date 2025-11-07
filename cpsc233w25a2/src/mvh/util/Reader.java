package mvh.util;

import mvh.enums.WeaponType;
import mvh.world.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Utility class for reading and loading a world from a file.
 * The file format specifies the world dimensions followed by entity data.
 * This class parses the file and initializes a {@code World} object accordingly.
 *
 * @author Simrandeep Kaur
 * @email simrandeep.simrandee@ucalgary.ca
 * Tutorial T06
 * Date March 16, 2025
 * @version 1.1
 */
public final class Reader {

    /**
     * Reads a world file and loads it into a {@code World} object.
     *
     * @param fileWorld The file containing world data.
     * @return A new {@code World} instance based on the file data, or {@code null} if an error occurs.
     */
    public static World loadWorld(File fileWorld) {
        try {
            Scanner input = new Scanner(fileWorld);
            int rows = input.nextInt();
            int columns = input.nextInt();
            // Read world dimensions (rows and columns)
            World newWorld = new World(rows, columns);
            input.nextLine(); // Move to the next line
            for (int i = 0; i < rows*columns && input.hasNext(); i++) {
                String wholeLine = input.nextLine();
                String[]  elements = wholeLine.split(",");
                int row = Integer.parseInt(elements[0]); // Row number
                int column = Integer.parseInt(elements[1]); //Column number
                // If the line contains a hero or monster
                if (elements.length > 2) {
                    String entity = elements[2];
                    // If entity is a Monster
                    if (entity.equals("MONSTER")) {
                        char monsterSymbol = elements[3].charAt(0); // Monster symbol
                        int monsterHealth = Integer.parseInt(elements[4]); // Monster health
                        char monsterWeapon = elements[5].charAt(0); // Monster weapon type
                        // Create a Monster object
                        Monster monster1 = new Monster(monsterHealth, monsterSymbol, WeaponType.getWeaponType(monsterWeapon));
                        newWorld.addEntity(row, column, monster1); // Add to newWorld
                    }
                    // If entity is a Hero
                    else if (entity.equals("HERO")) {
                        char heroSymbol = elements[3].charAt(0); //Hero symbol
                        int heroHealth = Integer.parseInt(elements[4]); //Hero health
                        int heroWeaponStrength = Integer.parseInt(elements[5]);  // Hero weapon strength
                        int heraArmourStrength = Integer.parseInt(elements[6]); // Hero armor strength
                        // Create a Hero object
                        Hero hero1 = new Hero(heroHealth, heroSymbol, heroWeaponStrength, heraArmourStrength);
                        newWorld.addEntity(row, column, hero1); // Add to world
                    }
                }
            }
            input.close(); // Close the file scanner
            return newWorld; // Return the newWorld

        } catch (FileNotFoundException e) {
            return null; // Return null if an error occurs
        }
    }
}
