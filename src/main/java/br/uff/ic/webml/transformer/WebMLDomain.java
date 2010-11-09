
/*
*
 */
package br.uff.ic.webml.transformer;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.Domain;
import br.uff.ic.mda.tclib.IValidator;
import br.uff.ic.mda.tclib.IXMIParser;

import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class WebMLDomain extends Domain {

    /**
     * Constructs ...
     *
     */
    public WebMLDomain() {
        super(null, null, new ArrayList<IValidator>());
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    public void createMetamodel() throws ContractException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    public void createSpecificationOfCurrentDiagram() throws ContractException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method description
     *
     *
     * @param ixmip
     *
     * @throws ContractException
     */
    @Override
    protected void loadDataFromParser(IXMIParser ixmip) throws ContractException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    public void prettyPrint() throws ContractException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
