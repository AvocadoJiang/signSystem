package com.isl.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.isl.entity.request.AcademyReq;

import lombok.Data;

/**
 * 学院信息实体类
 * @author Jason Chiang
 *
 */

@Entity
@Table(name="academy")
@Data
public class Academy {
	
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
	private String academyID;
	
	/** 学院名称 **/
	private String academyName;
	
	/** 创建者 **/
	private String createBy;
	
	/** 最近一次更新者 **/
	private String updateBy;
	
	/** 创建时间 **/
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createTime;

	/** 更新时间 **/
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updateTime;
	
	
	
	public Academy() {
		super();
	}
	
	public Academy(AcademyReq req) {
		super();
		this.academyName = req.getAcademy_name();
	}
	
	
}
