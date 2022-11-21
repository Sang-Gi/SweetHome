package com.ssafy.sweethome.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "User: 사용자 정보", description = "사용자 정보를 담은 Class")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @ApiModelProperty(value = "사용자 계정")
    private String userId;

    @ApiModelProperty(value = "사용자 계정 비밀번호")
    private String userPwd;

    @ApiModelProperty(value = "사용자 이름")
    private String userName;

    @ApiModelProperty(value = "사용자 거주지 주소")
    private String userAddress;

    @ApiModelProperty(value = "사용자 이메일 계정")
    private String userEmail;

    @ApiModelProperty(value = "사용자 유형(관리자, 일반 회원)")
    private String type;

    @ApiModelProperty(value = "가입일")
    private String joinDate;

    @ApiModelProperty(value = "토큰")
    private String token;

    @ApiModelProperty(value = "프로필 사진 이름")
    private String originProfileImgName;
}
