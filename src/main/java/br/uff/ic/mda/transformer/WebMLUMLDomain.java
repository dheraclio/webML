package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.IValidator;
import br.uff.ic.mda.tclib.JoinedDomain;
import br.uff.ic.mda.transformer.core.syntax.webmluml.WebMLUMLMetaModeler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Daniel
 */
public class WebMLUMLDomain extends JoinedDomain<WebMLDomain, UMLDomain> {


    /**
     * Constructs ...
     *
     *
     * @param sourceDomain
     * @param targetDomain
     *
     * @throws ContractException
     */
    public WebMLUMLDomain(WebMLDomain sourceDomain, UMLDomain targetDomain) throws ContractException {
        super(sourceDomain, targetDomain,
                new ArrayList<IValidator>(Arrays.asList(new WebMLUMLInvariantValidator())));
    }

    /**
     * Constructs ...
     *
     *
     * @param sourceDomain
     * @param targetDomain
     * @param validators
     *
     * @throws ContractException
     */
    public WebMLUMLDomain(WebMLDomain sourceDomain, UMLDomain targetDomain,
            Collection<IValidator> validators)
            throws ContractException {
        super(sourceDomain, targetDomain, validators);
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    protected void createLocalMetamodel() throws ContractException {
        WebMLUMLMetaModeler.insertMetaModel();
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    public void createSpecificationOfCurrentDiagram() throws ContractException {
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    public void transformDomain() throws ContractException {
        WebMLUMLMetaModeler.applyRules(this);
    }

    /**
     *
     * @return
     */
    public WebMLDomain getSourceDomain() {
        return sourceDomain;
    }

    /**
     *
     * @return
     */
    public UMLDomain getTargetDomain() {
        return targetDomain;
    }
}
