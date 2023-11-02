package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.testEntity.ProductTestBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidCaptor;

    static Stream<UUID> uuid() {
        return Stream.of(
                UUID.fromString("4c78be6d-1a6d-47bb-ae4a-0b63f2beccd0"),
                UUID.fromString("201a14b7-3cb5-4b94-ada6-a09c6464536b"),
                UUID.fromString("feda712b-54b8-4e9e-ba67-fbc5665c3cab")
        );
    }

    @ParameterizedTest
    @MethodSource("uuid")
    void shouldGetInfoProductDtoByUuid(UUID uuid) {

        // given
        InfoProductDto expected = ProductTestBuilder.builder().withUuid(uuid).build().buildInfoProduct();
        doReturn(expected)
                .when(productMapper)
                .toInfoProductDto(any(Product.class));
        doReturn(Optional.of(ProductTestBuilder.builder().withUuid(uuid).build().buildProduct()))
                .when(productRepository).findById(uuid);

        // when
        InfoProductDto actual = productService.get(uuid);

        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.name())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.description())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.price());

    }

    @Test
    void getAll() {

        // given
        List<Product> products = ProductTestBuilder.builder().build().products();

        List<InfoProductDto> expectedProductDto = ProductTestBuilder.builder().build().infoProductDtos();

        doReturn(products)
                .when(productRepository.findAll());
        doReturn(expectedProductDto)
                .when(productMapper).toListInfoProductDto(products);

        // when
        List<InfoProductDto> actualList = productService.getAll();

        // then

        assertEquals(expectedProductDto, actualList);
    }

    @Test
    void createShouldInvokeRepository_withoutProductUuid() {

        // given
        Product product = ProductTestBuilder.builder()
                .withUuid(null)
                .build().buildProduct();
        ProductDto productDto = ProductTestBuilder.builder().build().buildProductDTO();

        doReturn(product)
                .when(productMapper).toProduct(productDto);
        doReturn(product)
                .when(productRepository).save(product);

        // when
        productService.create(productDto);

        //then
        verify(productRepository).save(productCaptor.capture());
        assertNull(productCaptor.getValue().getUuid());

    }

    @Test
    void updateExistingProduct() {

        // given
        Product product = ProductTestBuilder.builder().build().buildProduct();

        ProductDto productUpdateDto = ProductTestBuilder.builder()
                .withName("Супер шпала")
                .withDescription("Практически вечная")
                .withPrice(BigDecimal.valueOf(24))
                .build().buildProductDTO();

        Product updatedProduct = ProductTestBuilder.builder()
                .withName(productUpdateDto.name())
                .withDescription(productUpdateDto.description())
                .withPrice(productUpdateDto.price())
                .build().buildProduct();

        doReturn(Optional.of(product))
                .when(productRepository).findById(product.getUuid());
        doReturn(updatedProduct)
                .when(productMapper).merge(product, productUpdateDto);

        // when

        productService.update(product.getUuid(), productUpdateDto);

        // then

        verify(productRepository).save(updatedProduct);
    }

    @Test
    void deleteProductByUuid() {

        // given
        ProductTestBuilder testData = ProductTestBuilder.builder().build();

        // when
        productService.delete(testData.getUuid());

        // then
        verify(productRepository)
                .delete(testData.getUuid());
    }
}