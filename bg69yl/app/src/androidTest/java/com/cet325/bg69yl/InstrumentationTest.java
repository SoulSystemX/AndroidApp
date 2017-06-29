package com.cet325.bg69yl;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * Created by Illug on 12/01/2017.
 */


public class InstrumentationTest extends ActivityInstrumentationTestCase2<AddPlace> {

    private Activity mMainActivity;
    private EditText name;


    public InstrumentationTest() {
        super(AddPlace.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Get the activity instance
        mMainActivity = getActivity();
        // Get instance of the editText box
        name = (EditText)mMainActivity.findViewById(R.id.NameET);

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPersistentData(){
        name = (EditText)mMainActivity.findViewById(R.id.NameET);
        // Get instance of the editText box
        final String na1 = "MyName";

        // To access UI via an instrumentation test you must use
        // runOnUiThread() and override the run() method
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                name.setText(na1);
            }
        });

        // Close the activity and see if the text we sent to mEditText persists

        mMainActivity.finish();
        setActivity(null);

        // Re-open the activity
        mMainActivity = getActivity();
        String na2 = name.getText().toString();

        // Check the value in editText after re-opening matches our expected value
        assertEquals(na2, na1);
    }


}
