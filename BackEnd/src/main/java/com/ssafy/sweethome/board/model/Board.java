package com.ssafy.sweethome.board.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "Board: 게시글 정보", description = "게시글 정보를 담은 Class")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @ApiModelProperty(value = "게시글 고유 번호")
    private int articleNo;

    @ApiModelProperty(value = "작성자 계정")
    private String userId;

    @ApiModelProperty(value = "게시글 제목")
    private String subject;

    @ApiModelProperty(value = "게시글 내용")
    private String content;

    @ApiModelProperty(value = "조회수")
    private int hit;

    @ApiModelProperty(value = "게시글 작성일")
    private String regTime;
}
