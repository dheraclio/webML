package br.uff.ic.mda.transformer.core.syntax.java;

/**
 *
 * @author robertowm
 */
public class JavaSyntax {

    /**
     *
     * @return
     */
    public static Class getJavaClass() {
        return new Class();
    }

    /**
     *
     * @return
     */
    public static Interface getJavaInterface() {
        return new Interface();
    }

    /**
     *
     * @return
     */
    public static Attribute getJavaAttribute() {
        return new Attribute();
    }

    /**
     *
     * @return
     */
    public static Method getJavaMethod() {
        return new Method();
    }

    /**
     *
     * @return
     */
    public static Parameter getJavaParameter() {
        return new Parameter();
    }

    /**
     *
     * @return
     */
    public static Constructor getJavaConstructor() {
        return new Constructor();
    }
}
