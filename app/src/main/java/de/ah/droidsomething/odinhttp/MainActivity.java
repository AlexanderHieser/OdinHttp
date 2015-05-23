package de.ah.droidsomething.odinhttp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import de.ah.droidsomething.odin.OdinResponse;
import de.ah.droidsomething.odin.interfaces.IOdinJSONMapping;
import de.ah.droidsomething.odin.interfaces.RequestCallback;
import de.ah.droidsomething.odin.requests.GETRequest;
import de.ah.droidsomething.odin.requests.POSTRequest;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GETRequest().setURL("http://www.google.de").execute(new RequestCallback() {
            @Override
            public void onFinish(OdinResponse response) {
                Log.i("TEST", response.getResponseBody());
            }

            @Override
            public void onError(String error) {
                Log.i("TEST", error);

            }
        });
        
        new POSTRequest().setURL("http://www.google.de").executeJSONMapping(new IOdinJSONMapping<GETRequest>() {
            @Override
            public void onFinish(GETRequest object) {

            }

            @Override
            public void onError(String error) {

            }
        },GETRequest.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
