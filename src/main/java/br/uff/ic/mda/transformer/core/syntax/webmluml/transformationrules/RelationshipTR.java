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

    private static final String RELATIONSHIPTOASSOCIATION = "RelationshipToAssociation";

    private static final String ROLE_2ASSOCIATION = "transformationToAssociation";
    private static final String ROLE_ASSOCIATION = "association";
    private static final String ROLE_RELATIONSHIP = "relationship";

    public RelationshipTR(WebMLUMLDomain domain) {
        super(DataViewPackage.RELATIONSHIP +".allInstances()",DataViewPackage.RELATIONSHIP +".allInstances()",domain);
    }

    @Override
    protected void doTransformation(String id, String name) throws ContractException {
        
    }

    @Override
    protected void doLink(String id) throws ContractException {
        
    }

    @Override
    public void insertMetaModelClasses() throws ContractException {
        manager.insertClass(RELATIONSHIPTOASSOCIATION);
        manager.insertAttribute(RELATIONSHIPTOASSOCIATION, "name", "String");
    }

    @Override
    public void insertMetaModelAssociations() throws ContractException {
        manager.insertAssociation(DataViewPackage.ENTITY, ROLE_RELATIONSHIP, CARD_1, CARD_1, ROLE_2ASSOCIATION, RELATIONSHIPTOASSOCIATION);
        manager.insertAssociation(RELATIONSHIPTOASSOCIATION, ROLE_2ASSOCIATION, CARD_1, CARD_1,ROLE_ASSOCIATION, UMLMetaModeler.CLASS);
    }

    
}
