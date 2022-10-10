
package model;

public class GRNReport {
    private String barcord;
    private String product_name;
    private String brand;
    private String bprice;
    private String qty;
    private String mfd;
    private String exd;
    private String sub_total;

    public GRNReport(String barcord, String product_name, String brand, String bprice, String qty, String mfd, String exd, String sub_total) {
        this.barcord = barcord;
        this.product_name = product_name;
        this.brand = brand;
        this.bprice = bprice;
        this.qty = qty;
        this.mfd = mfd;
        this.exd = exd;
        this.sub_total = sub_total;
    }

    
    
    public String getBarcord() {
        return barcord;
    }

    public void setBarcord(String barcord) {
        this.barcord = barcord;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBprice() {
        return bprice;
    }

    public void setBprice(String bprice) {
        this.bprice = bprice;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getMfd() {
        return mfd;
    }

    public void setMfd(String mfd) {
        this.mfd = mfd;
    }

    public String getExd() {
        return exd;
    }

    public void setExd(String exd) {
        this.exd = exd;
    }

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }
}
