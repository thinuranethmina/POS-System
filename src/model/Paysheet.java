package model;

public class Paysheet {

    private String date;
    private String atime;
    private String dtime;
    private String hours;
    private String amount;

    public Paysheet(String date, String atime, String dtime, String hours, String amount) {
        this.date = date;
        this.atime = atime;
        this.dtime = dtime;
        this.hours = hours;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAtime() {
        return atime;
    }

    public void setAtime(String atime) {
        this.atime = atime;
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
