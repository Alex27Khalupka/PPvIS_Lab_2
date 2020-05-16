package lab2;


import lab2.parsers.SAXInParser;


import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
        SAXInParser parser = new SAXInParser();
        List<Train> trainList = parser.getParsedData();

        TableInfo.setTrainsList(trainList);
        MainPanel.createAndShowGUI();
    }
}
