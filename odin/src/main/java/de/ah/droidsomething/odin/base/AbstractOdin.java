package de.ah.droidsomething.odin.base;

import dalvik.system.DexClassLoader;
import de.ah.droidsomething.odin.requests.DELETERequest;
import de.ah.droidsomething.odin.requests.GETRequest;
import de.ah.droidsomething.odin.requests.POSTRequest;
import de.ah.droidsomething.odin.requests.PUTRequest;

/**
 * Created by Alexander Hieser on 20.08.2015.
 */
public abstract class AbstractOdin implements  IOdin{

    public abstract POSTRequest Post();
    public abstract GETRequest  Get();
    public abstract DELETERequest Delete();
    public abstract PUTRequest Put();
}
