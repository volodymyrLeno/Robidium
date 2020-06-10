package com.robidium.demo.compiler.builder.UIPathActions.excel;

import com.robidium.demo.compiler.builder.UIPathElement;
import com.robidium.demo.compiler.entity.Action;
import org.w3c.dom.Element;

public class PasteIntoRange extends UIPathElement implements ExcelRange {
    private Element writeRange;
    private Action action;

    public PasteIntoRange(Action action) {
        this.action = action;
        createPasteIntoRange();
    }

    private void createPasteIntoRange() {
        writeRange = doc.createElement("ui:ExcelWriteRange");
        setAttributes(writeRange, action);
        doSequence.appendChild(writeRange);
    }

    @Override
    public Element getDomElement() {
        return writeRange;
    }
}
