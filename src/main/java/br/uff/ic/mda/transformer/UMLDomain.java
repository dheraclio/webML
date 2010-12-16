package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.Domain;
import br.uff.ic.mda.tclib.IValidator;
import br.uff.ic.mda.tclib.IXMIParser;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.syntax.uml.UMLMetaModeler;
import br.uff.ic.mda.xmiparser.uml.UMLAssociation;
import br.uff.ic.mda.xmiparser.uml.UMLAssociationEnd;
import br.uff.ic.mda.xmiparser.uml.UMLAttribute;
import br.uff.ic.mda.xmiparser.uml.UMLClass;
import br.uff.ic.mda.xmiparser.uml.UMLElement;
import br.uff.ic.mda.xmiparser.uml.UMLOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Daniel
 */
public class UMLDomain extends Domain {


    /**
     *
     * @throws ContractException
     */
    public UMLDomain() throws ContractException {
        super(null, null,
                new ArrayList<IValidator>(
                Arrays.asList(
                new UMLInvariantValidator())));
    }

    /**
     *
     * @param parser
     * @param xmiSourceModel
     * @throws ContractException
     */
    public UMLDomain(IXMIParser parser, String xmiSourceModel) throws ContractException {
        super(parser, xmiSourceModel,
                new ArrayList<IValidator>(
                Arrays.asList(
                new UMLInvariantValidator())));
    }

    /**
     *
     * @param parser
     * @param xmiSourceModel
     * @param validators
     */
    public UMLDomain(IXMIParser parser, String xmiSourceModel, Collection<IValidator> validators) {
        super(parser, xmiSourceModel, validators);
    }

    @Override
    public void createMetamodel() throws ContractException {
        UMLMetaModeler.createMetamodel();
    }

    /**
     *
     * @throws ContractException
     */
    @Override
    public void printDomain() throws ContractException {        
    }

