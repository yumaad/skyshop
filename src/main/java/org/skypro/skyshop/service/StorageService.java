package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        this.products = new HashMap<>();
        this.articles = new HashMap<>();

        fillTestData();
    }

    private void fillTestData() {
        UUID iceCreamId = UUID.randomUUID();
        UUID cakeId = UUID.randomUUID();
        UUID chocolateId = UUID.randomUUID();

        products.put(iceCreamId, new SimpleProduct(iceCreamId, "Ice-cream", 50));
        products.put(cakeId, new DiscountedProduct(cakeId, "Cake", 100, 30));
        products.put(chocolateId, new FixPriceProduct(chocolateId, "Chocolate"));

        articles.put(UUID.randomUUID(), new Article(UUID.randomUUID(), "Ice-cream", "Delicious vanilla ice-cream with natural ingredients."));
        articles.put(UUID.randomUUID(), new Article(UUID.randomUUID(), "Cake", "Soft and fluffy cake with chocolate frosting."));
        articles.put(UUID.randomUUID(), new Article(UUID.randomUUID(), "Chocolate", "Premium dark chocolate with 70% cocoa."));
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> searchables = new ArrayList<>();
        searchables.addAll(products.values());
        searchables.addAll(articles.values());
        return searchables;
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }
}