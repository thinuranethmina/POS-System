package model;

public class StockReport {

    private String barcode;
    private String product;
    private String category;
    private String brand;
    private String qty;
    private String bprice;
    private String sprice;
    private String mfd;
    private String exd;

    public StockReport(String barcode, String product, String category, String brand, String qty, String bprice, String sprice, String mfd, String exd) {
        this.barcode = barcode;
        this.product = product;
        this.category = category;
        this.brand = brand;
        this.qty = qty;
        this.bprice = bprice;
        this.sprice = sprice;
        this.mfd = mfd;
        this.exd = exd;
    }

    
    
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getBprice() {
        return bprice;
    }

    public void setBprice(String bprice) {
        this.bprice = bprice;
    }

    public String getSprice() {
        return sprice;
    }

    public void setSprice(String sprice) {
        this.sprice = sprice;
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

}
