package core;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
public class Controller {

    @GetMapping("/get")
    public String get(@RequestParam(value = "key") String key)
    {
        return "Hello world";
    }
}
