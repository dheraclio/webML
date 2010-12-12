/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.ic.mda.transformer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class WebMLUMLDomainTest {

    public WebMLUMLDomainTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testCreateMetamodel() throws Exception {
        WebMLUMLDomain instance = new WebMLUMLDomain(new WebMLDomain(),new UMLDomain());
        instance.createMetamodel();
        assertTrue(true);
    }

    @Test
    public void testCreateSpecificationOfCurrentDiagram() throws Exception {
        System.out.println("createSpecificationOfCurrentDiagram");
        WebMLUMLDomain instance = null;
        instance.createSpecificationOfCurrentDiagram();
        fail("The test case is a prototype.");
    }

    @Test
    public void testTransformDomain() throws Exception {
        System.out.println("transformDomain");
        WebMLUMLDomain instance = null;
        instance.transformDomain();
        fail("The test case is a prototype.");
    }

}