package com.ssafy.sweethome.favorite_housedeal.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "FavoriteHousedeal: 사용자 관심 거래 정보", description = "사용자의 관심 거래 정보를 담은 Class")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteHousedeal {
    @ApiModelProperty(value = "관심 거래 고유 번호")
    private long housedealNo;

    @ApiModelProperty(value = "사용자 계정")
    private String userId;
}
