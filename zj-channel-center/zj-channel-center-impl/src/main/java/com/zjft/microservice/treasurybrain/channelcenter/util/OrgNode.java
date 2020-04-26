package com.zjft.microservice.treasurybrain.channelcenter.util;


import java.util.List;

/**
 * @author 吴磊
 */
public class OrgNode {

	private String no;
	private String name;
	private String parentOrg;
	private int left;
	private int right;
	private int treelev;

	private List<OrgNode> childOrgs;

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getTreelev() {
		return treelev;
	}

	public void setTreelev(int treelev) {
		this.treelev = treelev;
	}

	public List<OrgNode> getChildOrgs() {
		return childOrgs;
	}

	public void setChildOrgs(List<OrgNode> childOrgs) {
		this.childOrgs = childOrgs;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentOrg() {
		return parentOrg;
	}

	public void setParentOrg(String parentOrg) {
		this.parentOrg = parentOrg;
	}
}
