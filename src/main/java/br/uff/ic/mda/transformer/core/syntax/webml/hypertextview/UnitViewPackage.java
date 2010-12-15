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
public class UnitViewPackage extends WebMLBasicPackage {

    /**
     *
     */
    public static final String UNIT = PREFIX + "Unit";

    /**
     *
     */
    public static final String CONTENTUNIT = PREFIX + "ContentUnit";
    /**
     *
     */
    public static final String CONTENTUNIT_TYPE = "type";
    /**
     *
     */
    public static final String OPERATIONUNIT = PREFIX + "OperationUnit";
    /**
     *
     */
    public static final String OPERATIONUNIT_SECURE = "secure";
    /**
     *
     */
    public static final String GETUNIT = PREFIX + "GetUnit";


    /**
     *
     */
    public static final String ROLE_SOURCEUNIT = "sourceUnit";
    /**
     *
     */
    public static final String ROLE_SOURCEUNITOF = "sourceUnitOf";
    /**
     *
     */
    public static final String ROLE_TARGETPAGE = "targetUnit";
    /**
     *
     */
    public static final String ROLE_TARGETPAGEOF = "targetUnitOf";

    /**
     *
     * @throws ContractException
     */
    public static void insertMetaModel() throws ContractException{
        manager.insertClass(UNIT);
        manager.insertClass(CONTENTUNIT);
        manager.insertClass(OPERATIONUNIT);
        manager.insertClass(GETUNIT);

        manager.insertGeneralization(OPERATIONUNIT,UNIT);
        manager.insertGeneralization(OPERATIONUNIT,ServiceViewPackage.PORTELEMENT);
        manager.insertGeneralization(OPERATIONUNIT,AreaViewPackage.AREAELEMENT);
        manager.insertGeneralization(OPERATIONUNIT,SiteViewPackage.SITEVIEWELEMENT);

        manager.insertGeneralization(CONTENTUNIT,UNIT);
        manager.insertGeneralization(CONTENTUNIT,PageViewPackage.PAGEELEMENT);

        manager.insertGeneralization(GETUNIT,UNIT);

        insertMetaModelAttributes();        
    }

    private static void insertMetaModelAttributes() throws ContractException{
        manager.insertAttribute(CONTENTUNIT, CONTENTUNIT_TYPE, TYPE_STRING);
        manager.insertAttribute(OPERATIONUNIT, OPERATIONUNIT_SECURE, TYPE_BOOLEAN);
    }

    /**
     *
     * @throws ContractException
     */
    public static void insertMetaModelAssociations() throws ContractException{
        
        manager.insertAssociation(UNIT, ROLE_SOURCEUNIT, CARD_1, CARD_0_N, ROLE_SOURCEUNITOF, LinkViewPackage.LINK);
        manager.insertAssociation(UNIT, ROLE_TARGETPAGE, CARD_1, CARD_0_N, ROLE_TARGETPAGEOF, LinkViewPackage.LINK);
    }


}
