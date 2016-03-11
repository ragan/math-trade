package trade.math;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import trade.math.form.NewTradeUserForm;
import trade.math.service.TradeUserService;

@Component
public class SetDefaultAdminApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TradeUserService tradeUserService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!tradeUserService.findByUsername("admin").isPresent()) {
            tradeUserService.save(new NewTradeUserForm("admin", "admin@email", "admin", "admin"),
                    TradeUserRole.ROLE_ADMIN);
        }
    }
}
