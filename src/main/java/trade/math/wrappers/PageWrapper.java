package trade.math.wrappers;

import java.util.List;

/**
 * Created by daniel on 22.02.16.
 */
public interface PageWrapper<T> {

    List<T> getItems();

    List<Integer> getPagination();

    Integer getActualPage();

    Integer getPageCount();

}
