package com.dfocus.pmsg.service.atom.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfocus.pmsg.common.dao.MintMapper;
import com.dfocus.pmsg.common.entity.Mint;
import com.dfocus.pmsg.service.atom.IMintService;
import com.dfocus.pmsg.service.dto.mint.MintDto;
import com.dfocus.pmsg.service.dto.mint.SearchMint;
import com.dfocus.pmsg.service.transfer.MintDtoTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: baozi
 * @Date: 2019/6/25 15:27
 * @Description:
 */
@Service
public class MintServiceImpl extends ServiceImpl<MintMapper, Mint> implements IMintService {

	@Autowired
	MintMapper mintDao;

	@Override
	public boolean createMint(MintDto mintDto) {
		Mint mint = MintDtoTransfer.dtoTransPo(mintDto);
		int id = mintDao.insert(mint);
		return id > 0;
	}

	@Override
	public List<MintDto> getMints(SearchMint searchMint) {
		List<MintDto> mintDtos = new ArrayList<>();

		LambdaQueryWrapper<Mint> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(Mint::getAccount, searchMint.getAccount());
		wrapper.eq(Mint::getNickName, searchMint.getNickName());

		List<Mint> mints = mintDao.selectList(wrapper);
		mints.forEach(mint -> mintDtos.add(MintDtoTransfer.poTransDto(mint)));
		return mintDtos;
	}

	/**
	 * todo 简单mock数据 目前还没有引入分页插件 待替换mybatis和选型分页插件 计划： 1. mybatis-plus + spring common 2.
	 * mybatis + PageHelper
	 */
	@Override
	public Page<MintDto> listMints(Pageable pageable) {
		List<MintDto> mintDtos = new ArrayList<>();
		List<Mint> mints = mintDao.selectList(Wrappers.emptyWrapper());
		mints.forEach(mint -> mintDtos.add(MintDtoTransfer.poTransDto(mint)));
		return new PageImpl<>(mintDtos);
	}

}
