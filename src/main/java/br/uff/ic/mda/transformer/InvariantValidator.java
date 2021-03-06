package br.uff.ic.mda.transformer;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.IValidator;
import br.uff.ic.mda.tclib.ModelManager;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author robertowm original
 *
 * @author daniel 09-11-10
 *      load invariants from properties file
 *      encapsulate fields
 *      identation
 */
public abstract class InvariantValidator implements IValidator {

    /** Field description */
    private Logger logger = null;
    /** Field description */
    private String domainName;
    /** Field description */
    private Map<String, String> invariants;

    /**
     * Constructs ...
     *
     *
     * @param domainName
     * @param loggerFileName
     *
     * @throws ContractException
     */
    public InvariantValidator(String domainName, String loggerFileName) throws ContractException {
        setup(domainName,loggerFileName);
    }

    /**
     * Constructs ...
     *
     *
     * @param domainName
     * @param loggerFileName
     *
     * @throws ContractException
     */
    public void setup(String domainName, String loggerFileName) throws ContractException {
        this.domainName = domainName;
        this.logger = LoggerFactory.getLogger(loggerFileName);
        this.invariants = new HashMap<String, String>();
        createOperations();
        createInvariants();
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    @Override
    public void validate() throws ContractException {
        if (!checkAllInvariants()) {
            throw new ContractException("[ERROR] Can't validate " + getDomainName() + " domain.");
        }
    }

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    protected abstract void createOperations() throws ContractException;

    /**
     * Method description
     *
     *
     * @throws ContractException
     */
    protected abstract void createInvariants() throws ContractException;

    /**
     * Create invariants from propertie file on resource
     *
     * @param resourcePath
     * @throws ContractException
     */
    protected void createInvariants(String resourcePath) throws ContractException {
        try {
            InputStream in = this.getClass().getResourceAsStream(resourcePath + ".properties");
            Properties prop = new Properties();
            prop.load(in);
            for (Iterator it = prop.entrySet().iterator(); it.hasNext();) {
                Entry<String, String> entry = (Entry<String, String>) it.next();
                this.insertInvariant(entry.getKey(), entry.getValue());
            }
        } catch (IOException ex) {
            final String msg = "Fail to create Invariants";
            ContractException e = new ContractException(msg, ex);
            getLogger().error(msg, e);
            throw e;
        }
    }

    /**
     * Method description
     *
     * @param invName
     * @param invBody
     *
     * @return
     * @throws ContractException
     */
    protected final boolean insertInvariant(String invName, String invBody) throws ContractException {
        try {
            if( this.getInvariants().containsKey(invName) ){
                throw new Exception("Trying to overwrite invariant" + invName);
            }
            this.getInvariants().put(invName, invBody);
        } catch (Exception ex) {
            final String msg = "Fail to insert invariant";
            ContractException e = new ContractException(msg, ex);
            getLogger().error(msg,e);
            throw e;
        }

        return true;
    }

    /**
     * Check all invariants of this metamodel and log the result
     *
     * @return if all invariants was checked and passed then 'true' else 'false'
     */
    public final boolean checkAllInvariants() {
        boolean result = true;
        getLogger().info(" ---- Checking invariants ---");

        for (Map.Entry<String, String> entry : getInvariants().entrySet()) {
            result &= checkInvariant(entry.getKey(), entry.getValue());
        }

        getLogger().info(" -- Result: " + result + " --");
        getLogger().info(" --------");

        return result;
    }

    /**
     * Check an invariant of this metamodel
     *
     * @param name
     * @param query
     * @return if the invariant was checked and passed then 'true' else 'false'
     */
    protected final boolean checkInvariant(String name, String query) {
        String result = null;

        try {
            result = ModelManager.instance().query(query);

            if (result.equals("true")) {
                getLogger().info("TRUE to query " + name + " -=- result: " + result);

                return true;
            } else {
                logger.error("FALSE to query " + name + " -=- result: " + result);

                return false;
            }
        } catch (ContractException ex) {
            logger.error("EXCEPTION execution query '" + name + "': " + ex.getMessage());

            return false;
        }
    }

    /**
     * @return the invariants
     */
    protected Map<String, String> getInvariants() {
        return this.invariants;
    }

    /**
     * @param invariants the invariants to set
     */
    protected void setInvariants(Map<String, String> invariants) {
        this.invariants.putAll(invariants);
    }

    /**
     * @return the logger
     */
    protected Logger getLogger() {
        return logger;
    }

    /**
     * @param logger the logger to set
     */
    protected void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * @return the domainName
     */
    protected String getDomainName() {
        return domainName;
    }

    /**
     * @param domainName the domainName to set
     */
    protected void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
