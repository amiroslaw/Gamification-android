package xyz.miroslaw.gamification_android;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import xyz.miroslaw.gamification_android.createDeck.CreateDeckContract;
import xyz.miroslaw.gamification_android.createDeck.CreateDeckPresenter;
import xyz.miroslaw.gamification_android.database.dao.CommonDao;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.model.CardType;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class CreateDeckPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private Context context;
    @Mock
    private CommonDao commonDao;
    @Mock
    private CreateDeckContract.View view;

    private CreateDeckPresenter presenter;

    public static final int MAX_CARD_IN_DECK = 30;
    public static final Card CARD_MEDIUM = new Card(CardType.MEDIUM, "name", "description", "path");
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        presenter = new CreateDeckPresenter(view, context);
    }

    @Test
    public void shouldEnableReturningAndClearTexts_whenUserClicksNextButton() {
        presenter.onNextClick(CARD_MEDIUM.getTitle(), CARD_MEDIUM.getDescription());

        verify(view).disableReturning(false);
        verify(view).clearTexts();
    }
    @Test
    public void shouldShowEndingDialog_whenUserAddedAllCards() {
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
    // TODO: convert to spy?
    @Test
    public void shouldShowPrevCard_whenUserClicksPreviousButton() {
//        Whitebox.setInternalState(presenter, "card", CARD_MEDIUM);

        doNothing().when(view).showTypeValue(CARD_MEDIUM.getType());
        doNothing().when(view).setPrevCardValues(CARD_MEDIUM.getTitle(), CARD_MEDIUM.getDescription(), CARD_MEDIUM.getImage());

        presenter.onPrevClick();

        verify(view).showTypeValue(CARD_MEDIUM.getType());
        verify(view).setPrevCardValues(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
    @Test
    public void shouldDisableReturning_whenUserGoBackToFirstCard() {
        Whitebox.setInternalState(presenter, "cardCounter", 1);

        presenter.onPrevClick();

        verify(view).disableReturning(true);
    }


}
