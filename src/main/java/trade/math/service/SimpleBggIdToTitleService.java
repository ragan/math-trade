package trade.math.service;

import org.springframework.stereotype.Service;

/**
 * Created by karol on 17.02.16.
 */
@Service
public class SimpleBggIdToTitleService implements BggIdToTitleService {
    @Override
    public String getTitle(int bggId) {
        return "title";
    }
}
