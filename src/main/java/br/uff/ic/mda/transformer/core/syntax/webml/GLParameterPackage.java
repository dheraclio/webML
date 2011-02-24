/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.ic.mda.transformer.core.syntax.webml;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.syntax.webml.WebMLBasicPackage;
import br.uff.ic.mda.transformer.core.syntax.webml.CommonElementsPackage;
import br.uff.ic.mda.transformer.core.syntax.webml.DataViewPackage;

/**
 *
 * @author Daniel
 */
public class GLParameterPackage extends WebMLBasicPackage {

    /**
     *
     */
    public static final String LOCALPARAMETER = PREFIX +"LocalParameter";
    /**
     *
     */
    public static final String GLPARAMETER = PREFIX +"GLParameter";
    /**
     *
     */
    public static final String GLPARAMETER_TYPE = "type";
    /**
     *
     */
    public static final String GLOBALPARAMETER = PREFIX +"GLOBALParameter";

    /**
     *
     */
    public static final String ROLE_TYPE = "type";
    /**
     *
     */
    public static final String ROLE_TYPED = "typed";

    /**
     *
     * @throws ContractException
     */
    public static void insertMetaModel() throws ContractException{
        manager.insertClass(LOCALPARAMETER);
        manager.insertClass(GLPARAMETER);
        manager.insertClass(GLOBALPARAMETER);

        manager.insertGeneralization(LOCALPARAMETER,GLPARAMETER);
        manager.insertGeneralization(LOCALPARAMETER,SiteViewPackage.SITEVIEWELEMENT);
        manager.insertGeneralization(GLOBALPARAMETER,GLPARAMETER);

        insertMetaModelAttributes();        
    }

    private static void insertMetaModelAttributes() throws ContractException{
        //Trocado por associacao 1-1
        //manager.insertAttribute(GLPARAMETER, GLPARAMETER_TYPE, WEBMLTYPE);
    }

    /**
     *
     * @throws ContractException
     */
    public static void insertMetaModelAssociations() throws ContractException{
        manager.insertAssociation(DataViewPackage.ENTITY, ROLE_OWNER, CARD_0_N, CARD_0_1, ROLE_ELEMENT, GLOBALPARAMETER);

        manager.insertAssociation(GLPARAMETER,ROLE_TYPE,CARD_1,CARD_1, ROLE_TYPED,CommonElementsPackage.WEBMLTYPE);
    }
}
