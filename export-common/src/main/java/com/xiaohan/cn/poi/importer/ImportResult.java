package com.xiaohan.cn.poi.importer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入Excel结果包装类
 *
 * @author teddy
 * @since 2022/12/20
 */
public class ImportResult implements Serializable{
	
	private static final long serialVersionUID = 6516453896146103930L;

	/**
	 * 是否导入成功
	 * true为成功，false为失败
	 */
	private boolean success;

	/**
	 * 失败信息
	 */
	private String failMsg;

	/**
	 * 失败的具体行号
	 */
	private List<Integer> failRowNums = new ArrayList<>();

	/**
	 * 失败行对应的失败信息
	 */
	private List<String> failRowMsgs = new ArrayList<>();

	/**
	 * 失败行数
	 */
	private int failRowCount = 0;

	/**
	 * 成功行数
	 */
	private int successRowCount = 0;

	/**
	 * 是否有反馈列
	 */
	private boolean feedback;

	/**
	 * 其他信息
	 */
	private String extraMsg;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}

	public List<Integer> getFailRowNums() {
		return failRowNums;
	}

	public void setFailRowNums(List<Integer> failRowNums) {
		this.failRowNums = failRowNums;
	}

	public List<String> getFailRowMsgs() {
		return failRowMsgs;
	}

	public void setFailRowMsgs(List<String> failRowMsgs) {
		this.failRowMsgs = failRowMsgs;
	}

	public int getFailRowCount() {
		return failRowCount;
	}

	public void setFailRowCount(int failRowCount) {
		this.failRowCount = failRowCount;
	}

	public int getSuccessRowCount() {
		return successRowCount;
	}

	public void setSuccessRowCount(int successRowCount) {
		this.successRowCount = successRowCount;
	}
	
	public boolean isFeedback() {
		return feedback;
	}
	
	public void setFeedback(boolean feedback) {
		this.feedback = feedback;
	}

	public String getExtraMsg() {
		return extraMsg;
	}

	public void setExtraMsg(String extraMsg) {
		this.extraMsg = extraMsg;
	}
}
