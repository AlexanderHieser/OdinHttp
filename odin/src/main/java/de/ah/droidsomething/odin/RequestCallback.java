package de.ah.droidsomething.odin;

/**
 * Created by student on 20.05.15.
 */
public interface RequestCallback {

     void onFinish(final OdinResponse response);
     void onError(final String error);
}
