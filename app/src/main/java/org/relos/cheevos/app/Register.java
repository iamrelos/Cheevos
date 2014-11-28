package org.relos.cheevos.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.relos.cheevos.R;
import org.relos.cheevos.misc.HelperClass;

/**
 * Register activity
 * <p/>
 * Created by Christian (ReloS) Soler on 11/26/2014.
 */
public class Register extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_register, container, false);

        // set title
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Register");

        final EditText etEmail = (EditText) view.findViewById(R.id.et_email);
        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
        final EditText etPasswordConfirm = (EditText) view.findViewById(R.id.et_confirm_password);
        final EditText etGamertag = (EditText) view.findViewById(R.id.et_gamertag);
        final Button btRegister = (Button) view.findViewById(R.id.bt_register);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                final String passwordConfirm = etPasswordConfirm.getText().toString();
                final String gamertag = etGamertag.getText().toString();

                if (password.equals(passwordConfirm) && password.length() > 5) {
                    registerUser(email, password, gamertag);
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Password did not matched or was less than 6 characters in length.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
        });

        return view;
    }

    private void registerUser(String email, String password, String gamertag) {

        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);
        user.put("gamertag", gamertag);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getActivity(), "Successfully register and logged in", Toast.LENGTH_LONG).show();

                    HelperClass.reloadActivity(getActivity());
                } else {
                    // log error
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
