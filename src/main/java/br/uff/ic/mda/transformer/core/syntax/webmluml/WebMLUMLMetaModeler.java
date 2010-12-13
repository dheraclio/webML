/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webmluml;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.UMLDomain;
import br.uff.ic.mda.transformer.WebMLDomain;
import br.uff.ic.mda.transformer.WebMLUMLDomain;
import br.uff.ic.mda.transformer.core.syntax.uml.UMLMetaModeler;
import br.uff.ic.mda.transformer.core.syntax.webml.commonelements.CommonElementsPackage;
import br.uff.ic.mda.transformer.core.syntax.webml.dataview.DataViewPackage;
import br.uff.ic.mda.transformer.core.syntax.webmluml.transformationrules.TransformationRule;
import br.uff.ic.mda.transformer.core.syntax.webmluml.transformationrules.TransformationRuleFactory;
import br.uff.ic.mda.transformer.core.util.BasicModeler;
import java.util.LinkedList;

/**
 * Modifications:
 *  Attribute 'derivation Query' renamed to 'derivationQuery'
 *  Cadinalities set to 1 when not specified
 *
 * @author Daniel
 *
 */
public abstract class WebMLUMLMetaModeler extends BasicModeler {

    //WebMLUML prefix
    public static final String PREFIX = "WebMLUML_";
    //WebML types to UML types
    public static final String TR_WEBMLDATATYPE2UMLDATATYPE = PREFIX + "WebMLDataType2UMLDataType";
    public static final String ROLE_WEBML = "webml";
    public static final String ROLE_UML = "uml";
    public static final String ROLE_TRANSFORM = "transformer";

    //WebML Attribute to UML Attribute
    public static final String TR_ATTRIBUTE2ATTRIBUTE = PREFIX + "WebMLAttribute2UMLAttribute";
    //WebML Entity to UML Class
    public static final String TR_ENTITY2CLASS = PREFIX + "WebMLEntity2UMLClass";    
    //WebML Relationship to UML Association
    public static final String TR_RELATIONSHIP2ASSOCIATION = PREFIX + "WebMLRelationshipToAssociation";

    public static void applyRules(WebMLUMLDomain aThis) throws ContractException {

        //First transform
        for (TransformationRule tr : getRules(aThis)) {
            tr.transform();
        }

        //Then link
        for (TransformationRule tr : getRules(aThis)) {
            tr.link();
        }
    }

    public static void insertMetaModel() throws ContractException {
        insertMetaModelClasses();
        insertMetaModelAssociations();
    }

    private static void insertMetaModelClasses() throws ContractException {
        final String name = "name";
        final String nameType = "String";

        manager.insertClass(TR_WEBMLDATATYPE2UMLDATATYPE);
        manager.insertAttribute(TR_WEBMLDATATYPE2UMLDATATYPE, name, nameType);

        manager.insertClass(TR_ATTRIBUTE2ATTRIBUTE);
        manager.insertAttribute(TR_ATTRIBUTE2ATTRIBUTE, name, nameType);

        manager.insertClass(TR_ENTITY2CLASS);
        manager.insertAttribute(TR_ENTITY2CLASS, name, nameType);       

        manager.insertClass(TR_RELATIONSHIP2ASSOCIATION);
        manager.insertAttribute(TR_RELATIONSHIP2ASSOCIATION, name, nameType);
    }

    private static void insertMetaModelAssociations() throws ContractException {
        //WebML Attribute to UMLType
        manager.insertAssociation(UMLMetaModeler.DATATYPE, ROLE_UML, CARD_1, CARD_0_1, ROLE_TRANSFORM, TR_WEBMLDATATYPE2UMLDATATYPE);
        manager.insertAssociation(CommonElementsPackage.UMLDATATYPE, ROLE_WEBML, CARD_1, CARD_0_1, ROLE_TRANSFORM, TR_WEBMLDATATYPE2UMLDATATYPE);

        //WebML Attribute to UMLAttribute
        manager.insertAssociation(UMLMetaModeler.ATTRIBUTE, ROLE_UML, CARD_1, CARD_0_1, ROLE_TRANSFORM, TR_ATTRIBUTE2ATTRIBUTE);
        manager.insertAssociation(CommonElementsPackage.UMLATTRIBUTE, ROLE_WEBML, CARD_1, CARD_0_1, ROLE_TRANSFORM, TR_ATTRIBUTE2ATTRIBUTE);

        //WebML Entity to UMLClass
        manager.insertAssociation(UMLMetaModeler.CLASS, ROLE_UML, CARD_1, CARD_0_1, ROLE_TRANSFORM, TR_ENTITY2CLASS);
        manager.insertAssociation(DataViewPackage.ENTITY, ROLE_WEBML, CARD_1, CARD_0_1, ROLE_TRANSFORM, TR_ENTITY2CLASS);        

        //WebML Relationship to UML
        manager.insertAssociation(DataViewPackage.RELATIONSHIP, ROLE_WEBML, CARD_1, CARD_0_1, ROLE_TRANSFORM, TR_RELATIONSHIP2ASSOCIATION);
        manager.insertAssociation(UMLMetaModeler.ASSOCIATION, ROLE_UML, CARD_1, CARD_0_1, ROLE_TRANSFORM, TR_RELATIONSHIP2ASSOCIATION);
    }

    private static LinkedList<TransformationRule> getRules(WebMLUMLDomain domain) throws ContractException {
        return TransformationRuleFactory.getRules(domain);
    }
}
