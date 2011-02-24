/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer.core.syntax.webmluml;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.transformer.UMLDomain;
import br.uff.ic.mda.transformer.WebMLUMLDomain;
import br.uff.ic.mda.transformer.core.syntax.uml.UMLMetaModeler;
import br.uff.ic.mda.transformer.core.syntax.webml.CommonElementsPackage;
import br.uff.ic.mda.transformer.core.syntax.webml.DataViewPackage;
import br.uff.ic.mda.transformer.core.syntax.webmluml.WebMLUMLMetaModeler;
import br.uff.ic.mda.transformer.core.util.JDHelper;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Daniel
 */
public class TypeTR extends BasicTR implements TransformationRule {

    Map<String, String> hashTypes = new HashMap<String, String>();
    Map<String, String> hashNames = new HashMap<String, String>();

    /**
     *
     * @param domain
     */
    public TypeTR(WebMLUMLDomain domain) {
        String transf = getExactInstancesQuery(CommonElementsPackage.UMLDATATYPE);
        String link = getExactInstancesQuery(WebMLUMLMetaModeler.TR_WEBMLDATATYPE2UMLDATATYPE);
        setup(transf,link, domain);
        
        //UML
        put(CommonElementsPackage.UMLINTEGER, UMLMetaModeler.UMLINTEGER,"Integer");
        put(CommonElementsPackage.UMLSTRING, UMLMetaModeler.UMLSTRING,"String");
        put(CommonElementsPackage.UMLBOOLEAN, UMLMetaModeler.UMLBOOLEAN,"Boolean");
        put(CommonElementsPackage.UMLDOUBLE, UMLMetaModeler.UMLDOUBLE,"Double");
        put(CommonElementsPackage.UMLREAL, UMLMetaModeler.UMLREAL,"Real");

        //Java Profile
        put(CommonElementsPackage.JAVAVOID, UMLMetaModeler.UMLVOID,"void");
        put(CommonElementsPackage.JAVADATE, UMLMetaModeler.UMLDATE,"Date");


        //WebML Profile
        put(CommonElementsPackage.WEBML_IDENTIFIERTYPE, UMLMetaModeler.UMLSTRING,"String");
    }

    /**
     *
     * @param id
     * @param name
     * @throws ContractException
     */
    @Override
    protected void doTransformation(String id, String name) throws ContractException {
        String umlId = hashTypes.get(id);
        String umlName = hashNames.get(id);
        if(umlId == null || umlName== null){
            throw new ContractException(ContractException.MSG_TRANSFORMATIONEXCEPTION);
        }
        getDomain().getTargetDomain().insertType(umlId, umlName);
        insertLink(id, umlId);
    }

    private boolean insertLink(String webmlId, String umlId) throws ContractException {
        final String attName = "name";
        String name = webmlId + "To" + umlId;
        String id = JDHelper.getNewId();
        boolean result = manager.insertObject(WebMLUMLMetaModeler.TR_WEBMLDATATYPE2UMLDATATYPE, id);
        result &= manager.insertValue(WebMLUMLMetaModeler.TR_WEBMLDATATYPE2UMLDATATYPE,
                attName, id, name);
        result &= manager.insertLink(UMLMetaModeler.DATATYPE, umlId,
                                    WebMLUMLMetaModeler.ROLE_UML,
                                    WebMLUMLMetaModeler.ROLE_TRANSFORM,
                                    id, WebMLUMLMetaModeler.TR_WEBMLDATATYPE2UMLDATATYPE);
        result &= manager.insertLink(CommonElementsPackage.UMLDATATYPE,
                webmlId, WebMLUMLMetaModeler.ROLE_WEBML,
                WebMLUMLMetaModeler.ROLE_TRANSFORM, id,
                WebMLUMLMetaModeler.TR_WEBMLDATATYPE2UMLDATATYPE);
        return result;
    }

     /**
     *
     * @param id
     * @throws ContractException
     */
    @Override
    protected void doLink(String id) throws ContractException {
    }

    private void put(String key, String type, String name) {
        hashTypes.put(key,type);
        hashNames.put(key,name);
    }
}
