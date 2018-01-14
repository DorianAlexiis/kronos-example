package com.kronos.example.ui.modules.main;


import com.kronos.example.data.models.Contact;

import java.util.ArrayList;


public interface MainFragmentView {
    void setContactsAdapter(ArrayList<Contact> contacts);
    void onFailure();
}
