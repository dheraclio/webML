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
    public class LinkViewPackage extends WebMLBasicPackage {

    public static final String LINK = PREFIX +"Link";
    public static final String LINK_DEFAULTCOUPLING = "defaultCoupling";
    public static final String LINK_NEWWINDOW = "newWindow";
    public static final String NONCONTEXTUALLINK = PREFIX +"NonContextualLink";
    public static final String CONTEXTUALLINK = PREFIX +"ContextualLink";
    public static final String KOLINK = PREFIX +"KOLink";
    public static final String KOLINK_CODE = "code";
    public static final String OKLINK = PREFIX +"OkLink";
    public static final String OKLINK_CODE = "code";
    public static final String AUTOMATIC = PREFIX +"Automatic";
    public static final String TRANSPORT = PREFIX +"Transport";
    
    public static final String ROLE_LINKPARAMETER = "linkParameter";
    public static final String ROLE_LINKPARAMETEROF = "linkParameterOf";

    public static void insertMetaModel() throws ContractException {
        manager.insertClass(LINK);
        manager.insertClass(NONCONTEXTUALLINK);
        manager.insertClass(CONTEXTUALLINK);
        manager.insertClass(KOLINK);
        manager.insertClass(OKLINK);
        manager.insertClass(AUTOMATIC);
        manager.insertClass(TRANSPORT);

        manager.insertGeneralization(NONCONTEXTUALLINK, LINK);
        manager.insertGeneralization(CONTEXTUALLINK, LINK);

        manager.insertGeneralization(KOLINK, CONTEXTUALLINK);
        manager.insertGeneralization(OKLINK, CONTEXTUALLINK);
        manager.insertGeneralization(AUTOMATIC, CONTEXTUALLINK);
        manager.insertGeneralization(TRANSPORT, CONTEXTUALLINK);

        insertMetaModelAttributes();
    }

    private static void insertMetaModelAttributes() throws ContractException {

        manager.insertAttribute(LINK, LINK_DEFAULTCOUPLING, TYPE_BOOLEAN);
        manager.insertAttribute(LINK, LINK_NEWWINDOW, TYPE_BOOLEAN);

        manager.insertAttribute(KOLINK, KOLINK_CODE, TYPE_STRING);
        manager.insertAttribute(OKLINK, OKLINK_CODE, TYPE_STRING);
    }

    public static void insertMetaModelAssociations() throws ContractException {
        manager.insertAssociation(CONTEXTUALLINK, ROLE_LINKPARAMETER, CARD_1, CARD_1_N, ROLE_LINKPARAMETEROF, ParameterViewPackage.LINKPARAMETERCOUPLING);
    }
}
