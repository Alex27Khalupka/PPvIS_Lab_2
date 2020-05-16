package lab2.parsers;

import lab2.Train;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DOMInParser {
    private static final String xmlFilePath = "src/lab2/files/trains_created.xml";
    private static final String AllTrains_TAG = "Trains";
    private static final String Train_TAG = "train";
    private static final String Station_TAG = "station";
    private static final String Name_TAG = "name";
    private static final String DepartureTime_TAG = "departuretime";
    private static final String ArrivalTime_TAG = "arrivaltime";
    private static final String TripTime_TAG = "triptime";
    private static final String Number_ATTRRIBUTE = "number";
    private static final String StationList_TAG = "stations";


    public DOMInParser() {
    }

    public void parse(List<Train> trainList) throws ParserConfigurationException, IOException, SAXException, TransformerException{


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement(AllTrains_TAG);
        document.appendChild(root);

        for (Train currentTrain : trainList) {


            //train element
            Element train = document.createElement(Train_TAG);
            Attr attr = document.createAttribute(Number_ATTRRIBUTE);
            // number of train
            attr.setValue(currentTrain.getNumber() + "");
            train.setAttributeNode(attr);
            root.appendChild(train);

            //stations
            Element Stations = document.createElement(StationList_TAG);
            train.appendChild(Stations);

            // departure station
            Element dStation = document.createElement(Station_TAG);
            Stations.appendChild(dStation);

            Element dName = document.createElement(Name_TAG);
            Element departureTime = document.createElement(DepartureTime_TAG);
            dName.appendChild(document.createTextNode(currentTrain.getDepartureStation()));
            departureTime.appendChild(document.createTextNode(formatter.format(currentTrain.getDepartureDate())));

            dStation.appendChild(dName);
            dStation.appendChild(departureTime);

            // arrival station
            Element aStation = document.createElement(Station_TAG);
            Stations.appendChild(aStation);

            Element aName = document.createElement(Name_TAG);
            Element arrivalTime = document.createElement(ArrivalTime_TAG);
            aName.appendChild(document.createTextNode(currentTrain.getArrivalStation()));
            arrivalTime.appendChild(document.createTextNode(formatter.format(currentTrain.getArrivalDate())));

            aStation.appendChild(aName);
            aStation.appendChild(arrivalTime);

            Element tripTime = document.createElement(TripTime_TAG);
            train.appendChild(tripTime);

            tripTime.appendChild(document.createTextNode(currentTrain.getTripTime()));

        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath));

        transformer.transform(domSource, streamResult);
        System.out.println("XML File created");
    }

}
