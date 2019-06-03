package com.isl.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isl.entity.Academy;
import com.isl.entity.request.AcademyReq;
import com.isl.entity.response.AcademyResp;
import com.isl.service.AcademyRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "学院信息相关接口")
@RestController
@RequestMapping("/academy")
public class AcademyController {
	private AcademyRepository academyRepository;
	
	/**
	 * 构造函数注入
	 * @param academyRepository
	 */
	public AcademyController(AcademyRepository academyRepository) {
		super();
		this.academyRepository = academyRepository;
	}
	@ApiOperation(value = "获取全部学院信息" ,  notes="获取全部学院信息,以数组形式一次性返回数据")
	@ApiResponses({@ApiResponse(code = 200, message = "操作成功",response = AcademyResp.class),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 400, message = "客户端请求的语法错误,服务器无法理解"),
        @ApiResponse(code = 405, message = "权限不足")})
	@GetMapping("/all")
	public List<AcademyResp> getAll(){
		return academyRepository.findAll()
				.stream()
				.map(entity->new AcademyResp(entity))
				.collect(Collectors.toList());
	}
	
	
	@ApiOperation(value = "新增学院信息" ,  notes="上传必要的学院信息信息来创建一个新的学院信息")
	@ApiResponses({@ApiResponse(code = 200, message = "操作成功",response = AcademyResp.class),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 400, message = "客户端请求的语法错误,服务器无法理解"),
        @ApiResponse(code = 405, message = "权限不足")})
	@PostMapping("/add")
	public AcademyResp add(@ApiParam(value="需要更新的课时信息,以json格式放入Request Body中",required=true) @Valid @RequestBody AcademyReq academyReq,HttpSession session) {
		
		Academy academy = new Academy(academyReq);
		//学院信息自定义完整性进行校验
		AcademyCheck(academy);
		
		academyRepository.save(academy);
		return new AcademyResp(academy);
		
	}
	
	@ApiOperation(value = "删除学院信息" ,  notes="根据学院信息的academy_id来删除一个学院信息")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "path",name = "academy_id", value = "被操作的目标主键,直接放入地址中,替换{academy_id}", required = true) })
	@ApiResponses({@ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 400, message = "客户端请求的语法错误,服务器无法理解"),
        @ApiResponse(code = 405, message = "权限不足")})
	@DeleteMapping("/{academy_id}")
	public ResponseEntity<Void> delete(@PathVariable("academy_id")String academy_id,HttpSession session){
		return academyRepository.findById(academy_id)
				.map(entity->{
					academyRepository.delete(entity);
					return new ResponseEntity<Void>(HttpStatus.OK);})
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@ApiOperation(value = "更新学院信息信息" ,  notes="通过academy_id定位学院信息并更新其信息")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "path",name = "academy_id", value = "被操作的目标主键,直接放入地址中,替换{academy_id}", required = true) })
	@ApiResponses({@ApiResponse(code = 200, message = "操作成功",response = AcademyResp.class),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 400, message = "客户端请求的语法错误,服务器无法理解"),
        @ApiResponse(code = 405, message = "权限不足")})
	@PutMapping("/{academy_id}")
	public ResponseEntity<AcademyResp> update(@PathVariable("academy_id")String academy_id,
			@ApiParam(value="需要更新的课时信息,以json格式放入Request Body中",required=true) @RequestBody AcademyReq academyReq,HttpSession session){
		
		Academy academy = new Academy(academyReq);
		
		return academyRepository.findById(academy_id)
				.map(entity->{
					if(StringUtils.isNotBlank(academy.getAcademyName())) {
						entity.setAcademyName(academy.getAcademyName());
					}
					
					AcademyCheck(entity);
					
					academyRepository.save(entity);
					return entity;})
				.map(entity->new ResponseEntity<AcademyResp>(new AcademyResp(entity),HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@ApiOperation(value = "根据主键查找学院信息" ,  notes="根据学院信息academy_id查找学院信息")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "path",name = "academy_id", value = "被操作的目标主键,直接放入地址中,替换{academy_id}", required = true) })
	@ApiResponses({@ApiResponse(code = 200, message = "操作成功",response = AcademyResp.class),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 400, message = "客户端请求的语法错误,服务器无法理解"),
        @ApiResponse(code = 405, message = "权限不足")})
	@GetMapping("/{academy_id}")
	public  ResponseEntity<AcademyResp> findByID(@PathVariable("academy_id")String academy_id){
		return academyRepository.findById(academy_id)
				.map(entity->new ResponseEntity<AcademyResp>(new AcademyResp(entity),HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	//学院信息自定义完整性进行校验，校验有问题就抛异常
	private void AcademyCheck(@Valid Academy entity) {
		
	}
	
}
