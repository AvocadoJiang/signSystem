package com.isl.entity.request;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="academyReq",description="学院信息")
@Data
public class AcademyReq {
	
	@ApiModelProperty(value="学院的名称", example="电子与信息工程学院")
	@NotNull
	private String academy_name;

}
