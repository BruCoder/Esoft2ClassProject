package pt.ipleiria.estg.dei.ei.es2.bio;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

/**
 * Created by Bruno on 12-03-2015.
 */
public class MyEditTextPreference extends EditTextPreference {

    public MyEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if(positiveResult)
            setSummary(getValuesPrefs());
    }

    private CharSequence getValuesPrefs(){

        return getText()!=null?getText():super.getSummary();
    }
}
