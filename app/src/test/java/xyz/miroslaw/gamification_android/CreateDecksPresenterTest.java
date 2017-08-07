package xyz.miroslaw.gamification_android;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import xyz.miroslaw.gamification_android.createDeck.CreateDeckContract;
import xyz.miroslaw.gamification_android.createDeck.CreateDeckPresenter;
import xyz.miroslaw.gamification_android.model.CardType;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class CreateDecksPresenterTest {

    @Mock
    private Context context;
    @Mock
    private CreateDeckContract.View view;

    private CreateDeckPresenter presenter;

    @Before
    void setup(){
        MockitoAnnotations.initMocks(this);
        // add constructor
        presenter = new CreateDeckPresenter(view, context);
    }

    @Test
    void whenUserClicksNextButton(){
        presenter.onNextClick("title", "desc");
        Mockito.verify(view).clearTexts();
    }

    @Test
    void whenUserClicksPreviousButton(){
        presenter.onPrevClick();
        verify(view).showTypeValue(CardType.SMALL);
        verify(view).setPrevCardValues(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
}
