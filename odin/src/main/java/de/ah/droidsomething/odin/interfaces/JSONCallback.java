package de.ah.droidsomething.odin.interfaces;

import java.util.Objects;

/**
 * Created by Alexander Hieser on 23.05.2015.
 */
public interface JSONCallback<T extends Object>  {
    void onFinish(T object);
    void onError(String error);
}
