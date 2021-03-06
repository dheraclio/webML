/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.ic.mda.transformer.core.syntax.webml;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.core.syntax.webml.WebMLBasicPackage;

/**
 *
 * @author Daniel
 */
public class TransactionViewPackage extends WebMLBasicPackage {

    /**
     *
     */
    public static final String TRANSACTION = PREFIX + "Transaction";
    /**
     *
     */
    public static final String TRANSACTIONELEMENT = PREFIX + "TransactionElement";

    /**
     *
     * @throws ContractException
     */
    public static void insertMetaModel() throws ContractException{
        manager.insertClass(TRANSACTION);
        manager.insertClass(TRANSACTIONELEMENT);

        manager.insertGeneralization(TRANSACTION,AreaViewPackage.AREAELEMENT);
        manager.insertGeneralization(TRANSACTION,SiteViewPackage.SITEVIEWELEMENT);
        manager.insertGeneralization(TRANSACTION,ServiceViewPackage.PORTELEMENT);
    }

    /**
     *
     * @throws ContractException
     */
    public static void insertMetaModelAssociations() throws ContractException{
        manager.insertAssociation(TRANSACTION, ROLE_OWNER, CARD_0_N, CARD_1, ROLE_ELEMENT, TRANSACTIONELEMENT);
    }

}
