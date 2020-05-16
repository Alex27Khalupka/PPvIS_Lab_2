package lab2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TableInfo {
    private static List<Train> trainsList = new ArrayList<>();
    private static List <Object[]> trainsListArray = new ArrayList<>();

    public static void setTrainsList(List<Train> trainList) {
        trainsList = trainList;
        trainsListArray = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        for (int i=0; i<trainsList.size(); i++) {
            Object[] train = {trainsList.get(i).getNumber(),
                    trainsList.get(i).getDepartureStation(),
                    trainsList.get(i).getArrivalStation(),
                    formatter.format(trainsList.get(i).getDepartureDate()),
                    formatter.format(trainsList.get(i).getArrivalDate()),
                    trainsList.get(i).getTripTime()};
            trainsListArray.add(train);
        }
    }

    public static List<Train> getTrainsList() {
        return trainsList;
    }

    public static void setTrainsListArray(List<Object[]> lst) {
        trainsListArray = lst;
    }

    public static List<Object[]> getTrainsListArray() throws ParseException {
        return trainsListArray;
    }
}