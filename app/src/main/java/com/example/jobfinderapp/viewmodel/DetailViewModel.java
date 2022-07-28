package com.example.jobfinderapp.viewmodel;

import com.example.jobfinderapp.repository.local.entity.Result;
import com.example.jobfinderapp.ui.base.BaseViewModel;

public class DetailViewModel extends BaseViewModel {
    private Result result;

    public DetailViewModel() {
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
