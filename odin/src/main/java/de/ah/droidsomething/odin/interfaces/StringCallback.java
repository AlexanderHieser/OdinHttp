package de.ah.droidsomething.odin.interfaces;

import de.ah.droidsomething.odin.OdinResponse;

/**
 * Created by student on 20.05.15.
 */
public interface StringCallback {
     void onFinish(final OdinResponse response);
     void onError(final String error);
}
