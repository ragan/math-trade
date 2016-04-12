package trade.math.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import trade.math.domain.tradeItem.TradeItem;
import trade.math.domain.tradeItem.TradeItemDTO;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.domain.tradeUser.TradeUserService;
import trade.math.domain.wantList.WantListDTO;
import trade.math.domain.wantList.WantListEntryDTO;
import trade.math.domain.wantList.WantListService;
import trade.math.model.TradeUser;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class WantListController {

    private final TradeItemService tradeItemService;

    private final WantListService wantListService;

    private final TradeUserService tradeUserService;

    @Autowired
    public WantListController(
            TradeItemService tradeItemService,
            WantListService wantListService,
            TradeUserService tradeUserService
    ) {
        this.tradeItemService = tradeItemService;
        this.wantListService = wantListService;
        this.tradeUserService = tradeUserService;
    }

    @RequestMapping(value = "/wantList", method = RequestMethod.GET)
    public String wantListComposer(Model model, Principal principal) {
        TradeUser user = tradeUserService.findByUsername(principal.getName());
        List<TradeItem> items = tradeItemService.findByOwner(user);
        List<WantListDTO> dtos = wantListService.findByItems(items).stream().map(WantListDTO::new).collect(toList());
        model.addAttribute("wantLists", dtos);

        return "wantList";
    }

    @RequestMapping(value = "/wantList/entries", method = RequestMethod.GET)
    @ResponseBody
    public List<WantListEntryDTO> getWantListItems(@RequestParam Long id) {
        //TODO: trzeba się upewnić, że użytkownik przegląda tylko swoje listy
        return wantListService.findEntries(tradeItemService.findById(id)).stream().map(WantListEntryDTO::new)
                .collect(toList());
    }

    @RequestMapping(value = "/wantList/entries", method = RequestMethod.PUT)
    @ResponseBody
    public boolean saveWantListItems(
            @RequestParam
            Long itemId,
            @RequestParam(value = "wantIds[]", required = false)
            Long[] wantIds
    ) {
        TradeItem item = tradeItemService.findById(itemId);
        if (wantIds == null) wantIds = new Long[]{};
        List<TradeItem> offers = tradeItemService.findByIds(Arrays.asList(wantIds));
        wantListService.setWants(item, offers);
        return true;
    }


    @RequestMapping(value = "/wantList/items", method = RequestMethod.GET)
    @ResponseBody
    public TradeItemDTO findItemById(@RequestParam long id) {
        TradeItem item = tradeItemService.findById(id);
        if (item == null)
            return null;

        return new TradeItemDTO(item, false);
    }

    @RequestMapping(value = "/wantList/tradeMaximizer", method = RequestMethod.GET)
    @ResponseBody
    public String getWantListTM(Principal principal) {
        TradeUser user = tradeUserService.findByUsername(principal.getName());
        return tradeItemService.generateTradeWantListTM(user);
    }
}
