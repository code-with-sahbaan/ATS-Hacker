package code.with.sahbaan.neohire.Controllers.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class DefaultController {

    @GetMapping("health")
    public String health() {
        return "UP";
    }
}
