package com.example.mealcalendar.patternobserver.observerasknutri;

public interface ReportRequestSubject {

    void registerObserver(ReportRequestObserver observer);
    void notifyObservers();
}
