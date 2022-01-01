package catan.controllers;

import catan.models.Model;
import catan.views.View;

public class AbstractController<M extends Model, V extends View<M>> implements Controller<M, V> {
    protected M model;
    protected V view;

    @Override
    public M getModel() {
        return model;
    }

    @Override
    public void setModel(M model) {
        this.model = model;
        view.setModel(model);
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void setView(V view) {
        this.view = view;
        view.setModel(model);
    }
}
