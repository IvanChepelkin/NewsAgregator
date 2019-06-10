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
    final EditText input = new EditText(getActivity());

    public interface ClickAddChannelDialog {
        void setClickOkAddChannel(String saveUrlChannel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        clickAddChannelDialog = (ClickAddChannelDialog) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder addChannelDialog = new AlertDialog.Builder(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText("http://www.free-lance.ru/rss/projects.xml");
        addChannelDialog.setView(input);
        addChannelDialog
                .setMessage(R.string.enterChannelText)
                .setPositiveButton(R.string.okText, (dialog, which) -> clickAddChannelDialog.setClickOkAddChannel(input.getText().toString()));
        return addChannelDialog.create();
    }

}


