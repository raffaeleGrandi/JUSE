package jusePack.units.collisions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import jusePack.utils.Const;

class SensorArrayTest {

    @Test
    void defaultInitValueIsZero() {
        SensorArray sa = new SensorArray();
        for (int i = 0; i < sa.length(); i++) {
            assertEquals(0, sa.getSensorValue(i));
        }
    }

    @Test
    void customInitValueIsApplied() {
        SensorArray sa = new SensorArray(255);
        for (int i = 0; i < sa.length(); i++) {
            assertEquals(255, sa.getSensorValue(i));
        }
    }

    @Test
    void lengthEqualsSensorCount() {
        SensorArray sa = new SensorArray();
        assertEquals(Const.sensorCount, sa.length());
    }

    @Test
    void isEmptyOnFreshArray() {
        SensorArray sa = new SensorArray();
        assertTrue(sa.isEmpty());
        assertFalse(sa.isFull());
    }

    @Test
    void setSensorValueIncrementsCounter() {
        SensorArray sa = new SensorArray();
        sa.setSensorValue(0, 5);
        sa.setSensorValue(1, 3);
        assertEquals(5, sa.getSensorValue(0));
        assertEquals(3, sa.getSensorValue(1));
        assertEquals(2, sa.collisionNum());
    }

    @Test
    void zeroValueDoesNotIncrementCounter() {
        SensorArray sa = new SensorArray();
        sa.setSensorValue(0, 0); // value == initSensValue (0), counter not incremented
        assertEquals(0, sa.collisionNum());
    }

    @Test
    void isFullWhenAllSensorsSet() {
        SensorArray sa = new SensorArray();
        for (int i = 0; i < sa.length(); i++) {
            sa.setSensorValue(i, 1);
        }
        assertTrue(sa.isFull());
        assertFalse(sa.isEmpty());
    }

    @Test
    void resetRestoresInitialState() {
        SensorArray sa = new SensorArray(255);
        sa.setSensorValue(0, 10);
        sa.setSensorValue(1, 20);
        sa.resetSensors();
        assertEquals(255, sa.getSensorValue(0));
        assertEquals(255, sa.getSensorValue(1));
        assertEquals(0, sa.collisionNum());
        assertTrue(sa.isEmpty());
    }

    @Test
    void sensorAnglesAreEvenlySpaced() {
        SensorArray sa = new SensorArray();
        double expected = 0.0;
        double step = Math.toRadians(360.0 / Const.sensorCount);
        for (int i = 0; i < sa.length(); i++) {
            assertEquals(expected, sa.getSensorAngle(i), 1e-10);
            expected += step;
        }
    }

    @Test
    void checkPointIsNullAfterReset() {
        SensorArray sa = new SensorArray();
        sa.setCheckingPoint(0, new java.awt.geom.Point2D.Double(1, 2));
        assertNotNull(sa.getCheckPoint(0));
        sa.resetSensors();
        assertNull(sa.getCheckPoint(0));
    }

    @Test
    void getSensorsReturnsSameArray() {
        SensorArray sa = new SensorArray();
        sa.setSensorValue(2, 7);
        int[] arr = sa.getSensors();
        assertEquals(7, arr[2]);
        assertEquals(sa.length(), arr.length);
    }
}
