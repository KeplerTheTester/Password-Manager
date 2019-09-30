package com.example.passwordholder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.FingerprintDialogFragment;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.icu.util.RangeValueIterator;
import android.icu.util.ValueIterator;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    // TODO: 2019-06-13 Find a way for the user to copy something into their clipboard
    // TODO: 2019-09-25 Have the ability  to add a category to the database by user -- DONE
    // TODO: 2019-09-25 REFACTOR CODE SO THAT CATEGORY CAN BE NORMAL
    // TODO: 2019-09-26 FUNCTIONALITY TO LONG PRESS TO DELETE -- done
    // TODO: 2019-09-26 LONG CLICK-- done
    ArrayList<String> categories = new ArrayList<>();
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    static ArrayList<Object> dataRows;
    static List<Category> dataCategories;
    static RecyclerAdapterHeader recyclerAdapterHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MainActivity.this.deleteDatabase("database-name");
        categories.add("test");
        categories.add("one");
        categories.add("two");
        categories.add("three");
        categories.add("add more");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recycler view in main will have categories, then drop down for reach thing, and then
        //to actually view it, a simple fingerprint scan to show the password, and easy way to copy
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.floatingAddBtn);
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.floatingSubBtn);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        testDatabase();
        testAdapterSorted();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openCreateAccountDialog();
                //startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));


                //testDatabase();
                //testAdapterSorted();
                //these two work

                //testAdapter();
                //testAlgorithm();

                addItem();
                recyclerAdapterHeader.swap(rowsCalculated(dataCategories));
                //all really bad practices, bound to be bugs
            }
        });
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
                recyclerAdapterHeader.swap(rowsCalculated(dataCategories));
                //can simplify the code by having those two things into one if statement
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
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
        List<Category> test = db.categoryDao().getAll();
        //dataCategories = db.categoryDao().getAllSorted();
        dataCategories = db.categoryDao().getFullSorted();
        //to test algorithm to seperate things
        //System.out.println(test.get(test.size()-1));
        Toast.makeText(this, "The size"+test.size(), Toast.LENGTH_LONG).show();
        for(int i=0; i<dataCategories.size(); i++)
        {
            Log.d("Tester", ""+dataCategories.get(i));
        }
        //This is for testing how database works, not good code practice
        /*Category category = new Category();
        category.setFirstName("xylphabeticalTestFirst");
        category.setLastName("aalphabeticalTestLast");
        category.setUid(1110);

        db.categoryDao().addCategory(category);

        //System.out.println("the size of the test algorithm "+testAlgorithm.size());

        /*for(Category ca: test)
        {
            System.out.println("category "+ ca.getFirstName()+"  "+ca.getLastName()
                    +"  "+ca.getUid());
            //yep 
        }*/
        /*for(Category ca: testAlgorithm)
        {
            System.out.println("category "+ ca.getFirstName()+"  "+ca.getLastName()
                    +"  "+ca.getUid());
            //yep
        }*/


    }

    private void testAdapter()
    {
        List<Object> objects = new ArrayList<>();
        Category firstCategory = new Category();
        Headers headers = new Headers("Titre");
        firstCategory.setItemCategory("test");
        firstCategory.setItemDetail("test");
        Category secondCategory = new Category();
        secondCategory.setItemCategory("test");
        secondCategory.setItemCategory("test");
        objects.add(headers);
        objects.add(firstCategory);
        objects.add(secondCategory);
        objects.add(secondCategory);
        RecyclerAdapterHeader recyclerAdapterHeader = new RecyclerAdapterHeader(objects);
        recyclerView.setAdapter(recyclerAdapterHeader);
    }

    private ArrayList<Object> rowsCalculated(List<Category> allRows)
    {
        ArrayList<Object> objectsRecycler = new ArrayList<>();
        for(Category category: allRows)
        {
            Log.d("categories", ""+category);
        }
        String lastHeader = "";
        /*if(allRows.size() > 0)
        {
            objectsRecycler.add(new Headers(allRows.get(0).getFirstName()));
            objectsRecycler.add(new Category(allRows.get(0).getLastName()));

            for(int i=1; i<allRows.size(); i++)
            {
                Category temp = (Category) objectsRecycler.get(objectsRecycler.size()-1);
                Category toPlace = new Category(allRows.get(i).getLastName());
                if(temp.getLastName().equals(allRows.get(i).getLastName()))
                    objectsRecycler.add(toPlace);
                else{
                    objectsRecycler.add(new Headers(allRows.get(i).getFirstName()));
                    objectsRecycler.add(toPlace);
                }

            }
        }*/
        //Method works as intended
        for(int i=0;  i<allRows.size(); i++)
        {
            /*if(lastHeader.compareTo(allRows.get(i).getFirstName())==0)
            {
                Log.d("rowsCalculated","header");
                objectsRecycler.add(new Category(allRows.get(i).getUid(), allRows.get(i).getFirstName(),
                        allRows.get(i).getLastName()));
            }
            else
            {
                Log.d("rowsCalculated","item");
                objectsRecycler.add(new Headers(allRows.get(i).getFirstName()));
                objectsRecycler.add(new Category(allRows.get(i).getUid(), allRows.get(i).getFirstName(),
                        allRows.get(i).getLastName()));
                lastHeader = allRows.get(i).getFirstName();
            }*/
            if(lastHeader.compareTo(allRows.get(i).getItemCategory())==0)
            {
                Log.d("rowsCalculated","header");
                objectsRecycler.add(new Category(allRows.get(i).getUid(), allRows.get(i).getItemCategory(),
                        allRows.get(i).getItemDetail()));
            }
            else
            {
                Log.d("rowsCalculated","item");
                objectsRecycler.add(new Headers(allRows.get(i).getItemCategory()));
                objectsRecycler.add(new Category(allRows.get(i).getUid(), allRows.get(i).getItemCategory(),
                        allRows.get(i).getItemDetail()));
                lastHeader = allRows.get(i).getItemCategory();
            }
        }
        /*for(Object o: objectsRecycler)
        {
            if(o instanceof Headers)
            {
                System.out.println(((Headers) o).getTitle());
            }
            else if(o instanceof Category)
            {
                System.out.println(((Category) o).getFirstName());
            }
        }*/
        return objectsRecycler;
    }

    private void testAdapterSorted()
    {

        recyclerAdapterHeader = new RecyclerAdapterHeader(rowsCalculated(dataCategories));
        recyclerView.setAdapter(recyclerAdapterHeader);

    }

    private void addItem()
    {
        //this will include having a simple dialog with two text inputs
        //this crashes -- adding Uid to check if that will fix it, fixes it
        Category category = new Category();
        category.setItemCategory("apple");
        category.setItemDetail("iMessage");
        //int randomNum = ThreadLocalRandom.current().nextInt(0, 500 + 1); weird interaction
        int randomNum = (int)(Math.random() * ((900 - 1) + 1)) + 1;
        category.setUid(randomNum);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
        Log.d("addItem",""+category);
        db.categoryDao().addCategory(category);
        testDatabase();


    }

    private void deleteItem()
    {
        //for now it will only delete the last element. Code in Array adapter should be able to detect
        //that it is an erased row which allows me to not worry about updating that list
        Category categoryToDelete = dataCategories.get(dataCategories.size()-1);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
        db.categoryDao().delete(categoryToDelete);
        testDatabase();
    }
}


