package com.alphalab.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * An Amine.
 */
@Table("amine")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Amine extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("myname")
    private String myname;

    @Column("familyname")
    private String familyname;

    @Column("age")
    private Integer age;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Amine id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMyName() {
        return myname;
    }

    public Amine myname(String myname) {
        this.setMyName(myname);
        return this;
    }

    public void setMyName(String myname) {
        this.myname = myname;
    }

    public String getFamilyName() {
        return familyname;
    }

    public Amine familyname(String familyname) {
        this.setFamilyName(familyname);
        return this;
    }

    public void setFamilyName(String familyname) {
        this.familyname = familyname;
    }

    public Integer getAge() {
        return age;
    }

    public Amine age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
