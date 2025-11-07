package mvh.test;

import mvh.enums.Direction;
import mvh.enums.WeaponType;
import mvh.util.Reader;
import mvh.world.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the `MvH` game logic, focusing on world creation, entity interactions, and movement behaviors.
 * These tests cover different functionalities of the game, including loading the world from a file, generating
 * world strings, entity movement, and attack behaviors for both Heroes and Monsters.
 *
 * @author Simrandeep Kaur
 * @email simrandeep.simrandee@ucalgary.ca
 * Tutorial T06
 * Date March 16, 2025
 * @version 1.1
 */
class MvHTest {

    /**
     * This test does not count for credit for A2
     */
    @Test
    void gameStringMH() {
        //This test mimics the example world.txt input
        //Should be good example of common output format to match for your gameString/worldString attempts for A2

        //Since IDs are a static class variable, for each unit test to act like World is new and entities are the first we've seen
        //We need to reset the ID counter to 1 so Monster is 1 and Hero is 2
        //If we didn't do this each gameString test our IDs would continue to increase through-out all the tests instead
        //of starting at 1 each time
        Entity.resetIDCounter();
        //Do a regular test
        //Make OO test framework
        Monster monster = new Monster(10, 'M', WeaponType.SWORD);
        Hero hero = new Hero(10, 'H', 3, 1);
        World world = new World(3, 3);
        world.addEntity(0, 0, monster);
        world.addEntity(2, 2, hero);
        //Expected is this String
        String expected = """
                #####
                #M..#
                #...#
                #..H#
                #####
                NAME   \tS\tH\tSTATE\tINFO
                Mons(1)\tM\t10\tALIVE\tSWORD
                Hero(2)\tH\t10\tALIVE\t3\t1
                """;
        //Get actual from world.gameString()
        String actual = world.gameString();
        //Now call function
        assertEquals(expected, actual);
    }

    @Test
    public void testLoadWorld_validFile() throws IOException {
        File file = new File("world.txt"); // Adjust path to match your file location
        World world = Reader.loadWorld(file);
        assertNotNull(world, "World should not be null after loading from file.");
        assertEquals(3, world.getRows(), "World should have 3 rows.");
        assertEquals(3, world.getColumns(), "World should have 3 columns.");
    }

    @Test
    public void testLoadWorld_invalidFile() {
        File file = new File("invalidWorld.txt");

        // Adjust to expect FileNotFoundException
        assertNull(Reader.loadWorld(file)); //loadWorld should return null if the file does not exist
    }

    @Test
    public void testGameString() {
        Entity.resetIDCounter();
        World world1 = new World(3, 3); // Create a sample world
        // Set up the world with entities
        world1.addEntity(0, 0, new Hero(10, 'H', 5, 3));
        world1.addEntity(1, 1, new Monster(10, 'M', WeaponType.SWORD));

        String expectedOutput = "#####\n#H..#\n#.M.#\n#...#\n#####\nNAME   \tS\tH\tSTATE\tINFO\nHero(1)\tH\t10\tALIVE\t5\t3\nMons(2)\tM\t10\tALIVE\tSWORD\n";
        String actualOutput = world1.gameString();
        assertEquals(expectedOutput, actualOutput, "The game string should match the expected output.");
    }

    @Test
    public void testWorldString() {
        World world = new World(3, 3); // Create a sample world
        // Set up the world with entities
        world.addEntity(0, 0, new Hero(10, 'H', 5, 3));
        world.addEntity(1, 1, new Monster(10, 'M', WeaponType.SWORD));

        String expectedOutput = "#####\n#H..#\n#.M.#\n#...#\n#####\n";
        String actualOutput = world.worldString();
        assertEquals(expectedOutput, actualOutput, "The world string should match the expected output.");
    }

