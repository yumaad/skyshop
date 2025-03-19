package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {
    private final int price;

    public SimpleProduct(UUID id, String productName, int price) {
        super(id, productName);
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }
}