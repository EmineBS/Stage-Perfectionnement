package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import com.alphalab.domain.enumeration.BadgeStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Badge} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AmineDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;
    private String myname;

    private String familyname;

    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMyName() {
        return myname;
    }

    public void setMyName(String myname) {
        this.myname = myname;
    }

    public String getFamilyName() {
        return familyname;
    }

    public void setFamilyName(String familyname) {
        this.familyname = familyname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmineDTO)) {
            return false;
        }

        AmineDTO amineDTO = (AmineDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, amineDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "AmineDTO{" +
            "id=" + id +
            ", myname='" + myname + '\'' +
            ", familyname='" + familyname + '\'' +
            ", age=" + age +
            '}';
    }
}
