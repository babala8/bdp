package com.zjft.microservice.treasurybrain.datainsight.domain;

public class SelfDefReports {
    private String id;

    private String name;

    private String filename;

    private Integer groupid;

    private Integer status;

    private String creator;

    private String creatorOrgno;

    private String createTime;

    private String lastestModOp;

    private String lastestModTime;

    private String parameters;

    private SelfDefGroup selfDefGroup;
    
    private Integer count; //createorupdate使用，无业务含义
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getCreatorOrgno() {
        return creatorOrgno;
    }

    public void setCreatorOrgno(String creatorOrgno) {
        this.creatorOrgno = creatorOrgno == null ? null : creatorOrgno.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastestModOp() {
        return lastestModOp;
    }

    public void setLastestModOp(String lastestModOp) {
        this.lastestModOp = lastestModOp == null ? null : lastestModOp.trim();
    }

    public String getLastestModTime() {
        return lastestModTime;
    }

    public void setLastestModTime(String lastestModTime) {
        this.lastestModTime = lastestModTime;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters == null ? null : parameters.trim();
    }
    
   public SelfDefGroup getSelfDefGroup() {
	return selfDefGroup;
   }
   
   public void setSelfDefGroup(SelfDefGroup selfDefGroup) {
	this.selfDefGroup = selfDefGroup;
   }
}