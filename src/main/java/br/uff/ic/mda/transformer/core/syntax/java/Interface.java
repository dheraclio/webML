package br.uff.ic.mda.transformer.core.syntax.java;

import br.uff.ic.mda.transformer.core.util.XFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author robertowm
 */
public class Interface implements Persistent {

    private String directoryPath;
    private String packagePath;
    private String name;
    private String visibility;
    private List<String> classNamesThatExtends;
    private List<Attribute> constants;
    private List<String> importPaths;
    private List<Method> methods;

    /**
     *
     */
    protected Interface() {
    }

    /**
     *
     */
    @Override
    public void persist() {
        String path = "";
        if (directoryPath != null && !"".equals(directoryPath.trim())) {
            path += directoryPath;
        }
        if (packagePath != null && !"".equals(packagePath.trim())) {
            path += packagePath.replace(".", "/") + "/";
        }
        XFile output = new XFile(path + name + ".java");

        StringBuilder sourceCode = new StringBuilder();

        if (packagePath != null && !"".equals(packagePath.trim())) {
            sourceCode.append("package ").append(packagePath).append(";\n\n");
        }

        if (importPaths != null && !importPaths.isEmpty()) {
            for (String string : importPaths) {
                sourceCode.append("import ").append(string).append(";\n");
            }
            sourceCode.append("\n");
        }

        if (visibility != null && !"".equals(visibility.trim())) {
            sourceCode.append(visibility).append(" ");
        }

        sourceCode.append("interface ").append(name);

        if (classNamesThatExtends != null && !classNamesThatExtends.isEmpty()) {
            sourceCode.append(" extends ");
            for (int i = 0; i < classNamesThatExtends.size() - 1; i++) {
                sourceCode.append(classNamesThatExtends.get(i)).append(", ");
            }
            sourceCode.append(classNamesThatExtends.get(classNamesThatExtends.size() - 1));
        }

        sourceCode.append(" {\n\n");

        if (constants != null) {
            for (Attribute atributo : constants) {
                sourceCode.append(atributo.serialize()).append("\n");
            }
            sourceCode.append("\n");
        }

        if (methods != null) {
            for (Method metodo : methods) {
                sourceCode.append(metodo.serialize()).append("\n\n");
            }
        }

        sourceCode.append("\n}");

        output.substituteFor(sourceCode);
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
    public Interface setName(String name) {
        this.name = name;
        return this;
    }

    /**
     *
     * @return
     */
    public String getPackagePath() {
        return this.packagePath;
    }

    /**
     *
     * @param packagePath
     * @return
     */
    public Interface setPackagePath(String packagePath) {
        this.packagePath = packagePath;
        return this;
    }

    /**
     *
     * @return
     */
    public String getDirectoryPath() {
        return this.directoryPath;
    }

    /**
     *
     * @param directoryPath
     * @return
     */
    public Interface setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
        return this;
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
    public Interface setVisibility(String visibility) {
        this.visibility = visibility;
        return this;
    }

    /**
     *
     * @return
     */
    public List<String> getClassNamesThatExtends() {
        return this.classNamesThatExtends;
    }

    /**
     *
     * @param className
     * @return
     */
    public Interface addClassNamesThatExtends(String className) {
        if (classNamesThatExtends == null) {
            classNamesThatExtends = new ArrayList<String>();
        }
        classNamesThatExtends.add(className);
        return this;
    }

    /**
     *
     * @return
     */
    public List<Attribute> getConstants() {
        return this.constants;
    }

    /**
     *
     * @param constant
     * @return
     */
    public Interface addConstant(Attribute constant) {
        if (constants == null) {
            constants = new ArrayList<Attribute>();
        }
        constant.setVisibility("public");
        constants.add(constant);
        return this;
    }

    /**
     *
     * @return
     */
    public List<String> getImportPaths() {
        return this.importPaths;
    }

    /**
     *
     * @param importPath
     * @return
     */
    public Interface addImportPath(String importPath) {
        if (importPaths == null) {
            importPaths = new ArrayList<String>();
        }
        importPaths.add(importPath);
        return this;
    }

    /**
     *
     * @return
     */
    public List<Method> getMethods() {
        return this.methods;
    }

    /**
     *
     * @param method
     * @return
     */
    public Interface addMethod(Method method) {
        if (methods == null) {
            methods = new ArrayList<Method>();
        }
        methods.add(method);
        return this;
    }

    /**
     *
     * @param type
     * @param name
     * @param parameters
     * @return
     */
    public Interface addMethod(String type, String name, Parameter... parameters) {
        Method metodo = new Method().setVisibility("public").setReturnType(type).setName(name, true);
        for (Parameter parametro : parameters) {
            metodo.addParameter(parametro);
        }
        return this.addMethod(metodo);
    }
}
