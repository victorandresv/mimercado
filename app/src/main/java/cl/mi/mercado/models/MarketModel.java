package cl.mi.mercado.models;

import com.google.firebase.Timestamp;

public class MarketModel {

    private String id;
    private Timestamp created_at;
    private String email;
    private String firstname;
    private String lastname;
    private String plan;
    private Timestamp plan_from;
    private Timestamp plan_to;
    private String storename;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public void setPlan_to(Timestamp plan_to) {
        this.plan_to = plan_to;
    }

    public void setPlan_from(Timestamp plan_from) {
        this.plan_from = plan_from;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getPlan_to() {
        return plan_to;
    }

    public Timestamp getPlan_from() {
        return plan_from;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public String getStorename() {
        return storename;
    }

    public String getPlan() {
        return plan;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "MarketModel{" +
                "created_at=" + created_at +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", plan='" + plan + '\'' +
                ", plan_from=" + plan_from +
                ", plan_to=" + plan_to +
                ", storename='" + storename + '\'' +
                '}';
    }
}
