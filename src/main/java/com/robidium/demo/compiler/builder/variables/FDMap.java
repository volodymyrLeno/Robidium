package com.robidium.demo.compiler.builder.variables;

import com.robidium.demo.compiler.builder.ScriptBuilder;
import org.w3c.dom.Element;

public class FDMap implements Variable {
    public static final String NAME = "FD";
    private static final FDMap INSTANCE = new FDMap();
    private Element dictionary;

    private FDMap() {
        dictionary = ScriptBuilder.getDoc().createElement("Variable");
        dictionary.setAttribute("Name", "FD");
        dictionary.setAttribute("x:TypeArguments", "scg:Dictionary(x:String, x:String)");
//        assignValues();
    }

    public FDMap getInstance() {
        return INSTANCE;
    }

    public void assignValues(String values) {
        Element def = ScriptBuilder.getDoc().createElement("Variable.Default");
        Element value = ScriptBuilder.getDoc().createElement("mva:VisualBasicValue");
        value.setAttribute("x:TypeArguments", "scg:Dictionary(x:String, x:String)");
        value.setAttribute("ExpressionText", "new Dictionary(Of String, String) from " + values);
//        "{{&quot;Estonia&quot;, &quot;+372&quot;}, {&quot;USA&quot;,&quot;+1&quot;}}"
        def.appendChild(value);
        dictionary.appendChild(def);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Element getDomElement() {
        return dictionary;
    }
}
