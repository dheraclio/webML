/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.util;

import org.junit.Test;
import java.io.InputStream;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class TestHelper {

    /**
     * Fake test just to stop error of initialization
     */
    @Test
    public void testFake(){
        assertTrue(true);
    }

    /**
     *
     * @return
     */
    public static String getCurrentTargetTestPath() {
        String currentDir = System.getProperty("user.dir");
        return currentDir + "/target/test-classes/";
    }

    /**
     * Method description
     *
     *
     * @param file
     *
     * @param suddirectory
     * @return
     */
    public static InputStream getResourceFile(String file, String suddirectory) {
        String path = suddirectory + file;
        InputStream in = path.getClass().getResourceAsStream(path);
        assertTrue(in != null);
        return in;
    }

    public static void assertResult(String sText, String sResult, int firstIgnoredLines) {
        assertTrue(sText.compareTo(sResult) == 0);
    }
}
