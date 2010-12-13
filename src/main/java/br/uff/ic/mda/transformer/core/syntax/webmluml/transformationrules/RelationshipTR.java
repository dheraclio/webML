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

    public RelationshipTR(WebMLUMLDomain domain) {
        super(DataViewPackage.RELATIONSHIP + ".allInstances()", DataViewPackage.RELATIONSHIP + ".allInstances()", domain);
    }

    @Override
    protected void doTransformation(String id, String name) throws ContractException {
    }

    @Override
    protected void doLink(String id) throws ContractException {
    }
}
