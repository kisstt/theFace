package com.face.exception;


import com.face.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;


/**
 * 2012
 * 
 * @author xgf
 * 
 */
public class BaseAppException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5181998288644329015L;

	/** 异常代码. */
	private String code;

	/** 异常描述. */
	private String desc;

	/** 异常发生时间. */
	private Date time;

	/** 错误堆栈. */
	private Throwable cause;

	/**
	 * 默认构造器.
	 */
	public BaseAppException() {
		super();
	}

	/**
	 * BaseAppException
	 * 
	 * @param code
	 * @param desc
	 * @param cause
	 */
	public BaseAppException(String code, String desc, Throwable cause) {
		super(desc, cause);
		this.code = code;
		this.desc = desc;
		this.cause = cause;
		this.time = DateUtils.getNowDate();
	}

	/**
	 * 参数构造器.
	 * 
	 * @param msg
	 *            String 错误消息
	 */
	public BaseAppException(String msg) {
		super(msg);
	}

	public BaseAppException(String msg, Throwable e) {
		super(msg, e);
	}
	
	/**
	 * 参数构造器
	 * 
	 * @param code
	 *            String 错误编码
	 * @param msg
	 *            String 错误消息
	 */
	public BaseAppException(String code, String msg) {
		super(msg);
		this.code = code;
		this.desc = msg;
	}

	/**
	 * 设置错误编码.
	 * 
	 * @param code
	 *            String
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 设置错误描述信息.
	 * 
	 * @param desc
	 *            String
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 设置错误发生时间.
	 * 
	 * @param time
	 *            Date
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * 设置错误.
	 * 
	 * @param cause
	 *            Throwable
	 */
	public void setThrowable(Throwable cause) {
		this.cause = cause;
	}

	/**
	 * 得到错误编码.
	 * 
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 得到错误描述.
	 * 
	 * @return String
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * 得到错误发生时间.
	 * 
	 * @return Date
	 */
	public Date getTime() {
		if (time == null) {
			time = new Date();
		}
		return time;
	}

	/**
	 * 得到错误
	 * 
	 * @return Throwable
	 */
	public Throwable getCause() {
		return this.cause;
	}
}
