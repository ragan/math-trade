package trade.math.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import trade.math.form.NewTradeItemForm;
import trade.math.service.TradeItemService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

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
    public String main(Model model, @RequestParam(value = "page", required = false) Optional<Integer> pageNum) {
        prepareTradeList();

        model.addAttribute("mainList", tradeItemService.findAll(new PageRequest(pageNum.orElse(1) - 1, 10)));

        return "index";
    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.POST)
    public String deleteTradeItem(@RequestParam(value = "deleteId") Integer deleteId, HttpServletRequest request) {
        tradeItemService.deleteById(deleteId.longValue());

        return "redirect:" + request.getHeader("referer");
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
