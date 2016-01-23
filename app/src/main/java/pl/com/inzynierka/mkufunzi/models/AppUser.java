package pl.com.inzynierka.mkufunzi.models;

import pl.com.inzynierka.mkufunzi.bluetooth.ConnectThread;

/**
 * Singleton class used to hold whole data about present user
 */
public class AppUser {

    private User user;
    private Protege protege;
    private Card card;
    private WHBMI whbmi;
    private static AppUser ourInstance = new AppUser();
    private ConnectThread connectThread;

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

    public WHBMI getWhbmi() {
        return whbmi;
    }

    public void setWhbmi(WHBMI whbmi) {
        this.whbmi = whbmi;
    }

    public ConnectThread getConnectThread() {
        return connectThread;
    }

    public void setConnectThread(ConnectThread connectThread) {
        this.connectThread = connectThread;
    }
}
