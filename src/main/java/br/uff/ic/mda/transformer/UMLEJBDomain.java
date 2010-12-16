package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.IValidator;
import br.uff.ic.mda.tclib.JoinedDomain;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.syntax.uml.UMLMetaModeler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Daniel
 */
public class UMLEJBDomain extends JoinedDomain<UMLDomain, EJBDomain> {

    public static final String UMLASSOCIATIONCLASSTOEJBDATACLASS = "UMLAssociationClassToEJBDataClass";
    public static final String UMLASSOCIATIONCLASSTOEJBKEYCLASS = "UMLAssociationClassToEJBKeyClass";
    public static final String UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE = "UMLAssociationEndEmEJBAssociationusingRule10";
    public static final String UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE11 = "UMLAssociationEndEmEJBAssociationusingRule11";
    public static final String UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE8 = "UMLAssociationEndToEJBDataEndusingRule8";
    public static final String UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE9 = "UMLAssociationEndToEJBDataEndusingRule9";
    public static final String UMLASSOCIATIONTOEJBDATAASSOCIATION = "UMLAssociationToEJBDataAssociation";
    public static final String UMLATTRIBUTETOEJBATTRIBUTE = "UMLAttributeToEJBAttribute";
    public static final String UMLCLASSTOEJBDATACLASS = "UMLClassToEJBDataClass";
    public static final String UMLCLASSTOEJBENTITYCOMPONENT = "UMLClassToEJBEntityComponent";
    public static final String UMLCLASSTOEJBKEYCLASS = "UMLClassToEJBKeyClass";
    public static final String UMLDATATYPETOEJBDATATYPE = "UMLDataTypeToEJBDataType";
    public static final String UMLOPERATIONTOBUSINESSMETHOD = "UMLOperationToBusinessMethod";
    public static final String UMLPARAMETERTOEJBPARAMETER = "UMLParameterToEJBParameter";
    public static final String UMLSETTOEJBSET = "UMLSetToEJBSet";

    /**
     *
     * @param sourceDomain
     * @param targetDomain
     * @throws ContractException
     */
    public UMLEJBDomain(UMLDomain sourceDomain, EJBDomain targetDomain) throws ContractException {
        super(sourceDomain, targetDomain,
                new ArrayList<IValidator>(
                Arrays.asList(
                new UMLEJBInvariantValidator())));
    }

    /**
     *
     * @param sourceDomain
     * @param targetDomain
     * @param validators
     * @throws ContractException
     */
    public UMLEJBDomain(UMLDomain sourceDomain, EJBDomain targetDomain, Collection<IValidator> validators) throws ContractException {
        super(sourceDomain, targetDomain, validators);
    }

    @Override
    protected void createLocalMetamodel() throws ContractException {
        insertMetamodelClasses();
        insertMetamodelAttributes();
        insertMetamodelAssociations();
        insertMetamodelOperations();
    }

    @Override
    public void createSpecificationOfCurrentDiagram() throws ContractException {
    }

