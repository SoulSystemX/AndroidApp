package com.cet325.bg69yl;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * Created by Illug on 12/01/2017.
 */


public class InstrumentationTest3 extends ActivityInstrumentationTestCase2<AddPlace> {

    private Activity mMainActivity;
    private EditText PriceET;


    public InstrumentationTest3() {
        super(AddPlace.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Get the activity instance
        mMainActivity = getActivity();
        // Get instance of the editText box
        PriceET = (EditText)mMainActivity.findViewById(R.id.PriceET);

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPersistentData(){
        PriceET = (EditText)mMainActivity.findViewById(R.id.PriceET);
        // Get instance of the editText box
        final String pr1 = "1.0";

        // To access UI via an instrumentation test you must use
        // runOnUiThread() and override the run() method
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PriceET.setText(pr1);
            }
        });

        // Close the activity and see if the text we sent to mEditText persists

        mMainActivity.finish();
        setActivity(null);

        // Re-open the activity
        mMainActivity = getActivity();
        String pr2 = PriceET.getText().toString();

        // Check the value in editText after re-opening matches our expected value
        assertEquals(pr1, pr2);
    }


}
