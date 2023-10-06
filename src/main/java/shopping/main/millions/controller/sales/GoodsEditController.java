package shopping.main.millions.controller.sales;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shopping.main.millions.dto.sales.GoodsImageDto;
import shopping.main.millions.dto.sales.GoodsModifyDto;
import shopping.main.millions.dto.sales.GoodsSaveDto;
import shopping.main.millions.dto.sales.StockSaveDto;
import shopping.main.millions.service.sales.GoodsSaveService;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsEditController {

    private final GoodsSaveService goodsSaveService;

    @PostMapping("/edit")
    public ResponseEntity<Map<String,String>> goodsEdit(@RequestPart ("imageFile") List<MultipartFile> imageFile,
                                                        @RequestPart ("goodsSaveDto") GoodsSaveDto goodsSaveDto) {


        System.out.println("imageFile = " + imageFile + ", goodsSaveDto = " + goodsSaveDto);

        return goodsSaveService.editItem(goodsSaveDto, imageFile);
    }

    @PutMapping("/check/{productId}")
    public ResponseEntity<Map<String,String>> goodsUpdate(@PathVariable("productId") Long productId ,
                                                          @RequestParam Long userId,
                                                          @RequestBody GoodsModifyDto modifyDto,
                                                          @RequestBody StockSaveDto stockSaveDto,
                                                          @RequestBody GoodsImageDto goodsImageDto ){

        return goodsSaveService.modifyItem(modifyDto,productId,userId,stockSaveDto,goodsImageDto);

    }
}
