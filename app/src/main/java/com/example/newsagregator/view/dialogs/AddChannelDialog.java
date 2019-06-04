package com.example.newsagregator.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.widget.EditText;

import com.example.newsagregator.R;

public class AddChannelDialog extends DialogFragment {
    private ClickAddChannelDialog clickAddChannelDialog;

    public interface ClickAddChannelDialog {
        void setClickOkAddChannel(String saveUrlChannel);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder addChannelDialog = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText("https://www.fl.ru/rss/all.xml?subcategory=103&category=8");
        addChannelDialog.setView(input);
        addChannelDialog
                .setMessage(R.string.enterChannelText)
                .setPositiveButton(R.string.okText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clickAddChannelDialog.setClickOkAddChannel(input.getText().toString());
                    }
                });
        return addChannelDialog.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        clickAddChannelDialog = (ClickAddChannelDialog) getActivity();

    }
}


