package shopping.main.millions.controller.sales;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shopping.main.millions.dto.sales.GoodsSaveDto;
import shopping.main.millions.service.sales.GoodsSaveService;

import java.util.Map;
@Log4j2
@Controller
@RequiredArgsConstructor
@RestController("/goods")
public class GoodsEditController {

    private final GoodsSaveService goodsSaveService;

    @PostMapping("/edit")
    public ResponseEntity<Map<String,String>> goodsEdit(@ModelAttribute GoodsSaveDto goodsSaveDto){

        return goodsSaveService.editItem(goodsSaveDto);
    }
}
