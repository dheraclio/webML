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
public class AreaViewPackage extends WebMLBasicPackage {

    public static final String AREA = PREFIX + "Area";
    public static final String AREA_SECURE = "secure";
    public static final String AREA_LOCALIZED = "localized";
    public static final String AREA_LANDMARK = "landmark";
    public static final String AREA_PROTECTED = "protected";

    public static final String AREAELEMENT = PREFIX + "AreaElement";

    public static final String ROLE_DEFAULTAREA = "defaultArea";
    public static final String ROLE_DEFAULTAREAOF = "defaultAreaOf";

    public static void insertMetaModel() throws ContractException{

        manager.insertClass(AREA);
        manager.insertClass(AREAELEMENT);

        manager.insertGeneralization(AREA,AREAELEMENT);

        insertMetaModelAttributes();        
    }

    private static void insertMetaModelAttributes() throws ContractException{

        manager.insertAttribute(AREA, AREA_SECURE, TYPE_BOOLEAN);
        manager.insertAttribute(AREA, AREA_LOCALIZED, TYPE_BOOLEAN);
        manager.insertAttribute(AREA, AREA_LANDMARK, TYPE_BOOLEAN);
        manager.insertAttribute(AREA, AREA_PROTECTED, TYPE_BOOLEAN);      
    }

    public static void insertMetaModelAssociations() throws ContractException{
        manager.insertAssociation(AREA, ROLE_OWNER, CARD_0_N, CARD_0_1, ROLE_ELEMENT, AREAELEMENT);
        
        manager.insertAssociation(AREA, ROLE_DEFAULTAREAOF, CARD_0_1, CARD_0_1, ROLE_DEFAULTAREA, SiteViewPackage.SITEVIEW);
        //manager.insertAssociation(AREA, ROLE_DEFAULTAREAOF, CARD_0_1, CARD_0_1, ROLE_DEFAULTAREA, AREA);
    }
}
