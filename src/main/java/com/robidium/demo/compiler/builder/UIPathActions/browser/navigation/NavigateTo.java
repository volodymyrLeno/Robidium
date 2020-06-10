package com.robidium.demo.compiler.builder.UIPathActions.browser.navigation;

import com.robidium.demo.compiler.builder.UIPathElement;
import com.robidium.demo.compiler.entity.Action;
import org.w3c.dom.Element;

public class NavigateTo extends UIPathElement {
    private Element navigateTo;
    private Action action;

    public NavigateTo(Action action) {
        this.action = action;
        createNavigate();
    }

    private void createNavigate() {
        navigateTo = doc.createElement("ui:NavigateTo");
        setAttributes();
        doSequence.appendChild(navigateTo);
    }

    private void setAttributes() {
        navigateTo.setAttribute("DisplayName", "Navigate To");
        navigateTo.setAttribute("Url", action.getUrl());
    }

    @Override
    public Element getDomElement() {
        return navigateTo;
    }
}
