package tests.pieces;

import main.pieces.Princess;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 * Created by samir on 9/7/16.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        PieceTest.class,
        PawnTest.class, RookTest.class, KnightTest.class,
        BishopTest.class, QueenTest.class, KingTest.class,
        FerzTest.class, PrincessTest.class
})
public class AllPieceTests {
}