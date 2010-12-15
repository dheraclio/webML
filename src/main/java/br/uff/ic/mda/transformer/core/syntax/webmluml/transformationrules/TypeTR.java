/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webmluml.transformationrules;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.WebMLUMLDomain;
import br.uff.ic.mda.transformer.core.syntax.uml.UMLMetaModeler;
import br.uff.ic.mda.transformer.core.syntax.webml.commonelements.CommonElementsPackage;
import br.uff.ic.mda.transformer.core.syntax.webml.dataview.DataViewPackage;
import br.uff.ic.mda.transformer.core.syntax.webmluml.WebMLUMLMetaModeler;
import br.uff.ic.mda.transformer.core.util.JDHelper;

/**
 *
 * @author Daniel
 */
public class TypeTR extends BasicTR implements TransformationRule {

    /**
     *
     * @param domain
     */
    public TypeTR(WebMLUMLDomain domain) {
        super(CommonElementsPackage.UMLDATATYPE + ".allInstances()", WebMLUMLMetaModeler.TR_WEBMLDATATYPE2UMLDATATYPE + ".allInstances()", domain);
    }

    /**
     *
     * @param id
     * @param name
     * @throws ContractException
     */
    @Override
    protected void doTransformation(String id, String name) throws ContractException {
        String umlId = id;
        String umlName = name;
        getDomain().getTargetDomain().insertType(umlId, umlName);
        insertLink(id, umlId);
    }

    /**
     *
     * @param id
     * @throws ContractException
     */
    @Override
    protected void doLink(String id) throws ContractException {
    }

    private boolean insertLink(String webmlId, String umlId) throws ContractException {
        final String attName = "name";
        String name = webmlId + "To" + umlId;
        String id = JDHelper.getNewId();
        boolean result = manager.insertObject(WebMLUMLMetaModeler.TR_WEBMLDATATYPE2UMLDATATYPE, id);
        result &= manager.insertValue(WebMLUMLMetaModeler.TR_WEBMLDATATYPE2UMLDATATYPE, attName, id, name);
        result &= manager.insertLink(UMLMetaModeler.DATATYPE, umlId, WebMLUMLMetaModeler.ROLE_UML, WebMLUMLMetaModeler.ROLE_TRANSFORM, id, WebMLUMLMetaModeler.TR_WEBMLDATATYPE2UMLDATATYPE);
        result &= manager.insertLink(CommonElementsPackage.UMLDATATYPE, webmlId, WebMLUMLMetaModeler.ROLE_WEBML, WebMLUMLMetaModeler.ROLE_TRANSFORM, id, WebMLUMLMetaModeler.TR_WEBMLDATATYPE2UMLDATATYPE);
        return result;
    }
}
