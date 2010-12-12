package br.uff.ic.mda.transformer.core.syntax.java;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author robertowm
 */
public class Method implements Nonpersistent {

    private String visibility = "public";
    private boolean staticMethod = false;
    private boolean overridable = true;
    private boolean abstractMethod = false;
    private String returnType = "void";
    private String name;
    private List<Parameter> parameters;
    private List<String> exceptions;
    private String[] code;

    /**
     *
     */
    protected Method() {
    }

    /**
     *
     * @param visibility
     * @param returnType
     * @param name
     */
    protected Method(String visibility, String returnType, String name) {
        this.visibility = visibility;
        this.returnType = returnType;
        this.name = name;
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
    public Method setVisibility(String visibility) {
        this.visibility = visibility;
        return this;
    }

    /**
     *
     * @return
     */
    public boolean isStaticMethod() {
        return staticMethod;
    }

    /**
     *
     * @param staticMethod
     * @return
     */
    public Method setStaticMethod(boolean staticMethod) {
        this.staticMethod = staticMethod;
        return this;
    }

    /**
     *
     * @return
     */
    public boolean isOverridable() {
        return overridable;
    }

    /**
     *
     * @param overridable
     * @return
     */
    public Method setOverridable(boolean overridable) {
        this.overridable = overridable;
        return this;
    }

    /**
     *
     * @return
     */
    public boolean isAbstractMethod() {
        return abstractMethod;
    }

    /**
     *
     * @param abstractMethod
     * @return
     */
    public Method setAbstractMethod(boolean abstractMethod) {
        this.abstractMethod = abstractMethod;
        return this;
    }

    /**
     *
     * @return
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     *
     * @param returnType
     * @return
     */
    public Method setReturnType(String returnType) {
        this.returnType = returnType;
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
    public Method setName(String name) {
        return setName(name, false);
    }

    /**
     *
     * @param name
     * @param lowerFirstLetter
     * @return
     */
    public Method setName(String name, boolean lowerFirstLetter) {
        this.name = (lowerFirstLetter) ? lowerFirstLetter(name) : name;
        return this;
    }

    /**
     *
     * @param s
     * @return
     */
    protected String lowerFirstLetter(String s) {
        return s.replaceFirst(s.substring(0, 1), s.substring(0, 1).toLowerCase());
    }

    /**
     *
     * @return
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     *
     * @param parameter
     * @return
     */
    public Method addParameter(Parameter parameter) {
        if (parameters == null) {
            parameters = new ArrayList<Parameter>();
        }
        parameters.add(parameter);
        return this;
    }

    /**
     *
     * @param type
     * @param name
     * @return
     */
    public Method addParameter(String type, String name) {
        if (parameters == null) {
            parameters = new ArrayList<Parameter>();
        }
        parameters.add(new Parameter(type, name));
        return this;
    }

    /**
     *
     * @return
     */
    public String[] getCode() {
        return code;
    }

    /**
     *
     * @param code
     * @return
     */
    public Method setCode(String[] code) {
        this.code = code;
        return this;
    }

    /**
     *
     * @return
     */
    public List<String> getExceptions() {
        return exceptions;
    }

    /**
     *
     * @param exception
     * @return
     */
    public Method addException(String exception) {
        if (exceptions == null) {
            exceptions = new ArrayList<String>();
        }
        exceptions.add(exception);
        return this;
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
        if (staticMethod) {
            sb.append("static ");
        }
        if (!overridable) {
            sb.append("final ");
        }
        if (abstractMethod) {
            sb.append("abstract ");
        }

        sb.append(returnType).append(" ").append(name);

        sb.append("(");
        if (parameters != null && !parameters.isEmpty()) {
            for (int i = 0; i < parameters.size() - 1; i++) {
                sb.append(parameters.get(i).serialize()).append(", ");
            }
            sb.append(parameters.get(parameters.size() - 1).serialize());
        }
        sb.append(")");

        if (exceptions != null && !exceptions.isEmpty()) {
            sb.append(" throws ");
            for (int i = 0; i < exceptions.size() - 1; i++) {
                sb.append(exceptions.get(i)).append(", ");
            }
            sb.append(exceptions.get(exceptions.size() - 1));
        }

        if (abstractMethod) {
            sb.append(";\n");
        } else {
            sb.append(" {\n");
            if (code != null) {
                for (int i = 0; i < code.length; i++) {
                    sb.append("\t\t").append(code[i]).append("\n");
                }
            }
            sb.append("\t}");
        }
        return sb;
    }
}
