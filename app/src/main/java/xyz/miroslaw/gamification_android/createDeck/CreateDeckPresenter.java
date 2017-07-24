package xyz.miroslaw.gamification_android.createDeck;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import xyz.miroslaw.gamification_android.database.DatabaseManager;
import xyz.miroslaw.gamification_android.database.dao.DeckDao;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.model.CardType;
import xyz.miroslaw.gamification_android.model.Deck;

public class CreateDeckPresenter implements CreateDeckContract.Presenter {
    private  final int maxCardInDeck = 11;
    private final String DEBUGTAG = "myDebug "+getClass().getSimpleName();


    private CreateDeckContract.FormView formView;
    private CreateDeckContract.NavigationView navigationView;
    private List<Card> cards = new ArrayList<>();
    private String imgPath = "";
    private int cardCounter = 1;
    private DeckDao deckDao;
    public CreateDeckPresenter(CreateDeckContract.NavigationView view){
        this.navigationView = view;
        view.setPresenter(this);
    }
    public CreateDeckPresenter(CreateDeckContract.FormView view, Context context){
        this.formView = view;
        view.setPresenter(this);
        deckDao = new DeckDao(context);
    }

    @Override
    public void onNextClick(String name, String description) {
        Log.d(DEBUGTAG, "nextCard: name " + name +" desc "+ description );
        if(cardCounter <= maxCardInDeck) {
            CardType type = getType();
            cards.add(new Card(type, name, description, imgPath));
            Log.d(DEBUGTAG, "nextCard: type " + type +" desc "+ description );
//            navigationView.setTxtTypeValue(type.toString());
            if (navigationView == null){
                Log.d(DEBUGTAG, "onNextClick: navView is null" );
            }
            navigationView.setTxtTypeValue("test");
            formView.clearTexts();
            cardCounter++;
        } else {
//           showDialog();
//            saveDeck();
        }
    }

    private CardType getType() {
        if(cardCounter == 1) return CardType.LARGE;
        if(cardCounter > 1 && cardCounter < 5) return CardType.MEDIUM;
        return CardType.SMALL;
    }

    @Override
    public void onPrevClick() {
        navigationView.setTxtTypeValue("prev");
    }


    @Override
    public void onImgBtnClick(String imgPath) {
        this.imgPath = imgPath;
//        formView.changeTxtGetImg("switch");
//        formView.showPhoto(pathImg);
    }

    private void saveDeck(){

//        CardDao cardDao = new CardDao();

        Deck deck = new Deck("from presenter");
//        Deck deck = deckDao.findById(5);

        Card card = new Card(CardType.SMALL, "type test");
        card.setDeck(deck);
        deckDao.createOrUpdate(deck);
//        cardDao.createOrUpdate(card);
//        List<Deck> decks = deckDao.findAll();
//        Deck fetchDeck = deckDao.findById(1);
        Log.d(DEBUGTAG, "onCreate: how many deck " + deckDao.findAll().size());
//        Log.d(DEBUGTAG, "onCreate: how many card " + cardDao.findAll().size());
//        Log.d(DEBUGTAG, "onCreate: how many deck " + fetchDeck.getDeckName());
        DatabaseManager.releaseHelper();
    }

}
