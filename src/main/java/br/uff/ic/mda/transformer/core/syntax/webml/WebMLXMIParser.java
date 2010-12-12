/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webml;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.IXMIParser;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.syntax.uml.UMLMetaModeler;
import br.uff.ic.mda.transformer.core.syntax.webml.dataview.DataViewPackage;
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

    private static final String CLASS = UMLMetaModeler.CLASS;
    private static final String NAME = "name";
    private static final String CLASSNAME = "className";
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
        //insertMethods();
    }

    private void insertClasses() throws ContractException {
        Set<Entry<String, UMLClass>> classSet = parser.getClasses().entrySet();
        for (Entry<String, UMLClass> classEntry : classSet) {
            UMLClass umlclass = classEntry.getValue();
            if (!umlclass.hasStereotype()) {
                insertClass(umlclass.getId(), umlclass.getName());
            }
        }

        for (Entry<String, UMLClass> classEntry : classSet) {
            UMLClass umlclass = classEntry.getValue();
            if (umlclass.hasStereotype()) {
                insertStereotypedClass(umlclass.getId(), umlclass.getName(), umlclass.getStereotypeRefId());
            }
        }
    }

    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    private boolean insertClass(String id, String name) throws ContractException {
        return insertObject(CLASS, id, name);
    }

    private boolean insertStereotypedClass(String id, String name, String stereotypeId) throws ContractException {
        boolean result = true;
        String stereotypeName = getStereoTypeName(stereotypeId);
        result &= manager.insertObject(stereotypeName, id);
        result &= insertValue(stereotypeName, NAME, id, name);
        return result;
    }    

    private boolean insertOperation(String id, String name, String visibility, String returnType, String classId) throws ContractException {

        boolean result = insertObject("Operation", id, name);
        result &= manager.insertValue("Operation", "visibility", id, visibility == null ? "" : visibility);
        result &= manager.insertLink("Operation", id, "types", "classifier", returnType, "Classifier");
        result &= manager.insertLink("Operation", id, "feature", "class", classId, "Class");
        return result;
    }

    private boolean insertParameter(String id, String name, String type, String operationId) throws ContractException {
        final String parameter = "Parameter";
        boolean result = insertObject(parameter, id, name);

        result &= manager.insertLink(parameter, id, "types", "classifier", type, "Classifier");
        result &= manager.insertLink(parameter, id, "parameter", "operation", operationId, "Operation");

        return result;
    }

    private boolean insertSet(String id, String name, String elementType) throws ContractException {
        final String UMLSET = "UMLSet";
        boolean result = insertObject(UMLSET, id, name);
        result &= manager.insertLink(UMLSET, id, "setA", "elementType", elementType, "Classifier");
        return result;
    }

    private boolean insertObject(String className, String classId, String objectName) throws ContractException {
        boolean result = true;
        result &= manager.insertObject(className, classId);
        result &= insertValue(className, NAME, classId, objectName);
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

    private boolean insertAttribute(String id, String name, String visibility, String type, UMLClass classInstance) throws ContractException {
        String attribute = "Attribute";

        boolean result = true;
        result &= manager.insertObject(attribute, id);
        result &= manager.insertValue(attribute, "name", id, name == null ? "" : name);
        result &= manager.insertValue(attribute, "visibility", id, visibility == null ? "" : visibility);
        result &= manager.insertLink(attribute, id, "types", "classifier", type, "Classifier");
        if(classInstance.hasStereotype()){
            String steretypeName = getStereoTypeName(classInstance.getStereotypeRefId());
            result &= manager.insertLink(attribute, id, DataViewPackage.ROLE_ATTRIBUTE, DataViewPackage.ROLE_ATTRIBUTEOF, classInstance.getId(), steretypeName);
        } else{
            throw new ContractException("Class not stereotiped:" +  classInstance.getName());
        }

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

            this.insertAssociationEnd(association2.getId(), association2.getName(), association2.getVisibility(), class2.getId(), String.valueOf(association2.getLower()), String.valueOf(association2.getUpper()), association2.isAggregation(), class1.getId());//Class Id ??
            this.insertAssociationEnd(association1.getId(), association1.getName(), association1.getVisibility(), class1.getId(), String.valueOf(association1.getLower()), String.valueOf(association1.getUpper()), association1.isAggregation(), class2.getId());
            this.insertLinksBetweenAssociationEnds(association2.getId(), association1.getId());
            this.insertAssociation(associationEntry.getValue().getId(), associationEntry.getValue().getName(), association2.getId(), association1.getId());
        }
    }

    private boolean insertAssociation(String id, String name, String... associationEndIds) throws ContractException {
        if (associationEndIds.length < 2) {
            throw new ContractException("Association can't have less than 2 AssociationEnd");
        }

        boolean result = true;
        result &= insertObject("Association", id, name);
        for (String associationEndId : associationEndIds) {
            result &= manager.insertLink("AssociationEnd", associationEndId, "associationEnds", "association", id, "Association");
        }
        return result;
    }

    private boolean insertAssociationEnd(String id, String name, String visibility, String type, String lower, String upper, Boolean composition, String classId) throws ContractException {

        boolean result = true;
        result &= insertObject("AssociationEnd", id, name);
        result &= insertValue("AssociationEnd", "visibility", id, visibility);
        result &= insertValue("AssociationEnd", "lower", id, lower);
        result &= insertValue("AssociationEnd", "upper", id, upper);
        result &= insertValue("AssociationEnd", "composition", id, composition);
        result &= manager.insertLink("AssociationEnd", id, "types", "classifier", type, "Classifier");
        result &= manager.insertLink("AssociationEnd", id, "feature", "class", classId, "Class");
        return result;
    }

    private boolean insertLinksBetweenAssociationEnds(String associationEnd, String otherEnd) throws ContractException {
        boolean result = true;
        result &= manager.insertLink("AssociationEnd", associationEnd, "others", "otherEnd", otherEnd, "AssociationEnd");
        result &= manager.insertLink("AssociationEnd", otherEnd, "others", "otherEnd", associationEnd, "AssociationEnd");
        return result;
    }

    private void insertMethods() throws ContractException {
        Set<Entry<String, UMLClass>> classSet = parser.getClasses().entrySet();
        for (Entry<String, UMLClass> classeEntry : classSet) {
            List<UMLOperation> operationArray = classeEntry.getValue().getOperations();
            for (UMLOperation operation : operationArray) {
                UMLElement element = (UMLElement) parser.getDataTypes().get(processID(operation.getType()));
                String operationType = null;
                if ("List".equals(element.getName())) {
                    String setId = "ID" + System.nanoTime();
                    this.insertSet(setId, element.getName(), "UMLVoid");
                    operationType = setId;
                } else {
                    operationType = queryType(element.getName());
                    if (operationType == null || "null".equals(operationType)) {
                        operationType = processID(operation.getType());
                    }
                }
                this.insertOperation(operation.getId(), operation.getName(), operation.getVisibility(), operationType, classeEntry.getValue().getId());
                for (UMLElement parameter : operation.getParameters()) {
                    String parameterType = null;
                    try {
                        parameterType = operation.getParameterType(parameter.getId());
                        UMLElement pElement = (UMLElement) parser.getDataTypes().get(processID(parameterType));
                        if (pElement != null) {
                            // Eh um DataType
                            if ("List".equals(pElement.getName())) {
                                String setId = "ID" + System.nanoTime();
                                this.insertSet(setId, pElement.getName(), "UMLVoid");
                                parameterType = setId;
                            } else {
                                parameterType = queryType(pElement.getName());
                                if (parameterType == null || "null".equals(parameterType)) {
                                    parameterType = processID(operation.getParameterType(parameter.getId()));
                                }
                            }
                        } else {
                            // Nao eh um DataType
                            parameterType = processID(operation.getParameterType(parameter.getId()));
                        }
                    } catch (Exception ex) {
                        throw new ContractException("Can't recover parameter type", ex);
                    }
                    this.insertParameter(parameter.getId(), parameter.getName(), parameterType, operation.getId());
                }
            }
        }
    }

    private String queryType(String attributeTypeName) throws ContractException {
        final String startQuery = "DataType.allInstances()->select(dt | dt.name = '";
        final String endQuery = "')->asOrderedSet()->first()";
        return manager.query( startQuery + attributeTypeName + endQuery);
    }

    private String processID(String oldID) {
        return "ID".concat(oldID).replace("\\.", "").replace(".", "").replace(":", "").replace("-", "").replace(" ", "");
    }

    private String getStereoTypeName(String stereotypeId) throws ContractException{
        String name = parser.getStereoTypeName(stereotypeId);
        if(name == null){
            throw new ContractException("Missing stereotype");
        }
        return name;
    }

}
