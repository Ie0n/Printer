package com.daily.jcy.printer.utils;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class StringIdEntity {
    @Id public long id;
    public String uid;
}
