package com.example.mealcalendar.patternobserver;

import java.util.Observer;

public interface Subject {

    void registerObserver(ReportObserver o);
    void notifyObservers();
}
