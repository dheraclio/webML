package br.uff.ic.mda.transformer.core.syntax.ejb;

import br.uff.ic.mda.tclib.ContractException;
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

/**
 *
 * @author Daniel
 */
public class EJBMetamodeler {

    public static final String METAMODEL = "EJBModelElement";
    public static final String CLASSIFIER = "EJBClassifier";
    public static final String TYPED = "EJBTyped";
    public static final String DATASCHEMA = "EJBDataSchema";
    public static final String DATATYPE = "EJBDataType";
    public static final String SET = "EJBSet";
    public static final String CLASS = "EJBClass";
    public static final String DATASCHEMAELEMENT = "EJBDataSchemaElement";
    public static final String KEYCLASS = "EJBKeyClass";
    public static final String COMPONENT = "EJBComponent";
    public static final String DATACLASS = "EJBDataClass";
    public static final String SESSIONCOMPONENT = "EJBSessionComponent";
    public static final String DATAASSOCIATION = "EJBDataAssociation";
    public static final String ENTITYCOMPONENT = "EJBEntityComponent";
    public static final String TABLE = "Table";
    public static final String FEATURE = "EJBFeature";
    public static final String ASSOCIATIONEND = "EJBAssociationEnd";
    public static final String ATTRIBUTE = "EJBAttribute";
    public static final String BUSINESSMETHOD = "BusinessMethod";
    public static final String SERVINGATTRIBUTE = "EJBServingAttribute";
    public static final String PARAMETER = "EJBParameter";

    /**
     * 
     * @throws ContractException
     */
    public static void createMetamodel() throws ContractException {
        insertMetamodelClasses();
        insertMetamodelAttributes();
        insertMetamodelAssociations();
        //insertMetamodelOperations();
    }

    /**
     *
     * @throws ContractException
     */
    public static void createSpecificationOfCurrentDiagram() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertObject(DATASCHEMAELEMENT, "NULL_EJBDSE");
        manager.insertValue(DATASCHEMAELEMENT, "name", "NULL_EJBDSE", "null");
        manager.insertObject(DATASCHEMA, "NULL_EJBDS");
        manager.insertValue(DATASCHEMA, "name", "NULL_EJBDS", "null");
        manager.insertObject(CLASSIFIER, "NULL_EJBC");
        manager.insertValue(CLASSIFIER, "name", "NULL_EJBC", "null");
        manager.insertObject(TYPED, "NULL_EJBT");
        manager.insertValue(TYPED, "name", "NULL_EJBT", "null");
        manager.insertObject(BUSINESSMETHOD, "NULL_BM");
        manager.insertValue(BUSINESSMETHOD, "name", "NULL_BM", "null");

