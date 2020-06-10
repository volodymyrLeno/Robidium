package com.robidium.demo.compiler.builder.UIPathActions;

import com.robidium.demo.compiler.builder.UIPathElement;
import org.w3c.dom.Element;

public class AddToDictionary extends UIPathElement {
    private Element addToDictionary;

    public AddToDictionary(String dictionary, String key, String value) {
        addToDictionary = doc.createElement("maes:AddToDictionary");
        addToDictionary.setAttribute("DisplayName", "Add to dictionary");
        addToDictionary.setAttribute("x:TypeArguments", "x:String, x:String");
        addToDictionary.setAttribute("Dictionary", String.format("[%s]", dictionary));
        addToDictionary.setAttribute("Key", key);
        addToDictionary.setAttribute("Value", String.format("[%s]", value));
        doSequence.appendChild(addToDictionary);
    }

    @Override
    public Element getDomElement() {
        return addToDictionary;
    }
}
