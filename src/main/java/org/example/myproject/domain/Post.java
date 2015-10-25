package org.example.myproject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by k on 10/25/15.
 */
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @Column(length = 32, nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String title;
    private String detail;
    private String name;
    private String phone;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;

    @JsonProperty(value = "updatetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updateTime;

    @PrePersist
    protected void preCreate() {
        updateTime = createTime = new Date();
    }

    @Transient
    @JsonIgnore
    private boolean updateModifyTime = true;

    @PreUpdate
    protected void preUpdate() {
        if (updateModifyTime)
            updateTime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUpdateModifyTime(boolean updateModifyTime) {
        this.updateModifyTime = updateModifyTime;
    }

}
