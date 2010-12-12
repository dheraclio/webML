/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.ic.mda.transformer;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class WebMLDomainTest {

    public WebMLDomainTest() {
    }
    
    @Test
    public void testCreateMetamodel() throws Exception {
        WebMLDomain instance = new WebMLDomain();
        instance.createMetamodel();
        assertTrue(true);
    }

    @Test
    public void testCreateSpecificationOfCurrentDiagram() throws Exception {
        WebMLDomain instance = new WebMLDomain();
        instance.createSpecificationOfCurrentDiagram();
        assertTrue(true);
    }

    @Test
    public void testLoadDataFromParser() throws Exception {
    }

}