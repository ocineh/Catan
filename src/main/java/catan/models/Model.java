package catan.models;

import catan.controllers.listeners.ChangeListener;

public interface Model {
    void addChangeListener(ChangeListener listener);

    void changed();
}
