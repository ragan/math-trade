package trade.math.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.domain.tradeItem.wantListItem.WantListItem;

import java.security.Principal;
import java.util.List;

/**
 * Created by daniel on 25.03.16.
 */
@Controller
public class WantListController {

    private TradeItemService tradeItemService;

    @Autowired
    public WantListController(TradeItemService tradeItemService) {
        this.tradeItemService = tradeItemService;
    }


    @RequestMapping(value = "/wantList", method = RequestMethod.GET)
    public String wantListComposer(Model model, Principal principal) {
        model.addAttribute("myGames", tradeItemService.findByRecentTradeListAndOwner(principal.getName()));

        return "wantList";
    }

    @RequestMapping("/wantList/getList.command")
    @ResponseBody
    public List<WantListItem> getWantListItems(@RequestParam Long itemId){
        return tradeItemService.findById(itemId).getWantList();
    }

    @RequestMapping("/wantList/saveList.command")
    @ResponseBody
    public boolean saveWantListItems(@RequestParam Long itemId, @RequestParam Long[] wantIds){
        return tradeItemService.updateWantList(itemId, wantIds);
    }
}
