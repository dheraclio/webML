package br.uff.ic.mda.transformer.core.syntax.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class Constructor implements Nonpersistent {

    private List<Parameter> parameters;
    private String code;
    private String className;

    /**
     *
     */
    public Constructor() {
    }

    /**
     *
     * @param className
     * @param code
     * @param parameters
     */
    public Constructor(String className, String code, Parameter... parameters) {
        this(className, code, Arrays.asList(parameters));
    }

    /**
     *
     * @param className
     * @param code
     * @param parameters
     */
    public Constructor(String className, String code, List<Parameter> parameters) {
        this.className = className;
        this.code = code;
        this.parameters = parameters;
    }

    /**
     *
     * @return
     */
    public List<Parameter> getParameters() {
        return this.parameters;
    }

    /**
     *
     * @param parameter
     * @return
     */
    public Constructor addParameter(Parameter parameter) {
        if (parameters == null) {
            parameters = new ArrayList<Parameter>();
        }
        parameters.add(parameter);
        return this;
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return this.code;
    }

    /**
     *
     * @param code
     * @return
     */
    public Constructor setCode(String code) {
        this.code = code;
        return this;
    }

    /**
     *
     * @return
     */
    public String getClassName() {
        return this.className;
    }

    /**
     *
     * @param className
     * @return
     */
    public Constructor setClassName(String className) {
        this.className = className;
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public StringBuilder serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tpublic ").append(className);
        if (parameters != null && !parameters.isEmpty()) {
            sb.append("(");
            for (int i = 0; i < parameters.size() - 1; i++) {
                sb.append(parameters.get(i).getType()).append(" ").append(parameters.get(i).getName()).append(", ");
            }
            sb.append(parameters.get(parameters.size() - 1).getType()).append(" ").append(parameters.get(parameters.size() - 1).getName()).append(")");
        } else {
            sb.append("()");
        }
        sb.append(" {\n");
        if (code != null && !code.trim().isEmpty()) {
            for (String string : code.split("\n")) {
                sb.append("\t\t").append(string).append("\n");
            }
        }
        sb.append("\t}\n");
        return sb;
    }
}
