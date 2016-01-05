/**   
* @Title: UpFileBean.java 
* @Package com.vrv.cems.service.updownload.bean 
* @Description: TODO(用一句话描述该文件做什么) 
* @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
* @date 2015年8月27日 下午3:49:41 
* @version V1.0   
*/
package com.vrv.cems.service.updownload.bean;

/** 
 * @ClassName: UpFileBean 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tangtieqiao
			tangtieqiao@vrvmail.com.cn
 * @date 2015年8月27日 下午3:49:41 
 *  
 */
public class UpFileBean {
	private String devOnlyId;
	private String userOnlyId;
	private String fileName;
	private String crc;
	// base：基础版本，custom：定制版本
	private String type;
	private String productType;
	private String size;
	private String osType;
	/** 
	* <p>Title: </p> 
	* <p>Description: </p>  
	*/
	public UpFileBean() {
		super();
	}
	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param devOnlyId
	* @param userOnlyId
	* @param fileName
	* @param crc
	* @param type
	* @param productType
	* @param size
	* @param osType 
	*/
	public UpFileBean(String devOnlyId, String userOnlyId, String fileName,
			String crc, String type, String productType, String size,
			String osType) {
		super();
		this.devOnlyId = devOnlyId;
		this.userOnlyId = userOnlyId;
		this.fileName = fileName;
		this.crc = crc;
		this.type = type;
		this.productType = productType;
		this.size = size;
		this.osType = osType;
	}
	/** 
	 * @return devOnlyId 
	 */
	public String getDevOnlyId() {
		return devOnlyId;
	}
	/** 
	 * @param devOnlyId 要设置的 devOnlyId 
	 */
	public void setDevOnlyId(String devOnlyId) {
		this.devOnlyId = devOnlyId;
	}
	/** 
	 * @return userOnlyId 
	 */
	public String getUserOnlyId() {
		return userOnlyId;
	}
	/** 
	 * @param userOnlyId 要设置的 userOnlyId 
	 */
	public void setUserOnlyId(String userOnlyId) {
		this.userOnlyId = userOnlyId;
	}
	/** 
	 * @return fileName 
	 */
	public String getFileName() {
		return fileName;
	}
	/** 
	 * @param fileName 要设置的 fileName 
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/** 
	 * @return crc 
	 */
	public String getCrc() {
		return crc;
	}
	/** 
	 * @param crc 要设置的 crc 
	 */
	public void setCrc(String crc) {
		this.crc = crc;
	}
	/** 
	 * @return type 
	 */
	public String getType() {
		return type;
	}
	/** 
	 * @param type 要设置的 type 
	 */
	public void setType(String type) {
		this.type = type;
	}
	/** 
	 * @return productType 
	 */
	public String getProductType() {
		return productType;
	}
	/** 
	 * @param productType 要设置的 productType 
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/** 
	 * @return size 
	 */
	public String getSize() {
		return size;
	}
	/** 
	 * @param size 要设置的 size 
	 */
	public void setSize(String size) {
		this.size = size;
	}
	/** 
	 * @return osType 
	 */
	public String getOsType() {
		return osType;
	}
	/** 
	 * @param osType 要设置的 osType 
	 */
	public void setOsType(String osType) {
		this.osType = osType;
	}
	/*
	* Title: toString
	*Description: 
	* @return 
	* @see java.lang.Object#toString() 
	*/
	@Override
	public String toString() {
		return "UpFileBean [devOnlyId=" + devOnlyId + ", userOnlyId="
				+ userOnlyId + ", fileName=" + fileName + ", crc=" + crc
				+ ", type=" + type + ", productType=" + productType + ", size="
				+ size + ", osType=" + osType + "]";
	}
	


	
}
