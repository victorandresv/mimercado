package cl.mi.mercado.interfaces;

import cl.mi.mercado.models.MarketModel;

public interface FirestoreSingleStore {
    void Ok(MarketModel data);
    void Error(Exception e);
}
