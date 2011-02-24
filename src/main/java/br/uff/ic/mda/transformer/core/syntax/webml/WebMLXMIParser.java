/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webml;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.IXMIParser;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.util.JDHelper;
import br.uff.ic.mda.xmiparser.uml.UMLAssociation;
import br.uff.ic.mda.xmiparser.uml.UMLAssociationEnd;
import br.uff.ic.mda.xmiparser.uml.UMLAttribute;
import br.uff.ic.mda.xmiparser.uml.UMLClass;
import br.uff.ic.mda.xmiparser.uml.UMLElement;
import br.uff.ic.mda.xmiparser.uml.UMLOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Daniel
 */
public class WebMLXMIParser {
    private static final String ATTR_COMPOSITION = "composition";
    private static final String ATTR_LOWER = "lower";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_UPPER = "upper";
    private static final String ATTR_VISIBILITY = "visibility";
    private static final String ROLE_ASSOCIATION = "association";
    private static final String ROLE_ASSOCIATIONENDS = "associationEnds";
    private static final String ROLE_CLASS = "class";
    private static final String ROLE_CLASSIFIER = "classifier";
    private static final String ROLE_ELEMENTTYPE = "elementType";
    private static final String ROLE_FEATURE = "feature";
    private static final String ROLE_OPERATION = "operation";
    private static final String ROLE_OTHEREND = "otherEnd";
    private static final String ROLE_OTHERS = "others";
    private static final String ROLE_PARAMETER = "parameter";
    private static final String ROLE_SETA = "setA";
    private static final String ROLE_TYPES = "types";

    private IXMIParser parser;
    private ModelManager manager = ModelManager.instance();

    /**
     *
     * @param parser
     */
    protected WebMLXMIParser(IXMIParser parser) {
        this.parser = parser;
    }

    /**
     *
     * @param parser
     * @throws ContractException
     */
    public static void parse(IXMIParser parser) throws ContractException {
        new WebMLXMIParser(parser).parse();
    }

    private void parse() throws ContractException {

        //Inserting classes
        insertClasses();

        //Inserting attributes
        insertAttributes();

        //Inserting Association End
        insertAssociationEnd();

        //Inserting Methods
        insertMethods();
    }

    private void insertClasses() throws ContractException {
        Set<Entry<String, UMLClass>> classSet = parser.getClasses().entrySet();
        for (Entry<String, UMLClass> classEntry : classSet) {
            UMLClass umlclass = classEntry.getValue();
            if (!umlclass.hasStereotype()) {
                throw new ContractException("Class not stereotyped: "
                        + umlclass.getName());
            } else {
                insertStereotypedClass(umlclass.getId(),
                        umlclass.getName(), umlclass.getStereotypeRefId());
            }
        }
    }

    private boolean insertStereotypedClass(String id, String name, String stereotypeId)
            throws ContractException {
        boolean result = true;
        final String attName = ATTR_NAME;
        String stereotypeName = getStereoTypeName(stereotypeId);
        result &= manager.insertObject(stereotypeName, id);
        result &= insertValue(stereotypeName, attName, id, name);
        return result;
    }

    private void insertAttributes() throws ContractException {
        Set<Entry<String, UMLClass>> classSet = parser.getClasses().entrySet();
        for (Entry<String, UMLClass> classEntry : classSet) {
            for (UMLAttribute attribute : classEntry.getValue().getAttributes()) {
                String attributeTypeName = null;
                String attributeTypeId = null;
                if (parser.getDataTypes().containsKey(attribute.getType())) {
                    attributeTypeName = ((UMLElement) parser.getDataTypes().get(attribute.getType())).getName();
                    attributeTypeId = queryType(attributeTypeName);
                    if (attributeTypeId == null || "null".equals(attributeTypeId)) {
                        throw new ContractException(attribute.getName() + "(" + attribute.getType()
                                + ") type not found in class " + classEntry.getValue().getName()
                                + " " + classEntry.getValue().getId());
                    }
                } else {
                    throw new ContractException(attribute.getName() + "(" + attribute.getType()
                            + ") type not found in class " + classEntry.getValue().getName()
                            + " " + classEntry.getValue().getId());
                }
                this.insertAttribute(attribute.getId(), attribute.getName(), attribute.getVisibility(), attributeTypeId, classEntry.getValue());
            }
        }
    }

    private boolean insertAttribute(String attrId, String name, String visibility, String type, UMLClass classInstance) throws ContractException {
        if (!classInstance.hasStereotype()) {
            throw new ContractException("Class not stereotyped:" + classInstance.getName());
        }

        String classifer = CommonElementsPackage.UMLCLASSIFIER;
        String attribute = CommonElementsPackage.UMLATTRIBUTE;
        String steretypeName = getStereoTypeName(classInstance.getStereotypeRefId());

        boolean result = true;
        result &= manager.insertObject(attribute, attrId);
        result &= insertValue(attribute, ATTR_NAME, attrId, name);
        result &= insertValue(attribute, ATTR_VISIBILITY, attrId, visibility);
        result &= manager.insertLink(attribute, attrId, ROLE_TYPES, ROLE_CLASSIFIER, type, classifer);
        result &= manager.insertLink(attribute, attrId, ROLE_FEATURE, ROLE_CLASS, classInstance.getId(), steretypeName);

        return result;
    }

