package com.robidium.demo.compiler.builder.variables;

import com.robidium.demo.compiler.builder.ScriptBuilder;
import org.w3c.dom.Element;

public class ValuePerIndex implements Variable {

    public static final String NAME = "valuePerIndex";

    private Element valuePerIndex;

    public ValuePerIndex() {
        valuePerIndex = ScriptBuilder.getDoc().createElement("Variable");
        valuePerIndex.setAttribute("Name", NAME);
        valuePerIndex.setAttribute("x:TypeArguments", "scg:Dictionary(x:String, x:String)");
        setDefaultValue();
    }

    private void setDefaultValue() {
        Element def = ScriptBuilder.getDoc().createElement("Variable.Default");
        Element visualBasicValue = ScriptBuilder.getDoc().createElement("mva:VisualBasicValue");
        visualBasicValue.setAttribute("x:TypeArguments", "scg:Dictionary(x:String, x:String)");
        visualBasicValue.setAttribute("ExpressionText", "new Dictionary(of String, String)");
        def.appendChild(visualBasicValue);
        valuePerIndex.appendChild(def);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Element getDomElement() {
        return valuePerIndex;
    }
}
