package com.andrii.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "group", schema = "public", catalog = "java_task")
public class Group {
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "group_name")
    private String groupName;
    @ManyToOne
    @JoinColumn(name = "parent_group_id", referencedColumnName = "id")
    private Group groupByParentGroupId;

}
