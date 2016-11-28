package pl.saramak.rxjavaapp;


import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import java.util.concurrent.atomic.AtomicInteger;

public class NetworkRepository {

    private static final String TAG = "NetworkRepository";
    AtomicInteger i = new AtomicInteger(0);

    public NetworkRepository() {

    }



    public Observable<Content> downloadContent(final String country) {
        return Observable.create(new ObservableOnSubscribe<Content>() {
            @Override
            public void subscribe(ObservableEmitter<Content> e) throws Exception {
                if (!e.isDisposed()) {
                    i.incrementAndGet();
                    doHugeWork();
                    if (i.get() % 2 == 0) {
                        Log.d(TAG, "NetworkRepository onNext " + i.get() + "  e " +  e.isDisposed() + "");
                        e.onNext(new Content(country));
                    }else
                    {
                        Log.d(TAG, "NetworkRepository onError " + i.get() + "  e " +  e.isDisposed() + "");
                        e.onError(new Exception("blad " + country));
                    }

                }
            }
        });
    }

    private void doHugeWork() throws InterruptedException {
        Thread.sleep(1 *1000);
    }
}
