package com.robidium.demo.compiler.builder.UIPathActions.browser;

import com.robidium.demo.compiler.builder.UIPathElement;
import com.robidium.demo.compiler.entity.Action;
import org.w3c.dom.Element;

public class ClickCheckbox extends UIPathElement {
    private Element check;
    private Action action;

    public ClickCheckbox(Action action) {
        this.action = action;
        createCheck();
    }

    private void createCheck() {
        check = doc.createElement("ui:Check");
        setAttributes();
        createTarget();
        doSequence.appendChild(check);
    }

    private void setAttributes() {
        check.setAttribute("Action", "Check");
        check.setAttribute("DisplayName", "Check");
    }

    private void createTarget() {
        Element checkTarget = doc.createElement("ui:Check.Target");
        Element target = doc.createElement("ui:Target");
        target.setAttribute("Selector", "&lt;webctrl id='" + action.getTargetId() + "' tag='INPUT' /&gt;");
        target.setAttribute("WaitForReady", "COMPLETE");

        check.appendChild(checkTarget).appendChild(target);
    }

    @Override
    public Element getDomElement() {
        return check;
    }
}