    private boolean insertObject(String className, String classId, String objectName) throws ContractException {
        boolean result = true;
        final String attName = ATTR_NAME;
        result &= manager.insertObject(className, classId);
        result &= insertValue(className, attName, classId, objectName);
        return result;
    }

    private void insertAssociationEnd() throws ContractException {
        Set<Entry<String, UMLAssociation>> associationSet = parser.getAssociationMap().entrySet();

        for (Entry<String, UMLAssociation> associationEntry : associationSet) {

            Map<String, UMLAssociationEnd> associationEndMap = associationEntry.getValue().getAssociationEndMap();

            if (associationEndMap.size() != 2) {
                throw new ContractException("Error in associations end size for " + associationEntry.getValue().getId() + " association");
            }

            Set<Entry<String, UMLAssociationEnd>> associationEndSet = associationEndMap.entrySet();
            ArrayList<UMLAssociationEnd> associationEndArray = new ArrayList<UMLAssociationEnd>();

            for (Entry<String, UMLAssociationEnd> associationEnd : associationEndSet) {
                associationEndArray.add(associationEnd.getValue());
            }

            UMLAssociationEnd association2 = associationEndArray.get(0);
            UMLAssociationEnd association1 = associationEndArray.get(1);
            UMLClass class1 = (UMLClass) parser.getClasses().get(association1.getParticipantClassId());
            UMLClass class2 = (UMLClass) parser.getClasses().get(association2.getParticipantClassId());

            this.insertAssociationEnd(association2, class2, class1);
            this.insertAssociationEnd(association1, class1, class2);
            this.insertLinksBetweenAssociationEnds(association2.getId(), association1.getId());
            this.insertAssociation(associationEntry.getValue().getId(), associationEntry.getValue().getName(), association2.getId(), association1.getId());
        }
    }

    private boolean insertAssociationEnd(UMLAssociationEnd association, UMLClass typeInstance, UMLClass classInstance) throws ContractException {
        final String associationEnd = CommonElementsPackage.UMLASSOCIATIONEND;

        String id = association.getId();
        String lower = String.valueOf(association.getLower());
        String upper = String.valueOf(association.getUpper());

        boolean result = true;
        result &= insertObject(associationEnd, id, association.getName());
        result &= insertValue(associationEnd, ATTR_VISIBILITY, id, association.getVisibility());
        result &= insertValue(associationEnd, ATTR_LOWER, id, lower);
        result &= insertValue(associationEnd, ATTR_UPPER, id, upper);
        result &= insertValue(associationEnd, ATTR_COMPOSITION, id, association.isAggregation());
        result &= manager.insertLink(associationEnd, id, ROLE_TYPES, ROLE_CLASSIFIER, typeInstance.getId(), CommonElementsPackage.UMLCLASSIFIER);
        result &= manager.insertLink(associationEnd, id, ROLE_FEATURE, ROLE_CLASS, classInstance.getId(), CommonElementsPackage.UMLCLASS);
        return result;
    }

    private boolean insertAssociation(String id, String name, String... associationEndIds) throws ContractException {
        final String association = CommonElementsPackage.UMLASSOCIATION;
        final String associationEnd = CommonElementsPackage.UMLASSOCIATIONEND;

        if (associationEndIds.length < 2) {
            throw new ContractException(association + " can't have less than 2 " + associationEnd);
        }
        boolean result = true;
        result &= insertObject(association, id, name);
        for (String associationEndId : associationEndIds) {
            result &= manager.insertLink(associationEnd, associationEndId, ROLE_ASSOCIATIONENDS, ROLE_ASSOCIATION, id, association);
        }
        return result;
    }

    private boolean insertLinksBetweenAssociationEnds(String associationEnd, String otherEnd) throws ContractException {
        final String associationEndClass = CommonElementsPackage.UMLASSOCIATIONEND;

        boolean result = true;
        result &= manager.insertLink(associationEndClass, associationEnd, ROLE_OTHERS, ROLE_OTHEREND, otherEnd, associationEndClass);
        result &= manager.insertLink(associationEndClass, otherEnd, ROLE_OTHERS, ROLE_OTHEREND, associationEnd, associationEndClass);
        return result;
    }

    private void insertMethods() throws ContractException {
        Set<Entry<String, UMLClass>> classSet = parser.getClasses().entrySet();
        for (Entry<String, UMLClass> classeEntry : classSet) {
            List<UMLOperation> operationArray = classeEntry.getValue().getOperations();
            insertMethodOperations(classeEntry.getValue(), operationArray);
        }
    }

