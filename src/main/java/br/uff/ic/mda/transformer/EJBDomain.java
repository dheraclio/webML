package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.Domain;
import br.uff.ic.mda.tclib.IValidator;
import br.uff.ic.mda.tclib.IXMIParser;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.syntax.java.Attribute;
import br.uff.ic.mda.transformer.core.syntax.java.Class;
import br.uff.ic.mda.transformer.core.syntax.java.Constructor;
import br.uff.ic.mda.transformer.core.syntax.java.Interface;
import br.uff.ic.mda.transformer.core.syntax.java.JavaSyntax;
import br.uff.ic.mda.transformer.core.syntax.java.Method;
import br.uff.ic.mda.transformer.core.syntax.java.Parameter;
import br.uff.ic.mda.transformer.core.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Daniel
 */
public class EJBDomain extends Domain {

    /**
     *
     * @throws ContractException
     */
    public EJBDomain() throws ContractException {
        super(null, null,
                new ArrayList<IValidator>(
                    Arrays.asList(
                        new EJBInvariantValidator()
                    )
                )
             );
    }

    /**
     *
     * @param parser
     * @param xmiSourceModel
     * @throws ContractException
     */
    public EJBDomain(IXMIParser parser, String xmiSourceModel) throws ContractException {
        super(parser, xmiSourceModel,
                new ArrayList<IValidator>(
                    Arrays.asList(
                        new EJBInvariantValidator()
                    )
                )
             );
    }

    /**
     *
     * @param parser
     * @param xmiSourceModel
     * @param validators
     */
    public EJBDomain(IXMIParser parser, String xmiSourceModel, Collection<IValidator> validators) {
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
    @Override
    public void printDomain() throws ContractException {
        generateEJBKeyClass();
        generateEJBEntityComponent();
        generateEJBDataClass();
    }

    @Override
    protected void loadDataFromParser(IXMIParser parser) throws ContractException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @throws ContractException
     */
    public void insertMetamodelClasses() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertClass("EJBModelElement");
        manager.insertClass("EJBClassifier");
        manager.insertClass("EJBTyped");
        manager.insertClass("EJBDataSchema");
        manager.insertClass("EJBDataType");
        manager.insertClass("EJBSet");
        manager.insertClass("EJBClass");
        manager.insertClass("EJBDataSchemaElement");
        manager.insertClass("EJBKeyClass");
        manager.insertClass("EJBComponent");
        manager.insertClass("EJBDataClass");
        manager.insertClass("EJBSessionComponent");
        manager.insertClass("EJBDataAssociation");
        manager.insertClass("EJBEntityComponent");
        manager.insertClass("Table");
        manager.insertClass("EJBFeature");
        manager.insertClass("EJBAssociationEnd");
        manager.insertClass("EJBAttribute");
        manager.insertClass("BusinessMethod");
        manager.insertClass("EJBServingAttribute");
        manager.insertClass("EJBParameter");

        manager.insertGeneralization("EJBClassifier", "EJBModelElement");
        manager.insertGeneralization("EJBTyped", "EJBModelElement");
        manager.insertGeneralization("Table", "EJBModelElement");
        manager.insertGeneralization("EJBDataSchema", "EJBModelElement");
        manager.insertGeneralization("EJBDataSchemaElement", "EJBModelElement");
        manager.insertGeneralization("EJBDataType", "EJBClassifier");
        manager.insertGeneralization("EJBSet", "EJBDataType");
        manager.insertGeneralization("EJBClass", "EJBClassifier");
        manager.insertGeneralization("EJBComponent", "EJBClass");
        manager.insertGeneralization("EJBKeyClass", "EJBClass");
        manager.insertGeneralization("EJBDataClass", "EJBClass");
        manager.insertGeneralization("EJBSessionComponent", "EJBComponent");
        manager.insertGeneralization("EJBEntityComponent", "EJBComponent");
        manager.insertGeneralization("EJBDataClass", "EJBDataSchemaElement");
        manager.insertGeneralization("EJBDataAssociation", "EJBDataSchemaElement");
        manager.insertGeneralization("EJBFeature", "EJBTyped");
        manager.insertGeneralization("EJBParameter", "EJBTyped");
        manager.insertGeneralization("BusinessMethod", "EJBFeature");
        manager.insertGeneralization("EJBAttribute", "EJBFeature");
        manager.insertGeneralization("EJBAssociationEnd", "EJBFeature");
        manager.insertGeneralization("EJBServingAttribute", "EJBAssociationEnd");
    }

