/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.ic.mda.transformer.core.util;

/**
 *
 * @author robertowm
 */
public final class StringUtils {

    private StringUtils() {}

    /**
     * Lower the first letter of a given string
     * @param string    given string to lower the first letter
     * @return  a string with the first letter lowered
     */
    public static String lowerFirstLetter(String string) {
        return string.replaceFirst(string.substring(0,1), string.substring(0,1).toLowerCase());
    }

    /**
     * Upper the first letter of a given string
     * @param string    given string to upper the first letter
     * @return  a string with the first letter uppered
     */
    public static String upperFirstLetter(String string) {
        return string.replaceFirst(string.substring(0,1), string.substring(0,1).toUpperCase());
    }

}
