/*
*   ADD SUBTASK POPUP
*   A dialog fragment which gets user input for creating new subtask
*   CONTAINS    Cancel and add buttons
*               EditText form
* */

package com.example.tasqr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddSubTaskPopUp extends DialogFragment {

    private EditText subTaskName;
    private AddSubTaskListener listener;

    private Button dismiss;
    private Button ok;
    /* Main on create method */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        /* Creating layout dependencies */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_addsubtask, null);

        subTaskName = view.findViewById(R.id.subtask_nameadd);
        dismiss = view.findViewById(R.id.dismiss);
        ok = view.findViewById(R.id.ok);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.sendSubTaskName(subTaskName.getText().toString());
                dismiss();
            }
        });

        builder.setView(view);

        return builder.create();
    }

    /* Method to get context for listener */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddSubTaskListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AddSubTaskListener interface");
        }
    }

    /* Listener interface for add subtask activity to override for it to get subtask name*/
    public interface AddSubTaskListener
    {
        void sendSubTaskName(String subTaskName);
    }

}
