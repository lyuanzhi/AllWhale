package com.jumbox.demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumbox.demo.common.idworker.Sid;
import com.jumbox.demo.common.utils.PageResult;
import com.jumbox.demo.mbg.mapper.*;
import com.jumbox.demo.mbg.pojo.*;
import com.jumbox.demo.mbg.vo.GetCommentListVo;
import com.jumbox.demo.mbg.vo.GetCommunityListVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Resource
    private CommunityMapper communityMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private CommunityHeartMapper communityHeartMapper;

    @Resource
    private CommentHeartMapper commentHeartMapper;

    @Resource
    private CommunityTagsMapper communityTagsMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageResult getCommunityList(Integer page, String userId){
        PageHelper.startPage(page, 10);
        Example example = new Example(Community.class);
        example.createCriteria().andEqualTo("isCancelled",0);
        example.setOrderByClause("create_time desc");
        List<Community> communityList = communityMapper.selectByExample(example);

        List<GetCommunityListVo> getCommunityListVoList = new LinkedList<>();
        for (Community community : communityList){
            User user = new User();
            if (community.getIsAnonymous()==0){
                user = userMapper.selectByPrimaryKey(community.getUserId());
            }
            Example example1 = new Example(Comment.class);
            example1.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("isCancelled", 0);
            Integer commentNum = commentMapper.selectCountByExample(example1);

            Example example2 = new Example(CommunityHeart.class);
            example2.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("isCancelled", 0);
            Integer heartNum = communityHeartMapper.selectCountByExample(example2);

            Example example3 = new Example(CommunityHeart.class);
            example3.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("userId", userId)
                    .andEqualTo("isCancelled",0);
            int hasHeart = 0;
            if(communityHeartMapper.selectCountByExample(example3)!=0){
                hasHeart = 1;
            }

            GetCommunityListVo getCommunityListVo = GetCommunityListVo.POJOtoVO(community, user.getUsername(), user.getAvatarUrl(),
                    commentNum, heartNum, hasHeart);

            getCommunityListVoList.add(getCommunityListVo);
        }

        PageInfo<Community> pageList = new PageInfo<>(communityList);
        PageResult pagedResult = new PageResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(getCommunityListVoList);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageResult getCommunityListAdmin(Integer page, String userId){
        PageHelper.startPage(page, 10);
        Example example = new Example(Community.class);
        example.createCriteria().andEqualTo("isCancelled",0);
        example.setOrderByClause("create_time desc");
        List<Community> communityList = communityMapper.selectByExample(example);

        List<GetCommunityListVo> getCommunityListVoList = new LinkedList<>();
        for (Community community : communityList){
            User user = userMapper.selectByPrimaryKey(community.getUserId());
            Example example1 = new Example(Comment.class);
            example1.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("isCancelled", 0);
            Integer commentNum = commentMapper.selectCountByExample(example1);

            Example example2 = new Example(CommunityHeart.class);
            example2.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("isCancelled", 0);
            Integer heartNum = communityHeartMapper.selectCountByExample(example2);

            Example example3 = new Example(CommunityHeart.class);
            example3.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("userId", userId)
                    .andEqualTo("isCancelled",0);
            int hasHeart = 0;
            if(communityHeartMapper.selectCountByExample(example3)!=0){
                hasHeart = 1;
            }

            GetCommunityListVo getCommunityListVo = GetCommunityListVo.POJOtoVO(community, user.getUsername(), user.getAvatarUrl(),
                    commentNum, heartNum, hasHeart);

            getCommunityListVoList.add(getCommunityListVo);
        }

        PageInfo<Community> pageList = new PageInfo<>(communityList);
        PageResult pagedResult = new PageResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(getCommunityListVoList);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Community addCommunity(String userId, String content, Integer isAnonymous, String id, Integer imgNumber, String itsTags){
        Community community = new Community();
        community.setId(id);
        community.setUserId(userId);
        community.setContent(content);
        community.setIsAnonymous(isAnonymous);
        Date date = new Date();
        community.setCreateTime(date.getTime());
        community.setIsCancelled(0);
        community.setImgNumber(imgNumber);
        community.setItsTags(itsTags);
        communityMapper.insert(community);
        return community;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String deleteCommunity(String id){
        Community community = new Community();
        community.setId(id);
        community.setIsCancelled(1);
        communityMapper.updateByPrimaryKeySelective(community);
        return "success";
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<GetCommentListVo> getCommentList(String communityId, String userId){
        Example example = new Example(Comment.class);
        example.createCriteria().andEqualTo("communityId", communityId).andEqualTo("isCancelled",0);
        example.setOrderByClause("create_time asc");
        List<Comment> commentList = commentMapper.selectByExample(example);
        List<GetCommentListVo> getCommentListVoList = new LinkedList<>();
        for (Comment comment : commentList){
            User user = new User();
            if (comment.getIsAnonymous()==0){
                user = userMapper.selectByPrimaryKey(comment.getUserId());
            }

            Example example1 = new Example(CommentHeart.class);
            example1.createCriteria().andEqualTo("commentId", comment.getId()).andEqualTo("isCancelled", 0);
            Integer heartNum = commentHeartMapper.selectCountByExample(example1);

            Example example2 = new Example(CommentHeart.class);
            example2.createCriteria().andEqualTo("commentId", comment.getId()).andEqualTo("userId", userId)
                    .andEqualTo("isCancelled",0);
            int hasHeart = 0;
            if(commentHeartMapper.selectCountByExample(example2)!=0){
                hasHeart = 1;
            }
            GetCommentListVo getCommentListVo = GetCommentListVo.POJOtoVO(comment, user.getUsername(), user.getAvatarUrl(), heartNum, hasHeart);
            getCommentListVoList.add(getCommentListVo);
        }

        Example example3 = new Example(Comment.class);
        example3.createCriteria().andEqualTo("communityId", communityId).andEqualTo("isCancelled",0);
        Comment comment1 = new Comment();
        comment1.setIsChecked(1);
        commentMapper.updateByExampleSelective(comment1, example3);

        return getCommentListVoList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String addComment(String parentId, String userId, String communityId, String content, Integer isAnonymous){
        Comment comment = new Comment();
        comment.setId(Sid.next());
        comment.setParentId(parentId);
        comment.setUserId(userId);
        comment.setCommunityId(communityId);
        comment.setContent(content);
        comment.setIsAnonymous(isAnonymous);
        Date date = new Date();
        comment.setCreateTime(date.getTime());
        comment.setIsCancelled(0);
        comment.setIsChecked(0);
        commentMapper.insert(comment);
        return comment.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String deleteComment(String id){
        Example example = new Example(Comment.class);
        example.createCriteria().andEqualTo("id", id).orEqualTo("parentId", id);

        Comment comment = new Comment();
        comment.setIsCancelled(1);
        commentMapper.updateByExampleSelective(comment, example);

        return "success";
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String changeCommunityHeart(String communityId, String userId, Integer type){
        Example example = new Example(CommunityHeart.class);
        example.createCriteria().andEqualTo("communityId", communityId).andEqualTo("userId", userId);
        CommunityHeart communityHeart = new CommunityHeart();
        Date date = new Date();
        communityHeart.setCreateTime(date.getTime());
        if(communityHeartMapper.selectCountByExample(example)==0){
            communityHeart.setCommunityId(communityId);
            communityHeart.setUserId(userId);
            communityHeart.setId(Sid.next());
            communityHeart.setIsCancelled(0);
            communityHeartMapper.insert(communityHeart);
        }else{
            communityHeart.setIsCancelled(type);
            communityHeartMapper.updateByExampleSelective(communityHeart,example);
        }
        return "success";
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String changeCommentHeart(String commentId, String userId, Integer type){
        Example example = new Example(CommentHeart.class);
        example.createCriteria().andEqualTo("commentId", commentId).andEqualTo("userId", userId);
        CommentHeart commentHeart = new CommentHeart();
        Date date = new Date();
        commentHeart.setCreateTime(date.getTime());
        if(commentHeartMapper.selectCountByExample(example)==0){
            commentHeart.setCommentId(commentId);
            commentHeart.setUserId(userId);
            commentHeart.setId(Sid.next());
            commentHeart.setIsCancelled(0);
            commentHeartMapper.insert(commentHeart);
        }else{
            commentHeart.setIsCancelled(type);
            commentHeartMapper.updateByExampleSelective(commentHeart,example);
        }
        return "success";
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer getCommunityHasHeart(String communityId, String userId){
        Example example = new Example(CommunityHeart.class);
        example.createCriteria().andEqualTo("communityId", communityId).andEqualTo("userId", userId);
        CommunityHeart communityHeart = communityHeartMapper.selectOneByExample(example);
        if(communityHeart == null) {
            return 0;
        }
        return 1 - communityHeart.getIsCancelled();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<GetCommunityListVo> getMyTopic(String userId){
        List<GetCommunityListVo> getCommunityListVoList = new LinkedList<>();
        Example example = new Example(Community.class);
        example.createCriteria().andEqualTo("userId",userId).andEqualTo("isCancelled",0);
        example.setOrderByClause("create_time desc");
        List<Community> communityList = communityMapper.selectByExample(example);
        for (Community community : communityList){
            User user = new User();
            if (community.getIsAnonymous()==0){
                user = userMapper.selectByPrimaryKey(community.getUserId());
            }
            Example example1 = new Example(Comment.class);
            example1.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("isCancelled", 0);
            Integer commentNum = commentMapper.selectCountByExample(example1);

            Example example2 = new Example(CommunityHeart.class);
            example2.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("isCancelled", 0);
            Integer heartNum = communityHeartMapper.selectCountByExample(example2);

            Example example3 = new Example(CommunityHeart.class);
            example3.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("userId", userId)
                    .andEqualTo("isCancelled",0);
            int hasHeart = 0;
            if(communityHeartMapper.selectCountByExample(example3)!=0){
                hasHeart = 1;
            }

            GetCommunityListVo getCommunityListVo = GetCommunityListVo.POJOtoVO(community, user.getUsername(), user.getAvatarUrl(),
                    commentNum, heartNum, hasHeart);

            getCommunityListVoList.add(getCommunityListVo);
        }
        return getCommunityListVoList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String setOfficialTopic(String content){
        Community community = new Community();
        community.setId("1");
        community.setContent(content);
        Date date = new Date();
        community.setCreateTime(date.getTime());
        communityMapper.updateByPrimaryKeySelective(community);
        return "success";
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public GetCommunityListVo getOfficialTopic(String userId){
        Community community1 = new Community();
        community1.setId("1");
        Community community = communityMapper.selectByPrimaryKey(community1);
        Example example1 = new Example(Comment.class);
        example1.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("isCancelled", 0);
        Integer commentNum = commentMapper.selectCountByExample(example1);

        Example example2 = new Example(CommunityHeart.class);
        example2.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("isCancelled", 0);
        Integer heartNum = communityHeartMapper.selectCountByExample(example2);

        Example example3 = new Example(CommunityHeart.class);
        example3.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("userId", userId)
                .andEqualTo("isCancelled",0);
        int hasHeart = 0;
        if(communityHeartMapper.selectCountByExample(example3)!=0){
            hasHeart = 1;
        }
        return GetCommunityListVo.POJOtoVO(community, "ALLWHALE小助手", "../../static/none2.png", commentNum, heartNum, hasHeart);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getCommunityTags(){
        Example example = new Example(CommunityTags.class);
        example.setOrderByClause("create_time asc");
        List<CommunityTags> communityTagsList = communityTagsMapper.selectByExample(example);
        List<String> stringList = new LinkedList<>();
        for (CommunityTags communityTags : communityTagsList){
            stringList.add(communityTags.getName());
        }
        return stringList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageResult searchCommunity(Integer page, String userId, List<String> searchTags){
        PageHelper.startPage(page, 10);
        Example example = new Example(Community.class);
        if(searchTags.size()==0){
            example.createCriteria().andEqualTo("isCancelled",0)
                    .andEqualTo("itsTags",";");
        }else if(searchTags.size()==1){
            example.createCriteria().andEqualTo("isCancelled",0)
                    .andLike("itsTags","%"+searchTags.get(0)+"%");
        }else if(searchTags.size()==2){
            example.createCriteria().andEqualTo("isCancelled",0)
                    .andLike("itsTags","%"+searchTags.get(0)+"%")
                    .andLike("itsTags","%"+searchTags.get(1)+"%");
        }else if(searchTags.size()==3){
            example.createCriteria().andEqualTo("isCancelled",0)
                    .andLike("itsTags","%"+searchTags.get(0)+"%")
                    .andLike("itsTags","%"+searchTags.get(1)+"%")
                    .andLike("itsTags","%"+searchTags.get(2)+"%");
        }
        example.setOrderByClause("create_time desc");
        List<Community> communityList = communityMapper.selectByExample(example);

        List<GetCommunityListVo> getCommunityListVoList = new LinkedList<>();
        for (Community community : communityList){
            User user = new User();
            if (community.getIsAnonymous()==0){
                user = userMapper.selectByPrimaryKey(community.getUserId());
            }
            Example example1 = new Example(Comment.class);
            example1.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("isCancelled", 0);
            Integer commentNum = commentMapper.selectCountByExample(example1);

            Example example2 = new Example(CommunityHeart.class);
            example2.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("isCancelled", 0);
            Integer heartNum = communityHeartMapper.selectCountByExample(example2);

            Example example3 = new Example(CommunityHeart.class);
            example3.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("userId", userId)
                    .andEqualTo("isCancelled",0);
            int hasHeart = 0;
            if(communityHeartMapper.selectCountByExample(example3)!=0){
                hasHeart = 1;
            }

            GetCommunityListVo getCommunityListVo = GetCommunityListVo.POJOtoVO(community, user.getUsername(), user.getAvatarUrl(),
                    commentNum, heartNum, hasHeart);

            getCommunityListVoList.add(getCommunityListVo);
        }

        PageInfo<Community> pageList = new PageInfo<>(communityList);
        PageResult pagedResult = new PageResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(getCommunityListVoList);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

}
