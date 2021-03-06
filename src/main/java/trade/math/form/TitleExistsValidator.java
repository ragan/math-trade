package trade.math.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import trade.math.model.TradeItemCategory;
import trade.math.repository.TradeBoardGameTitleRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class TitleExistsValidator implements ConstraintValidator<TitleExistsConstraint, NewTradeItemForm> {

    @Autowired
    private TradeBoardGameTitleRepository tradeBoardGameTitleRepository;

    @Override
    public void initialize(TitleExistsConstraint constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(NewTradeItemForm newTradeItemForm, ConstraintValidatorContext context) {
        if (newTradeItemForm.getCategory() == TradeItemCategory.BOARD_GAME)
            return !newTradeItemForm.getTitle().isEmpty() &&
                    tradeBoardGameTitleRepository.findOneByTitleAllIgnoreCase(newTradeItemForm.getTitle()).isPresent();

        return !newTradeItemForm.getTitle().isEmpty();
    }
}
