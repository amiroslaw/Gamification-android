package xyz.miroslaw.gamification_android.model;


import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
    public class Deck implements BaseModel {

        @DatabaseField(generatedId = true)
        private int id;
        @DatabaseField
        private int howManyBlankCards;
        @DatabaseField(unique = true, canBeNull = false)
        private String deckName;
        @DatabaseField(dataType = DataType.INTEGER)
        private int isStarted = 0;

        @ForeignCollectionField
        private ForeignCollection<Card> cards;

        public Deck() {

        }

        public Deck(String deckName) {
            this();
            this.deckName = deckName;
        }

        public Deck(int howManyCards, String deckName) {
            this(deckName);
            this.howManyBlankCards = howManyCards;
        }

        public Deck(Deck deck) {
            this.cards = deck.cards;
            this.deckName = deck.deckName;
            this.isStarted = deck.isStarted;
            this.howManyBlankCards = deck.howManyBlankCards;
        }

        public ForeignCollection<Card> getCards() {
            return cards;
        }

        public void setCards(ForeignCollection<Card> cards) {
            this.cards = cards;
        }

        public int getIsStarted() {
            return isStarted;
        }

        public void setIsStarted(int isStarted) {
            this.isStarted = isStarted;
        }

        public int getHowManyBlankCards() {
            return howManyBlankCards;
        }

        public void setHowManyBlankCards(int howManyBlankCards) {
            this.howManyBlankCards = howManyBlankCards;
        }

        public void setId(int ID) {
            this.id = ID;
        }

        public int getId() {
            return id;
        }

        public String getDeckName() {
            return deckName;
        }

        public void setDeckName(String deckName) {
            this.deckName = deckName;
        }

        @Override
        public String toString() {
            return "Decks [id=" + id + ", howManyCards=" + howManyBlankCards + ", deckName=" + deckName + ", isStarted="
                    + isStarted + ", cards=" + cards + "]";
        }
}
