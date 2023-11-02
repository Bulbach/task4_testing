package ru.clevertec.product.mapper.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.testEntity.ProductTestBuilder;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductMapperImplTest {

    @InjectMocks
    private ProductMapperImpl productMapper;

    @Test
    void shouldReturnNewProductWithoutUUID() {

        // given
        ProductDto expected = ProductTestBuilder.builder().build().buildProductDTO();

        // when
        Product actual = productMapper.toProduct(expected);

        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.name())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.description())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.price());
    }

    @Test
    void shouldBeReturnInfoProductDtoBasedOnExistingProductWithUUID() {

        // given
        UUID uuid = UUID.fromString("4c78be6d-1a6d-47bb-ae4a-0b63f2beccd0");
        Product expected = ProductTestBuilder.builder().build().buildProduct();
        // when
        InfoProductDto actual = productMapper.toInfoProductDto(expected);

        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice());
    }

    @Test
    void shouldBeReturnsAnUpdatedProductMakingChangesFromProductDtoBasedOnExistingProduct() {

        // given
        ProductDto expectedDto = ProductTestBuilder.builder()
                .withName("Модерон")
                .withDescription("тележка путейская")
                .withPrice(BigDecimal.valueOf(34))
                .build().buildProductDTO();
        Product expected = ProductTestBuilder.builder().build().buildProduct();

        // when
        Product actual = productMapper.merge(expected, expectedDto);

        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(Product.Fields.name, expectedDto.name())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expectedDto.description())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expectedDto.price());
    }
}