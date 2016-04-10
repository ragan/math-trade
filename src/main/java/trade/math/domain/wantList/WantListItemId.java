package trade.math.domain.wantList;

import java.io.Serializable;

/**
 * Created by daniel on 23.03.16.
 */
public class WantListItemId implements Serializable {

    private Long offerTradeItemId;

    private Long wantTradeItemId;

    public int hashCode(){
        return (int)(offerTradeItemId + wantTradeItemId);
    }

    public boolean equals(Object object){
        if(object instanceof WantListItemId){
            WantListItemId otherId = (WantListItemId) object;
            return (otherId.wantTradeItemId == this.wantTradeItemId) && (otherId.offerTradeItemId == this.offerTradeItemId);
        }
        return false;
    }
}
