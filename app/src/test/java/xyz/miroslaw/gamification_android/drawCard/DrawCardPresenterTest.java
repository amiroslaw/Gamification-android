package xyz.miroslaw.gamification_android.drawCard;


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

import xyz.miroslaw.gamification_android.database.dao.CardDao;
import xyz.miroslaw.gamification_android.database.dao.DeckDao;
import xyz.miroslaw.gamification_android.model.Deck;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static xyz.miroslaw.gamification_android.DataHelper.CARDS;
import static xyz.miroslaw.gamification_android.DataHelper.CARD_LARGE;
import static xyz.miroslaw.gamification_android.DataHelper.DECK1;


@RunWith(MockitoJUnitRunner.class)
public class DrawCardPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    public Context context;
    @Mock
    public DeckDao deckDao;
    @Mock
    public CardDao cardDao;
    @Mock
    private DrawCardFragment view;
    @InjectMocks
    private DrawCardPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void shouldFetchAndSwapCards_whenInit() {
        when(deckDao.findById(anyInt())).thenReturn(DECK1);
        when(cardDao.findAllFromDeck(anyInt())).thenReturn(CARDS);
        presenter.cards = new ArrayList<>(CARDS);

        presenter.initDeck(anyInt());

        verify(deckDao).findById(anyInt());
        verify(cardDao).findAllFromDeck(anyInt());
        assertEquals(CARD_LARGE, presenter.cards.get(0));
    }

    @Test
    public void shouldUpdateDeck() {
        presenter.saveParametersInDB();

        verify(deckDao).createOrUpdate(any(Deck.class));
    }

    @Test
    public void shouldChangeActivity_whenAreNoCards() {
        presenter.cards = new ArrayList<>();
        presenter.deck = new Deck();

        presenter.drawCard();

        verify(view).onExit();
    }

    @Test
    public void shouldShowLargeAward_whenAreOneAward() {
        presenter.cards = new ArrayList<>(Collections.singletonList(CARD_LARGE));
        presenter.deck = new Deck();
        presenter.deck.setHowManyBlankCards(0);

        presenter.drawCard();
        int cardsSize = presenter.cards.size();

        verify(cardDao).deleteById(anyInt());
        verify(view).showAward(CARD_LARGE);
        assertEquals(cardsSize, 0);
    }

    @Test
    public void shouldShowCardCounter_whenAreManyCards() {
        presenter.cards = new ArrayList<>(CARDS);
        presenter.deck = new Deck();
        presenter.deck.setHowManyBlankCards(4);

        presenter.drawCard();

        verify(view).showCardCounter(6);
    }

    @Test
    public void shouldReturnTrue_whenIsAnyDeck() {
        when(deckDao.countAll()).thenReturn(3);

        boolean isDeck = presenter.isAnyDeck();

        assertEquals(true, isDeck);
    }

    @Test
    public void shouldReturnFalse_whenAreNotAnyDecks() {
        when(deckDao.countAll()).thenReturn(0);

        boolean isDeck = presenter.isAnyDeck();

        assertEquals(false, isDeck);
    }

}