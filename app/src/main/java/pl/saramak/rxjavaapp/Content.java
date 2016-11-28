package pl.saramak.rxjavaapp;

import java.util.ArrayList;
import java.util.List;

public class Content {
    private String country;
    private List<ContentItem> cities;

    public Content(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return country;
    }

    public List<ContentItem> getCities() {
        return new ArrayList<>();
    }
}
