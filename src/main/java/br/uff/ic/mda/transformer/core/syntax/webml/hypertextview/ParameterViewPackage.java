/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webml.hypertextview;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.core.syntax.webml.WebMLBasicPackage;

/**
 *
 * @author Daniel
 */
public class ParameterViewPackage extends WebMLBasicPackage {

    public static final String LINKPARAMETERCOUPLING = "LinkParameterCoupling";
    public static final String LINKPARAMETERCOUPLING_PASSING = "passing";
    public static final String LINKPARAMETERCOUPLING_LOOPBACK = "loopback";
    public static final String PARAMETER = "Parameter";
    public static final String PARAMETER_PASSING = "passing";
    public static final String PARAMETER_LOOPBACK = "loopback";
    //ASSOCIATIONS   
    public static final String ASSOCIATIONROLE_SOURCEUNITPARAMETER = "sourceUnitParameter";
    public static final String ASSOCIATIONROLE_SOURCEPARAMETER = "sourceParameter";
    public static final String ASSOCIATIONROLE_TARGETUNITPARAMETER = "targetUnitParameter";
    public static final String ASSOCIATIONROLE_TARGETPARAMETER = "targetParameter";
    public static final String ASSOCIATIONROLE_OUTPUTPARAMETER = "outputParameter";
    public static final String ASSOCIATIONROLE_INPUTPARAMETER = "inputParameter";

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

    public static void insertMetaModelAssociations() throws ContractException {
        //TODO completar
    }
}
