package droidsometing.de.odinhttp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import de.ah.droidsomething.odin.OdinResponse;
import de.ah.droidsomething.odin.base.Odin;
import de.ah.droidsomething.odin.interfaces.StringCallback;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Odin odin = new Odin();
        odin.Get().setURL("https://www.google.de/").execute(new StringCallback() {
            @Override
            public void onFinish(OdinResponse response) {
                Log.i("TEST",response.getResponseBody());
            }

            @Override
            public void onError(String error) {
                Log.i("TEST",error);

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
