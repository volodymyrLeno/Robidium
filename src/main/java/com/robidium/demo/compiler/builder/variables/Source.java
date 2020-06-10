package com.robidium.demo.compiler.builder.variables;

import com.robidium.demo.compiler.builder.ScriptBuilder;
import org.w3c.dom.Element;

public class Source implements Variable {
    public static final String NAME = "source";

    private Element source;

    public Source() {
        source = ScriptBuilder.getDoc().createElement("Variable");
        source.setAttribute("x:TypeArguments", "x:String");
        source.setAttribute("Name", NAME);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Element getDomElement() {
        return source;
    }
}
