package AgriPrice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import AgriPrice.model.PriceRecord;
import AgriPrice.service.PriceService;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public List<PriceRecord> getPrices(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String commodity) {

        return priceService.getPrices(state, commodity);
    }
}