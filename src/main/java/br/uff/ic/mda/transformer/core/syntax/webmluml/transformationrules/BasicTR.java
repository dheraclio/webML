/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webmluml.transformationrules;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.WebMLUMLDomain;
import br.uff.ic.mda.transformer.core.util.BasicModeler;
import br.uff.ic.mda.transformer.core.util.JDHelper;

/**
 *
 * @author Daniel
 */
public abstract class BasicTR extends BasicModeler{
   
    private WebMLUMLDomain domain;
    private String transformQuery;
    private String linkQuery;

    protected BasicTR(String transformQuery, String linkQuery,WebMLUMLDomain domain) {
        setTransformQuery(transformQuery);
        setLinkQuery(linkQuery);
        setDomain(domain);
    }

    public void link() throws ContractException {

        if (getLinkQuery() == null || getLinkQuery().isEmpty()) {
            throw new ContractException(ContractException.MSG_TRANSFORMATIONEXCEPTION);
        }

        for (String id : JDHelper.filterQueryResult(getLinkQuery())) {
            doLink(id);
        }
    }

    protected abstract void doLink(String id) throws ContractException;

    public void transform() throws ContractException {
        
        if (getTransformQuery() == null || getTransformQuery().isEmpty()) {
            throw new ContractException(ContractException.MSG_TRANSFORMATIONEXCEPTION);
        }

        for (String id : JDHelper.filterQueryResult(getTransformQuery())) {
            String name = JDHelper.getName(id, manager);
            doTransformation(id, name);
        }
    }

    protected abstract void doTransformation(String id, String name) throws ContractException;

    /**
     * @return the query
     */
    public String getTransformQuery() {
        return transformQuery;
    }

    /**
     * @param query the query to set
     */
    public final void setTransformQuery(String query) {
        this.transformQuery = query;
    }

    /**
     * @return the linkQuery
     */
    public String getLinkQuery() {
        return linkQuery;
    }

    /**
     * @param linkQuery the linkQuery to set
     */
    public final void setLinkQuery(String linkQuery) {
        this.linkQuery = linkQuery;
    }

    /**
     * @return the sourceDomain
     */
    protected WebMLUMLDomain getDomain() {
        return domain;
    }

    /**
     * @param domain 
     */
    protected final void setDomain(WebMLUMLDomain domain) {
        this.domain = domain;
    }

}