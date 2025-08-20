package com.example.mealcalendar.patternobserver.observerreport;


public interface Subject {

    void registerObserver(ReportObserver o);
    void notifyObservers();
}
