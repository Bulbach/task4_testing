package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.testEntity.ProductTestBuilder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InMemoryProductRepositoryTest {

    @InjectMocks
    private InMemoryProductRepository productRepository;

    @Test
    void shouldFindProductById() {

        // given
        Product expected = ProductTestBuilder.builder().build().buildProduct();

        // when
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void findAll() {

        // given
        List<Product> expectedList = ProductTestBuilder.builder().build().products();

        doReturn(expectedList).when(productRepository.findAll());

        // when
        List<Product> actual = productRepository.findAll();

        // then
        assertEquals(expectedList, actual);
    }

    @Test
    void saveOrUpdateProduct() {

        // given
        Product expected = ProductTestBuilder.builder().build().buildProduct();
        productRepository = Mockito.mock(InMemoryProductRepository.class);

        // when
        Product actual = productRepository.save(expected);
        when(productRepository.save(expected)).thenReturn(expected);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void deleteProductByUuid() {

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