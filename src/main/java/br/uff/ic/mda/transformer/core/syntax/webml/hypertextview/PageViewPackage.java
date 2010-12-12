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
public class PageViewPackage extends WebMLBasicPackage {

    public static final String PAGE = "Page";
    public static final String PAGE_PROTECTED = "protected";
    public static final String PAGE_SECURE = "secure";
    public static final String PAGE_LANDMARK = "landmark";
    public static final String PAGEELEMENT = "PageElement";
    public static final String CHOREOGRAPHY = "Choreography";
    public static final String CHOREOGRAPHYSEQUENCE = "ChoreographySequence";
    public static final String CHOREOGRAPHYPROPAGATION = "ChoreographyPropagation";
   
    //ASSOCIATIONS
    public static final String ASSOCIATIONROLE_HOMEPAGE = "homePage";
    public static final String ASSOCIATIONROLE_HOMEPAGEOF = "homePageOf";
    public static final String ASSOCIATIONROLE_DEFAULTPAGE = "defaultPage";
    public static final String ASSOCIATIONROLE_DEFAULTPAGEOF = "defaultPageOf";
    public static final String ASSOCIATIONROLE_SOURCEPAGE = "sourcePage";
    public static final String ASSOCIATIONROLE_SOURCEPAGEOF = "sourcePageOf";
    public static final String ASSOCIATIONROLE_TARGETPAGE = "targetPage";
    public static final String ASSOCIATIONROLE_TARGETPAGEOF = "targetPageOf";

    public static final String ASSOCIATIONROLE_CONTENT = "content";
    public static final String ASSOCIATIONROLE_CONTENTOF = "contentOf";

    public static final String ASSOCIATIONROLE_LINK = "link";
    public static final String ASSOCIATIONROLE_LINKOF = "linkOf";

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

    private static void insertMetaModelAttributes() throws ContractException {
        manager.insertAttribute(PAGE, PAGE_SECURE, TYPE_BOOLEAN);
        manager.insertAttribute(PAGE, PAGE_PROTECTED, TYPE_BOOLEAN);
        manager.insertAttribute(PAGE, PAGE_LANDMARK, TYPE_BOOLEAN);
    }

    public static void insertMetaModelAssociations() throws ContractException {
        manager.insertAssociation(PAGE, ASSOCIATIONROLE_HOMEPAGE, CARD_1, CARD_1, ASSOCIATIONROLE_HOMEPAGEOF, SiteViewPackage.SITEVIEW);

        //manager.insertAssociation(PAGE, ASSOCIATIONROLE_DEFAULTPAGE, CARD_0_1, CARD_1, ASSOCIATIONROLE_DEFAULTPAGEOF, PAGE);
        manager.insertAssociation(PAGE, ASSOCIATIONROLE_DEFAULTPAGE, CARD_0_1, CARD_1, ASSOCIATIONROLE_DEFAULTPAGEOF, AreaViewPackage.AREA);

        manager.insertAssociation(PAGE, ASSOCIATIONROLE_SOURCEPAGE, CARD_1, CARD_0_N, ASSOCIATIONROLE_SOURCEPAGEOF, LinkViewPackage.LINK);
        manager.insertAssociation(PAGE, ASSOCIATIONROLE_TARGETPAGE, CARD_1, CARD_0_N, ASSOCIATIONROLE_TARGETPAGEOF, LinkViewPackage.LINK);

        manager.insertAssociation(CHOREOGRAPHY, ASSOCIATIONROLE_LINK, CARD_1, CARD_1_N, ASSOCIATIONROLE_LINKOF, LinkViewPackage.LINK);
        manager.insertAssociation(CHOREOGRAPHY, ASSOCIATIONROLE_CONTENT, CARD_1, CARD_1_N, ASSOCIATIONROLE_CONTENTOF, UnitViewPackage.CONTENTUNIT);
    }
}
