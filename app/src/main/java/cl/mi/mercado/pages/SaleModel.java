package cl.mi.mercado.pages;

import com.google.firebase.Timestamp;

public class SaleModel {

    private String id;
    private String client;
    private Timestamp created_at;
    private String status;

    public SaleModel(String id, String client, Timestamp created_at, String status) {
        this.id = id;
        this.client = client;
        this.created_at = created_at;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getClient() {
        return client;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "SaleModel{" +
                "id='" + id + '\'' +
                ", client='" + client + '\'' +
                ", created_at=" + created_at +
                ", status='" + status + '\'' +
                '}';
    }
}
