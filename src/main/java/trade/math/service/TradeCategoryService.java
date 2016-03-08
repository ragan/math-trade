package trade.math.service;

import trade.math.form.NewTradeCategoryForm;
import trade.math.model.TradeCategory;

import java.util.List;

/**
 * Created by daniel on 08.03.16.
 */
public interface TradeCategoryService {

    TradeCategory save(NewTradeCategoryForm newTradeCategoryForm);

    List<TradeCategory> findAll();

    TradeCategory findById(Long id);

    boolean deleteAll();

    boolean deleteById(Long id);

}
