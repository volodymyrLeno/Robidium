package com.robidium.demo.compiler.builder.UIPathActions.excel;

import com.robidium.demo.compiler.builder.UIPathElement;
import com.robidium.demo.compiler.builder.Variables;
import com.robidium.demo.compiler.entity.Action;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerException;

public class EditRange extends UIPathElement implements ExcelRange {
    private Element writeRange;
    private Action action;

    public EditRange(Action action) {
        this.action = action;
        createWriteRange();
    }

    private void createWriteRange() {
        buildDataTable();
        writeRange = doc.createElement("ui:ExcelWriteRange");
        setAttributes(writeRange, action);
        doSequence.appendChild(writeRange);
    }

    private void buildDataTable() {
        Element buildDataTable = doc.createElement("ui:BuildDataTable");
        try {
            TableInfo tableInfo = new TableInfo(action);
            buildDataTable.setAttribute("DisplayName", "Build Data Table");
            buildDataTable.setAttribute("DataTable", "[" + Variables.DATA_TABLE + "]");
            buildDataTable.setAttribute("TableInfo", tableInfo.getTableInfo());
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        doSequence.appendChild(buildDataTable);
    }

    @Override
    public Element getDomElement() {
        return writeRange;
    }
}
