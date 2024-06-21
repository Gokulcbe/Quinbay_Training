package org.example;

public class ProductDetails { //Product Details
    int prodId;
    String prodName;
    int prodStock;
    double prodPrice;
    String flag;

    ProductDetails(int prodId, String prodName, int prodStock, double prodPrice){
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodStock = prodStock;
        this.prodPrice = prodPrice;
        this.flag = "false";
    }
}
