package com.robidium.demo.compiler.builder.variables;

import com.robidium.demo.compiler.builder.ScriptBuilder;
import org.w3c.dom.Element;

public class Target implements Variable {
    public static final String NAME = "target";

    private Element target;

    public Target() {
        target = ScriptBuilder.getDoc().createElement("Variable");
        target.setAttribute("x:TypeArguments", "x:String");
        target.setAttribute("Name", NAME);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Element getDomElement() {
        return target;
    }
}
