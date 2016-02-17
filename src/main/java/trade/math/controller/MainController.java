package trade.math.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import trade.math.form.NewTradeItemForm;
import trade.math.repository.TradeItemRepository;
import trade.math.service.TradeItemService;

/**
 * Created by danielpietrzak on 15.02.2016.
 */
@Controller
public class MainController {

    private TradeItemService tradeItemService;

    public MainController() {

    }

    @Autowired
    public MainController(TradeItemService tradeItemService) {
        this.tradeItemService = tradeItemService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main() {
        return "index";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signUp() {
        return "signUp";
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.GET)
    public String addTradeItem(NewTradeItemForm newTradeItemForm) {
        return "addItem";
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public String doAddTradeItem(NewTradeItemForm newTradeItemForm) {
        tradeItemService.save(newTradeItemForm);
        return "redirect:/addItem";
    }

}
