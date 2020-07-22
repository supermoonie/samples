package com.github.supermoonie;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    private final static Pattern NUM_PATTER = Pattern.compile("(\\d+)");

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        Matcher matcher = NUM_PATTER.matcher("5 （已克隆）");
        if (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }


}
