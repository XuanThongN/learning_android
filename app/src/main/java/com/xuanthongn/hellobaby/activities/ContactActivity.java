package com.xuanthongn.hellobaby.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuanthongn.hellobaby.ContactAdapter;
import com.xuanthongn.hellobaby.R;
import com.xuanthongn.hellobaby.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList;
    private Button buttonCreateContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);

        recyclerView = findViewById(R.id.recyclerView);
        contactList = getContactList(); // Replace this with your actual method to get the list of contacts

        contactAdapter = new ContactAdapter(contactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactAdapter);
        buttonCreateContact = findViewById(R.id.buttonCreateContact);

        //Click to open contact create
        buttonCreateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, CustomerCreateActivity.class);
                startActivity(intent);
            }
        });


    }

    // Replace this method with your actual method to get the list of contacts
    private List<Contact> getContactList() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Thong Nguyen", "1234567890"));
        contacts.add(new Contact("Jane Doe", "0987654321"));
        contacts.add(new Contact("John Smith", "5555555555"));
        contacts.add(new Contact("Jane Smith", "4444444444"));
        contacts.add(new Contact("Michael Johnson", "1111111111"));
        contacts.add(new Contact("Michelle Johnson", "2222222222"));
        contacts.add(new Contact("David Williams", "3333333333"));
        contacts.add(new Contact("Emily Williams", "6666666666"));
        contacts.add(new Contact("James Brown", "7777777777"));
        contacts.add(new Contact("Emma Brown", "8888888888"));
        contacts.add(new Contact("Robert Jones", "9999999999"));
        return contacts;

    }

}
