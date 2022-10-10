package model;

public class AdditionalCostReport {
    
    private String id;
    private String date;
    private String payment_type;
    private String total_payble_amount;
    private String payment;
    private String balance;
    private String details;

    public AdditionalCostReport(String id, String date, String payment_type, String total_payble_amount, String payment, String balance, String details) {
        this.id = id;
        this.date = date;
        this.payment_type = payment_type;
        this.total_payble_amount = total_payble_amount;
        this.payment = payment;
        this.balance = balance;
        this.details = details;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTotal_payble_amount() {
        return total_payble_amount;
    }

    public void setTotal_payble_amount(String total_payble_amount) {
        this.total_payble_amount = total_payble_amount;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
}
