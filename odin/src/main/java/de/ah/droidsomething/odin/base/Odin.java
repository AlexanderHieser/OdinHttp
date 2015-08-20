package de.ah.droidsomething.odin.base;

import de.ah.droidsomething.odin.requests.DELETERequest;
import de.ah.droidsomething.odin.requests.GETRequest;
import de.ah.droidsomething.odin.requests.POSTRequest;
import de.ah.droidsomething.odin.requests.PUTRequest;

/**
 * Created by Alexander Hieser on 20.08.2015.
 */
public class Odin extends AbstractOdin {
    @Override
    public POSTRequest Post() {
        return new POSTRequest(this);
    }

    @Override
    public GETRequest Get() {
        return new GETRequest();
    }

    @Override
    public DELETERequest Delete() {
        return new DELETERequest();
    }

    @Override
    public  PUTRequest Put() {
        return new PUTRequest();
    }

    @Override
    public String getAPIToken() {
        return null;
    }

    @Override
    public String getAuthenticationToken() {
        return null;
    }
}
