package trade.math.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import trade.math.TradeUserRole;
import trade.math.bgsearch.BoardGameSearchResult;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.service.TradeBoardGameService;
import trade.math.service.TradeItemService;
import trade.math.service.TradeUserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

/**
 * Created by danielpietrzak on 15.02.2016.
 */
@Controller
public class MainController {

    private TradeItemService tradeItemService;

    private TradeUserService tradeUserService;

    private TradeBoardGameService tradeBoardGameService;

    public MainController() {
    }

    @Autowired
    public MainController(TradeItemService tradeItemService, TradeUserService tradeUserService, TradeBoardGameService tradeBoardGameService) {
        this.tradeItemService = tradeItemService;
        this.tradeUserService = tradeUserService;
        this.tradeBoardGameService = tradeBoardGameService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(Model model, Authentication authentication, @RequestParam(value = "page", required = false) Optional<Integer> pageNum) {
        model.addAttribute("mainList", tradeItemService.findAll(new PageRequest(pageNum.orElse(1) - 1, 10), isAdmin(authentication), getUserName(authentication)));

        return "index";
    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.POST)
    @ResponseBody
    public String deleteTradeItem(@RequestParam(value = "deleteId") Integer deleteId, Authentication authentication) {
        return tradeItemService.deleteById(deleteId.longValue(), isAdmin(authentication), getUserName(authentication)) ? "success" : "failure";
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
    public String doAddTradeItem(@Valid NewTradeItemForm newTradeItemForm,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {
        if (bindingResult.hasErrors())
            return "addItem";
        tradeItemService.save(newTradeItemForm, principal.getName());
//        redirectAttributes.addAttribute("success");
        return "redirect:/addItem?success";
    }

    @RequestMapping(value = "/wantList", method = RequestMethod.GET)
    public String wantListComposer() {
        return "wantList";
    }

    @RequestMapping("/search")
    @ResponseBody
    public BoardGameSearchResult searchGames(@RequestParam String title) {
        return tradeBoardGameService.searchByName(title);
    }

    //Helpers
    private String getUserName(Authentication authentication) {
        if (authentication != null)
            return authentication.getName();
        return "";
    }

    private boolean isAdmin(Authentication authentication) {

        if (authentication != null)
            for (GrantedAuthority authority : authentication.getAuthorities())
                if (authority.getAuthority().equals(TradeUserRole.ROLE_ADMIN.name()))
                    return true;

        return false;
    }
}
