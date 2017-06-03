package org.itstep.pps2701.blokhin.androidshopproject.dataclasses;

/**
 * Created by Vit on 01.06.2017.
 */

// декоратор для Product, для вывода в списке товаров заказа
public class CartProduct extends Product{
    private Product product;
    private boolean inCart = false;
    private int quantity = 0;

    public CartProduct(Product product, boolean inCart, int quantity) {
        this.product = product;
        this.inCart = inCart;
        this.quantity = quantity;
    }

    @Override
    public long getId() {
        return product.getId();
    }
    @Override
    public void setId(long id) {
        product.setId(id);
    }

    @Override
    public String getName() {
        return product.getName();
    }
    @Override
    public void setName(String name) {
        product.setName(name);
    }

    @Override
    public String getDescription() {
        return product.getDescription();
    }
    @Override
    public void setDescription(String description) {
        product.setDescription(description);
    }

    @Override
    public double getPrice() {
        return product.getPrice();
    }
    @Override
    public void setPrice(double price) {
        product.setPrice(price);
    }

    public CartProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public boolean isInCart() {
        return inCart;
    }
    public void setInCart(boolean inCart) {
        this.inCart = inCart;
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
} // class CartProduct
