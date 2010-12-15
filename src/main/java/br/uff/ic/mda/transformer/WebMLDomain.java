
/*
 *
 */
package br.uff.ic.mda.transformer;

import br.uff.ic.mda.transformer.core.syntax.webml.dataview.DataViewPackage;
import br.uff.ic.mda.transformer.core.syntax.webml.presentationview.PresentationViewPackage;
import br.uff.ic.mda.transformer.core.syntax.webml.hypertextview.HypertextViewPackage;
import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.Domain;
import br.uff.ic.mda.tclib.IValidator;
import br.uff.ic.mda.tclib.IXMIParser;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.syntax.webml.WebMLXMIParser;
import br.uff.ic.mda.transformer.core.syntax.webml.commonelements.CommonElementsPackage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author daniel
 */
public class WebMLDomain extends Domain {

    /**
     * Constructs ...
     *
     * @throws ContractException
     */
    public WebMLDomain() throws ContractException {
        super(null, null, new ArrayList<IValidator>(Arrays.asList(new WebMLInvariantValidator())));
    }

    /**
     *
     * @param parser
     * @param xmiSourceModel
     * @throws ContractException
     */
    public WebMLDomain(IXMIParser parser, String xmiSourceModel) throws ContractException {
        super(parser, xmiSourceModel, new ArrayList<IValidator>(Arrays.asList(new WebMLInvariantValidator())));
    }

    /**
     *
     * @param parser
     * @param xmiSourceModel
     * @param validators
     */
    public WebMLDomain(IXMIParser parser, String xmiSourceModel, Collection<IValidator> validators) {
        super(parser, xmiSourceModel, validators);
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    public void createMetamodel() throws ContractException {
        //Ordered throught dependencies
        CommonElementsPackage.insertMetaModel();
        DataViewPackage.insertMetaModel();
        PresentationViewPackage.insertMetaModel();
        HypertextViewPackage.insertMetaModel();
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    public void createSpecificationOfCurrentDiagram() throws ContractException {
        CommonElementsPackage.createSpecification();
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
    public void loadDataFromParser(IXMIParser ixmip) throws ContractException {
        WebMLXMIParser.parse(parser);
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    protected void printDomain() throws ContractException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
