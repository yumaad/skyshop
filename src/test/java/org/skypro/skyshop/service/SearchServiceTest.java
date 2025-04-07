package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void search_WhenStorageEmpty_ReturnsEmptyList() {
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());
        assertTrue(searchService.search("sweet").isEmpty());
    }

    @Test
    void search_WhenNoMatches_ReturnsEmptyList() {
        Product product = new SimpleProduct(UUID.randomUUID(), "Ice-cream", 50);
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(product));
        assertTrue(searchService.search("Chocolate").isEmpty());
    }

    @Test
    void search_WhenMatchExists_ReturnsSearchResult() {
        Product product = new DiscountedProduct(UUID.randomUUID(), "Cake", 100, 30);
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(product));

        List<SearchResult> results = searchService.search("Cake");
        assertEquals(1, results.size());
        assertEquals("Cake", results.get(0).getName());
    }

    @Test
    void search_IsCaseInsensitive() {
        Product product = new FixPriceProduct(UUID.randomUUID(), "Chocolate");
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(product));

        assertFalse(searchService.search("chocolate").isEmpty());
        assertFalse(searchService.search("CHOCOLATE").isEmpty());
    }

    @Test
    void search_WhenArticleExists_ReturnsResult() {
        Article article = new Article(UUID.randomUUID(),
                "Sweet Recipes",
                "How to make perfect chocolate cake");
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(article));

        assertFalse(searchService.search("recipe").isEmpty());
    }
}