/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.ic.mda.transformer.core.syntax.webml.hypertextview;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.core.syntax.webml.WebMLBasicPackage;

/**
 *
 * @author Daniel
 */
public abstract class HypertextViewPackage extends WebMLBasicPackage {

    public static void insertMetaModel() throws ContractException {
        CorePackage.insertMetaModel();
        SiteViewPackage.insertMetaModel();
        GLParameterPackage.insertMetaModel();
        ServiceViewPackage.insertMetaModel();
        AreaViewPackage.insertMetaModel();
        TransactionViewPackage.insertMetaModel();
        PageViewPackage.insertMetaModel();
        LinkViewPackage.insertMetaModel();
        ParameterViewPackage.insertMetaModel();
        UnitViewPackage.insertMetaModel();

        insertMetaModelAssociations();
    }

    private static void insertMetaModelAssociations() throws ContractException {
        CorePackage.insertMetaModelAssociations();
        SiteViewPackage.insertMetaModelAssociations();
        GLParameterPackage.insertMetaModelAssociations();
        ServiceViewPackage.insertMetaModelAssociations();
        AreaViewPackage.insertMetaModelAssociations();
        TransactionViewPackage.insertMetaModelAssociations();
        PageViewPackage.insertMetaModelAssociations();
        LinkViewPackage.insertMetaModelAssociations();
        ParameterViewPackage.insertMetaModelAssociations();
        UnitViewPackage.insertMetaModelAssociations();
    }
}
