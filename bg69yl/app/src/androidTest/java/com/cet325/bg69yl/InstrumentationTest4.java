package com.cet325.bg69yl;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * Created by Illug on 12/01/2017.
 */


public class InstrumentationTest4 extends ActivityInstrumentationTestCase2<AddPlace> {

    private Activity mMainActivity;
    private EditText description;


    public InstrumentationTest4() {
        super(AddPlace.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Get the activity instance
        mMainActivity = getActivity();
        // Get instance of the editText box
        description = (EditText)mMainActivity.findViewById(R.id.DescriptionET);

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPersistentData(){
        description = (EditText)mMainActivity.findViewById(R.id.DescriptionET);
        // Get instance of the editText box
        final String de1 = "A place in the world";

        // To access UI via an instrumentation test you must use
        // runOnUiThread() and override the run() method
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                description.setText(de1);
            }
        });

        // Close the activity and see if the text we sent to mEditText persists

        mMainActivity.finish();
        setActivity(null);

        // Re-open the activity
        mMainActivity = getActivity();
        String de2 = description.getText().toString();

        // Check the value in editText after re-opening matches our expected value
        assertEquals(de1, de2);
    }


}
