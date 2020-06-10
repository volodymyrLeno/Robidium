package com.robidium.demo.compiler.builder.variables;

import com.robidium.demo.compiler.builder.ScriptBuilder;
import com.robidium.demo.compiler.transformation.semantic.DependencyService;
import org.apache.commons.text.StringEscapeUtils;
import org.w3c.dom.Element;

import java.util.stream.Collectors;

public class FD implements Variable {
    public static final String NAME = "FD";

    private Element fd;

    public FD() {
        fd = ScriptBuilder.getDoc().createElement("Variable");
        fd.setAttribute("Name", NAME);
        fd.setAttribute("x:TypeArguments", "scg:Dictionary(x:String, x:String)");
        setDefaultValue();
    }

    private void setDefaultValue() {
        String values = DependencyService.getInstance().getDependencies().stream()
                .map(dependency -> dependency.getDependerPerDependee().entrySet().stream()
                        .map(e -> String.format("{\"%s\",\"%s\"}", e.getKey(), e.getValue()))
                        .collect(Collectors.joining(",")))
                .collect(Collectors.joining(",", "{", "}"));

        Element def = ScriptBuilder.getDoc().createElement("Variable.Default");
        Element visualBasicValue = ScriptBuilder.getDoc().createElement("mva:VisualBasicValue");
        visualBasicValue.setAttribute("x:TypeArguments", "scg:Dictionary(x:String, x:String)");
        visualBasicValue.setAttribute("ExpressionText", "new Dictionary(of String, String) from " +
                StringEscapeUtils.escapeXml11(values));
        def.appendChild(visualBasicValue);
        fd.appendChild(def);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Element getDomElement() {
        return fd;
    }
}
