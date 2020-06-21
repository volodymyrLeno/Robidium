package com.robidium.demo.compiler.builder.UIPathActions.browser;

import com.robidium.demo.compiler.builder.UIPathElement;
import org.w3c.dom.Element;

public class Select extends UIPathElement {
    private static Element selectItem;

    public static void of(String id, String value) {
        selectItem = doc.createElement("ui:SelectItem");
        selectItem.setAttribute("DisplayName", "Select Item");
        selectItem.setAttribute("Item", String.format("[%s]", value));
        setTarget(id);
        doSequence.appendChild(selectItem);
    }

    private static void setTarget(String id) {
        Element target = doc.createElement("ui:Target");
        target.setAttribute("Selector", String.format("&lt;webctrl name='%s' /&gt;", id));
        target.setAttribute("WaitForReady", "COMPLETE");

        Element elementTarget = doc.createElement("ui:SelectItem.Target");
        elementTarget.appendChild(target);

        selectItem.appendChild(elementTarget);
    }

    @Override
    public Element getDomElement() {
        return selectItem;
    }
}
