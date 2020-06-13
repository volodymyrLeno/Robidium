package com.robidium.demo.compiler.builder;

import com.robidium.demo.compiler.builder.UIPathActions.SourceSwitchController;
import com.robidium.demo.compiler.builder.UIPathActions.browser.navigation.ChromeScope;
import com.robidium.demo.compiler.builder.UIPathActions.browser.navigation.OpenBrowser;
import com.robidium.demo.compiler.builder.UIPathActions.excel.ExcelScope;
import com.robidium.demo.compiler.builder.variables.*;
import lombok.Getter;
import org.apache.commons.text.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ScriptBuilder {
    private static Document doc;
    private MainSequence mainSequence;
    private Element doSequence;

    @Getter
    private Source source;
    @Getter
    private Target target;
    @Getter
    private SourceSwitchController sourceSwitchController;

    public ScriptBuilder() {
        createNewDoc();

        sourceSwitchController = new SourceSwitchController();

        Activity activity = new Activity();
        Imports imports = new Imports();
        Variables variables = new Variables();
        source = new Source();
        target = new Target();
        variables.init(Arrays.asList(new Clipboard(), source, target,
                new PreviousTarget(), new FD(), new ValuePerIndex()));
        Arguments arguments = new Arguments();
        mainSequence = new MainSequence();

        activity.appendChild(imports);
        activity.appendChild(arguments);
        activity.appendChild(mainSequence).appendChild(variables);
    }

    public static Document getDoc() {
        return doc;
    }

    public static void writeScript(String patternId) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            Files.createDirectories(Paths.get("scripts"));
            BufferedWriter w = new BufferedWriter(new FileWriter(String.format("scripts/script_%s.xaml", patternId)));
            w.write(StringEscapeUtils.unescapeXml(output));
            w.close();
            System.out.println("File saved!");
        } catch (TransformerException | IOException e) {
            e.printStackTrace();
        }
    }

    public void createOpenBrowser(String url) {
        Element doSequence = createDoSequence();
        OpenBrowser openBrowser = new OpenBrowser(url);
        openBrowser.getActivityAction().appendChild(doSequence);
        mainSequence.appendChild(openBrowser);
    }

    public void createExcelScope(String workBookName) {
        Element doSequence = createDoSequence();
        ExcelScope excelScope = new ExcelScope(workBookName);
        excelScope.getActivityAction().appendChild(doSequence);
        mainSequence.appendChild(excelScope);
    }

    private void createBrowserScope() {
        Element doSequence = createDoSequence();
        ChromeScope chromeScope = new ChromeScope();
        chromeScope.getActivityAction().appendChild(doSequence);
        mainSequence.appendChild(chromeScope);
    }

    private void createNewDoc() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            UIPathElement.doc = doc;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Element createDoSequence() {
        doSequence = doc.createElement("Sequence");
        doSequence.setAttribute("DisplayName", "Do");
        UIPathElement.setDoSequence(doSequence);

        return doSequence;
    }

    public void createNewScope(String targetApp, String filePath) {
        switch (targetApp) {
            case "Excel":
                createExcelScope(filePath);
                break;
            case "Chrome":
                createBrowserScope();
                break;
            default:
                throw new IllegalArgumentException("Wrong application scope!");
        }
    }

    public void setDoSequence(Element doSequence) {
        this.doSequence = doSequence;
    }

    public MainSequence getMainSequence() {
        return mainSequence;
    }
}