package scripts.AdvancedWalking.Tests.Core.Mathematics;

import org.junit.Test;
import scripts.AdvancedWalking.Core.Mathematics.FastMath;

import static org.junit.Assert.*;

/**
 * @author Laniax
 */
public class FastMathTest {

    @Test
    public void testByteMinMax() throws Exception {

        byte value1 = FastMath.minMax((byte) 10, (byte) 5, (byte) 15);
        byte value2 = FastMath.minMax((byte) 3, (byte) 5, (byte) 15);
        byte value3 = FastMath.minMax((byte) 17, (byte) 5, (byte) 15);

        assertEquals(10, value1);
        assertEquals(5, value2);
        assertEquals(15, value3);
    }

    @Test
    public void testShortMinMax() throws Exception {

        short value1 = FastMath.minMax((short) 10, (short) 5, (short) 15);
        short value2 = FastMath.minMax((short) 3, (short) 5, (short) 15);
        short value3 = FastMath.minMax((short) 17, (short) 5, (short) 15);

        assertEquals(10, value1);
        assertEquals(5, value2);
        assertEquals(15, value3);
    }

    @Test
    public void testIntMinMax() throws Exception {

        int value1 = FastMath.minMax(10, 5, 15);
        int value2 = FastMath.minMax(3, 5, 15);
        int value3 = FastMath.minMax(17, 5, 15);

        assertEquals(10, value1);
        assertEquals(5, value2);
        assertEquals(15, value3);
    }

    @Test
    public void testLongMinMax() throws Exception {

        long value1 = FastMath.minMax((long) 10, 5, 15);
        long value2 = FastMath.minMax((long) 3, 5, 15);
        long value3 = FastMath.minMax((long) 17, 5, 15);

        assertEquals(10, value1);
        assertEquals(5, value2);
        assertEquals(15, value3);
    }

    @Test
    public void testFloatMinMax() throws Exception {

        float value1 = FastMath.minMax(10.0f, 5.0f, 15.0f);
        float value2 = FastMath.minMax(3.0f, 5.0f, 15.0f);
        float value3 = FastMath.minMax(17.0f, 5.0f, 15.0f);

        assertEquals(10.0f, value1, 0.01f);
        assertEquals(5.0f, value2, 0.01f);
        assertEquals(15.0f, value3, 0.01f);
    }

    @Test
    public void testDoubleMinMax() throws Exception {

        double value1 = FastMath.minMax(10.0d, 5.0d, 15.0d);
        double value2 = FastMath.minMax(3.0d, 5.0d, 15.0d);
        double value3 = FastMath.minMax(17.0d, 5.0d, 15.0d);

        assertEquals(10.0d, value1, 0.01d);
        assertEquals(5.0d, value2, 0.01d);
        assertEquals(15.0d, value3, 0.01d);

    }
}