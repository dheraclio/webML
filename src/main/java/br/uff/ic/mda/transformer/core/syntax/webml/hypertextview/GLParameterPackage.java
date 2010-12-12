/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.ic.mda.transformer.core.syntax.webml.hypertextview;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.syntax.webml.WebMLBasicPackage;
import br.uff.ic.mda.transformer.core.syntax.webml.commonelements.CommonElementsPackage;
import br.uff.ic.mda.transformer.core.syntax.webml.dataview.DataViewPackage;

/**
 *
 * @author Daniel
 */
public class GLParameterPackage extends WebMLBasicPackage {

    public static final String LOCALPARAMETER = "LocalParameter";
    public static final String GLPARAMETER = "GLParameter";
    public static final String GLPARAMETER_TYPE = "type";
    public static final String GLOBALPARAMETER = "GLOBALParameter";

    public static final String ASSOCIATIONROLE_GLPARAMETER_TYPE = "type";
    public static final String ASSOCIATIONROLE_GLPARAMETER_TYPED = "typed";

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

    public static void insertMetaModelAssociations() throws ContractException{
        manager.insertAssociation(DataViewPackage.ENTITY, ROLE_OWNER, CARD_0_N, CARD_0_1, ROLE_ELEMENT, GLOBALPARAMETER);

        manager.insertAssociation(GLPARAMETER,ASSOCIATIONROLE_GLPARAMETER_TYPE,CARD_1,CARD_1, ASSOCIATIONROLE_GLPARAMETER_TYPED,CommonElementsPackage.WEBMLTYPE);
    }
}
