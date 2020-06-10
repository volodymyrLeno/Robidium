package com.robidium.demo.compiler.builder.UIPathActions.imperative;

import com.robidium.demo.compiler.builder.UIPathActions.InvokeCode;
import com.robidium.demo.compiler.transformation.synthetic.ActionDataTransformer;
import com.robidium.demo.compiler.transformation.synthetic.DataTransformer;
import com.robidium.demo.compiler.transformation.synthetic.Functions;
import org.apache.commons.text.StringEscapeUtils;
import org.w3c.dom.Element;

public class ImperativeInvokeCode extends InvokeCode {

    public ImperativeInvokeCode(String code, String condition, String input) {
        code = Functions.getImperativeNewListFunction() + code;
        Element invokeCode = createInvokeCode(StringEscapeUtils.escapeXml11(code).replaceAll("\n", "&#xD;&#xA;"));
        invokeCode.setAttribute("x:Key", condition);
    }

    public ImperativeInvokeCode(String selector, String input) {
        DataTransformer dataTransformer = new ActionDataTransformer();

        if (dataTransformer.isDataTransformationPresent(selector)) {
            isElementPresent = true;
            String code = generateCode(selector, dataTransformer, input);
            createInvokeCode(code);
        }
    }

    private String generateCode(String selector, DataTransformer dataTransformer, String input) {
        String code = dataTransformer.createTransformation(selector);
//        code = Functions.getImperativeNewListFunction(input) + code;
        return StringEscapeUtils.escapeXml11(code).replaceAll("\n", "&#xD;&#xA;");
    }
}
