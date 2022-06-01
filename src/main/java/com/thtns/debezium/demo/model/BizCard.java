package com.thtns.debezium.demo.model;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员办理的业务卡
 * </p>
 *
 * @author thtns
 * @since 2021-03-29
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BizCard {

    private static final long serialVersionUID = 1L;


    private Long memberId;


    private Integer type;


    private Integer num;


    private BigDecimal balance;


    private LocalDate validDate;


    private Long carId;


    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
