package lab2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Train {

    private int Number;
    private String departureStation;
    private String arrivalStation;
    private Date departureDate;
    private Date arrivalDate;
    private String tripTime = null;

    public Train(){

    }

    public Train(int Number, String departureStation, String arrivalStation, Date departureDate, Date arrivalDate) throws ParseException {
        this.setNumber(Number);
        this.setDepartureStation(departureStation);
        this.setArrivalStation(arrivalStation);
        this.setDepartureDate(departureDate);
        this.setArrivalDate(arrivalDate);
    }

    public String calculateTripTime(Date departureDate, Date arrivalDate) throws ParseException {
        long diff = arrivalDate.getTime() - departureDate.getTime();
        long days = (diff / (1000*60*60*24));
        diff -= days*1000*60*60*24;
        long hours = (diff / (1000*60*60));
        diff -= hours*1000*60*60;
        long minutes = (diff / (1000*60));

        return days + "d " + hours + "h " + minutes+ "m";
    }

    public int getNumber(){
        return this.Number;
    }

    public String getDepartureStation(){
        return this.departureStation;
    }

    public String getArrivalStation(){
        return this.arrivalStation;
    }

    public Date getDepartureDate(){
        return this.departureDate;
    }

    public Date getArrivalDate(){
        return this.arrivalDate;
    }

    public String getTripTime(){
        if (this.tripTime == null) {
            long diff = arrivalDate.getTime() - departureDate.getTime();
            long days = (diff / (1000 * 60 * 60 * 24));
            diff -= days * 1000 * 60 * 60 * 24;
            long hours = (diff / (1000 * 60 * 60));
            diff -= hours * 1000 * 60 * 60;
            long minutes = (diff / (1000 * 60));
            this.tripTime = days + "d " + hours + "h " + minutes+ "m";
        }
        return this.tripTime;
    }

    public void setNumber(int Number){
        this.Number = Number;
    }

    public void setDepartureStation(String departureStation){
        this.departureStation = departureStation;
    }

    public void setArrivalStation(String arrivalDate){
        this.arrivalStation = arrivalDate;
    }

    public void setDepartureDate(Date departureDate){
        this.departureDate = departureDate;
    }

    public void setArrivalDate(Date arrivalDate){
        this.arrivalDate = arrivalDate;
    }

}
