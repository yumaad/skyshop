package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    @Test
    void addProduct_WhenProductNotExists_ThrowsException() {
        UUID id = UUID.randomUUID();
        when(storageService.getProductById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class, () -> basketService.addProduct(id));
    }

    @Test
    void addProduct_WhenProductExists_CallsAddToBasket() {
        UUID id = UUID.randomUUID();
        Product product = new SimpleProduct(id, "Ice-cream", 50);
        when(storageService.getProductById(id)).thenReturn(Optional.of(product));

        basketService.addProduct(id);
        verify(productBasket, times(1)).addProduct(id);
    }

    @Test
    void getUserBasket_WhenBasketEmpty_ReturnsEmptyBasket() {
        when(productBasket.getProducts()).thenReturn(Map.of());
        UserBasket basket = basketService.getUserBasket();
        assertTrue(basket.getItems().isEmpty());
        assertEquals(0, basket.getTotal());
    }

    @Test
    void getUserBasket_WithProducts_ReturnsCorrectTotal() {
        UUID iceCreamId = UUID.randomUUID();
        Product iceCream = new SimpleProduct(iceCreamId, "Ice-cream", 50);

        UUID cakeId = UUID.randomUUID();
        Product cake = new DiscountedProduct(cakeId, "Cake", 100, 30);

        when(storageService.getProductById(iceCreamId)).thenReturn(Optional.of(iceCream));
        when(storageService.getProductById(cakeId)).thenReturn(Optional.of(cake));
        when(productBasket.getProducts()).thenReturn(Map.of(
                iceCreamId, 2,
                cakeId, 1
        ));

        UserBasket basket = basketService.getUserBasket();
        assertEquals(2, basket.getItems().size());
        assertEquals(170, basket.getTotal());
    }
}