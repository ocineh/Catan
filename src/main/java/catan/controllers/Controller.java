package catan.controllers;

import catan.models.Model;
import catan.views.View;

public interface Controller<M extends Model, V extends View<M>> {
    M getModel();

    void setModel(M model);

    V getView();

    void setView(V view);
}
