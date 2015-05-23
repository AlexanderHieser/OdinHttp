package de.ah.droidsomething.odin.interfaces;

import org.apache.http.Header;
import org.apache.http.NameValuePair;

import java.util.ArrayList;


/**
 * Created by Alexander Hieser on 23.05.2015.
 */
public interface IOdinRequest<E> {

    E setURL(String url);
    E setURLParams(ArrayList<NameValuePair> params);
    E setURLParams(NameValuePair... params);
    E setHeaders(Header... headers);
    E setHeaders(ArrayList<Header> headers);
    E clearHeaders();
    E clearURLParams();

    boolean isValid();
}
