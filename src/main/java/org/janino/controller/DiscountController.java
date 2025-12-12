package org.janino.controller;

import org.janino.service.DynamicDiscountService;
import org.janino.service.HardcodedDiscountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/discount")
@Controller
public class DiscountController {

    private final HardcodedDiscountService hardcodedService;
    private final DynamicDiscountService dynamicService;

    public DiscountController(HardcodedDiscountService hardcodedService, DynamicDiscountService dynamicService) {
        this.hardcodedService = hardcodedService;
        this.dynamicService = dynamicService;
    }

    /**
     * Serve the HTML UI
     */
    @GetMapping("/")
    public String home() {
        return "janino";
    }

    /**
     * REST API for hardcoded rule
     */
    @GetMapping("/hardcoded")
    @ResponseBody
    public double hardcoded(@RequestParam double amount) {
        return hardcodedService.calculate(amount);
    }

    /**
     * REST API for dynamic rule
     */
    @GetMapping("/dynamic")
    @ResponseBody
    public double dynamic(@RequestParam double amount) throws Exception {
        return dynamicService.calculate(amount);
    }
}
