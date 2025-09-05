package org.example.dto;

import org.example.entity.Product;

import java.util.UUID;

public record ProductDto(
        UUID productId,
        String name,
        String description,
        Double price,
        Integer quantityInStock
) {
    public Product mapToProduct() {
        Product product = new Product();
        product.setProductId(this.productId);
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setQuantityInStock(this.quantityInStock);
        return product;
    }
}
