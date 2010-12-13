/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webml;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.IXMIParser;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.syntax.webml.commonelements.CommonElementsPackage;
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

    private IXMIParser parser;
    private ModelManager manager;

    protected WebMLXMIParser(IXMIParser parser) {
        this.parser = parser;
        this.manager = ModelManager.instance();
    }

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
                throw new ContractException("Class not stereotyped: " + umlclass.getName());
            } else {
                insertStereotypedClass(umlclass.getId(), umlclass.getName(), umlclass.getStereotypeRefId());
            }
        }

    }

    private boolean insertStereotypedClass(String id, String name, String stereotypeId) throws ContractException {
        boolean result = true;
        final String attName = "name";
        String stereotypeName = getStereoTypeName(stereotypeId);
        //String stereotypeObjId = JDHelper.getId(stereotypeName);
        result &= manager.insertObject(stereotypeName, id);
        result &= insertValue(stereotypeName, attName, id, name);

        //System.out.println("Insert object: " + stereotypeName + " id: " + id);
        return result;
    }

    private boolean insertOperation(String id, String name, String visibility, String returnType, String classId) throws ContractException {
        final String operation = CommonElementsPackage.UMLOPERATION;
        final String classifier = CommonElementsPackage.UMLCLASSIFIER;
        final String className = CommonElementsPackage.UMLCLASS;

        boolean result = insertObject(operation, id, name);
        result &= insertValue(operation, "visibility", id, visibility);
        result &= manager.insertLink(operation, id, "types", "classifier", returnType, classifier);
        result &= manager.insertLink(operation, id, "feature", "class", classId, className);
        return result;
    }

    private boolean insertParameter(String id, String name, String type, String operationId) throws ContractException {
        final String parameter = CommonElementsPackage.UMLPARAMETER;
        final String classifier = CommonElementsPackage.UMLCLASSIFIER;
        final String operation = CommonElementsPackage.UMLOPERATION;

        boolean result = insertObject(parameter, id, name);

        result &= manager.insertLink(parameter, id, "types", "classifier", type, classifier);
        result &= manager.insertLink(parameter, id, "parameter", "operation", operationId, operation);

        return result;
    }

    private boolean insertSet(String id, String name, String elementType) throws ContractException {
        final String UMLSET = CommonElementsPackage.UMLUMLSET;
        final String classifier = CommonElementsPackage.UMLCLASSIFIER;

        boolean result = insertObject(UMLSET, id, name);
        result &= manager.insertLink(UMLSET, id, "setA", "elementType", elementType, classifier);
        return result;
    }

    private boolean insertObject(String className, String classId, String objectName) throws ContractException {
        boolean result = true;
        final String attName = "name";
        result &= manager.insertObject(className, classId);
        result &= insertValue(className, attName, classId, objectName);
        return result;
    }

    private boolean insertValue(String className, String attributeName, String objectName, String value) throws ContractException {
        return manager.insertValue(className, attributeName, objectName, value == null ? "" : value);
    }

    private boolean insertValue(String className, String attributeName, String objectName, Boolean value) throws ContractException {
        return manager.insertValue(className, attributeName, objectName, value == true ? "true" : "false");
    }

    private void insertAttributes() throws ContractException {
        Set<Entry<String, UMLClass>> classSet = parser.getClasses().entrySet();
        for (Entry<String, UMLClass> classEntry : classSet) {
            List<UMLAttribute> attributeArray = classEntry.getValue().getAttributes();
            for (UMLAttribute attribute : attributeArray) {
                String attributeTypeName = null;
                String attributeTypeId = null;
                if (parser.getDataTypes().containsKey(attribute.getType())) {
                    attributeTypeName = ((UMLElement) parser.getDataTypes().get(attribute.getType())).getName();
                    attributeTypeId = queryType(attributeTypeName);
                    if (attributeTypeId == null || "null".equals(attributeTypeId)) {
                        throw new ContractException("Attribute (" + attribute.getType() + ") type not found in class" + classEntry.getValue().getName() + " " + classEntry.getValue().getId());
                    }
                } else {
                    throw new ContractException("Attribute (" + attribute.getType() + ") type not found in class" + classEntry.getValue().getName() + " " + classEntry.getValue().getId());
                }
                this.insertAttribute(attribute.getId(), attribute.getName(), attribute.getVisibility(), attributeTypeId, classEntry.getValue());
            }
        }
    }

    private boolean insertAttribute(String attrid, String name, String visibility, String type, UMLClass classInstance) throws ContractException {
        if (!classInstance.hasStereotype()) {
            throw new ContractException("Class not stereotyped:" + classInstance.getName());
        }

        String classifer = CommonElementsPackage.UMLCLASSIFIER;
        String attribute = CommonElementsPackage.UMLATTRIBUTE;
        String steretypeName = getStereoTypeName(classInstance.getStereotypeRefId());

        boolean result = true;
        result &= manager.insertObject(attribute, attrid);
        result &= insertValue(attribute, "name", attrid, name);
        result &= insertValue(attribute, "visibility", attrid, visibility);
        result &= manager.insertLink(attribute, attrid, "types", "classifier", type, classifer);
        result &= manager.insertLink(attribute, attrid, "feature", "class", classInstance.getId(), steretypeName);

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
        result &= insertValue(associationEnd, "visibility", id, association.getVisibility());
        result &= insertValue(associationEnd, "lower", id, lower);
        result &= insertValue(associationEnd, "upper", id, upper);
        result &= insertValue(associationEnd, "composition", id, association.isAggregation());
        result &= manager.insertLink(associationEnd, id, "types", "classifier", typeInstance.getId(), CommonElementsPackage.UMLCLASSIFIER);
        result &= manager.insertLink(associationEnd, id, "feature", "class", classInstance.getId(), CommonElementsPackage.UMLCLASS);
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
            result &= manager.insertLink(associationEnd, associationEndId, "associationEnds", "association", id, association);
        }
        return result;
    }

    private boolean insertLinksBetweenAssociationEnds(String associationEnd, String otherEnd) throws ContractException {
        final String associationEndClass = CommonElementsPackage.UMLASSOCIATIONEND;

        boolean result = true;
        result &= manager.insertLink(associationEndClass, associationEnd, "others", "otherEnd", otherEnd, associationEndClass);
        result &= manager.insertLink(associationEndClass, otherEnd, "others", "otherEnd", associationEnd, associationEndClass);
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
                String parameterType = getParameterType(operation,parameter);
                this.insertParameter(parameter.getId(), parameter.getName(), parameterType, operation.getId());
            }
        }
    }

    private String getOperationType(UMLElement element, String typeId) throws ContractException {
        if ("List".equals(element.getName())) {
            String setId = "ID" + System.nanoTime();
            this.insertSet(setId, element.getName(), "UMLVoid");
            return setId;
        } else {
            String operationType = queryType(element.getName());
            if (operationType == null || "null".equals(operationType)) {
                operationType = typeId;
            }
            return operationType;
        }
    }

    private String queryType(String attributeTypeName) throws ContractException {
        final String startQuery = CommonElementsPackage.UMLDATATYPE + ".allInstances()->select(dt | dt.name = '";
        final String endQuery = "')->asOrderedSet()->first()";
        return manager.query(startQuery + attributeTypeName + endQuery);
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

    private String getParameterType(UMLOperation operation,UMLElement parameter) throws ContractException {
        try {
            String parameterType = operation.getParameterType(parameter.getId());
            UMLElement pElement = (UMLElement) parser.getDataTypes().get(processID(parameterType));
            if (pElement != null) {
                // Eh um DataType
                if ("List".equals(pElement.getName())) {
                    String setId = "ID" + System.nanoTime();
                    this.insertSet(setId, pElement.getName(), "UMLVoid");
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
