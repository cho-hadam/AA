package com.ninecm.aa;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private CosmeticRepository repository;
    private LiveData<List<Cosmetic>> allCosmetics;
    private Cosmetic cosmetic;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new CosmeticRepository(application);
        allCosmetics = repository.getAll();
    }

    public LiveData<List<Cosmetic>> getAll() {
        return allCosmetics;
    }

    public Cosmetic getById(int id) {
        cosmetic = repository.getById(id);
        return cosmetic;
    }

    public void insert(Cosmetic cosmetic) {
        repository.insert(cosmetic);
    }

    public void update(Cosmetic cosmetic) {
        repository.update(cosmetic);
    }

    public void delete(Cosmetic cosmetic) {
        repository.delete(cosmetic);
    }
}
