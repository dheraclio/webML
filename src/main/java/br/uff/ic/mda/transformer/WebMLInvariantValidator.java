package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.ContractException;

/**
 *
 * @author daniel
 */
public class WebMLInvariantValidator extends InvariantValidator {

    /**
     * Constructs ...
     *
     *
     * @throws ContractException
     */
    public WebMLInvariantValidator() throws ContractException {
        super("WebML", "WebMLInvariantValidator"); //Domain, Logger
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    protected void createInvariants() throws ContractException {
        createInvariants("WebMLInvariantValidator");
    }

    @Override
    protected void createOperations() throws ContractException {        
    }
}