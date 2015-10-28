package learning.android.tenmarks.com.androidlearning;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Talha on 10/28/15.
 */
public class LearnAndroidDialogFragment extends DialogFragment {

    public static final String MESSAGE = "current_classroom";
    public static final String LABEL_YES_BTN = "yes_button_label";
    public static final String LABEL_NO_BTN = "no_button_label";
    public static final String SHOW_YES_NO = "show_yes_no";
    public static final String QUESTION_INDEX = "question_index";

    public static LearnAndroidDialogFragment createInstance(String message) {
        LearnAndroidDialogFragment dialog = new LearnAndroidDialogFragment();
        Bundle args = new Bundle();
        args.putString(MESSAGE, message);
        dialog.setArguments(args);
        return dialog;
    }

    public static LearnAndroidDialogFragment createInstance(String message, String okLabel, String noLabel, boolean showTwoButtons, int index) {
        LearnAndroidDialogFragment dialog = new LearnAndroidDialogFragment();
        Bundle args = new Bundle();
        args.putString(MESSAGE, message);
        args.putString(LABEL_YES_BTN, okLabel);
        args.putString(LABEL_NO_BTN, noLabel);
        args.putBoolean(SHOW_YES_NO, showTwoButtons);
        args.putInt(QUESTION_INDEX, index);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String message = "";
        String btnOkayLabel = "";
        String btnNoLabel = "";
        boolean hasTwoButtons = false;
        int questionIndex = -1;

        Bundle args = getArguments();
        if (args != null) {
            message = args.getString(MESSAGE, "");
            btnOkayLabel = args.getString(LABEL_YES_BTN, "");
            btnNoLabel = args.getString(LABEL_NO_BTN, "");
            hasTwoButtons = args.getBoolean(SHOW_YES_NO, false);
            questionIndex = args.getInt(QUESTION_INDEX, -1);
        }
        return getTextDialog(message, btnOkayLabel, btnNoLabel, hasTwoButtons, questionIndex);
    }

    private Dialog getTextDialog(String message, String yesButtonLabel,
                                 String noButtonLabel, boolean showYesAndNo, final int index) {
        final Context context = getActivity();
        // TODO I shall try to cover styles tomorrow. So just don't worry about R.style.AppTheme on next line
        final Dialog builder = new Dialog(context);
        View view = View.inflate(getActivity(), R.layout.fragment_learn_android_dialog, null);
        ((TextView) view.findViewById(R.id.msg)).setText(message);
        Button ok = (Button) view.findViewById(R.id.button1);
        if (!TextUtils.isEmpty(yesButtonLabel)) {
            ok.setText(yesButtonLabel);
        } else {
            ok.setText("OK");
        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO discuss returning data from one fragment to other.
                builder.dismiss();
            }
        });

        Button no = (Button) view.findViewById(R.id.button2);
        no.setVisibility(showYesAndNo ? View.VISIBLE : View.GONE);
        if (showYesAndNo) {
            if (!TextUtils.isEmpty(noButtonLabel)) {
                no.setText(noButtonLabel);
            } else {
                no.setText("No");
            }
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.dismiss();
                }
            });
        }

        view.setVisibility(View.VISIBLE);
        builder.setContentView(view);
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(android.content.DialogInterface dialog, int keyCode, android.view.KeyEvent event) {

                // TODO pay attention to Cancelable.
                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK) && isCancelable()) {
                    builder.dismiss();
                    return true; // pretend we've processed it
                } else {
                    return false; // pass on to be processed as normal
                }
            }
        });
        return builder;
    }

}
