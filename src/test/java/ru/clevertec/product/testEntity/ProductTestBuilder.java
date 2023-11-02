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
import java.util.List;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class ProductTestBuilder {

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

    public Product buildProduct() {
        return Product.builder()
                .uuid(uuid)
                .name(name)
                .description(description)
                .price(price)
                .created(created)
                .build();
    }

    public ProductDto buildProductDTO() {
        return new ProductDto(name, description, price);
    }

    public InfoProductDto buildInfoProduct() {
        return new InfoProductDto(uuid, name, description, price);
    }

    public List<Product> products() {
        UUID uuid1 = UUID.fromString("4c78be6d-1a6d-47bb-ae4a-0b63f2beccd0");
        UUID uuid2 = UUID.fromString("201a14b7-3cb5-4b94-ada6-a09c6464536b");
        UUID uuid3 = UUID.fromString("feda712b-54b8-4e9e-ba67-fbc5665c3cab");
        List<Product> productList = List.of(
                Product.builder()
                        .uuid(uuid1)
                        .name("Шпала")
                        .description("Бетонная")
                        .price(BigDecimal.valueOf(50))
                        .created(LocalDateTime.of(2022, Month.MAY, 14, 13, 45))
                        .build(),
                Product.builder()
                        .uuid(uuid2)
                        .name("Рельс")
                        .description("Стальной")
                        .price(BigDecimal.valueOf(1700))
                        .created(LocalDateTime.of(2023, Month.OCTOBER, 28, 20, 20))
                        .build(),
                Product.builder()
                        .uuid(uuid3)
                        .name("Костыль")
                        .description("Путевой")
                        .price(BigDecimal.valueOf(12.5))
                        .created(LocalDateTime.of(2023, Month.AUGUST, 20, 15, 15))
                        .build()
        );
        return productList;
    }

    public List<InfoProductDto> infoProductDtos() {

        UUID uuid1 = UUID.fromString("4c78be6d-1a6d-47bb-ae4a-0b63f2beccd0");
        UUID uuid2 = UUID.fromString("201a14b7-3cb5-4b94-ada6-a09c6464536b");
        UUID uuid3 = UUID.fromString("feda712b-54b8-4e9e-ba67-fbc5665c3cab");


        List<InfoProductDto> infoProductDtos = List.of(
                new InfoProductDto(uuid1, "Шпала", "Бетонная", BigDecimal.valueOf(50)),
                new InfoProductDto(uuid2, "Рельс", "Стальной", BigDecimal.valueOf(1700)),
                new InfoProductDto(uuid3, "Костыль", "Путевой", BigDecimal.valueOf(12.5)));

        return infoProductDtos;
    }

    public List<ProductDto> productDtos() {

               List<ProductDto> productDtos = List.of(
                new ProductDto( "Шпала", "Бетонная", BigDecimal.valueOf(50)),
                new ProductDto( "Рельс", "Стальной", BigDecimal.valueOf(1700)),
                new ProductDto( "Костыль", "Путевой", BigDecimal.valueOf(12.5)));

        return productDtos;
    }

}
