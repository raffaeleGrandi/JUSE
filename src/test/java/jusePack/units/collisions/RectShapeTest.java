package jusePack.units.collisions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RectShapeTest {

    private static final double EPS = 1e-6;

    // 20×20 axis-aligned square centred at (50, 50)
    private RectShape square() {
        return new RectShape(50, 50, 20, 20, 0);
    }

    @Test
    void rayHitsFromLeft() {
        RectShape r = square();
        // Ray from x=0, y=50 heading right → entry at x=40
        double d = r.rayIntersect(0, 50, 1, 0, 1000);
        assertEquals(40.0, d, EPS);
    }

    @Test
    void rayHitsFromTop() {
        RectShape r = square();
        // Ray from (50, 0) heading down (+y) → entry at y=40
        double d = r.rayIntersect(50, 0, 0, 1, 1000);
        assertEquals(40.0, d, EPS);
    }

    @Test
    void rayMissesPassingRight() {
        RectShape r = square();
        // Ray at y=50 starts to the right and moves further right
        double d = r.rayIntersect(200, 50, 1, 0, 1000);
        assertEquals(-1, d);
    }

    @Test
    void rayMissesPassingAbove() {
        RectShape r = square();
        double d = r.rayIntersect(0, 0, 1, 0, 1000);
        assertEquals(-1, d);
    }

    @Test
    void originInsideReturnsZero() {
        RectShape r = square();
        double d = r.rayIntersect(50, 50, 1, 0, 1000);
        assertEquals(0, d);
    }

    @Test
    void rayTooShortReturnsNegOne() {
        RectShape r = square();
        // Entry at 40px but maxDist = 5
        double d = r.rayIntersect(0, 50, 1, 0, 5);
        assertEquals(-1, d);
    }

    @Test
    void rotated90DegreeRect() {
        // 40×10 rectangle rotated 90°, centred at origin
        // After rotation it effectively becomes 10×40
        RectShape r = new RectShape(0, 0, 40, 10, Math.PI / 2);
        // Ray from (0, -100) heading down (+y) should hit at y=-20 (half of 40)
        double d = r.rayIntersect(0, -100, 0, 1, 200);
        assertEquals(80.0, d, EPS);
    }

    @Test
    void rayParallelToFaceAndInsideSlabHits() {
        // 40×20 rect at origin, ray parallel to X axis at y=5 (within hh=10)
        RectShape r = new RectShape(0, 0, 40, 20, 0);
        // Ray from (-100, 5) heading right → entry at x=-20 → distance = 80
        double d = r.rayIntersect(-100, 5, 1, 0, 1000);
        assertEquals(80.0, d, EPS);
    }

    @Test
    void rayParallelToFaceAndOutsideSlabMisses() {
        RectShape r = new RectShape(0, 0, 40, 20, 0);
        // Ray at y=20 (outside hh=10)
        double d = r.rayIntersect(-100, 20, 1, 0, 1000);
        assertEquals(-1, d);
    }
}
