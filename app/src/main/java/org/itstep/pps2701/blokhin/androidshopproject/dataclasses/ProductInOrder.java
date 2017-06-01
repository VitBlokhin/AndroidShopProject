package org.itstep.pps2701.blokhin.androidshopproject.dataclasses;

/**
 * Created by Vit on 01.06.2017.
 */
public class ProductInOrder {
    private Purchase purchase;
    private Product product;

    public ProductInOrder(Purchase purchase, Product product) {
        this.purchase = purchase;
        this.product = product;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    @Override
    public String toString() {
        return product.getName() + ", " + purchase.getQuantity() + " шт., по цене " + purchase.getQuantity() * product.getPrice() + " р.";
    }
} // class ProductInOrder
