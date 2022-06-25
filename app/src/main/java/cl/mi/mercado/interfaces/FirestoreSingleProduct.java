package cl.mi.mercado.interfaces;

import cl.mi.mercado.models.ProductModel;

public interface FirestoreSingleProduct {
    void Ok(ProductModel data);
    void Error(Exception e);
}