    /**
     *
     * @throws ContractException
     */
    public void insertMetamodelClasses() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertClass(UMLDATATYPETOEJBDATATYPE);                      // Regra Minha
        manager.insertClass(UMLCLASSTOEJBKEYCLASS);                         // Regra 1
        manager.insertClass(UMLASSOCIATIONCLASSTOEJBKEYCLASS);              // Regra 2
        manager.insertClass(UMLOPERATIONTOBUSINESSMETHOD);                  // Regra 12
        manager.insertClass(UMLPARAMETERTOEJBPARAMETER);                    // Regra 13
        manager.insertClass(UMLATTRIBUTETOEJBATTRIBUTE);                    // Regra 7
        manager.insertClass(UMLCLASSTOEJBDATACLASS);                        // Regra 4
        manager.insertClass(UMLASSOCIATIONTOEJBDATAASSOCIATION);            // Regra 5
        manager.insertClass(UMLASSOCIATIONCLASSTOEJBDATACLASS);             // Regra 6
        manager.insertClass(UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE8);       // Regra 8
        manager.insertClass(UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE9);       // Regra 9
        manager.insertClass(UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE);  // Regra 10
        manager.insertClass(UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE11);  // Regra 11
        manager.insertClass(UMLCLASSTOEJBENTITYCOMPONENT);                  // Regra 3
        manager.insertClass(UMLSETTOEJBSET);                          // Minha regra
    }

    /**
     *
     * @throws ContractException
     */
    public void insertMetamodelAttributes() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertAttribute(UMLDATATYPETOEJBDATATYPE, "name", "String");
        manager.insertAttribute(UMLCLASSTOEJBKEYCLASS, "name", "String");
        manager.insertAttribute(UMLASSOCIATIONCLASSTOEJBKEYCLASS, "name", "String");
        manager.insertAttribute(UMLOPERATIONTOBUSINESSMETHOD, "name", "String");
        manager.insertAttribute(UMLPARAMETERTOEJBPARAMETER, "name", "String");
        manager.insertAttribute(UMLATTRIBUTETOEJBATTRIBUTE, "name", "String");
        manager.insertAttribute(UMLCLASSTOEJBDATACLASS, "name", "String");
        manager.insertAttribute(UMLASSOCIATIONTOEJBDATAASSOCIATION, "name", "String");
        manager.insertAttribute(UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE8, "name", "String");
        manager.insertAttribute(UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE9, "name", "String");
        manager.insertAttribute(UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE, "name", "String");
        manager.insertAttribute(UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE11, "name", "String");
        manager.insertAttribute(UMLCLASSTOEJBENTITYCOMPONENT, "name", "String");
        manager.insertAttribute(UMLASSOCIATIONCLASSTOEJBDATACLASS, "name", "String");
        manager.insertAttribute(UMLSETTOEJBSET, "name", "String");
    }

    /**
     *
     * @throws ContractException
     */
    public void insertMetamodelAssociations() throws ContractException {
        ModelManager manager = ModelManager.instance();

        // Associacoes UMLDataTypeToEJBDataType
        manager.insertAssociation(UMLMetaModeler.DATATYPE, "dataType", "1", "0..1", "transformerToEjbDataType", UMLDATATYPETOEJBDATATYPE);
        manager.insertAssociation("EJBDataType", "ejbDataType", "1", "0..1", "transformerToEjbDataType", UMLDATATYPETOEJBDATATYPE);

        // Associacoes UMLClassToEJBKeyClass
        manager.insertAssociation(UMLMetaModeler.CLASS, "class", "1", "0..1", "transformerToClass", UMLCLASSTOEJBKEYCLASS);
        manager.insertAssociation("EJBAttribute", "id", "1", "0..1", "transformerToClass", UMLCLASSTOEJBKEYCLASS);
        manager.insertAssociation("EJBKeyClass", "keyClass", "1", "0..1", "transformerToClass", UMLCLASSTOEJBKEYCLASS);

        // Associacoes UMLAssociationClassToEJBKeyClass
        manager.insertAssociation(UMLMetaModeler.ASSOCIATIONCLASS, "associationClass", "1", "0..1", "transformerToAssociationClass", UMLASSOCIATIONCLASSTOEJBKEYCLASS);
        manager.insertAssociation("EJBAttribute", "id", "2", "0..1", "transformerToAssociationClass", UMLASSOCIATIONCLASSTOEJBKEYCLASS);
        manager.insertAssociation("EJBKeyClass", "keyClass", "1", "0..1", "transformerToAssociationClass", UMLASSOCIATIONCLASSTOEJBKEYCLASS);

        // Associacoes UMLOperationToBusinessMethod
        manager.insertAssociation("Operation", "operation", "1", "0..1", "transformerToBusinessMethod", UMLOPERATIONTOBUSINESSMETHOD);
        manager.insertAssociation("BusinessMethod", "businessMethod", "1", "0..1", "transformerToBusinessMethod", UMLOPERATIONTOBUSINESSMETHOD);

        // Associacoes UMLParameterToEJBParameter
        manager.insertAssociation("Parameter", "parameter", "1", "0..1", "transformerToEjbParameter", UMLPARAMETERTOEJBPARAMETER);
        manager.insertAssociation("EJBParameter", "ejbParameter", "1", "0..1", "transformerToEjbParameter", UMLPARAMETERTOEJBPARAMETER);

        // Associacoes UMLAttributeToEJBAttribute
        manager.insertAssociation(UMLMetaModeler.ATTRIBUTE, "attribute", "1", "0..1", "transformerToEjbAttribute", UMLATTRIBUTETOEJBATTRIBUTE);
        manager.insertAssociation("EJBAttribute", "ejbAttribute", "1", "0..1", "transformerToEjbAttribute", UMLATTRIBUTETOEJBATTRIBUTE);

        // Associacoes UMLClassToEJBDataClass
        manager.insertAssociation(UMLMetaModeler.CLASS, "class", "1", "0..1", "transformerToEjbDataClass", UMLCLASSTOEJBDATACLASS);
        manager.insertAssociation("EJBDataClass", "ejbDataClass", "1", "0..1", "transformerToEjbDataClass", UMLCLASSTOEJBDATACLASS);

        // Associacoes UMLAssociationToEJBDataAssociation
        manager.insertAssociation(UMLMetaModeler.ASSOCIATION, "association", "1", "0..1", "transformerToEjbDataAssociationusingRule5", UMLASSOCIATIONTOEJBDATAASSOCIATION);
        manager.insertAssociation("EJBDataAssociation", "ejbDataAssociation", "1", "0..1", "transformerToEjbDataAssociationusingRule5", UMLASSOCIATIONTOEJBDATAASSOCIATION);

        // Associacoes UMLAssociationClassToEJBDataClass
        manager.insertAssociation(UMLMetaModeler.ASSOCIATIONCLASS, "associationClass", "1", "0..1", "transformerToEjbDataClassfromAssociationClass", UMLASSOCIATIONCLASSTOEJBDATACLASS);
        manager.insertAssociation("EJBDataClass", "ejbDataClass", "1", "0..1", "transformerToEjbDataClassfromAssociationClass", UMLASSOCIATIONCLASSTOEJBDATACLASS);

        // Associacoes UMLAssociationEndToEJBDataEndusingRule8
        manager.insertAssociation(UMLMetaModeler.ASSOCIATIONEND, "associationEnd", "1", "0..1", "transformerToEjbAssociationEndusingRule8", UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE8);
        manager.insertAssociation("EJBAssociationEnd", "ejbAssociationEnd", "1", "0..1", "transformerToEjbAssociationEndusingRule8", UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE8);

        // Associacoes UMLAssociationEndToEJBDataEndusingRule9
        manager.insertAssociation(UMLMetaModeler.ASSOCIATIONEND, "associationEnd", "1", "0..1", "transformerToEjbAssociationEndusingRule9", UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE9);
        manager.insertAssociation("EJBAssociationEnd", "ejbAssociationEnd", "1", "0..1", "transformerToEjbAssociationEndusingRule9", UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE9);

        // Associacoes UMLAssociationEndEmEJBAssociationusingRule10
        manager.insertAssociation(UMLMetaModeler.ASSOCIATIONEND, "associationEnd", "1", "0..1", "transformerToEjbDataAssociationusingRule10", UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE);
        manager.insertAssociation("EJBDataAssociation", "ejbDataAssociation", "1", "0..1", "transformerToEjbDataAssociationusingRule10", UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE);
        manager.insertAssociation("EJBAssociationEnd", "ejbAssociationEnd1", "1", "0..1", "transformerToEjbDataAssociationusingRule10_1", UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE);
        manager.insertAssociation("EJBAssociationEnd", "ejbAssociationEnd2", "1", "0..1", "transformerToEjbDataAssociationusingRule10_2", UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE);

        // Associacoes UMLAssociationEndEmEJBAssociationusingRule11
        manager.insertAssociation(UMLMetaModeler.ASSOCIATIONEND, "associationEnd", "1", "0..1", "transformerToEjbDataAssociationusingRule11", UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE11);
        manager.insertAssociation("EJBAssociationEnd", "ejbAssociationEnd2", "1", "0..1", "transformerToEjbDataAssociationusingRule11", UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE11);

        // Associacoes UMLClassToEJBEntityComponent
        manager.insertAssociation(UMLMetaModeler.CLASS, "class", "1", "0..1", "transformerToEntityComponent", UMLCLASSTOEJBENTITYCOMPONENT);
        manager.insertAssociation("EJBEntityComponent", "entityComponent", "1", "0..1", "transformerToEntityComponent", UMLCLASSTOEJBENTITYCOMPONENT);
        manager.insertAssociation("EJBDataClass", "dataClass", "1", "0..1", "transformerToEntityComponent", UMLCLASSTOEJBENTITYCOMPONENT);
        manager.insertAssociation("EJBDataSchema", "dataSchema", "1", "0..1", "transformerToEntityComponent", UMLCLASSTOEJBENTITYCOMPONENT);
        manager.insertAssociation("EJBServingAttribute", "servingAttribute", "1", "0..1", "transformerToEntityComponent", UMLCLASSTOEJBENTITYCOMPONENT);

        // Associacoes UMLSetToEJBSet
        manager.insertAssociation("UMLSet", "umlSet", "1", "0..1", "transformerToSet", UMLSETTOEJBSET);
        manager.insertAssociation("EJBSet", "ejbSet", "1", "0..1", "transformerToSet", UMLSETTOEJBSET);
    }

    /**
     *
     * @return
     * @throws ContractException
     */
    public boolean insertMetamodelOperations() throws ContractException {
        return true;
    }

    // Specific methods of this domain
    // UMLDataTypeToEJBDataType
    /**
     *
     * @param umlDataTypeId
     * @param ejbDataTypeId
     * @return
     * @throws ContractException
     */
    public boolean insertUMLDataTypeToEJBDataType(String umlDataTypeId, String ejbDataTypeId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlDataTypeId + "To" + ejbDataTypeId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLDATATYPETOEJBDATATYPE, id);
        result &= manager.insertValue(UMLDATATYPETOEJBDATATYPE, "name", id, name);
        result &= manager.insertLink(UMLMetaModeler.DATATYPE, umlDataTypeId, "dataType", "transformerToEjbDataType", id, UMLDATATYPETOEJBDATATYPE);
        result &= manager.insertLink("EJBDataType", ejbDataTypeId, "ejbDataType", "transformerToEjbDataType", id, UMLDATATYPETOEJBDATATYPE);

        return result;
    }

    // UMLClassToEJBKeyClass
    /**
     *
     * @param umlClassId
     * @param ejbKeyClassId
     * @param ejbAttributeId
     * @return
     * @throws ContractException
     */
    public boolean insertUMLClassToEJBKeyClass(String umlClassId, String ejbKeyClassId, String ejbAttributeId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlClassId + "To" + ejbKeyClassId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLCLASSTOEJBKEYCLASS, id);
        result &= manager.insertValue(UMLCLASSTOEJBKEYCLASS, "name", id, name);
        result &= manager.insertLink(UMLMetaModeler.CLASS, umlClassId, "class", "transformerToClass", id, UMLCLASSTOEJBKEYCLASS);
        result &= manager.insertLink("EJBKeyClass", ejbKeyClassId, "keyClass", "transformerToClass", id, UMLCLASSTOEJBKEYCLASS);
        result &= manager.insertLink("EJBAttribute", ejbAttributeId, "id", "transformerToClass", id, UMLCLASSTOEJBKEYCLASS);

        return result;
    }

    // UMLAssociationClassToEJBKeyClass
    /**
     *
     * @param umlClassId
     * @param ejbKeyClassId
     * @param ejbAttribute1Id
     * @param ejbAttribute2Id
     * @return
     * @throws ContractException
     */
    public boolean insertUMLAssociationClassToEJBKeyClass(String umlClassId, String ejbKeyClassId, String ejbAttribute1Id, String ejbAttribute2Id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlClassId + "To" + ejbKeyClassId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLASSOCIATIONCLASSTOEJBKEYCLASS, id);
        result &= manager.insertValue(UMLASSOCIATIONCLASSTOEJBKEYCLASS, "name", id, name);
        result &= manager.insertLink(UMLMetaModeler.ASSOCIATIONCLASS, umlClassId, "associationClass", "transformerToAssociationClass", id, UMLASSOCIATIONCLASSTOEJBKEYCLASS);
        result &= manager.insertLink("EJBKeyClass", ejbKeyClassId, "keyClass", "transformerToAssociationClass", id, UMLASSOCIATIONCLASSTOEJBKEYCLASS);
        result &= manager.insertLink("EJBAttribute", ejbAttribute1Id, "id", "transformerToAssociationClass", id, UMLASSOCIATIONCLASSTOEJBKEYCLASS);
        result &= manager.insertLink("EJBAttribute", ejbAttribute2Id, "id", "transformerToAssociationClass", id, UMLASSOCIATIONCLASSTOEJBKEYCLASS);

        return result;
    }

    // UMLParameterToEJBParameter
    /**
     *
     * @param umlParameterId
     * @param ejbParameterId
     * @return
     * @throws ContractException
     */
    public boolean insertUMLParameterToEJBParameter(String umlParameterId, String ejbParameterId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlParameterId + "To" + ejbParameterId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLPARAMETERTOEJBPARAMETER, id);
        result &= manager.insertValue(UMLPARAMETERTOEJBPARAMETER, "name", id, name);
        result &= manager.insertLink("Parameter", umlParameterId, "parameter", "transformerToEjbParameter", id, UMLPARAMETERTOEJBPARAMETER);
        result &= manager.insertLink("EJBParameter", ejbParameterId, "ejbParameter", "transformerToEjbParameter", id, UMLPARAMETERTOEJBPARAMETER);

        return result;
    }

    // UMLOperationToBusinessMethod
    /**
     *
     * @param umlOperationId
     * @param businessMethodId
     * @return
     * @throws ContractException
     */
    public boolean insertUMLOperationToBusinessMethod(String umlOperationId, String businessMethodId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlOperationId + "To" + businessMethodId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLOPERATIONTOBUSINESSMETHOD, id);
        result &= manager.insertValue(UMLOPERATIONTOBUSINESSMETHOD, "name", id, name);
        result &= manager.insertLink("Operation", umlOperationId, "operation", "transformerToBusinessMethod", id, UMLOPERATIONTOBUSINESSMETHOD);
        result &= manager.insertLink("BusinessMethod", businessMethodId, "businessMethod", "transformerToBusinessMethod", id, UMLOPERATIONTOBUSINESSMETHOD);

        return result;
    }

    // UMLAttributeToEJBAttribute
    /**
     *
     * @param umlAttributeId
     * @param ejbAttributeId
     * @return
     * @throws ContractException
     */
    public boolean insertUMLAttributeToEJBAttribute(String umlAttributeId, String ejbAttributeId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlAttributeId + "To" + ejbAttributeId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLATTRIBUTETOEJBATTRIBUTE, id);
        result &= manager.insertValue(UMLATTRIBUTETOEJBATTRIBUTE, "name", id, name);
        result &= manager.insertLink(UMLMetaModeler.ATTRIBUTE, umlAttributeId, "attribute", "transformerToEjbAttribute", id, UMLATTRIBUTETOEJBATTRIBUTE);
        result &= manager.insertLink("EJBAttribute", ejbAttributeId, "ejbAttribute", "transformerToEjbAttribute", id, UMLATTRIBUTETOEJBATTRIBUTE);

        return result;
    }

    // UMLClassToEJBDataClass
    /**
     *
     * @param umlClassId
     * @param ejbDataClassId
     * @return
     * @throws ContractException
     */
    public boolean insertUMLClassToEJBDataClass(String umlClassId, String ejbDataClassId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlClassId + "To" + ejbDataClassId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLCLASSTOEJBDATACLASS, id);
        result &= manager.insertValue(UMLCLASSTOEJBDATACLASS, "name", id, name);
        result &= manager.insertLink(UMLMetaModeler.CLASS, umlClassId, "class", "transformerToEjbDataClass", id, UMLCLASSTOEJBDATACLASS);
        result &= manager.insertLink("EJBDataClass", ejbDataClassId, "ejbDataClass", "transformerToEjbDataClass", id, UMLCLASSTOEJBDATACLASS);

        return result;
    }

    // UMLAssociationToEJBDataAssociation
    /**
     *
     * @param umlAssociationId
     * @param ejbDataAssociationId
     * @return
     * @throws ContractException
     */
    public boolean insertUMLAssociationToEJBDataAssociation(String umlAssociationId, String ejbDataAssociationId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlAssociationId + "To" + ejbDataAssociationId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLASSOCIATIONTOEJBDATAASSOCIATION, id);
        result &= manager.insertValue(UMLASSOCIATIONTOEJBDATAASSOCIATION, "name", id, name);
        result &= manager.insertLink(UMLMetaModeler.ASSOCIATION, umlAssociationId, "association", "transformerToEjbDataAssociationusingRule5", id, UMLASSOCIATIONTOEJBDATAASSOCIATION);
        result &= manager.insertLink("EJBDataAssociation", ejbDataAssociationId, "ejbDataAssociation", "transformerToEjbDataAssociationusingRule5", id, UMLASSOCIATIONTOEJBDATAASSOCIATION);

        return result;
    }

    // UMLAssociationClassToEJBDataClass
    /**
     *
     * @param umlAssociationClassId
     * @param ejbDataClassId
     * @return
     * @throws ContractException
     */
    public boolean insertUMLAssociationClassToEJBDataClass(String umlAssociationClassId, String ejbDataClassId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlAssociationClassId + "To" + ejbDataClassId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLASSOCIATIONCLASSTOEJBDATACLASS, id);
        result &= manager.insertValue(UMLASSOCIATIONCLASSTOEJBDATACLASS, "name", id, name);
        result &= manager.insertLink(UMLMetaModeler.ASSOCIATIONCLASS, umlAssociationClassId, "associationClass", "transformerToEjbDataClassfromAssociationClass", id, UMLASSOCIATIONCLASSTOEJBDATACLASS);
        result &= manager.insertLink("EJBDataClass", ejbDataClassId, "ejbDataClass", "transformerToEjbDataClassfromAssociationClass", id, UMLASSOCIATIONCLASSTOEJBDATACLASS);

        return result;
    }

    // UMLAssociationEndToEJBDataEndusingRule8
    /**
     *
     * @param umlAssociationEndId
     * @param ejbAssociationEndId
     * @return
     * @throws ContractException
     */
    public boolean insertUMLAssociationEndToEJBDataEndusingRule8(String umlAssociationEndId, String ejbAssociationEndId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlAssociationEndId + "To" + ejbAssociationEndId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE8, id);
        result &= manager.insertValue(UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE8, "name", id, name);
        result &= manager.insertLink(UMLMetaModeler.ASSOCIATIONEND, umlAssociationEndId, "associationEnd", "transformerToEjbAssociationEndusingRule8", id, UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE8);
        result &= manager.insertLink("EJBAssociationEnd", ejbAssociationEndId, "ejbAssociationEnd", "transformerToEjbAssociationEndusingRule8", id, UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE8);

        return result;
    }

    // UMLAssociationEndToEJBDataEndusingRule9
    /**
     *
     * @param umlAssociationEndId
     * @param ejbAssociationEndId
     * @return
     * @throws ContractException
     */
    public boolean insertUMLAssociationEndToEJBDataEndusingRule9(String umlAssociationEndId, String ejbAssociationEndId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlAssociationEndId + "To" + ejbAssociationEndId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE9, id);
        result &= manager.insertValue(UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE9, "name", id, name);
        result &= manager.insertLink(UMLMetaModeler.ASSOCIATIONEND, umlAssociationEndId, "associationEnd", "transformerToEjbAssociationEndusingRule9", id, UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE9);
        result &= manager.insertLink("EJBAssociationEnd", ejbAssociationEndId, "ejbAssociationEnd", "transformerToEjbAssociationEndusingRule9", id, UMLASSOCIATIONENDTOEJBDATAENDUSINGRULE9);

        return result;
    }

    // UMLAssociationEndEmEJBAssociationusingRule10
    /**
     *
     * @param umlAssociationEndId
     * @param ejbAssociationId
     * @param ejbAssociationEnd1Id
     * @param ejbAssociationEnd2Id
     * @return
     * @throws ContractException
     */
    public boolean insertUMLAssociationEndEmEJBAssociationusingRule10(String umlAssociationEndId, String ejbAssociationId, String ejbAssociationEnd1Id, String ejbAssociationEnd2Id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlAssociationEndId + "To" + ejbAssociationId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE, id);
        result &= manager.insertValue(UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE, "name", id, name);
        result &= manager.insertLink(UMLMetaModeler.ASSOCIATIONEND, umlAssociationEndId, "associationEnd", "transformerToEjbDataAssociationusingRule10", id, UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE);
        result &= manager.insertLink("EJBDataAssociation", ejbAssociationId, "ejbDataAssociation", "transformerToEjbDataAssociationusingRule10", id, UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE);
        result &= manager.insertLink("EJBAssociationEnd", ejbAssociationEnd1Id, "ejbAssociationEnd1", "transformerToEjbDataAssociationusingRule10_1", id, UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE);
        result &= manager.insertLink("EJBAssociationEnd", ejbAssociationEnd2Id, "ejbAssociationEnd2", "transformerToEjbDataAssociationusingRule10_2", id, UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE);

        return result;
    }

    // UMLAssociationEndEmEJBAssociationusingRule11
    /**
     *
     * @param umlAssociationEndId
     * @param ejbAssociationEnd2Id
     * @return
     * @throws ContractException
     */
    public boolean insertUMLAssociationEndEmEJBAssociationusingRule11(String umlAssociationEndId, String ejbAssociationEnd2Id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlAssociationEndId + "To" + ejbAssociationEnd2Id;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE11, id);
        result &= manager.insertValue(UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE11, "name", id, name);
        result &= manager.insertLink(UMLMetaModeler.ASSOCIATIONEND, umlAssociationEndId, "associationEnd", "transformerToEjbDataAssociationusingRule11", id, UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE11);
        result &= manager.insertLink("EJBAssociationEnd", ejbAssociationEnd2Id, "ejbAssociationEnd2", "transformerToEjbDataAssociationusingRule11", id, UMLASSOCIATIONENDEMEJBASSOCIATIONUSINGRULE11);

        return result;
    }

    // UMLClassToEJBEntityComponent
    /**
     *
     * @param umlClassId
     * @param ejbEntityComponentId
     * @param ejbDataClassId
     * @param ejbDataSchemaId
     * @param ejbServingAttributeId
     * @return
     * @throws ContractException
     */
    public boolean insertUMLClassToEJBEntityComponent(String umlClassId, String ejbEntityComponentId, String ejbDataClassId, String ejbDataSchemaId, String ejbServingAttributeId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlClassId + "To" + ejbEntityComponentId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLCLASSTOEJBENTITYCOMPONENT, id);
        result &= manager.insertValue(UMLCLASSTOEJBENTITYCOMPONENT, "name", id, name);
        result &= manager.insertLink(UMLMetaModeler.CLASS, umlClassId, "class", "transformerToEntityComponent", id, UMLCLASSTOEJBENTITYCOMPONENT);
        result &= manager.insertLink("EJBEntityComponent", ejbEntityComponentId, "entityComponent", "transformerToEntityComponent", id, UMLCLASSTOEJBENTITYCOMPONENT);
        result &= manager.insertLink("EJBDataClass", ejbDataClassId, "dataClass", "transformerToEntityComponent", id, UMLCLASSTOEJBENTITYCOMPONENT);
        result &= manager.insertLink("EJBDataSchema", ejbDataSchemaId, "dataSchema", "transformerToEntityComponent", id, UMLCLASSTOEJBENTITYCOMPONENT);
        result &= manager.insertLink("EJBServingAttribute", ejbServingAttributeId, "servingAttribute", "transformerToEntityComponent", id, UMLCLASSTOEJBENTITYCOMPONENT);

        return result;
    }

    // UMLSetToEJBSet
    /**
     * 
     * @param umlSetId
     * @param ejbSetId
     * @return
     * @throws ContractException
     */
    public boolean insertUMLSetToEJBSet(String umlSetId, String ejbSetId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;
        String name = umlSetId + "To" + ejbSetId;
        String id = name + System.nanoTime();
        result &= manager.insertObject(UMLSETTOEJBSET, id);
        result &= manager.insertValue(UMLSETTOEJBSET, "name", id, name);
        result &= manager.insertLink("UMLSet", umlSetId, "umlSet", "transformerToSet", id, UMLSETTOEJBSET);
        result &= manager.insertLink("EJBSet", ejbSetId, "ejbSet", "transformerToSet", id, UMLSETTOEJBSET);

        return result;
    }

    /*
     * !!!!!!!!!! Transformer -> A lot of methods to transform from UML to EJB !!!!!!!!!!
     */
    /**
     * Lower the first letter of a given string
     * @param string    given string to lower the first letter
     * @return  a string with the first letter lowered
     */
    protected String lowerFirstLetter(String string) {
        return string.replaceFirst(string.substring(0, 1), string.substring(0, 1).toLowerCase());
    }

    /**
     * Process the result of a XEOS's query and transform it in a array of strings
     * @param result    the result of a XEOS's query
     * @return  an array with the result of a XEOS's query
     */
    protected String[] processQueryResult(String result) {
        result = result.replace("\n", "");
        int inicio = result.indexOf("{");
        int fim = result.indexOf("}");

        if (inicio < 0 && fim < 0) {
            return result.replace("{", "").replace("}", "").replace(" ", "").replace("'", "").split(",");
        } else {
            if (inicio < 0) {
                return result.substring(0, fim).replace("{", "").replace("}", "").replace(" ", "").replace("'", "").split(",");
            } else if (fim < 0) {
                return result.substring(inicio + 1, result.length()).replace("{", "").replace("}", "").replace(" ", "").replace("'", "").split(",");
            }
        }
        String[] processedResult = result.substring(inicio + 1, fim).replace("{", "").replace("}", "").replace(" ", "").replace("'", "").split(",");
        if (processedResult != null && processedResult.length == 1 && "".equals(processedResult[0])) {
            return new String[0];
        }
        return processedResult;
    }

    private String findEjbDataTypeBasedonUmlId(String umlId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] result = null;

        // Procura por um DataType -> EjbDataType
        // Regra Minha
