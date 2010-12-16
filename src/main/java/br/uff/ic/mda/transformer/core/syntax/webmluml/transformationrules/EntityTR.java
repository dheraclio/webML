/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webmluml.transformationrules;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.WebMLUMLDomain;
import br.uff.ic.mda.transformer.core.syntax.uml.UMLMetaModeler;
import br.uff.ic.mda.transformer.core.syntax.webml.dataview.DataViewPackage;
import br.uff.ic.mda.transformer.core.syntax.webmluml.WebMLUMLMetaModeler;
import br.uff.ic.mda.transformer.core.util.JDHelper;

/**
 *
 * @author Daniel
 */
public class EntityTR extends BasicTR implements TransformationRule {

    /**
     *
     * @param domain
     */
    public EntityTR(WebMLUMLDomain domain) {
        String transf = getExactInstancesQuery(DataViewPackage.ENTITY);
        String link = getExactInstancesQuery(WebMLUMLMetaModeler.TR_ENTITY2CLASS);
        setup(transf,link, domain);
    }

    /**
     *
     * @param id
     * @param webmlEntityName
     * @throws ContractException
     */
    @Override
    protected void doTransformation(String id, String webmlEntityName) throws ContractException {
        String umlClassName = webmlEntityName;
        String umlClassId = JDHelper.getNewId();
        getDomain().getTargetDomain().insertClass(umlClassId, umlClassName);
        insertWebMLEntityToUMLClass(id, umlClassId);
    }

    /**
     *
     * @param id
     * @throws ContractException
     */
    @Override
    protected void doLink(String id) throws ContractException {
    }

    private boolean insertWebMLEntityToUMLClass(String webmlEntityId, String umlClassId) throws ContractException {
        final String attName = "name";
        String name = webmlEntityId + "To" + umlClassId;
        String id = JDHelper.getNewId();
        boolean result = manager.insertObject(WebMLUMLMetaModeler.TR_ENTITY2CLASS, id);
        result &= manager.insertValue(WebMLUMLMetaModeler.TR_ENTITY2CLASS, attName, id, name);
        result &= manager.insertLink(DataViewPackage.ENTITY, webmlEntityId, WebMLUMLMetaModeler.ROLE_WEBML, WebMLUMLMetaModeler.ROLE_TRANSFORM, id, WebMLUMLMetaModeler.TR_ENTITY2CLASS);
        result &= manager.insertLink(UMLMetaModeler.CLASS, umlClassId, WebMLUMLMetaModeler.ROLE_UML, WebMLUMLMetaModeler.ROLE_TRANSFORM, id, WebMLUMLMetaModeler.TR_ENTITY2CLASS);
        return result;
    }
}
