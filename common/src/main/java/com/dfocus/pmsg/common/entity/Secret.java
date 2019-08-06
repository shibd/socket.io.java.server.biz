package com.dfocus.pmsg.common.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目公钥表
 * </p>
 *
 * @author baozi
 * @since 2019-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Secret extends Model<Secret> {

    private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 公钥
     */
    private String publicKey;


    @Override
    protected Serializable pkVal() {
        return this.projectId;
    }

}
