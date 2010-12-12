package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.ContractException;

/**
 *
 * @author robertowm
 */
public class UMLEJBInvariantValidator extends InvariantValidator {

    /**
     *
     * @throws ContractException
     */
    public UMLEJBInvariantValidator() throws ContractException {
        super("UMLEJB", "UMLEJBInvariantValidator");
    }

    @Override
    protected void createInvariants() throws ContractException {
        createInvariants("UMLEJBInvariantValidator");
    }

    @Override
    protected void createOperations() throws ContractException {        
    }
}
