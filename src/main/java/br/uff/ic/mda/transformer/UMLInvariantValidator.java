package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.ContractException;

/**
 *
 * @author daniel
 */
public class UMLInvariantValidator extends InvariantValidator {

    /**
     * Constructs ...
     *
     *
     * @throws ContractException
     */
    public UMLInvariantValidator() throws ContractException {
        super( "UML", "UMLInvariants" );//Domain, Logger
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    protected void createInvariants() throws ContractException {        
        createInvariants( "UMLInvariants" );
    }

    @Override
    protected void createOperations() throws ContractException {
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
