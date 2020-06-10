package com.robidium.demo.compiler.builder;

import com.robidium.demo.compiler.builder.variables.Variable;
import org.w3c.dom.Element;

import java.util.List;

public class Variables extends UIPathElement {
    public static final String DATA_TABLE = "dataTable";
    public static final String BROWSER = "currentBrowser";
    public static final String TRANSFORMED_VALUE = "transformed_value";
    public static final String SPLIT = "split";
    public static final String CELLS = "cells";

    protected Element variables;

    public Variables() {
        variables = doc.createElement("Sequence.Variables");

        createCurrentBrowserVariable();
        createDataTableVariable();
        createTransformedValue();
        createCellsVariable();
    }

    public void init(List<Variable> vars) {
        vars.forEach(v -> variables.appendChild(v.getDomElement()));
    }

    private void createCurrentBrowserVariable() {
        Element currentBrowserVariable = doc.createElement("Variable");
        currentBrowserVariable.setAttribute("Name", BROWSER);
        currentBrowserVariable.setAttribute("x:TypeArguments", "ui:Browser");

        variables.appendChild(currentBrowserVariable);
    }

    private void createDataTableVariable() {
        Element dataTableVariable = doc.createElement("Variable");
        dataTableVariable.setAttribute("Name", DATA_TABLE);
        dataTableVariable.setAttribute("x:TypeArguments", "sd:DataTable");

        variables.appendChild(dataTableVariable);
    }

    private void createTransformedValue() {
        Element transformedValue = doc.createElement("Variable");
        transformedValue.setAttribute("Name", TRANSFORMED_VALUE);
        transformedValue.setAttribute("x:TypeArguments", "x:String");

        variables.appendChild(transformedValue);
    }

    private void createCellsVariable() {
        Element cells = doc.createElement("Variable");
        cells.setAttribute("x:TypeArguments", "scg:List(x:String)");
        cells.setAttribute("Name", Variables.CELLS);
        cells.setAttribute("Default", "[New List(Of String)]");

        variables.appendChild(cells);
    }

    @Override
    public Element getDomElement() {
        return variables;
    }
}
