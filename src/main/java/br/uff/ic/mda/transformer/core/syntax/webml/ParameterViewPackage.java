/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webml;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.core.syntax.webml.WebMLBasicPackage;

/**
 *
 * @author Daniel
 */
public class ParameterViewPackage extends WebMLBasicPackage {

    /**
     *
     */
    public static final String LINKPARAMETERCOUPLING = PREFIX + "LinkParameterCoupling";
    /**
     *
     */
    public static final String LINKPARAMETERCOUPLING_PASSING = "passing";
    /**
     *
     */
    public static final String LINKPARAMETERCOUPLING_LOOPBACK = "loopback";
    /**
     *
     */
    public static final String PARAMETER = PREFIX + "Parameter";
    /**
     *
     */
    public static final String PARAMETER_PASSING = "passing";
    /**
     *
     */
    public static final String PARAMETER_LOOPBACK = "loopback";
    //ASSOCIATIONS   
    /**
     *
     */
    public static final String ROLE_SOURCEUNITPARAMETER = "sourceUnitParameter";
    /**
     *
     */
    public static final String ROLE_SOURCEPARAMETER = "sourceParameter";
    /**
     *
     */
    public static final String ROLE_TARGETUNITPARAMETER = "targetUnitParameter";
    /**
     *
     */
    public static final String ROLE_TARGETPARAMETER = "targetParameter";
    /**
     *
     */
    public static final String ROLE_OUTPUTPARAMETER = "outputParameter";
    /**
     *
     */
    public static final String ROLE_INPUTPARAMETER = "inputParameter";

    /**
     *
     * @throws ContractException
     */
    public static void insertMetaModel() throws ContractException {
        manager.insertClass(LINKPARAMETERCOUPLING);
        manager.insertClass(PARAMETER);

        insertMetaModelAttributes();
    }

    private static void insertMetaModelAttributes() throws ContractException {
        manager.insertAttribute(LINKPARAMETERCOUPLING, LINKPARAMETERCOUPLING_PASSING, TYPE_BOOLEAN);
        manager.insertAttribute(LINKPARAMETERCOUPLING, LINKPARAMETERCOUPLING_LOOPBACK, TYPE_BOOLEAN);

        manager.insertAttribute(PARAMETER, LINKPARAMETERCOUPLING_PASSING, TYPE_BOOLEAN);
        manager.insertAttribute(PARAMETER, LINKPARAMETERCOUPLING_LOOPBACK, TYPE_BOOLEAN);
    }

    /**
     *
     * @throws ContractException
     */
    public static void insertMetaModelAssociations() throws ContractException {
        //TODO completar
    }
}
