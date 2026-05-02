package jusePack.units.collisions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OvalShapeTest {

    private static final double EPS = 1e-6;

    // Circle of radius 10 centred at (50, 50)
    private OvalShape circle() {
        return new OvalShape(50, 50, 20, 20);
    }

    @Test
    void rayHitsCircleFromLeft() {
        OvalShape s = circle();
        // Ray starts at x=0, y=50 heading right (+x)
        double d = s.rayIntersect(0, 50, 1, 0, 1000);
        assertEquals(40.0, d, EPS); // centre at 50, radius 10 → entry at x=40
    }

    @Test
    void rayMissesByPassingAbove() {
        OvalShape s = circle();
        // Ray starts at y=0 (well above circle height) heading right
        double d = s.rayIntersect(0, 0, 1, 0, 1000);
        assertEquals(-1, d);
    }

    @Test
    void rayBehindCircleReturnsNegOne() {
        OvalShape s = circle();
        // Ray starts to the right of circle, pointing further right
        double d = s.rayIntersect(200, 50, 1, 0, 1000);
        assertEquals(-1, d);
    }

    @Test
    void originInsideCircleReturnsZero() {
        OvalShape s = circle();
        // Origin exactly at centre → inside → distance 0
        double d = s.rayIntersect(50, 50, 1, 0, 1000);
        assertEquals(0, d);
    }

    @Test
    void rayTooShortReturnsNegOne() {
        OvalShape s = circle();
        // Circle entry at distance 40, but maxDist = 10
        double d = s.rayIntersect(0, 50, 1, 0, 10);
        assertEquals(-1, d);
    }

    @Test
    void rayHitsEllipseVertically() {
        // Ellipse: cx=0, cy=0, width=20 (a=10), height=40 (b=20)
        OvalShape ellipse = new OvalShape(0, 0, 20, 40);
        // Ray from top going down (+y): entry at y=-20 from origin heading +y
        double d = ellipse.rayIntersect(0, -100, 0, 1, 200);
        assertEquals(80.0, d, EPS); // entry at y=-20, origin at y=-100
    }

    @Test
    void tangentRayHitsAtOnePoint() {
        OvalShape s = circle();
        // Ray tangent to circle along y=60 (just touching)
        double d = s.rayIntersect(0, 60, 1, 0, 1000);
        // discriminant ≈ 0 → single tangent hit at x=50
        assertEquals(50.0, d, EPS);
    }
}
