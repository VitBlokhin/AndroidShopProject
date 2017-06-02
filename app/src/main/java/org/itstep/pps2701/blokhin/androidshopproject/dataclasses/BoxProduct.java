package org.itstep.pps2701.blokhin.androidshopproject.dataclasses;

/**
 * Created by Vit on 01.06.2017.
 */

// декоратор для Product, для вывода в списке товаров заказа
public class BoxProduct extends Product{
    private Product product;
    private boolean boxed = false;
    private int quantity = 0;

    public BoxProduct(Product product, boolean boxed, int quantity) {
        this.product = product;
        this.boxed = boxed;
        this.quantity = quantity;
    }

    public BoxProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public boolean isBoxed() {
        return boxed;
    }
    public void setBoxed(boolean boxed) {
        this.boxed = boxed;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return product.getName();
    }
} // class BoxProduct
