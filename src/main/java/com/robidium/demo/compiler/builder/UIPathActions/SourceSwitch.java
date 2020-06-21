package com.robidium.demo.compiler.builder.UIPathActions;

import com.robidium.demo.compiler.builder.UIPathElement;
import com.robidium.demo.compiler.builder.variables.Source;
import org.w3c.dom.Element;

public class SourceSwitch extends UIPathElement {
    private Element sourceSwitch;

    public SourceSwitch() {
        sourceSwitch = doc.createElement("Switch");
        setAttributes();
        doSequence.appendChild(sourceSwitch);
    }

    private void setAttributes() {
        sourceSwitch.setAttribute("DisplayName", "Source Switch");
        sourceSwitch.setAttribute("x:TypeArguments", "x:String");
        sourceSwitch.setAttribute("Expression", String.format("[%s]", Source.NAME));
    }

    @Override
    public Element getDomElement() {
        return sourceSwitch;
    }
}
