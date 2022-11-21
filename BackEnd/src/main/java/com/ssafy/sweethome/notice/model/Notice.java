package com.ssafy.sweethome.notice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "Notice: 공지사항 정보", description = "공지사항의 정보를 담은 Class")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @ApiModelProperty(value = "공지사항 고유 번호")
    private int articleNo;

    @ApiModelProperty(value = "공지사항 작성자 계정")
    private String userId;

    @ApiModelProperty(value = "공지사항 제목")
    private String subject;

    @ApiModelProperty(value = "공지사항 내용")
    private String content;

    @ApiModelProperty(value = "조회수")
    private int hit;

    @ApiModelProperty(value = "공지사항 작성일")
    private String regTime;
}
