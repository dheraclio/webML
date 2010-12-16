package br.uff.ic.mda.transformer.core.syntax.ejb;

import br.uff.ic.mda.tclib.ContractException;
import br.uff.ic.mda.tclib.ModelManager;
import br.uff.ic.mda.transformer.core.syntax.java.Attribute;
import br.uff.ic.mda.transformer.core.syntax.java.Class;
import br.uff.ic.mda.transformer.core.syntax.java.Constructor;
import br.uff.ic.mda.transformer.core.syntax.java.Interface;
import br.uff.ic.mda.transformer.core.syntax.java.JavaSyntax;
import br.uff.ic.mda.transformer.core.syntax.java.Method;
import br.uff.ic.mda.transformer.core.syntax.java.Parameter;
import br.uff.ic.mda.transformer.core.util.StringUtils;

/**
 *
 * @author Daniel
 */
public class EJBCodeGenerator{

    public static void generate() throws ContractException {
        generateEJBKeyClass();
        generateEJBEntityComponent();
        generateEJBDataClass();
    }

    // code generation
    private static String convertEjbTypeToJavaType(String ejbType) throws ContractException {
        ModelManager manager = ModelManager.instance();

        if ("true".equals(manager.query(ejbType + ".oclIsTypeOf(" + EJBMetamodeler.DATACLASS +")").replace("'", ""))) {
            return manager.query(ejbType + ".name").replace("'", "") + "DataObject";
        }
        if ("true".equals(manager.query(ejbType + ".oclIsTypeOf("+ EJBMetamodeler.SET+")").replace("'", ""))) {
            String collectionType = ModelManager.processQueryResult(manager.query(ejbType + ".elementType.name"))[0];
            if (collectionType.equals("void"))
                return "Collection";
            return "Collection<" + collectionType + ">";
        }
        return manager.query(ejbType + ".name").replace("'", "");
    }

