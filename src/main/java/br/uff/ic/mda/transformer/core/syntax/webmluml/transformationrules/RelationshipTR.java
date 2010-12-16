/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webmluml.transformationrules;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.WebMLUMLDomain;
import br.uff.ic.mda.transformer.core.syntax.uml.UMLMetaModeler;
import br.uff.ic.mda.transformer.core.syntax.webml.dataview.DataViewPackage;

/**
 *
 * @author Daniel
 */
public class RelationshipTR extends BasicTR implements TransformationRule {

    /**
     *
     * @param domain
     */
    public RelationshipTR(WebMLUMLDomain domain) {
        String transf = getExactInstancesQuery(DataViewPackage.RELATIONSHIP);
        String link = getExactInstancesQuery(DataViewPackage.RELATIONSHIP);
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
    }

    /**
     *
     * @param id
     * @throws ContractException
     */
    @Override
    protected void doLink(String id) throws ContractException {
    }
}
