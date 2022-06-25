package cl.mi.mercado.models;

public class ProductModel {
    private String name;
    private String sku;
    private String measure;
    private double price;
    private double price_buy;
    private int quantity;
    private String id;

    public ProductModel(){

    }

    public ProductModel(String name,String sku,String measure,double price){
        this.name = name;
        this.sku = sku;
        this.measure = measure;
        this.price = price;
    }

    public ProductModel(String name,String sku,String measure,double price, double price_buy, int quantity){
        this.name = name;
        this.sku = sku;
        this.measure = measure;
        this.price = price;
        this.price_buy = price_buy;
        this.quantity = quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setPrice_buy(double price_buy) {
        this.price_buy = price_buy;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public double getPrice_buy() {
        return price_buy;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getSku() {
        return sku;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "name='" + name + '\'' +
                ", sku='" + sku + '\'' +
                ", measure='" + measure + '\'' +
                ", price=" + price +
                ", price_buy=" + price_buy +
                ", quantity=" + quantity +
                '}';
    }
}
