/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webmluml.transformationrules;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.WebMLUMLDomain;
import br.uff.ic.mda.transformer.core.syntax.uml.UMLMetaModeler;
import br.uff.ic.mda.transformer.core.syntax.webml.dataview.DataViewPackage;
import br.uff.ic.mda.transformer.core.util.JDHelper;

/**
 *
 * @author Daniel
 */
public class EntityTR extends BasicTR implements TransformationRule {

    private static final String TR_ENTITY2CLASS = "Entity2Class";
    private static final String ROLE_CLASS = "class";
    private static final String ROLE_ENTITY = "entity";
    private static final String ROLE_TRANSFORM2CLASS = "transformerToClass";

    public EntityTR(WebMLUMLDomain domain) {
        super(DataViewPackage.ENTITY + ".allInstances()", TR_ENTITY2CLASS + ".allInstances()", domain);
    }

    @Override
    protected void doTransformation(String id, String webmlEntityName) throws ContractException {
        String umlClassName = webmlEntityName;
        String umlClassId = JDHelper.getId(umlClassName);
        getDomain().getTargetDomain().insertClass(umlClassId, umlClassName);
        insertWebMLEntityToUMLClass(id, umlClassId);
    }

    @Override
    protected void doLink(String id) throws ContractException {
    }

    @Override
    public void insertMetaModelAssociations() throws ContractException {
        manager.insertAssociation(UMLMetaModeler.CLASS, "class", CARD_1, CARD_1, ROLE_TRANSFORM2CLASS, TR_ENTITY2CLASS);
        manager.insertAssociation(DataViewPackage.ENTITY, ROLE_ENTITY, CARD_1, CARD_1, ROLE_TRANSFORM2CLASS, TR_ENTITY2CLASS);
    }

    @Override
    public void insertMetaModelClasses() throws ContractException {
        manager.insertClass(TR_ENTITY2CLASS);
        manager.insertAttribute(TR_ENTITY2CLASS, "name", "String");
    }

    private boolean insertWebMLEntityToUMLClass(String webmlEntityId, String umlClassId) throws ContractException {
        String name = webmlEntityId + "To" + umlClassId;
        String id = JDHelper.getId(name);
        boolean result = manager.insertObject(TR_ENTITY2CLASS, id);
        result &= manager.insertValue(TR_ENTITY2CLASS, "name", id, name);
        result &= manager.insertLink(DataViewPackage.ENTITY, webmlEntityId, ROLE_ENTITY, ROLE_TRANSFORM2CLASS, id, TR_ENTITY2CLASS);
        result &= manager.insertLink(UMLMetaModeler.CLASS, umlClassId, ROLE_CLASS, ROLE_TRANSFORM2CLASS, id, TR_ENTITY2CLASS);
        return result;
    }
}
