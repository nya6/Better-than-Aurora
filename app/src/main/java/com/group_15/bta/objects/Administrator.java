package com.group_15.bta.objects;

import java.io.Serializable;

public class Administrator extends User implements Serializable {

    public Administrator()
    {
        super();
    }
    public Administrator(String newName, String newPassword) {
        super(newName, newPassword);

    }
}