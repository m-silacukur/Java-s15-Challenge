package com.library.person;

import com.library.core.Displayable;

public abstract class Person implements Displayable {

    private String name;

    protected Person(String name) {
        this.name = name;
    }

    public abstract String whoYouAre();

    public String getName()         { return name; }
    public void setName(String name){ this.name = name; }

    @Override
    public void display() {
        System.out.println("  Ad   : " + name);
        System.out.println("  Tür  : " + whoYouAre());
    }
}
