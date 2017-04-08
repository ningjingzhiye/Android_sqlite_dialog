package com.example.database;

public class Person {
    public int id;
    public String sname;
    public String sid;

    public Person()
    {
    }

    public Person(String name, String sid)
    {
        this.sname = name;
        this.sid = sid;
    }
}