        manager.insertObject(DATATYPE, "EJBInteger");
        manager.insertValue(DATATYPE, "name", "EJBInteger", "Integer");
        manager.insertObject(DATATYPE, "EJBDouble");
        manager.insertValue(DATATYPE, "name", "EJBDouble", "Double");
        manager.insertObject(DATATYPE, "EJBString");
        manager.insertValue(DATATYPE, "name", "EJBString", "String");
        manager.insertObject(DATATYPE, "EJBDate");
        manager.insertValue(DATATYPE, "name", "EJBDate", "Date");
        manager.insertObject(DATATYPE, "EJBBoolean");
        manager.insertValue(DATATYPE, "name", "EJBBoolean", "Boolean");
        manager.insertObject(DATATYPE, "EJBVoid");
        manager.insertValue(DATATYPE, "name", "EJBVoid", "void");
    }

    /**
     *
     * @throws ContractException
     */
    private static void insertMetamodelClasses() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertClass(METAMODEL);
        manager.insertClass(CLASSIFIER);
        manager.insertClass(TYPED);
        manager.insertClass(DATASCHEMA);
        manager.insertClass(DATATYPE);
        manager.insertClass(SET);
        manager.insertClass(CLASS);
        manager.insertClass(DATASCHEMAELEMENT);
        manager.insertClass(KEYCLASS);
        manager.insertClass(COMPONENT);
        manager.insertClass(DATACLASS);
        manager.insertClass(SESSIONCOMPONENT);
        manager.insertClass(DATAASSOCIATION);
        manager.insertClass(ENTITYCOMPONENT);
        manager.insertClass(TABLE);
        manager.insertClass(FEATURE);
        manager.insertClass(ASSOCIATIONEND);
        manager.insertClass(ATTRIBUTE);
        manager.insertClass(BUSINESSMETHOD);
        manager.insertClass(SERVINGATTRIBUTE);
        manager.insertClass(PARAMETER);

        manager.insertGeneralization(CLASSIFIER, METAMODEL);
        manager.insertGeneralization(TYPED, METAMODEL);
        manager.insertGeneralization(TABLE, METAMODEL);
        manager.insertGeneralization(DATASCHEMA, METAMODEL);
        manager.insertGeneralization(DATASCHEMAELEMENT, METAMODEL);
        manager.insertGeneralization(DATATYPE, CLASSIFIER);
        manager.insertGeneralization(SET, DATATYPE);
        manager.insertGeneralization(CLASS, CLASSIFIER);
        manager.insertGeneralization(COMPONENT, CLASS);
        manager.insertGeneralization(KEYCLASS, CLASS);
        manager.insertGeneralization(DATACLASS, CLASS);
        manager.insertGeneralization(SESSIONCOMPONENT, COMPONENT);
        manager.insertGeneralization(ENTITYCOMPONENT, COMPONENT);
        manager.insertGeneralization(DATACLASS, DATASCHEMAELEMENT);
        manager.insertGeneralization(DATAASSOCIATION, DATASCHEMAELEMENT);
        manager.insertGeneralization(FEATURE, TYPED);
        manager.insertGeneralization(PARAMETER, TYPED);
        manager.insertGeneralization(BUSINESSMETHOD, FEATURE);
        manager.insertGeneralization(ATTRIBUTE, FEATURE);
        manager.insertGeneralization(ASSOCIATIONEND, FEATURE);
        manager.insertGeneralization(SERVINGATTRIBUTE, ASSOCIATIONEND);
    }

    /**
     *
     * @throws ContractException
     */
    private static void insertMetamodelAssociations() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertAssociation(CLASSIFIER, "ejbtype", "1", "*", "ejbtyped", TYPED);
        manager.insertAssociation(DATASCHEMA, "package", "1", "*", "element", DATASCHEMAELEMENT);
        manager.insertAssociation(SET, "set", "0..*", "1", "elementType", CLASSIFIER);
        manager.insertAssociation(CLASS, "class", "1", "0..*", "feature", FEATURE);
        manager.insertAssociation(ENTITYCOMPONENT, "entityComp", "0..1", "0..*", "usedTable", TABLE);
        manager.insertAssociation(DATAASSOCIATION, "association", "0..1", "2", "associationEnds", ASSOCIATIONEND);
        manager.insertAssociation(BUSINESSMETHOD, "operation", "1", "0..*", "parameter", PARAMETER);
    }

    /**
     *
     * @throws ContractException
     */
    private static void insertMetamodelAttributes() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertAttribute(METAMODEL, "name", "String");
        manager.insertAttribute(ASSOCIATIONEND, "lower", "String");
        manager.insertAttribute(ASSOCIATIONEND, "upper", "String");
        manager.insertAttribute(ASSOCIATIONEND, "composition", "Boolean");
        manager.insertAttribute(ATTRIBUTE, "visibility", "String");
    }

    /**
     *
     * @return
     * @throws ContractException
     */
    private static boolean insertMetamodelOperations() throws ContractException {
        boolean result = true;

        ModelManager manager = ModelManager.instance();

        // isCreateMethod -> verifica se um m�todo � um Create Method
        // *** verifica apenas o nome pois o retorno n�o � poss�vel de saber com base na abstra��o do metamodelo ***
        result &= manager.insertOperation(BUSINESSMETHOD, "isCreateMethod", "Boolean", "if self.name.size() >= 6 then self.name.substring(1, 6) = 'create' and if self.type.oclIsTypeOf(EJBDataClass) then true else (if self.type.oclIsTypeOf(EJBSet) then (self.type.oclAsType(EJBSet).elementType.oclIsTypeOf(EJBDataClass) or self.type.oclAsType(EJBSet).elementType.name = 'void') else false endif) endif else false endif", new Object[0]);

        // isFinderMethod -> verifica se um m�todo � um Finder Method
        // *** verifica apenas o nome pois o retorno n�o � poss�vel de saber com base na abstra��o do metamodelo ***
        result &= manager.insertOperation(BUSINESSMETHOD, "isFinderMethod", "Boolean", "if self.name.size() >= 4 then self.name.substring(1, 4) = 'find' and if self.type.oclIsTypeOf(EJBDataClass) then true else (if self.type.oclIsTypeOf(EJBSet) then (self.type.oclAsType(EJBSet).elementType.oclIsTypeOf(EJBDataClass) or self.type.oclAsType(EJBSet).elementType.name = 'void') else false endif) endif else false endif", new Object[0]);

        // isRemoveMethod -> verifica se um m�todo � um Remove Method
        result &= manager.insertOperation(BUSINESSMETHOD, "isRemoveMethod", "Boolean", "if self.name.size() >= 6 then self.name.substring(1, 6) = 'remove' and (self.type.name = 'null' or self.type.name = 'void') and self.parameter->notEmpty() else false endif", new Object[0]);

        // isHomeMethod -> verifica se um m�todo � um Home Method
        result &= manager.insertOperation(BUSINESSMETHOD, "isHomeMethod", "Boolean", "if self.name.size() >= 6 then self.name.substring(1, 6) <> 'remove' and self.name.substring(1, 6) <> 'create' else true endif and if self.name.size() >= 4 then self.name.substring(1,4) <> 'find' else true endif and self.parameter.type->forAll(p | if p.oclIsTypeOf(EJBDataType) then true else (if p.oclIsTypeOf(EJBSet) then (p.oclAsType(EJBSet).elementType.oclIsTypeOf(EJBDataType) or p.oclAsType(EJBSet).elementType.name = 'void') else false endif) endif)", new Object[0]);

        if (!result) {
            throw new ContractException("It was not possible to insert all the operations in the EJB model");
        }

        return result;
    }
}
