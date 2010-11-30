package glslplugin.structureview;

import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.util.Icons;
import glslplugin.GLSLSupportLoader;
import glslplugin.lang.elements.declarations.GLSLDeclarator;
import glslplugin.lang.elements.types.GLSLQualifiedType;
import glslplugin.lang.elements.types.GLSLTypeQualifier;

import javax.swing.*;

/**
 * Copyright (c) 2008. Jean-Paul Balabanian
 * User: jeanpaul
 * Date: 30.jan.2009
 * Time: 09:48:23
 */
class GLSLPresentation implements ItemPresentation {
    public static final Icon FOLDER_OPEN = Icons.DIRECTORY_OPEN_ICON;
    public static final Icon FOLDER_CLOSED = Icons.DIRECTORY_CLOSED_ICON;
    public static final Icon FIELD = Icons.FIELD_ICON;
    public static final Icon PROTOTYPE = Icons.ABSTRACT_METHOD_ICON;
    public static final Icon FUNCTION = Icons.METHOD_ICON;
    public static final Icon STRUCT = Icons.ANNOTATION_TYPE_ICON;

    private String name;
    private Icon openIcon = null;
    private Icon icon = null;

    public GLSLPresentation(String name) {
        this.name = name;
    }

    public String getPresentableText() {
        return name;
    }

    public String getLocationString() {
        return null;
    }

    public Icon getIcon(boolean open) {
        if (open && openIcon != null) {
            return openIcon;
        }

        return icon;
    }

    public TextAttributesKey getTextAttributesKey() {
        return null;
    }

    private void setOpenIcon(Icon icon) {
        this.openIcon = icon;
    }

    private void setIcon(Icon icon) {
        this.icon = icon;
    }

    public static GLSLPresentation createMethodPresentation(String name, String... parameters) {
        GLSLPresentation presentation = new GLSLPresentation(name + " " + prettyPrint(parameters));
        presentation.setIcon(FUNCTION);
        return presentation;
    }

    public static GLSLPresentation createFolderPresentation(String name) {
        GLSLPresentation presentation = new GLSLPresentation(name);
        presentation.setIcon(FOLDER_CLOSED);
        presentation.setOpenIcon(FOLDER_OPEN);
        return presentation;
    }

    public static GLSLPresentation createFilePresentation(String name) {
        GLSLPresentation presentation = new GLSLPresentation(name);
        presentation.setIcon(GLSLSupportLoader.GLSL.getIcon());
        return presentation;
    }

    public static GLSLPresentation createStructPresentation(String structName) {
        GLSLPresentation presentation = new GLSLPresentation(structName);
        presentation.setIcon(STRUCT);
        return presentation;
    }

    public static GLSLPresentation createFieldPresentation(String type, GLSLDeclarator[] declarators) {
        String dec = prettyPrint(stringify(declarators, new Stringifyer<GLSLDeclarator>() {
            public String stringify(GLSLDeclarator glslDeclarator) {
                return glslDeclarator.getIdentifier().getIdentifierName();
            }
        }));
        GLSLPresentation presentation = new GLSLPresentation(dec + " : " + type);
        presentation.setIcon(FIELD);
        return presentation;
    }

    private static String prettyPrint(String[] names) {
        String result = "";
        if (names.length > 0) {
            for (int i = 0; i < names.length - 1; i++) {
                result += names[i] + ", ";
            }
            result += names[names.length - 1];
        }

        return result;
    }

    public static GLSLPresentation createPrototypePresentation(String name) {
        GLSLPresentation presentation = new GLSLPresentation(name);
        presentation.setIcon(PROTOTYPE);
        return presentation;
    }

    public static GLSLPresentation createFieldPresentation(GLSLDeclarator declarator) {
        String result = "";

        GLSLQualifiedType type = declarator.getQualifiedType();
        GLSLTypeQualifier[] qualifiers = type.getQualifiers();

        if (qualifiers.length > 0) {
            result += prettyPrint(stringify(qualifiers, new Stringifyer<GLSLTypeQualifier>() {
                public String stringify(GLSLTypeQualifier glslQualifier) {
                    return glslQualifier.toString();
                }
            }));
            result += " ";
        }

        result += declarator.getIdentifierName();

        result += " : ";
        result += type.getType().getTypename();

        GLSLPresentation presentation = new GLSLPresentation(result);
        presentation.setIcon(FIELD);
        return presentation;

    }

    private static <T> String[] stringify(T[] array, Stringifyer<T> stringifyer) {
        String[] result = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            result[i] = stringifyer.stringify(array[i]);
        }

        return result;
    }

    private interface Stringifyer<T> {
        String stringify(T t);
    }
}
