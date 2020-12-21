package com.conpany.project;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * jquery easyui树的vo 封装
 * @author lk
 *
 */
public class TreeVo implements Serializable {

	private String id;
	private String text;
	private String iconCls;//图标
	private List<TreeVo> children = new ArrayList<>();
	private String state;//closed,open
	private Map<String,String> attributes = new HashMap<String, String>();
	private Boolean checked;
	private Boolean isChecked = true;
	private String parent;
	private String pid;
	private String ptext;
	private boolean open = true;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
        if (!open) {
            this.state = "closed";
        }

    }

    private int depth; //树形结构的深的
	private boolean hasChildren = false;
	private boolean issubsetplus = false;
    @JSONField(serialize = false)//不参与Json序列化
	private TreeVo parentVo;
	private String timebalance;
    private int descenCount=0;//后代所有数量
	private String name;//多语言版本 显示用
	private String defaultName;
    @JSONField(serialize = false)//不参与Json序列化
	private List<String> childrenText = new ArrayList<String>();
	private String describe;
	private String script;
	private String remark;
	private String rule;
	private String showProcess;
	private String submit;
	private String reject;
	private String time;
    @JSONField(serialize = false)//不参与Json序列化
    private int getCJCount=-1;//叉积值

    public int getGetCJCount() {
        if (getCJCount == -1) {
            return getChildCount();
        }
        return getCJCount;
    }

    public void setGetCJCount(int getCJCount) {
        this.getCJCount = getCJCount;
    }

    public void setParent(TreeVo treeVo){
		treeVo.addChildren(this);
		this.setParentVo(treeVo);
	}
    public TreeVo(){

    }
    public TreeVo(String id,String name){
	    this.id = id;
	    this.name = name;
    }
	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public void addChildren(TreeVo treeVo){
		if(children == null){
			children = new ArrayList<TreeVo>();
		}
		treeVo.setPtext(this.getText());
        treeVo.setParentVo(this);
		children.add(treeVo);
        this.addDescenCount(treeVo.getDescenCount()+1);
    }
    public int getDescenCount() {
        return descenCount;
    }
	public void addAttribute(String key,String value){
		if(attributes == null){
			attributes = new HashMap<String, String>();
		}
		attributes.put(key, value);
	}


	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public List<TreeVo> getChildren() {
		return children;
	}
	public void setChildren(List<TreeVo> children) {
		this.children = children;
	}
	public String getState() {
		return state;
	}
	
	public String getTimebalance() {
		return timebalance;
	}

	public void setTimebalance(String timebalance) {
		this.timebalance = timebalance;
	}

	public void setState(String state) {
		this.state = state;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public String getAttributeValue(String paramname){
		if(attributes != null){
			return attributes.get(paramname);
		}
		return null;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

    public boolean issubsetplus() {
        return issubsetplus;
    }

    public void setissubsetplus(boolean issubsetplus) {
        this.issubsetplus = issubsetplus;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPtext() {
		return ptext;
	}

	public void setPtext(String ptext) {
		this.ptext = ptext;
	}
	
	public TreeVo getParentVo() {
		return parentVo;
	}

	public void setParentVo(TreeVo parentVo) {
		this.parentVo = parentVo;
	}

	public int getChildCount(){
		int count = 0;
		if(this.getChildren() != null && this.getChildren().size() > 0){
			for (TreeVo vo : this.getChildren()) {
				count++;
				count = count + vo.getChildCount();
			}
		}
		return count;
	}
	
	public int getParentCount(){
		if(this.getParentVo() != null){
			return 1 + this.getParentVo().getParentCount();
		}else{
			return 0;
		}
	}
    public void addDescenCount(int descenCount){
        this.descenCount += descenCount;
        if (this.getParentVo()!=null)
            this.getParentVo().addDescenCount(descenCount);
    }
	@Override
	public String toString() {
		return "TreeVo [id=" + id + ", text=" + text + "]";
	}

	public List<String> getChildrenText() {
		return childrenText;
	}

	public void setChildrenText(List<String> childrenText) {
		this.childrenText = childrenText;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getShowProcess() {
		return showProcess;
	}

	public void setShowProcess(String showProcess) {
		this.showProcess = showProcess;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getReject() {
		return reject;
	}

	public void setReject(String reject) {
		this.reject = reject;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}
}