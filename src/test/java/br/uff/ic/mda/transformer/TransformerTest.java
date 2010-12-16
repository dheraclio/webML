/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.ChainTransformationContract;
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

    private String umlBlog = TestHelper.getCurrentTargetTestPath() + "/transformer/blog.xmi";
    private String webmlBlog = TestHelper.getCurrentTargetTestPath() + "/transformer/blogwebml.xmi";
    private String entitywebml = TestHelper.getCurrentTargetTestPath() + "/transformer/entitywebml.xmi";
    private int loopPause = 250;

    //UML para EJB original do Roberto
//    @Test
    public void testUMLtoEJBTransformation() throws Exception {

        UMLDomain umlDomain = new UMLDomain(new XMIParser(), umlBlog);
        EJBDomain ejbDomain = new EJBDomain();
        UMLEJBDomain joinedDomain = new UMLEJBDomain(umlDomain, ejbDomain);
        TransformationContract contract = new TransformationContract(joinedDomain);
        contract.transform();
        assertTrue(true);
    }

    //UML para EJB, loop de chamada comum
//    @Test
    public void testUMLtoEJBTransformationLoop() throws Exception {

        for (int i = 0; i < 5; i++) {
            testUMLtoEJBTransformation();
            Thread.sleep(loopPause);
        }
    }

    //UML para EJB usando chaintransformation
//    @Test
    public void testUMLtoEJBChainTransformation() throws Exception {

        UMLDomain umlDomain = new UMLDomain(new XMIParser(), umlBlog);
        EJBDomain ejbDomain = new EJBDomain();
        UMLEJBDomain ejbjoinedDomain = new UMLEJBDomain(umlDomain, ejbDomain);

        ChainTransformationContract chain = new ChainTransformationContract();
        chain.add(new TransformationContract(ejbjoinedDomain));
        chain.transform();
    }

    //UML para EJB, loop de chamada a chaintransformation
//    @Test
    public void testUMLtoEJBChainTransformationLoop() throws Exception {

        for (int i = 0; i < 5; i++) {
            testUMLtoEJBChainTransformation();
            Thread.sleep(loopPause);
        }
    }

    //WebML para UML, sem saída
    //@Test
    public void testWebMLToUMLTransformation() throws Exception {
        WebMLDomain webmlDomain = new WebMLDomain(new XMIParser(), webmlBlog);
        UMLDomain umlDomain = new UMLDomain();
        WebMLUMLDomain joinedDomain = new WebMLUMLDomain(webmlDomain, umlDomain);

        TransformationContract contract = new TransformationContract(joinedDomain);
        contract.transform();
        assertTrue(true);
    }

    //WebML para UML, sem saída usando chaintransformation
//    @Test
    public void testWebMLtoUMLChainTransformation() throws Exception {
        WebMLDomain webmlDomain = new WebMLDomain(new XMIParser(), webmlBlog);
        UMLDomain umlDomain = new UMLDomain(new XMIParser(), umlBlog);
        WebMLUMLDomain joinedDomain = new WebMLUMLDomain(webmlDomain, umlDomain);

        ChainTransformationContract chain = new ChainTransformationContract();
        chain.add(new TransformationContract(joinedDomain));
        chain.transform();
    }

    @Test
    public void testWebMLToEJBChainTransformation() throws Exception {
        //WebMLDomain webmlDomain = new WebMLDomain(new XMIParser(), webmlBlog);
        WebMLDomain webmlDomain = new WebMLDomain(new XMIParser(), entitywebml);
        UMLDomain umlDomain = new UMLDomain();
        EJBDomain ejbDomain = new EJBDomain();
        WebMLUMLDomain joinedDomain = new WebMLUMLDomain(webmlDomain, umlDomain);
        UMLEJBDomain ejbjoinedDomain = new UMLEJBDomain(umlDomain, ejbDomain);

        ChainTransformationContract chain = new ChainTransformationContract();
        chain.add(new TransformationContract(joinedDomain));
        chain.add(new TransformationContract(ejbjoinedDomain));
        chain.transform();

//        System.out.println(ModelManager.instance().query("WEBMLUML_DataType"));
//        System.out.println(ModelManager.instance().query("WEBMLUML_DataType.allInstances()"));
//        System.out.println(ModelManager.instance().query("WEBMLUML_DataType.allInstances().name"));

        assertTrue(true);
    }

    //@Test
    public void testWebMLToEJBChainTransformationLoop() throws Exception {
        for (int i = 0; i < 5; i++) {
            testWebMLToEJBChainTransformation();
            Thread.sleep(loopPause);
        }
    }
}
