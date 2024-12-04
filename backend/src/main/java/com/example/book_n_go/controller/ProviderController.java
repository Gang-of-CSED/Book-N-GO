package main.java.com.example.book_n_go.controller;

@RestController
@RequestMapping("/provider")
public class ProviderController {

    @GetMapping
    public String get() {
        return "Provider: Get provider controller";
    }

    @PostMapping
    public String post() {
        return "Provider: Post provider controller";
    }

    @PutMapping
    public String put() {
        return "Provider: Put provider controller";
    }

    @DeleteMapping
    public String delete() {
        return "Provider: Delete provider controller";
    }

}