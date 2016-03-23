package trade.math.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import trade.math.domain.tradeList.TradeListStatusDTO;
import trade.math.domain.tradeList.TradeListService;

@ControllerAdvice
public class TradeListAdvisor {

    @Autowired
    private TradeListService tradeListService;

    @ModelAttribute(value = "listStatus")
    public TradeListStatusDTO tradeListState() {
        return tradeListService.getTradeListStatus();
    }

}
