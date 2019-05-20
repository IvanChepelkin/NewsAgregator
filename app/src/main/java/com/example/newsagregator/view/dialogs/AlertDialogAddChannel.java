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

public class AlertDialogAddChannel extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder addChannelDialog = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText("http://www.free-lance.ru/rss/projects.xml");
        addChannelDialog.setView(input);
        addChannelDialog
                .setMessage("Введите адрес канала")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return addChannelDialog.create();
    }
}


//addChannelDialog.setTitle("Введите адрес канала"); //literals


//        final EditText input = new EditText(this);
//        input.setInputType(InputType.TYPE_CLASS_TEXT);
//        input.setText("http://www.free-lance.ru/rss/projects.xml");
//        addChannelDialog.setView(input);
//        addChannelDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                newsPresenter.setClickOkAddChannels(input.getText().toString());
//            }
//        });
//        addChannelDialog.show();

