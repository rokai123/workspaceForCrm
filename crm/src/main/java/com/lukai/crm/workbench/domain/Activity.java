package com.lukai.crm.workbench.domain;

import java.util.Objects;

public class Activity {
  private String id; //主键
  private String owner; //所有者，外键 关联tal_user
  private String name; //市场活动名称
  private String startDate; //开始日期 年月日
  private String endDate; //结束日期
  private String cost; //成本
  private String description; //描述
  private String createTime; //创建时间 年月日时分秒
  private String createBy; //创建人
  private String editTime; //修改时间 年月日时分秒
  private String editBy; //修改人

    public Activity() {
    }

    public Activity(String id, String owner, String name, String startDate, String endDate, String cost, String description, String createTime, String createBy, String editTime, String editBy) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.description = description;
        this.createTime = createTime;
        this.createBy = createBy;
        this.editTime = editTime;
        this.editBy = editBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) && Objects.equals(owner, activity.owner) && Objects.equals(name, activity.name) && Objects.equals(startDate, activity.startDate) && Objects.equals(endDate, activity.endDate) && Objects.equals(cost, activity.cost) && Objects.equals(description, activity.description) && Objects.equals(createTime, activity.createTime) && Objects.equals(createBy, activity.createBy) && Objects.equals(editTime, activity.editTime) && Objects.equals(editBy, activity.editBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, name, startDate, endDate, cost, description, createTime, createBy, editTime, editBy);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", cost='" + cost + '\'' +
                ", description='" + description + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", editTime='" + editTime + '\'' +
                ", editBy='" + editBy + '\'' +
                '}';
    }
}
