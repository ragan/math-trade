package trade.math.domain.tradeList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class TradeListStatusDTOTest {

    @Mock
    private TradeList tradeListMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testWhenTradeListIsClosed() throws Exception {
        when(tradeListMock.getState()).thenReturn(TradeListState.CLOSED);
        TradeListStatusDTO status = new TradeListStatusDTO(tradeListMock);

        assertThat(status.canClose(), is(false));
        assertThat(status.canOpen(), is(true));

        assertThat(status.canGroup(), is(true));
        assertThat(status.canTrade(), is(true));
    }

    @Test
    public void testWhenTradeListIsOpen() throws Exception {
        when(tradeListMock.getState()).thenReturn(TradeListState.OPEN);
        TradeListStatusDTO status = new TradeListStatusDTO(tradeListMock);

        assertThat(status.canClose(), is(true));
        assertThat(status.canOpen(), is(false));

        assertThat(status.canGroup(), is(true));
        assertThat(status.canTrade(), is(false));
    }

    @Test
    public void testWhenTradeListIsArchival() throws Exception {
        when(tradeListMock.getState()).thenReturn(TradeListState.DONE);
        TradeListStatusDTO status = new TradeListStatusDTO(tradeListMock);

        assertThat(status.canClose(), is(false));
        assertThat(status.canOpen(), is(false));

        assertThat(status.canGroup(), is(false));
        assertThat(status.canTrade(), is(false));
    }
}