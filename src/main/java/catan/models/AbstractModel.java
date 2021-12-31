package catan.models;

import catan.controllers.listeners.ChangeListener;

import java.util.ArrayList;

public class AbstractModel implements Model {
    private final ArrayList<ChangeListener> changeListeners;

    public AbstractModel() {
        this.changeListeners = new ArrayList<>();
    }

    @Override
    public final void addChangeListener(ChangeListener listener) {
        changeListeners.add(listener);
    }

    @Override
    public final void changed() {
        changeListeners.forEach(ChangeListener::stateChanged);
    }
}
