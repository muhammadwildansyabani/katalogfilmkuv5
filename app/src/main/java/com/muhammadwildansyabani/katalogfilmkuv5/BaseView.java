package com.muhammadwildansyabani.katalogfilmkuv5;

public interface BaseView <T extends BasePresenter> {
    void setPresenter(T presenter);
}
