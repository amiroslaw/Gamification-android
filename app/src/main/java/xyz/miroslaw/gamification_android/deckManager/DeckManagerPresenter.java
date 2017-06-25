package xyz.miroslaw.gamification_android.deckManager;


public class DeckManagerPresenter implements DeckManagerContract.Presenter {
        private DeckManagerContract.View view;

        public DeckManagerPresenter(DeckManagerContract.View view){
            this.view = view;
            view.setPresenter(this);
        }

    }
