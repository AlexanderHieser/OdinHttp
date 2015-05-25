package de.ah.droidsomething.odinhttp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpResponse;

import de.ah.droidsomething.odin.interfaces.CustomCallback;
import de.ah.droidsomething.odin.requests.GETRequest;
import de.ah.droidsomething.odin.requests.PUTRequest;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GETRequest().setURL("http://www.google.de").execute(OnTwo.class, new CustomCallback<OnTwo>() {
            @Override
            public void onFinish(OnTwo object) {
                Log.i("TAG",object.getOne());
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public OnTwo onPrepare(HttpResponse response) {
                OnTwo o = new OnTwo();
                o.setOne(response.getStatusLine().getReasonPhrase());
                o.setTwo("test");
                return o;
            }
        });
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
