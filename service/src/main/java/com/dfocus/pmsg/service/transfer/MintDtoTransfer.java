package com.dfocus.pmsg.service.transfer;

import com.dfocus.pmsg.common.entity.Mint;
import com.dfocus.pmsg.service.dto.mint.MintDto;

/**
 * @Auther: baozi
 * @Date: 2019/6/25 15:33
 * @Description:
 */
public abstract class MintDtoTransfer {

	public static Mint dtoTransPo(MintDto mintDto) {
		Mint mint = new Mint();
		mint.setAccount(mintDto.getAccount());
		mint.setPassword(mintDto.getPassword());
		mint.setNickName(mintDto.getNickName());
		return mint;
	}

	public static MintDto poTransDto(Mint mintPo) {
		MintDto mintDto = new MintDto();
		mintDto.setAccount(mintPo.getAccount());
		mintDto.setPassword(mintPo.getPassword());
		mintDto.setNickName(mintPo.getNickName());
		return mintDto;
	}

}
