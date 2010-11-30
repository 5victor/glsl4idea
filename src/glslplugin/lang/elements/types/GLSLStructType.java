package glslplugin.lang.elements.types;

import glslplugin.lang.elements.declarations.GLSLDeclarator;
import glslplugin.lang.elements.declarations.GLSLTypeDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * GLSLStructType is ...
 *
 * @author Yngve Devik Hammersland
 *         Date: Feb 26, 2009
 *         Time: 11:20:35 AM
 */
public class GLSLStructType extends GLSLType {

    private GLSLTypeDefinition definition;
    private String typename;
    private Map<String, GLSLType> members = null;
    private GLSLType[] memberTypes = null;

    public GLSLStructType(GLSLTypeDefinition definition) {
        this.definition = definition;
        final GLSLDeclarator[] declarators = definition.getDeclarators();

        typename = definition.getTypeName();

        members = new HashMap<String, GLSLType>();
        memberTypes = new GLSLType[declarators.length];

        for (int i = 0; i < declarators.length; i++) {
            final GLSLDeclarator declarator = declarators[i];
            members.put(declarator.getIdentifierName(), declarator.getType());
            memberTypes[i] = declarator.getType();
        }
    }

    public String getTypename() {
        return typename;
    }

    @Override
    public GLSLTypeDefinition getDefinition() {
        return definition;
    }

    @Override
    public boolean typeEquals(GLSLType otherType) {
        if (otherType instanceof GLSLStructType) {
            GLSLStructType other = (GLSLStructType) otherType;

            // typename should be enough as only a single struct can have any given name
            // TODO: Check definition reference instead? NOTE: It may be null
            if (getTypename().equals(other.typename)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public Map<String, GLSLType> getMembers() {
        return members;
    }

    @Override
    public GLSLType[] getMemberTypes() {
        return memberTypes;
    }

    @Override
    public boolean hasMembers() {
        return true;
    }

    @Override
    public GLSLFunctionType[] getConstructors() {
        return new GLSLFunctionType[]{
                new GLSLBasicFunctionType(getTypename(), this, getMemberTypes())
        };
    }


}
