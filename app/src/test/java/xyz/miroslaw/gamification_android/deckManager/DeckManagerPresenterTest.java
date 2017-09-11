package xyz.miroslaw.gamification_android.deckManager;


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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import xyz.miroslaw.gamification_android.database.dao.CardDao;
import xyz.miroslaw.gamification_android.database.dao.DeckDao;
import xyz.miroslaw.gamification_android.viewUtils.Item;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static xyz.miroslaw.gamification_android.DataHelper.DECK1;
import static xyz.miroslaw.gamification_android.DataHelper.DECK2;
import static xyz.miroslaw.gamification_android.DataHelper.DECKS;
import static xyz.miroslaw.gamification_android.DataHelper.DECK_ITEMS;


@RunWith(MockitoJUnitRunner.class)
public class DeckManagerPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    public Context context;
    @Mock
    public DeckDao deckDao;
    @Mock
    public CardDao cardDao;
    @Mock
    private DeckManagerContract.View view;
    @InjectMocks
    private DeckManagerPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnTrue_whenIsAnyDeck() {
        when(deckDao.countAll()).thenReturn(3);

        boolean isDeck = presenter.isAnyDeck();

        assertEquals(true, isDeck);
    }

    @Test
    public void shouldReturnFalse_whenAreNoAnyDecks() {
        when(deckDao.countAll()).thenReturn(0);

        boolean isDeck = presenter.isAnyDeck();

        assertEquals(false, isDeck);
    }

    @Test
    public void shouldDeleteDeckAndCards_whenDeckWasDeleted() {
        presenter.decksList = new ArrayList<>(Arrays.asList(DECK1, DECK2));

        presenter.deleteDeck(1);
        int decksSize = presenter.decksList.size();

        verify(deckDao).deleteById(anyInt());
        verify(cardDao).deleteAllCardsInDeck(anyInt());
        assertEquals(decksSize, 1);
    }

    @Test
    public void shouldDeleteDeckAndCards_whenLastDeckWasDeleted() {
        presenter.decksList = new ArrayList<>(Collections.singletonList(DECK1));
        presenter.deleteDeck(0);
        int decksSize = presenter.decksList.size();

        verify(deckDao).deleteById(anyInt());
        verify(cardDao).deleteAllCardsInDeck(anyInt());
        verify(view).showNoDecksView();
        assertEquals(decksSize, 0);
    }


    @Test
    public void shouldReturnListItem_whenGetAdapterItemsWasCalled() {
        when(deckDao.findAll()).thenReturn(DECKS);

        List<Item> items = presenter.getAdapterItems();

        verify(deckDao).findAll();
        assertEquals(DECK_ITEMS, items);
    }

}