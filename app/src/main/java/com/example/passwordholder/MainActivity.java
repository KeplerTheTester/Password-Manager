package com.example.passwordholder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.FingerprintDialogFragment;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // TODO: 2019-06-13 Find a way for the user to copy something into their clipboard
    // TODO: 2019-06-16 Find how to read and write Database and display that result
    // TODO: 2019-06-16 Create a recycler view that on click it expand and shows another recycler
    ArrayList<String> categories = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categories.add("test");
        categories.add("one");
        categories.add("two");
        categories.add("three");
        categories.add("add more");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recycler view in main will have categories, then drop down for reach thing, and then
        //to actually view it, a simple fingerprint scan to show the password, and easy way to copy
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Clicked " +
                        "successfully ", Toast.LENGTH_SHORT).show();
                //openCreateAccountDialog();
                //startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
                testDatabase();

            }
        });

    }

    private void openCreateAccountDialog()
    {
        //this dialog will be simple way for the user to add an account or create a new account with
        //password
        //User will be asked the category of the account [Google, Yahoo, Reddit, or Add Category]
        //then prompt to put in their user name, then asked what their username is, then option to
        //add password or generate a new one. If generated give a list of requirements needed then
        //generate and create account.
        testDialog();

    }


    //create an

    private void viewsToBeGone(boolean generatePassword)
    {
        //if the user decides to generate a password then all the view to generate will be there,
        //otherwise the view to input password will be there.
        if(generatePassword)
        {

        }
        else
        {

        }
    }

    private void testDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // 2. Chain together various setter methods to set the dialog characteristics
        final String [] temp = new String[categories.size()];
        final ArrayList<String> optionsTaken = new ArrayList<>();
        int i=0;
        for(String s: categories)
        {
            temp[i] = s;
            i++;
        }
        Toast.makeText(this, "size of temp array "+temp.length, Toast.LENGTH_SHORT).show();
        //builder.setMessage("Add New Account ")
                builder.setTitle("Test 1.0")
                .setMultiChoiceItems(temp, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                        if(b)
                        {
                            optionsTaken.add(temp[i]);
                        }
                        else if(optionsTaken.contains(temp[i]))
                        {
                            optionsTaken.remove(temp[i]);
                        }
                        Toast.makeText(MainActivity.this, "" +
                                "Length of options taken "+optionsTaken.size(), Toast.LENGTH_SHORT).show();
                    }
                });

        // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();
        //On finish this will create a list of requirements to be added to the class.
        dialog.show();
    }

    private void testDatabase()
    {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        List<Category> test = db.categoryDao().getAll();
        for(Category category: test)
        {
            System.out.println(category);
            //yep
        }


    }
}

