/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.ic.mda.transformer.core.syntax.webml.hypertextview;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.syntax.webml.WebMLBasicPackage;

/**
 *
 * @author Daniel
 */
public class SiteViewPackage extends WebMLBasicPackage {

    public static final String SITEVIEW = PREFIX + "SiteView";
    public static final String SITEVIEW_PROTECTED = "protected";
    public static final String SITEVIEW_SECURE = "secure";
    public static final String SITEVIEW_LOCALIZED = "localized";

    public static final String SITEVIEWELEMENT = PREFIX + "SiteViewElement";

    public static void insertMetaModel() throws ContractException{
        
        manager.insertClass(SITEVIEW);
        manager.insertClass(SITEVIEWELEMENT);

        manager.insertGeneralization(SITEVIEW,CorePackage.HYPERTEXTMODELELEMENT);

        insertMetaModelAttributes();        
    }

    private static void insertMetaModelAttributes() throws ContractException{
        
        manager.insertAttribute(SITEVIEW, SITEVIEW_PROTECTED, TYPE_BOOLEAN);
        manager.insertAttribute(SITEVIEW, SITEVIEW_SECURE, TYPE_BOOLEAN);
        manager.insertAttribute(SITEVIEW, SITEVIEW_LOCALIZED, TYPE_BOOLEAN);
    }

    public static void insertMetaModelAssociations() throws ContractException{
        manager.insertAssociation(SITEVIEW, ROLE_OWNER, CARD_0_N, CARD_1, ROLE_ELEMENT, SITEVIEWELEMENT);
    }

}
