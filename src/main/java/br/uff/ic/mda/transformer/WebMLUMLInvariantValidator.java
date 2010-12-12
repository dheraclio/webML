package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.ContractException;

/**
 *
 * @author daniel
 */
public class WebMLUMLInvariantValidator extends InvariantValidator {

    /**
     * Constructs ...
     *
     *
     * @throws ContractException
     */
    public WebMLUMLInvariantValidator() throws ContractException {
        super( "WebMLUML", "WebMLUMLInvariantValidator" );//Domain, Logger
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    protected void createInvariants() throws ContractException {        
        createInvariants( "WebMLUMLInvariantValidator" );
    }

    @Override
    protected void createOperations() throws ContractException {
    }
}