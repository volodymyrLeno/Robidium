package com.robidium.demo.compiler.builder.variables;

import com.robidium.demo.compiler.builder.ScriptBuilder;
import org.w3c.dom.Element;

public class PreviousTarget implements Variable {
    public static final String NAME = "prevTarget";

    private Element prevTarget;

    public PreviousTarget() {
        prevTarget = ScriptBuilder.getDoc().createElement("Variable");
        prevTarget.setAttribute("x:TypeArguments", "x:String");
        prevTarget.setAttribute("Name", NAME);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Element getDomElement() {
        return prevTarget;
    }
}
