package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.Domain;
import br.uff.ic.mda.tclib.IValidator;
import br.uff.ic.mda.tclib.IXMIParser;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.syntax.ejb.EJBCodeGenerator;
import br.uff.ic.mda.transformer.core.syntax.ejb.EJBMetamodeler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Daniel
 */
public class EJBDomain extends Domain {
    public static final String ROLE_TYPE = "ejbtype";
    public static final String ROLE_TYPED = "ejbtyped";

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
        EJBMetamodeler.createMetamodel();
    }

    /**
     *
     * @throws ContractException
     */
    @Override
    public void printDomain() throws ContractException {
        EJBCodeGenerator.generate();
    }

    @Override
    protected void loadDataFromParser(IXMIParser parser) throws ContractException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void createSpecificationOfCurrentDiagram() throws ContractException {
        EJBMetamodeler.createSpecificationOfCurrentDiagram();
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
        result &= manager.insertObject(EJBMetamodeler.DATASCHEMA, id);
        result &= manager.insertValue(EJBMetamodeler.DATASCHEMA, "name", id, name == null ? "" : name);
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
        result &= manager.insertObject(EJBMetamodeler.DATATYPE, id);
        result &= manager.insertValue(EJBMetamodeler.DATATYPE, "name", id, name == null ? "" : name);
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
        result &= manager.insertObject(EJBMetamodeler.SET, id);
        result &= manager.insertValue(EJBMetamodeler.SET, "name", id, name == null ? "" : name);
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

        return ModelManager.instance().insertLink(EJBMetamodeler.SET, id, "set", "elementType", idType, EJBMetamodeler.CLASSIFIER);
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
        result &= manager.insertObject(EJBMetamodeler.KEYCLASS, id);
        result &= manager.insertValue(EJBMetamodeler.KEYCLASS, "name", id, name == null ? "" : name);
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
        result &= manager.insertObject(EJBMetamodeler.DATACLASS, id);
        result &= manager.insertValue(EJBMetamodeler.DATACLASS, "name", id, name == null ? "" : name);
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
        return ModelManager.instance().insertLink(EJBMetamodeler.DATASCHEMA, ejbDataSchemaId, "package", "element", id, EJBMetamodeler.DATACLASS);
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
        result &= manager.insertObject(EJBMetamodeler.DATAASSOCIATION, id);
        result &= manager.insertValue(EJBMetamodeler.DATAASSOCIATION, "name", id, name == null ? "" : name);
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
        return ModelManager.instance().insertLink(EJBMetamodeler.DATASCHEMA, ejbDataSchemaId, "package", "element", id, EJBMetamodeler.DATAASSOCIATION);
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
            result &= manager.insertLink(EJBMetamodeler.DATAASSOCIATION, id, "association", "associationEnds", ejbAssociationEnd, EJBMetamodeler.ASSOCIATIONEND);
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
        result &= manager.insertObject(EJBMetamodeler.SESSIONCOMPONENT, id);
        result &= manager.insertValue(EJBMetamodeler.SESSIONCOMPONENT, "name", id, name == null ? "" : name);
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
        result &= manager.insertObject(EJBMetamodeler.ENTITYCOMPONENT, id);
        result &= manager.insertValue(EJBMetamodeler.ENTITYCOMPONENT, "name", id, name == null ? "" : name);
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
        result &= manager.insertObject(EJBMetamodeler.TABLE, id);
        result &= manager.insertValue(EJBMetamodeler.TABLE, "name", id, name == null ? "" : name);
        result &= manager.insertLink(EJBMetamodeler.ENTITYCOMPONENT, ejbEntityComponentId, "entityComp", "usedTable", id, EJBMetamodeler.TABLE);
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
        result &= manager.insertObject(EJBMetamodeler.BUSINESSMETHOD, id);
        result &= manager.insertValue(EJBMetamodeler.BUSINESSMETHOD, "name", id, name == null ? "" : name);
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
        return ModelManager.instance().insertLink(EJBMetamodeler.BUSINESSMETHOD, id, ROLE_TYPED, ROLE_TYPE, typeId, EJBMetamodeler.CLASSIFIER);
    }
    /**
     *
     * @param id
     * @param ejbClassId
     * @return
     * @throws ContractException
     */
    public boolean insertBusinessMethodClassLink(String id, String ejbClassId) throws ContractException {
        return ModelManager.instance().insertLink(EJBMetamodeler.BUSINESSMETHOD, id, "feature", "class", ejbClassId, "EJBClass");
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
        result &= manager.insertObject(EJBMetamodeler.PARAMETER, id);
        result &= manager.insertValue(EJBMetamodeler.PARAMETER, "name", id, name == null ? "" : name);
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
        return ModelManager.instance().insertLink(EJBMetamodeler.PARAMETER, id, ROLE_TYPED, ROLE_TYPE, typeId, EJBMetamodeler.CLASSIFIER);
    }
    /**
     *
     * @param id
     * @param businessMethodId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBParameterBusinessMethodLink(String id, String businessMethodId) throws ContractException {
        return ModelManager.instance().insertLink(EJBMetamodeler.BUSINESSMETHOD, businessMethodId, "operation", "parameter", id, EJBMetamodeler.PARAMETER);
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
        result &= manager.insertObject(EJBMetamodeler.ATTRIBUTE, id);
        result &= manager.insertValue(EJBMetamodeler.ATTRIBUTE, "name", id, name == null ? "" : name);
        result &= manager.insertValue(EJBMetamodeler.ATTRIBUTE, "visibility", id, visibility == null ? "" : visibility);
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
        return ModelManager.instance().insertLink(EJBMetamodeler.ATTRIBUTE, id, ROLE_TYPED, ROLE_TYPE, typeId, EJBMetamodeler.CLASSIFIER);
    }
    /**
     *
     * @param id
     * @param ejbClassId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBAttributeClassLink(String id, String ejbClassId) throws ContractException {
        return ModelManager.instance().insertLink(EJBMetamodeler.ATTRIBUTE, id, "feature", "class", ejbClassId, "EJBClass");
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
        result &= manager.insertObject(EJBMetamodeler.ASSOCIATIONEND, id);
        result &= manager.insertValue(EJBMetamodeler.ASSOCIATIONEND, "name", id, name == null ? "" : name);
        result &= manager.insertValue(EJBMetamodeler.ASSOCIATIONEND, "lower", id, lower == null ? "" : lower);
        result &= manager.insertValue(EJBMetamodeler.ASSOCIATIONEND, "upper", id, upper == null ? "" : upper);
        result &= manager.insertValue(EJBMetamodeler.ASSOCIATIONEND, "composition", id, composition == true ? "true" : "false");
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
        return ModelManager.instance().insertLink(EJBMetamodeler.ASSOCIATIONEND, id, ROLE_TYPED, ROLE_TYPE, typeId, EJBMetamodeler.CLASSIFIER);
    }
    /**
     *
     * @param id
     * @param ejbClassId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBAssociationEndClassLink(String id, String ejbClassId) throws ContractException {
        return ModelManager.instance().insertLink(EJBMetamodeler.ASSOCIATIONEND, id, "feature", "class", ejbClassId, "EJBClass");
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
        result &= manager.insertObject(EJBMetamodeler.SERVINGATTRIBUTE, id);
        result &= manager.insertValue(EJBMetamodeler.SERVINGATTRIBUTE, "name", id, name == null ? "" : name);
        result &= manager.insertValue(EJBMetamodeler.SERVINGATTRIBUTE, "lower", id, lower == null ? "" : lower);
        result &= manager.insertValue(EJBMetamodeler.SERVINGATTRIBUTE, "upper", id, upper == null ? "" : upper);
        result &= manager.insertValue(EJBMetamodeler.SERVINGATTRIBUTE, "composition", id, composition == true ? "true" : "false");
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
        return ModelManager.instance().insertLink(EJBMetamodeler.SERVINGATTRIBUTE, id, ROLE_TYPED, ROLE_TYPE, typeId, EJBMetamodeler.CLASSIFIER);
    }
    /**
     *
     * @param id
     * @param ejbClassId
     * @return
     * @throws ContractException
     */
    public boolean insertEJBServingAttributeClassLink(String id, String ejbClassId) throws ContractException {
        return ModelManager.instance().insertLink(EJBMetamodeler.SERVINGATTRIBUTE, id, "feature", "class", ejbClassId, EJBMetamodeler.CLASS);
    }

}
