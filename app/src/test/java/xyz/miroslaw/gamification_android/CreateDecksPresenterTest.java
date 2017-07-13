package xyz.miroslaw.gamification_android;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import xyz.miroslaw.gamification_android.createDeck.CreateDeckContract;
import xyz.miroslaw.gamification_android.createDeck.CreateDeckPresenter;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class CreateDecksPresenterTest {
    private CreateDeckPresenter presenter;

    @Mock
    private CreateDeckContract.NavigationView view;

    @Before
    void setup(){
        MockitoAnnotations.initMocks(this);
        // add constructor
        presenter = new CreateDeckPresenter(view);
    }

    @Test
    void whenUserClicksNextButton(){
        presenter.onNextClick();
        Mockito.verify(view).setTxtTypeValue(Mockito.anyString());
    }

    @Test
    void whenUserClicksPreviousButton(){
        presenter.onPrevClick();
        verify(view).setTxtTypeValue(Mockito.anyString());
    }
}
