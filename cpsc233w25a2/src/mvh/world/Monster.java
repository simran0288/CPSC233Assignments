package mvh.world;

import mvh.enums.Direction;
import mvh.enums.WeaponType;

/**
 * The Monster class represents an enemy entity in the game.
 * Each Monster has a fixed armor strength and a user-specified weapon type.
 * Monsters can move, attack, and interact with the world.
 *
 * @author Simrandeep Kaur
 * @email simrandeep.simrandee@ucalgary.ca
 * Tutorial T06
 * Date March 16, 2025
 * @version 1.1
 */
public final class Monster extends Entity {

    /**
     * The set armor strength of a Monster
     */
    private static final int MONSTER_ARMOR_STRENGTH = 2;

    /**
     * The user provided weapon type
     */
    private final WeaponType weaponType;

    /**
     * A Monster has regular health and symbol as well as a weapon type
     *
     * @param health     Health of Monster
     * @param symbol     Symbol for map to show Monster
     * @param weaponType The weapon type of the Monster
     */
    public Monster(int health, char symbol, WeaponType weaponType) {
        super(symbol, health);
        this.weaponType = weaponType;
    }

    /**
     * Gets Monster's weapon type
     * @return The Monster's weapon type
     */
    public WeaponType getWeaponType(){
        return this.weaponType;
    }

    /**
     * The weapon strength of monster is from their weapon type
     * @return The weapon strength of monster is from their weapon type
     */
    @Override
    public int weaponStrength() {
        return weaponType.getWeaponStrength();
    }

    /**
     * The armor strength of monster is from the stored constant
     * @return The armor strength of monster is from the stored constant
     */
    @Override
    public int armorStrength() {
        return MONSTER_ARMOR_STRENGTH;
    }


    /**
     * Determines the direction in which the Monster should move.
     * The Monster moves towards the nearest alive Hero if possible.
     * If no Hero is found, the Monster moves southeast or randomly.
     *
     * @param local The current local 5x5 world centred at monster
     * @return The direction in which the Monster will move
     */
    @Override
    public Direction chooseMove(World local) {
        int monsterRow = (local.getRows()-1)/2;
        int monsterColumn = monsterRow;
        int size = local.getRows();
        // Search for the nearest alive Hero in the world (starting from bottom-right)
        for (int i = size - 1; i >= 0; i--){
            if (i == 0 || i == size -1) {
                for (int j = size - 1; j >= 0; j--) {
                    if (local.isHero(i, j) && local.getEntity(i, j).isAlive()) {
                        // Find the best direction to move toward the Hero
                        for (Direction move : Direction.getDirections(i - monsterRow, j - monsterColumn)) {
                            int rowToMoveOn = move.getRowChange() + monsterRow;
                            int columnToMoveOn = move.getColumnChange() + monsterColumn;
                            if (local.canMoveOnTopOf(rowToMoveOn, columnToMoveOn)) {
                                return move; // return direction of move towards the Hero
                            }
                        }
                    }
                }
            }
            else {
                for (int j = size - 1; j >= 0; j -= 4) {
                    if (local.isHero(i, j) && local.getEntity(i, j).isAlive()) {
                        // Find the best direction to move toward the Hero
                        for (Direction move : Direction.getDirections(i - monsterRow, j - monsterColumn)) {
                            int rowToMoveOn = move.getRowChange() + monsterRow;
                            int columnToMoveOn = move.getColumnChange() + monsterColumn;
                            if (local.canMoveOnTopOf(rowToMoveOn, columnToMoveOn)) {
                                return move; // return direction of move towards the Hero
                            }
                        }
                    }
                }
            }
        }
        // If no Hero is found, move southeast if possible
        Direction move = Direction.SOUTHEAST;
        int rowToMoveOn = monsterRow + move.getRowChange();
        int columnToMoveOn = monsterColumn + move.getColumnChange();
        if (local.canMoveOnTopOf(rowToMoveOn, columnToMoveOn) )
            return move;
        else {
            // If southeast is blocked, move in a random direction if possible
            move = Direction.getRandomDirection();
            rowToMoveOn = monsterRow + move.getRowChange();
            columnToMoveOn = monsterColumn + move.getColumnChange();
            if (local.canMoveOnTopOf(rowToMoveOn, columnToMoveOn) )
                return move;
        }
        return Direction.STAY; // Stay in place if no valid move is found
    }

    /**
     * Determines the direction in which the Monster will attack.
     * The Monster attacks the nearest alive Hero.
     *
     * @param local The current local 3x3 world centred at monster
     * @return The direction in which the Monster will attack, or null if no Heroes are found
     */
    @Override
    public Direction attackWhere(World local) {
        int monsterRow = (local.getRows() - 1)/2;
        int monsterColumn = monsterRow;
        // Search for the nearest alive Hero
        for (int i = local.getRows() - 1; i >= 0; i--){
            for (int j = local.getColumns() - 1; j >= 0; j--){
                if (local.isHero(i, j) && local.getEntity(i, j).isAlive()){
                    //Return Direction of attack
                    return Direction.getDirection(i-monsterRow, j-monsterColumn);
                }
            }
        }
        //Return null if no hero is found in local world
        return null;
    }

    /**
     * Can only be moved on top of if dad
     * @return isDead()
     */
    @Override
    public boolean canMoveOnTopOf() {
        return isDead();
    }

    /**
     * Can only be attacked if alive
     * @return isAlive()
     */
    @Override
    public boolean canBeAttacked() {
        return isAlive();
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + weaponType;
    }

}
