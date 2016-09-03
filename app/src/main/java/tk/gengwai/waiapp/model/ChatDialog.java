package tk.gengwai.waiapp.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import tk.gengwai.waiapp.R;

/**
 * Created by danielchan303 on 2/9/2016.
 */
public class ChatDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_add_title)
                .setPositiveButton(R.string.dialog_add, new addBtn())
                .setNegativeButton(R.string.dialog_cancel, new cancelBtn());
        return builder.create();
    }

    private class addBtn implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Toast.makeText(getActivity(), "testing", Toast.LENGTH_SHORT).show();
        }
    }

    private class cancelBtn implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
        }
    }
}
