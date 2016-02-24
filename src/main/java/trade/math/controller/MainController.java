package trade.math.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.service.TradeItemService;
import trade.math.service.TradeUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

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
    public String main(Model model, @RequestParam(value = "page", required = false) Optional<Integer> pageNum) {
        prepareTradeList();

        model.addAttribute("mainList", tradeItemService.findAll(new PageRequest(pageNum.orElse(1) - 1, 10)));

        return "index";
    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.POST)
    @ResponseBody
    public String deleteTradeItem(@RequestParam(value = "deleteId") Integer deleteId) {
        return tradeItemService.deleteById(deleteId.longValue()) ? "success" : "failure";
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


//TODO: usunąć po dodaniu możliwości dodwania gier

    private boolean dbIsReady = false;

    private void prepareTradeList() {
        if (dbIsReady)
            return;

        tradeItemService.deleteAll();

        for (int i = 0; i < 100; i++) {
            NewTradeItemForm form = new NewTradeItemForm();
            form.setDescription("desc" + i);
            form.setBggId(i);

            tradeItemService.save(form);
        }
        dbIsReady = true;
    }

}
