package pl.com.inzynierka.mkufunzi.models;

/**
 * Created by impresyjna on 04.01.16.
 */
public class AppUser {

    private User user = new User();
    private Protege protege = new Protege();
    private Card card = new Card();
    private static AppUser ourInstance = new AppUser();

    public static AppUser getInstance() {
        return ourInstance;
    }

    private AppUser() {
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Protege getProtege() {
        return protege;
    }

    public void setProtege(Protege protege) {
        this.protege = protege;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
