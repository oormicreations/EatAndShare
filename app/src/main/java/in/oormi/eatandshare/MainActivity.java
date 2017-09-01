package in.oormi.eatandshare;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    float billAmt = 0;
    float shareAmt = 0;
    int people = 1;
    float tipPc = 0;
    float tipAmt = 0;
    float totalAmt = 0;
    DecimalFormat fStr = new DecimalFormat("#0.00");
    boolean updateEnable1 = true;
    boolean updateEnable2 = true;
    boolean updateEnable3 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText bAmtET = (EditText)findViewById(R.id.editBillAmt);
        EditText peopleET = (EditText)findViewById(R.id.editPeople);
        final EditText tipPcET = (EditText)findViewById(R.id.editTipPc);
        final EditText tipAmtET = (EditText)findViewById(R.id.editTipAmt);
        final EditText totalAmtET = (EditText)findViewById(R.id.editTotal);

        bAmtET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                billAmt = 0;
                if(s.length()>0) billAmt = Float.parseFloat(s.toString());
                doCalc();
                totalAmtET.setText(fStr.format(totalAmt));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        peopleET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                people = 1;
                if(s.length()>0) people = Integer.parseInt(s.toString());
                if(people < 1) people = 1;
                doCalc();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tipPcET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                updateEnable1 = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tipPc = 0;
                if(s.length() > 0) tipPc = Float.parseFloat(s.toString());
                tipAmt = billAmt * tipPc/100.0f;
                if(updateEnable2) tipAmtET.setText(fStr.format(tipAmt));
                doCalc();
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateEnable1 = true;
            }
        });

        tipAmtET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                updateEnable2 = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tipAmt = 0;
                if(s.length() > 0) tipAmt = Float.parseFloat(s.toString());
                if(billAmt > 0.0f) tipPc = (tipAmt * 100.0f)/billAmt;
                if(updateEnable1) tipPcET.setText(fStr.format(tipPc));
                if(updateEnable3) totalAmtET.setText(fStr.format(billAmt + tipAmt));
                doCalc();
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateEnable2 = true;
            }
        });

        totalAmtET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                updateEnable3 = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                totalAmt = 0;
                if(s.length() > 0) totalAmt = Float.parseFloat(s.toString());
                tipAmt = totalAmt - billAmt;
                if(updateEnable2) tipAmtET.setText(fStr.format(tipAmt));
                doCalc();
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateEnable3 = true;
            }
        });

    }

    private void doCalc(){
        TextView tv = (TextView)findViewById(R.id.textShareAmt);
        tipAmt = billAmt * tipPc/100.0f;
        totalAmt = billAmt + tipAmt;
        shareAmt = totalAmt/people;

        String shareAmtStr = fStr.format(shareAmt);
        tv.setText(shareAmtStr);
    }

    private ShareActionProvider mShareActionProvider;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.infomenu, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider)  MenuItemCompat.getActionProvider(item);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=in.oormi.eatandshare");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this app!");
        setShareIntent(shareIntent);
        return true;
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                //Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, InfoActivity.class);
                startActivity(i);
                break;
            case R.id.menu_item_share:
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//                sendIntent.setType("text/plain");
//                startActivity(Intent.createChooser(sendIntent, "Share using"));
                break;
        }
        return true;
    }
}