    private void insertMethodOperations(UMLClass classInst, List<UMLOperation> operationArray) throws ContractException {
        for (UMLOperation operation : operationArray) {
            //Return
            String typeId = processID(operation.getType());
            UMLElement element = (UMLElement) parser.getDataTypes().get(typeId);
            String operationType = getOperationType(element, typeId);
            this.insertOperation(operation.getId(), operation.getName(), operation.getVisibility(), operationType, classInst.getId());

            //Parameters
            for (UMLElement parameter : operation.getParameters()) {
                String parameterType = getParameterType(operation, parameter);
                this.insertParameter(parameter.getId(), parameter.getName(), parameterType, operation.getId());
            }
        }
    }

    private boolean insertOperation(String id, String name, String visibility, String returnType, String classId) throws ContractException {
        final String operation = CommonElementsPackage.UMLOPERATION;
        final String classifier = CommonElementsPackage.UMLCLASSIFIER;
        final String className = CommonElementsPackage.UMLCLASS;

        boolean result = insertObject(operation, id, name);
        result &= insertValue(operation, ATTR_VISIBILITY, id, visibility);
        result &= manager.insertLink(operation, id, ROLE_TYPES, ROLE_CLASSIFIER, returnType, classifier);
        result &= manager.insertLink(operation, id, ROLE_FEATURE, ROLE_CLASS, classId, className);
        return result;
    }

    private boolean insertParameter(String id, String name, String type, String operationId) throws ContractException {
        final String parameter = CommonElementsPackage.UMLPARAMETER;
        final String classifier = CommonElementsPackage.UMLCLASSIFIER;
        final String operation = CommonElementsPackage.UMLOPERATION;

        boolean result = insertObject(parameter, id, name);

        result &= manager.insertLink(parameter, id, ROLE_TYPES, ROLE_CLASSIFIER, type, classifier);
        result &= manager.insertLink(parameter, id, ROLE_PARAMETER, ROLE_OPERATION, operationId, operation);

        return result;
    }

    private boolean insertValue(String className, String attributeName, String objectName, String value) throws ContractException {
        return manager.insertValue(className, attributeName, objectName, value == null ? "" : value);
    }

    private boolean insertValue(String className, String attributeName, String objectName, Boolean value) throws ContractException {
        return manager.insertValue(className, attributeName, objectName, value == true ? "true" : "false");
    }

    private String getOperationType(UMLElement element, String typeId) throws ContractException {
        if ("List".equals(element.getName())) {
            String setId = JDHelper.getNewId();
            this.insertSet(setId, element.getName(), CommonElementsPackage.JAVAVOID);
            return setId;
        } else {
            String operationType = queryType(element.getName());
            if (operationType == null || "null".equals(operationType)) {
                operationType = typeId;
            }
            return operationType;
        }
    }

    private boolean insertSet(String id, String name, String elementType) throws ContractException {
        final String umlSet = CommonElementsPackage.UMLUMLSET;
        final String classifier = CommonElementsPackage.UMLCLASSIFIER;

        boolean result = insertObject(umlSet, id, name);
        result &= manager.insertLink(umlSet, id, ROLE_SETA, ROLE_ELEMENTTYPE, elementType, classifier);
        return result;
    }

    private String queryType(String attributeTypeName) throws ContractException {
        final String startQuery = CommonElementsPackage.UMLDATATYPE + ".allInstances()->select(dt | dt.name = '";
        final String endQuery = "')->asOrderedSet()->first()";
        try {
            return manager.query(startQuery + attributeTypeName + endQuery);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    private String processID(String oldID) {
        return "ID".concat(oldID).replace("\\.", "").replace(".", "").replace(":", "").replace("-", "").replace(" ", "");
    }

    private String getStereoTypeName(String stereotypeId) throws ContractException {
        String name = parser.getStereoTypeName(stereotypeId);
        if (name == null) {
            throw new ContractException("Missing stereotype");
        }
        return WebMLBasicPackage.PREFIX + name;
    }

    private String getParameterType(UMLOperation operation, UMLElement parameter) throws ContractException {
        try {
            String parameterType = operation.getParameterType(parameter.getId());
            UMLElement pElement = (UMLElement) parser.getDataTypes().get(processID(parameterType));
            if (pElement != null) {
                // Eh um DataType
                if ("List".equals(pElement.getName())) {
                    String setId = JDHelper.getNewId();
                    this.insertSet(setId, pElement.getName(), CommonElementsPackage.JAVAVOID);
                    parameterType = setId;
                } else {
                    parameterType = queryType(pElement.getName());
                    if (parameterType != null) {
                        return parameterType;
                    }
                }
            }
            return processID(operation.getParameterType(parameter.getId()));
        } catch (Exception ex) {
            throw new ContractException("Can't recover parameter type", ex);
        }
    }
}
