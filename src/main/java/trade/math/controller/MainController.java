package trade.math.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by danielpietrzak on 15.02.2016.
 */
@Controller
public class MainController {

    private String dyndanieWithSpring="";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String main() {
        return "index";
    }

}