    @Override
    public void loadDataFromParser(IXMIParser parser) throws ContractException {
        Set<Entry<String, UMLClass>> classSet = parser.getClasses().entrySet();
        
        //Inserting classes
        for (Entry<String, UMLClass> classEntry : classSet) {
            this.insertClass(classEntry.getValue().getId(), classEntry.getValue().getName());
        }

        //Inserting attributes
        for (Entry<String, UMLClass> classEntry : classSet) {
            List<UMLAttribute> attributeArray = classEntry.getValue().getAttributes();
            for (UMLAttribute attribute : attributeArray) {
                String attributeTypeName = null;
                String attributeTypeId = null;
                if (parser.getDataTypes().containsKey(attribute.getType())) {
                    attributeTypeName = ((UMLElement) parser.getDataTypes().get(attribute.getType())).getName();
                    attributeTypeId = ModelManager.instance().query("DataType.allInstances()->select(dt | dt.name = '" + attributeTypeName + "')->asOrderedSet()->first()");
                    if (attributeTypeId == null || "null".equals(attributeTypeId)) {
                        throw new ContractException("Attribute (" + attribute.getType() + ") type not found in class" + classEntry.getValue().getName() + " " + classEntry.getValue().getId());
                    }
                } else {
                    throw new ContractException("Attribute (" + attribute.getType() + ") type not found in class" + classEntry.getValue().getName() + " " + classEntry.getValue().getId());
                }
                this.insertAttribute(attribute.getId(), attribute.getName(), attribute.getVisibility(), attributeTypeId, classEntry.getValue().getId());
            }
        }

        //Inserting Association End
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

        //Inserting Methods
        for (Entry<String, UMLClass> classeEntry : classSet) {
            List<UMLOperation> operationArray = classeEntry.getValue().getOperations();
            for (UMLOperation operation : operationArray) {
                UMLElement element = (UMLElement) parser.getDataTypes().get(processID(operation.getType()));
//                String operationType = element.getName();
//                if ("String".equals(operationType) || "Boolean".equals(operationType)) {
//                    operationType = "UML" + operationType;
//                } else if ("List".equals(operationType)) {
//                    String setId = "ID" + System.nanoTime();
//                    this.insertSet(setId, operationType, classeEntry.getValue().getId());
//                    operationType = setId;
//                } else {
//                    operationType = processID(operation.getType());
//                }
                String operationType = null;
                if ("List".equals(element.getName())) {
                    String setId = "ID" + System.nanoTime();
                    this.insertSet(setId, element.getName(), "UMLVoid");
                    operationType = setId;
                } else {
                    operationType = ModelManager.instance().query("DataType.allInstances()->select(dt | dt.name = '" + element.getName() + "')->asOrderedSet()->first()");
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
                                parameterType = ModelManager.instance().query("DataType.allInstances()->select(dt | dt.name = '" + pElement.getName() + "')->asOrderedSet()->first()");
                                if (parameterType == null || "null".equals(parameterType)) {
                                    parameterType = processID(operation.getParameterType(parameter.getId()));
                                }
                            }
//                            if ("String".equals(pElement.getName()) || "Boolean".equals(pElement.getName())) {
//                                parameterType = "UML" + pElement.getName();
//                            } else if ("List".equals(pElement.getName())) {
//                                String setId = "ID" + System.nanoTime();
//                                this.insertSet(setId, pElement.getName(), null);
//                                parameterType = setId;
//                            } else {
//                                parameterType = processID(operation.getParameterType(parameter.getId()));
//                            }
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

    private String processID(String oldID) {
        String newID = "ID";
        newID = newID.concat(oldID);
        newID = newID.replace("\\.", "");
        newID = newID.replace(".", "");
        newID = newID.replace(":", "");
        newID = newID.replace("-", "");
        newID = newID.replace(" ", "");
        return newID;
    }

    @Override
    public void createSpecificationOfCurrentDiagram() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertObject(UMLMetaModeler.CLASSIFIER, "NULL_CLASSIFIER");
        manager.insertObject(UMLMetaModeler.CLASS, "NULL_CLASS");
        manager.insertObject(UMLMetaModeler.OPERATION, "NULL_OPERATION");
        manager.insertObject(UMLMetaModeler.ASSOCIATION, "NULL_ASSOCIATION");

        //UML Standart Elements (from XMIParser)
        insertType(UMLMetaModeler.UMLINTEGER,"Integer");
        insertType(UMLMetaModeler.UMLSTRING,"String");
        insertType(UMLMetaModeler.UMLBOOLEAN,"Boolean");

        //Java Profile
        insertType(UMLMetaModeler.UMLDATE,"Date");
        insertType(UMLMetaModeler.UMLVOID,"void");

        // Other
        insertType(UMLMetaModeler.UMLDOUBLE,"Double");
        insertType(UMLMetaModeler.UMLREAL,"Real");
    }

    

    // Specific methods of this domain
    // Class
    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    public boolean insertClass(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject(UMLMetaModeler.CLASS, id);
        result &= manager.insertValue(UMLMetaModeler.CLASS, "name", id, name == null ? "" : name);
        return result;
    }

    // Set
    /**
     *
     * @param id
     * @param name
     * @param elementType
     * @return
     * @throws ContractException
     */
    private boolean insertSet(String id, String name, String elementType) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject(UMLMetaModeler.UMLSET, id);
        result &= manager.insertValue(UMLMetaModeler.UMLSET, "name", id, name == null ? "" : name);
        result &= manager.insertLink(UMLMetaModeler.UMLSET, id, "setA", "elementType", elementType, UMLMetaModeler.CLASSIFIER);
        return result;
    }

    // Attribute
    /**
     *
     * @param id
     * @param name
     * @param visibility
     * @param type
     * @param classId
     * @return
     * @throws ContractException
     */
    public boolean insertAttribute(String id, String name, String visibility, String type, String classId) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject(UMLMetaModeler.ATTRIBUTE, id);
        result &= manager.insertValue(UMLMetaModeler.ATTRIBUTE, "name", id, name == null ? "" : name);
        result &= manager.insertValue(UMLMetaModeler.ATTRIBUTE, "visibility", id, visibility == null ? "" : visibility);
        result &= insertAttributeTypeLink(id, type);
        result &= insertAttributeClassLink(id, classId);
        return result;
    }

    /**
     *
     * @param id
     * @param type
     * @return
     * @throws ContractException
     */
    public boolean insertAttributeTypeLink(String id,String type) throws ContractException{
        if (type != null) {
            return ModelManager.instance().insertLink(UMLMetaModeler.ATTRIBUTE, id, "types", "classifier", type, UMLMetaModeler.CLASSIFIER);
        }
        return false;
    }

    /**
     *
     * @param id
     * @param classId
     * @return
     * @throws ContractException
     */
    public boolean insertAttributeClassLink(String id, String classId) throws ContractException{
        if (classId != null) {
            return ModelManager.instance().insertLink(UMLMetaModeler.ATTRIBUTE, id, "feature", "class", classId, UMLMetaModeler.CLASS);
        }
        return false;
    }

