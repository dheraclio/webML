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
public class ServiceViewPackage extends WebMLBasicPackage {

    public static final String SERVICEVIEW = PREFIX + "ServiceView";
    public static final String SERVICEVIEW_SECURE = "secure";

    public static final String PORT = PREFIX + "Port";
    public static final String PORT_SECURE = "secure";

    public static final String PORTELEMENT = PREFIX + "PortElement";

    public static void insertMetaModel() throws ContractException{
        manager.insertClass(SERVICEVIEW);
        manager.insertClass(PORT);
        manager.insertClass(PORTELEMENT);

        manager.insertGeneralization(SERVICEVIEW,CorePackage.HYPERTEXTMODELELEMENT);

        insertMetaModelAttributes();        
    }

    private static void insertMetaModelAttributes() throws ContractException{

        manager.insertAttribute(SERVICEVIEW, SERVICEVIEW_SECURE, TYPE_BOOLEAN);
        manager.insertAttribute(PORT, PORT_SECURE, TYPE_BOOLEAN);
    }

    public static void insertMetaModelAssociations() throws ContractException{
        manager.insertAssociation(PORT, ROLE_OWNER, CARD_0_N, CARD_1, ROLE_ELEMENT, PORTELEMENT);
    }

}
