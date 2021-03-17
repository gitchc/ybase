package com.yonyou.mde.web.start;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ά�ȳ�Ա���ݴ��ݶ������άƽ̨��
 * @author sunzeg
 * @since 2020-08-27
 *
 */
public class DimMemberDTO {
    
	private String pk;
	
	private String code;
	
	private String parentPk;
	
	private String parentCode;
	
    private float weight;
	
	private String uniqKey;
	
	private long uniqKeyLong;
	
    /**
     * ÿһ��ά�Ȳ��ά��ȡֵ
     */
    private Map<String, LevelValueDTO> levelValues = new LinkedHashMap<String, LevelValueDTO>();

    public String getUniqKey() {
		return uniqKey;
	}

	public void setUniqKey(String uniqKey) {
		this.uniqKey = uniqKey;
	}

	public long getUniqKeyLong() {
		return uniqKeyLong;
	}

	public void setUniqKeyLong(long uniqKeyLong) {
		this.uniqKeyLong = uniqKeyLong;
	}

	public Map<String, LevelValueDTO> getLevelValues() {
		return levelValues;
	}

	public void setLevelValues(Map<String, LevelValueDTO> levelValues) {
		this.levelValues = levelValues;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentPk() {
		return parentPk;
	}

	public void setParentPk(String parentPk) {
		this.parentPk = parentPk;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public LevelValueDTO getLevelValue(String levelCode) {
		return this.levelValues.get(levelCode);
	}
	
	public void setLevelValue(String levelCode, LevelValueDTO value) {
		this.levelValues.put(levelCode, value);
	}
	
	public String toString() {
		return code + "-" + parentCode;
	}
}
