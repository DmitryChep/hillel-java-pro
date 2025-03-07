package ua.ithillel.javapro.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.ithillel.javapro.model.Product;
import ua.ithillel.javapro.service.ProductService;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private Product testProduct1;
    private Product testProduct2;
    private Long testProductId;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService))
                .build();
        testProduct1 = new Product(1L, "Product1", 100.0);
        testProduct2 = new Product(2L, "Product2", 200.0);
        testProductId = 1L;

    }

    @Test
    public void findProductById_shouldReturnProduct_whenProductExists() throws Exception {
        when(productService.getProductById(testProductId)).thenReturn(Optional.of(testProduct1));

        mockMvc.perform(get("/products/{id}", testProductId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Product1"))
                .andExpect(jsonPath("$.price").value(100.0));

        verify(productService, times(1)).getProductById(testProductId);
    }

    @Test
    public void findProductById_shouldReturnNotFound_whenProductDoesNotExist() throws Exception {
        when(productService.getProductById(testProductId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/{id}", testProductId))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getProductById(testProductId);
    }

    @Test
    public void findAllProducts_shouldReturnAllProducts_whenProductsExist() throws Exception {
        List<Product> productList = Arrays.asList(testProduct1, testProduct2);
        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Product1"))
                .andExpect(jsonPath("$[1].name").value("Product2"));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void findAllProducts_shouldReturnNotFound_whenNoProductsExist() throws Exception {
        when(productService.getAllProducts()).thenReturn(null);

        mockMvc.perform(get("/products"))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void createProduct_shouldReturnCreated_whenProductIsValid() throws Exception {
        testProduct1.setId(null);
        Product savedProduct = new Product(1L, "Product1", 100.0);
        when(productService.addProduct(testProduct1)).thenReturn(savedProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Product1\",\"price\":100.0}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product1"))
                .andExpect(jsonPath("$.price").value(100.0));

        verify(productService, times(1)).addProduct(testProduct1);
    }

    @Test
    public void deleteProduct_shouldReturnNoContent_whenProductExists() throws Exception {

        doNothing().when(productService).deleteProduct(testProductId);

        mockMvc.perform(delete("/products/{id}", testProductId))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(testProductId);
    }

    @Test
    public void deleteProduct_shouldReturnNotFound_whenProductDoesNotExist() throws Exception {

        doThrow(new NoSuchElementException()).when(productService).deleteProduct(testProductId);

        mockMvc.perform(delete("/products/{id}", testProductId))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).deleteProduct(testProductId);
    }

    @Test
    public void deleteProduct_shouldReturnBadRequest_whentestProductIdIsInvalid() throws Exception {

        doThrow(new IllegalArgumentException()).when(productService).deleteProduct(testProductId);

        mockMvc.perform(delete("/products/{id}", testProductId))
                .andExpect(status().isBadRequest());

        verify(productService, times(1)).deleteProduct(testProductId);
    }

    @Test
    public void updateProduct_shouldReturnUpdatedProduct_whenProductExists() throws Exception {
        testProduct1.setId(null);
        testProduct1.setName("UpdatedProduct");
        Product updatedProduct = new Product(testProductId, "UpdatedProduct", 100.0);
        when(productService.updateProduct(testProductId, testProduct1)).thenReturn(Optional.of(updatedProduct));

        mockMvc.perform(put("/products/{id}", testProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"UpdatedProduct\",\"price\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("UpdatedProduct"))
                .andExpect(jsonPath("$.price").value(100.0));

        verify(productService, times(1)).updateProduct(testProductId, testProduct1);
    }

    @Test
    public void updateProduct_shouldReturnNotFound_whenProductDoesNotExist() throws Exception {
        testProduct1.setId(null);
        testProduct1.setName("UpdatedProduct");
        when(productService.updateProduct(testProductId, testProduct1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/products/{id}", testProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"UpdatedProduct\",\"price\":100.0}"))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).updateProduct(testProductId, testProduct1);
    }
}
