package de.ah.droidsomething.odin;
import org.apache.http.Header;
import org.apache.http.StatusLine;

import java.util.ArrayList;


/**
 * Created by Alexander Hieser on 20.05.15.
 */
public class OdinResponse {

    public String getResponseBody() {
        return ResponseBody;
    }

    public StatusLine getResponseStatusLines() {
        return responseStatusLines;
    }

    public Header[] getHeaders() {
        return headers;
    }

    private String ResponseBody;
    private StatusLine responseStatusLines;
    private Header[] headers ;

    public OdinResponse(String body, StatusLine status, Header[] headers) {
        this.ResponseBody = body;
        this.responseStatusLines = status;
        this.headers = headers;
    }
}
