package br.uff.ic.mda.transformer.core.syntax.java;

/**
 *
 * @author robertowm
 */
public class Parameter implements Nonpersistent {

    private String type;
    private String name;

    /**
     *
     */
    protected Parameter() {
        this(null, null);
    }

    /**
     *
     * @param type
     * @param name
     */
    protected Parameter(String type, String name) {
        this.type = type;
        this.name = name;
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
    public Parameter setType(String type) {
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
    public Parameter setName(String name) {
        return setName(name, false);
    }

    /**
     *
     * @param name
     * @param lowerFirstLetter
     * @return
     */
    public Parameter setName(String name, boolean lowerFirstLetter) {
        this.name = (lowerFirstLetter) ? lowerFirstLetter(name) : name;
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
        sb.append(type).append(" ").append(name);
        return sb;
    }

}
