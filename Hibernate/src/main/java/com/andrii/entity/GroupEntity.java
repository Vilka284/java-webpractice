package com.andrii.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "group", schema = "public", catalog = "java_task")
public class GroupEntity {
    private int id;
    private String groupName;
    private GroupEntity groupByParentGroupId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupEntity that = (GroupEntity) o;

        if (id != that.id) return false;
        return Objects.equals(groupName, that.groupName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "parent_group_id", referencedColumnName = "id")
    public GroupEntity getGroupByParentGroupId() {
        return groupByParentGroupId;
    }

    public void setGroupByParentGroupId(GroupEntity groupByParentGroupId) {
        this.groupByParentGroupId = groupByParentGroupId;
    }
}
