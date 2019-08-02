package com.dfocus.pmsg.transfer;

import com.dfocus.pmsg.service.dto.mint.MintDto;
import com.dfocus.pmsg.vo.mint.MintVo;

/**
 * @Auther: baozi
 * @Date: 2019/6/27 11:01
 * @Description:
 */
public abstract class MintVoTransfer {

	public static MintDto voToDto(MintVo mintVo) {
		MintDto mintDto = new MintDto();
		mintDto.setAccount(mintVo.getAccount());
		mintDto.setPassword(mintVo.getPassword());
		mintDto.setNickName(mintVo.getNickName());
		return mintDto;
	}

	public static MintVo dtoToVo(MintDto mintDto) {
		MintVo mintVo = new MintVo();
		mintVo.setAccount(mintDto.getAccount());
		mintVo.setPassword(mintDto.getPassword());
		mintVo.setNickName(mintDto.getNickName());
		return mintVo;
	}

}
