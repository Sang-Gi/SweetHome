package com.ssafy.sweethome.favorite_region.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "FavoriteRegion: 사용자 관심 지역 정보", description = "사용자의 관심 지역 정보를 담은 Class")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRegion {
    @ApiModelProperty(value = "사용자 계정")
    private String userId;

    @ApiModelProperty(value = "관심 지역 고유 번호")
    private String dongCode;
}
