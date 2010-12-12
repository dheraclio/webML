package br.uff.ic.mda.transformer.core.syntax.java;

import br.uff.ic.mda.transformer.core.util.XFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author robertowm
 */
public class Class implements Persistent {

    private String directoryPath;
    private String packagePath;
    private String name;
    private String visibility;
    private boolean abstractClass = false;
    private String classNameThatExtends;
    private List<String> classNamesThatImplements;
    private List<Attribute> attributes;
    private List<String> importPaths;
    private List<Constructor> constructors;
    private List<Method> methods;

    /**
     *
     */
    protected Class() {
    }

    /**
     *
     */
    @Override
    public void persist() {
        String path = directoryPath;
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

        if (abstractClass) {
            sourceCode.append("abstract ");
        }

        sourceCode.append("class ").append(name);

        if (classNameThatExtends != null && !"".equals(classNameThatExtends.trim())) {
            sourceCode.append(" extends ").append(classNameThatExtends);
        }

        if (classNamesThatImplements != null && !classNamesThatImplements.isEmpty()) {
            sourceCode.append(" implements ");
            for (int i = 0; i < classNamesThatImplements.size() - 1; i++) {
                sourceCode.append(classNamesThatImplements.get(i)).append(", ");
            }
            sourceCode.append(classNamesThatImplements.get(classNamesThatImplements.size() - 1));
        }

        sourceCode.append(" {\n\n");

        if (attributes != null) {
            for (Attribute atributo : attributes) {
                sourceCode.append(atributo.serialize()).append("\n");
            }
            sourceCode.append("\n");
        }


        if (constructors != null) {
            for (Constructor construtor : constructors) {
                sourceCode.append(construtor.serialize()).append("\n");
            }
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
    public Class setName(String name) {
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
    public Class setPackagePath(String packagePath) {
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
    public Class setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
        if (this.directoryPath.charAt(this.directoryPath.length() - 1) != '/') {
            this.directoryPath += "/";
        }
        return this;
    }

    /**
     *
     * @return
     */
    public boolean isAbstractClass() {
        return this.abstractClass;
    }

    /**
     *
     * @param abstractClass
     * @return
     */
    public Class setAbstractClass(boolean abstractClass) {
        this.abstractClass = abstractClass;
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
    public Class setVisibility(String visibility) {
        this.visibility = visibility;
        return this;
    }

    /**
     *
     * @return
     */
    public String getClassNameThatExtends() {
        return this.classNameThatExtends;
    }

    /**
     *
     * @param classNameThatExtends
     * @return
     */
    public Class setClassNameThatExtends(String classNameThatExtends) {
        this.classNameThatExtends = classNameThatExtends;
        return this;
    }

    /**
     *
     * @return
     */
    public List<String> getClassNamesThatImplements() {
        return this.classNamesThatImplements;
    }

    /**
     *
     * @param className
     * @return
     */
    public Class addClassNamesThatImplements(String className) {
        if (classNamesThatImplements == null) {
            classNamesThatImplements = new ArrayList<String>();
        }
        classNamesThatImplements.add(className);
        return this;
    }

    /**
     *
     * @return
     */
    public List<Attribute> getAttributes() {
        return this.attributes;
    }

    /**
     *
     * @param attribute
     * @return
     */
    public Class addAttribute(Attribute attribute) {
        if (attributes == null) {
            attributes = new ArrayList<Attribute>();
        }
        attributes.add(attribute);
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
    public Class addImportPath(String importPath) {
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
    public List<Constructor> getConstructors() {
        return this.constructors;
    }

    /**
     *
     * @param constructor
     * @return
     */
    public Class addConstructor(Constructor constructor) {
        if (constructors == null) {
            constructors = new ArrayList<Constructor>();
        }
        constructors.add(constructor);
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
    public Class addMethod(Method method) {
        if (methods == null) {
            methods = new ArrayList<Method>();
        }
        methods.add(method);
        return this;
    }
}
