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
import com.example.newsagregator.view.MainActivity;

public class DeleteChannelDialog extends DialogFragment {
    private String[] channelsArray;
    private boolean[] positionChannelToDelete;
    private ClickOkDeleteChannelDialog clickOkDeleteChannelDialog;

    public interface ClickOkDeleteChannelDialog {
        void setClickOkAddChannel(boolean[] positionChannelToDelete);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            channelsArray = getArguments().getStringArray(MainActivity.KEY_channelsArray);
            if (channelsArray != null) {
                positionChannelToDelete = new boolean[channelsArray.length];
                for (int i = 0; i < positionChannelToDelete.length; i++) {
                    positionChannelToDelete[i] = false;
                }
            }
        }

        AlertDialog.Builder deleteChannelsDialog = new AlertDialog.Builder(getActivity());
        deleteChannelsDialog.setTitle(R.string.chooseChannelTxt);

        final EditText inputs = new EditText(getActivity());
        inputs.setInputType(InputType.TYPE_CLASS_TEXT);
        deleteChannelsDialog.setView(inputs);
        deleteChannelsDialog.setMultiChoiceItems(channelsArray, positionChannelToDelete, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                positionChannelToDelete[which] = true;
            }
        });
        deleteChannelsDialog.setPositiveButton(R.string.deleteTxt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickOkDeleteChannelDialog.setClickOkAddChannel(positionChannelToDelete);
            }
        });
        return deleteChannelsDialog.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        clickOkDeleteChannelDialog = (ClickOkDeleteChannelDialog) getActivity();
    }
}