//        result = processQueryResult(manager.query("UMLDataTypeToEJBDataType.allInstances()->select(a | a.dataType->exists(dt : DataType | dt = " + umlId + "))"));
        result = processQueryResult(manager.query("UMLDataTypeToEJBDataType.allInstances()->select(a | a.dataType = " + umlId + ")"));
        if (result != null && result.length > 0 && !"".equals(result[0])) {
            return processQueryResult(manager.query(result[0] + ".ejbDataType"))[0];
        }
        // Procura por um UMLSet -> EJBSet
//        result = processQueryResult(manager.query("UMLSetToEJBSet.allInstances()->select(a | a.umlSet->exists(s : UMLSet | s = " + umlId + "))"));
        result = processQueryResult(manager.query("UMLSetToEJBSet.allInstances()->select(a | a.umlSet = " + umlId + ")"));
        if (result != null && result.length > 0 && !"".equals(result[0])) {
            return processQueryResult(manager.query(result[0] + ".ejbSet"))[0];
        }
        return null;
    }

    private String findEjbDataClassBasedonUmlId(String umlId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] result = null;

        // Procura por um Class -> EJBDataClass
        // Regra 3
//        result = processQueryResult(manager.query("UMLClassToEJBEntityComponent.allInstances()->select(a | a.class->includes(" + umlId + "))"));
        result = processQueryResult(manager.query("UMLClassToEJBEntityComponent.allInstances()->select(a | a.class = " + umlId + ")"));
        if (result != null && result.length > 0 && !"".equals(result[0])) {
            return processQueryResult(manager.query(result[0] + ".dataClass"))[0];
        }
        // Regra 4
