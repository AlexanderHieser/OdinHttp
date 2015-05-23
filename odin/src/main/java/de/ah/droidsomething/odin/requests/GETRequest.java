package de.ah.droidsomething.odin.requests;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import de.ah.droidsomething.odin.OdinResponse;
import de.ah.droidsomething.odin.interfaces.RequestCallback;
import de.ah.droidsomething.odin.base.AbstractRequest;

/**
 * Created by Alex on 23.05.2015.
 */
public class GETRequest extends AbstractRequest<GETRequest> {

    private final String TAG = "GETRequest";
    private RequestCallback requestCallback;
    private HttpGet get;
    private OdinResponse response;

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

    /**
     * Executes final request and make callbacks
     */
    private void executeRequest() {
        new AsyncTask<Void,Void,OdinResponse>() {
            @Override
            protected OdinResponse doInBackground(Void... voids) {
                DefaultHttpClient client = new DefaultHttpClient();
                try {
                    HttpResponse httpResponse = client.execute(get);
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

    /**
     * Initialize this request before executing
     */
    private boolean initRequest() {
        String urlparams = URLEncodedUtils.format(getURLParams(), "UTF-8");
        Log.i("URL", getURL() + urlparams);
        Header[] headers = new Header[getHeaders().size()];
        getHeaders().toArray(headers);
        get = new HttpGet(getURL()+"?"+urlparams);
        get.setHeaders(headers);
        return true;
    }
}
