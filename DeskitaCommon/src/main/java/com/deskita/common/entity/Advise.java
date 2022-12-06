package com.deskita.common.entity;

import javax.persistence.*;

@Entity
@Table(name="advise")
public class Advise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="phone")
    private String phone;

    @Column(name="name")
    private String name;

    @Column(name="product_id")
    private int productId;

    @Column(name = "is_resolved" )
    private boolean isResolved;
    public Advise( String phone, String name, int productId) {
        this.phone = phone;
        this.name = name;
        this.productId = productId;
        this.isResolved=false;
    }

    public Advise() {

    }

    public boolean getIsResolved() {
        return isResolved;
    }

    public void setIsResolved(boolean is_resolved) {
        this.isResolved = is_resolved;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
