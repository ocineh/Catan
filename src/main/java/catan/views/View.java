package catan.views;

import catan.models.Model;

public interface View<T extends Model> {
    void setModel(T model);
}