    @Test
    public void testGetLocal_3x3() {
        World world = new World(3, 3); // Create a sample 5x5 world
        // Set up the world with entities
        world.addEntity(2, 2, new Hero(10, 'H', 5, 3));

        World localWorld = world.getLocal(7, 2, 2);
        assertNotNull(localWorld, "Local world should not be null.");
        assertEquals(7, localWorld.getRows(), "Local world should have 3 rows.");
        assertEquals(7, localWorld.getColumns(), "Local world should have 3 columns.");
        // hero should be at the centre of local world
        assertEquals(localWorld.getEntity(3, 3), world.getEntity(2, 2));
        assertEquals("""
                #######
                #...###
                #...###
                #..H###
                #######
                #######
                #######
                """, localWorld.worldString());
    }
    @Test
    public void testGetLocal_5x5() {
        World world = new World(5, 5); // Create a sample 5x5 world
        // Set up the world with entities
        world.addEntity(2, 2, new Hero(10, 'H', 5, 3));

        World localWorld = world.getLocal(5, 2, 2);
        assertNotNull(localWorld, "Local world should not be null.");
        assertEquals(5, localWorld.getRows(), "Local world should have 5 rows.");
        assertEquals(5, localWorld.getColumns(), "Local world should have 5 columns.");
        assertEquals("""
                #######
                #.....#
                #.....#
                #..H..#
                #.....#
                #.....#
                #######
                """, localWorld.worldString());
    }
    @Test
    public void testGetLocal__3x3() {
        Entity.resetIDCounter();
        World world = new World(5, 5); // Create a sample 5x5 world
        // Set up the world with entities
        world.addEntity(2, 2, new Hero(10, 'H', 5, 3));
        world.addEntity(1,1, new Monster(10, 'M', WeaponType.AXE));
        World localWorld = world.getLocal(3, 2, 2);
        assertNotNull(localWorld, "Local world should not be null.");
        assertEquals(3, localWorld.getRows(), "Local world should have 3 rows.");
        assertEquals(3, localWorld.getColumns(), "Local world should have 3 columns.");
        // hero should be at the centre of local world
        assertEquals(localWorld.getEntity(1, 1), world.getEntity(2, 2));
        assertEquals("""
                #####
                #M..#
                #.H.#
                #...#
                #####
                """, localWorld.worldString());
    }
    @Test
    public void testHeroChooseMove() {
        Entity.resetIDCounter();
        World world = new World(5, 5); // Create a sample world
        // Set up the world with entities
        world.addEntity(3, 3, new Hero(10, 'H', 5, 3));
        world.addEntity(1, 3, new Monster(10, 'M', WeaponType.SWORD));

        Hero hero = (Hero) world.getEntity(3, 3);
        Direction moveDirection = hero.chooseMove(world.getLocal(5, 3, 3));
        assertEquals(Direction.NORTH, moveDirection);// Hero should move north to progress towards monster
    }
    @Test
    public void testHeroChooseMove_noMonster() {
        Entity.resetIDCounter();
        World world = new World(5, 5); // Create a sample world
        world.addEntity(2, 2, new Hero(10, 'H', 5, 3));

        Hero hero = (Hero) world.getEntity(2, 2);
        Direction moveDirection = hero.chooseMove(world.getLocal(5, 2, 2));
        assertEquals(Direction.NORTHWEST, moveDirection, "If no monster is around, Hero should move NORTHWEST.");
    }
    @Test
    public void testMonsterChooseMove() {
        Entity.resetIDCounter();
        World world2 = new World(5, 5); // Create a sample world
        world2.addEntity(4, 2, new Monster(10, 'M', WeaponType.SWORD));
        world2.addEntity(2, 2, new Hero(10, 'H', 5, 3));

        Monster monster = (Monster) world2.getEntity(4, 2);
        Direction moveDirection = monster.chooseMove(world2.getLocal(5, 4, 2));
        assertEquals(Direction.NORTH, moveDirection, "Monster should return a direction to move.");
        Entity.resetIDCounter();
        World world3 = new World(5, 5); // Create a sample world
        world3.addEntity(0, 1, new Monster(10, 'M', WeaponType.SWORD));
        world3.addEntity(2, 2, new Hero(10, 'H', 5, 3));
        Monster monster2 = (Monster) world3.getEntity(0, 1);
        Direction moveDirection2 = monster2.chooseMove(world3.getLocal(5, 0, 1));
        assertEquals(Direction.SOUTHEAST, moveDirection2, "Monster should return a direction to move.");
    }

    @Test
    public void testMonsterChooseMove_noHero() {
        Entity.resetIDCounter();
        World world = new World(5, 5); // Create a sample world
        world.addEntity(2, 2, new Monster(10, 'M', WeaponType.SWORD));

        Monster monster = (Monster) world.getEntity(2, 2);
        Direction moveDirection = monster.chooseMove(world.getLocal(5, 2, 2));
        assertEquals(Direction.SOUTHEAST, moveDirection, "If no hero is around, Monster should move SOUTHEAST.");
    }
    @Test
    public void testHeroAttackWhere() {
        World world = new World(5, 5); // Create a sample world
        world.addEntity(0, 2, new Hero(10, 'H', 5, 3));
        world.addEntity(1, 1, new Monster(10, 'M', WeaponType.SWORD));

        Hero hero = (Hero) world.getEntity(0, 2);
        Direction attackDirection = hero.attackWhere(world.getLocal(3, 0, 2));
        assertNotNull(attackDirection, "Hero should attack the first Monster they encounter.");
        assertEquals(Direction.SOUTHWEST, attackDirection); // Hero should attack monster in SOUTHWEST direction
    }

    @Test
    public void testHeroAttackWhere_noMonster() {
        World world = new World(5, 5); // Create a sample world
        world.addEntity(2, 2, new Hero(10, 'H', 5, 3));

        Hero hero = (Hero) world.getEntity(2, 2);
        Direction attackDirection = hero.attackWhere(world.getLocal(3, 2, 2));
        assertNull(attackDirection, "Hero should not attack if no alive monsters are around.");
    }
    @Test
    public void testMonsterAttackWhere() {
        World world = new World(5, 5); // Create a sample world
        world.addEntity(0, 2, new Monster(10, 'M', WeaponType.SWORD));
        world.addEntity(1, 1, new Hero(10, 'H', 5, 3));

        Monster monster = (Monster) world.getEntity(0, 2);
        Direction attackDirection = monster.attackWhere(world.getLocal(3, 0, 2));
        assertNotNull(attackDirection, "Monster should attack the first Hero they encounter.");
        assertEquals(Direction.SOUTHWEST, attackDirection);
        world.addEntity(1, 3, new Hero(10, 'H', 5, 3));
        Direction attackDirection2 = monster.attackWhere(world.getLocal(3, 0, 2));
        assertEquals(Direction.SOUTHEAST, attackDirection2); // Monster should attack the new monster now

    }

    @Test
    public void testMonsterAttackWhere_noHero() {
        World world = new World(5, 5); // Create a sample world
        world.addEntity(2, 2, new Monster(10, 'M', WeaponType.SWORD));

        Monster monster = (Monster) world.getEntity(2, 2);
        Direction attackDirection = monster.attackWhere(world.getLocal(3, 2, 2));
        assertNull(attackDirection, "Monster should not attack if no alive heroes are around.");
    }

}