    // Operation
    /**
     *
     * @param id
     * @param name
     * @param visibility
     * @param returnType
     * @param classId
     * @return
     * @throws ContractException
     */
    private boolean insertOperation(String id, String name, String visibility, String returnType, String classId) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject(UMLMetaModeler.OPERATION, id);
        result &= manager.insertValue(UMLMetaModeler.OPERATION, "name", id, name == null ? "" : name);
        result &= manager.insertValue(UMLMetaModeler.OPERATION, "visibility", id, visibility == null ? "" : visibility);
        result &= manager.insertLink(UMLMetaModeler.OPERATION, id, "types", "classifier", returnType, UMLMetaModeler.CLASSIFIER);
        result &= manager.insertLink(UMLMetaModeler.OPERATION, id, "feature", "class", classId, UMLMetaModeler.CLASS);
        return result;
    }

    // Parameter
    /**
     *
     * @param id
     * @param name
     * @param type
     * @param operationId
     * @return
     * @throws ContractException
     */
    private boolean insertParameter(String id, String name, String type, String operationId) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject(UMLMetaModeler.PARAMETER, id);
        result &= manager.insertValue(UMLMetaModeler.PARAMETER, "name", id, name == null ? "" : name);
        result &= manager.insertLink(UMLMetaModeler.PARAMETER, id, "types", "classifier", type, UMLMetaModeler.CLASSIFIER);
        result &= manager.insertLink(UMLMetaModeler.PARAMETER, id, "parameter", "operation", operationId, UMLMetaModeler.OPERATION);
        return result;
    }

    // Association
    /**
     *
     * @param id
     * @param name
     * @param associationEndIds
     * @return
     * @throws ContractException
     */
    private boolean insertAssociation(String id, String name, String... associationEndIds) throws ContractException {
        if (associationEndIds.length < 2) {
            throw new ContractException("Association can't have less than 2 AssociationEnd");
        }
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject(UMLMetaModeler.ASSOCIATION, id);
        result &= manager.insertValue(UMLMetaModeler.ASSOCIATION, "name", id, name == null ? "" : name);
        for (String associationEndId : associationEndIds) {
            result &= manager.insertLink(UMLMetaModeler.ASSOCIATIONEND, associationEndId, "associationEnds", "association", id, UMLMetaModeler.ASSOCIATION);
        }
        return result;
    }

    // AssociationEnd
    /**
     *
     * @param id
     * @param name
     * @param visibility
     * @param type
     * @param lower
     * @param upper
     * @param composition
     * @param classId
     * @return
     * @throws ContractException
     */
    private boolean insertAssociationEnd(String id, String name, String visibility, String type, String lower, String upper, Boolean composition, String classId) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject(UMLMetaModeler.ASSOCIATIONEND, id);
        result &= manager.insertValue(UMLMetaModeler.ASSOCIATIONEND, "name", id, name == null ? "" : name);
        result &= manager.insertValue(UMLMetaModeler.ASSOCIATIONEND, "visibility", id, visibility == null ? "" : visibility);
        result &= manager.insertValue(UMLMetaModeler.ASSOCIATIONEND, "lower", id, lower == null ? "" : lower);
        result &= manager.insertValue(UMLMetaModeler.ASSOCIATIONEND, "upper", id, upper == null ? "" : upper);
        result &= manager.insertValue(UMLMetaModeler.ASSOCIATIONEND, "composition", id, composition == true ? "true" : "false");
        result &= manager.insertLink(UMLMetaModeler.ASSOCIATIONEND, id, "types", "classifier", type, UMLMetaModeler.CLASSIFIER);
        result &= manager.insertLink(UMLMetaModeler.ASSOCIATIONEND, id, "feature", "class", classId, UMLMetaModeler.CLASS);
        return result;
    }

    /**
     *
     * @param associationEnd
     * @param otherEnd
     * @return
     * @throws ContractException
     */
    private boolean insertLinksBetweenAssociationEnds(String associationEnd, String otherEnd) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertLink(UMLMetaModeler.ASSOCIATIONEND, associationEnd, "others", "otherEnd", otherEnd, UMLMetaModeler.ASSOCIATIONEND);
        result &= manager.insertLink(UMLMetaModeler.ASSOCIATIONEND, otherEnd, "others", "otherEnd", associationEnd, UMLMetaModeler.ASSOCIATIONEND);
        return result;
    }

    /**
     *
     * @param objectName
     * @param value
     * @throws ContractException
     */
    public void insertType(String objectName, String value) throws ContractException {
        final String className = UMLMetaModeler.DATATYPE;
        final String attrName = "name";
        ModelManager.instance().insertObject(className, objectName);
        ModelManager.instance().insertValue(className, attrName, objectName, value);
    }
}
