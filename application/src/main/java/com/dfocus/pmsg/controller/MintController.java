package com.dfocus.pmsg.controller;

import com.dfocus.pmsg.service.atom.IMintService;
import com.dfocus.pmsg.service.dto.mint.SearchMint;
import com.dfocus.pmsg.service.dto.mint.MintDto;
import com.dfocus.pmsg.transfer.MintVoTransfer;
import com.dfocus.pmsg.vo.mint.MintVo;
import com.dfocus.mint.web.rsp.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: baozi
 * @Date: 2019/6/11 10:05
 * @Description:
 */
@Api("接口: 薄荷Demo")
@RestController
public class MintController {

	@Autowired
	IMintService mintService;

	@ApiOperation("接口: 创建薄荷")
	@RequestMapping(method = RequestMethod.POST, value = "/mint")
	Response<Boolean> createMint(@RequestBody MintVo mintVo) {
		MintDto mintDto = MintVoTransfer.voToDto(mintVo);
		return Response.success(mintService.createMint(mintDto));
	}

	@ApiOperation("接口: 搜索薄荷")
	@RequestMapping(method = RequestMethod.POST, value = "/mints/search")
	Response<List<MintVo>> getMints(@RequestBody SearchMint searchMint) {
		List<MintVo> mintVos = new ArrayList<>();
		List<MintDto> mints = mintService.getMints(searchMint);
		mints.forEach(mintDto -> mintVos.add(MintVoTransfer.dtoToVo(mintDto)));
		return Response.success(mintVos);
	}

	@ApiOperation("接口: 分页列表")
	@RequestMapping(method = RequestMethod.GET, value = "/mints")
	Response<Page<MintVo>> list(Pageable pageable) {
		Page<MintDto> mints = mintService.listMints(pageable);
		return Response.success(mints.map(MintVoTransfer::dtoToVo));
	}

}
