package com.robidium.demo.compiler.builder.UIPathActions.imperative;

import com.robidium.demo.compiler.builder.Arguments;
import com.robidium.demo.compiler.builder.UIPathActions.excel.CopyCell;
import com.robidium.demo.compiler.entity.Action;
import org.w3c.dom.Element;

public class ImperativeCopyCell extends CopyCell {

    public ImperativeCopyCell(Action action) {
        this.action = action;
        createCopyCell(doSequence);
        setCellAttribute();
    }

    protected void createCopyCell(Element doSequence) {
        excelReadCell = doc.createElement("ui:ExcelReadCell");
        setCopyCellAttributes();
        createResult();
        doSequence.appendChild(excelReadCell);
    }

    private void setCellAttribute() {
        String id = action.getTargetId();

        if (id.matches("[a-zA-Z]+")) {
            id = String.format("[&quot;%s&quot; + %s]", id, Arguments.ROW);
        } else if (id.matches("\\d+")) {
            id = String.format("[%s + &quot;%s&quot;]", Arguments.COLUMN, id);
        }

        excelReadCell.setAttribute("Cell", id);
    }
}
