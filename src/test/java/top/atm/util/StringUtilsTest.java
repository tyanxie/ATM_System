package top.atm.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author taifu
 */

@SuppressWarnings ("ConstantConditions")
public class StringUtilsTest {

    @Test
    public void join() {
    }

    @Test
    public void testEquals() {
        Assert.assertFalse(StringUtils.equals("123", "456"));
        Assert.assertFalse(StringUtils.equals(null, "456"));
        Assert.assertFalse(StringUtils.equals("123", null));
        Assert.assertTrue(StringUtils.equals(null, null));
        Assert.assertTrue(StringUtils.equals("123", "123"));
    }

    @Test
    public void equalsIgnoreCase() {
        Assert.assertFalse(StringUtils.equalsIgnoreCase("abc", "def"));
        Assert.assertFalse(StringUtils.equalsIgnoreCase(null, "456"));
        Assert.assertFalse(StringUtils.equalsIgnoreCase("123", null));
        Assert.assertTrue(StringUtils.equalsIgnoreCase(null, null));
        Assert.assertTrue(StringUtils.equalsIgnoreCase("abc", "ABc"));
        Assert.assertTrue(StringUtils.equalsIgnoreCase("a1b2c3", "a1B2C3"));
    }
}