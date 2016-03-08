package trade.math.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.math.form.NewTradeCategoryForm;
import trade.math.model.TradeCategory;
import trade.math.repository.TradeCategoryRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by daniel on 08.03.16.
 */
@Service
@Transactional
public class SimpleTradeCategoryService implements TradeCategoryService {

    private final TradeCategoryRepository tradeCategoryRepository;

    @Autowired
    public SimpleTradeCategoryService(TradeCategoryRepository tradeCategoryRepository) {
        this.tradeCategoryRepository = tradeCategoryRepository;
    }

    @Override
    public TradeCategory save(NewTradeCategoryForm newTradeCategoryForm) {
        TradeCategory tradeCategory = new TradeCategory();

        tradeCategory.setTitle(newTradeCategoryForm.getTitle());

        return tradeCategoryRepository.save(tradeCategory);
    }

    @Override
    public List<TradeCategory> findAll() {
        return tradeCategoryRepository.findAll();
    }

    @Override
    public TradeCategory findById(Long id) {
        return tradeCategoryRepository.findOne(id);
    }

    @Override
    public boolean deleteAll() {
        tradeCategoryRepository.deleteAll();

        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        tradeCategoryRepository.delete(id);

        return true;
    }
}
