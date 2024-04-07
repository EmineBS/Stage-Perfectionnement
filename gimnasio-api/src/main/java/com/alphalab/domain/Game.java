package com.alphalab.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("game")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Game extends AbstractAuditingEntity<Long> implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("gamename")
    private String gamename;
    
    @Column("style")
    private String style;

    public Long getId() {
        return this.id;
    }

    public Game id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameName() {
        return this.gamename;
    }

    public Game gameName(String gamename) {
        this.setGameName(gamename);
        return this;
    }

    public void setGameName(String gamename) {
        this.gamename = gamename;
    }

    public String getStyle() {
        return this.style;
    }

    public Game style(String style) {
        this.setStyle(style);
        return this;
    }

    public void setStyle(String style) {
        this.style = style;
    }

}