    /**
     *
     * @throws ContractException
     */
    public void insertMetamodelAssociations() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertAssociation("EJBClassifier", "type", "1", "*", "typed", "EJBTyped");
        manager.insertAssociation("EJBDataSchema", "package", "1", "*", "element", "EJBDataSchemaElement");
        manager.insertAssociation("EJBSet", "set", "0..*", "1", "elementType", "EJBClassifier");
        manager.insertAssociation("EJBClass", "class", "1", "0..*", "feature", "EJBFeature");
        manager.insertAssociation("EJBEntityComponent", "entityComp", "0..1", "0..*", "usedTable", "Table");
        manager.insertAssociation("EJBDataAssociation", "association", "0..1", "2", "associationEnds", "EJBAssociationEnd");
        manager.insertAssociation("BusinessMethod", "operation", "1", "0..*", "parameter", "EJBParameter");
    }

    /**
     *
     * @throws ContractException
     */
    public void insertMetamodelAttributes() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertAttribute("EJBModelElement", "name", "String");
        manager.insertAttribute("EJBAssociationEnd", "lower", "String");
        manager.insertAttribute("EJBAssociationEnd", "upper", "String");
        manager.insertAttribute("EJBAssociationEnd", "composition", "Boolean");
        manager.insertAttribute("EJBAttribute", "visibility", "String");
    }

    /**
     *
     * @return
     * @throws ContractException
     */
    public boolean insertMetamodelOperations() throws ContractException {
        boolean result = true;

        Object[] params;
        String[] param;

        ModelManager manager = ModelManager.instance();

        // isCreateMethod -> verifica se um m�todo � um Create Method
        // *** verifica apenas o nome pois o retorno n�o � poss�vel de saber com base na abstra��o do metamodelo ***
//        result &= manager.insertOperation("BusinessMethod", "isCreateMethod", "Boolean", "if self.name.size() >= 6 then self.name.substring(1, 6) = 'create' and self.type->forAll(a | if a.oclIsTypeOf(EJBDataClass) then true else (if a.oclIsTypeOf(EJBSet) then a.oclAsType(EJBSet).elementType.oclIsTypeOf(EJBDataClass) else false endif) endif ) else false endif", new Object[0]);
        result &= manager.insertOperation("BusinessMethod", "isCreateMethod", "Boolean", "if self.name.size() >= 6 then self.name.substring(1, 6) = 'create' and if self.type.oclIsTypeOf(EJBDataClass) then true else (if self.type.oclIsTypeOf(EJBSet) then (self.type.oclAsType(EJBSet).elementType.oclIsTypeOf(EJBDataClass) or self.type.oclAsType(EJBSet).elementType.name = 'void') else false endif) endif else false endif", new Object[0]);

        // isFinderMethod -> verifica se um m�todo � um Finder Method
        // *** verifica apenas o nome pois o retorno n�o � poss�vel de saber com base na abstra��o do metamodelo ***
//        result &= manager.insertOperation("BusinessMethod", "isFinderMethod", "Boolean", "if self.name.size() >= 4 then self.name.substring(1, 4) = 'find' and self.type->forAll(a | if a.oclIsTypeOf(EJBDataClass) then true else (if a.oclIsTypeOf(EJBSet) then a.oclAsType(EJBSet).elementType.oclIsTypeOf(EJBDataClass) else false endif) endif ) else false endif", new Object[0]);
        result &= manager.insertOperation("BusinessMethod", "isFinderMethod", "Boolean", "if self.name.size() >= 4 then self.name.substring(1, 4) = 'find' and if self.type.oclIsTypeOf(EJBDataClass) then true else (if self.type.oclIsTypeOf(EJBSet) then (self.type.oclAsType(EJBSet).elementType.oclIsTypeOf(EJBDataClass) or self.type.oclAsType(EJBSet).elementType.name = 'void') else false endif) endif else false endif", new Object[0]);

        // isRemoveMethod -> verifica se um m�todo � um Remove Method
//        result &= manager.insertOperation("BusinessMethod", "isRemoveMethod", "Boolean", "if self.name.size() >= 6 then self.name.substring(1, 6) = 'remove' and self.type->forAll(a | a.name = 'null') and self.parameter->notEmpty() else false endif", new Object[0]);
        result &= manager.insertOperation("BusinessMethod", "isRemoveMethod", "Boolean", "if self.name.size() >= 6 then self.name.substring(1, 6) = 'remove' and (self.type.name = 'null' or self.type.name = 'void') and self.parameter->notEmpty() else false endif", new Object[0]);

        // isHomeMethod -> verifica se um m�todo � um Home Method
        result &= manager.insertOperation("BusinessMethod", "isHomeMethod", "Boolean", "if self.name.size() >= 6 then self.name.substring(1, 6) <> 'remove' and self.name.substring(1, 6) <> 'create' else true endif and if self.name.size() >= 4 then self.name.substring(1,4) <> 'find' else true endif and self.parameter.type->forAll(p | if p.oclIsTypeOf(EJBDataType) then true else (if p.oclIsTypeOf(EJBSet) then (p.oclAsType(EJBSet).elementType.oclIsTypeOf(EJBDataType) or p.oclAsType(EJBSet).elementType.name = 'void') else false endif) endif)", new Object[0]);

        if (!result) {
            throw new ContractException("It was not possible to insert all the operations in the EJB model");
        }

        return result;
    }

    @Override
    public void createSpecificationOfCurrentDiagram() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertObject("EJBDataSchemaElement", "NULL_EJBDSE");
        manager.insertValue("EJBDataSchemaElement", "name", "NULL_EJBDSE", "null");
        manager.insertObject("EJBDataSchema", "NULL_EJBDS");
        manager.insertValue("EJBDataSchema", "name", "NULL_EJBDS", "null");
        manager.insertObject("EJBClassifier", "NULL_EJBC");
        manager.insertValue("EJBClassifier", "name", "NULL_EJBC", "null");
        manager.insertObject("EJBTyped", "NULL_EJBT");
        manager.insertValue("EJBTyped", "name", "NULL_EJBT", "null");
        manager.insertObject("BusinessMethod", "NULL_BM");
        manager.insertValue("BusinessMethod", "name", "NULL_BM", "null");

        manager.insertObject("EJBDataType", "EJBInteger");
        manager.insertValue("EJBDataType", "name", "EJBInteger", "Integer");
        manager.insertObject("EJBDataType", "EJBDouble");
        manager.insertValue("EJBDataType", "name", "EJBDouble", "Double");
        manager.insertObject("EJBDataType", "EJBString");
        manager.insertValue("EJBDataType", "name", "EJBString", "String");
        manager.insertObject("EJBDataType", "EJBDate");
        manager.insertValue("EJBDataType", "name", "EJBDate", "Date");
        manager.insertObject("EJBDataType", "EJBBoolean");
        manager.insertValue("EJBDataType", "name", "EJBBoolean", "Boolean");
        manager.insertObject("EJBDataType", "EJBVoid");
        manager.insertValue("EJBDataType", "name", "EJBVoid", "void");
    }

    // Specific methods of this domain
    // EJBDataSchema
    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    public boolean insertEJBDataSchema(String id, String name) throws ContractException {
        return insertEJBDataSchemaStub(id, name);
    }
    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    public boolean insertEJBDataSchemaStub(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("EJBDataSchema", id);
        result &= manager.insertValue("EJBDataSchema", "name", id, name == null ? "" : name);
        return result;
    }

    // EJBDataType
    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    public boolean insertEJBDataType(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("EJBDataType", id);
        result &= manager.insertValue("EJBDataType", "name", id, name == null ? "" : name);
        return result;
    }

    // EJBSet
    /**
     *
     * @param id
     * @param name
     * @param idType
     * @return
     * @throws ContractException
     */
    public boolean insertEJBSet(String id, String name, String idType) throws ContractException {
        return insertEJBSetStub(id, name)
                && insertEJBSetTypeLink(id, idType);
    }
    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    public boolean insertEJBSetStub(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("EJBSet", id);
        result &= manager.insertValue("EJBSet", "name", id, name == null ? "" : name);
        return result;
    }
    /**
     *
     * @param id
     * @param idType
     * @return
     * @throws ContractException
     */
    public boolean insertEJBSetTypeLink(String id, String idType) throws ContractException {
        return ModelManager.instance().insertLink("EJBSet", id, "set", "elementType", idType, "EJBClassifier");
    }

    // EJBKeyClass
    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    public boolean insertEJBKeyClass(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("EJBKeyClass", id);
        result &= manager.insertValue("EJBKeyClass", "name", id, name == null ? "" : name);
        return result;
    }

    //EJBDataClass
    /**
     *
     * @param id
     * @param name
     * @param ejbDataSchemaId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBDataClass(String id, String name, String ejbDataSchemaId) throws ContractException {
        return insertEJBDataClassStub(id, name)
                && insertEJBDataClassSchemaLink(id, ejbDataSchemaId);
    }
    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    public boolean insertEJBDataClassStub(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("EJBDataClass", id);
        result &= manager.insertValue("EJBDataClass", "name", id, name == null ? "" : name);
        return result;
    }
    /**
     *
     * @param id
     * @param ejbDataSchemaId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBDataClassSchemaLink(String id, String ejbDataSchemaId) throws ContractException {
        return ModelManager.instance().insertLink("EJBDataSchema", ejbDataSchemaId, "package", "element", id, "EJBDataClass");
    }

    // EJBDataAssociation
    /**
     *
     * @param id
     * @param name
     * @param ejbDataSchemaId
     * @param ejbAssociationEnds
     * @return
     * @throws ContractException
     */
    public boolean insertEJBDataAssociation(String id, String name, String ejbDataSchemaId, String... ejbAssociationEnds) throws ContractException {
        return insertEJBDataAssociationStub(id, name)
                && insertEJBDataAssociationSchemaLink(id, ejbDataSchemaId)
                && insertEJBDataAssociationEndLinks(id, ejbAssociationEnds);
    }
    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    public boolean insertEJBDataAssociationStub(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("EJBDataAssociation", id);
        result &= manager.insertValue("EJBDataAssociation", "name", id, name == null ? "" : name);
        return result;
    }
    /**
     *
     * @param id
     * @param ejbDataSchemaId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBDataAssociationSchemaLink(String id, String ejbDataSchemaId) throws ContractException {
        return ModelManager.instance().insertLink("EJBDataSchema", ejbDataSchemaId, "package", "element", id, "EJBDataAssociation");
    }
    /**
     *
     * @param id
     * @param ejbAssociationEnds
     * @return
     * @throws ContractException
     */
    public boolean insertEJBDataAssociationEndLinks(String id, String... ejbAssociationEnds) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        for (String ejbAssociationEnd : ejbAssociationEnds) {
            result &= manager.insertLink("EJBDataAssociation", id, "association", "associationEnds", ejbAssociationEnd, "EJBAssociationEnd");
        }
        return result;
    }

    // EJBSessionComponent
    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    public boolean insertEJBSessionComponent(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("EJBSessionComponent", id);
        result &= manager.insertValue("EJBSessionComponent", "name", id, name == null ? "" : name);
        return result;
    }

    // EJBEntityComponent
    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    public boolean insertEJBEntityComponent(String id, String name) throws ContractException {
        return insertEJBEntityComponentStub(id, name);
    }
    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    public boolean insertEJBEntityComponentStub(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("EJBEntityComponent", id);
        result &= manager.insertValue("EJBEntityComponent", "name", id, name == null ? "" : name);
        return result;
    }

    // Table
    /**
     *
     * @param id
     * @param name
     * @param ejbEntityComponentId
     * @return
     * @throws ContractException
     */
    public boolean insertTable(String id, String name, String ejbEntityComponentId) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("Table", id);
        result &= manager.insertValue("Table", "name", id, name == null ? "" : name);
        result &= manager.insertLink("EJBEntityComponent", ejbEntityComponentId, "entityComp", "usedTable", id, "Table");
        return result;
    }

    // BusinessMethod
    /**
     *
     * @param id
     * @param name
     * @param typeId
     * @param ejbClassId
     * @return
     * @throws ContractException
     */
    public boolean insertBusinessMethod(String id, String name, String typeId, String ejbClassId) throws ContractException {
        return insertBusinessMethodStub(id, name)
                && insertBusinessMethodTypeLink(id, typeId)
                && insertBusinessMethodClassLink(id, ejbClassId);
    }
    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    public boolean insertBusinessMethodStub(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("BusinessMethod", id);
        result &= manager.insertValue("BusinessMethod", "name", id, name == null ? "" : name);
        return result;
    }
    /**
     *
     * @param id
     * @param typeId
     * @return
     * @throws ContractException
     */
    public boolean insertBusinessMethodTypeLink(String id, String typeId) throws ContractException {
        return ModelManager.instance().insertLink("BusinessMethod", id, "typed", "type", typeId, "EJBClassifier");
    }
    /**
     *
     * @param id
     * @param ejbClassId
     * @return
     * @throws ContractException
     */
    public boolean insertBusinessMethodClassLink(String id, String ejbClassId) throws ContractException {
        return ModelManager.instance().insertLink("BusinessMethod", id, "feature", "class", ejbClassId, "EJBClass");
    }

    // EJBParameter
    /**
     *
     * @param id
     * @param name
     * @param typeId
     * @param businessMethodId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBParameter(String id, String name, String typeId, String businessMethodId) throws ContractException {
        return insertEJBParameterStub(id, name)
                && insertEJBParameterTypeLink(id, typeId)
                && insertEJBParameterBusinessMethodLink(id, businessMethodId);
    }
    /**
     *
     * @param id
     * @param name
     * @return
     * @throws ContractException
     */
    public boolean insertEJBParameterStub(String id, String name) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("EJBParameter", id);
        result &= manager.insertValue("EJBParameter", "name", id, name == null ? "" : name);
        return result;
    }
    /**
     *
     * @param id
     * @param typeId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBParameterTypeLink(String id, String typeId) throws ContractException {
        return ModelManager.instance().insertLink("EJBParameter", id, "typed", "type", typeId, "EJBClassifier");
    }
    /**
     *
     * @param id
     * @param businessMethodId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBParameterBusinessMethodLink(String id, String businessMethodId) throws ContractException {
        return ModelManager.instance().insertLink("BusinessMethod", businessMethodId, "operation", "parameter", id, "EJBParameter");
    }

    // EJBAttribute
    /**
     *
     * @param id
     * @param name
     * @param visibility
     * @param typeId
     * @param ejbClassId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBAttribute(String id, String name, String visibility, String typeId, String ejbClassId) throws ContractException {
        return insertEJBAttributeStub(id, name, visibility)
                && insertEJBAttributeTypeLink(id, typeId)
                && insertEJBAttributeClassLink(id, ejbClassId);
    }
    /**
     *
     * @param id
     * @param name
     * @param visibility
     * @return
     * @throws ContractException
     */
    public boolean insertEJBAttributeStub(String id, String name, String visibility) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("EJBAttribute", id);
        result &= manager.insertValue("EJBAttribute", "name", id, name == null ? "" : name);
        result &= manager.insertValue("EJBAttribute", "visibility", id, visibility == null ? "" : visibility);
        return result;
    }
    /**
     *
     * @param id
     * @param typeId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBAttributeTypeLink(String id, String typeId) throws ContractException {
        return ModelManager.instance().insertLink("EJBAttribute", id, "typed", "type", typeId, "EJBClassifier");
    }
    /**
     *
     * @param id
     * @param ejbClassId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBAttributeClassLink(String id, String ejbClassId) throws ContractException {
        return ModelManager.instance().insertLink("EJBAttribute", id, "feature", "class", ejbClassId, "EJBClass");
    }

    // EJBAssociationEnd
    /**
     *
     * @param id
     * @param name
     * @param lower
     * @param upper
     * @param composition
     * @param typeId
     * @param ejbClassId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBAssociationEnd(String id, String name, String lower, String upper, Boolean composition, String typeId, String ejbClassId) throws ContractException {
        return insertEJBAssociationEndStub(id, name, lower, upper, composition)
                && insertEJBAssociationEndTypeLink(id, typeId)
                && insertEJBAssociationEndClassLink(id, ejbClassId);
    }
    /**
     *
     * @param id
     * @param name
     * @param lower
     * @param upper
     * @param composition
     * @return
     * @throws ContractException
     */
    public boolean insertEJBAssociationEndStub(String id, String name, String lower, String upper, Boolean composition) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("EJBAssociationEnd", id);
        result &= manager.insertValue("EJBAssociationEnd", "name", id, name == null ? "" : name);
        result &= manager.insertValue("EJBAssociationEnd", "lower", id, lower == null ? "" : lower);
        result &= manager.insertValue("EJBAssociationEnd", "upper", id, upper == null ? "" : upper);
        result &= manager.insertValue("EJBAssociationEnd", "composition", id, composition == true ? "true" : "false");
        return result;
    }
    /**
     *
     * @param id
     * @param typeId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBAssociationEndTypeLink(String id, String typeId) throws ContractException {
        return ModelManager.instance().insertLink("EJBAssociationEnd", id, "typed", "type", typeId, "EJBClassifier");
    }
    /**
     *
     * @param id
     * @param ejbClassId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBAssociationEndClassLink(String id, String ejbClassId) throws ContractException {
        return ModelManager.instance().insertLink("EJBAssociationEnd", id, "feature", "class", ejbClassId, "EJBClass");
    }

    // EJBServingAttribute
    /**
     *
     * @param id
     * @param name
     * @param lower
     * @param upper
     * @param composition
     * @param typeId
     * @param ejbClassId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBServingAttribute(String id, String name, String lower, String upper, Boolean composition, String typeId, String ejbClassId) throws ContractException {
        return insertEJBServingAttributeStub(id, name, lower, upper, composition)
                && insertEJBServingAttributeTypeLink(id, typeId)
                && insertEJBServingAttributeClassLink(id, ejbClassId);
    }
    /**
     *
     * @param id
     * @param name
     * @param lower
     * @param upper
     * @param composition
     * @return
     * @throws ContractException
     */
    public boolean insertEJBServingAttributeStub(String id, String name, String lower, String upper, Boolean composition) throws ContractException {
        ModelManager manager = ModelManager.instance();
        boolean result = true;
        result &= manager.insertObject("EJBServingAttribute", id);
        result &= manager.insertValue("EJBServingAttribute", "name", id, name == null ? "" : name);
        result &= manager.insertValue("EJBServingAttribute", "lower", id, lower == null ? "" : lower);
        result &= manager.insertValue("EJBServingAttribute", "upper", id, upper == null ? "" : upper);
        result &= manager.insertValue("EJBServingAttribute", "composition", id, composition == true ? "true" : "false");
        return result;
    }
    /**
     *
     * @param id
     * @param typeId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBServingAttributeTypeLink(String id, String typeId) throws ContractException {
        return ModelManager.instance().insertLink("EJBServingAttribute", id, "typed", "type", typeId, "EJBClassifier");
    }
    /**
     *
     * @param id
     * @param ejbClassId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBServingAttributeClassLink(String id, String ejbClassId) throws ContractException {
        return ModelManager.instance().insertLink("EJBServingAttribute", id, "feature", "class", ejbClassId, "EJBClass");
    }

    // code generation
    private String convertEjbTypeToJavaType(String ejbType) throws ContractException {
        ModelManager manager = ModelManager.instance();
//        if ("EJBInteger".equals(ejbType)) {
//            return "Integer";
//        }
//        if ("EJBDouble".equals(ejbType)) {
//            return "Double";
//        }
//        if ("EJBString".equals(ejbType)) {
//            return "String";
//        }
//        if ("EJBDate".equals(ejbType)) {
//            return "Date";
//        }
//        if ("EJBBoolean".equals(ejbType)) {
//            return "Boolean";
//        }
//        if ("EJBVoid".equals(ejbType)) {
//            return "void";
//        }
        if ("true".equals(manager.query(ejbType + ".oclIsTypeOf(EJBDataClass)").replace("'", ""))) {
            return manager.query(ejbType + ".name").replace("'", "") + "DataObject";
        }
        if ("true".equals(manager.query(ejbType + ".oclIsTypeOf(EJBSet)").replace("'", ""))) {
            String collectionType = ModelManager.processQueryResult(manager.query(ejbType + ".elementType.name"))[0];
            if (collectionType.equals("void"))
                return "Collection";
            return "Collection<" + collectionType + ">";
        }
        return manager.query(ejbType + ".name").replace("'", "");
    }

    private void generateEJBKeyClass() throws ContractException {
        ModelManager manager = ModelManager.instance();
        
        String query = manager.query("EJBKeyClass.allInstances()");
        String[] ids = ModelManager.processQueryResult(query);

        for (String id : ids) {
            try {
                String idClass = id.replace("'", "").trim();

                Class keyClass = JavaSyntax.getJavaClass();
                keyClass.setDirectoryPath("generated_code/");
                keyClass.setPackagePath("app");

                query = idClass + ".name";

                keyClass.setName(manager.query(query).replace("'", ""));
                keyClass.addClassNamesThatImplements("java.io.Serializable");
                keyClass.addConstructor(new Constructor(keyClass.getName(), ""));
                keyClass.setVisibility("public");

                query = "EJBAttribute.allInstances()->select(attr | attr.class = " + idClass + ")->asSet()";
                String[] attributeIds = ModelManager.processQueryResult(manager.query(query));
                for (String attributeId : attributeIds) {
                    String idAtributo = attributeId.replace("'", "").trim();
                    Attribute attribute = JavaSyntax.getJavaAttribute();
                    attribute.setVisibility("public");
                    query = idAtributo + ".type";
                    attribute.setType(convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(query))[0]));
                    query = idAtributo + ".name";
                    attribute.setName(ModelManager.processQueryResult(manager.query(query))[0], true);

                    keyClass.addAttribute(attribute);
                }

                StringBuilder sourceCode = new StringBuilder();
                for (Attribute attribute : keyClass.getAttributes()) {
                    sourceCode.append("this.")
                            .append(attribute.getName())
                            .append(" = ")
                            .append(attribute.getName())
                            .append(";\n");
                }

                Constructor constructor = JavaSyntax.getJavaConstructor().setClassName(keyClass.getName()).setCode(sourceCode.toString());
                for (Attribute attribute : keyClass.getAttributes()) {
                    constructor.addParameter(JavaSyntax.getJavaParameter().setName(attribute.getName()).setType(attribute.getType()));
                }
                keyClass.addConstructor(constructor);

                // Insere 'public boolean equals(Object obj)'
                Method equals = JavaSyntax.getJavaMethod().setVisibility("public").setReturnType("boolean").setName("equals").addParameter("Object", "obj");
                StringBuilder equalsCondition = new StringBuilder();
                for (int i = 0; i < keyClass.getAttributes().size() - 1; i++) {
                    Attribute attribute = keyClass.getAttributes().get(i);
                    equalsCondition.append("this.")
                            .append(attribute.getName())
                            .append(".equals(other.")
                            .append(attribute.getName())
                            .append(") and ");
                }
                Attribute lastKeyClassAttribute = keyClass.getAttributes().get(keyClass.getAttributes().size() - 1);
                equalsCondition.append("this.")
                        .append(lastKeyClassAttribute.getName())
                        .append(".equals(other.")
                        .append(lastKeyClassAttribute.getName())
                        .append(")");
                equals.setCode(new String[]{
                            "if(!(obj instanceof " + keyClass.getName() + ")) {",
                            "\treturn false;",
                            "}",
                            keyClass.getName() + " other = (" + keyClass.getName() + ") obj;",
                            "if (" + equalsCondition + ") {",
                            "\treturn true;",
                            "}",
                            "return false;"
                        });
                keyClass.addMethod(equals);

                // Insere 'public int hashCode()'
                Method hashCode = JavaSyntax.getJavaMethod().setVisibility("public").setReturnType("int").setName("hashCode");
                String[] hashCodeCode = new String[keyClass.getAttributes().size() + 2];
                int initialHashValue = 3;
                int otherHashValue = 19;
                hashCodeCode[0] = "int hash = " + initialHashValue + ";";
                hashCodeCode[hashCodeCode.length - 1] = "return hash;";
                for (int i = 0; i < keyClass.getAttributes().size(); i++) {
                    Attribute attribute = keyClass.getAttributes().get(i);
                    hashCodeCode[i + 1] = "hash = " + otherHashValue + " * hash + (this." + attribute.getName() + " != null ? this." + attribute.getName() + ".hashCode() : 0);";
                }
                hashCode.setCode(hashCodeCode);
                keyClass.addMethod(hashCode);

                keyClass.persist();
            } catch (Exception ex) {
                throw new ContractException("Can't persist an EJBKeyClass", ex);
            }
        }
    }

    private void generateEJBEntityComponent() throws ContractException {
        ModelManager manager = ModelManager.instance();
        
        String[] ids = ModelManager.processQueryResult(manager.query("UMLClassToEJBEntityComponent.allInstances()").replace("\n", ""));

        for (String id : ids) {
            try {
                // Criando o EJBObject
                Interface ejbObject = JavaSyntax.getJavaInterface();
                ejbObject.setDirectoryPath("generated_code/");
                ejbObject.setPackagePath("app");

                ejbObject.addImportPath("java.rmi.*");
                ejbObject.addImportPath("javax.naming.*");
                ejbObject.addImportPath("javax.ejb.*");
                ejbObject.addImportPath("java.util.*");
                ejbObject.setName(ModelManager.processQueryResult(manager.query(id + ".entityComponent.name"))[0]);
                ejbObject.addClassNamesThatExtends("EJBObject");
                ejbObject.setVisibility("public");

                // Insere getter e setter para o DataClass
                String dataClassId = ModelManager.processQueryResult(manager.query(id + ".dataClass"))[0];
                String dataClassName = convertEjbTypeToJavaType(dataClassId);
                Method getterDataClass = JavaSyntax.getJavaMethod();
                getterDataClass.setName("get" + dataClassName).setReturnType(dataClassName).setVisibility("public").addException("RemoteException").setAbstractMethod(true);
                ejbObject.addMethod(getterDataClass);

                Method setterDataClass = JavaSyntax.getJavaMethod();
                setterDataClass.setName("set" + dataClassName).setReturnType("void").setVisibility("public").addParameter(dataClassName, "update").addException("NamingException").addException("FinderException").addException("CreateException").addException("RemoteException").setAbstractMethod(true);
                ejbObject.addMethod(setterDataClass);

                String[] businessMethodIds = ModelManager.processQueryResult(manager.query(id + ".entityComponent.feature->select(f : EJBFeature | f.oclIsTypeOf(BusinessMethod))->collect(f : EJBFeature | f.oclAsType(BusinessMethod))->select(bm : BusinessMethod | bm.name.substring(1, 4) <> 'find')"));
                for (String businessMethodId : businessMethodIds) {
                    Method method = JavaSyntax.getJavaMethod();
                    method.setVisibility("public").setReturnType(convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(businessMethodId + ".type"))[0])).setName(manager.query(businessMethodId + ".name").replace("'", "")).addException("RemoteException").setAbstractMethod(true);

                    String[] parameterIds = ModelManager.processQueryResult(manager.query(businessMethodId + ".parameter"));
                    for (String parameterId : parameterIds) {
                        Parameter parameter = JavaSyntax.getJavaParameter();
                        parameter.setName(manager.query(parameterId + ".name").replace("'", ""), true).setType(convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(parameterId + ".type"))[0]));
                        method.addParameter(parameter);
                    }
                    ejbObject.addMethod(method);
                }

                ejbObject.persist();

                // Criando o EJBHome

                Interface ejbHome = JavaSyntax.getJavaInterface();
                ejbHome.setDirectoryPath("generated_code/");
                ejbHome.setPackagePath("app");

                ejbHome.addImportPath("java.rmi.*");
                ejbHome.addImportPath("javax.naming.*");
                ejbHome.addImportPath("javax.ejb.*");
                ejbHome.addImportPath("java.util.*");
                ejbHome.setName(ModelManager.processQueryResult(manager.query(id + ".entityComponent.name"))[0] + "Home");
                ejbHome.addClassNamesThatExtends("EJBHome");
                ejbHome.setVisibility("public");

                String primaryKeyId = ModelManager.processQueryResult(manager.query("UMLClassToEJBKeyClass.allInstances()->select(a | a.class = " + id + ".class).keyClass"))[0];
                String primaryKeyName = manager.query(primaryKeyId + ".name").replace("'", "");
                // Insere create(...)
                Method createMethod = JavaSyntax.getJavaMethod();
                createMethod.setName("create").setVisibility("public").setReturnType(ejbObject.getName()).setAbstractMethod(true).addException("CreateException").addException("RemoteException");
                // Adicionar parametros
                // 1) Relacoes do DataClass
//                for (String idAssociationEnd : tratarResultadoQuery(this.dominio.query(idDataClass + ".feature->select(f : EJBFeature | f.oclIsTypeOf(EJBAssociationEnd))"))) {
//                    if (!"1".equals(this.dominio.query(idAssociationEnd + ".upper").replace("'", ""))) {
//                        boolean isDataClass = "true".equals(this.dominio.query(idAssociationEnd + ".type->asOrderedSet()->first().oclIsKindOf(EJBDataClass)"));
//                        create.addParametro("List<" + (isDataClass ? tratarResultadoQuery(this.dominio.query(idAssociationEnd + ".type.name"))[0] + "DataObject" : tratarResultadoQuery(this.dominio.query(idAssociationEnd + ".type.name"))[0]) + ">", this.dominio.query(idAssociationEnd + ".name").replace("'", ""));
//                    } else {
//                        create.addParametro(tratarResultadoQuery(this.dominio.query(idAssociationEnd + ".type.name"))[0], this.dominio.query(idAssociationEnd + ".name").replace("'", ""));
//                    }
//                }
//                // 2) Atributos do DataClass
//                for (String idAttribute : tratarResultadoQuery(this.dominio.query(idDataClass + ".feature->select(f : EJBFeature | f.oclIsTypeOf(EJBAttribute))"))) {
//                    create.addParametro(converterTipoEjbParaJava(tratarResultadoQuery(this.dominio.query(idAttribute + ".type"))[0]), this.dominio.query(idAttribute + ".name").replace("'", ""));
//                }
                createMethod.addParameter(dataClassName, "newObject");
                ejbHome.addMethod(createMethod);

                // Insere findByPrimaryKey(PK)
                Method findByPrimaryKeyMethod = JavaSyntax.getJavaMethod();
                findByPrimaryKeyMethod.setName("findByPrimaryKey").setVisibility("public").setReturnType(ejbObject.getName()).setAbstractMethod(true).addException("FinderException").addException("RemoteException");
                // Adiciona parametro (PK)
                findByPrimaryKeyMethod.addParameter(primaryKeyName, "primaryKey");
                ejbHome.addMethod(findByPrimaryKeyMethod);

                // Insere os outros metodos tipo finder
                String[] finderIds = ModelManager.processQueryResult(manager.query(id + ".entityComponent.feature->select(f : EJBFeature | f.oclIsTypeOf(BusinessMethod))->collect(f : EJBFeature | f.oclAsType(BusinessMethod))->select(bm : BusinessMethod | bm.name.substring(1, 4) = 'find')"));
                for (String finderId : finderIds) {
                    String finderName = manager.query(finderId + ".name").replace("'", "");
                    Method method = JavaSyntax.getJavaMethod();
                    method.setVisibility("public").setName(finderName).addException("RemoteException").addException("FinderException").setAbstractMethod(true);

                    if (finderName.startsWith("findAll") || finderName.startsWith("findMany")) {
                        method.setReturnType("java.util.Collection<" + ejbObject.getName() + ">");
                    } else {
                        method.setReturnType(ejbObject.getName());
                    }

                    String[] parameterIds = ModelManager.processQueryResult(manager.query(finderId + ".parameter"));
                    for (String parameterId : parameterIds) {
                        Parameter parameter = JavaSyntax.getJavaParameter();
                        parameter.setName(manager.query(parameterId + ".name").replace("'", ""), true).setType(convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(parameterId + ".type"))[0]));
                        method.addParameter(parameter);
                    }
                    ejbHome.addMethod(method);
                }



                ejbHome.persist();

                // Criar o EntityBean correspondente
                Class entityBean = JavaSyntax.getJavaClass();
                entityBean.setDirectoryPath("generated_code/");
                entityBean.setPackagePath("app");

//                classe.addImport("java.rmi.*");
                entityBean.addImportPath("javax.naming.*");
                entityBean.addImportPath("javax.ejb.*");
                entityBean.addImportPath("java.util.*");
                entityBean.setName(ModelManager.processQueryResult(manager.query(id + ".entityComponent.name"))[0] + "Bean");
                entityBean.addClassNamesThatImplements("EntityBean");
                entityBean.setVisibility("public");

                // EJB stuff
                entityBean.addAttribute(JavaSyntax.getJavaAttribute().setName("context").setType("EntityContext").setVisibility("private"));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("setEntityContext").setVisibility("public").setReturnType("void").setCode(new String[]{"this.context = context;"}).addParameter("EntityContext", "context"));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("unsetEntityContext").setVisibility("public").setReturnType("void").setCode(new String[]{"this.context = null;"}));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbActivate").setVisibility("public").setReturnType("void"));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbPassivate").setVisibility("public").setReturnType("void"));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbRemove").setVisibility("public").setReturnType("void"));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbLoad").setVisibility("public").setReturnType("void"));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbStore").setVisibility("public").setReturnType("void"));

                // Adicionando atributo 'key'
                Attribute primaryKeyAttribute = JavaSyntax.getJavaAttribute();
                primaryKeyAttribute.setName("key").setVisibility("private").setType(dataClassName);
                entityBean.addAttribute(primaryKeyAttribute);

                // Adicionando getter para 'key'
                Method getterPrimaryKey = JavaSyntax.getJavaMethod();
                getterPrimaryKey.setVisibility("public").setReturnType(dataClassName).setName("get" + StringUtils.upperFirstLetter(dataClassName)).setCode(new String[]{"return key;"});
                entityBean.addMethod(getterPrimaryKey);

                // Adicionando setter para 'key'
                Method setterPrimaryKey = JavaSyntax.getJavaMethod();
                setterPrimaryKey.setVisibility("public").setReturnType("void").setName("set" + StringUtils.upperFirstLetter(dataClassName)).addParameter(dataClassName, "newObj").setCode(new String[]{"this.key = newObj;"}).addException("NamingException").addException("FinderException").addException("CreateException");
                entityBean.addMethod(setterPrimaryKey);

                // Adiciona ejbCreate
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbCreate").setVisibility("public").setReturnType(primaryKeyName).addException("NamingException").addException("FinderException").addException("CreateException").setCode(new String[]{"if (newObj == null) {", "\tthrow new CreateException(\"The field 'newObj' must not be null\");", "}", "", "// TODO add additional validation code, throw CreateException if data is not valid", setterPrimaryKey.getName() + "(newObj);", "", "return null;"}).addParameter(dataClassName, "newObj"));

                // Adiciona ejbPostCreate (Ver se da pra automatizar)
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbPostCreate").setVisibility("public").setReturnType("void").setCode(new String[]{"// TODO populate relationships here if appropriate"}).addParameter(dataClassName, "newObj"));

                // Adicionando construtor padrao
                entityBean.addConstructor(JavaSyntax.getJavaConstructor().setClassName(entityBean.getName()));

                // Adicionando o metodo create
//                Method createEntityBeanMethod = JavaSyntax.getJavaMethod().setName("create").setVisibility("public").setRetorno(primaryKeyName).addException("CreateException").setCode(new String[]{"// TODO - You must decide how your persistence will work.", "return null;"});
//                for (String idAssociationEnd : tratarResultadoQuery(this.dominio.query(idDataClass + ".feature->select(f : EJBFeature | f.oclIsTypeOf(EJBAssociationEnd))"))) {
//                    if (!"1".equals(this.dominio.query(idAssociationEnd + ".upper").replace("'", ""))) {
//                        create.addParametro("List<" + tratarResultadoQuery(this.dominio.query(idAssociationEnd + ".type.name"))[0] + ">", this.dominio.query(idAssociationEnd + ".name").replace("'", ""));
//                    } else {
//                        create.addParametro(tratarResultadoQuery(this.dominio.query(idAssociationEnd + ".type.name"))[0], this.dominio.query(idAssociationEnd + ".name").replace("'", ""));
//                    }
//                }
//                // 2) Atributos do DataClass
//                for (String idAttribute : tratarResultadoQuery(this.dominio.query(idDataClass + ".feature->select(f : EJBFeature | f.oclIsTypeOf(EJBAttribute))"))) {
//                    createEB.addParametro(converterTipoEjbParaJava(tratarResultadoQuery(this.dominio.query(idAttribute + ".type"))[0]), this.dominio.query(idAttribute + ".name").replace("'", ""));
//                }
//                entityBean.addMethod(createEntityBeanMethod);
                // Adicionando o metodo findByPrimaryKey
//                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("findByPrimaryKey").setVisibility("public").setRetorno(ejbObject.getName()).addException("FinderException").addParameter(primaryKeyName, "primaryKey").setCode(new String[]{"// TODO - You must decide how your persistence will work.", "return null;"}));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbFindByPrimaryKey").setVisibility("public").setReturnType(primaryKeyName).addException("FinderException").addParameter(primaryKeyName, "primaryKey").setCode(new String[]{"// TODO - You must decide how your persistence will work.", "return null;"}));

                // Adicionando metodos oriundos da transformacao
                for (String finderId : finderIds) {
                    String finderName = manager.query(finderId + ".name").replace("'", "");
                    Method method = JavaSyntax.getJavaMethod();
                    method.setVisibility("public");
                    if (finderName.startsWith("findAll") || finderName.startsWith("findMany")) {
                        method.setReturnType("java.util.Collection<" + primaryKeyName + ">");
                    } else {
                        method.setReturnType(primaryKeyName);
                    }
                    method.setName("ejb" + StringUtils.upperFirstLetter(finderName)).setCode(new String[]{"// TODO", "return null;"});
                    method.addException("FinderException");

                    String[] parameterIds = ModelManager.processQueryResult(manager.query(finderId + ".parameter"));
                    for (String parameterId : parameterIds) {
                        Parameter parameter = JavaSyntax.getJavaParameter();
                        parameter.setName(manager.query(parameterId + ".name").replace("'", ""), true).setType(convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(parameterId + ".type"))[0]));
                        method.addParameter(parameter);
                    }
                    entityBean.addMethod(method);
                }

                for (String businessMethodId : businessMethodIds) {
                    Method method = JavaSyntax.getJavaMethod();
                    method.setVisibility("public").setName(manager.query(businessMethodId + ".name").replace("'", ""));

                    String returnType = convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(businessMethodId + ".type"))[0]);
                    method.setReturnType(returnType);
                    if ("void".equals(returnType)) {
                        method.setCode(new String[]{"// TODO"});
                    } else {
                        method.setCode(new String[]{"// TODO", "return null;"});
                    }

                    String[] parameterIds = ModelManager.processQueryResult(manager.query(businessMethodId + ".parameter"));
                    for (String parameterId : parameterIds) {
                        Parameter parameter = JavaSyntax.getJavaParameter();
                        parameter.setName(manager.query(parameterId + ".name").replace("'", ""), true).setType(convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(parameterId + ".type"))[0]));
                        method.addParameter(parameter);
                    }
                    entityBean.addMethod(method);
                }

                entityBean.persist();
            } catch (Exception ex) {
                throw new ContractException("Can't persist an EJBEntityComponent", ex);
            }
        }
    }

    private void generateEJBDataClass() throws ContractException {
        ModelManager manager = ModelManager.instance();
        String[] ids = ModelManager.processQueryResult(manager.query("EJBDataClass.allInstances()").replace("\n", ""));

        for (String id : ids) {
            try {

                Class dataClass = JavaSyntax.getJavaClass();
                dataClass.setDirectoryPath("generated_code/");
                dataClass.setPackagePath("app");

                dataClass.addImportPath("java.util.*");
                dataClass.setName(manager.query(id + ".name").replace("'", "") + "DataObject");
                dataClass.setVisibility("public");
                dataClass.addClassNamesThatImplements("java.io.Serializable");

                // Adicionando atributo 'key'
                String umlClassId = ModelManager.processQueryResult(manager.query("Class.allInstances()->select(c | c.name = " + id + ".name)"))[0];
                String keyClassId = ModelManager.processQueryResult(manager.query("if " + umlClassId + ".oclIsTypeOf(Class) then UMLClassToEJBKeyClass.allInstances()->select(a | a.class = " + umlClassId + ").keyClass else UMLAssociationClassToEJBKeyClass.allInstances()->select(a | a.associationClass.oclAsType(Class) = " + umlClassId + ").keyClass endif"))[0];
                String keyClassName = manager.query(keyClassId + ".name").replace("'", "");
                Attribute primaryKey = JavaSyntax.getJavaAttribute().setName("key").setType(keyClassName).setVisibility("private");
                dataClass.addAttribute(primaryKey);

                // Adicionando construtor padrao
                dataClass.addConstructor(JavaSyntax.getJavaConstructor().setClassName(dataClass.getName()));

                // Adicionando construtor com o 'key' como parametro
                Constructor constructor = JavaSyntax.getJavaConstructor();
                constructor.setClassName(dataClass.getName()).addParameter(JavaSyntax.getJavaParameter().setName("key").setType(manager.query(keyClassId + ".name").replace("'", "")));
                constructor.setCode("this.key = key;");
                dataClass.addConstructor(constructor);

                // Adicionando getter para 'key'
                Method getterPrimaryKey = JavaSyntax.getJavaMethod();
                getterPrimaryKey.setVisibility("public").setReturnType(keyClassName).setName("get" + StringUtils.upperFirstLetter(keyClassName)).setCode(new String[]{"return key;"});
                dataClass.addMethod(getterPrimaryKey);

                // Adicionando setter para 'key'
                Method setterPrimaryKey = JavaSyntax.getJavaMethod();
                setterPrimaryKey.setVisibility("public").setReturnType("void").setName("set" + StringUtils.upperFirstLetter(keyClassName)).addParameter(keyClassName, "key").setCode(new String[]{"this.key = key;"});
                dataClass.addMethod(setterPrimaryKey);

                // Insere relacionamentos (AssociationEnd) com getters e setters
                String[] associationEndIds = null;
                // 1) upper = '1'
                associationEndIds = ModelManager.processQueryResult(manager.query(id + ".feature->select(f : EJBFeature | f.oclIsTypeOf(EJBAssociationEnd))->collect(f : EJBFeature | f.oclAsType(EJBAssociationEnd))->select(ae : EJBAssociationEnd | ae.upper = '1')"));
                for (String associationEndId : associationEndIds) {
//                    String nome = this.dominio.query(idAssocEnd + ".name").replace("'", "") + tratarResultadoQuery(this.dominio.query(idAssocEnd + ".type.name"))[0];
                    String name = manager.query(associationEndId + ".name").replace("'", "");
                    boolean isDataClass = "true".equals(manager.query(associationEndId + ".type.oclIsTypeOf(EJBDataClass)").replace("'", ""));
                    String type = isDataClass ? ModelManager.processQueryResult(manager.query(associationEndId + ".type.name"))[0] + "DataObject" : ModelManager.processQueryResult(manager.query(associationEndId + ".type.name"))[0];
                    Attribute attribute = JavaSyntax.getJavaAttribute().setVisibility("private").setType(type).setName(name);
                    dataClass.addAttribute(attribute);

                    Method getter = JavaSyntax.getJavaMethod();
                    getter.setName("get" + StringUtils.upperFirstLetter(name)).setReturnType(type).setVisibility("public").setCode(new String[]{"return " + name + ";"});
                    dataClass.addMethod(getter);

                    Method setter = JavaSyntax.getJavaMethod();
                    setter.setName("set" + StringUtils.upperFirstLetter(name)).setReturnType("void").setVisibility("public").addParameter(type, name).setCode(new String[]{"this." + name + " = " + name + ";"});
                    dataClass.addMethod(setter);

                }
                // 2) upper <> '1'
                associationEndIds = ModelManager.processQueryResult(manager.query(id + ".feature->select(f : EJBFeature | f.oclIsTypeOf(EJBAssociationEnd))->collect(f : EJBFeature | f.oclAsType(EJBAssociationEnd))->select(ae : EJBAssociationEnd | ae.upper <> '1')"));
                for (String associationEndId : associationEndIds) {
                    String simpleName = manager.query(associationEndId + ".name").replace("'", "");
//                    String nomeCompleto = nomeSimples + tratarResultadoQuery(this.dominio.query(idAssocEnd + ".type.name"))[0];
                    String type = "true".equals(manager.query(associationEndId + ".type.oclIsTypeOf(EJBDataClass)").replace("'", "")) ? ModelManager.processQueryResult(manager.query(associationEndId + ".type.name"))[0] + "DataObject" : ModelManager.processQueryResult(manager.query(associationEndId + ".type.name"))[0];
                    String listType = "List<" + type + ">";
//                    Atributo atrib = SintaxeJava.getJavaAttribute().setVisibilidade("private").setTipo(tipoLista).setNome(nomeCompleto);
                    Attribute attribute = JavaSyntax.getJavaAttribute().setVisibility("private").setType(listType).setName(simpleName);
                    dataClass.addAttribute(attribute);

                    Method getter = JavaSyntax.getJavaMethod();
                    getter.setName("get" + StringUtils.upperFirstLetter(simpleName)).setReturnType(listType).setVisibility("public").setCode(new String[]{"return " + simpleName + ";"});
                    dataClass.addMethod(getter);

                    Method add = JavaSyntax.getJavaMethod();
                    add.setName("add" + StringUtils.upperFirstLetter(simpleName)).setReturnType("void").setVisibility("public").addParameter(type, simpleName).setCode(new String[]{"if (this." + simpleName + " == null) {", "\tthis." + simpleName + " = new ArrayList<" + type + ">();", "}", "this." + simpleName + ".add(" + simpleName + ");"});
                    dataClass.addMethod(add);

                    Method remove = JavaSyntax.getJavaMethod();
                    remove.setName("remove" + StringUtils.upperFirstLetter(simpleName)).setReturnType("void").setVisibility("public").addParameter(type, simpleName).setCode(new String[]{"this." + simpleName + ".remove(" + simpleName + ");"});
                    dataClass.addMethod(remove);
                }

                // Insere atributos (visibilidade = 'private') com getters e setters
                String[] attributeIds = ModelManager.processQueryResult(manager.query(id + ".feature->select(f : EJBFeature | f.oclIsTypeOf(EJBAttribute))"));
                for (String attributeId : attributeIds) {
                    String attributeName = manager.query(attributeId + ".name").replace("'", "");
                    // mudei aki

                    String attributeType = convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(attributeId + ".type"))[0]);

                    Attribute attribute = JavaSyntax.getJavaAttribute();
                    attribute.setName(attributeName).setType(attributeType).setVisibility("private");
                    dataClass.addAttribute(attribute);

                    Method getter = JavaSyntax.getJavaMethod();
                    getter.setName("get" + StringUtils.upperFirstLetter(attributeName)).setReturnType(attributeType).setVisibility("public").setCode(new String[]{"return " + attributeName + ";"});
                    dataClass.addMethod(getter);

                    Method setter = JavaSyntax.getJavaMethod();
                    setter.setName("set" + StringUtils.upperFirstLetter(attributeName)).setReturnType("void").setVisibility("public").addParameter(attributeType, attributeName).setCode(new String[]{"this." + attributeName + " = " + attributeName + ";"});
                    dataClass.addMethod(setter);
                }

                dataClass.persist();
            } catch (Exception ex) {
                throw new ContractException("Can't persist an EJBDataClass", ex);
            }
        }
    }
}
