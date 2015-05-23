package de.ah.droidsomething.odin.base;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

import de.ah.droidsomething.odin.interfaces.IOdinRequest;
import de.ah.droidsomething.odin.OdinResponse;

/**
 * Created by Alexander Hieser on 23.05.2015.
 */

public abstract class AbstractRequest<T> implements IOdinRequest<T> {

    private String URL;
    private ArrayList<NameValuePair> URLParams;
    private ArrayList<Header> Headers;

    public AbstractRequest() {
        this.URLParams = new ArrayList<NameValuePair>();
        this.Headers = new ArrayList<Header>();
    }

    @Override
    public T setURL(String url) {
        this.URL = url;
        return (T)this;
    }

    @Override
    public T setURLParams(ArrayList<NameValuePair> params) {
        this.URLParams.addAll(params);
        return (T)this;
    }

    @Override
    public T setURLParams(NameValuePair... params) {
        for (NameValuePair pair : params) {
            this.URLParams.add(pair);
        }
        return (T)this;

    }

    @Override
    public T setHeaders(Header... headers) {
        for(Header header : headers) {
            this.Headers.add(header);
        }
        return (T)this;

    }

    @Override
    public T setHeaders(ArrayList<Header> headers) {
        this.Headers.addAll(headers);
        return (T)this;

    }

    @Override
    public T clearHeaders() {
        this.Headers.clear();
        return (T)this;

    }

    @Override
    public T clearURLParams() {
        this.URLParams.clear();
        return (T)this;

    }

    public OdinResponse parseResponse(HttpResponse response) {
        try {
            String body = EntityUtils.toString(response.getEntity());
            OdinResponse rs = new OdinResponse(body,response.getStatusLine(), response.getAllHeaders());
            return  rs;
        }catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isValid() {
        if(this.URL != null && this.URL.startsWith("http://") || this.URL.startsWith("https://")) {
            return true;
        }
        throw  new IllegalArgumentException("Wrong Host in URL");
    }


    public String getURL() {
        return URL;
    }

    public ArrayList<NameValuePair> getURLParams() {
        return URLParams;
    }

    public ArrayList<Header> getHeaders() {
        return Headers;
    }
}
