package pl.saramak.rxjavaapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = "MainActivity";
    Presenter presenter;
    private View errorButton;
    private EditText searchEditText;
    private RecyclerView mRecyclerCitiesView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mCitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorButton = findViewById(R.id.gen_error);
        searchEditText = (EditText) findViewById(R.id.city_edit_text);
        mRecyclerCitiesView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerCitiesView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerCitiesView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mCitiesAdapter = new MyAdapter();
        ArrayList<ContentItem> ci = new ArrayList<>();
        ci.add(new ContentItem("Warszawa", 100000));
        mCitiesAdapter.setData(ci);
        mRecyclerCitiesView.setAdapter(mCitiesAdapter);
        presenter = new Presenter();
        presenter.lisenCache().repeat(4).subscribe(new Observer<Content>() {
            @Override
            public void onSubscribe(Disposable d) {
                //show("onSubscribe " + d);
            }

            @Override
            public void onNext(Content value) {
                show("onNext c" + value);
                mCitiesAdapter.setData(value.getCities());
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

}
