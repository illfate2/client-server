package client.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class SAXParser extends DefaultHandler {
    private ArrayList<StudentInfo> patients = new ArrayList<>();
    private String thisElement = "";
    private String name;
    private Integer group;
    private Integer byIllness;
    private Integer anotherReason;
    private Integer withoutReason;
    private int readCounter = 0;

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        thisElement = qName;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        switch (thisElement) {
            case "name":
                name = new String(ch, start, length);
                break;
            case "group":
                group = Integer.parseInt(new String(ch, start, length));
                break;
            case "byIllness":
                byIllness = Integer.parseInt(new String(ch, start, length));
                break;
            case "anotherReason":
                anotherReason = Integer.parseInt(new String(ch, start, length));
                break;
            case "withoutReason":
                withoutReason = Integer.parseInt(new String(ch, start, length));
                break;
        }
    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (!thisElement.equals("tableDate")) {
            readCounter++;
        }
        if (readCounter == 5) {
            patients.add(new StudentInfo(name, group, byIllness, anotherReason, withoutReason));
            readCounter = 0;
        }
        thisElement = "";
    }

    public ArrayList<StudentInfo> getStudents() {
        return patients;
    }
}
