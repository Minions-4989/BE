package shopping.main.millions.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shopping.main.millions.dto.product.ProductDto;
import shopping.main.millions.dto.sales.GoodsImageDto;
import shopping.main.millions.entity.category.CategoryEntity;
import shopping.main.millions.entity.product.GoodsImageEntity;
import shopping.main.millions.entity.product.ProductEntity;
import shopping.main.millions.repository.product.CategoryRepository;
import shopping.main.millions.repository.product.ProductRepository;
import shopping.main.millions.repository.sales.GoodsImageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final GoodsImageRepository goodsImageRepository;

    public List<ProductDto> getProductsByPage(Pageable pageable) {
        Page<ProductEntity> productListPage = productRepository.findAll(pageable);
        return getProductDtos(productListPage);
    }

    private List<ProductDto> getProductDtos(Page<ProductEntity> productListPage) {
        List<ProductEntity> productList;
        if(productListPage.hasContent()){
            productList = productListPage.getContent();
        }else{
            return null;
        }
        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductEntity productEntity : productList) {
            Optional<List<GoodsImageEntity>> goodsImageById = goodsImageRepository.findGoodsImageEntitiesByProductEntity_ProductId(productEntity.getProductId());
            List<GoodsImageEntity> goodsImageEntityList = goodsImageById.get();
            List<GoodsImageDto> goodsImageDtoList = new ArrayList<>();

            for (GoodsImageEntity goodsImageEntity: goodsImageEntityList){
            GoodsImageDto goodsImageDto = GoodsImageDto.builder()
                    .productImage(goodsImageEntity.getProductImage())
                    .productImageOriginName(goodsImageEntity.getProductImageOriginName())
                    .productImageSave(goodsImageEntity.getProductImageSave())
                    .build();
            goodsImageDtoList.add(goodsImageDto);
            }
            ProductDto productDto = ProductDto.builder()
                    .productId(productEntity.getProductId())
                    .productPrice(productEntity.getProductPrice())
                    .productName(productEntity.getProductName())
                    .goodsImageDtoList(goodsImageDtoList)
                    .build();

            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    public ResponseEntity<?> findProductById(Long id) {
        // productId에 해당하는　productEntity 가져오기
        Optional<ProductEntity> byId = productRepository.findById(id);

        if(byId.isPresent()) {
            ProductEntity productEntity = byId.get();

            // entity -> dto
            ProductDto productDTO = ProductDto
                    .builder()
                    .productId(productEntity.getProductId())
                    .productName(productEntity.getProductName())
                    .productPrice(productEntity.getProductPrice())
                    .build();

            return ResponseEntity.status(200).body(productDTO);
        }else{
            return ResponseEntity.status(400).body("상품이 없습니다");

        }
    }

    public List<ProductDto> getProductsByCategory(Pageable pageable, String categoryName) {

        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findByCategoryName(categoryName);

        if(categoryEntityOptional.isPresent()) {
            CategoryEntity categoryEntity = categoryEntityOptional.get();

            Page<ProductEntity> productListPage = productRepository.findAllByCategoryEntity_CategoryName(categoryEntity.getCategoryName(), pageable);

            return getProductDtos(productListPage);
        }
        else{
            return null;
        }
    }
}