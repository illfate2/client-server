package client.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class DOMParser {

    public void parse(List<StudentInfo> tableData, File file) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.newDocument();
        Element docRootElement = document.createElement("tableData");

        for (int index = 0; index < tableData.size(); index++) {
            docRootElement.appendChild(addPlayerToDocument(index, tableData.get(index), document));
        }

        document.appendChild(docRootElement);
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(file);
        saveDataInFile(source, result);
    }

    private Element addPlayerToDocument(int index, StudentInfo student, Document document) {

        Element patientItem = document.createElement("student");
        patientItem.setAttribute("id", Integer.toString(index));
        document.appendChild(patientItem);

        Element fullName = document.createElement("name");
        fullName.appendChild(document.createTextNode(student.getName()));
        patientItem.appendChild(fullName);

        Element address = document.createElement("group");
        address.appendChild(document.createTextNode(student.getGroup().toString()));
        patientItem.appendChild(address);

        Element birthDate = document.createElement("byIllness");
        birthDate.appendChild(document.createTextNode(student.getByIllness().toString()));
        patientItem.appendChild(birthDate);

        Element receiptDate = document.createElement("anotherReason");
        receiptDate.appendChild(document.createTextNode(student.getAnotherReason().toString()));
        patientItem.appendChild(receiptDate);

        Element doctorFullName = document.createElement("withoutReason");
        doctorFullName.appendChild(document.createTextNode(student.getWithoutReason().toString()));
        patientItem.appendChild(doctorFullName);

        return patientItem;
    }

    private void saveDataInFile(DOMSource source, StreamResult result) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);
    }
}
