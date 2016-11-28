package pl.saramak.rxjavaapp;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class NetworkWithCashUseCase {

    NetworkRepository networkRepository = new NetworkRepository();
    CacheRepository cacheRepository = new CacheRepository();

    public Observable<Content> listenCash(){
        return cacheRepository.cache;
    }

    public Observable<Content> getCitiesIn(final String country) {
        return cacheRepository.getContentFromCache(country).onErrorResumeNext(networkRepository.downloadContent(country).doOnNext(new Consumer<Content>() {
            @Override
            public void accept(Content content) throws Exception {
                cacheRepository.saveInCache(country, content);
            }
        }));
    }
}
