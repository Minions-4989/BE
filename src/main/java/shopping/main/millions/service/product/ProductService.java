package shopping.main.millions.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shopping.main.millions.dto.product.ProductDto;
import shopping.main.millions.entity.category.CategoryEntity;
import shopping.main.millions.entity.product.ProductEntity;
import shopping.main.millions.repository.product.CategoryRepository;
import shopping.main.millions.repository.product.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public Page<ProductDto> getProductsByPage(Pageable pageable) {
        Page<ProductEntity> products = productRepository.findAll(pageable);
        Page<ProductDto> productDto = products.map(product -> ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .build());
        return productDto;
    }

    public ResponseEntity<?> findProductById(Long id) {
        // productId에 해당하는　productEntity 가져오기
        Optional<ProductEntity> byId = productRepository.findById(id);

        if (byId.isPresent()) {
            ProductEntity productEntity = byId.get();

            // entity -> dto
            ProductDto productDTO = ProductDto
                    .builder()
                    .productId(productEntity.getProductId())
                    .productName(productEntity.getProductName())
                    .productPrice(productEntity.getProductPrice())
                    .build();

            return ResponseEntity.status(200).body(productDTO);
        } else {
            return ResponseEntity.status(400).body("상품이 없습니다");

        }
    }


    public Page<ProductDto> getProductsByCategory(Pageable pageable, String categoryName) {

        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findByCategoryName(categoryName);

        if (categoryEntityOptional.isPresent()) {
            CategoryEntity categoryEntity = categoryEntityOptional.get();

            Page<ProductEntity> products = productRepository.findAllByCategoryEntityOrderByProductId(categoryEntity, pageable);

            Page<ProductDto> productDtoPage = products.map(product -> ProductDto.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .productDate(product.getProductDate())
                    .build());
            return productDtoPage;
        } else {
            return null;
        }
    }

        public Page<ProductDto> getProductsByCategory (Pageable pageable, Long categoryId){
            Page<ProductEntity> products = productRepository.findAllByCategoryEntityOrderByProductId(categoryId, pageable);
            Page<ProductDto> productDto = products.map(product -> ProductDto.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .build());
            return productDto;

        }
    }
