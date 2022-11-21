package com.ssafy.sweethome.comment.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "Comment: 댓글 정보", description = "댓글 정보를 담은 Class")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @ApiModelProperty(value = "댓글 고유 번호")
    private int commentId;

    @ApiModelProperty(value = "게시글 고유 번호")
    private int articleNo;

    @ApiModelProperty(value = "댓글 작성자 계정명")
    private String userId;

    @ApiModelProperty(value = "댓글 내용")
    private String content;

    @ApiModelProperty(value = "댓글 작성일")
    private String regTime;
}

