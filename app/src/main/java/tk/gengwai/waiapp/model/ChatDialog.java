package tk.gengwai.waiapp.model;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

import tk.gengwai.waiapp.R;

public class ChatDialog extends DialogFragment {
    private String userInput;
    private String title;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_add_title)
                .setPositiveButton(R.string.dialog_add, new addBtn())
                .setView(inflater.inflate(R.layout.dialog_chat, null));
        return builder.create();
    }

    public interface DialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String userInput);
    }

    DialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (DialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    private class addBtn implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            // Send the positive button event back to the host activity
            userInput = ((EditText) getDialog().findViewById(R.id.dialog_chat_input)).getText().toString();
            mListener.onDialogPositiveClick(ChatDialog.this, userInput);
            dialogInterface.dismiss();
        }
    }


}
