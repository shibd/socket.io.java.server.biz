package com.dfocus.pmsg.service.atom;

import com.dfocus.pmsg.service.dto.mint.SearchMint;
import com.dfocus.pmsg.service.dto.mint.MintDto;
import com.dfocus.pmsg.common.entity.Mint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Auther: baozi
 * @Date: 2019/6/25 14:57
 * @Description:
 */
public interface IMintService extends IService<Mint> {

	/**
	 * 创建mint
	 * @param mintDto
	 * @return
	 */
	boolean createMint(MintDto mintDto);

	/**
	 * 搜索用户
	 * @param searchMint
	 * @return
	 */
	List<MintDto> getMints(SearchMint searchMint);

	/**
	 * 分页用户
	 * @return
	 */
	Page<MintDto> listMints(Pageable pageable);

}
