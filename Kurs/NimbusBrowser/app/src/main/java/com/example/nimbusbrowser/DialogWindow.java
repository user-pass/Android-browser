package com.example.nimbusbrowser;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.rapidbrowser.R;


public class DialogWindow extends AppCompatDialogFragment {
    private EditText editTextWebName;
    private EditText editTextWebURL;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.prompt, null);

        builder.setView(view)
                .setTitle("Add new favorite: ")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String WebName = editTextWebName.getText().toString();
                        String WebAddress = editTextWebURL.getText().toString();
                        listener.applyTexts(WebName, WebAddress);
                    }
                });

        editTextWebName = view.findViewById(R.id.edit_webname);
        editTextWebURL = view.findViewById(R.id.edit_webaddress);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String WebName, String WebAddress);
    }
}
