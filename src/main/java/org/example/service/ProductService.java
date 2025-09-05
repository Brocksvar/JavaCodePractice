package org.example.service;

import org.example.dao.ProductRepository;
import org.example.dto.ProductDto;
import org.example.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(Product::mapToProductDto).toList();
    }

    public ProductDto getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт с id=" + id + " не найден"))
                .mapToProductDto();
    }

    public ProductDto createProduct(ProductDto product) {
        return productRepository.save(product.mapToProduct()).mapToProductDto();
    }

    public ProductDto updateProduct(UUID id, ProductDto updatedProductDto) {
        Product updatedProduct = updatedProductDto.mapToProduct();
        return productRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedProduct.getName());
                    existing.setDescription(updatedProduct.getDescription());
                    existing.setPrice(updatedProduct.getPrice());
                    existing.setQuantityInStock(updatedProduct.getQuantityInStock());
                    return productRepository.save(existing).mapToProductDto();
                })
                .orElseThrow(() -> new RuntimeException("Продукт с id=" + id + " не найден"));
    }

    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Продукт с id=" + id + " не найден");
        }
        productRepository.deleteById(id);
    }
}
