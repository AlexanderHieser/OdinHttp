package de.ah.droidsomething.odin.requests;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import de.ah.droidsomething.odin.base.AbstractOdin;
import de.ah.droidsomething.odin.base.Odin;
import de.ah.droidsomething.odin.interfaces.CustomCallback;
import de.ah.droidsomething.odin.interfaces.JSONCallback;
import de.ah.droidsomething.odin.OdinResponse;
import de.ah.droidsomething.odin.interfaces.StringCallback;
import de.ah.droidsomething.odin.base.AbstractRequest;

/**
 * Created by Alex on 23.05.2015.
 */
public class POSTRequest extends AbstractRequest<POSTRequest> {

    private final String TAG = "POSTRequest";
    private StringCallback requestCallback;
    private JSONCallback jsonRequestCallback;
    private CustomCallback customCallback;
    private Class thisType;
    private HttpPost post;
    private OdinResponse response;
    private AbstractOdin parent;

    public POSTRequest(AbstractOdin parent) {
        this.parent = parent;
    }

    /**
     * Starts asynchronous executing
     * @param callback
     */
    @Override
    public void execute(StringCallback callback) {
        this.requestCallback = callback;
        if (isValid() && initRequest()) {
            initRequest();
            new AsyncTask<Void,Void,OdinResponse>() {
                @Override
                protected OdinResponse doInBackground(Void... voids) {
                    DefaultHttpClient client = new DefaultHttpClient();
                    try {
                        HttpResponse httpResponse = client.execute(post);
                        response = parseResponse(httpResponse);
                        return response;
                    }catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                        requestCallback.onError(e.getMessage());
                    }
                    requestCallback.onError("Sorry, an unkown error occured");
                    return null;
                }

                @Override
                protected void onPostExecute(OdinResponse odinResponse) {
                    super.onPostExecute(odinResponse);
                    requestCallback.onFinish(odinResponse);
                }
            }.execute();
        }
    }

    @Override
    public <T> void execute( Class tClass,JSONCallback<T> callback) {
        this.jsonRequestCallback = callback;
        if (isValid() && initRequest()) {
            this.thisType = tClass;
            initRequest();
            new AsyncTask<Void,Void,OdinResponse>() {
                @Override
                protected OdinResponse doInBackground(Void... voids) {
                    DefaultHttpClient client = new DefaultHttpClient();
                    try {
                        HttpResponse httpResponse = client.execute(post);
                        response = parseResponse(httpResponse);

                        return response;
                    }catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                        requestCallback.onError(e.getMessage());
                    }
                    requestCallback.onError("Sorry, an unkown error occured");
                    return null;
                }

                @Override
                protected void onPostExecute(OdinResponse odinResponse) {
                    super.onPostExecute(odinResponse);
                    try{
                        Gson gson = new Gson();
                        Object o = thisType.newInstance();
                        o = gson.fromJson(odinResponse.getResponseBody(),thisType);
                        jsonRequestCallback.onFinish(o);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }
    }

    @Override
    public <T> void execute(Class tClass, CustomCallback<T> callback) {
        this.customCallback = callback;
        if (isValid() && initRequest()) {
            initRequest();
            new AsyncTask<Void, Void, HttpResponse>() {
                @Override
                protected HttpResponse doInBackground(Void... voids) {
                    DefaultHttpClient client = new DefaultHttpClient();
                    try {
                        HttpResponse httpResponse = client.execute(post);
                        return httpResponse;
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                        requestCallback.onError(e.getMessage());
                    }
                    requestCallback.onError("Sorry, an unkown error occured");
                    return null;
                }

                @Override
                protected void onPostExecute(HttpResponse response) {
                    super.onPostExecute(response);
                    Object o = customCallback.onPrepare(response);
                    customCallback.onFinish(o);
                }
            }.execute();
        }
    }

    /**
     * Parse the given Object to JSON String and sets Content-Type Header application/json
     * @param o Object to parse
     * @param tClass Class of the Object to parse
     * @return instance
     */
    public POSTRequest setJSONBody(Object o, Class tClass) {
        String jsonbody;
        Gson gson = new Gson();
        jsonbody = gson.toJson(o,tClass);
        try {
            this.post.setEntity(new StringEntity(jsonbody));
            this.setHeaders(new BasicHeader("Content-Type","application/json"));
            return this;
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot parse given object to JSON");
        }
    }

    public POSTRequest setBody(String body) {
        try {
            this.post.setEntity(new StringEntity(body));
            return this;
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot set Body");
        }
    }

    /**
     * Initialize this request before executing
     */
    private boolean initRequest() {
        String urlparams = URLEncodedUtils.format(getURLParams(), "UTF-8");
        Log.i("URL", getURL() + urlparams);
        Header[] headers = new Header[getHeaders().size()];
        getHeaders().toArray(headers);
        post = new HttpPost(getURL()+"?"+urlparams);
        post.setHeaders(headers);
        return true;
    }
}
