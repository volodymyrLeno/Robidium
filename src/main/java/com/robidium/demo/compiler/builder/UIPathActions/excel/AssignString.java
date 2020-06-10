package com.robidium.demo.compiler.builder.UIPathActions.excel;

import com.robidium.demo.compiler.builder.UIPathElement;
import com.robidium.demo.compiler.builder.variables.Variable;
import org.w3c.dom.Element;

public class AssignString extends UIPathElement {
    private Element assign;

    public AssignString(String context, Variable variable) {
        assign = doc.createElement("Assign");
        assign.appendChild(getOutArgument(variable));
        assign.appendChild(getInArgument(context));
        doSequence.appendChild(assign);
    }

    private Element getOutArgument(Variable variable) {
        Element assignTo = doc.createElement("Assign.To");
        Element outArgument = doc.createElement("OutArgument");
        outArgument.setAttribute("x:TypeArguments", "x:String");
        outArgument.setTextContent(String.format("[%s]", variable.getName()));
        assignTo.appendChild(outArgument);

        return assignTo;
    }

    private Element getInArgument(String context) {
        Element assignValue = doc.createElement("Assign.Value");
        Element inArgument = doc.createElement("InArgument");
        inArgument.setAttribute("x:TypeArguments", "x:String");
        inArgument.setTextContent(context);
        assignValue.appendChild(inArgument);

        return assignValue;
    }

    @Override
    public Element getDomElement() {
        return assign;
    }
}
