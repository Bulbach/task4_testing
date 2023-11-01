package ru.clevertec.product.testEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class ProductTestData {

    @Builder.Default()
    private UUID uuid = UUID.fromString("4c78be6d-1a6d-47bb-ae4a-0b63f2beccd0");
    @Builder.Default()
    private String name = "Шпала";
    @Builder.Default()
    private String description = "Бетонная железнодорожная";
    @Builder.Default()
    private BigDecimal price = BigDecimal.valueOf(50);
    @Builder.Default()
    private LocalDateTime created = LocalDateTime.of(2022, Month.MAY, 14, 13, 45);

    public static Product buildProduct() {
        ProductTestData testData = ProductTestData.builder().build();
        return Product.builder()
                .uuid(testData.uuid)
                .name(testData.name)
                .description(testData.description)
                .price(testData.price)
                .created(testData.getCreated())
                .build();
    }
    public static ProductDto buildProductDTO() {
        ProductTestData testData = ProductTestData.builder().build();
        return new ProductDto(testData.name, testData.description, testData.price);
    }

    public static InfoProductDto buildInfoProduct() {
        ProductTestData testData = ProductTestData.builder().build();
        return new InfoProductDto(testData.uuid, testData.name, testData.description, testData.price);
    }

}
