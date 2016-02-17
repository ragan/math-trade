package trade.math.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import trade.math.form.NewTradeItemForm;
import trade.math.repository.TradeItemRepository;
import trade.math.service.TradeItemService;

import javax.validation.Valid;

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
    public String doAddTradeItem(@Valid NewTradeItemForm newTradeItemForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "addItem";
        tradeItemService.save(newTradeItemForm);
        return "redirect:/addItem";
    }

}
