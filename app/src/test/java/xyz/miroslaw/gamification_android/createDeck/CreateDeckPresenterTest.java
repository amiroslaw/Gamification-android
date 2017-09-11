package xyz.miroslaw.gamification_android.createDeck;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.model.CardType;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class CreateDeckPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private Context context;
    @Mock
    private CreateDeckContract.View view;
    @InjectMocks
    private CreateDeckPresenter presenter;

    private static final int MAX_CARD_IN_DECK = 30;
    private static final Card CARD_MEDIUM = new Card(CardType.MEDIUM, "name", "description", "path");
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.presenter = new CreateDeckPresenter(view, context);
    }

    @Test
    public void shouldEnableReturningAndClearTexts_whenUserClicksNextButton() {
        presenter.onNextClick(CARD_MEDIUM.getTitle(), CARD_MEDIUM.getDescription());

        verify(view).disableReturning(false);
        verify(view).clearTexts();
    }
    @Test
    public void shouldShowEndingDialog_whenUserAddedAllCards() {
        //depreciated in mockito 2
        Whitebox.setInternalState(presenter, "cardCounter", MAX_CARD_IN_DECK);

        presenter.onNextClick(CARD_MEDIUM.getTitle(), CARD_MEDIUM.getDescription());

        verify(view).showDialog();
    }

    @Test
    public void shouldReturnLargeCard_whenCounterIs1(){
        Assert.assertEquals(CardType.LARGE, presenter.computeType(1));
    }
    @Test
    public void shouldReturnMediumCard_whenCounterIsBetween2and4(){
        Assert.assertEquals(CardType.MEDIUM, presenter.computeType(2));
    }
    @Test
    public void shouldReturnSmallCard_whenCounterIsGreaterThan4(){
        Assert.assertEquals(CardType.SMALL, presenter.computeType(5));
    }
    @Test
    public void shouldShowPrevCard_whenUserClicksPreviousButton() {
        presenter.cards.add(CARD_MEDIUM);
//        doNothing().when(view).showTypeValue(CARD_MEDIUM.getType());
//        doNothing().when(view).setPrevCardValues(CARD_MEDIUM.getTitle(), CARD_MEDIUM.getDescription(), CARD_MEDIUM.getImage());

        presenter.onPrevClick();

        verify(view).showTypeValue(CARD_MEDIUM.getType());
        verify(view).setPrevCardValues(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
    @Test
    public void shouldDisableReturning_whenUserGoBackToFirstCard() {
        Whitebox.setInternalState(presenter, "cardCounter", 1);
        presenter.cards.add(CARD_MEDIUM);
        presenter.onPrevClick();

        verify(view).disableReturning(true);
    }


}
