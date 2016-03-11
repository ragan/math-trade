package trade.math.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trade.math.model.TradeItem;
import trade.math.model.TradeList;
import trade.math.model.TradeListState;
import trade.math.repository.TradeListRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TradeListServiceImpl implements TradeListService {

    private final TradeListRepository tradeListRepository;

    @Autowired
    public TradeListServiceImpl(TradeListRepository tradeListRepository) {
        this.tradeListRepository = tradeListRepository;
    }

    @Override
    public TradeList createNewList() {
        TradeList tradeList = new TradeList();
        tradeList.setCreationTime(new Date());
        tradeList.setState(TradeListState.OPEN);
        return tradeListRepository.save(tradeList);
    }

    @Override
    public List<TradeList> findAll() {
        return tradeListRepository.findAll();
    }

    @Override
    @Transactional
    public TradeList findMostRecentList() {
        return tradeListRepository.findRecentList();
    }

    @Override
    public void deleteAll() {
        tradeListRepository.deleteAll();
    }

    @Override
    public void setState(TradeListState tradeListState) {
        TradeList list = tradeListRepository.findRecentList();
        list.setState(tradeListState);
        tradeListRepository.save(list);
    }

    @Override
    public TradeList findById(Long tradeListId) {
        return tradeListRepository.findOne(tradeListId);
    }

}
