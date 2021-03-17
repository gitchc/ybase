package com.yonyou.mde.web.start;

/**
 * LevelValue���ݴ��ݶ������άƽ̨��
 * @author sunzeg
 * @since 2020-08-27
 *
 */
public class LevelValueDTO {

	/**
     *  �ڵ�PKֵ
     */
    private String pk;
    /**
     *  �ڵ�CODEֵ
     */
    private String code;

    /**
     * ά�Ȳ�����
     */
    private String pkLevel;
    
    /**
     * ά�Ȳ����
     */
    private String codeLevel;
    
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
	public String getPkLevel() {
		return pkLevel;
	}
	public void setPkLevel(String pkLevel) {
		this.pkLevel = pkLevel;
	}
	public String getCodeLevel() {
		return codeLevel;
	}
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}
	
	public String toString() {
		return code + "-" + codeLevel;
	}
}
