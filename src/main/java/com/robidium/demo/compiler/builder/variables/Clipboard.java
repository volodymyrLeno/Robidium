package com.robidium.demo.compiler.builder.variables;

import com.robidium.demo.compiler.builder.ScriptBuilder;
import org.w3c.dom.Element;

public class Clipboard implements Variable {
    public static final String NAME = "clipboard";

    private Element clipboardVariable;

    public Clipboard() {
        clipboardVariable = ScriptBuilder.getDoc().createElement("Variable");
        clipboardVariable.setAttribute("Name", NAME);
        clipboardVariable.setAttribute("x:TypeArguments", "x:String");
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Element getDomElement() {
        return clipboardVariable;
    }
}
