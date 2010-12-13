/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webml.hypertextview;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.syntax.webml.WebMLBasicPackage;

/**
 *
 * @author Daniel
 */
public class CorePackage extends WebMLBasicPackage {

    public static final String HYPERTEXTMODEL = PREFIX +"HypertextModel";
    public static final String HYPERTEXTMODELELEMENT = PREFIX +"HypertextModelElement";

    public static void insertMetaModel() throws ContractException {
        manager.insertClass(HYPERTEXTMODEL);
        manager.insertClass(HYPERTEXTMODELELEMENT);
    }

    public static void insertMetaModelAssociations() throws ContractException {
        manager.insertAssociation(HYPERTEXTMODEL, ROLE_OWNER, CARD_0_N, CARD_N, ROLE_ELEMENT, HYPERTEXTMODELELEMENT);
    }
}
