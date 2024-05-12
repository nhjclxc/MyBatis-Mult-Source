package com.nhjclxc.mms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BizOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long orderId;
    private String goodsName;
    private String destination;
    private LocalDateTime orderTime;
}
