package tests;

import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.pieces.AllPieceTests;
import tests.game.BoardTest;

/**
 * Created by samir on 9/21/16.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllPieceTests.class, BoardTest.class
})
public class RunAllTests extends TestSuite {
}
