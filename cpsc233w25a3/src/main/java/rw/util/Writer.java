package rw.util;

import rw.battle.Battle;
import rw.battle.Entity;
import rw.battle.Maximal;
import rw.battle.PredaCon;
import rw.enums.Symbol;
import rw.enums.WeaponType;

import java.io.File;
import java.io.PrintWriter;

/**
 * Utility class to assist with saving battle configurations to a file.
 * <p>This class provides methods for writing battle grid data and entities to a file,
 * allowing the game's battle state to be persisted and later loaded.</p>
 *
 * @author Simrandeep Kaur
 * @version 1.0
 * @email simrandeep.simrandee@ucalgary.ca
 * @tutorial T06
 * @date April 07, 2025
 */

public final class Writer {

    /**
     * Saves the current battle state to a file.
     * This method writes the grid dimensions and each entity's details (such as type, health, and weapon)
     * to the specified file.
     *
     * @param battle The Battle object containing the grid and entities to be saved.
     * @param file   The file to which the battle data will be saved.
     * @return true if the battle was successfully saved, false if an error occurred.
     */
    public static boolean saveBattle(Battle battle, File file) {
        try {
            // Create a PrintWriter object to write to the specified file
            PrintWriter writer = new PrintWriter(file);

            // Write the dimensions of the battle grid (rows and columns)
            int rows = battle.getRows();
            int columns = battle.getColumns();
            writer.write(rows + "\n");
            writer.write(columns + "\n");

            // Loop through each position in the grid and check if an entity is present
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    writer.write(i + "," + j); // Write the current grid coordinates

                    // Retrieve the entity at the current grid position
                    Entity entity = battle.getEntity(i, j);

                    // Check if the entity is a WALL and write its information
                    if (entity != null) {
                        if (entity.getSymbol() == Symbol.WALL.getSymbol()) {
                            writer.write(",WALL");
                        }
                        // Check if the entity is a PredaCon and write its information
                        else if (entity instanceof PredaCon) {
                            char weaponChar;
                            WeaponType weaponType = ((PredaCon) entity).getWeaponType();

                            // Map the weapon type to a corresponding character
                            if (weaponType == WeaponType.CLAWS) {
                                weaponChar = 'C';
                            } else if (weaponType == WeaponType.LASER) {
                                weaponChar = 'L';
                            } else
                                weaponChar = 'T';

                            // Write the entity details for a PredaCon
                            writer.write(",PREDACON," + entity.getSymbol() + "," + ((PredaCon) entity).getName() +
                                    "," + ((PredaCon) entity).getHealth() + "," + weaponChar);
                        }
                        // Check if the entity is a Maximal and write its information
                        else if (entity instanceof Maximal) {
                            writer.write(",MAXIMAL," + entity.getSymbol() + "," + ((Maximal) entity).getName() +
                                    "," + ((Maximal) entity).getHealth() + "," + ((Maximal) entity).weaponStrength()
                                    + "," + ((Maximal) entity).armorStrength());
                        }
                    }
                    writer.write("\n"); // Write a newline after each entity's data
                }
            }
            writer.close(); // Close the writer after finishing
            return true; // Return true to indicate success
        } catch (Exception e) {
            return false; // Return false if there is an error opening the file
        }
    }
}
