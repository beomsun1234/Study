package com.example.helloopenapiswaggerui;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class CalculatorController {

    @GetMapping(value = "/addition")
    public Integer addition(@RequestParam Integer num1, @RequestParam Integer num2) {
        return num1 + num2;
    }

    @GetMapping(value = "/subtraction")
    public Integer subtraction(@RequestParam Integer num1, @RequestParam Integer num2) {
        return num1 - num2;
    }

    @GetMapping(value = "/multiplication")
    public Integer multiplication(@RequestParam Integer num1, @RequestParam Integer num2) {
        return num1 * num2;
    }

    @GetMapping(value = "/division")
    public Integer division(@RequestParam Integer num1, @RequestParam Integer num2) {
        return num1 / num2;
    }


}
