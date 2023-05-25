package com.example.selfcheckout2.service;

import com.example.selfcheckout2.data.Category;
import com.example.selfcheckout2.data.Product;
import com.example.selfcheckout2.data.ProductData;
import com.example.selfcheckout2.data.Supermarket;
import com.example.selfcheckout2.repository.CategoryRepository;
import com.example.selfcheckout2.repository.ProductRepository;
import com.example.selfcheckout2.repository.SupermarketRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SupermarketRepository supermarketRepository;

    @Override
    public ProductData saveProduct(ProductData product) {
        Supermarket supermarket = supermarketRepository.getById(product.getSupermarketId());
        Optional<Category> category = categoryRepository.findCategoryByName(product.category);
        Product newProduct = new Product(product.name, product.description, product.quantity,product.barcode,product.price, supermarket,category.get());
        Product product1 = productRepository.save(newProduct);
        product.setId(product1.getId());
        return product;
    }

    @Override
    public void saveProductList(List<ProductData> productList) {
        for (ProductData p : productList
             ) {
            Optional<Category> category = categoryRepository.findCategoryByName(p.category);
            Product newProduct = new Product(p.name, p.description, p.quantity,p.barcode,p.price, category.get());
            productRepository.save(newProduct);
        }
    }

    @Override
    public ProductData getProductById(Long productId) {
        return null;
    }

    public ProductData getProductByBarcode(String barcode)
    {
        List<Product> productList = productRepository.findAll();
        System.out.println("BARCODE === " + barcode);
        for (Product p : productList
             ) {
            System.out.println("LIST = " + p.getBarcode());
            if(p.getBarcode().equals(barcode))
            {
                Optional<Category> category = categoryRepository.findById(p.getCategory().getId());
                if(category.isPresent())
                {
                    return new ProductData(p.name, p.description, p.quantity,p.barcode,category.get().getName(), p.price);
                }

            }
        }
        return null;
    }
    public void retrieveProductsFromAllSupermarkets(){
        List<Supermarket> supermarkets = supermarketRepository.findAll();

        supermarkets.forEach(supermarket -> {
            String uri = "http://localhost:3000/" + supermarket.getEndpoint();
            if(supermarket.dataFormat.equals("json"))
            {
                WebClient webClient = WebClient.create();
                WebClient.ResponseSpec responseSpec = webClient.get()
                        .uri(uri)
                        .retrieve();
                List<ProductData> productDataList = responseSpec.bodyToMono(new ParameterizedTypeReference<List<ProductData>>() {}).block();
                productDataList.forEach(productData -> {
                    Optional<Category> category = categoryRepository.findCategoryByName(productData.category);
                    Product product = new Product(productData.name, productData.description, productData.quantity, productData.barcode,productData.price, supermarket, category.get());
                    productRepository.save(product);
                });

            }else if(supermarket.dataFormat.equals("xml"))
            {
                WebClient webClient = WebClient.builder()
                        .exchangeStrategies(ExchangeStrategies.builder()
                                .codecs(configurer -> configurer.defaultCodecs().jaxb2Decoder(new Jaxb2XmlDecoder()))
                                .build())
                        .build();

                WebClient.ResponseSpec responseSpec = webClient.get()
                        .uri(uri)
                        .accept(MediaType.APPLICATION_XML)
                        .retrieve();

                Mono<String> responseBody = responseSpec.bodyToMono(String.class);
                XmlMapper xmlMapper = new XmlMapper();
                try {
                    List<ProductData> productDataList = xmlMapper.readValue(responseBody.block(), new TypeReference<List<ProductData>>() {});
                    productDataList.forEach(productData ->{
                        Optional<Category> category = categoryRepository.findCategoryByName(productData.getCategory());
                        Product product = new Product(productData.name, productData.description, productData.quantity, productData.barcode, productData.price, supermarket,category.get() );
                        productRepository.save(product);
                    } );
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }


        });
    }

    @Override
    public List<ProductData> getProductsBySupermarketAdministrator(String email) {
        Supermarket supermarket = supermarketRepository.findSupermarketByEmailContact(email);
        List<Product> products =  productRepository.findProductBySupermarket(supermarket);
        List<ProductData> productDataList = new ArrayList<>();
        products.forEach(product -> {
            Optional<Category> category = categoryRepository.findById(product.getCategory().getId());
            if(category.isPresent())
            {
                productDataList.add(new ProductData(product.name, product.description, product.quantity, product.barcode, category.get().getName(), product.price, product.getId(), product.getSupermarket().getId()));

            }
        });
        return productDataList;
    }

    @Override
    public Product updateProduct(ProductData productData, Long id) throws ValidationException {
        Optional<Product> product = productRepository.findById(id);
        System.out.println("product "+ productData);
        if(product.isPresent())
        {
            Product oldProductValue = product.get();
            oldProductValue.setName(productData.name);
            oldProductValue.setBarcode(productData.getBarcode());
            oldProductValue.setDescription(productData.getDescription());
            Optional<Category> category = categoryRepository.findById(product.get().getCategory().getId());
            if(category.isPresent())
            {
                oldProductValue.setCategory(category.get());
            }

            oldProductValue.setPrice(productData.getPrice());
            oldProductValue.setQuantity(productData.quantity);
                        return productRepository.save(oldProductValue);
        }
        else
        {
            throw new ValidationException("Product doesn't exist!");

        }
    }
    public ProductData getProductByName(String name)
    {
        Product product = productRepository.findProductByName(name);
        return new ProductData(product.getName(), product.getDescription(), product.getQuantity(), product.getBarcode(), product.getCategory().getName(), product.getPrice());
    }
}
