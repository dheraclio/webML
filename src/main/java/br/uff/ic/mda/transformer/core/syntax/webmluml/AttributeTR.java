/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webmluml;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.WebMLUMLDomain;
import br.uff.ic.mda.transformer.core.syntax.uml.UMLMetaModeler;
import br.uff.ic.mda.transformer.core.syntax.webml.CommonElementsPackage;
import br.uff.ic.mda.transformer.core.syntax.webmluml.WebMLUMLMetaModeler;
import br.uff.ic.mda.transformer.core.util.JDHelper;

/**
 *
 * @author Daniel
 */
public class AttributeTR extends BasicTR implements TransformationRule {

    /**
     *
     * @param domain
     */
    public AttributeTR(WebMLUMLDomain domain) {
        String transf = getAllInstancesQuery(CommonElementsPackage.UMLATTRIBUTE);
        String link = getExactInstancesQuery(WebMLUMLMetaModeler.TR_ATTRIBUTE2ATTRIBUTE);
        setup(transf,link, domain);
    }

    /**
     *
     * @param id
     * @param name
     * @throws ContractException
     */
    @Override
    protected void doTransformation(String id, String name) throws ContractException {
        String umlName = name;
        String umlId = JDHelper.getNewId();
        String umlVisibility = getFirstQueryResult(id, "visibility");
        String webMLClassId = getFirstQueryResult(id, "class");
        String umlClassId = findUMLClassFromWebMLId(webMLClassId);
        String webMLType = getFirstQueryResult(id, "classifier");
        String umlTypeId = findUMLTypeIdFromWebMLTypeId(webMLType);

        getDomain().getTargetDomain().insertAttribute(umlId, umlName,umlVisibility,umlTypeId,umlClassId);
        insertWebMLAttributeToUMLAttribute(id, umlId);
    }

    /**
     *
     * @param id
     * @throws ContractException
     */
    @Override
    protected void doLink(String id) throws ContractException {

        String webmlAttributeId = getFirstQueryResult(id, WebMLUMLMetaModeler.ROLE_WEBML);
        String umlAttributeId = getFirstQueryResult(id, WebMLUMLMetaModeler.ROLE_UML);

        // webmlumlAttribute.type <~> umlAttribute.type
        String webmlAttributeTypeId = getFirstQueryResult(webmlAttributeId, "class");
        String umlAttributeTypeId = findUMLTypeIdFromWebMLTypeId(webmlAttributeTypeId);
        getDomain().getTargetDomain().insertAttributeTypeLink(umlAttributeId, umlAttributeTypeId);

        // webmlumlAttribute.class <~> umlAttribute.class
        String webmlAttributeClassId = getFirstQueryResult(webmlAttributeId, "classifier");
        String umlAttributeClassId = findUMLClassFromWebMLId(webmlAttributeClassId);
        getDomain().getTargetDomain().insertAttributeClassLink(umlAttributeId, umlAttributeClassId);
    }

    private boolean insertWebMLAttributeToUMLAttribute(String webmlId, String umlId) throws ContractException {
        final String attName = "name";
        String name = webmlId + "To" + umlId;
        String id = JDHelper.getNewId();
        boolean result = manager.insertObject(WebMLUMLMetaModeler.TR_ATTRIBUTE2ATTRIBUTE, id);
        result &= manager.insertValue(WebMLUMLMetaModeler.TR_ATTRIBUTE2ATTRIBUTE, attName, id, name);
        result &= manager.insertLink(UMLMetaModeler.ATTRIBUTE, umlId, WebMLUMLMetaModeler.ROLE_UML, WebMLUMLMetaModeler.ROLE_TRANSFORM, id, WebMLUMLMetaModeler.TR_ATTRIBUTE2ATTRIBUTE);
        result &= manager.insertLink(CommonElementsPackage.UMLATTRIBUTE, webmlId, WebMLUMLMetaModeler.ROLE_WEBML, WebMLUMLMetaModeler.ROLE_TRANSFORM, id, WebMLUMLMetaModeler.TR_ATTRIBUTE2ATTRIBUTE);
        return result;
    }

    
}
