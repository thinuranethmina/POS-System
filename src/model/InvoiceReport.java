
package model;

public class InvoiceReport {
    private String product;
    private String unitPrice;
    private String qty;
    private String subTotal;

    public InvoiceReport(String product, String unitPrice, String qty, String subTotal) {
        this.product = product;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.subTotal = subTotal;
    }
    
    

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }
}
