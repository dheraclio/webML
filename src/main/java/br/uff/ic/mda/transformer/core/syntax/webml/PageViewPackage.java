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
public class PageViewPackage extends WebMLBasicPackage {

    /**
     *
     */
    public static final String PAGE = PREFIX + "Page";
    /**
     *
     */
    public static final String PAGE_PROTECTED = "protected";
    /**
     *
     */
    public static final String PAGE_SECURE = "secure";
    /**
     *
     */
    public static final String PAGE_LANDMARK = "landmark";
    /**
     *
     */
    public static final String PAGEELEMENT = PREFIX + "PageElement";
    /**
     *
     */
    public static final String CHOREOGRAPHY = PREFIX + "Choreography";
    /**
     *
     */
    public static final String CHOREOGRAPHYSEQUENCE = PREFIX + "ChoreographySequence";
    /**
     *
     */
    public static final String CHOREOGRAPHYPROPAGATION = PREFIX + "ChoreographyPropagation";
    //ASSOCIATIONS
    /**
     *
     */
    public static final String ROLE_HOMEPAGE = "homePage";
    /**
     *
     */
    public static final String ROLE_HOMEPAGEOF = "homePageOf";
    /**
     *
     */
    public static final String ROLE_DEFAULTPAGE = "defaultPage";
    /**
     *
     */
    public static final String ROLE_DEFAULTPAGEOF = "defaultPageOf";
    /**
     *
     */
    public static final String ROLE_SOURCEPAGE = "sourcePage";
    /**
     *
     */
    public static final String ROLE_SOURCEPAGEOF = "sourcePageOf";
    /**
     *
     */
    public static final String ROLE_TARGETPAGE = "targetPage";
    /**
     *
     */
    public static final String ROLE_TARGETPAGEOF = "targetPageOf";
    /**
     *
     */
    public static final String ROLE_CONTENT = "content";
    /**
     *
     */
    public static final String ROLE_CONTENTOF = "contentOf";
    /**
     *
     */
    public static final String ROLE_LINK = "link";
    /**
     *
     */
    public static final String ROLE_LINKOF = "linkOf";

    /**
     *
     * @throws ContractException
     */
    public static void insertMetaModel() throws ContractException {
        manager.insertClass(PAGE);
        manager.insertClass(PAGEELEMENT);
        manager.insertClass(CHOREOGRAPHY);
        manager.insertClass(CHOREOGRAPHYSEQUENCE);
        manager.insertClass(CHOREOGRAPHYPROPAGATION);

        manager.insertGeneralization(PAGE, AreaViewPackage.AREAELEMENT);
        manager.insertGeneralization(PAGE, SiteViewPackage.SITEVIEWELEMENT);
        manager.insertGeneralization(PAGE, ServiceViewPackage.PORTELEMENT);

        manager.insertGeneralization(CHOREOGRAPHYPROPAGATION, CHOREOGRAPHY);
        manager.insertGeneralization(CHOREOGRAPHYSEQUENCE, CHOREOGRAPHY);

        insertMetaModelAttributes();
    }

    /**
     *
     * @throws ContractException
     */
    private static void insertMetaModelAttributes() throws ContractException {
        manager.insertAttribute(PAGE, PAGE_SECURE, TYPE_BOOLEAN);
        manager.insertAttribute(PAGE, PAGE_PROTECTED, TYPE_BOOLEAN);
        manager.insertAttribute(PAGE, PAGE_LANDMARK, TYPE_BOOLEAN);
    }

    /**
     *
     * @throws ContractException
     */
    public static void insertMetaModelAssociations() throws ContractException {
        manager.insertAssociation(PAGE, ROLE_HOMEPAGE, CARD_1, CARD_0_1, ROLE_HOMEPAGEOF, SiteViewPackage.SITEVIEW);

        //manager.insertAssociation(PAGE, ROLE_DEFAULTPAGE, CARD_0_1, CARD_1, ROLE_DEFAULTPAGEOF, PAGE);
        manager.insertAssociation(PAGE, ROLE_DEFAULTPAGE, CARD_0_1, CARD_1, ROLE_DEFAULTPAGEOF, AreaViewPackage.AREA);

        manager.insertAssociation(PAGE, ROLE_SOURCEPAGE, CARD_1, CARD_0_N, ROLE_SOURCEPAGEOF, LinkViewPackage.LINK);
        manager.insertAssociation(PAGE, ROLE_TARGETPAGE, CARD_1, CARD_0_N, ROLE_TARGETPAGEOF, LinkViewPackage.LINK);

        manager.insertAssociation(CHOREOGRAPHY, ROLE_LINK, CARD_1, CARD_1_N, ROLE_LINKOF, LinkViewPackage.LINK);
        manager.insertAssociation(CHOREOGRAPHY, ROLE_CONTENT, CARD_1, CARD_1_N, ROLE_CONTENTOF, UnitViewPackage.CONTENTUNIT);
    }
}
