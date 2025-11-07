package mvh.world;

import mvh.enums.Direction;

/**
 * The Hero class represents a playable character in the game.
 * A Hero is an Entity with user-provided weapon strength and armor strength.
 * Heroes can move, attack, and interact with the world.
 *
 * @author Simrandeep Kaur
 * @email simrandeep.simrandee@ucalgary.ca
 * Tutorial T06
 * Date March 16, 2025
 * @version 1.1
 */
public final class Hero extends Entity{

    /**
     * The user provided weapon strength
     */
    private final int weaponStrength;

    /**
     * The user provided armor strength
     */
    private final int armorStrength;

    /**
     * A Hero has regular health and symbol as well as a weapon strength and armor strength
     * @param health Health of hero
     * @param symbol Symbol for map to show hero
     * @param weaponStrength The weapon strength of the hero
     * @param armorStrength The armor strength of the hero
     */
    public Hero(int health, char symbol, int weaponStrength, int armorStrength) {
        super(symbol, health);
        this.armorStrength = armorStrength;
        this.weaponStrength = weaponStrength;
    }

    /**
     * The weapon strength of monster is from user value
     * @return The weapon strength of monster is from user value
     */
    @Override
    public int weaponStrength() {
        return weaponStrength;
    }

    /**
     * The armor strength of monster is from user value
     * @return The armor strength of monster is from user value
     */
    @Override
    public int armorStrength() {
        return armorStrength;
    }


    /**
     * Determines the direction in which the hero should move.
     * The hero moves towards the nearest alive monster if possible.
     * If no monster is nearby, the hero moves northwest or chooses a random direction.
     *
     * @param local The current local 5x5 world centred at hero
     * @return The direction in which the hero will move
     */
    @Override
    public Direction chooseMove(World local) {
        int heroRow = (local.getRows()-1)/2;
        int heroColumn = heroRow;
        int size = local.getRows();
        // Search for the nearest alive monster in the world
        for (int i = 0; i < size; i++){
            if (i == 0 || i == size -1) {
                for (int j = 0; j < size; j++) {
                    if (local.isMonster(i, j) && local.getEntity(i, j).isAlive()) {
                        // Find the best direction to move toward the monster
                        for (Direction p : Direction.getDirections(i - heroRow, j - heroColumn)) {
                            int rowToMoveOn = p.getRowChange() + heroRow;
                            int columnToMoveOn = p.getColumnChange() + heroColumn;
                            if (local.canMoveOnTopOf(rowToMoveOn, columnToMoveOn))
                                return p; // Move towards the monster
                        }
                    }
                }
            }
            else
                for (int j = 0; j < size; j += 4){
                    if (local.isMonster(i, j) && local.getEntity(i, j).isAlive()) {
                        // Find the best direction to move toward the monster
                        for (Direction p : Direction.getDirections(i - heroRow, j - heroColumn)) {
                            int rowToMoveOn = p.getRowChange() + heroRow;
                            int columnToMoveOn = p.getColumnChange() + heroColumn;
                            if (local.canMoveOnTopOf(rowToMoveOn, columnToMoveOn))
                                return p; // Move towards the monster
                        }
                    }
                }
        }
        // If no monster is found, move northwest if possible
        Direction move = Direction.NORTHWEST;
        int rowToMoveOn = heroRow + move.getRowChange();
        int columnToMoveOn = heroColumn + move.getColumnChange();
        if (local.canMoveOnTopOf(rowToMoveOn, columnToMoveOn) )
            return move;
        else {
            // If northwest is blocked, move in a random direction if possible
            move = Direction.getRandomDirection();
            rowToMoveOn = heroRow + move.getRowChange();
            columnToMoveOn = heroColumn + move.getColumnChange();
            if (local.canMoveOnTopOf(rowToMoveOn, columnToMoveOn) )
                return move;
        }
        // Stay in place if no valid move is found
        return Direction.STAY;
    }

    /**
     * Determines the direction in which the hero will attack.
     * The hero attacks the nearest alive monster.
     *
     * @param local The current local 3x3 world centred at hero
     * @return The direction in which the hero will attack, or null if no monsters are found
     */
    @Override
    public Direction attackWhere(World local) {
        int heroRow = (local.getRows()-1)/2;
        int heroColumn = heroRow;
        int size = local.getRows();
        // Search for the nearest alive monster
        for (int i = 0;i < size; i++){
            for (int j = 0; j < size ; j++){
                if (local.isMonster(i, j) && local.getEntity(i, j).isAlive()){
                    return Direction.getDirection(i-heroRow, j-heroColumn); //return direction of attack
                    }
            }
        }
        //Return null if no monster found in 3x3 local world
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
    public String toString(){
        return super.toString()+"\t"+weaponStrength+"\t"+armorStrength;
    }

}
