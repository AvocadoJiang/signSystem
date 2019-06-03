package com.isl.entity.response;

import java.io.Serializable;

import com.isl.entity.Academy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="academyResp",description="学院信息")
@Data
public class AcademyResp implements Serializable {
	
	@ApiModelProperty(value="学院信息ID")
	private String academy_id;
	
	@ApiModelProperty(value="学院名称")
	private String academy_name;
	
	public AcademyResp(Academy entity) {
		super();
		this.academy_id = entity.getAcademyID();
		this.academy_name = entity.getAcademyName();
	}
	
}
