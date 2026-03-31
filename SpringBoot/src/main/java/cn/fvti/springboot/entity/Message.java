package cn.fvti.springboot.entity;

import javax.persistence.*;

@Entity
@Table(name = "message_list")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "support")
    private Integer support;

    @Column(name = "has_sup")
    private Boolean hasSup;

    @Column(name = "unnamed")
    private Boolean unnamed;

    @Column(name = "time")
    private Long time;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getSupport() {
        return support;
    }

    public void setSupport(Integer support) {
        this.support = support;
    }

    public Boolean getHasSup() {
        return hasSup;
    }

    public void setHasSup(Boolean hasSup) {
        this.hasSup = hasSup;
    }

    public Boolean getUnnamed() {
        return unnamed;
    }

    public void setUnnamed(Boolean unnamed) {
        this.unnamed = unnamed;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
