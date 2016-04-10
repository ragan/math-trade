package trade.math.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import trade.math.TradeUserRole;
import trade.math.bgsearch.BoardGameSearchResult;
import trade.math.domain.tradeItem.TradeItem;
import trade.math.domain.tradeItem.TradeItemDTO;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.domain.tradeUser.TradeUserService;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeUser;
import trade.math.service.TradeBoardGameService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by danielpietrzak on 15.02.2016.
 */
@Controller
public class MainController {

    private TradeItemService tradeItemService;

    private TradeUserService tradeUserService;

    private TradeBoardGameService tradeBoardGameService;

    public MainController() {
        //
    }

    @Autowired
    public MainController(TradeItemService tradeItemService, TradeUserService tradeUserService, TradeBoardGameService tradeBoardGameService) {
        this.tradeItemService = tradeItemService;
        this.tradeUserService = tradeUserService;
        this.tradeBoardGameService = tradeBoardGameService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(
            Model model,
            Authentication authentication,
            @RequestParam(value = "page", required = false)
            Optional<Integer> pageNum
    ) {
        TradeUser user = null;
        if (authentication != null) {
            try {
                user = tradeUserService.findByUsername(authentication.getName());
            } catch (UsernameNotFoundException ignored) {
            }
        }
        model.addAttribute("mainList", tradeItemService.findAllByRecentTradeList(
                new PageRequest(pageNum.orElse(1) - 1, 10), isAdmin(authentication), user));
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.POST)
    @ResponseBody
    public String deleteTradeItem(
            @RequestParam(value = "deleteId")
            Integer deleteId, Authentication auth
    ) {
        TradeUser user = tradeUserService.findByUsername(auth.getName());
        TradeItem item = tradeItemService.findById(deleteId.longValue());
        if (tradeItemService.canDelete(item, user)) {
            tradeItemService.deleteById(deleteId.longValue());
            return "success";
        }
        return "failure";
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
    public String doAddTradeItem(
            @Valid
            NewTradeItemForm newTradeItemForm,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors())
            return "addItem";
        TradeUser user = tradeUserService.findByUsername(principal.getName());
        tradeItemService.save(newTradeItemForm, user);
        return "redirect:/addItem?success";
    }

    @RequestMapping("/search")
    @ResponseBody
    public BoardGameSearchResult searchGames(@RequestParam String title) {
        return tradeBoardGameService.searchByName(title);
    }

    @RequestMapping("/searchOnTradeList")
    @ResponseBody
    public List<TradeItemDTO> searchItemsOnTradeList(@RequestParam String title, Principal principal) {
        TradeUser user = tradeUserService.findByUsername(principal.getName());
        return tradeItemService.findByRecentTradeListAndNameAndNotOwner(title, user).stream()
                .map(item -> new TradeItemDTO(item, false))
                .collect(toList());
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
                if (authority.getAuthority().equals(TradeUserRole.ROLE_ADMIN.name())) //TODO: może mieć kilka...
                    return true;

        return false;
    }
}
