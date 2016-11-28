package pl.saramak.rxjavaapp;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheRepository {

    private Map<String, Content> contentHashMap = new ConcurrentHashMap<>();

    PublishSubject<Content> cache = PublishSubject.create();

    public void saveInCache(String country, Content content) {
        contentHashMap.put(country, content);
        cache.onNext(content);
    }

    public Observable<Content> checkCache(){
        return cache;
    }

    public Observable<Content> getContentFromCache(final String country) {
        return Observable.create(new ObservableOnSubscribe<Content>() {
            @Override
            public void subscribe(ObservableEmitter<Content> emiter) throws Exception {
                if (!emiter.isDisposed()) {
                    Content content = contentHashMap.get(country);
                    if (content == null) {
                        emiter.onError(new Exception("Empty cache"));
//                        cache.onError(new Exception("cache"));
                    } else {
                      emiter.onNext(content);

                    }
                }
            }
        }).doOnNext(new Consumer<Content>() {
            @Override
            public void accept(Content content) throws Exception {
                cache.onNext(content);
            }
        });
    }
}
