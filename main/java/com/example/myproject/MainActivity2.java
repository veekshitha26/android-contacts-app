package com.example.myproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    // creating a new variable for our edit text and button.
    private EditText nameEdt, phoneEdt, emailEdt;
    private Button addContactEdt;
    EditText idEdtName,idEdtPhoneNumber,idEdtEmail;
    Button idBtnAddContact,bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2createnewcontact);

        // on below line we are initializing our variables.
        nameEdt = findViewById(R.id.idEdtName);
        phoneEdt = findViewById(R.id.idEdtPhoneNumber);
        emailEdt = findViewById(R.id.idEdtEmail);
        addContactEdt = findViewById(R.id.idBtnAddContact);

        // on below line we are adding on click listener for our button.
        addContactEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are getting text from our edit text.
                String name = nameEdt.getText().toString();
                String phone = phoneEdt.getText().toString();
                String email = emailEdt.getText().toString();

                // on below line we are making a text validation.
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(email) && TextUtils.isEmpty(phone)) {
                    Toast.makeText(MainActivity2.this, "Please enter the data in all fields. ", Toast.LENGTH_SHORT).show();
                } else {
                    // calling a method to add contact.
                    addContact(name, email, phone);
                }
            }
        });

    }

    private void addContact(String name, String email, String phone) {
        // in this method we are calling an intent and passing data to that
        // intent for adding a new contact.
        Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
        contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        contactIntent
                .putExtra(ContactsContract.Intents.Insert.NAME, name)
                .putExtra(ContactsContract.Intents.Insert.PHONE, phone)
                .putExtra(ContactsContract.Intents.Insert.EMAIL, email);
        startActivityForResult(contactIntent, 1);
    }
    public void insert()
    {
        try
        {
            String name = idEdtName.getText().toString();
            String phno = idEdtPhoneNumber.getText().toString();
            String email = idEdtEmail.getText().toString();
            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS records(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR, phno VARCHAR,email VARCHAR)");
            String sql = "insert into records(name,phno,email)values(?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,name);
            statement.bindString(2,phno);
            statement.bindString(3,email);
            statement.execute();
            Toast.makeText(this,"Record added",Toast.LENGTH_LONG).show();
            idEdtName.setText("");
            idEdtPhoneNumber.setText("");
            idEdtEmail.setText("");
            idEdtName.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Record Fail",Toast.LENGTH_LONG).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // in on activity result method.
        if (requestCode == 1) {
            // we are checking if the request code is 1
            if (resultCode == Activity.RESULT_OK) {
                // if the result is ok we are displaying a toast message.
                Toast.makeText(this, "Contact has been added.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(i);
            }
            // else we are displaying a message as contact addition has cancelled.
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled Added Contact",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
