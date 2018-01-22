package com.dikong.lightcontroller;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
        System.out.println("test");
        String s = "ALL_ON_OFF=1,BV;全开或全关";
        String[] split = s.split("=");
        System.out.println(split[0]);
        String[] split1 = split[1].split(",");
        System.out.println(split1[0]);
        String[] split2 = split1[1].split(";");
        System.out.println(split2[0]);
        System.out.println(split2[1]);
        System.out.println(split2[2]);
    }
}
