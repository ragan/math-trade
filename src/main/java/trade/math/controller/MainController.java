package trade.math.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.repository.TradeItemRepository;
import trade.math.service.TradeItemService;
import trade.math.service.TradeUserService;

import javax.validation.Valid;

/**
 * Created by danielpietrzak on 15.02.2016.
 */
@Controller
public class MainController {

    private TradeItemService tradeItemService;

    private TradeUserService tradeUserService;

    public MainController() {
    }

    @Autowired
    public MainController(TradeItemService tradeItemService, TradeUserService tradeUserService) {
        this.tradeItemService = tradeItemService;
        this.tradeUserService = tradeUserService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main() {
        return "index";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signUp(NewTradeUserForm newTradeUserForm) {
        return "signUp";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(@Valid NewTradeUserForm newTradeUserForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "signUp";
        tradeUserService.save(newTradeUserForm);
        return "redirect:/";
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
