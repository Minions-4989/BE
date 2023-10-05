package shopping.main.millions.controller.sales;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shopping.main.millions.dto.sales.GoodsSaveDto;
import shopping.main.millions.service.sales.GoodsSaveService;

import java.util.Map;
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsEditController {

    private final GoodsSaveService goodsSaveService;

    @PostMapping("/edit")
    public ResponseEntity<Map<String,String>> goodsEdit(@ModelAttribute GoodsSaveDto goodsSaveDto){

        return goodsSaveService.editItem(goodsSaveDto);
    }
}
