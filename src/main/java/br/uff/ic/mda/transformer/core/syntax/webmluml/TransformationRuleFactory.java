package br.uff.ic.mda.transformer.core.syntax.webmluml;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.UMLDomain;
import br.uff.ic.mda.transformer.WebMLDomain;
import br.uff.ic.mda.transformer.WebMLUMLDomain;
import br.uff.ic.mda.transformer.core.syntax.webmluml.WebMLUMLMetaModeler;
import java.util.LinkedList;

/**
 *
 * @author Daniel
 */
public class TransformationRuleFactory {

    /**
     *
     */
    public static final String RELATIONSHIPTR = "RelationshipTR";
    /**
     *
     */
    public static final String ENTITYTR = "EntityTR";
    /**
     *
     */
    public static final String ATTRIBUTETR = "AttributeTR";
    /**
     *
     */
    public static final String TYPETR = "TypeTR";
    private static LinkedList<String> ruleNames = new LinkedList<String>();
    private static LinkedList<TransformationRule> transformationRules = new LinkedList<TransformationRule>();

    /**
     *
     * @param domain
     * @return
     * @throws ContractException
     */
    public static LinkedList<TransformationRule> getRules(WebMLUMLDomain domain) throws ContractException {
        if (transformationRules.isEmpty()) {
            for (String rule : getRuleNames()) {
                TransformationRule tr = TransformationRuleFactory.get(rule, domain);
                if (tr != null) {
                    transformationRules.add(tr);
                }
            }
        }
        return transformationRules;
    }

    private static TransformationRule get(String rule,WebMLUMLDomain domain) throws ContractException {
        if (RELATIONSHIPTR.equals(rule)) {
            return new RelationshipTR(domain);
        } if (ENTITYTR.equals(rule)) {
            return new EntityTR(domain);
        } if (TYPETR.equals(rule)) {
            return new TypeTR(domain);
        } if (ATTRIBUTETR.equals(rule)) {
            return new AttributeTR(domain);
        } else {
            throw new ContractException(ContractException.MSG_TRANSFORMATIONEXCEPTION);
        }
    }

    private static LinkedList<String> getRuleNames() {
        //Order of execution
        if (ruleNames.isEmpty()) {
            ruleNames.add(TYPETR);            
            ruleNames.add(ENTITYTR);
            ruleNames.add(ATTRIBUTETR);
            ruleNames.add(RELATIONSHIPTR);
        }
        return ruleNames;
    }
}
