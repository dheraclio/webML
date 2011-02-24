/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webmluml;

import br.uff.ic.mda.tclib.ContractException;

/**
 *
 * @author Daniel
 */
public interface TransformationRule {

    /**
     *
     * @throws ContractException
     */
    public void transform() throws ContractException;

    /**
     *
     * @throws ContractException
     */
    public void link() throws ContractException;
}
