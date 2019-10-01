package test;

import model.TimeStamp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimeStampTest {
    TimeStamp timeStamp;

    @Test
    public void constructorThrowsIllegalArgumentException() {
        try {
            timeStamp = new TimeStamp(-5);
            fail("TimeStamp's constructor should have thrown an exception!");
        } catch (IllegalArgumentException e) {
            //passes, don't do anything
        }
    }

    @Test
    public void getTimeInSecondsZeroTest() {
        timeStamp = new TimeStamp(0);
        assertEquals(0,timeStamp.getTimeStampInSeconds());
    }

    @Test
    public void getTimeInSecondsPositiveTest() {
        timeStamp = new TimeStamp(13213);
        assertEquals(13213,timeStamp.getTimeStampInSeconds());
    }

    @Test
    public void getFormatZeroSecondsTest() {
        timeStamp = new TimeStamp(0);
        assertEquals("00:00",timeStamp.getFormat());
    }

    @Test
    public void getFormatOneDigitSecondsTest() {
        timeStamp = new TimeStamp(6);
        assertEquals("00:06",timeStamp.getFormat());
    }

    @Test
    public void getFormatTenSecondsTest() {
        timeStamp = new TimeStamp(10);
        assertEquals("00:10", timeStamp.getFormat());
    }

    @Test
    public void getFormatTwoDigitSecondsTest() {
        timeStamp = new TimeStamp(59);
        assertEquals("00:59", timeStamp.getFormat());
    }

    @Test
    public void getFormatOneMinuteTest() {
        timeStamp = new TimeStamp(60 * 1);
        assertEquals("01:00", timeStamp.getFormat());
    }

    @Test
    public void getFormatOneDigitMinuteTest() {
        timeStamp = new TimeStamp(60 * 1 + 47);
        assertEquals("01:47", timeStamp.getFormat());
    }

    @Test
    public void getFormatTenMinutesTest() {
        timeStamp = new TimeStamp(10 * 60);
        assertEquals("10:00", timeStamp.getFormat());
    }

    @Test
    public void getFormatTwoDigitMinutesTest() {
        timeStamp = new TimeStamp(12 * 60 + 26);
        assertEquals("12:26", timeStamp.getFormat());
    }


    @Test
    public void getFormatMinutesSecondsTest() {
        timeStamp = new TimeStamp(11 * 60 + 38);
        assertEquals("11:38", timeStamp.getFormat());
    }

    @Test
    public void getFormatOneHourTest() {
        timeStamp = new TimeStamp(1 * 3600);
        assertEquals("01:00:00", timeStamp.getFormat());
    }

    @Test
    public void getFormatTenHoursTest() {
        timeStamp = new TimeStamp(10 * 3600);
        assertEquals("10:00:00",timeStamp.getFormat());
    }

    @Test
    public void getFormatHoursMinutesSecondsTest() {
        timeStamp = new TimeStamp(40 + 11 * 60 + 12 * 3600);
        assertEquals("12:11:40", timeStamp.getFormat());
    }

    @Test
    public void getFormatWithMillisecondsHoursMinutesSecondsTest() {
        timeStamp = new TimeStamp(3.4 + 23 * 60 + 2 * 3600);
        assertEquals("02:23:03.40",timeStamp.getFormatWithMilliseconds());
    }

    @Test
    public void getFormatWithMillisecondsMinutesSecondsTest() {
        timeStamp = new TimeStamp(5.0 + 1 * 60);
        assertEquals("01:05.00",timeStamp.getFormatWithMilliseconds());
    }

    @Test
    public void isAfterFalseTest() {
        timeStamp = new TimeStamp(13);
        assertFalse(timeStamp.isAfter(new TimeStamp(58)));
    }

    @Test
    public void isAfterSameTest() {
        timeStamp = new TimeStamp(910);
        assertFalse(timeStamp.isAfter(new TimeStamp(910)));
    }

    @Test
    public void isAfterTrueTest() {
        timeStamp = new TimeStamp(3);
        assertTrue(timeStamp.isAfter(new TimeStamp(1)));
    }

    @Test
    public void getTimeStampAfterOffsetZeroTest() {
        timeStamp = new TimeStamp(0);
        assertEquals(new TimeStamp(0 + 0), timeStamp.getTimeStampAfterOffset(0));
    }

    @Test
    public void getTimeStampAfterOffsetZeroToPositiveTest() {
        timeStamp = new TimeStamp(0);
        assertEquals(new TimeStamp(0 + 321), timeStamp.getTimeStampAfterOffset(321));
    }

    @Test
    public void getTimeStampAfterOffsetPositiveToPositiveTest() {
        timeStamp = new TimeStamp(2311.323);
        assertEquals(new TimeStamp(2311.323 + 33), timeStamp.getTimeStampAfterOffset(33));
    }

    @Test
    public void isEqualsNullTest() {
        timeStamp = new TimeStamp(23985);
        assertFalse(timeStamp.equals(null));
    }

    @Test
    public void isEqualsSameVariableTest() {
        timeStamp = new TimeStamp(321);
        assertTrue(timeStamp.equals(timeStamp));
    }

    @Test
    public void isEqualsDifferentTypeTest() {
        timeStamp = new TimeStamp(19);
        assertFalse(timeStamp.equals("foo"));
    }

    @Test
    public void isEqualsZeroTrueTest() {
        timeStamp = new TimeStamp(0);
        assertTrue(timeStamp.equals(new TimeStamp(0)));
    }

    @Test
    public void isEqualsZeroFalseTest() {
        timeStamp = new TimeStamp(0);
        assertFalse(timeStamp.equals(new TimeStamp(13.3)));
    }

    @Test
    public void isEqualsPosTrueTest() {
        timeStamp = new TimeStamp(32.30);
        assertTrue(timeStamp.equals(new TimeStamp(32.30)));
    }

    @Test
    public void isEqualsPosFalseTest() {
        timeStamp = new TimeStamp(776.4);
        assertFalse(timeStamp.equals(new TimeStamp(32.30)));
    }

    @Test
    public void getHashCodeEqualTest() {
        timeStamp = new TimeStamp(513);
        assertEquals(timeStamp.hashCode(), new TimeStamp(513).hashCode());
    }

    @Test
    public void getHashCodeNotEqualTest() {
        timeStamp = new TimeStamp(6941.231);
        assertFalse(timeStamp.hashCode() == new TimeStamp(31).hashCode());
    }


    @Test
    public void getTimeStampAfterOffsetThrowsIllegalArgumentExceptionTest() {
        timeStamp = new TimeStamp(13);
        try {
            timeStamp.getTimeStampAfterOffset(-3);
            fail("getTimeStampAfterOffset was supposed to throw an exception if argument < 0");
        } catch (IllegalArgumentException e) {
            //passes, so do nothing
        }
    }
}
