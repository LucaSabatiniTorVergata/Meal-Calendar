package com.example.mealcalendar;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class AntiCodeSmellPrinter {

    private static final  PrintStream OUT =  new PrintStream(new FileOutputStream(FileDescriptor.out));
    private String name;
    public AntiCodeSmellPrinter(String name) {
        this.name = name;
    }

    public synchronized void print(String msg){
        OUT.println('[' + name + "]: " + msg);
    }

}
