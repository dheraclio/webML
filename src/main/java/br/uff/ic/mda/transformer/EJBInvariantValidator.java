package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.ContractException;

/**
 *
 * @author daniel
 */
public class EJBInvariantValidator extends InvariantValidator {

    /**
     * Constructs ...
     *
     *
     * @throws ContractException
     */
    public EJBInvariantValidator() throws ContractException {
        super("EJB", "EJBInvariantValidator"); //Domain, Logger
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    protected void createInvariants() throws ContractException {
        createInvariants("EJBInvariantValidator");
    }

    @Override
    protected void createOperations() throws ContractException {
    }
}
