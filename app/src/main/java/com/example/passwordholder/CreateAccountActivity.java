package com.example.passwordholder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        String [] categories = {"Google", "Yahoo","Apple", "GitHub","Discord", "Slack", "Add New" +
                "Category"};
        final List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Google");
        spinnerArray.add("Slack");
        spinnerArray.add("Discord");
        spinnerArray.add("LinkedIn");
        spinnerArray.add("GitHub");
        spinnerArray.add("New Category");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        final Spinner categoriesSpinner = (Spinner) findViewById(R.id.spinnerCategories);
        EditText userNameEditText = (EditText) findViewById(R.id.userNameField);
        final EditText passWordEditText = (EditText) findViewById(R.id.passWordField);
        Button addPasswordBtn = (MaterialButton) findViewById(R.id.addPassword);
        Button generatePasswordBtn = (MaterialButton) findViewById(R.id.generatePassword);
        passWordEditText.setVisibility(View.GONE);
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.account_categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        */
        addPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passWordEditText.setVisibility(View.VISIBLE);
                passWordEditText.setEnabled(true);
                passWordEditText.setText("");
            }
        });

        generatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this will call the dialog first, everything else will be in that method instead
                //of here
                showPasswordReqDialog();
                passWordEditText.setVisibility(View.VISIBLE);
                passWordEditText.setText("Random Password");
                passWordEditText.setFocusable(false);
                //passWordEditText.setEnabled(false);


            }
        });

        passWordEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passWordEditText.getTransformationMethod() != null) {
                    passWordEditText.setTransformationMethod(null);
                }
                else
                {
                    passWordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }

            }
        });

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categoriesSpinner.setAdapter(adapter);
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                Toast.makeText(CreateAccountActivity.this, "" +
                        spinnerArray.get(pos), Toast.LENGTH_SHORT).show();
                if(spinnerArray.get(pos).equals("New Category"))
                {
                    System.out.println("this will create a thing" +
                            " that will allow to add a new category");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    private void showPasswordReqDialog()
    {
        //,
        final ArrayList<String> requirements = new ArrayList<>();
        final String [] test = {"special characters", "length greater than 10"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
        builder.setTitle("Test Password requirements")
                .setMultiChoiceItems(test, null, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b)
                        {
                            //add it to the requirements arraylist, or initialize the Object
                            requirements.add(test[i]);
                        }
                        else if(requirements.contains(test[i]))
                        {
                            requirements.remove(test[i]);
                        }
                    }
                })
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //This will be where the rest of the code on top will be placed
                        //and where password will be generated
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requirements.clear();

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
