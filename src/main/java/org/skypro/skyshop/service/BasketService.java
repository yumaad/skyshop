package org.skypro.skyshop.service;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProduct(UUID id) {
        Product product = storageService.getProductById(id)
                .orElseThrow(() -> new NoSuchProductException("Product with ID " + id + " not found"));
        productBasket.addProduct(id);
    }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> basketProducts = productBasket.getProducts();

        List<BasketItem> items = basketProducts.entrySet().stream()
                .map(entry -> new BasketItem(
                        storageService.getProductById(entry.getKey()).orElseThrow(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());

        return new UserBasket(items);
    }
}