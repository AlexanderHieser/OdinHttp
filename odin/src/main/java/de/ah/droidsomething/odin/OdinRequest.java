package de.ah.droidsomething.odin;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alexander Hieser on 20.05.15.
 */
public class OdinRequest {

    /** Attributes **/
    private OdinMethods Method;
    private String Encoding = "UTF-8";
    private String URL;
    private ArrayList<NameValuePair> parameters;
    private ArrayList<Header> headers;

    /** Methods **/

    public OdinRequest() {
        this.parameters = new ArrayList<NameValuePair>();
        this.headers = new ArrayList<Header>();
    }

    public OdinRequest setMethodType(OdinMethods method) {
        this.Method = method;
        return this;
    }

    public OdinRequest setRequestURL(String url) {
        this.URL = url;
        return this;
    }

    public OdinRequest setMethodType(String method) {
        try {
            OdinMethods odinMethods = parseMethods(method);
            this.setMethodType(odinMethods);
            return this;
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this;
        }
    }

    public OdinRequest setRequestParams(ArrayList<NameValuePair> params) {
        for (NameValuePair param : params) {
             parameters.add(param);
        }
        return  this;
    }

    public OdinRequest setHeaders(Header... h) {
        for(Header header : h) {
            headers.add(header);
        }
        return this;
    }

    public void clearHeaders() {
        this.headers.clear();
    }

    public void clearParameters() {
        parameters.clear();;
    }

    public void execute(final RequestCallback callback) {
        if(isValid()) {
        new AsyncTask<Void,Void,HttpResponse>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(HttpResponse httpResponse) {
                super.onPostExecute(httpResponse);
                if(httpResponse != null) {
                    OdinResponse easyResponse = parseResponse(httpResponse);
                    if(easyResponse == null){
                        callback.onError("An error occured. Cant parse HTTPResponse");
                    }
                    callback.onFinish(easyResponse);
                }
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(HttpResponse httpResponse) {
                super.onCancelled(httpResponse);
            }


            @Override
            protected void onCancelled() {
                super.onCancelled();
            }

            @Override
            protected HttpResponse doInBackground(Void... params) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response;
                    switch (Method) {
                        case GET: {
                            HttpGet get = createGETRequest();
                            response = client.execute(get);
                            return  response;

                        }
                        case POST: {
                            HttpPost post = createPOSTRequest();
                            response = client.execute(post);
                            return response;
                        }
                        case PUT: {
                            HttpPut put = createPUTRequest();
                            response = client.execute(put);
                            return response;
                        }
                        case DELETE: {
                            HttpDelete delete = createDELETERequest();
                            response = client.execute(delete);
                            return response;
                        }
                    }
                }catch(IOException exception) {
                    callback.onError(exception.getMessage());
                }
                callback.onError("An error occured. Response is null");
                return null;
            }
        }.execute();
        }
    }


    /**
     * Creates GET Request with URL Parameters etc.
     * @return
     */
    private HttpGet createGETRequest(){
        String urlparams = URLEncodedUtils.format(this.parameters,Encoding);
        Log.i("URL",this.URL+"?"+urlparams);
        HttpGet get = new HttpGet(this.URL+"?"+urlparams);
        Header[] headers = new Header[this.headers.size()];
        get.setHeaders((this.headers.toArray(headers)));
        return get;
    }

    /**
     * Creates POST Request with URL Parameters etc.
     * @return
     */
    private HttpPost createPOSTRequest(){
        String urlparams = URLEncodedUtils.format(this.parameters,Encoding);
        Log.i("URL",this.URL+"?"+urlparams);
        HttpPost post = new HttpPost(this.URL+urlparams);
        Header[] headers = new Header[this.headers.size()];
        post.setHeaders((this.headers.toArray(headers)));
        return post;
    }

    /**
     * Creates PUT Request with URL Parameters etc.
     * @return
     */
    private HttpPut createPUTRequest(){
        String urlparams = URLEncodedUtils.format(this.parameters,Encoding);
        Log.i("URL",this.URL+urlparams);
        HttpPut put = new HttpPut(this.URL+urlparams);
        Header[] headers = new Header[this.headers.size()];
        put.setHeaders((this.headers.toArray(headers)));
        return put;
    }

    /**
     * Creates Delete Request with URL Parameters etc.
     * @return
     */
    private HttpDelete createDELETERequest(){
        String urlparams = URLEncodedUtils.format(this.parameters,Encoding);
        Log.i("URL",this.URL+urlparams);
        HttpDelete delete = new HttpDelete(this.URL+urlparams);
        Header[] headers = new Header[this.headers.size()];
        delete.setHeaders((this.headers.toArray(headers)));
        return delete;
    }


    /**
     * Parse incoming response to OdinResponse
     * @param response
     * @return
     */
    private OdinResponse parseResponse(HttpResponse response) {
        try {
            String body = EntityUtils.toString(response.getEntity());
            OdinResponse rs = new OdinResponse(body,response.getStatusLine(), response.getAllHeaders());
            return  rs;
        }catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * Parse the given String into Method Enums
     * @param method
     * @return
     * @throws IllegalArgumentException
     */
    private OdinMethods parseMethods(String method) throws IllegalArgumentException{
        if(method.equals("GET")) {
            return OdinMethods.GET;
        }
        if(method.equals("POST")) {
            return OdinMethods.POST;
        }
        if(method.equals("PUT")){
            return OdinMethods.PUT;
        }
        if(method.equals("DELETE")){
            return OdinMethods.DELETE;
        }
        throw new IllegalArgumentException("Cant parse given HTTP Method string");
    }

    /**
     * Validates the Request
     * @return
     */
    private boolean isValid(){
        if(this.Method == null) {
            throw new IllegalArgumentException("Error, empty HTTP Method");
        }
        return  true;
    }

}
