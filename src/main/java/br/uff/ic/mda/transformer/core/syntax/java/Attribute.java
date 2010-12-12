package br.uff.ic.mda.transformer.core.syntax.java;

/**
 *
 * @author robertowm
 */
public class Attribute implements Nonpersistent {

    private String visibility;
    private boolean staticAttribute;
    private boolean constantAttribute;
    private String type;
    private String name;
    private String value;

    /**
     *
     */
    protected Attribute() {
        this(null, false, false, null, null, null);
    }

    /**
     *
     * @param visibility
     * @param type
     * @param name
     */
    protected Attribute(String visibility, String type, String name) {
        this(visibility, false, false, type, name, null);
    }

    /**
     *
     * @param visibility
     * @param type
     * @param name
     * @param value
     */
    protected Attribute(String visibility, String type, String name, String value) {
        this(visibility, false, false, type, name, value);
    }

    /**
     *
     * @param visibility
     * @param staticAttribute
     * @param constantAttribute
     * @param type
     * @param name
     * @param value
     */
    protected Attribute(String visibility, boolean staticAttribute, boolean constantAttribute, String type, String name, String value) {
        this.visibility = visibility;
        this.staticAttribute = staticAttribute;
        this.constantAttribute = constantAttribute;
        this.type = type;
        this.name = name;
        this.value = value;
    }

    /**
     *
     * @return
     */
    public String getVisibility() {
        return this.visibility;
    }

    /**
     *
     * @param visibility
     * @return
     */
    public Attribute setVisibility(String visibility) {
        this.visibility = visibility;
        return this;
    }

    /**
     *
     * @return
     */
    public boolean isStaticAttribute() {
        return this.staticAttribute;
    }

    /**
     *
     * @param staticAttribute
     * @return
     */
    public Attribute setStaticAttribute(boolean staticAttribute) {
        this.staticAttribute = staticAttribute;
        return this;
    }

    /**
     *
     * @return
     */
    public boolean isConstantAttribute() {
        return this.constantAttribute;
    }

    /**
     *
     * @param constantAttribute
     * @return
     */
    public Attribute setConstantAttribute(boolean constantAttribute) {
        this.constantAttribute = constantAttribute;
        return this;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @param type
     * @return
     */
    public Attribute setType(String type) {
        this.type = type;
        return this;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @param name
     * @return
     */
    public Attribute setName(String name) {
        return setName(name, false);
    }

    /**
     *
     * @param name
     * @param lowerFirstLetter
     * @return
     */
    public Attribute setName(String name, boolean lowerFirstLetter) {
        this.name = (lowerFirstLetter) ? lowerFirstLetter(name) : name;
        return this;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return this.value;
    }

    /**
     *
     * @param value
     * @return
     */
    public Attribute setValue(String value) {
        this.value = value;
        return this;
    }

    /**
     *
     * @param s
     * @return
     */
    protected String lowerFirstLetter(String s) {
        return s.replaceFirst(s.substring(0,1), s.substring(0,1).toLowerCase());
    }

    /**
     *
     * @return
     */
    @Override
    public StringBuilder serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t");
        if (visibility != null && !"".equals(visibility.trim())) {
            sb.append(visibility).append(" ");
        }
        if (staticAttribute) {
            sb.append("static ");
        }
        if (constantAttribute) {
            sb.append("final ");
        }
        sb.append(type).append(" ").append(name);
        if (value != null) {
            sb.append(" = ").append(value);
        }
        sb.append(";");
        return sb;
    }

}
