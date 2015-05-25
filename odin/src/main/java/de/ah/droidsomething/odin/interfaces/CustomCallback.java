package de.ah.droidsomething.odin.interfaces;

import org.apache.http.HttpResponse;

/**
 * Created by Alex on 25.05.2015.
 */
public interface CustomCallback<T extends Object> {

    void onFinish(T object);
    void onError(String error);
    T onPrepare(HttpResponse response);
}
