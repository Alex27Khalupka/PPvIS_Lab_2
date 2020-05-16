package lab2.parsers;

import lab2.Train;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SAXInParser extends DefaultHandler {

    Report report;

    public List<Train> getParsedData(){
        return report.trainList;
    }

    public SAXInParser() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        MyHandler handler = new MyHandler();
        xmlReader.setContentHandler(handler);
        xmlReader.parse("src/lab2/files/trains.xml");

        report = handler.getReport();
    }

    private static class MyHandler extends DefaultHandler {

        private Report report;
        private String currentElement;
        private Train currentTrain;
        private Station currentStation;

        Report getReport() {
            return this.report;
        }

        @Override
        public void startDocument() throws SAXException {
            System.out.println("Starting XML parsing...");
        }

        @Override
        public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) throws SAXException {
            currentElement = qName;

            switch (currentElement) {
                case ("Trains"): {
                    report = new Report();
                    report.trainList = new ArrayList<>();
                }
                break;

                case ("train"): {
                    currentTrain = new Train();
                    currentTrain.setNumber(Integer.parseInt(attributes.getValue("number")));
                }
                break;

                case ("station"): {
                    currentStation = new Station();
                }
                break;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String text = new String(ch, start, length);

            if (text.contains("<") | currentElement == null) {
                return;
            }

            switch (currentElement) {
                case "name": {
                    //System.out.println(text);
                    currentStation.Name = text;
                } break;
                case "departuretime": {
                    try {
                        //System.out.println(text + "this is date");
                        currentStation.date = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(text);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    currentStation.type = "departure";
                } break;
                case "arrivaltime": {
                    try {
                        currentStation.date = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(text);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    currentStation.type = "arrival";
                } break;
                default:{

                }
            }
        }

        @Override
        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            switch (qName) {
                case "station": {
                    if (currentStation.type.equals("departure")) {
                        currentTrain.setDepartureStation(currentStation.Name);
                        currentTrain.setDepartureDate(currentStation.date);
                    } else {
                        currentTrain.setArrivalStation(currentStation.Name);
                        currentTrain.setArrivalDate(currentStation.date);
                    }
                }
                break;
                case "train": {
                    report.trainList.add(currentTrain);
                }
                break;
            }
            currentElement = null;
        }

        @Override
        public void endDocument() {
            System.out.println("Stop parse XML...");
        }
    }
}
class Report{
    List<Train> trainList;
}

class Station{
    String Name;
    Date date;
    String type;
}