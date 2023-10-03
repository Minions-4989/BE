package shopping.main.millions.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shopping.main.millions.dto.product.ProductDto;
import shopping.main.millions.entity.product.ProductEntity;
import shopping.main.millions.repository.product.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

//    public ResponseEntity<List<ProductDto>> findAll() {
//        // entity -> dto
//        List<ProductEntity> productList = productRepository.findAll();
//        List<ProductDto> productDTOList = new ArrayList<>();
//        for (ProductEntity productEntity : productList) {
//            ProductDto productDTO = ProductDto
//                    .builder()
//                    .productId(productEntity.getProductId())
//                    .productName(productEntity.getProductName())
//                    .productPrice(productEntity.getProductPrice())
//                    .productDate(productEntity.getProductDate())
//                    .build();
//            productDTOList.add(productDTO);
//        }
//
//        return ResponseEntity.status(200).body(productDTOList);
//    }

    public Page<ProductDto> getProductsByPage(Pageable pageable) {
        Page<ProductEntity> products = productRepository.findAll(pageable);
        Page<ProductDto> productDto = products.map(product -> ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productDate(product.getProductDate())
                .build());
        return productDto;
    }

    public ResponseEntity<ProductDto> findProductById(Long id) {
        // productId에 해당하는　productEntity 가져오기
        Optional<ProductEntity> byId = productRepository.findById(id);
        ProductEntity productEntity = byId.get();
        // entity -> dto
        ProductDto productDTO = ProductDto
                .builder()
                .productId(productEntity.getProductId())
                .productName(productEntity.getProductName())
                .productPrice(productEntity.getProductPrice())
                .productDate(productEntity.getProductDate())
                .build();

        return ResponseEntity.status(200).body(productDTO);
    }
}