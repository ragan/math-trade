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
import trade.math.domain.wantList.WantListEntryDTO;
import trade.math.domain.wantList.WantListService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class WantListController {

    private TradeItemService tradeItemService;

    private WantListService wantListItemService;

    @Autowired
    public WantListController(TradeItemService tradeItemService, WantListService wantListItemService) {
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
    public List<WantListEntryDTO> getWantListItems(@RequestParam Long itemId) {
        return wantListItemService.findEntries(
                tradeItemService.findById(itemId)).stream().map(WantListEntryDTO::new).collect(toList());
    }

    @RequestMapping(value = "/wantList/saveList.command", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveWantListItems(
            @RequestParam
            Long itemId,
            @RequestParam(value = "wantIds[]", required = false)
            Long[] wantIds
    ) {
        TradeItem item = tradeItemService.findById(itemId);
        List<TradeItem> offers = tradeItemService.findByIds(Arrays.asList(wantIds));
        wantListItemService.setWants(item, offers);
        return true;
    }


    @RequestMapping("/wantList/findItemById")
    @ResponseBody
    public WantListEntryDTO findItemById(@RequestParam long id) {
        return tradeItemService.findByIdWantItem(id);
    }

    @RequestMapping("/wantList/getListTM.command")
    @ResponseBody
    public String getWantListTM(Principal principal) {
        return tradeItemService.generateTradeWantListTM(principal.getName());
    }
}
