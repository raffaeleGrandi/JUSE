package jusePack.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void defaultConstructorIsOrigin() {
        Position p = new Position();
        assertEquals(0.0, p.getX());
        assertEquals(0.0, p.getY());
        assertEquals(0.0, p.getTheta());
    }

    @Test
    void fullConstructorStoresValues() {
        Position p = new Position(3.0, 4.0, Math.PI / 2);
        assertEquals(3.0, p.getX());
        assertEquals(4.0, p.getY());
        assertEquals(Math.PI / 2, p.getTheta(), 1e-10);
    }

    @Test
    void copyConstructorIsIndependent() {
        Position original = new Position(1.0, 2.0, 0.5);
        Position copy = new Position(original);
        assertEquals(original.getX(), copy.getX());
        assertEquals(original.getY(), copy.getY());
        assertEquals(original.getTheta(), copy.getTheta(), 1e-10);

        // Mutating original must not affect copy
        original.setPosition(9.0, 9.0, 9.0);
        assertEquals(1.0, copy.getX());
        assertEquals(2.0, copy.getY());
    }

    @Test
    void getThetaIsNormalizedTo2Pi() {
        // theta stored as 3*PI should be returned as PI (mod 2PI)
        Position p = new Position(0, 0, 3 * Math.PI);
        assertEquals(Math.PI, p.getTheta(), 1e-10);
    }

    @Test
    void setPositionWithThreeArgs() {
        Position p = new Position();
        p.setPosition(5.0, 6.0, 1.0);
        assertEquals(5.0, p.getX());
        assertEquals(6.0, p.getY());
        assertEquals(1.0, p.theta);
    }

    @Test
    void setPositionWithPositionArg() {
        Position p = new Position();
        Position src = new Position(7.0, 8.0, 2.0);
        p.setPosition(src);
        assertEquals(7.0, p.getX());
        assertEquals(8.0, p.getY());
        assertEquals(2.0, p.theta);
    }

    @Test
    void setLocationUpdatesXY() {
        Position p = new Position(1.0, 1.0, 1.0);
        p.setLocation(3.5, 4.5);
        assertEquals(3.5, p.getX());
        assertEquals(4.5, p.getY());
    }

    @Test
    void getPositionReturnsCopy() {
        Position p = new Position(2.0, 3.0, 0.1);
        Position copy = p.getPosition();
        assertNotSame(p, copy);
        assertEquals(p.getX(), copy.getX());
        assertEquals(p.getY(), copy.getY());
        assertEquals(p.theta, copy.theta);
    }

    @Test
    void toStringContainsCoordinates() {
        Position p = new Position(1.0, 2.0, 3.0);
        String s = p.toString();
        assertTrue(s.contains("1.0"));
        assertTrue(s.contains("2.0"));
        assertTrue(s.contains("3.0"));
    }
}
