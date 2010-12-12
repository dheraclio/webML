/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.TransformationContract;
import br.uff.ic.mda.xmiparser.XMIParser;
import br.uff.ic.mda.transformer.util.TestHelper;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class TransformerTest {

    //@Test
    public void testUMLtoEJBTransformation() throws Exception {
        String pathXmi = TestHelper.getCurrentTargetTestPath() + "/transformer/blog.xmi";
        UMLDomain umlDomain = new UMLDomain(new XMIParser(), pathXmi);
        EJBDomain ejbDomain = new EJBDomain();
        UMLEJBDomain joinedDomain = new UMLEJBDomain(umlDomain, ejbDomain);
        TransformationContract contract = new TransformationContract(joinedDomain);
        contract.transform();
        assertTrue(true);
    }

    @Test
    public void testWebMLToUMLTransformation() throws Exception {
        String pathXmi = TestHelper.getCurrentTargetTestPath() + "/transformer/blogwebml.xmi";
        WebMLDomain webmlDomain = new WebMLDomain(new XMIParser(), pathXmi);
        UMLDomain umlDomain = new UMLDomain();
        WebMLUMLDomain joinedDomain = new WebMLUMLDomain(webmlDomain, umlDomain);
        TransformationContract contract = new TransformationContract(joinedDomain);
        contract.transform();
        assertTrue(true);
    }

    //@Test
    public void testWebMLToEJBTransformation() throws Exception {
        String pathXmi = TestHelper.getCurrentTargetTestPath() + "/transformer/blogwebml.xmi";
        WebMLDomain webmlDomain = new WebMLDomain(new XMIParser(), pathXmi);
        UMLDomain umlDomain = new UMLDomain();
        WebMLUMLDomain joinedDomain = new WebMLUMLDomain(webmlDomain, umlDomain);
        TransformationContract contract = new TransformationContract(joinedDomain);
        contract.transform();

        EJBDomain ejbDomain = new EJBDomain();
        UMLEJBDomain ejbjoinedDomain = new UMLEJBDomain(umlDomain, ejbDomain);
        TransformationContract contract2 = new TransformationContract(ejbjoinedDomain);
        contract2.transform();

        assertTrue(true);
    }
}
