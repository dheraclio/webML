package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.Domain;
import br.uff.ic.mda.tclib.IValidator;
import br.uff.ic.mda.tclib.IXMIParser;
import br.uff.ic.mda.tclib.ModelManager;
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
        insertMetamodelClasses();
        insertMetamodelAttributes();
        insertMetamodelAssociations();
        insertMetamodelOperations();
    }

    /**
     *
     * @throws ContractException
     */
    public void printDomain() throws ContractException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadDataFromParser(IXMIParser parser) throws ContractException {
        Set<Entry<String, UMLClass>> classSet = parser.getClasses().entrySet();
        Set<Entry<String, UMLAttribute>> attributeSet = parser.getAttributes().entrySet();

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

        manager.insertObject("Classifier", "NULL_CLASSIFIER");
        manager.insertObject("Class", "NULL_CLASS");
        manager.insertObject("Operation", "NULL_OPERATION");
        manager.insertObject("Association", "NULL_ASSOCIATION");

        //UML Standart Elements (from XMIParser)
        manager.insertObject("DataType", "UMLInteger");
        manager.insertValue("DataType", "name", "UMLInteger", "Integer");

        manager.insertObject("DataType", "UMLString");
        manager.insertValue("DataType", "name", "UMLString", "String");

        manager.insertObject("DataType", "UMLBoolean");
        manager.insertValue("DataType", "name", "UMLBoolean", "Boolean");

        //Java Profile
        manager.insertObject("DataType", "UMLDate");
        manager.insertValue("DataType", "name", "UMLDate", "Date");

        manager.insertObject("DataType", "UMLVoid");
        manager.insertValue("DataType", "name", "UMLVoid", "void");

        // Other
        manager.insertObject("DataType", "UMLDouble");
        manager.insertValue("DataType", "name", "UMLDouble", "Double");

        manager.insertObject("DataType", "UMLReal");
        manager.insertValue("DataType", "name", "UMLReal", "Real");
    }

    /**
     *
     * @throws ContractException
     */
    public void insertMetamodelClasses() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertClass("ModelElement");
        manager.insertClass("Classifier");
        manager.insertClass("Typed");
        manager.insertClass("DataType");
        manager.insertClass("UMLSet");
        manager.insertClass("Class");
        manager.insertClass("Interface");
        manager.insertClass("AssociationClass");
        manager.insertClass("Feature");

        manager.insertClass("AssociationEnd");
        manager.insertClass("Association");
        manager.insertClass("Attribute");
        manager.insertClass("Operation");
        manager.insertClass("Parameter");
        manager.insertGeneralization("Association", "ModelElement");
        manager.insertGeneralization("Classifier", "ModelElement");
        manager.insertGeneralization("Typed", "ModelElement");
        manager.insertGeneralization("DataType", "Classifier");
        manager.insertGeneralization("UMLSet", "DataType");
        manager.insertGeneralization("Class", "Classifier");
        manager.insertGeneralization("Interface", "Classifier");
        manager.insertGeneralization("AssociationClass", "Class");
        manager.insertGeneralization("AssociationClass", "Association");
        manager.insertGeneralization("Feature", "Typed");
        manager.insertGeneralization("AssociationEnd", "Feature");
        manager.insertGeneralization("Attribute", "Feature");
        manager.insertGeneralization("Operation", "Feature");
        manager.insertGeneralization("Parameter", "Typed");
    }

    /**
     *
     * @throws ContractException
     */
    private void insertMetamodelAttributes() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertAttribute("ModelElement", "name", "String");
        manager.insertAttribute("Feature", "visibility", "String"); // ### Tipo VisibilityKind
        manager.insertAttribute("AssociationEnd", "lower", "String"); // ### Tipo Lowerbound
        manager.insertAttribute("AssociationEnd", "upper", "String"); // ### Tipo Upperbound
        manager.insertAttribute("AssociationEnd", "composition", "Boolean");
    }

    // Corrigir os nomes das associacoes para = ao Metamodelo
    /**
     *
     * @throws ContractException
     */
    private void insertMetamodelAssociations() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertAssociation("AssociationEnd", "otherEnd", "0..1", "0..*", "others", "AssociationEnd");
        manager.insertAssociation("AssociationEnd", "associationEnds", "1..*", "1", "association", "Association");
        manager.insertAssociation("Operation", "operation", "1", "*", "parameter", "Parameter");
        manager.insertAssociation("Class", "class", "0..1", "*", "feature", "Feature");
        manager.insertAssociation("Class", "classes", "*", "*", "implementedInterfaces", "Interface");
        manager.insertAssociation("Class", "inheritsFrom", "*", "*", "inheritedBy", "Class");
        manager.insertAssociation("Classifier", "classifier", "0..1", "*", "types", "Typed");
        manager.insertAssociation("UMLSet", "setA", "0..*", "1", "elementType", "Classifier");
    }

    /**
     *
     * @return
     * @throws ContractException
     */
    private boolean insertMetamodelOperations() throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;

        Object[] params;
        String[] param;

        result &= manager.insertOperation("Class", "getAllContained", "Set(Class)",
                "Class.allInstances()->select(c | c.subindo(c.emptySet())->includes(self))", new Object[0]);

        result &= manager.insertOperation("Class", "subindo", "Set(Class)",
                "if self.oclIsTypeOf(Class) "
                + "then self.subindoClass(lista) "
                + "else "
                + "self.oclAsType(AssociationClass).subindoAssociationClass(lista) "
                + "endif", new Object[]{new String[]{"lista", "Set(Class)"}});

        result &= manager.insertOperation("Class", "subindoClass", "Set(Class)",
                "if "
                + "self.feature"
                + "->select(f : Feature | f.oclIsKindOf(AssociationEnd))"
                + "->collect(f : Feature | f.oclAsType(AssociationEnd))"
                + "->exists(ae : AssociationEnd | ae.composition = true)"
                + " then "
                + "self.feature"
                + "->select(f : Feature | f.oclIsKindOf(AssociationEnd))"
                + "->collect(f : Feature | f.oclAsType(AssociationEnd))"
                + "->select(ae : AssociationEnd | ae.composition = true)"
                + ".classifier"
                + "->select(c : Classifier | c.oclIsKindOf(Class))"
                + "->collect(c : Classifier | c.oclAsType(Class))"
                + "->asOrderedSet()"
                + "->first()"
                + ".subindo(lista->including(self))"
                + " else "
                + "lista->including(self)"
                + " endif", new Object[]{new String[]{"lista", "Set(Class)"}});

        result &= manager.insertOperation("AssociationClass", "subindoAssociationClass", "Set(Class)",
                "if "
                + "self.associationEnds->size() = 1 "
                + "then "
                + "self.associationEnds"
                + "->asOrderedSet()"
                + "->first()"
                + ".class"
                + "->asOrderedSet()"
                + "->first()"
                + ".subindo(lista->including(self))"
                + "else "
                + "lista->including(self) "
                + "endif", new Object[]{new String[]{"lista", "Set(Class)"}});

        result &= manager.insertOperation("Class", "emptySet", "Set(Class)",
                "Class.allInstances()->select(c|false) ", new Object[0]);


        result &= manager.insertOperation("Class", "getOuterMostContainer", "Class",
                "if self.oclIsTypeOf(Class) "
                + "then self.getOuterMostContainerFromClass() "
                + "else "
                + "self.oclAsType(AssociationClass).getOuterMostContainerFromAssociationClass() "
                + "endif", new Object[0]);

        result &= manager.insertOperation("Class", "getOuterMostContainerFromClass", "Class",
                "if "
                + "self.feature"
                + "->select(f : Feature | f.oclIsKindOf(AssociationEnd))"
                + "->collect(f : Feature | f.oclAsType(AssociationEnd))"
                + "->exists(ae : AssociationEnd | ae.composition = true)"
                + " then "
                + "self.feature"
                + "->select(f : Feature | f.oclIsKindOf(AssociationEnd))"
                + "->collect(f : Feature | f.oclAsType(AssociationEnd))"
                + "->select(ae : AssociationEnd | ae.composition = true)"
                + ".classifier"
                + "->select(c : Classifier | c.oclIsKindOf(Class))"
                + "->collect(c : Classifier | c.oclAsType(Class))"
                + "->asOrderedSet()"
                + "->first()"
                + ".getOuterMostContainer()"
                + " else "
                + "self"
                + " endif", new Object[0]);

        result &= manager.insertOperation("Association", "getOuterMostContainerFromAssociation", "Class",
                "if self.oclIsTypeOf(Association) then "
                + "self.associationEnds"
                + "->select(ae : AssociationEnd | ae.composition = true)"
                + "->asOrderedSet()"
                + "->first()"
                + ".classifier"
                + "->select(c : Classifier | c.oclIsKindOf(Class))"
                + "->collect(c : Classifier | c.oclAsType(Class))"
                + "->asOrderedSet()"
                + "->first()"
                + ".getOuterMostContainer() "
                + "else self.oclAsType(AssociationClass).getOuterMostContainerFromAssociationClass() endif", new Object[0]);

        result &= manager.insertOperation("AssociationClass", "getOuterMostContainerFromAssociationClass", "Class",
                "if "
                + "self.associationEnds->size() = 1 "
                + "then "
                + "self.associationEnds"
                + "->asOrderedSet()"
                + "->first()"
                + ".class"
                + ".getOuterMostContainer() "
//                + ".getOuterMostContainer()"
//                + "->asOrderedSet()"
//                + "->first() "
                + "else "
                + "self "
                + "endif", new Object[0]);

        result &= manager.insertOperation("Class", "isOuterMostContainer", "Boolean",
                "self = self.getOuterMostContainer()", new Object[0]);

        result &= manager.insertOperation("Class", "getAllOuterMostContainer", "Set(Class)",
                "Class.allInstances()->select(c : Class | c.isOuterMostContainer())->asSet()", new Object[0]);

        result &= manager.insertOperation("Class", "superPlus", "Set(Class)",
                "self.superPlusOnSet(self.emptySet())", new Object[0]);

        result &= manager.insertOperation("Class", "superPlusOnSet", "Set(Class)",
                "if self.inheritsFrom->notEmpty() and rs->excludes(self) then "
                + "self.inheritsFrom->collect(c : Class | c.superPlusOnSet(rs->including(self)))->flatten()->asSet()"
                + " else "
                + "rs->including(self)"
                + " endif", new Object[]{new String[]{"rs", "Set(Class)"}});

        if (!result) {
            throw new ContractException("It was not possible to insert all the operations in the UML model");
        }

        return result;
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
        result &= manager.insertObject("Class", id);
        result &= manager.insertValue("Class", "name", id, name == null ? "" : name);
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
        result &= manager.insertObject("UMLSet", id);
        result &= manager.insertValue("UMLSet", "name", id, name == null ? "" : name);
        result &= manager.insertLink("UMLSet", id, "setA", "elementType", elementType, "Classifier");
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
    private boolean insertAttribute(String id, String name, String visibility, String type, String classId) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("Attribute", id);
        result &= manager.insertValue("Attribute", "name", id, name == null ? "" : name);
        result &= manager.insertValue("Attribute", "visibility", id, visibility == null ? "" : visibility);
        result &= manager.insertLink("Attribute", id, "types", "classifier", type, "Classifier");
        result &= manager.insertLink("Attribute", id, "feature", "class", classId, "Class");
        return result;
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
        result &= manager.insertObject("Operation", id);
        result &= manager.insertValue("Operation", "name", id, name == null ? "" : name);
        result &= manager.insertValue("Operation", "visibility", id, visibility == null ? "" : visibility);
        result &= manager.insertLink("Operation", id, "types", "classifier", returnType, "Classifier");
        result &= manager.insertLink("Operation", id, "feature", "class", classId, "Class");
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
        result &= manager.insertObject("Parameter", id);
        result &= manager.insertValue("Parameter", "name", id, name == null ? "" : name);
        result &= manager.insertLink("Parameter", id, "types", "classifier", type, "Classifier");
        result &= manager.insertLink("Parameter", id, "parameter", "operation", operationId, "Operation");
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
        result &= manager.insertObject("Association", id);
        result &= manager.insertValue("Association", "name", id, name == null ? "" : name);
        for (String associationEndId : associationEndIds) {
            result &= manager.insertLink("AssociationEnd", associationEndId, "associationEnds", "association", id, "Association");
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
        result &= manager.insertObject("AssociationEnd", id);
        result &= manager.insertValue("AssociationEnd", "name", id, name == null ? "" : name);
        result &= manager.insertValue("AssociationEnd", "visibility", id, visibility == null ? "" : visibility);
        result &= manager.insertValue("AssociationEnd", "lower", id, lower == null ? "" : lower);
        result &= manager.insertValue("AssociationEnd", "upper", id, upper == null ? "" : upper);
        result &= manager.insertValue("AssociationEnd", "composition", id, composition == true ? "true" : "false");
        result &= manager.insertLink("AssociationEnd", id, "types", "classifier", type, "Classifier");
        result &= manager.insertLink("AssociationEnd", id, "feature", "class", classId, "Class");
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
        result &= manager.insertLink("AssociationEnd", associationEnd, "others", "otherEnd", otherEnd, "AssociationEnd");
        result &= manager.insertLink("AssociationEnd", otherEnd, "others", "otherEnd", associationEnd, "AssociationEnd");
        return result;
    }
}
