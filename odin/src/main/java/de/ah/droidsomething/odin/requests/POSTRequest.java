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
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import de.ah.droidsomething.odin.interfaces.IOdinJSONMapping;
import de.ah.droidsomething.odin.OdinResponse;
import de.ah.droidsomething.odin.interfaces.RequestCallback;
import de.ah.droidsomething.odin.base.AbstractRequest;

/**
 * Created by Alex on 23.05.2015.
 */
public class POSTRequest extends AbstractRequest<POSTRequest> {

    private final String TAG = "POSTRequest";
    private RequestCallback requestCallback;
    private IOdinJSONMapping jsonRequestCallback;
    private HttpPost post;
    private OdinResponse response;
    private Class thisType;


    /**
     * Starts asynchronous executing
     * @param callback
     */
    public void execute(RequestCallback callback) {
        this.requestCallback = callback;
        if (isValid() && initRequest()) {
            initRequest();
            executeRequest();
        }
    }

    public <T> void executeJSONMapping(IOdinJSONMapping<T> callback, Class type) {
        this.jsonRequestCallback = callback;
        if (isValid() && initRequest()) {
            this.thisType = type;
            initRequest();
        }
    }

    /**
     * Executes final request and make callbacks
     */
    private void executeRequest() {
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

    public  void executeJSON() {
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
                requestCallback.onFinish(odinResponse);
            }
        }.execute();
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
