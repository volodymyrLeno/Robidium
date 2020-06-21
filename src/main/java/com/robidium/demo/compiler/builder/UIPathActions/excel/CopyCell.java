package com.robidium.demo.compiler.builder.UIPathActions.excel;

import com.robidium.demo.compiler.builder.UIPathElement;
import com.robidium.demo.compiler.builder.variables.Clipboard;
import com.robidium.demo.compiler.entity.Action;
import org.w3c.dom.Element;

public class CopyCell extends UIPathElement {
    protected Element excelReadCell;
    protected Action action;

    protected void createResult() {
        Element excelReadCellResult = doc.createElement("ui:ExcelReadCell.Result");
        Element outArgument = doc.createElement("OutArgument");
        outArgument.setAttribute("x:TypeArguments", "x:String");
        outArgument.setTextContent("[" + Clipboard.NAME + "]");

        excelReadCell.appendChild(excelReadCellResult).appendChild(outArgument);
    }

    protected void setCopyCellAttributes() {
        excelReadCell.setAttribute("DisplayName", "Read Cell");
        excelReadCell.setAttribute("SheetName", action.getSheetName());
    }

    @Override
    public Element getDomElement() {
        return excelReadCell;
    }
}
