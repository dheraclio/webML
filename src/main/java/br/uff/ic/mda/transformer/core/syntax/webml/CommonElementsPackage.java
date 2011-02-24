/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webml;

import br.uff.ic.mda.tclib.ContractException;

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
    public static final String ROLE_ASSOCIATION = "association";
    public static final String ROLE_ASSOCIATIONENDS = "associationEnds";
    public static final String ROLE_CLASS = "class";
    public static final String ROLE_CLASSES = "classes";
    public static final String ROLE_CLASSIFIER = "classifier";
    public static final String ROLE_ELEMENTTYPE = "elementType";
    public static final String ROLE_FEATURE = "feature";
    public static final String ROLE_IMPLEMENTEDINTERFACES = "implementedInterfaces";
    public static final String ROLE_INHERITEDBY = "inheritedBy";
    public static final String ROLE_INHERITSFROM = "inheritsFrom";
    public static final String ROLE_OPERATION = "operation";
    public static final String ROLE_OTHEREND = "otherEnd";
    public static final String ROLE_OTHERS = "others";
    public static final String ROLE_PARAMETER = "parameter";
    public static final String ROLE_SETA = "setA";
    public static final String ROLE_TYPES = "types";
    public static final String ATTR_COMPOSITION = "composition";
    public static final String ATTR_LOWER = "lower";
    public static final String ATTR_NAME = "name";
    public static final String ATTR_UPPER = "upper";
    public static final String ATTR_VISIBILITY = "visibility";
    /**
     *
     */
    public static final String TYPE = PREFIX + "Type";
    public static final String TYPENAME_BOOLEAN = "Boolean";
    public static final String TYPENAME_DATE = "Date";
    public static final String TYPENAME_INTEGER = "Integer";
    public static final String TYPENAME_STRING = "String";
    public static final String TYPENAME_VOID = "void";
    /**
     *
     */
    public static final String WEBMLTYPE = PREFIX + "WebMLType";
    /**
     *
     */
    public static final String DOMAIN = PREFIX + "Domain";
    /**
     *
     */
    public static final String WEBML_IDENTIFIERTYPE = PREFIX + "IdentifierType";

    //--------------------------------------------------------------------------
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
    
    public static final String UMLDOUBLE = UMLPREFIX + "UMLDate";
    public static final String UMLREAL = UMLPREFIX + "UMLDate";


    //Java Profile
    private static final String JAVAPREFIX = "WEBMLJAVA_";
    public static final String JAVADATE = JAVAPREFIX + "UMLDate";
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

        manager.insertAttribute(IDENTIFIEDELEMENT, IDENTIFIEDELEMENT_ID, TYPE_STRING);
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
        insertType(UMLINTEGER, TYPENAME_INTEGER);
        insertType(UMLSTRING, TYPENAME_STRING);
        insertType(UMLBOOLEAN, TYPENAME_BOOLEAN);
        insertType(JAVADATE, TYPENAME_DATE);

        //Java Profile
        insertType(JAVAVOID, TYPENAME_VOID);

        //WebML Profile
        insertType(WEBML_IDENTIFIERTYPE, TYPENAME_STRING);
    }

    /**
     *
     * @param identifier
     * @param type
     * @throws ContractException
     */
    protected static void insertType(String identifier, String type) throws ContractException {        
        manager.insertObject(UMLDATATYPE, identifier);
        manager.insertValue(UMLDATATYPE, ATTR_NAME, identifier, type);
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

        manager.insertAttribute(UMLMODELELEMENT, ATTR_NAME, TYPENAME_STRING);
        manager.insertAttribute(UMLFEATURE, ATTR_VISIBILITY, TYPENAME_STRING);
        manager.insertAttribute(UMLASSOCIATIONEND, ATTR_LOWER, TYPENAME_STRING);
        manager.insertAttribute(UMLASSOCIATIONEND, ATTR_UPPER, TYPENAME_STRING);
        manager.insertAttribute(UMLASSOCIATIONEND, ATTR_COMPOSITION, TYPENAME_BOOLEAN);

        manager.insertAssociation(UMLASSOCIATIONEND, ROLE_OTHEREND, CARD_0_1, CARD_0_N, ROLE_OTHERS, UMLASSOCIATIONEND);
        manager.insertAssociation(UMLASSOCIATIONEND, ROLE_ASSOCIATIONENDS, CARD_1_N, CARD_1, ROLE_ASSOCIATION, UMLASSOCIATION);
        manager.insertAssociation(UMLOPERATION, ROLE_OPERATION, CARD_1, CARD_N, ROLE_PARAMETER, UMLPARAMETER);
        manager.insertAssociation(UMLCLASS, ROLE_CLASS, CARD_0_1, CARD_N, ROLE_FEATURE, UMLFEATURE);
        manager.insertAssociation(UMLCLASS, ROLE_CLASSES, CARD_N, CARD_N, ROLE_IMPLEMENTEDINTERFACES, UMLINTERFACE);
        manager.insertAssociation(UMLCLASS, ROLE_INHERITSFROM, CARD_N, CARD_N, ROLE_INHERITEDBY, UMLCLASS);
        manager.insertAssociation(UMLCLASSIFIER, ROLE_CLASSIFIER, CARD_0_1, CARD_N, ROLE_TYPES, UMLTYPED);
        manager.insertAssociation(UMLUMLSET, ROLE_SETA, CARD_0_N, CARD_1, ROLE_ELEMENTTYPE, UMLCLASSIFIER);
    }
}
