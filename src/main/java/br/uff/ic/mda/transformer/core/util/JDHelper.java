/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.ic.mda.transformer.core.util;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.ModelManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Daniel
 */
public abstract class JDHelper {

    public static String getNewId(){
        final String prefix = "ID";
        return  prefix + System.nanoTime();
    }

    public static String getName(String id, ModelManager manager) throws ContractException{
        final String name = ".name";
        return  manager.query(id + name).replace("'", "");
    }

    /**
     * @author Roberto
     * Process the result of a XEOS's query and transform it in a array of strings
     * @param original the result of a XEOS's query
     * @return  an array with the result of a XEOS's query
     */
    public static String[] processQueryResult(String original) {
        String result = original.replace("\n", "");
        int inicio = result.indexOf("{");
        int fim = result.indexOf("}");

        if (inicio < 0 && fim < 0) {
            return result.replace("{","").replace("}","").replace(" ", "").replace("'","").split(",");
        } else {
            if (inicio < 0) {
                return result.substring(0, fim).replace("{","").replace("}","").replace(" ", "").replace("'","").split(",");
            } else if (fim < 0) {
                return result.substring(inicio+1,result.length()).replace("{","").replace("}","").replace(" ", "").replace("'","").split(",");
            }
        }
        String[] processedResult = result.substring(inicio+1, fim).replace("{","").replace("}","").replace(" ", "").replace("'","").split(",");
        if (processedResult != null && processedResult.length == 1 && "".equals(processedResult[0])) {
            return new String[0];
        }
        return processedResult;
    }

    /**
     * Removes "" and nulls from processQueryResult result
     * @param result
     * @return
     */
    public static List<String> filterQueryResult(String result){
        String[] ids = processQueryResult(result);
        List<String> results = new ArrayList<String>();
        String blank = "";
        for (String id : ids) {
            if (id != null && !blank.equals(id)) {
                results.add(id);
            }
        }
        return results;
    }
}
