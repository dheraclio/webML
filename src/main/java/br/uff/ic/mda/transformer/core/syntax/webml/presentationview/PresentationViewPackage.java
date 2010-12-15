/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.ic.mda.transformer.core.syntax.webml.presentationview;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.syntax.webml.WebMLBasicPackage;

/**
 *
 * @author Daniel
 */
public abstract class PresentationViewPackage extends WebMLBasicPackage {

    /**
     *
     * @throws ContractException
     */
    public static void insertMetaModel() throws ContractException {
        //Not supplied by profile creators
    }

}
