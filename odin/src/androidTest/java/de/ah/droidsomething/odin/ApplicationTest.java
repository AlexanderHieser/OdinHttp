package de.ah.droidsomething.odin;

import android.test.InstrumentationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import de.ah.droidsomething.odin.interfaces.StringCallback;
import de.ah.droidsomething.odin.requests.GETRequest;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends InstrumentationTestCase {

    int statuscode ;
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }


    public void testGETRequest() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        new GETRequest().setURL("http://www.google.de").execute(new StringCallback() {

            @Override
            public void onFinish(OdinResponse response) {
                statuscode = response.getResponseStatusLines().getStatusCode();
                signal.countDown();
            }

            @Override
            public void onError(String error) {
                signal.countDown();
            }
        });
        signal.await(30, TimeUnit.SECONDS);
        assertEquals(statuscode, 200);
    }

}