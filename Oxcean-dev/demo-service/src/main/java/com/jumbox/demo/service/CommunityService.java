package com.jumbox.demo.service;

import com.jumbox.demo.common.utils.PageResult;
import com.jumbox.demo.mbg.pojo.Community;
import com.jumbox.demo.mbg.vo.GetCommentListVo;
import com.jumbox.demo.mbg.vo.GetCommunityListVo;

import java.util.List;

public interface CommunityService {

    public PageResult getCommunityList(Integer page, String userId);

    public PageResult getCommunityListAdmin(Integer page, String userId);

    public Community addCommunity(String userId, String content, Integer isAnonymous, String id, Integer imgNumber, String itsTags);

    public String deleteCommunity(String id);

    public List<GetCommentListVo> getCommentList(String communityId, String userId);

    public String addComment(String parentId, String userId, String communityId, String content, Integer isAnonymous);

    public String deleteComment(String id);

    public String changeCommunityHeart(String communityId, String userId, Integer type);

    public String changeCommentHeart(String commentId, String userId, Integer type);

    public Integer getCommunityHasHeart(String communityId, String userId);

    public List<GetCommunityListVo> getMyTopic(String userId);

    public String setOfficialTopic(String content);

    public GetCommunityListVo getOfficialTopic(String userId);

    public List<String> getCommunityTags();

    public PageResult searchCommunity(Integer page, String userId, List<String> searchTags);

}