    private static void generateEJBKeyClass() throws ContractException {
        ModelManager manager = ModelManager.instance();
        
        String query = manager.query(EJBMetamodeler.KEYCLASS +".allInstances()");
        String[] ids = ModelManager.processQueryResult(query);

        for (String id : ids) {
            try {
                String idClass = id.replace("'", "").trim();

                Class keyClass = JavaSyntax.getJavaClass();
                keyClass.setDirectoryPath("generated_code/");
                keyClass.setPackagePath("app");

                query = idClass + ".name";

                keyClass.setName(manager.query(query).replace("'", ""));
                keyClass.addClassNamesThatImplements("java.io.Serializable");
                keyClass.addConstructor(new Constructor(keyClass.getName(), ""));
                keyClass.setVisibility("public");

                query = EJBMetamodeler.ATTRIBUTE +".allInstances()->select(attr | attr.class = " + idClass + ")->asSet()";
                String[] attributeIds = ModelManager.processQueryResult(manager.query(query));
                for (String attributeId : attributeIds) {
                    String idAtributo = attributeId.replace("'", "").trim();
                    Attribute attribute = JavaSyntax.getJavaAttribute();
                    attribute.setVisibility("public");
                    query = idAtributo + ".type";
                    attribute.setType(convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(query))[0]));
                    query = idAtributo + ".name";
                    attribute.setName(ModelManager.processQueryResult(manager.query(query))[0], true);

                    keyClass.addAttribute(attribute);
                }

                StringBuilder sourceCode = new StringBuilder();
                for (Attribute attribute : keyClass.getAttributes()) {
                    sourceCode.append("this.")
                            .append(attribute.getName())
                            .append(" = ")
                            .append(attribute.getName())
                            .append(";\n");
                }

                Constructor constructor = JavaSyntax.getJavaConstructor().setClassName(keyClass.getName()).setCode(sourceCode.toString());
                for (Attribute attribute : keyClass.getAttributes()) {
                    constructor.addParameter(JavaSyntax.getJavaParameter().setName(attribute.getName()).setType(attribute.getType()));
                }
                keyClass.addConstructor(constructor);

                // Insere 'public boolean equals(Object obj)'
                Method equals = JavaSyntax.getJavaMethod().setVisibility("public").setReturnType("boolean").setName("equals").addParameter("Object", "obj");
                StringBuilder equalsCondition = new StringBuilder();
                for (int i = 0; i < keyClass.getAttributes().size() - 1; i++) {
                    Attribute attribute = keyClass.getAttributes().get(i);
                    equalsCondition.append("this.")
                            .append(attribute.getName())
                            .append(".equals(other.")
                            .append(attribute.getName())
                            .append(") and ");
                }
                Attribute lastKeyClassAttribute = keyClass.getAttributes().get(keyClass.getAttributes().size() - 1);
                equalsCondition.append("this.")
                        .append(lastKeyClassAttribute.getName())
                        .append(".equals(other.")
                        .append(lastKeyClassAttribute.getName())
                        .append(")");
                equals.setCode(new String[]{
                            "if(!(obj instanceof " + keyClass.getName() + ")) {",
                            "\treturn false;",
                            "}",
                            keyClass.getName() + " other = (" + keyClass.getName() + ") obj;",
                            "if (" + equalsCondition + ") {",
                            "\treturn true;",
                            "}",
                            "return false;"
                        });
                keyClass.addMethod(equals);

                // Insere 'public int hashCode()'
                Method hashCode = JavaSyntax.getJavaMethod().setVisibility("public").setReturnType("int").setName("hashCode");
                String[] hashCodeCode = new String[keyClass.getAttributes().size() + 2];
                int initialHashValue = 3;
                int otherHashValue = 19;
                hashCodeCode[0] = "int hash = " + initialHashValue + ";";
                hashCodeCode[hashCodeCode.length - 1] = "return hash;";
                for (int i = 0; i < keyClass.getAttributes().size(); i++) {
                    Attribute attribute = keyClass.getAttributes().get(i);
                    hashCodeCode[i + 1] = "hash = " + otherHashValue + " * hash + (this." + attribute.getName() + " != null ? this." + attribute.getName() + ".hashCode() : 0);";
                }
                hashCode.setCode(hashCodeCode);
                keyClass.addMethod(hashCode);

                keyClass.persist();
            } catch (Exception ex) {
                throw new ContractException("Can't persist an "+ EJBMetamodeler.KEYCLASS, ex);
            }
        }
    }

    private static void generateEJBEntityComponent() throws ContractException {
        ModelManager manager = ModelManager.instance();
        
        String[] ids = ModelManager.processQueryResult(manager.query("UMLClassToEJBEntityComponent.allInstances()").replace("\n", ""));

        for (String id : ids) {
            try {
                // Criando o EJBObject
                Interface ejbObject = JavaSyntax.getJavaInterface();
                ejbObject.setDirectoryPath("generated_code/");
                ejbObject.setPackagePath("app");

                ejbObject.addImportPath("java.rmi.*");
                ejbObject.addImportPath("javax.naming.*");
                ejbObject.addImportPath("javax.ejb.*");
                ejbObject.addImportPath("java.util.*");
                ejbObject.setName(ModelManager.processQueryResult(manager.query(id + ".entityComponent.name"))[0]);
                ejbObject.addClassNamesThatExtends("EJBObject");
                ejbObject.setVisibility("public");

                // Insere getter e setter para o DataClass
                String dataClassId = ModelManager.processQueryResult(manager.query(id + ".dataClass"))[0];
                String dataClassName = convertEjbTypeToJavaType(dataClassId);
                Method getterDataClass = JavaSyntax.getJavaMethod();
                getterDataClass.setName("get" + dataClassName).setReturnType(dataClassName).setVisibility("public").addException("RemoteException").setAbstractMethod(true);
                ejbObject.addMethod(getterDataClass);

                Method setterDataClass = JavaSyntax.getJavaMethod();
                setterDataClass.setName("set" + dataClassName).setReturnType("void").setVisibility("public").addParameter(dataClassName, "update").addException("NamingException").addException("FinderException").addException("CreateException").addException("RemoteException").setAbstractMethod(true);
                ejbObject.addMethod(setterDataClass);

                String[] businessMethodIds = ModelManager.processQueryResult(manager.query(id + ".entityComponent.feature->select(f : EJBFeature | f.oclIsTypeOf(BusinessMethod))->collect(f : EJBFeature | f.oclAsType(BusinessMethod))->select(bm : BusinessMethod | bm.name.substring(1, 4) <> 'find')"));
                for (String businessMethodId : businessMethodIds) {
                    Method method = JavaSyntax.getJavaMethod();
                    method.setVisibility("public").setReturnType(convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(businessMethodId + ".type"))[0])).setName(manager.query(businessMethodId + ".name").replace("'", "")).addException("RemoteException").setAbstractMethod(true);

                    String[] parameterIds = ModelManager.processQueryResult(manager.query(businessMethodId + ".parameter"));
                    for (String parameterId : parameterIds) {
                        Parameter parameter = JavaSyntax.getJavaParameter();
                        parameter.setName(manager.query(parameterId + ".name").replace("'", ""), true).setType(convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(parameterId + ".type"))[0]));
                        method.addParameter(parameter);
                    }
                    ejbObject.addMethod(method);
                }

                ejbObject.persist();

                // Criando o EJBHome

                Interface ejbHome = JavaSyntax.getJavaInterface();
                ejbHome.setDirectoryPath("generated_code/");
                ejbHome.setPackagePath("app");

                ejbHome.addImportPath("java.rmi.*");
                ejbHome.addImportPath("javax.naming.*");
                ejbHome.addImportPath("javax.ejb.*");
                ejbHome.addImportPath("java.util.*");
                ejbHome.setName(ModelManager.processQueryResult(manager.query(id + ".entityComponent.name"))[0] + "Home");
                ejbHome.addClassNamesThatExtends("EJBHome");
                ejbHome.setVisibility("public");

                String primaryKeyId = ModelManager.processQueryResult(manager.query("UMLClassToEJBKeyClass.allInstances()->select(a | a.class = " + id + ".class).keyClass"))[0];
                String primaryKeyName = manager.query(primaryKeyId + ".name").replace("'", "");
                // Insere create(...)
                Method createMethod = JavaSyntax.getJavaMethod();
                createMethod.setName("create").setVisibility("public").setReturnType(ejbObject.getName()).setAbstractMethod(true).addException("CreateException").addException("RemoteException");
                
                createMethod.addParameter(dataClassName, "newObject");
                ejbHome.addMethod(createMethod);

                // Insere findByPrimaryKey(PK)
                Method findByPrimaryKeyMethod = JavaSyntax.getJavaMethod();
                findByPrimaryKeyMethod.setName("findByPrimaryKey").setVisibility("public").setReturnType(ejbObject.getName()).setAbstractMethod(true).addException("FinderException").addException("RemoteException");
                // Adiciona parametro (PK)
                findByPrimaryKeyMethod.addParameter(primaryKeyName, "primaryKey");
                ejbHome.addMethod(findByPrimaryKeyMethod);

                // Insere os outros metodos tipo finder
                String[] finderIds = ModelManager.processQueryResult(manager.query(id + ".entityComponent.feature->select(f : EJBFeature | f.oclIsTypeOf(BusinessMethod))->collect(f : EJBFeature | f.oclAsType(BusinessMethod))->select(bm : BusinessMethod | bm.name.substring(1, 4) = 'find')"));
                for (String finderId : finderIds) {
                    String finderName = manager.query(finderId + ".name").replace("'", "");
                    Method method = JavaSyntax.getJavaMethod();
                    method.setVisibility("public").setName(finderName).addException("RemoteException").addException("FinderException").setAbstractMethod(true);

                    if (finderName.startsWith("findAll") || finderName.startsWith("findMany")) {
                        method.setReturnType("java.util.Collection<" + ejbObject.getName() + ">");
                    } else {
                        method.setReturnType(ejbObject.getName());
                    }

                    String[] parameterIds = ModelManager.processQueryResult(manager.query(finderId + ".parameter"));
                    for (String parameterId : parameterIds) {
                        Parameter parameter = JavaSyntax.getJavaParameter();
                        parameter.setName(manager.query(parameterId + ".name").replace("'", ""), true).setType(convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(parameterId + ".type"))[0]));
                        method.addParameter(parameter);
                    }
                    ejbHome.addMethod(method);
                }



                ejbHome.persist();

                // Criar o EntityBean correspondente
                Class entityBean = JavaSyntax.getJavaClass();
                entityBean.setDirectoryPath("generated_code/");
                entityBean.setPackagePath("app");