//        result = processQueryResult(manager.query("UMLClassToEJBDataClass.allInstances()->select(a | a.class->includes(" + umlId + "))"));
        result = processQueryResult(manager.query("UMLClassToEJBDataClass.allInstances()->select(a | a.class = " + umlId + ")"));
        if (result != null && result.length > 0 && !"".equals(result[0])) {
            return processQueryResult(manager.query(result[0] + ".ejbDataClass"))[0];
        }
        // Regra 6
//        result = processQueryResult(manager.query("UMLAssociationClassToEJBDataClass.allInstances()->select(a | a.associationClass->includes(" + umlId + "))"));
        result = processQueryResult(manager.query("UMLAssociationClassToEJBDataClass.allInstances()->select(a | a.associationClass = " + umlId + ")"));
        if (result != null && result.length > 0 && !"".equals(result[0])) {
            return processQueryResult(manager.query(result[0] + ".ejbDataClass"))[0];
        }

        return null;
    }

    private String findEjbKeyClassBasedonUmlId(String umlId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] result = null;
        // Procura por um Class -> EJBKeyClass
        // Regra 1
//        result = processQueryResult(manager.query("UMLClassToEJBKeyClass.allInstances()->select(a | a.class->exists(c : Class | c = " + umlId + "))"));
        result = processQueryResult(manager.query("UMLClassToEJBKeyClass.allInstances()->select(a | a.class = " + umlId + ")"));
        if (result != null && result.length > 0 && !"".equals(result[0])) {
            return processQueryResult(manager.query(result[0] + ".keyClass"))[0];
        }
        // Regra 2
//        result = processQueryResult(manager.query("UMLAssociationClassToEJBKeyClass.allInstances()->select(a | a.associationClass->exists(ac : AssociationClass | ac = " + umlId + "))"));
        result = processQueryResult(manager.query("UMLAssociationClassToEJBKeyClass.allInstances()->select(a | a.associationClass = " + umlId + ")"));
        if (result != null && result.length > 0 && !"".equals(result[0])) {
            return processQueryResult(manager.query(result[0] + ".keyClass"))[0];
        }

        return null;
    }

    private String findEjbIdBasedonUmlId(String umlId) throws ContractException {
        String r;
        try {
            r = findEjbDataTypeBasedonUmlId(umlId);
        } catch (Exception ex) {
            r = null;
        }
        if (r == null) {
            try {
                r = findEjbDataClassBasedonUmlId(umlId);
            } catch (Exception ex) {
                r = null;
            }
            if (r == null) {
                try {
                    r = findEjbKeyClassBasedonUmlId(umlId);
                } catch (Exception ex) {
                    r = null;
                }
                if (r == null) {
                    // Nao encontrou -> Dispara excecao
                    throw new ContractException("Can't find correspondent EJB object to UML object[id: " + umlId + "]");
                }
            }
        }
        return r;
    }

    private String findEJBAssociationEndBasedonUmlAssociationEnd(String associationEndId) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] result = null;

        // Regra 8
//        result = processQueryResult(manager.query("UMLAssociationEndToEJBDataEndusingRule8.allInstances()->select(a | a.associationEnd->exists(ae : AssociationEnd | ae = " + associationEndId + "))"));
        result = processQueryResult(manager.query("UMLAssociationEndToEJBDataEndusingRule8.allInstances()->select(a | a.associationEnd = " + associationEndId + ")"));
        if (result != null && result.length > 0 && !"".equals(result[0])) {
            return processQueryResult(manager.query(result[0] + ".ejbAssociationEnd"))[0];
        }
        // Regra 9
//        result = processQueryResult(manager.query("UMLAssociationEndToEJBDataEndusingRule9.allInstances()->select(a | a.associationEnd->exists(ae : AssociationEnd | ae = " + associationEndId + "))"));
        result = processQueryResult(manager.query("UMLAssociationEndToEJBDataEndusingRule9.allInstances()->select(a | a.associationEnd = " + associationEndId + ")"));
        if (result != null && result.length > 0 && !"".equals(result[0])) {
            return processQueryResult(manager.query(result[0] + ".ejbAssociationEnd"))[0];
        }
        // Regra 10
//        result = processQueryResult(manager.query("UMLAssociationEndEmEJBAssociationusingRule10.allInstances()->select(a | a.associationEnd->exists(ae : AssociationEnd | ae = " + associationEndId + "))"));
        result = processQueryResult(manager.query("UMLAssociationEndEmEJBAssociationusingRule10.allInstances()->select(a | a.associationEnd = " + associationEndId + ")"));
        if (result != null && result.length > 0 && !"".equals(result[0])) {
            return processQueryResult(manager.query(result[0] + ".ejbAssociationEnd2"))[0];
        }
        // Regra 11
