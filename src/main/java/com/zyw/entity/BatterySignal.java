package com.zyw.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BatterySignal {
    private Long id;
    private Long vin;
    @JsonProperty("Mx")
    private Double Mx;  // 最高电压
    @JsonProperty("Mi")
    private Double Mi;  // 最低电压
    @JsonProperty("Ix")
    private Double Ix;  // 最高电流
    @JsonProperty("Ii")
    private Double Ii;  // 最低电流
}
