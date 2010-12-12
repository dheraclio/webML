/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webmluml;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.UMLDomain;
import br.uff.ic.mda.transformer.WebMLDomain;
import br.uff.ic.mda.transformer.WebMLUMLDomain;
import br.uff.ic.mda.transformer.core.syntax.webmluml.transformationrules.TransformationRule;
import br.uff.ic.mda.transformer.core.syntax.webmluml.transformationrules.TransformationRuleFactory;
import br.uff.ic.mda.transformer.core.util.BasicModeler;
import java.util.LinkedList;

/**
 * Modifications:
 *  Attribute 'derivation Query' renamed to 'derivationQuery'
 *  Cadinalities set to 1 when not specified
 *
 * @author Daniel
 *
 */
public abstract class WebMLUMLMetaModeler extends BasicModeler {

    public static final String RELATIONSHIPTR = "RelationshipTR";
    public static final String ENTITYTR = "EntityTR";
    private static LinkedList<String> ruleNames = new LinkedList<String>();
    private static LinkedList<TransformationRule> transformationRules = new LinkedList<TransformationRule>();

    public static void applyRules(WebMLUMLDomain aThis) throws ContractException {

        for (TransformationRule tr : getRules(aThis)) {
            tr.transform();
        }

        for (TransformationRule tr : getRules(aThis)) {
            tr.link();
        }
    }

    public static void insertMetaModel(WebMLUMLDomain aThis) throws ContractException {
        insertMetaModelClasses(aThis);
        insertMetaModelAssociations(aThis);
    }

    private static void insertMetaModelClasses(WebMLUMLDomain aThis) throws ContractException {
        for (TransformationRule tr : getRules(aThis)) {
            tr.insertMetaModelClasses();
        }
    }

    private static void insertMetaModelAssociations(WebMLUMLDomain aThis) throws ContractException {
        for (TransformationRule tr : getRules(aThis)) {
            tr.insertMetaModelAssociations();
        }
    }

    private static LinkedList<String> getRuleNames() {
        if (ruleNames.isEmpty()) {
            //ruleNames.add(ENTITYTR);
            ruleNames.add(RELATIONSHIPTR);
        }
        return ruleNames;
    }

    private static LinkedList<TransformationRule> getRules(WebMLUMLDomain aThis) throws ContractException {
        if (transformationRules.isEmpty()) {
            for (String rule : getRuleNames()) {
                TransformationRule tr = TransformationRuleFactory.get(rule, aThis);
                if (tr != null) {
                    transformationRules.add(tr);
                }
            }
        }
        return transformationRules;
    }
}
