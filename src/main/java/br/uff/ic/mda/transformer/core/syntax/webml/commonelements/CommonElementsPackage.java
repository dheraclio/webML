/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webml.commonelements;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.core.syntax.uml.UMLMetaModeler;
import br.uff.ic.mda.transformer.core.syntax.webml.WebMLBasicPackage;

/**
 * Modifications:
 *  Attribute 'derivation Query' renamed to 'derivationQuery'
 *  Cardinalities set to 1 when not specified
 *
 * @author Daniel
 *
 */
public abstract class CommonElementsPackage extends WebMLBasicPackage {

    public static final String MODELELEMENT = "ModelElement";
    public static final String ELEMENTWITHCOMMENT = "ElementWithComment";
    public static final String COMMENT = "Comment";
    public static final String COMMENT_BODY = "body";
    public static final String DERIVABLEELEMENT = "DerivableElement";
    public static final String DERIVATIONCONSTRAINT = "DerivationConstraint";
    public static final String DERIVATIONCONSTRAINT_DERIVATIONQUERY = "derivationQuery";
    public static final String IDENTIFIEDELEMENT = "IdentifiedElement";
    public static final String IDENTIFIEDELEMENT_ID = "id";
    public static final String ELEMENTWITHPROPERTY = "ElementWithProperty";
    public static final String NAMEDELEMENT = "NamedElement";
    public static final String PROPERTY = "Property";
    public static final String ELEMENTWITHTYPE = "ElementWithType";
    public static final String DOMAINELEMENT = "DomainElement";
    public static final String TYPE = "Type";
    public static final String WEBMLTYPE = "WebMLType";
    public static final String DOMAIN = "Domain";

    public static void insertMetaModel() throws ContractException {
        manager.insertClass(MODELELEMENT);
        manager.insertClass(ELEMENTWITHCOMMENT);
        manager.insertClass(COMMENT);

        manager.insertClass(DERIVABLEELEMENT);
        manager.insertClass(DERIVATIONCONSTRAINT);

        manager.insertClass(IDENTIFIEDELEMENT);
        manager.insertClass(ELEMENTWITHPROPERTY);
        manager.insertClass(NAMEDELEMENT);
        manager.insertClass(PROPERTY);

        manager.insertClass(ELEMENTWITHTYPE);
        manager.insertClass(DOMAINELEMENT);
        manager.insertClass(TYPE);
        manager.insertClass(WEBMLTYPE);
        manager.insertClass(DOMAIN);

        manager.insertGeneralization(ELEMENTWITHCOMMENT, MODELELEMENT);
        manager.insertGeneralization(DERIVABLEELEMENT, MODELELEMENT);
        manager.insertGeneralization(IDENTIFIEDELEMENT, MODELELEMENT);
        manager.insertGeneralization(ELEMENTWITHPROPERTY, MODELELEMENT);
        manager.insertGeneralization(NAMEDELEMENT, MODELELEMENT);
        manager.insertGeneralization(ELEMENTWITHTYPE, MODELELEMENT);

        manager.insertGeneralization(IDENTIFIEDELEMENT, PROPERTY);
        manager.insertGeneralization(NAMEDELEMENT, PROPERTY);

        manager.insertGeneralization(NAMEDELEMENT, DOMAINELEMENT);

        manager.insertGeneralization(WEBMLTYPE, TYPE);
        manager.insertGeneralization(DOMAIN, TYPE);

        insertMetamodelAttributes();
        insertMetamodelAssociations();

    }

    private static void insertMetamodelAttributes() throws ContractException {

        manager.insertAttribute(COMMENT, COMMENT_BODY, TYPE_STRING);

        manager.insertAttribute(DERIVATIONCONSTRAINT, DERIVATIONCONSTRAINT_DERIVATIONQUERY, TYPE_STRING);

        //manager.insertAttribute(IDENTIFIEDELEMENT, IDENTIFIEDELEMENT_ID, TYPE_IDENTIFIER);
    }

    private static void insertMetamodelAssociations() throws ContractException {

        manager.insertAssociation(ELEMENTWITHCOMMENT, ROLE_OWNER, CARD_1, CARD_0_N, ROLE_ELEMENT, COMMENT);

        manager.insertAssociation(DERIVABLEELEMENT, ROLE_OWNER, CARD_1, CARD_0_1, ROLE_ELEMENT, DERIVATIONCONSTRAINT);

        manager.insertAssociation(ELEMENTWITHPROPERTY, ROLE_OWNER, CARD_1, CARD_0_N, ROLE_ELEMENT, PROPERTY);

        manager.insertAssociation(ELEMENTWITHTYPE, ROLE_OWNER, CARD_1, CARD_1, ROLE_ELEMENT, TYPE);

        manager.insertAssociation(DOMAIN, ROLE_OWNER, CARD_1, CARD_1_N, ROLE_ELEMENT, DOMAINELEMENT);
    }

    public static void createSpecification() throws ContractException {
        
        manager.insertObject(UMLMetaModeler.DATATYPE, TYPE_IDENTIFIER);
        manager.insertValue(UMLMetaModeler.DATATYPE, "name", TYPE_IDENTIFIER, "");

    }
}
