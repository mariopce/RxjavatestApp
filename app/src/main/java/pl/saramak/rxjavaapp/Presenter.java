package pl.saramak.rxjavaapp;


import io.reactivex.Observable;

public class Presenter {


    NetworkWithCashUseCase networkWithCashUseCase;
    public Presenter() {
        networkWithCashUseCase = new NetworkWithCashUseCase();
    }

    public Observable<Content> searchCities(String country) {
        return networkWithCashUseCase.getCitiesIn(country);
    }

    public Observable<Content> lisenCache() {
        return networkWithCashUseCase.listenCash();
    }
}
