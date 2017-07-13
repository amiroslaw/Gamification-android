package xyz.miroslaw.gamification_android.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

//@DatabaseTable(daoClass = CardDao.class)
@DatabaseTable
public class Card implements BaseModel {


    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private int type; // typ karty 1- nagroda 1, 2- nagroda 2, 4 nic
    @DatabaseField(canBeNull = false)
    private String title;
    @DatabaseField
    private String description, image;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    private Deck deck;

    public Card() {

    }

    public Card(int type, String title) {
        this();
        this.type = type;
        this.title = title;
    }

    public Card(int type, String title, String description, String image) {
        this(type, title);
        this.description = description;
        this.image = image;
    }
    public Card(Card card){
//        this.id = id;
        this.deck = null;
        this.type = card.type;
        this.title = card.title;
        this.description = card.description;
        this.image = card.image;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
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
