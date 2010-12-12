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

    public static final String LINK = "Link";
    public static final String LINK_DEFAULTCOUPLING = "defaultCoupling";
    public static final String LINK_NEWWINDOW = "newWindow";
    public static final String NONCONTEXTUALLINK = "NonContextualLink";
    public static final String CONTEXTUALLINK = "ContextualLink";
    public static final String KOLINK = "KOLink";
    public static final String KOLINK_CODE = "code";
    public static final String OKLINK = "OkLink";
    public static final String OKLINK_CODE = "code";
    public static final String AUTOMATIC = "Automatic";
    public static final String TRANSPORT = "Transport";
    public static final String ASSOCIATIONROLE_ = "Transport";

    public static final String ASSOCIATIONROLE_LINKPARAMETER = "linkParameter";
    public static final String ASSOCIATIONROLE_LINKPARAMETEROF = "linkParameterOf";

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
        manager.insertAssociation(CONTEXTUALLINK, ASSOCIATIONROLE_LINKPARAMETER, CARD_1, CARD_1_N, ASSOCIATIONROLE_LINKPARAMETEROF, ParameterViewPackage.LINKPARAMETERCOUPLING);
    }
}
