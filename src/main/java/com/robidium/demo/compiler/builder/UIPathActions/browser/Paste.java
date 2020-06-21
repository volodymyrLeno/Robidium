package com.robidium.demo.compiler.builder.UIPathActions.browser;

import com.robidium.demo.compiler.builder.UIPathElement;
import org.w3c.dom.Element;

public class Paste extends UIPathElement {
    private Element typeInto;

    protected void createPaste(String target, String value) {
        typeInto = doc.createElement("ui:TypeInto");
        setPasteAttributes(value);
        createTarget(target);
        doSequence.appendChild(typeInto);
    }

    private void createTarget(String targetValue) {
        Element typeIntoTarget = doc.createElement("ui:TypeInto.Target");
        Element target = doc.createElement("ui:Target");
        target.setAttribute("Selector", String.format("&lt;webctrl name='%s' tag='INPUT' /&gt;", targetValue));
        target.setAttribute("WaitForReady", "COMPLETE");
        typeIntoTarget.appendChild(target);
        typeInto.appendChild(typeIntoTarget);
    }

    private void setPasteAttributes(String value) {
        typeInto.setAttribute("Activate", "True");
        typeInto.setAttribute("ClickBeforeTyping", "False");
        typeInto.setAttribute("DisplayName", "Type Into 'INPUT'");
        typeInto.setAttribute("EmptyField", "False");
        typeInto.setAttribute("SendWindowMessages", "False");
        typeInto.setAttribute("SimulateType", "True");
        typeInto.setAttribute("DelayBetweenKeys", "0");
        typeInto.setAttribute("DelayBefore", "0");
        typeInto.setAttribute("DelayMS", "0");
        typeInto.setAttribute("Text", String.format("[%s]", value));
    }

    @Override
    public Element getDomElement() {
        return typeInto;
    }
}
