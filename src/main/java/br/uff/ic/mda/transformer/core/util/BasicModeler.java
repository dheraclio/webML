/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.util;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.ModelManager;

/**
 *
 * @author Daniel
 */
public abstract class BasicModeler {

    //"String"
    /**
     *
     */
    public static final String TYPE_STRING = "String";
    //"Boolean"
    /**
     *
     */
    public static final String TYPE_BOOLEAN = "Boolean";
    //"Integer"
    /**
     *
     */
    public static final String TYPE_INTEGER = "Integer";


    /**
     *
     */
    public static final String ROLE_ELEMENT = "elements";
    /**
     *
     */
    public static final String ROLE_OWNER = "owner";
    /**
     *
     */
    public static final String ROLE_SUB = "sub";
    /**
     *
     */
    public static final String ROLE_SUPER = "super";
    /**
     *
     */
    public static final String CARD_N = "*";
    /**
     *
     */
    public static final String CARD_1 = "1";
    /**
     *
     */
    public static final String CARD_0_N = "0..*";
    /**
     *
     */
    public static final String CARD_0_1 = "0..1";
    /**
     *
     */
    public static final String CARD_1_N = "1..*";

    /**
     *
     */
    protected static ModelManager manager = ModelManager.instance();

    /**
     *
     * @param id
     * @param role
     * @return
     * @throws ContractException
     */
    protected String getFirstQueryResult(String id, String role) throws ContractException {
        return manager.query(id +"."+role);
    }

    /**
     *
     * @param query
     * @return
     * @throws ContractException
     */
    protected String getFirstQueryResult(String query) throws ContractException {
        return JDHelper.processQueryResult(manager.query(query))[0];
    }

    /**
     *
     * @param relationship
     * @param role
     * @param id
     * @return
     * @throws ContractException
     */
    protected String getId(String relationship, String role, String id) throws ContractException {
        String[] result = JDHelper.processQueryResult(manager.query(relationship+ ".allInstances()->select(a | a."+ role + "= " + id + ")"));
        if (result != null && result.length > 0 && !"".equals(result[0])) {
            return result[0];
        }
        return null;
    }
}
