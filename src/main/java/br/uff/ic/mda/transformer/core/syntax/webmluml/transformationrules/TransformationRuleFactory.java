package br.uff.ic.mda.transformer.core.syntax.webmluml.transformationrules;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.UMLDomain;
import br.uff.ic.mda.transformer.WebMLDomain;
import br.uff.ic.mda.transformer.WebMLUMLDomain;
import br.uff.ic.mda.transformer.core.syntax.webmluml.WebMLUMLMetaModeler;

/**
 *
 * @author Daniel
 */
public class TransformationRuleFactory {

    /**
     *
     * @param rule
     * @param domain
     * @return
     * @throws ContractException
     *
     */
    public static TransformationRule get(String rule,WebMLUMLDomain domain) throws ContractException {
        if (WebMLUMLMetaModeler.RELATIONSHIPTR.equals(rule)) {
            return new RelationshipTR(domain);
        } if (WebMLUMLMetaModeler.ENTITYTR.equals(rule)) {
            return new EntityTR(domain);
        } else {
            throw new ContractException(ContractException.MSG_TRANSFORMATIONEXCEPTION);
        }
    }

}
