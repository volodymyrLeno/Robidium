package com.robidium.demo.compiler.builder.UIPathActions;

import com.robidium.demo.compiler.builder.UIPathElement;
import com.robidium.demo.compiler.builder.Variables;
import com.robidium.demo.compiler.builder.variables.Clipboard;
import com.robidium.demo.compiler.builder.variables.PreviousTarget;
import com.robidium.demo.compiler.builder.variables.Target;
import org.w3c.dom.Element;

public class InvokeCode extends UIPathElement {
    public boolean isElementPresent;
    private Element invokeCode;

    protected Element createInvokeCode(String code) {
        invokeCode = doc.createElement("ui:InvokeCode");
        invokeCode.setAttribute("Language", "CSharp");
        invokeCode.setAttribute("Code", code);
        createArgs();
        doSequence.appendChild(invokeCode);

        return invokeCode;
    }

    private void createArgs() {
        Element invokeCodeArguments = doc.createElement("ui:InvokeCode.Arguments");
        createInArgs(invokeCodeArguments);
        createInOutArguments(invokeCodeArguments);
        invokeCode.appendChild(invokeCodeArguments);
    }

    private void createInArgs(Element args) {
        Element inClipboard = doc.createElement("InArgument");
        inClipboard.setAttribute("x:TypeArguments", "x:String");
        inClipboard.setAttribute("x:Key", Clipboard.NAME);
        inClipboard.setTextContent("[" + Clipboard.NAME + "]");
        args.appendChild(inClipboard);

        Element split = doc.createElement("InArgument");
        split.setAttribute("x:TypeArguments", "s:String[]");
        split.setAttribute("x:Key", Variables.SPLIT);
        args.appendChild(split);

        Element cells = doc.createElement("InArgument");
        cells.setAttribute("x:TypeArguments", "scg:List(x:String)");
        cells.setAttribute("x:Key", Variables.CELLS);
        cells.setTextContent("[" + Variables.CELLS + "]");
        args.appendChild(cells);
    }

    private void createOutArgs(Element args) {
        Element outArgument = doc.createElement("OutArgument");
        outArgument.setAttribute("x:TypeArguments", "x:String");
        outArgument.setAttribute("x:Key", "transformed_value");
        outArgument.setTextContent("[" + Variables.TRANSFORMED_VALUE + "]");
        args.appendChild(outArgument);
    }

    private void createInOutArguments(Element args) {
        Element transformedValue = doc.createElement("InOutArgument");
        transformedValue.setAttribute("x:TypeArguments", "x:String");
        transformedValue.setAttribute("x:Key", Variables.TRANSFORMED_VALUE);
        transformedValue.setTextContent(String.format("[%s]", Variables.TRANSFORMED_VALUE));
        args.appendChild(transformedValue);

        Element prevTarget = doc.createElement("InOutArgument");
        prevTarget.setAttribute("x:TypeArguments", "x:String");
        prevTarget.setAttribute("x:Key", PreviousTarget.NAME);
        prevTarget.setTextContent(String.format("[%s]", PreviousTarget.NAME));
        args.appendChild(prevTarget);

        Element target = doc.createElement("InOutArgument");
        target.setAttribute("x:TypeArguments", "x:String");
        target.setAttribute("x:Key", Target.NAME);
        target.setTextContent(String.format("[%s]", Target.NAME));
        args.appendChild(target);
    }

    @Override
    public Element getDomElement() {
        return invokeCode;
    }
}
