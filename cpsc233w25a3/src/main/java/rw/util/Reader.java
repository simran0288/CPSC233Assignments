package rw.util;

import rw.battle.Battle;
import rw.battle.Entity;
import rw.battle.Maximal;
import rw.battle.PredaCon;
import rw.enums.Symbol;
import rw.enums.WeaponType;

import java.io.File;
import java.util.Scanner;

/**
 * This utility class assists with reading and parsing battle files for the game.
 * It loads a battle layout from a file and creates the appropriate entities for the battle grid.
 *
 * @author Simrandeep Kaur
 * @version 1.0
 * @email simrandeep.simrandee@ucalgary.ca
 * @tutorial T06
 * @date April 07, 2025
 */
public final class Reader {

    /**
     * Loads a battle configuration from a file and creates a Battle object.
     * <p>
     * This method reads the data from the provided file and uses it to create a Battle object,
     * placing entities (such as MAXIMALs, PREDACONs, and WALLs) at their specified positions on the grid.
     * The method assumes that the file is formatted in a specific way (first the dimensions of the grid,
     * followed by entity data in rows of comma-separated values).
     *
     * @param file The file containing the battle configuration.
     * @return A Battle object initialized with entities from the file, or null if the file is not found or an error occurs.
     */
    public static Battle loadBattle(File file) {
        try {
            Scanner input = new Scanner(file);

            // Read the dimensions of the battle grid (rows and columns)
            int rows = input.nextInt();
            int columns = input.nextInt();

            Battle battle = new Battle(rows, columns); // Initialize a new Battle object
            input.nextLine(); // Move to the next line after reading rows and columns

            // Loop through the file and process each line corresponding to an entity
            for (int i = 0; i < rows * columns && input.hasNext(); i++) {
                String wholeLine = input.nextLine(); // Read a line of input
                String[] elements = wholeLine.split(","); // Split the line into its components

                // Parse the row and column positions
                int row = Integer.parseInt(elements[0]);
                int column = Integer.parseInt(elements[1]);

                // Process the entity information, if present
                if (elements.length > 2) {
                    String entity = elements[2];

                    if (entity.equals("MAXIMAL")) {
                        // Parse MAXIMAL-specific data
                        char mSymbol = elements[3].charAt(0);
                        String mName = elements[4];
                        int mHealth = Integer.parseInt(elements[5]);
                        int mWeapon = Integer.parseInt(elements[6]);
                        int mArmor = Integer.parseInt(elements[7]);

                        // Only add the MAXIMAL if health is greater than 0
                        if (mHealth > 0) {
                            Maximal maximal = new Maximal(mSymbol, mName, mHealth, mWeapon, mArmor);
                            battle.addEntity(row, column, maximal);
                        } else if (mHealth == 0) {
                            // Add a dead entity in case of 0 health
                            battle.addEntity(row, column, new Entity(Symbol.DEAD.getSymbol()) {
                                @Override
                                public boolean canMoveOnTopOf() {
                                    return true; // A dead entity can be moved on top of
                                }

                                @Override
                                public boolean canBeAttacked() {
                                    return false; // A dead entity cannot be attacked
                                }
                            });
                        }
                    } else if (entity.equals("PREDACON")) {
                        // Parse PREDACON-specific data
                        char pSymbol = elements[3].charAt(0);
                        String pName = elements[4];
                        int pHealth = Integer.parseInt(elements[5]);
                        char pWeaponChar = elements[6].charAt(0);
                        WeaponType pWeapon;

                        // Determine the weapon type based on the character read
                        if (pWeaponChar == 'C') {
                            pWeapon = WeaponType.CLAWS;
                        } else if (pWeaponChar == 'T') {
                            pWeapon = WeaponType.TEETH;
                        } else
                            pWeapon = WeaponType.LASER;

                        // Only add the PREDACON if health is greater than 0
                        if (pHealth > 0) {
                            PredaCon p1 = new PredaCon(pSymbol, pName, pHealth, pWeapon);
                            battle.addEntity(row, column, p1); // Add PREDACON to the battle grid
                        } else if (pHealth == 0) {
                            // Add a dead entity in case of 0 health
                            battle.addEntity(row, column, new Entity(Symbol.DEAD.getSymbol()) {
                                @Override
                                public boolean canMoveOnTopOf() {
                                    return true; // A dead entity can be moved on top of
                                }

                                @Override
                                public boolean canBeAttacked() {
                                    return false; // A dead entity cannot be attacked
                                }
                            });
                        }
                    } else if (entity.equals("WALL")) {
                        // Add a WALL entity to the grid (impassable and indestructible)
                        battle.addEntity(row, column, new Entity(Symbol.WALL.getSymbol()) {
                            @Override
                            public boolean canMoveOnTopOf() {
                                return false; // A wall cannot be moved on top of
                            }

                            @Override
                            public boolean canBeAttacked() {
                                return false; // A wall cannot be attacked
                            }
                        });
                    }
                }
            }
            input.close(); // Close the scanner after reading the file
            return battle; // Return the initialized battle object

        } catch (Exception e) {
            return null; // Return null if an error occurs
        }
    }
}
