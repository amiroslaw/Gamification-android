package xyz.miroslaw.gamification_android.cardEditor;


import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import xyz.miroslaw.gamification_android.database.dao.CardDao;
import xyz.miroslaw.gamification_android.database.dao.DeckDao;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.viewUtils.Item;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static xyz.miroslaw.gamification_android.DataHelper.CARDS;
import static xyz.miroslaw.gamification_android.DataHelper.CARD_ITEMS;
import static xyz.miroslaw.gamification_android.DataHelper.CARD_LARGE;
import static xyz.miroslaw.gamification_android.DataHelper.DECK1;


@RunWith(MockitoJUnitRunner.class)
public class CardEditorPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    public Context context;
    @Mock
    public DeckDao deckDao;
    @Mock
    public CardDao cardDao;
    @Mock
    private CardEditorFragment cardEditorFragment;
    @Mock
    private CreateCardFragment createCardFragment;
    @InjectMocks
    private CardEditorPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void shouldReturnListItem_whenGetAdapterItemsWasCalled() {
        when(cardDao.findAllFromDeck(anyInt())).thenReturn(CARDS);

        List<Item> items = presenter.getAdapterItems(1);
        int cardsSize = items.size();

        verify(cardDao).findAllFromDeck(anyInt());
        assertEquals(cardsSize, 3);
        assertEquals(CARD_ITEMS, items);
    }

    @Test
    public void shouldDeleteCard() {
        presenter.cardList = new ArrayList<>(Collections.singletonList(CARD_LARGE));

        presenter.deleteCard(0);
        int cardsSize = presenter.cardList.size();

        verify(cardDao).delete(any(Card.class));
        assertEquals(cardsSize, 0);
    }

    @Test
    public void shouldDuplicateCard() {
        presenter.cardList = new ArrayList<>(Collections.singletonList(CARD_LARGE));
        when(cardDao.findById(anyInt())).thenReturn(CARD_LARGE);

        presenter.duplicateCard(0);
        int cardsSize = presenter.cardList.size();

        verify(cardDao).findById(anyInt());
        assertEquals(cardsSize, 2);
    }

    @Test
    public void shouldSaveCardToDB() {
        when(cardDao.findById(anyInt())).thenReturn(CARD_LARGE);

        presenter.onSaveCard(CARD_LARGE, DECK1.getId());

        verify(cardDao).createOrUpdate(any(Card.class));
    }

    @Test
    public void shouldFetchCardFromDB() {
        when(cardDao.findById(anyInt())).thenReturn(CARD_LARGE);

        presenter.getCard(CARD_LARGE.getId());

        verify(cardDao).findById(anyInt());
    }

}