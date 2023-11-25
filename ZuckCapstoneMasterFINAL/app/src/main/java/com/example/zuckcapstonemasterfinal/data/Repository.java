package com.example.zuckcapstonemasterfinal.data;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository repository;
    private List<Entry> entries;

    public Repository() {
        entries = new ArrayList<>();
    }

    public static synchronized Repository getRepository(){
        if (repository == null){
            repository = new Repository();
        }
        return repository;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void addEntry (Entry entry){
        entries.add(entry);
    }
}
