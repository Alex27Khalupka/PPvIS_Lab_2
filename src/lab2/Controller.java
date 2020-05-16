package lab2;

import lab2.parsers.DOMInParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {

    public static List<Train> generateTable(List<Train> trainsList) {
        return trainsList;
    }

    public static String[] generateTableHeader() {

        return new String[]{"Train", "Departure station", "Arrival station", "Departure date and time", "Arrival date and time", "Trip time"};
    }


    public static List<Train> saveInfo() throws ParserConfigurationException, ParseException, TransformerException, SAXException, IOException {
        DOMInParser parser = new DOMInParser();
        List<Train> trainsList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        for (int i = 0; i < TableInfo.getTrainsList().size(); i++){
            //Date departure = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse()

            Train currentTrain = new Train(TableInfo.getTrainsList().get(i).getNumber(),
                                           TableInfo.getTrainsList().get(i).getDepartureStation(),
                                           TableInfo.getTrainsList().get(i).getArrivalStation(),
                                           TableInfo.getTrainsList().get(i).getDepartureDate(),
                                           TableInfo.getTrainsList().get(i).getArrivalDate());

            currentTrain.getTripTime();
            trainsList.add(currentTrain);

        }
        parser.parse(trainsList);
        System.out.println("Document saved");

        return trainsList;
    }

    public static void setValue(int row, int column, Object value) throws ParseException {
        switch (column){
            case 0:{
                TableInfo.getTrainsList().get(row).setNumber((int) value);
            }break;
            case 1:{
                TableInfo.getTrainsList().get(row).setDepartureStation((String) value);
            }break;
            case 2:{
                TableInfo.getTrainsList().get(row).setArrivalStation((String) value);
            }break;
            case 3:{
                Date date = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse((String) value);
                TableInfo.getTrainsList().get(row).setDepartureDate(date);
            }break;
            case 4:{
                Date date = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse((String) value);
                TableInfo.getTrainsList().get(row).setArrivalDate(date);
            }break;
        }
    }

    public static int trainNumberDelete(int trainNumber) throws ParseException {
        List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
        int countDeletedRows = 0;
        for (int i = 0; i < trainList.size(); i++) {
            if (trainNumber == (int) trainList.get(i)[0]) {
                trainList.remove(i);
                countDeletedRows++;
                i--;
            }
        }
        TableInfo.setTrainsListArray(trainList);
        return countDeletedRows;
    }

    public static int departureStationDelete(String stationName) throws ParseException {
        List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
        int countDeletedRows = 0;
        for (int i = 0; i < trainList.size(); i++) {
            if (stationName.equals(trainList.get(i)[1])) {
                trainList.remove(i);
                countDeletedRows++;
                i--;
            }
        }
        TableInfo.setTrainsListArray(trainList);
        return countDeletedRows;
    }

    public static int arrivalStationDelete(String stationName) throws ParseException {
        List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
        int countDeletedRows = 0;
        for (int i = 0; i < trainList.size(); i++) {
            if (stationName.equals(trainList.get(i)[2])) {
                trainList.remove(i);
                countDeletedRows++;
                i--;
            }
        }
        TableInfo.setTrainsListArray(trainList);
        return countDeletedRows;
    }

    public static int tripTimeDelete(String tripTime) throws ParseException {
        List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
        int countDeletedRows = 0;
        for (int i = 0; i < trainList.size(); i++) {
            if (tripTime.equals(trainList.get(i)[5])) {
                trainList.remove(i);
                countDeletedRows++;
                i--;
            }
        }
        TableInfo.setTrainsListArray(trainList);
        return countDeletedRows;
    }

    public static int departureDateDelete(Date departureDate) throws ParseException {
        List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        int countDeletedRows = 0;
        for (int i = 0; i < trainList.size(); i++) {
            Date currentDate = new SimpleDateFormat("yyyy/MM/dd").parse((String) trainList.get(i)[3]);
            if (formatter.format(departureDate).equals(formatter.format(currentDate))) {
                trainList.remove(i);
                countDeletedRows++;
                i--;
            }
        }
        TableInfo.setTrainsListArray(trainList);
        return countDeletedRows;
    }

    public static int departureTimeDelete(Date lowerTime, Date upperTime) throws ParseException {
        List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        int countDeletedRows = 0;
        for (int i = 0; i < trainList.size(); i++) {
            Date currentDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse((String) trainList.get(i)[3]);

            String currentTime = getTime(formatter.format(currentDate));

            Date currentTimeCompare = new SimpleDateFormat("HH:mm").parse(currentTime);

            if (currentTimeCompare.compareTo(lowerTime) >= 0 && currentTimeCompare.compareTo(upperTime) <= 0) {
                trainList.remove(i);
                countDeletedRows++;
                i--;
            }
        }
        TableInfo.setTrainsListArray(trainList);
        return countDeletedRows;
    }

    public static int arrivalTimeDelete(Date lowerTime, Date upperTime) throws ParseException {
        List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        int countDeletedRows = 0;
        for (int i = 0; i < trainList.size(); i++) {
            Date currentDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse((String) trainList.get(i)[4]);

            String currentTime = getTime(formatter.format(currentDate));

            Date currentTimeCompare = new SimpleDateFormat("HH:mm").parse(currentTime);

            if (currentTimeCompare.compareTo(lowerTime) >= 0 && currentTimeCompare.compareTo(upperTime) <= 0) {
                trainList.remove(i);
                countDeletedRows++;
                i--;
            }
        }
        TableInfo.setTrainsListArray(trainList);
        return countDeletedRows;
    }

    public static List<Object[]> departureDateSearch(Date departureDate) throws ParseException {
    List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    for (int i = 0; i < trainList.size(); i++) {
        Date currentDate = new SimpleDateFormat("yyyy/MM/dd").parse((String) trainList.get(i)[3]);
        if (!(formatter.format(departureDate)).equals(formatter.format(currentDate))) {
            trainList.remove(i);
            i--;
        }
    }
    return trainList;
}

    public static List<Object[]> trainNumberSearch(int trainNumber) throws ParseException {
        List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
        for (int i = 0; i < trainList.size(); i++) {
            if (trainNumber != (int) trainList.get(i)[0]) {
                trainList.remove(i);

                i--;
            }
        }
        return trainList;
    }

    public static List<Object[]> departureTimeSearch(Date lowerTime, Date upperTime) throws ParseException {
        List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        for (int i = 0; i < trainList.size(); i++) {
            Date currentDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse((String) trainList.get(i)[3]);

            String currentTime = getTime(formatter.format(currentDate));

            Date currentTimeCompare = new SimpleDateFormat("HH:mm").parse(currentTime);

            if (currentTimeCompare.compareTo(lowerTime) < 0 || currentTimeCompare.compareTo(upperTime) > 0) {
                System.out.println(currentTimeCompare);
                trainList.remove(i);
                i--;
            }
        }
        return trainList;
    }

    public static List<Object[]> arrivalTimeSearch(Date lowerTime, Date upperTime) throws ParseException {
        List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        for (int i = 0; i < trainList.size(); i++) {
            Date currentDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse((String) trainList.get(i)[4]);

            String currentTime = getTime(formatter.format(currentDate));

            Date currentTimeCompare = new SimpleDateFormat("HH:mm").parse(currentTime);

            if (currentTimeCompare.compareTo(lowerTime) < 0 || currentTimeCompare.compareTo(upperTime) > 0) {
                System.out.println(currentTimeCompare);
                trainList.remove(i);
                i--;
            }
        }
        return trainList;
    }

    public static List<Object[]> departureStationSearch(String stationName) throws ParseException {
        List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
        for (int i = 0; i < trainList.size(); i++) {
            if (!(stationName.equals(trainList.get(i)[1]))) {
                trainList.remove(i);
                i--;
            }
        }
        return trainList;
    }

    public static List<Object[]> arrivalStationSearch(String stationName) throws ParseException {
        List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
        for (int i = 0; i < trainList.size(); i++) {
            if (!(stationName.equals(trainList.get(i)[2]))) {
                trainList.remove(i);
                i--;
            }
        }
        return trainList;
    }

    public static List<Object[]> tripTimeSearch(String stationName) throws ParseException {
        List<Object[]> trainList = new ArrayList<>(TableInfo.getTrainsListArray());
        for (int i = 0; i < trainList.size(); i++) {
            if (!(stationName.equals(trainList.get(i)[5]))) {
                trainList.remove(i);
                i--;
            }
        }
        return trainList;
    }

    private static String getTime(String str){
        return str.substring(11, 15);
    }


}