package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.UUID;

public abstract class Product implements Searchable {
    private final UUID id;
    private final String productName;

    public Product(UUID id, String productName) {
        this.id = id;
        this.productName = productName;
    }

    public UUID getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public abstract int getPrice();

    public boolean isSpecial() {
        return false;
    }

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return getProductName();
    }

    @JsonIgnore
    @Override
    public String getContentType() {
        return "PRODUCT";
    }

    @JsonIgnore
    @Override
    public String getName() {
        return getProductName();
    }

    @JsonIgnore
    @Override
    public String getStringRepresentation() {
        return getName() + " — " + getContentType();
    }
}