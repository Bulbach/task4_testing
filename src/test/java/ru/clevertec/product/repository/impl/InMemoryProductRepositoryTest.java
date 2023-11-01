package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.testEntity.ProductTestData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InMemoryProductRepositoryTest {
    @InjectMocks
    private InMemoryProductRepository productRepository;

    @Test
    void findById() {
        // given
        Product expected = ProductTestData.builder().build().buildProduct();

        // when
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        // given
        UUID uuid1 = UUID.fromString("4c78be6d-1a6d-47bb-ae4a-0b63f2beccd0");
        UUID uuid2 = UUID.fromString("201a14b7-3cb5-4b94-ada6-a09c6464536b");
        UUID uuid3 = UUID.fromString("feda712b-54b8-4e9e-ba67-fbc5665c3cab");
        List<Product> expectedList = Arrays.asList(
                Product.builder()
                        .uuid(uuid1)
                        .name("Шпала")
                        .description("Бетонная")
                        .price(BigDecimal.valueOf(50))
                        .created(LocalDateTime.of(2022, Month.MAY, 14, 13, 45))
                        .build()
                , Product.builder()
                        .uuid(uuid2)
                        .name("Рельс")
                        .description("Стальной")
                        .price(BigDecimal.valueOf(1700))
                        .created(LocalDateTime.of(2023, Month.OCTOBER, 28, 20, 20))
                        .build()
                , Product.builder()
                        .uuid(uuid3)
                        .name("Костыль")
                        .description("Путевой")
                        .price(BigDecimal.valueOf(12.5))
                        .created(LocalDateTime.of(2023, Month.AUGUST, 20, 15, 15))
                        .build()
        );
        Mockito.doReturn(expectedList).when(productRepository.findAll());

        // when
        List<Product> actual = productRepository.findAll();
        // then
        assertEquals(expectedList, actual);
    }

    @Test
    void save() {
        // given
        Product expected = ProductTestData.builder().build().buildProduct();
        productRepository = Mockito.mock(InMemoryProductRepository.class);
        // when
        Product actual = productRepository.save(expected);
        Mockito.when(productRepository.save(expected)).thenReturn(expected);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void delete() {
        // given
        UUID uuid = UUID.fromString("4c78be6d-1a6d-47bb-ae4a-0b63f2beccd0");
        productRepository = Mockito.mock(InMemoryProductRepository.class);
        // when
        productRepository.delete(uuid);
        // then
        verify(productRepository)
                .delete(uuid);

    }
}