package xyz.miroslaw.gamification_android.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Card implements BaseModel {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private CardType type;
    @DatabaseField(canBeNull = false)
    private String title;
    @DatabaseField
    private String description, image;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    private Deck deck;

    public Card() {
    }

    public Card(CardType type, String title) {
        this();
        this.type = type;
        this.title = title;
    }

    public Card(CardType type , String title, String description, String image) {
        this(type, title);
        this.description = description;
        this.image = image;
    }

    public Card(Card card) {
//        this.deck = null;
        this.title = card.title;
        this.description = card.description;
        this.type = card.type;
        this.image = card.image;
        this.deck = card.getDeck();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "type " + type + "; " + title + ": " + description;
    }

}
