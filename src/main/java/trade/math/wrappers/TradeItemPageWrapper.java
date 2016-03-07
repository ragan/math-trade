package trade.math.wrappers;

import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import trade.math.TradeUserRole;
import trade.math.model.TradeItem;
import trade.math.model.TradeUser;
import trade.math.model.dto.TradeItemDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by daniel on 22.02.16.
 */
public class TradeItemPageWrapper implements PageWrapper<TradeItemDTO> {

    private List<TradeItemDTO> items;
    private Pageable pageable;
    private long itemCount;
    private int actualPage;

    public TradeItemPageWrapper(List<TradeItem> items, Pageable pageable, long itemCount, boolean isAdmin, String userName) {

        this.items = prepareTradeItemDTOList(items, isAdmin, userName);
        this.pageable = pageable;
        this.itemCount = itemCount;

        actualPage = Math.max(1, Math.min(getPageCount(), pageable.getPageNumber() + 1));

    }

    @Override
    public List<TradeItemDTO> getItems() {
        return items;
    }

    @Override
    public List<Integer> getPagination() {
        return getPaginationList();
    }

    @Override
    public Integer getActualPage() {
        return actualPage;
    }

    @Override
    public Integer getPageCount() {

        float count = itemCount;
        if (count == 0)
            count = 1;

        return (int) Math.ceil(count / pageable.getPageSize());
    }

    //Helpers
    private List<Integer> getPaginationList() {
        int paginatorLength = 5;

        int pageCount = getPageCount();

        ArrayList<Integer> list = new ArrayList<Integer>();

        //len 5
        //active 3
        //pages 10
        // 1 2 _3_ 4 5 ... 10

        //len 5
        //active 7
        //pages 10
        // 1 ... 5 6 _7_ 8 9 ... 10
        int page = actualPage - (paginatorLength - 1) / 2;

        if (page > 1) {
            list.add(1);
            list.add(-1);
        }

        if (page < 1)
            page = 1;
        else if (page > pageCount - paginatorLength + 1)
            page = pageCount - paginatorLength + 1;


        for (int i = 0; i < paginatorLength; i++) {
            if (page > pageCount)
                break;
            list.add(page);
            page++;
        }

        if (page < pageCount + 1) {
            list.add(-1);
            list.add(pageCount);
        }

        return list;
    }

    private List<TradeItemDTO> prepareTradeItemDTOList(List<TradeItem> listTI, boolean isAdmin, String userName) {
//        List<TradeItemDTO> list = new ArrayList<>();
//        TradeItemDTO dto;

        return listTI.stream().map(ti -> {
            TradeItemDTO dto = new TradeItemDTO();
            dto.setId(ti.getId());
            dto.setTitle(ti.getTitle());
            dto.setDescription(ti.getDescription());
            dto.setImgUrl(ti.getImgUrl());
            dto.setCanDelete(checkPermission(ti.getOwner(), isAdmin, userName));
            return dto;
        }).collect(Collectors.toList());

//        for (TradeItem tradeItem : listTI) {
//            dto = new TradeItemDTO();
//
//            dto.setId(tradeItem.getId());
//            dto.setTitle(tradeItem.getTitle());
//            dto.setDescription(tradeItem.getDescription());
//            dto.setImgUrl(tradeItem.getImgUrl());
//            dto.setCanDelete(checkPermission(tradeItem.getOwner(), isAdmin, userName));
//
//            list.add(dto);
//        }
//        return list;
    }

    private boolean checkPermission(TradeUser owner, boolean isAdmin, String userName) {
        if (isAdmin)
            return true;

        if (userName.isEmpty())
            return false;

        return userName.equals(owner.getUsername());
    }
}
