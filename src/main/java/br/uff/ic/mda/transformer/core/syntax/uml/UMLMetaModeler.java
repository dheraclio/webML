/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.ic.mda.transformer.core.syntax.uml;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.ModelManager;

/**
 *
 * @author Daniel
 */
public class UMLMetaModeler {
    public static final String CLASSIFIER = "Classifier";
    /**
     *
     */
    public static final String DATATYPE = "DataType";
    /**
     *
     */
    public static final String CLASS = "Class";
    /**
     *
     */
    public static final String ATTRIBUTE = "Attribute";
    /**
     *
     */
    public static final String ASSOCIATION = "Association";
    /**
     *
     */
    public static final String ASSOCIATIONEND = "AssociationEnd";
    /**
     *
     */
    public static final String ASSOCIATIONCLASS = "AssociationClass";
    public static final String FEATURE = "Feature";
    public static final String INTERFACE = "Interface";
    public static final String MODELELEMENT = "ModelElement";
    public static final String OPERATION = "Operation";
    public static final String PARAMETER = "Parameter";
    public static final String TYPED = "Typed";
    public static final String UMLSET = "UMLSet";


    //Types
    public static final String UMLBOOLEAN = "UMLBoolean";
    public static final String UMLDATE = "UMLDate";
    public static final String UMLDOUBLE = "UMLDouble";
    public static final String UMLINTEGER = "UMLInteger";
    public static final String UMLREAL = "UMLReal";
    public static final String UMLSTRING = "UMLString";
    public static final String UMLVOID = "UMLVoid";

    public static void createMetamodel() throws ContractException {
        insertMetamodelClasses();
        insertMetamodelAttributes();
        insertMetamodelAssociations();
        insertMetamodelOperations();
    }

    /**
     *
     * @throws ContractException
     */
    private static void insertMetamodelClasses() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertClass(MODELELEMENT);
        manager.insertClass(CLASSIFIER);
        manager.insertClass(TYPED);
        manager.insertClass(DATATYPE);
        manager.insertClass(UMLSET);
        manager.insertClass(CLASS);
        manager.insertClass(INTERFACE);
        manager.insertClass(ASSOCIATIONCLASS);
        manager.insertClass(FEATURE);

        manager.insertClass(ASSOCIATIONEND);
        manager.insertClass(ASSOCIATION);
        manager.insertClass(ATTRIBUTE);
        manager.insertClass(OPERATION);
        manager.insertClass(PARAMETER);
        manager.insertGeneralization(ASSOCIATION, MODELELEMENT);
        manager.insertGeneralization( CLASSIFIER, MODELELEMENT);
        manager.insertGeneralization( TYPED, MODELELEMENT);
        manager.insertGeneralization(DATATYPE, CLASSIFIER);
        manager.insertGeneralization( UMLSET, DATATYPE);
        manager.insertGeneralization(CLASS, CLASSIFIER);
        manager.insertGeneralization( INTERFACE, CLASSIFIER);
        manager.insertGeneralization(ASSOCIATIONCLASS, CLASS);
        manager.insertGeneralization(ASSOCIATIONCLASS, ASSOCIATION);
        manager.insertGeneralization( FEATURE, TYPED);
        manager.insertGeneralization(ASSOCIATIONEND, FEATURE);
        manager.insertGeneralization(ATTRIBUTE, FEATURE);
        manager.insertGeneralization( OPERATION, FEATURE);
        manager.insertGeneralization( PARAMETER, TYPED);
    }

    /**
     *
     * @throws ContractException
     */
    private static void insertMetamodelAttributes() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertAttribute(MODELELEMENT,"name", "String");
        manager.insertAttribute(FEATURE,"visibility", "String"); // ### Tipo VisibilityKind
        manager.insertAttribute(ASSOCIATIONEND, "lower", "String"); // ### Tipo Lowerbound
        manager.insertAttribute(ASSOCIATIONEND, "upper", "String"); // ### Tipo Upperbound
        manager.insertAttribute(ASSOCIATIONEND, "composition", "Boolean");
    }

    // Corrigir os nomes das associacoes para = ao Metamodelo
    /**
     *
     * @throws ContractException
     */
    private static void insertMetamodelAssociations() throws ContractException {
        ModelManager manager = ModelManager.instance();

        manager.insertAssociation(ASSOCIATIONEND, "otherEnd", "0..1", "0..*", "others", ASSOCIATIONEND);
        manager.insertAssociation(ASSOCIATIONEND, "associationEnds", "1..*", "1", "association", ASSOCIATION);
        manager.insertAssociation(OPERATION,"operation", "1", "*", "parameter", PARAMETER);
        manager.insertAssociation(CLASS, "class", "0..1", "*", "feature", FEATURE);
        manager.insertAssociation(CLASS, "classes", "*", "*", "implementedInterfaces", INTERFACE);
        manager.insertAssociation(CLASS, "inheritsFrom", "*", "*", "inheritedBy", CLASS);
        manager.insertAssociation(CLASSIFIER,"classifier", "0..1", "*", "types", TYPED);
        manager.insertAssociation(UMLSET,"setA", "0..*", "1", "elementType", CLASSIFIER);
    }

    /**
     *
     * @return
     * @throws ContractException
     */
    private static boolean insertMetamodelOperations() throws ContractException {
        ModelManager manager = ModelManager.instance();

        boolean result = true;

        result &= manager.insertOperation(CLASS, "getAllContained", "Set(Class)",
                "Class.allInstances()->select(c | c.subindo(c.emptySet())->includes(self))", new Object[0]);

        result &= manager.insertOperation(CLASS, "subindo", "Set(Class)",
                "if self.oclIsTypeOf(Class) "
                + "then self.subindoClass(lista) "
                + "else "
                + "self.oclAsType(AssociationClass).subindoAssociationClass(lista) "
                + "endif", new Object[]{new String[]{"lista", "Set(Class)"}});

        result &= manager.insertOperation(CLASS, "subindoClass", "Set(Class)",
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

        result &= manager.insertOperation(ASSOCIATIONCLASS, "subindoAssociationClass", "Set(Class)",
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

        result &= manager.insertOperation(CLASS, "emptySet", "Set(Class)",
                "Class.allInstances()->select(c|false) ", new Object[0]);


        result &= manager.insertOperation(CLASS, "getOuterMostContainer", CLASS,
                "if self.oclIsTypeOf(Class) "
                + "then self.getOuterMostContainerFromClass() "
                + "else "
                + "self.oclAsType(AssociationClass).getOuterMostContainerFromAssociationClass() "
                + "endif", new Object[0]);

        result &= manager.insertOperation(CLASS, "getOuterMostContainerFromClass", CLASS,
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

        result &= manager.insertOperation(ASSOCIATION, "getOuterMostContainerFromAssociation", CLASS,
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

        result &= manager.insertOperation(ASSOCIATIONCLASS, "getOuterMostContainerFromAssociationClass", CLASS,
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

        result &= manager.insertOperation(CLASS, "isOuterMostContainer", "Boolean",
                "self = self.getOuterMostContainer()", new Object[0]);

        result &= manager.insertOperation(CLASS, "getAllOuterMostContainer", "Set(Class)",
                "Class.allInstances()->select(c : Class | c.isOuterMostContainer())->asSet()", new Object[0]);

        result &= manager.insertOperation(CLASS, "superPlus", "Set(Class)",
                "self.superPlusOnSet(self.emptySet())", new Object[0]);

        result &= manager.insertOperation(CLASS, "superPlusOnSet", "Set(Class)",
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
}