//                classe.addImport("java.rmi.*");
                entityBean.addImportPath("javax.naming.*");
                entityBean.addImportPath("javax.ejb.*");
                entityBean.addImportPath("java.util.*");
                entityBean.setName(ModelManager.processQueryResult(manager.query(id + ".entityComponent.name"))[0] + "Bean");
                entityBean.addClassNamesThatImplements("EntityBean");
                entityBean.setVisibility("public");

                // EJB stuff
                entityBean.addAttribute(JavaSyntax.getJavaAttribute().setName("context").setType("EntityContext").setVisibility("private"));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("setEntityContext").setVisibility("public").setReturnType("void").setCode(new String[]{"this.context = context;"}).addParameter("EntityContext", "context"));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("unsetEntityContext").setVisibility("public").setReturnType("void").setCode(new String[]{"this.context = null;"}));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbActivate").setVisibility("public").setReturnType("void"));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbPassivate").setVisibility("public").setReturnType("void"));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbRemove").setVisibility("public").setReturnType("void"));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbLoad").setVisibility("public").setReturnType("void"));
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbStore").setVisibility("public").setReturnType("void"));

                // Adicionando atributo 'key'
                Attribute primaryKeyAttribute = JavaSyntax.getJavaAttribute();
                primaryKeyAttribute.setName("key").setVisibility("private").setType(dataClassName);
                entityBean.addAttribute(primaryKeyAttribute);

                // Adicionando getter para 'key'
                Method getterPrimaryKey = JavaSyntax.getJavaMethod();
                getterPrimaryKey.setVisibility("public").setReturnType(dataClassName).setName("get" + StringUtils.upperFirstLetter(dataClassName)).setCode(new String[]{"return key;"});
                entityBean.addMethod(getterPrimaryKey);

                // Adicionando setter para 'key'
                Method setterPrimaryKey = JavaSyntax.getJavaMethod();
                setterPrimaryKey.setVisibility("public").setReturnType("void").setName("set" + StringUtils.upperFirstLetter(dataClassName)).addParameter(dataClassName, "newObj").setCode(new String[]{"this.key = newObj;"}).addException("NamingException").addException("FinderException").addException("CreateException");
                entityBean.addMethod(setterPrimaryKey);

                // Adiciona ejbCreate
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbCreate").setVisibility("public").setReturnType(primaryKeyName).addException("NamingException").addException("FinderException").addException("CreateException").setCode(new String[]{"if (newObj == null) {", "\tthrow new CreateException(\"The field 'newObj' must not be null\");", "}", "", "// TODO add additional validation code, throw CreateException if data is not valid", setterPrimaryKey.getName() + "(newObj);", "", "return null;"}).addParameter(dataClassName, "newObj"));

                // Adiciona ejbPostCreate (Ver se da pra automatizar)
                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbPostCreate").setVisibility("public").setReturnType("void").setCode(new String[]{"// TODO populate relationships here if appropriate"}).addParameter(dataClassName, "newObj"));

                // Adicionando construtor padrao
                entityBean.addConstructor(JavaSyntax.getJavaConstructor().setClassName(entityBean.getName()));

                entityBean.addMethod(JavaSyntax.getJavaMethod().setName("ejbFindByPrimaryKey").setVisibility("public").setReturnType(primaryKeyName).addException("FinderException").addParameter(primaryKeyName, "primaryKey").setCode(new String[]{"// TODO - You must decide how your persistence will work.", "return null;"}));

                // Adicionando metodos oriundos da transformacao
                for (String finderId : finderIds) {
                    String finderName = manager.query(finderId + ".name").replace("'", "");
                    Method method = JavaSyntax.getJavaMethod();
                    method.setVisibility("public");
                    if (finderName.startsWith("findAll") || finderName.startsWith("findMany")) {
                        method.setReturnType("java.util.Collection<" + primaryKeyName + ">");
                    } else {
                        method.setReturnType(primaryKeyName);
                    }
                    method.setName("ejb" + StringUtils.upperFirstLetter(finderName)).setCode(new String[]{"// TODO", "return null;"});
                    method.addException("FinderException");

                    String[] parameterIds = ModelManager.processQueryResult(manager.query(finderId + ".parameter"));
                    for (String parameterId : parameterIds) {
                        Parameter parameter = JavaSyntax.getJavaParameter();
                        parameter.setName(manager.query(parameterId + ".name").replace("'", ""), true).setType(convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(parameterId + ".type"))[0]));
                        method.addParameter(parameter);
                    }
                    entityBean.addMethod(method);
                }

                for (String businessMethodId : businessMethodIds) {
                    Method method = JavaSyntax.getJavaMethod();
                    method.setVisibility("public").setName(manager.query(businessMethodId + ".name").replace("'", ""));

                    String returnType = convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(businessMethodId + ".type"))[0]);
                    method.setReturnType(returnType);
                    if ("void".equals(returnType)) {
                        method.setCode(new String[]{"// TODO"});
                    } else {
                        method.setCode(new String[]{"// TODO", "return null;"});
                    }

                    String[] parameterIds = ModelManager.processQueryResult(manager.query(businessMethodId + ".parameter"));
                    for (String parameterId : parameterIds) {
                        Parameter parameter = JavaSyntax.getJavaParameter();
                        parameter.setName(manager.query(parameterId + ".name").replace("'", ""), true).setType(convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(parameterId + ".type"))[0]));
                        method.addParameter(parameter);
                    }
                    entityBean.addMethod(method);
                }

                entityBean.persist();
            } catch (Exception ex) {
                throw new ContractException("Can't persist an EJBEntityComponent", ex);
            }
        }
    }

    private static void generateEJBDataClass() throws ContractException {
        ModelManager manager = ModelManager.instance();
        String[] ids = ModelManager.processQueryResult(manager.query("EJBDataClass.allInstances()").replace("\n", ""));

        for (String id : ids) {
            try {

                Class dataClass = JavaSyntax.getJavaClass();
                dataClass.setDirectoryPath("generated_code/");
                dataClass.setPackagePath("app");

                dataClass.addImportPath("java.util.*");
                dataClass.setName(manager.query(id + ".name").replace("'", "") + "DataObject");
                dataClass.setVisibility("public");
                dataClass.addClassNamesThatImplements("java.io.Serializable");

                // Adicionando atributo 'key'
                String umlClassId = ModelManager.processQueryResult(manager.query("Class.allInstances()->select(c | c.name = " + id + ".name)"))[0];
                String keyClassId = ModelManager.processQueryResult(manager.query("if " + umlClassId + ".oclIsTypeOf(Class) then UMLClassToEJBKeyClass.allInstances()->select(a | a.class = " + umlClassId + ").keyClass else UMLAssociationClassToEJBKeyClass.allInstances()->select(a | a.associationClass.oclAsType(Class) = " + umlClassId + ").keyClass endif"))[0];
                String keyClassName = manager.query(keyClassId + ".name").replace("'", "");
                Attribute primaryKey = JavaSyntax.getJavaAttribute().setName("key").setType(keyClassName).setVisibility("private");
                dataClass.addAttribute(primaryKey);

                // Adicionando construtor padrao
                dataClass.addConstructor(JavaSyntax.getJavaConstructor().setClassName(dataClass.getName()));

                // Adicionando construtor com o 'key' como parametro
                Constructor constructor = JavaSyntax.getJavaConstructor();
                constructor.setClassName(dataClass.getName()).addParameter(JavaSyntax.getJavaParameter().setName("key").setType(manager.query(keyClassId + ".name").replace("'", "")));
                constructor.setCode("this.key = key;");
                dataClass.addConstructor(constructor);

                // Adicionando getter para 'key'
                Method getterPrimaryKey = JavaSyntax.getJavaMethod();
                getterPrimaryKey.setVisibility("public").setReturnType(keyClassName).setName("get" + StringUtils.upperFirstLetter(keyClassName)).setCode(new String[]{"return key;"});
                dataClass.addMethod(getterPrimaryKey);

                // Adicionando setter para 'key'
                Method setterPrimaryKey = JavaSyntax.getJavaMethod();
                setterPrimaryKey.setVisibility("public").setReturnType("void").setName("set" + StringUtils.upperFirstLetter(keyClassName)).addParameter(keyClassName, "key").setCode(new String[]{"this.key = key;"});
                dataClass.addMethod(setterPrimaryKey);

                // Insere relacionamentos (AssociationEnd) com getters e setters
                String[] associationEndIds = null;
                // 1) upper = '1'
                associationEndIds = ModelManager.processQueryResult(manager.query(id + ".feature->select(f : EJBFeature | f.oclIsTypeOf(EJBAssociationEnd))->collect(f : EJBFeature | f.oclAsType(EJBAssociationEnd))->select(ae : EJBAssociationEnd | ae.upper = '1')"));
                for (String associationEndId : associationEndIds) {
//                    String nome = this.dominio.query(idAssocEnd + ".name").replace("'", "") + tratarResultadoQuery(this.dominio.query(idAssocEnd + ".type.name"))[0];
                    String name = manager.query(associationEndId + ".name").replace("'", "");
                    boolean isDataClass = "true".equals(manager.query(associationEndId + ".type.oclIsTypeOf(EJBDataClass)").replace("'", ""));
                    String type = isDataClass ? ModelManager.processQueryResult(manager.query(associationEndId + ".type.name"))[0] + "DataObject" : ModelManager.processQueryResult(manager.query(associationEndId + ".type.name"))[0];
                    Attribute attribute = JavaSyntax.getJavaAttribute().setVisibility("private").setType(type).setName(name);
                    dataClass.addAttribute(attribute);

                    Method getter = JavaSyntax.getJavaMethod();
                    getter.setName("get" + StringUtils.upperFirstLetter(name)).setReturnType(type).setVisibility("public").setCode(new String[]{"return " + name + ";"});
                    dataClass.addMethod(getter);

                    Method setter = JavaSyntax.getJavaMethod();
                    setter.setName("set" + StringUtils.upperFirstLetter(name)).setReturnType("void").setVisibility("public").addParameter(type, name).setCode(new String[]{"this." + name + " = " + name + ";"});
                    dataClass.addMethod(setter);

                }
                // 2) upper <> '1'
                associationEndIds = ModelManager.processQueryResult(manager.query(id + ".feature->select(f : EJBFeature | f.oclIsTypeOf(EJBAssociationEnd))->collect(f : EJBFeature | f.oclAsType(EJBAssociationEnd))->select(ae : EJBAssociationEnd | ae.upper <> '1')"));
                for (String associationEndId : associationEndIds) {
                    String simpleName = manager.query(associationEndId + ".name").replace("'", "");
//                    String nomeCompleto = nomeSimples + tratarResultadoQuery(this.dominio.query(idAssocEnd + ".type.name"))[0];
                    String type = "true".equals(manager.query(associationEndId + ".type.oclIsTypeOf(EJBDataClass)").replace("'", "")) ? ModelManager.processQueryResult(manager.query(associationEndId + ".type.name"))[0] + "DataObject" : ModelManager.processQueryResult(manager.query(associationEndId + ".type.name"))[0];
                    String listType = "List<" + type + ">";
//                    Atributo atrib = SintaxeJava.getJavaAttribute().setVisibilidade("private").setTipo(tipoLista).setNome(nomeCompleto);
                    Attribute attribute = JavaSyntax.getJavaAttribute().setVisibility("private").setType(listType).setName(simpleName);
                    dataClass.addAttribute(attribute);

                    Method getter = JavaSyntax.getJavaMethod();
                    getter.setName("get" + StringUtils.upperFirstLetter(simpleName)).setReturnType(listType).setVisibility("public").setCode(new String[]{"return " + simpleName + ";"});
                    dataClass.addMethod(getter);

                    Method add = JavaSyntax.getJavaMethod();
                    add.setName("add" + StringUtils.upperFirstLetter(simpleName)).setReturnType("void").setVisibility("public").addParameter(type, simpleName).setCode(new String[]{"if (this." + simpleName + " == null) {", "\tthis." + simpleName + " = new ArrayList<" + type + ">();", "}", "this." + simpleName + ".add(" + simpleName + ");"});
                    dataClass.addMethod(add);

                    Method remove = JavaSyntax.getJavaMethod();
                    remove.setName("remove" + StringUtils.upperFirstLetter(simpleName)).setReturnType("void").setVisibility("public").addParameter(type, simpleName).setCode(new String[]{"this." + simpleName + ".remove(" + simpleName + ");"});
                    dataClass.addMethod(remove);
                }

                // Insere atributos (visibilidade = 'private') com getters e setters
                String[] attributeIds = ModelManager.processQueryResult(manager.query(id + ".feature->select(f : EJBFeature | f.oclIsTypeOf(EJBAttribute))"));
                for (String attributeId : attributeIds) {
                    String attributeName = manager.query(attributeId + ".name").replace("'", "");
                    // mudei aki

                    String attributeType = convertEjbTypeToJavaType(ModelManager.processQueryResult(manager.query(attributeId + ".type"))[0]);

                    Attribute attribute = JavaSyntax.getJavaAttribute();
                    attribute.setName(attributeName).setType(attributeType).setVisibility("private");
                    dataClass.addAttribute(attribute);

                    Method getter = JavaSyntax.getJavaMethod();
                    getter.setName("get" + StringUtils.upperFirstLetter(attributeName)).setReturnType(attributeType).setVisibility("public").setCode(new String[]{"return " + attributeName + ";"});
                    dataClass.addMethod(getter);

                    Method setter = JavaSyntax.getJavaMethod();
                    setter.setName("set" + StringUtils.upperFirstLetter(attributeName)).setReturnType("void").setVisibility("public").addParameter(attributeType, attributeName).setCode(new String[]{"this." + attributeName + " = " + attributeName + ";"});
                    dataClass.addMethod(setter);
                }

                dataClass.persist();
            } catch (Exception ex) {
                throw new ContractException("Can't persist an EJBDataClass", ex);
            }
        }
    }
}
