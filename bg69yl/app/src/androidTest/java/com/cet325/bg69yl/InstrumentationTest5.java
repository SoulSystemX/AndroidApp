package com.cet325.bg69yl;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * Created by Illug on 12/01/2017.
 */


public class InstrumentationTest5 extends ActivityInstrumentationTestCase2<AddPlace> {

    private Activity mMainActivity;
    private EditText location;


    public InstrumentationTest5() {
        super(AddPlace.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Get the activity instance
        mMainActivity = getActivity();
        // Get instance of the editText box
        location = (EditText)mMainActivity.findViewById(R.id.GeolocationET);

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPersistentData(){
        location = (EditText)mMainActivity.findViewById(R.id.GeolocationET);
        // Get instance of the editText box
        final String lo1 = "1,0";

        // To access UI via an instrumentation test you must use
        // runOnUiThread() and override the run() method
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                location.setText(lo1);
            }
        });

        // Close the activity and see if the text we sent to mEditText persists

        mMainActivity.finish();
        setActivity(null);

        // Re-open the activity
        mMainActivity = getActivity();
        String lo2 = location.getText().toString();

        // Check the value in editText after re-opening matches our expected value
        assertEquals(lo1, lo2);
    }


}
