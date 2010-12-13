/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webmluml.transformationrules;

import br.uff.ic.mda.tclib.ContractException;

/**
 *
 * @author Daniel
 */
public interface TransformationRule {

    public void transform() throws ContractException;

    public void link() throws ContractException;
}
