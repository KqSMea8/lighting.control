package com.dikong.lightcontroller;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Optional;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
        Optional<String> name =Optional.of("lrf");
        System.out.println(name.isPresent());
        System.out.println(Optional.ofNullable("lrf").isPresent());
        System.out.println(Optional.ofNullable(null).isPresent());
    }
}
