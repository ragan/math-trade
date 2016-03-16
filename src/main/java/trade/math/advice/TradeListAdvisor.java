package trade.math.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import trade.math.model.TradeListStatus;
import trade.math.service.TradeListService;

@ControllerAdvice
public class TradeListAdvisor {

    @Autowired
    private TradeListService tradeListService;

    @ModelAttribute(value = "listStatus")
    public TradeListStatus tradeListState() {
        return tradeListService.getTradeListStatus();
    }

}
