/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webml.commonelements;

import br.uff.ic.mda.tclib.ContractException;
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

    /**
     *
     */
    public static final String MODELELEMENT = PREFIX + "ModelElement";
    /**
     *
     */
    public static final String ELEMENTWITHCOMMENT = PREFIX + "ElementWithComment";
    /**
     *
     */
    public static final String COMMENT = PREFIX + "Comment";
    /**
     *
     */
    public static final String COMMENT_BODY = "body";
    /**
     *
     */
    public static final String DERIVABLEELEMENT = PREFIX + "DerivableElement";
    /**
     *
     */
    public static final String DERIVATIONCONSTRAINT = PREFIX + "DerivationConstraint";
    /**
     *
     */
    public static final String DERIVATIONCONSTRAINT_DERIVATIONQUERY = "derivationQuery";
    /**
     *
     */
    public static final String IDENTIFIEDELEMENT = PREFIX + "IdentifiedElement";
    /**
     *
     */
    public static final String IDENTIFIEDELEMENT_ID = "id";
    /**
     *
     */
    public static final String ELEMENTWITHPROPERTY = PREFIX + "ElementWithProperty";
    /**
     *
     */
    public static final String NAMEDELEMENT = PREFIX + "NamedElement";
    /**
     *
     */
    public static final String PROPERTY = PREFIX + "Property";
    /**
     *
     */
    public static final String ELEMENTWITHTYPE = PREFIX + "ElementWithType";
    /**
     *
     */
    public static final String DOMAINELEMENT = PREFIX + "DomainElement";
    /**
     *
     */
    public static final String TYPE = PREFIX + "Type";
    /**
     *
     */
    public static final String WEBMLTYPE = PREFIX + "WebMLType";
    /**
     *
     */
    public static final String DOMAIN = PREFIX + "Domain";
    //UML Base
    private static final String UMLPREFIX = "WEBMLUML_";
    /**
     * 
     */
    public static final String UMLMODELELEMENT = UMLPREFIX + "ModelElement";
    /**
     *
     */
    public static final String UMLCLASSIFIER = UMLPREFIX + "Classifier";
    /**
     *
     */
    public static final String UMLTYPED = UMLPREFIX + "Typed";
    /**
     *
     */
    public static final String UMLDATATYPE = UMLPREFIX + "DataType";
    /**
     *
     */
    public static final String UMLUMLSET = UMLPREFIX + "UMLSet";
    /**
     *
     */
    public static final String UMLCLASS = UMLPREFIX + "Class";
    /**
     *
     */
    public static final String UMLINTERFACE = UMLPREFIX + "Interface";
    /**
     *
     */
    public static final String UMLASSOCIATIONCLASS = UMLPREFIX + "AssociationClass";
    /**
     *
     */
    public static final String UMLFEATURE = UMLPREFIX + "Feature";
    /**
     *
     */
    public static final String UMLASSOCIATIONEND = UMLPREFIX + "AssociationEnd";
    /**
     *
     */
    public static final String UMLASSOCIATION = UMLPREFIX + "Association";
    /**
     *
     */
    public static final String UMLATTRIBUTE = UMLPREFIX + "Attribute";
    /**
     *
     */
    public static final String UMLOPERATION = UMLPREFIX + "Operation";
    /**
     *
     */
    public static final String UMLPARAMETER = UMLPREFIX + "Parameter";
    public static final String UMLINTEGER = UMLPREFIX + "UMLInteger";
    public static final String UMLSTRING = UMLPREFIX + "UMLString";
    public static final String UMLBOOLEAN = UMLPREFIX + "UMLBoolean";
    public static final String UMLDATE = UMLPREFIX + "UMLDate";

    //Java Profile
    private static final String JAVAPREFIX = "WEBMLJAVA_";
    public static final String JAVAVOID = JAVAPREFIX +"UMLVoid";

    /**
     *
     * @throws ContractException
     */
    public static void insertMetaModel() throws ContractException {
        insertUMLBaseModel();
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

    /**
     *
     * @throws ContractException
     */
    public static void createSpecification() throws ContractException {
        //UML Standart Elements (from XMIParser)
        insertType(UMLINTEGER, "Integer");
        insertType(UMLSTRING, "String");
        insertType(UMLBOOLEAN, "Boolean");
        insertType(UMLDATE, "Date");

        //Java Profile
        insertType(JAVAVOID, "void");

        //WebML Profile
        insertType(TYPE_IDENTIFIER, "String");
    }

    /**
     *
     * @param identifier
     * @param type
     * @throws ContractException
     */
    protected static void insertType(String identifier, String type) throws ContractException {
        final String attName = "name";
        manager.insertObject(UMLDATATYPE, identifier);
        manager.insertValue(UMLDATATYPE, attName, identifier, type);
    }

    private static void insertUMLBaseModel() throws ContractException {

        manager.insertClass(UMLMODELELEMENT);
        manager.insertClass(UMLCLASSIFIER);
        manager.insertClass(UMLTYPED);
        manager.insertClass(UMLDATATYPE);
        manager.insertClass(UMLUMLSET);
        manager.insertClass(UMLCLASS);
        manager.insertClass(UMLINTERFACE);
        manager.insertClass(UMLASSOCIATIONCLASS);
        manager.insertClass(UMLFEATURE);

        manager.insertClass(UMLASSOCIATIONEND);
        manager.insertClass(UMLASSOCIATION);
        manager.insertClass(UMLATTRIBUTE);
        manager.insertClass(UMLOPERATION);
        manager.insertClass(UMLPARAMETER);
        manager.insertGeneralization(UMLASSOCIATION, UMLMODELELEMENT);
        manager.insertGeneralization(UMLCLASSIFIER, UMLMODELELEMENT);
        manager.insertGeneralization(UMLTYPED, UMLMODELELEMENT);
        manager.insertGeneralization(UMLDATATYPE, UMLCLASSIFIER);
        manager.insertGeneralization(UMLUMLSET, UMLDATATYPE);
        manager.insertGeneralization(UMLCLASS, UMLCLASSIFIER);
        manager.insertGeneralization(UMLINTERFACE, UMLCLASSIFIER);
        manager.insertGeneralization(UMLASSOCIATIONCLASS, UMLCLASS);
        manager.insertGeneralization(UMLASSOCIATIONCLASS, UMLASSOCIATION);
        manager.insertGeneralization(UMLFEATURE, UMLTYPED);
        manager.insertGeneralization(UMLASSOCIATIONEND, UMLFEATURE);
        manager.insertGeneralization(UMLATTRIBUTE, UMLFEATURE);
        manager.insertGeneralization(UMLOPERATION, UMLFEATURE);
        manager.insertGeneralization(UMLPARAMETER, UMLTYPED);

        manager.insertAttribute(UMLMODELELEMENT, "name", "String");
        manager.insertAttribute(UMLFEATURE, "visibility", "String");
        manager.insertAttribute(UMLASSOCIATIONEND, "lower", "String");
        manager.insertAttribute(UMLASSOCIATIONEND, "upper", "String");
        manager.insertAttribute(UMLASSOCIATIONEND, "composition", "Boolean");

        manager.insertAssociation(UMLASSOCIATIONEND, "otherEnd", CARD_0_1, CARD_0_N, "others", UMLASSOCIATIONEND);
        manager.insertAssociation(UMLASSOCIATIONEND, "associationEnds", CARD_1_N, CARD_1, "association", UMLASSOCIATION);
        manager.insertAssociation(UMLOPERATION, "operation", CARD_1, CARD_N, "parameter", UMLPARAMETER);
        manager.insertAssociation(UMLCLASS, "class", CARD_0_1, CARD_N, "feature", UMLFEATURE);
        manager.insertAssociation(UMLCLASS, "classes", CARD_N, CARD_N, "implementedInterfaces", UMLINTERFACE);
        manager.insertAssociation(UMLCLASS, "inheritsFrom", CARD_N, CARD_N, "inheritedBy", UMLCLASS);
        manager.insertAssociation(UMLCLASSIFIER, "classifier", CARD_0_1, CARD_N, "types", UMLTYPED);
        manager.insertAssociation(UMLUMLSET, "setA", CARD_0_N, CARD_1, "elementType", UMLCLASSIFIER);
    }
}
