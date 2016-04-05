package trade.math.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import trade.math.domain.tradeItem.TradeItem;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.domain.tradeItem.wantListItem.WantListItem;
import trade.math.domain.tradeItem.wantListItem.WantListItemDTO;
import trade.math.domain.tradeItem.wantListItem.WantListItemService;

import java.security.Principal;
import java.util.List;

/**
 * Created by daniel on 25.03.16.
 */
@Controller
public class WantListController {

    private TradeItemService tradeItemService;
    private WantListItemService wantListItemService;


    @Autowired
    public WantListController(TradeItemService tradeItemService, WantListItemService wantListItemService) {
        this.tradeItemService = tradeItemService;
        this.wantListItemService = wantListItemService;
    }

    @RequestMapping(value = "/wantList", method = RequestMethod.GET)
    public String wantListComposer(Model model, Principal principal) {
        model.addAttribute("myGames", tradeItemService.findByRecentTradeListAndOwner(principal.getName()));

        return "wantList";
    }

    @RequestMapping(value = "/wantList/getList.command", method = RequestMethod.POST)
    @ResponseBody
    public List<WantListItemDTO> getWantListItems(@RequestParam Long itemId) {
        return wantListItemService.findWantListByOfferTradeItem(tradeItemService.findById(itemId));
    }

    @RequestMapping(value = "/wantList/saveList.command", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveWantListItems(@RequestParam Long itemId, @RequestParam(value = "wantIds[]", required = false) Long[] wantIds) {
        return tradeItemService.updateWantList(itemId, (wantIds == null ? new Long[0] : wantIds));
    }


    @RequestMapping("/wantList/findItemById")
    @ResponseBody
    public WantListItemDTO findItemById(@RequestParam long id){
        return tradeItemService.findByIdWantItem(id);
    }

    @RequestMapping("/wantList/getListTM.command")
    @ResponseBody
    public String getWantListTM(Principal principal){
        return tradeItemService.generateTradeWantListTM(principal.getName());
    }
}
