package br.uff.ic.webml.transformer;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.webml.util.InvariantValidator;

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
        super("WebML", "WebMLInvariants");
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    protected void createInvariants() throws ContractException {
        String root = this.getClass().getPackage().getName().replace(".", "/");
        String file = "WebMLInvariants.properties";

        createInvariants(root + file);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