//        result = processQueryResult(manager.query("UMLAssociationEndEmEJBAssociationusingRule11.allInstances()->select(a | a.associationEnd->exists(ae : AssociationEnd | ae = " + associationEndId + "))"));
        result = processQueryResult(manager.query("UMLAssociationEndEmEJBAssociationusingRule11.allInstances()->select(a | a.associationEnd = " + associationEndId + ")"));
        if (result != null && result.length > 0 && !"".equals(result[0])) {
            return processQueryResult(manager.query(result[0] + ".ejbAssociationEnd2"))[0];
        }

        // Nao encontrou -> Dispara excecao
        throw new ContractException("Can't find correspondent EJBAssociationEnd to AsociationEnd[id: " + associationEndId + "]");
    }

    /**
     *
     * @throws ContractException
     */
    @Override
    public void transformDomain() throws ContractException {
        // DataType -> EJBDataType
        transformUMLDataTypetoEJBDataType();

        // Cria elementos e linka (Caso unico: Class e AssociationClass -> EJBKeyClass)
        transformUMLClasstoEJBKeyClass();                 // Regra 1
        transformUMLAssociationClasstoEJBKeyClass();      // Regra 2

        // Cria objetos
        transformUMLClasstoEJBEntityComponent();          // Regra 3
        transformUMLClasstoEJBDataClass();                // Regra 4
        transformUMLAssociationtoEJBDataAssociation();    // Regra 5
        transformUMLAssociationClasstoEJBDataClass();     // Regra 6
        transformUMLAttributetoEJBAttribute();            // Regra 7
        transformRule8UMLAssociationEndtoEJBAssociationEnd(); // Regra 8
        transformRule9UMLAssociationEndtoEJBAssociationEnd(); // Regra 9
        transformRule10UMLAssociationEndtoEJBAssociation();   // Regra 10
        transformRule11UMLAssociationEndtoEJBAssociation();   // Regra 11
        transformRuleUMLOperationtoBusinessMethod();          // Regra 12
        transformRuleUMLParametertoEJBParameter();            // Regra 13
        transformUMLSettoEJBSet();                            // Minha regra

        // Cria links
        linkUMLClasstoEJBEntityComponent();                // Regra 3
        linkUMLAssociationtoEJBDataAssociation();          // Regra 5
        linkUMLClasstoEJBDataClass();                      // Regra 4
        linkUMLAssociationClasstoEJBDataClass();           // Regra 6
        linkUMLAttributetoEJBAttribute();                  // Regra 7
        linkRule8UMLAssociationEndtoEJBAssociationEnd();       // Regra 8
        linkRule9UMLAssociationEndtoEJBAssociationEnd();       // Regra 9
        linkRule10UMLAssociationEndtoEJBAssociation();         // Regra 10
        linkRule11UMLAssociationEndtoEJBAssociation();         // Regra 11
        linkRuleUMLOperationtoBusinessMethod();                // Regra 12
        linkUMLParametertoEJBParameter();                  // Regra 13
        linkUMLSettoEJBSet();

    }

    //--------------------------------------------------------------------------
    // REGRA SOBRE TIPOS DE DADOS - Completo (Cria e linka)
    private void transformUMLDataTypetoEJBDataType() throws ContractException {
        ModelManager manager = ModelManager.instance();
        // "Integer" -> "EJBInteger"
        if (!"EJBInteger".equals(manager.query("EJBInteger"))) {
            targetDomain.insertEJBDataType("EJBInteger", "Integer");
        }
        this.insertUMLDataTypeToEJBDataType("UMLInteger", "EJBInteger");

        // "Double" -> "EJBDouble"
        if (!"EJBDouble".equals(manager.query("EJBDouble"))) {
            targetDomain.insertEJBDataType("EJBDouble", "Double");
        }
        this.insertUMLDataTypeToEJBDataType("UMLDouble", "EJBDouble");

        // "Real" -> "EJBDouble"
        this.insertUMLDataTypeToEJBDataType("UMLReal", "EJBDouble");

        // "String" -> "EJBString"
        if (!"EJBString".equals(manager.query("EJBString"))) {
            targetDomain.insertEJBDataType("EJBString", "String");
        }
        this.insertUMLDataTypeToEJBDataType("UMLString", "EJBString");

        // "Date" -> "EJBDate"
        if (!"EJBDate".equals(manager.query("EJBDate"))) {
            targetDomain.insertEJBDataType("EJBDate", "Date");
        }
        this.insertUMLDataTypeToEJBDataType("UMLDate", "EJBDate");

        // "Boolean" -> "EJBBoolean"
        if (!"EJBBoolean".equals(manager.query("EJBBoolean"))) {
            targetDomain.insertEJBDataType("EJBBoolean", "Boolean");
        }
        this.insertUMLDataTypeToEJBDataType("UMLBoolean", "EJBBoolean");

        // "Void" -> "EJBVoid"
        if (!"EJBVoid".equals(manager.query("EJBVoid"))) {
            targetDomain.insertEJBDataType("EJBVoid", "void");
        }
        this.insertUMLDataTypeToEJBDataType("UMLVoid", "EJBVoid");
    }
    //--------------------------------------------------------------------------
    // REGRA 01 - Completo (Cria e linka)

    private void transformUMLClasstoEJBKeyClass() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("Class.allInstances()->select(c : Class | c.oclIsTypeOf(Class))"));

        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            if (!"NULL_CLASS".equals(id)) {
                createEJBKeyClassfromUMLClass(id, name);
            }
        }
    }

    private void createEJBKeyClassfromUMLClass(String umlClassId, String umlClassName) throws ContractException {
        String ejbKeyClassName = umlClassName + "Key";
        String ejbKeyClassId = ejbKeyClassName + System.nanoTime();
        String ejbAttributeName = umlClassName + "ID";
        String ejbAttributeId = ejbAttributeName + System.nanoTime();
        targetDomain.insertEJBKeyClass(ejbKeyClassId, ejbKeyClassName);
        targetDomain.insertEJBAttribute(ejbAttributeId, ejbAttributeName, "public", "EJBInteger", ejbKeyClassId);

        this.insertUMLClassToEJBKeyClass(umlClassId, ejbKeyClassId, ejbAttributeId);
    }
    //--------------------------------------------------------------------------
    // REGRA 02 - Completo (Cria e linka)

    private void transformUMLAssociationClasstoEJBKeyClass() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("AssociationClass.allInstances()"));

        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            createEJBKeyClassfromUMLAssociationClass(id, name);
        }
    }

    private void createEJBKeyClassfromUMLAssociationClass(String umlAssociationClassId, String umlAssociationClassName) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String ejbKeyClassName = umlAssociationClassName + "Key";
        String ejbKeyClassId = ejbKeyClassName + System.nanoTime();
        targetDomain.insertEJBKeyClass(ejbKeyClassId, ejbKeyClassName);

        String id = manager.query(umlAssociationClassId + ".associationEnds->asOrderedSet()->first()").replace("'", "");

        String firstName = processQueryResult(manager.query(id + ".classifier.name"))[0] + "ID";
        String firstId = firstName + System.nanoTime();
        targetDomain.insertEJBAttribute(firstId, firstName, "public", "EJBInteger", ejbKeyClassId);

        String otherId = null;
        String[] result = processQueryResult(manager.query(id + ".otherEnd.classifier.name"));
        if (result != null && result.length > 0 && !"".equals(result[0])) {
            String otherName = result[0] + "ID";
            otherId = otherName + System.nanoTime();
            targetDomain.insertEJBAttribute(otherId, otherName, "public", "EJBInteger", ejbKeyClassId);
        } else {
            String otherName = processQueryResult(manager.query(id + ".class.name"))[0] + "ID";
            otherId = otherName + System.nanoTime();
            targetDomain.insertEJBAttribute(otherId, otherName, "public", "EJBInteger", ejbKeyClassId);
        }
        this.insertUMLAssociationClassToEJBKeyClass(umlAssociationClassId, ejbKeyClassId, firstId, otherId);
    }
    //--------------------------------------------------------------------------
    // REGRA 3
    // Criacao

    private void transformUMLClasstoEJBEntityComponent() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("Class.allInstances()->select(c : Class | c.oclIsTypeOf(Class))->select(c : Class | not c.feature->select(f : Feature | f.oclIsKindOf(AssociationEnd))->collect(ft : Feature | ft.oclAsType(AssociationEnd))->exists(ae : AssociationEnd | ae.composition = true))"));

        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            if (!"NULL_CLASS".equals(id)) {
                createEjbEntityComponentfromUmlClass(id, name);
            }
        }
    }

    private void createEjbEntityComponentfromUmlClass(String classId, String className) throws ContractException {
        // Cria EJBEntityComponent
        String ejbEntityComponentName = className;
        String ejbEntityComponentId = ejbEntityComponentName + System.nanoTime();
        targetDomain.insertEJBEntityComponentStub(ejbEntityComponentId, ejbEntityComponentName);

        // Cria EJBDataSchema
        String ejbDataSchemaName = className;
        String ejbDataSchemaId = ejbDataSchemaName + System.nanoTime();
        targetDomain.insertEJBDataSchemaStub(ejbDataSchemaId, ejbDataSchemaName);

        // Cria EJBDataClass
        String ejbDataClassName = className;
        String ejbDataClassId = ejbDataClassName + System.nanoTime();
        targetDomain.insertEJBDataClassStub(ejbDataClassId, ejbDataClassName);

        // Cria EJBServingAttribute
        String ejbServingAttributeName = className;
        String ejbServingAttributeId = ejbServingAttributeName + System.nanoTime();
        String ejbServingAttributeLower = "1";
        String ejbServingAttributeUpper = "1";
        Boolean ejbServingAttributeComposition = false;
        targetDomain.insertEJBServingAttributeStub(ejbServingAttributeId, ejbServingAttributeName, ejbServingAttributeLower, ejbServingAttributeUpper, ejbServingAttributeComposition);

        // Cria Juncao
        this.insertUMLClassToEJBEntityComponent(classId, ejbEntityComponentId, ejbDataClassId, ejbDataSchemaId, ejbServingAttributeId);
    }
    // Linkagem

    private void linkUMLClasstoEJBEntityComponent() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("UMLClassToEJBEntityComponent.allInstances()"));

        for (String id : ids) {
            if (id != null && !"".equals(id)) {
                linkEjbEntityComponentfromUmlClass(id);
            }
        }
    }

    private void linkEjbEntityComponentfromUmlClass(String id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String umlClassId = processQueryResult(manager.query(id + ".class"))[0];
        String ejbEntityComponentId = processQueryResult(manager.query(id + ".entityComponent"))[0];
        String ejbDataClassId = processQueryResult(manager.query(id + ".dataClass"))[0];
        String ejbDataSchemaId = processQueryResult(manager.query(id + ".dataSchema"))[0];
        String ejbServingAttributeId = processQueryResult(manager.query(id + ".servingAttribute"))[0];

        // servingAttribute.class = entityComponent
        targetDomain.insertEJBServingAttributeClassLink(ejbServingAttributeId, ejbEntityComponentId);
        // servingAttribute.type = rootDataClass
        targetDomain.insertEJBServingAttributeTypeLink(ejbServingAttributeId, ejbDataClassId);
        // rootDataClass.package = dataSchema
        targetDomain.insertEJBDataClassSchemaLink(ejbDataClassId, ejbDataSchemaId);

        // class.getAllContained(Set(UML::Class){}).operations() <~> entityComponent.feature
        // Apesar da query estar correta e otimizada, o xeos nao retorna os resultados corretos quando recupera as features e no conjunto de classes possui uma AssociationClass
//        String[] idsUmlOperations = tratarResultadoQuery(origem.query(idUmlClass + ".getAllContained().feature->select(f : Feature | f.oclIsKindOf(Operation))"));
//        String[] umlOperationsIds = processQueryResult(manager.query("Operation.allInstances()->select(op : Operation | op.class->notEmpty())->select(op : Operation | " + umlClassId + ".getAllContained()->includes(op.class->asOrderedSet()->first().oclAsType(Class)))"));
        String[] umlOperationsIds = processQueryResult(manager.query("Operation.allInstances()->select(op : Operation | op.class->notEmpty())->select(op : Operation | " + umlClassId + ".getAllContained()->includes(op.class.oclAsType(Class)))"));
        if (umlOperationsIds != null && umlOperationsIds.length > 0 && !"".equals(umlOperationsIds[0])) {
            for (String operationId : umlOperationsIds) {
                String businessMethodId = processQueryResult(manager.query(operationId + ".transformerToBusinessMethod.businessMethod"))[0];
                targetDomain.insertBusinessMethodClassLink(businessMethodId, ejbEntityComponentId);
            }
        }
        // class.feature->select(oclKindOf(Attribute) or oclKindOf(AssociationEnd)) <~> rootDataClass.feature
        // Separando...
        //   1) class.feature->select(oclKindOf(Attribute)) <~> rootDataClass.feature
        String[] umlAttributesIds = processQueryResult(manager.query(umlClassId + ".feature->select(f | f.oclIsKindOf(Attribute))"));
        if (umlAttributesIds != null && umlAttributesIds.length > 0 && !"".equals(umlAttributesIds[0])) {
            for (String attributeId : umlAttributesIds) {
                String ejbAttributeId = processQueryResult(manager.query(attributeId + ".transformerToEjbAttribute.ejbAttribute"))[0];
                targetDomain.insertEJBAttributeClassLink(ejbAttributeId, ejbDataClassId);
            }
        }
        //   2) class.feature->select(oclKindOf(AssociationEnd)) <~> rootDataClass.feature
        String[] umlAssociationEndsIds = processQueryResult(manager.query(umlClassId + ".feature->select(f | f.oclIsKindOf(AssociationEnd))"));
        if (umlAssociationEndsIds != null && umlAssociationEndsIds.length > 0 && !"".equals(umlAssociationEndsIds[0])) {
            for (String associationEndId : umlAssociationEndsIds) {
                String ejbAssociationEndId = findEJBAssociationEndBasedonUmlAssociationEnd(associationEndId);
                targetDomain.insertEJBAssociationEndClassLink(ejbAssociationEndId, ejbDataClassId);
            }
        }
        // class.getAllContained(Set(UML::Class){}) <~> entityComponent.usedTable
        // --- Nao fiz pois nao tem transformacao para Table
    }
    //--------------------------------------------------------------------------
    // REGRA 4
    // Criacao

    private void transformUMLClasstoEJBDataClass() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("Class.allInstances()->select(c : Class | c.oclIsTypeOf(Class))->select(c : Class | c.feature->select(f : Feature | f.oclIsKindOf(AssociationEnd))->collect(ft : Feature | ft.oclAsType(AssociationEnd))->exists(ae : AssociationEnd | ae.composition = true))"));
        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            if (!"NULL_CLASS".equals(id)) {
                createEJBDataClassfromClass(id, name);
            }
        }
    }

    private void createEJBDataClassfromClass(String classId, String className) throws ContractException {
        String ejbDataClassName = className;
        String ejbDataClassId = ejbDataClassName + System.nanoTime();
        targetDomain.insertEJBDataClassStub(ejbDataClassId, ejbDataClassName);

        this.insertUMLClassToEJBDataClass(classId, ejbDataClassId);
    }
    // Linkagem

    private void linkUMLClasstoEJBDataClass() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("UMLClassToEJBDataClass.allInstances()"));
        for (String id : ids) {
            if (id != null && !"".equals(id)) {
                linkEJBDataClassfromClass(id);
            }
        }
    }

    private void linkEJBDataClassfromClass(String id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String umlClassId = processQueryResult(manager.query(id + ".class"))[0];
        String ejbDataClassId = processQueryResult(manager.query(id + ".ejbDataClass"))[0];

        // class.feature->select(oclKindOf(Attribute) or oclKindOf(AssociationEnd)) <~> nonRootDataClass.feature
        // Separando...
        //   1) class.feature->select(oclKindOf(Attribute)) <~> nonRootDataClass.feature
        String[] umlAttributesIds = processQueryResult(manager.query(umlClassId + ".feature->select(f : Feature | f.oclIsKindOf(Attribute))"));
        if (umlAttributesIds != null && umlAttributesIds.length > 0 && !"".equals(umlAttributesIds[0])) {
            for (String attributeId : umlAttributesIds) {
                String ejbAttributeId = processQueryResult(manager.query(attributeId + ".transformerToEjbAttribute.ejbAttribute"))[0];
                targetDomain.insertEJBAttributeClassLink(ejbAttributeId, ejbDataClassId);
            }
        }
        //   2) class.feature->select(oclKindOf(AssociationEnd)) <~> nonRootDataClass.feature
        String[] umlAssociationEndsIds = processQueryResult(manager.query(umlClassId + ".feature->select(f : Feature | f.oclIsKindOf(AssociationEnd))"));
        if (umlAssociationEndsIds != null && umlAssociationEndsIds.length > 0 && !"".equals(umlAssociationEndsIds[0])) {
            for (String associationEndId : umlAssociationEndsIds) {
                String ejbAssociationEndId = findEJBAssociationEndBasedonUmlAssociationEnd(associationEndId);
                targetDomain.insertEJBAssociationEndClassLink(ejbAssociationEndId, ejbDataClassId);
            }
        }
        // class.getOuterMostContainer() <~> nonRootClass.package
//        String ejbDataSchemaId = processQueryResult(manager.query("UMLClassToEJBEntityComponent.allInstances()->select(a | a.class->includes(" + umlClassId + ".getOuterMostContainer()))->collect(a | a.dataSchema)"))[0];
        String ejbDataSchemaId = processQueryResult(manager.query("UMLClassToEJBEntityComponent.allInstances()->select(a | a.class = " + umlClassId + ".getOuterMostContainer()).dataSchema"))[0];
        targetDomain.insertEJBDataClassSchemaLink(ejbDataClassId, ejbDataSchemaId);
    }
    //--------------------------------------------------------------------------
    // REGRA 5
    // Criacao

    private void transformUMLAssociationtoEJBDataAssociation() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("Association.allInstances()->select(a : Association | a.oclIsTypeOf(Association))->select(a : Association | a.associationEnds->exists(ae : AssociationEnd | ae.composition = true))"));

        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            if (!"NULL_ASSOCIATION".equals(id)) {
                createEJBDataAssociationfromAssociation(id, name);
            }
        }
    }

    private void createEJBDataAssociationfromAssociation(String id, String name) throws ContractException {
        String ejbDataAssociationName = name;
        String ejbDataAssociationId = ejbDataAssociationName + System.nanoTime();
        targetDomain.insertEJBDataAssociationStub(ejbDataAssociationId, ejbDataAssociationName);

        this.insertUMLAssociationToEJBDataAssociation(id, ejbDataAssociationId);
    }
    // Linkagem

    private void linkUMLAssociationtoEJBDataAssociation() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("UMLAssociationToEJBDataAssociation.allInstances()"));

        for (String id : ids) {
            if (id != null && !"".equals(id)) {
                linkEJBDataAssociationfromAssociation(id);
            }
        }
    }

    private void linkEJBDataAssociationfromAssociation(String id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String umlAssociationId = processQueryResult(manager.query(id + ".association"))[0];
        String[] umlAssociationEndsIds = processQueryResult(manager.query(umlAssociationId + ".associationEnds"));

        String ejbDataAssociationId = processQueryResult(manager.query(id + ".ejbDataAssociation"))[0];

        // assoc.end <~> dataAssoc.end
        if (umlAssociationEndsIds != null && umlAssociationEndsIds.length > 0 && !"".equals(umlAssociationEndsIds[0])) {
            for (String associationEndId : umlAssociationEndsIds) {
                String ejbAssociationEndId = findEJBAssociationEndBasedonUmlAssociationEnd(associationEndId);
                targetDomain.insertEJBDataAssociationEndLinks(ejbDataAssociationId, ejbAssociationEndId);
            }
        }
        // assoc.getOuterMostContainer() <~> dataAssoc.package
//        String ejbDataSchemaId = processQueryResult(manager.query("UMLClassToEJBEntityComponent.allInstances()->select(a | a.class->includes(" + umlAssociationId + ".getOuterMostContainerFromAssociation())).dataSchema"))[0];
        String ejbDataSchemaId = processQueryResult(manager.query("UMLClassToEJBEntityComponent.allInstances()->select(a | a.class = " + umlAssociationId + ".getOuterMostContainerFromAssociation()).dataSchema"))[0];
        targetDomain.insertEJBDataAssociationSchemaLink(ejbDataAssociationId, ejbDataSchemaId);
    }
    //--------------------------------------------------------------------------
    // REGRA 6
    // Criacao

    private void transformUMLAssociationClasstoEJBDataClass() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("AssociationClass.allInstances()"));

        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            createEJBDataClassfromAssociationClass(id, name);
        }
    }

    private void createEJBDataClassfromAssociationClass(String id, String name) throws ContractException {
        String ejbDataClassName = name;
        String ejbDataClassId = ejbDataClassName + System.nanoTime();
        targetDomain.insertEJBDataClassStub(ejbDataClassId, ejbDataClassName);

        this.insertUMLAssociationClassToEJBDataClass(id, ejbDataClassId);
    }
    // Linkagem

    private void linkUMLAssociationClasstoEJBDataClass() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("UMLAssociationClassToEJBDataClass.allInstances()"));
        for (String id : ids) {
            if (id != null && !"".equals(id)) {
                linkEJBDataClassfromAssociationClass(id);
            }
        }
    }

    private void linkEJBDataClassfromAssociationClass(String id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String umlAssociationClassId = processQueryResult(manager.query(id + ".associationClass"))[0];
        String ejbDataClassId = processQueryResult(manager.query(id + ".ejbDataClass"))[0];

        // associationClass.feature->select(oclKindOf(Attribute) or oclKindOf(AssociationEnd)) <~> nonRootDataClass.feature
        // Separando...
        //   1) associationClass.feature->select(oclKindOf(Attribute)) <~> nonRootDataClass.feature
        String[] umlAttributesIds = processQueryResult(manager.query(umlAssociationClassId + ".feature->select(f | f.oclAsType(Feature).oclIsKindOf(Attribute))"));
        if (umlAttributesIds != null && umlAttributesIds.length > 0 && !"".equals(umlAttributesIds[0])) {
            for (String attributeId : umlAttributesIds) {
                String ejbAttributeId = processQueryResult(manager.query(attributeId + ".transformerToEjbAttribute.ejbAttribute"))[0];
                targetDomain.insertEJBAttributeClassLink(ejbAttributeId, ejbDataClassId);
            }
        }
        //   2) associationClass.feature->select(oclKindOf(AssociationEnd)) <~> nonRootDataClass.feature
        String[] umlAssociationEndsIds = processQueryResult(manager.query(umlAssociationClassId + ".feature->select(f | f.oclAsType(Feature).oclIsKindOf(AssociationEnd))"));
        if (umlAssociationEndsIds != null && umlAssociationEndsIds.length > 0 && !"".equals(umlAssociationEndsIds[0])) {
            for (String associationEndId : umlAssociationEndsIds) {
                String ejbAssociationEndId = findEJBAssociationEndBasedonUmlAssociationEnd(associationEndId);
                targetDomain.insertEJBAssociationEndClassLink(ejbAssociationEndId, ejbDataClassId);
            }
        }
        // associationClass.getOuterMostContainer() <~> nonRootClass.package
//        String ejbDataSchemaId = processQueryResult(manager.query("UMLClassToEJBEntityComponent.allInstances()->select(a | a.class->includes(" + umlAssociationClassId + ".getOuterMostContainer())).dataSchema"))[0];
        String ejbDataSchemaId = processQueryResult(manager.query("UMLClassToEJBEntityComponent.allInstances()->select(a | a.class = " + umlAssociationClassId + ".getOuterMostContainer()).dataSchema"))[0];
        targetDomain.insertEJBDataClassSchemaLink(ejbDataClassId, ejbDataSchemaId);
    }
    //--------------------------------------------------------------------------
    // REGRA 7
    // Criacao

    private void transformUMLAttributetoEJBAttribute() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("Attribute.allInstances()"));

        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            createEJBAttributefromAttribute(id, name);
        }
    }

    private void createEJBAttributefromAttribute(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String ejbAttributeName = name;
        String ejbAttributeId = ejbAttributeName + System.nanoTime();
        String ejbAttributeVisibility = manager.query(id + ".visibility").replace("'", "");
        targetDomain.insertEJBAttributeStub(ejbAttributeId, ejbAttributeName, ejbAttributeVisibility);

        this.insertUMLAttributeToEJBAttribute(id, ejbAttributeId);
    }
    // Linkagem

    private void linkUMLAttributetoEJBAttribute() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("UMLAttributeToEJBAttribute.allInstances()"));

        for (String id : ids) {
            if (id != null && !"".equals(id)) {
                linkEJBAttributefromAttribute(id);
            }
        }
    }

    private void linkEJBAttributefromAttribute(String id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String umlAttributeId = processQueryResult(manager.query(id + ".attribute"))[0];
        String umlAttributeTypeId = processQueryResult(manager.query(umlAttributeId + ".classifier"))[0];

        String ejbAttributeId = processQueryResult(manager.query(id + ".ejbAttribute"))[0];

        // umlAttribute.type <~> ejbAttribute.type
        String ejbAttributeTypeId = findEjbIdBasedonUmlId(umlAttributeTypeId);
        targetDomain.insertEJBAttributeTypeLink(ejbAttributeId, ejbAttributeTypeId);
    }
    //--------------------------------------------------------------------------
    // REGRA 8
    // Criacao

    private void transformRule8UMLAssociationEndtoEJBAssociationEnd() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("AssociationEnd.allInstances()->select(ae : AssociationEnd | ae.association.oclIsTypeOf(Association))->select(ae : AssociationEnd | ae.class.getOuterMostContainer() = ae.classifier.oclAsType(Class).getOuterMostContainer())"));

        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            createEJBAssociationEndfromAssociationEndusingRule8(id, name);
        }
    }

    private void createEJBAssociationEndfromAssociationEndusingRule8(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String ejbAssociationEndName = name;
        String ejbAssociationEndId = ejbAssociationEndName + System.nanoTime();
        String ejbAssociationEndUpper = manager.query(id + ".upper").replace("'", "");
        String ejbAssociationEndLower = manager.query(id + ".lower").replace("'", "");
        Boolean ejbAssociationEndComposition = "true".equals(manager.query(id + ".composition").replace("'", "")) ? true : false;
        targetDomain.insertEJBAssociationEndStub(ejbAssociationEndId, ejbAssociationEndName, ejbAssociationEndLower, ejbAssociationEndUpper, ejbAssociationEndComposition);

        this.insertUMLAssociationEndToEJBDataEndusingRule8(id, ejbAssociationEndId);
    }
    // Linkagem

    private void linkRule8UMLAssociationEndtoEJBAssociationEnd() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("UMLAssociationEndToEJBDataEndusingRule8.allInstances()"));

        for (String id : ids) {
            if (id != null && !"".equals(id)) {
                linkEJBAssociationEndfromAssociationEndusingRule8(id);
            }
        }
    }

    private void linkEJBAssociationEndfromAssociationEndusingRule8(String id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String umlAssociationEndId = processQueryResult(manager.query(id + ".associationEnd"))[0];
        String ejbAssociationEndId = processQueryResult(manager.query(id + ".ejbAssociationEnd"))[0];

        // umlAssociationEnd.type <~> ejbAssociationEnd.type.oclAsType(EJB::EJBDataClass)
        String umlAssociationEndTypeId = processQueryResult(manager.query(umlAssociationEndId + ".classifier"))[0];
        String ejbAssociationEndTypeId = findEjbDataClassBasedonUmlId(umlAssociationEndTypeId);
        targetDomain.insertEJBAssociationEndTypeLink(ejbAssociationEndId, ejbAssociationEndTypeId);
    }
    //--------------------------------------------------------------------------
    // REGRA 9
    // Criacao

    private void transformRule9UMLAssociationEndtoEJBAssociationEnd() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("AssociationEnd.allInstances()->select(ae : AssociationEnd | ae.association.oclIsTypeOf(Association))->select(ae : AssociationEnd | not (ae.class.getOuterMostContainer() = ae.classifier.oclAsType(Class).getOuterMostContainer()))"));

        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            createEJBAssociationEndfromAssociationEndusingRule9(id, name);
        }
    }

    private void createEJBAssociationEndfromAssociationEndusingRule9(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String ejbAssociationEndName = name;
        String ejbAssociationEndId = ejbAssociationEndName + System.nanoTime();
        String ejbAssociationEndUpper = manager.query(id + ".upper").replace("'", "");
        String ejbAssociationEndLower = manager.query(id + ".lower").replace("'", "");
        Boolean ejbAssociationEndComposition = "true".equals(manager.query(id + ".composition").replace("'", "")) ? true : false;
        targetDomain.insertEJBAssociationEndStub(ejbAssociationEndId, ejbAssociationEndName, ejbAssociationEndLower, ejbAssociationEndUpper, ejbAssociationEndComposition);

        this.insertUMLAssociationEndToEJBDataEndusingRule9(id, ejbAssociationEndId);
    }
    // Linkagem

    private void linkRule9UMLAssociationEndtoEJBAssociationEnd() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("UMLAssociationEndToEJBDataEndusingRule9.allInstances()"));

        for (String id : ids) {
            if (id != null && !"".equals(id)) {
                linkEJBAssociationEndfromAssociationEndusingRule9(id);
            }
        }
    }

    private void linkEJBAssociationEndfromAssociationEndusingRule9(String id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String umlAssociationEndId = processQueryResult(manager.query(id + ".associationEnd"))[0];
        String ejbAssociationEndId = processQueryResult(manager.query(id + ".ejbAssociationEnd"))[0];

        // umlAssociationEnd.type <~> ejbAssociationEnd.type.oclAsType(EJB::EJBKeyClass)
        String umlAssociationEndTypeId = processQueryResult(manager.query(umlAssociationEndId + ".classifier"))[0];
        String ejbAssociationEndTypeId = findEjbKeyClassBasedonUmlId(umlAssociationEndTypeId);
        targetDomain.insertEJBAssociationEndTypeLink(ejbAssociationEndId, ejbAssociationEndTypeId);
    }
    //--------------------------------------------------------------------------
    // REGRA 10
    // Criacao

    private void transformRule10UMLAssociationEndtoEJBAssociation() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("AssociationEnd.allInstances()->select(ae : AssociationEnd | ae.upper <> '1')->select(ae : AssociationEnd | ae.association.oclIsTypeOf(AssociationClass))->select(ae : AssociationEnd | ae.association.oclAsType(AssociationClass).getOuterMostContainerFromAssociationClass() = ae.classifier->asOrderedSet()->first().oclAsType(Class).getOuterMostContainer())"));

        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            createEJBAssociationEndfromAssociationusingRule10(id, name);
        }
    }

    private void createEJBAssociationEndfromAssociationusingRule10(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();

        // Cria EJBAssociation
        String ejbAssociationName = "";
        String ejbAssociationId = ejbAssociationName + System.nanoTime();
        targetDomain.insertEJBDataAssociationStub(ejbAssociationId, ejbAssociationName);

        // Cria EJBAssociationEnd 1
        String ejbAssociationEnd1Name = manager.query(id + ".association.name").replace("'", "");
        String ejbAssociationEnd1Id = ejbAssociationEnd1Name + System.nanoTime();
        String ejbAssociationEnd1Lower = "0";
        String ejbAssociationEnd1Upper = "*";
        Boolean ejbAssociationEnd1Composition = false;
        targetDomain.insertEJBAssociationEndStub(ejbAssociationEnd1Id, ejbAssociationEnd1Name, ejbAssociationEnd1Lower, ejbAssociationEnd1Upper, ejbAssociationEnd1Composition);

        // Cria EJBAssociationEnd 2
        String ejbAssociationEnd2Name = processQueryResult(manager.query(id + ".classifier.name"))[0];
        String ejbAssociationEnd2Id = ejbAssociationEnd2Name + System.nanoTime();
        String ejbAssociationEnd2Lower = "1";
        String ejbAssociationEnd2Upper = "1";
        Boolean ejbAssociationEnd2Composition = "true".equals(manager.query(id + ".composition").replace("'", "")) ? true : false;
        targetDomain.insertEJBAssociationEndStub(ejbAssociationEnd2Id, ejbAssociationEnd2Name, ejbAssociationEnd2Lower, ejbAssociationEnd2Upper, ejbAssociationEnd2Composition);

        this.insertUMLAssociationEndEmEJBAssociationusingRule10(id, ejbAssociationId, ejbAssociationEnd1Id, ejbAssociationEnd2Id);
    }
    // Linkagem

    private void linkRule10UMLAssociationEndtoEJBAssociation() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("UMLAssociationEndEmEJBAssociationusingRule10.allInstances()"));

        for (String id : ids) {
            if (id != null && !"".equals(id)) {
                linkEJBAssociationEndfromAssociationusingRule10(id);
            }
        }
    }

    private void linkEJBAssociationEndfromAssociationusingRule10(String id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String umlAssociationEndId = processQueryResult(manager.query(id + ".associationEnd"))[0];
        String ejbDataAssociationId = processQueryResult(manager.query(id + ".ejbDataAssociation"))[0];
        String ejbAssociationEnd1Id = processQueryResult(manager.query(id + ".ejbAssociationEnd1"))[0];
        String ejbAssociationEnd2Id = processQueryResult(manager.query(id + ".ejbAssociationEnd2"))[0];

        // ejbAssociationEnd1.association = ejbAssociation && ejbAssociationEnd2.association = ejbAssociation
        targetDomain.insertEJBDataAssociationEndLinks(ejbDataAssociationId, ejbAssociationEnd1Id, ejbAssociationEnd2Id);

        // umlAssociationEnd.type <~> ejbAssociationEnd2.type.oclAsType(EJB::EJBDataClass)
        String umlAssociationEndTypeId = processQueryResult(manager.query(umlAssociationEndId + ".classifier"))[0];
        String ejbDataClassId = findEjbDataClassBasedonUmlId(umlAssociationEndTypeId);
        targetDomain.insertEJBAssociationEndTypeLink(ejbAssociationEnd2Id, ejbDataClassId);
        // umlAssociationEnd.type <~> ejbAssociationEnd1.class.oclAsType(EJB::EJBDataClass)
        targetDomain.insertEJBAssociationEndClassLink(ejbAssociationEnd1Id, ejbDataClassId);

        // umlAssociationEnd.association <~> ejbAssociationEnd1.type.oclAsType(EJB::EJBDataClass)
        String umlAssociationClassId = processQueryResult(manager.query(umlAssociationEndId + ".association"))[0];
        ejbDataClassId = findEjbDataClassBasedonUmlId(umlAssociationClassId);
        targetDomain.insertEJBAssociationEndTypeLink(ejbAssociationEnd1Id, ejbDataClassId);
        // umlAssociationEnd.association <~> ejbAssociationEnd2.class.oclAsType(EJB::EJBDataClass)
        targetDomain.insertEJBAssociationEndClassLink(ejbAssociationEnd2Id, ejbDataClassId);
    }
    //--------------------------------------------------------------------------
    // REGRA 11
    // Criacao

    private void transformRule11UMLAssociationEndtoEJBAssociation() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("AssociationEnd.allInstances()->select(ae : AssociationEnd | ae.upper <> '1')->select(ae : AssociationEnd | ae.association.oclIsTypeOf(AssociationClass))->select(ae : AssociationEnd | ae.association.oclAsType(AssociationClass).getOuterMostContainerFromAssociationClass() <> ae.classifier->asOrderedSet()->first().oclAsType(Class).getOuterMostContainer())"));

        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            createEJBAssociationEndfromAssociationusingRule11(id, name);
        }
    }

    private void createEJBAssociationEndfromAssociationusingRule11(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();

        // Cria EJBAssociationEnd 2
        String ejbAssociationEnd2Name = processQueryResult(manager.query(id + ".classifier.name"))[0];
        String ejbAssociationEnd2Id = ejbAssociationEnd2Name + System.nanoTime();
        String ejbAssociationEnd2Lower = "1";
        String ejbAssociationEnd2Upper = "1";
        Boolean ejbAssociationEnd2Composition = "true".equals(manager.query(id + ".composition").replace("'", "")) ? true : false;
        targetDomain.insertEJBAssociationEndStub(ejbAssociationEnd2Id, ejbAssociationEnd2Name, ejbAssociationEnd2Lower, ejbAssociationEnd2Upper, ejbAssociationEnd2Composition);

        this.insertUMLAssociationEndEmEJBAssociationusingRule11(id, ejbAssociationEnd2Id);
    }
    // Linkagem

    private void linkRule11UMLAssociationEndtoEJBAssociation() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("UMLAssociationEndEmEJBAssociationusingRule11.allInstances()"));
        for (String id : ids) {
            if (id != null && !"".equals(id)) {
                linkEJBAssociationEndfromAssociationusingRule11(id);
            }
        }
    }

    private void linkEJBAssociationEndfromAssociationusingRule11(String id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String umlAssociationEndId = processQueryResult(manager.query(id + ".associationEnd"))[0];
        String ejbAssociationEndId = processQueryResult(manager.query(id + ".ejbAssociationEnd2"))[0];

        // umlAssociationEnd.type <~> ejbAssociationEnd2.type.oclAsType(EJB::EJBKeyClass)
        String umlAssociationTypeId = processQueryResult(manager.query(umlAssociationEndId + ".classifier"))[0];
        targetDomain.insertEJBAssociationEndTypeLink(ejbAssociationEndId, findEjbKeyClassBasedonUmlId(umlAssociationTypeId));

        // umlAssociationEnd.association <~> ejbAssociationEnd2.class.oclAsType(EJB::EJBDataClass)
        String umlAssociationClassId = processQueryResult(manager.query(umlAssociationEndId + ".association"))[0];
        targetDomain.insertEJBAssociationEndClassLink(ejbAssociationEndId, findEjbDataClassBasedonUmlId(umlAssociationClassId));
    }
    //--------------------------------------------------------------------------
    // REGRA 12
    // Criacao

    private void transformRuleUMLOperationtoBusinessMethod() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("Operation.allInstances()"));

        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            if (!"NULL_OPERATION".equals(id)) {
                createBusinessMethodfromOperation(id, name);
            }
        }
    }

    private void createBusinessMethodfromOperation(String id, String name) throws ContractException {
        String businessMethodName = name;
        String businessMethodId = name + System.nanoTime();

        targetDomain.insertBusinessMethodStub(businessMethodId, businessMethodName);

        this.insertUMLOperationToBusinessMethod(id, businessMethodId);
    }
    // Linkagem

    private void linkRuleUMLOperationtoBusinessMethod() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("UMLOperationToBusinessMethod.allInstances()"));

        for (String id : ids) {
            if (id != null && !"".equals(id)) {
                linkBusinessMethodfromOperation(id);
            }
        }
    }

    private void linkBusinessMethodfromOperation(String id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String umlOperationId = processQueryResult(manager.query(id + ".operation"))[0];
        String[] umlOperationParameterIds = processQueryResult(manager.query(umlOperationId + ".parameter"));
        String umlOperationTypeId = processQueryResult(manager.query(umlOperationId + ".classifier"))[0];

        String businessMethodId = processQueryResult(manager.query(id + ".businessMethod").replace("'", ""))[0];

        // umlOperation.type <~> businessMethod.type        !!! Nao esta no livro !!!
        String businessMethodTypeId = findEjbIdBasedonUmlId(umlOperationTypeId);
        targetDomain.insertBusinessMethodTypeLink(businessMethodId, businessMethodTypeId);

        // umlOperation.parameter <~> businessMethod.parameter
        if (umlOperationParameterIds != null && umlOperationParameterIds.length > 0 && !"".equals(umlOperationParameterIds[0])) {
            for (String parameterId : umlOperationParameterIds) {
                String ejbParameterId = processQueryResult(manager.query(parameterId + ".transformerToEjbParameter.ejbParameter"))[0];
                targetDomain.insertEJBParameterBusinessMethodLink(ejbParameterId, businessMethodId);
            }
        }
    }
    //--------------------------------------------------------------------------
    // REGRA 13
    // Criacao

    private void transformRuleUMLParametertoEJBParameter() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("Parameter.allInstances()"));

        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            createEJBParameterfromParameter(id, name);
        }
    }

    private void createEJBParameterfromParameter(String id, String name) throws ContractException {
        String ejbParameterName = name;
        String ejbParameterId = ejbParameterName + System.nanoTime();

        targetDomain.insertEJBParameterStub(ejbParameterId, ejbParameterName);

        this.insertUMLParameterToEJBParameter(id, ejbParameterId);
    }
    // Linkagem

    private void linkUMLParametertoEJBParameter() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("UMLParameterToEJBParameter.allInstances()"));
        for (String id : ids) {
            if (id != null && !"".equals(id)) {
                linkEJBParameterfromParameter(id);
            }
        }
    }

    private void linkEJBParameterfromParameter(String id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String umlParameterId = processQueryResult(manager.query(id + ".parameter"))[0];
        String umlParameterTypeId = processQueryResult(manager.query(umlParameterId + ".classifier"))[0];

        String ejbParameterId = processQueryResult(manager.query(id + ".ejbParameter"))[0];

        // umlParameter.type <~> ejbParameter.type
        String ejbParameterTypeId = findEjbIdBasedonUmlId(umlParameterTypeId);
        targetDomain.insertEJBParameterTypeLink(ejbParameterId, ejbParameterTypeId);
    }
    //--------------------------------------------------------------------------
    // MINHA REGRA
    // Criacao

    private void transformUMLSettoEJBSet() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("UMLSet.allInstances()"));

        for (String id : ids) {
            String name = manager.query(id + ".name").replace("'", "");
            createEJBSetfromUMLSet(id, name);
        }
    }

    private void createEJBSetfromUMLSet(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String ejbSetName = name;
        String ejbSetId = ejbSetName + System.nanoTime();

        targetDomain.insertEJBSetStub(ejbSetId, ejbSetName);

        this.insertUMLSetToEJBSet(id, ejbSetId);
    }
    // Linkagem

    private void linkUMLSettoEJBSet() throws ContractException {
        ModelManager manager = ModelManager.instance();

        String[] ids = processQueryResult(manager.query("UMLSetToEJBSet.allInstances()"));
        for (String id : ids) {
            if (id != null && !"".equals(id)) {
                linkEJBSetfromUMLSet(id);
            }
        }
    }

    private void linkEJBSetfromUMLSet(String id) throws ContractException {
        ModelManager manager = ModelManager.instance();

        String umlSetId = processQueryResult(manager.query(id + ".umlSet"))[0];
        String umlSetTypeId = processQueryResult(manager.query(umlSetId + ".elementType"))[0];

        String ejbSetId = processQueryResult(manager.query(id + ".ejbSet"))[0];

        // umlSet.type <~> ejbSet.type
//        String ejbSetTypeId = findEjbDataClassBasedonUmlId(umlSetTypeId);
        String ejbSetTypeId = findEjbIdBasedonUmlId(umlSetTypeId);
        targetDomain.insertEJBSetTypeLink(ejbSetId, ejbSetTypeId);
    }
}
