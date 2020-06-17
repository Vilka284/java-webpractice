package com.springapp.andrii.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "group", schema = "public", catalog = "java_task")
public class Group {
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "group_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_group_id", referencedColumnName = "id")
    private Group parentGroup;
}
