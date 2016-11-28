package pl.saramak.rxjavaapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = "MainActivity";
    Presenter presenter;
    private View errorButton;
    private EditText searchEditText;
    private ListView resultList;
    private ArrayAdapter<ContentItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorButton = findViewById(R.id.gen_error);
        searchEditText = (EditText) findViewById(R.id.city_edit_text);
        resultList = (ListView) findViewById(R.id.list);
        adapter = new CityAdapter(this, android.R.layout.simple_list_item_2, Collections.<ContentItem>emptyList());
        resultList.setAdapter(adapter);
        presenter = new Presenter();
        presenter.lisenCache().repeat(4).subscribe(new Observer<Content>() {
            @Override
            public void onSubscribe(Disposable d) {
                //show("onSubscribe " + d);
            }

            @Override
            public void onNext(Content value) {
                show("onNext c" + value);
                //                        adapter.setCities(value.getCities());
            }

            @Override
            public void onError(Throwable e) {
                show("onError c " + e);
            }

            @Override
            public void onComplete() {
                show("onComplete ");
            }
        });
        errorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.searchCities(searchEditText.getText().toString()).subscribe(new Observer<Content>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //show("onSubscribe " + d);
                    }

                    @Override
                    public void onNext(Content value) {
                        show("onNext " + value);
//                        adapter.setCities(value.getCities());
                    }

                    @Override
                    public void onError(Throwable e) {
                        show("onError " + e);
                    }

                    @Override
                    public void onComplete() {
                        show("onComplete ");
                    }
                });
            }
        });
    }

    private void show(String message) {
        Log.d(TAG, "mess: " + message);
        Toast.makeText(MainActivity.this, "mess: " + message, Toast.LENGTH_SHORT).show();
    }

    public static class CityAdapter extends ArrayAdapter<ContentItem> {

        public CityAdapter(Context context, int resource, List<ContentItem> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
            TextView text2 = (TextView) view.findViewById(android.R.id.text2);

            text1.setText(getItem(position).cityName);
            text2.setText(getItem(position).people);
            return view;
        }
    };
}
