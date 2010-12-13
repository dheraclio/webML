/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webml.dataview;

import br.uff.ic.mda.transformer.core.syntax.webml.commonelements.CommonElementsPackage;
import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.core.syntax.webml.WebMLBasicPackage;

/**
 *  Modifications:
 *      Attribute 'content Type' renamed to 'contentType'
 *      Attribute 'duration' ignored since has no type
 *      Cadinalities set to 1 when not specified
 * @author Daniel
 */
public abstract class DataViewPackage extends WebMLBasicPackage {

    public static final String DATAMODEL = PREFIX + "DataModel";
    public static final String DATAMODELELEMENT = PREFIX + "DataModelElement";
    public static final String RELATIONSHIP = PREFIX + "Relationship";
    public static final String RELATIONSHIPROLE = PREFIX + "RelationshipRole";
    public static final String RELATIONSHIPROLE_MINCARD = "minCard";
    public static final String RELATIONSHIPROLE_MAXCARD = "maxCard";
    public static final String ENTITY = PREFIX + "Entity";
    public static final String ENTITY_DURATION = "duration";
    public static final String ATTRIBUTE = PREFIX + "Attribute";
    public static final String ATTRIBUTE_CONTENTTYPE = "contentType";
    
    //Roles
    public static final String ROLE_ATTRIBUTE = "attribute";
    public static final String ROLE_ATTRIBUTEOF = "attributeOf";

    public static void insertMetaModel() throws ContractException {
        manager.insertClass(DATAMODEL);
        manager.insertClass(DATAMODELELEMENT);
        manager.insertClass(RELATIONSHIP);
        manager.insertClass(RELATIONSHIPROLE);
        manager.insertClass(ENTITY);
        manager.insertClass(ATTRIBUTE);

        manager.insertGeneralization(DATAMODEL, CommonElementsPackage.IDENTIFIEDELEMENT);

        manager.insertGeneralization(DATAMODELELEMENT, CommonElementsPackage.IDENTIFIEDELEMENT);
        manager.insertGeneralization(DATAMODELELEMENT, CommonElementsPackage.ELEMENTWITHCOMMENT);
        manager.insertGeneralization(DATAMODELELEMENT, CommonElementsPackage.ELEMENTWITHPROPERTY);
        manager.insertGeneralization(DATAMODELELEMENT, CommonElementsPackage.NAMEDELEMENT);
        manager.insertGeneralization(DATAMODELELEMENT, CommonElementsPackage.DOMAIN);


        manager.insertGeneralization(RELATIONSHIP, DATAMODELELEMENT);        
        manager.insertGeneralization(ENTITY, DATAMODELELEMENT);

        manager.insertGeneralization(ATTRIBUTE, CommonElementsPackage.ELEMENTWITHTYPE);

        //UML base types
        manager.insertGeneralization(RELATIONSHIP, CommonElementsPackage.UMLCLASS);
        manager.insertGeneralization(ENTITY, CommonElementsPackage.UMLCLASS);

        insertMetamodelAttributes();
        insertMetamodelAssociations();
    }

    private static void insertMetamodelAttributes() throws ContractException {
        manager.insertAttribute(RELATIONSHIPROLE, RELATIONSHIPROLE_MINCARD, TYPE_INTEGER);
        manager.insertAttribute(RELATIONSHIPROLE, RELATIONSHIPROLE_MAXCARD, TYPE_INTEGER);
        manager.insertAttribute(ATTRIBUTE, ATTRIBUTE_CONTENTTYPE, TYPE_STRING);
    }

    private static void insertMetamodelAssociations() throws ContractException {
        manager.insertAssociation(DATAMODEL, ROLE_OWNER, CARD_1, CARD_0_N, ROLE_ELEMENT, DATAMODELELEMENT);

        manager.insertAssociation(ENTITY, ROLE_SUB, CARD_1, CARD_0_1, ROLE_SUPER, ENTITY);

        manager.insertAssociation(ENTITY, ROLE_ATTRIBUTEOF, CARD_1_N, CARD_0_N, ROLE_ATTRIBUTE, ATTRIBUTE);

        //manager.insertAssociation("Class", "class", "0..1", "*", "feature", "Feature");
    }
}
