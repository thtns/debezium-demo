package com.thtns.debezium.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thtns.debezium.demo.model.BizCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 会员办理的业务卡 Mapper 接口
 * </p>
 *
 * @author thtns
 * @since 2021-03-29
 */
@Mapper
public interface BizCardMapper extends BaseMapper<BizCard> {


    List<BizCard> listAllById(@Param("id") Long id, @Param("pageSize") Integer pageSize);

}
