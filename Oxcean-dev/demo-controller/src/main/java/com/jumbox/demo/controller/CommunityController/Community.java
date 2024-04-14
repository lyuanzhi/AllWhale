package com.jumbox.demo.controller.CommunityController;

import com.jumbox.demo.common.idworker.Sid;
import com.jumbox.demo.common.utils.CommonResult;
import com.jumbox.demo.common.utils.PageResult;
import com.jumbox.demo.mbg.vo.GetCommentListVo;
import com.jumbox.demo.mbg.vo.GetCommunityListVo;
import com.jumbox.demo.service.CommunityService;
import com.jumbox.demo.service.TrackingService;
import com.jumbox.demo.service.UserService;
import io.swagger.annotations.Api;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("/community")
@Api(value = "社区相关接口", tags = {"社区相关接口"})
public class Community {

    @Resource
    private CommunityService communityService;

    @Resource
    private TrackingService trackingService;

    @Resource
    private UserService userService;

    private static final Set<String> SUPPORTED_IMG_FORMATS =
            new HashSet<>(Arrays.asList("bmp", "jpg", "jpeg", "png", "gif"));

    @PostMapping(path = "/getCommunityList")
    public CommonResult<PageResult> getCommunityList(@RequestParam("page") Integer page, @RequestParam("userId") String userId) {
        return CommonResult.success(communityService.getCommunityList(page, userId));
    }

    @PostMapping(path = "/getCommunityListAdmin")
    public CommonResult<PageResult> getCommunityListAdmin(@RequestParam("page") Integer page, @RequestParam("userId") String userId) {
        return CommonResult.success(communityService.getCommunityListAdmin(page, userId));
    }

    @PostMapping(path = "/addCommunity")
    public CommonResult<com.jumbox.demo.mbg.pojo.Community> addCommunity(@RequestParam("userId") String userId, @RequestParam("content") String content,
                                                                         @RequestParam("isAnonymous") Integer isAnonymous, @RequestParam("id") String id,
                                                                         @RequestParam("imgNumber") Integer imgNumber, @RequestParam("itsTags") String itsTags,
                                                                         HttpServletRequest request) {
        if(userService.getDisable(userId)==1){
            return CommonResult.failed("您已被封号。");
        }
        trackingService.AddTrackingInfo(request, userId, "community/addCommunity (content: " + content + ")");
        return CommonResult.success(communityService.addCommunity(userId, content, isAnonymous, id, imgNumber, itsTags));
    }

    @PostMapping(path = "/deleteCommunity/{userId}")
    public CommonResult<String> deleteCommunity(@PathVariable String userId,
                                                @RequestParam("id") String id,
                                                HttpServletRequest request) {
        trackingService.AddTrackingInfo(request, userId, "community/deleteCommunity (communityId: " + id + ")");
        return CommonResult.success(communityService.deleteCommunity(id));
    }

    @PostMapping(path = "/getCommentList")
    public CommonResult<List<GetCommentListVo>> getCommentList(@RequestParam("communityId") String communityId, @RequestParam("userId") String userId) {
        return CommonResult.success(communityService.getCommentList(communityId, userId));
    }

    @PostMapping(path = "/addComment")
    public CommonResult<String> addComment(@RequestParam("parentId") String parentId, @RequestParam("userId") String userId,
                                           @RequestParam("communityId") String communityId, @RequestParam("content") String content,
                                           @RequestParam("isAnonymous") Integer isAnonymous, HttpServletRequest request) {
        if(userService.getDisable(userId)==1){
            return CommonResult.failed("您已被封号。");
        }
        trackingService.AddTrackingInfo(request, userId, "community/addComment (communityId: " + communityId + ")");
        return CommonResult.success(communityService.addComment(parentId,userId,communityId,content,isAnonymous));
    }

    @PostMapping(path = "/deleteComment/{userId}")
    public CommonResult<String> deleteComment(@PathVariable String userId,
                                              @RequestParam("id") String id,
                                              HttpServletRequest request) {
        trackingService.AddTrackingInfo(request, userId, "community/deleteComment (commentId: " + id + ")");
        return CommonResult.success(communityService.deleteComment(id));
    }

    @PostMapping(path = "/changeCommunityHeart")
    public CommonResult<String> changeCommunityHeart(@RequestParam("communityId") String communityId, @RequestParam("userId") String userId,
                                                     @RequestParam("type") Integer type, HttpServletRequest request) {
        trackingService.AddTrackingInfo(request, userId, "community/changeCommunityHeart (communityId: " + communityId + ")");
        return CommonResult.success(communityService.changeCommunityHeart(communityId, userId, type));
    }

    @PostMapping(path = "/changeCommentHeart")
    public CommonResult<String> changeCommentHeart(@RequestParam("commentId") String commentId, @RequestParam("userId") String userId,
                                                   @RequestParam("type") Integer type, HttpServletRequest request) {
        trackingService.AddTrackingInfo(request, userId, "community/changeCommentHeart (commentId: " + commentId + ")");
        return CommonResult.success(communityService.changeCommentHeart(commentId, userId, type));
    }

    @PostMapping(path = "/getCommunityHasHeart")
    public CommonResult<Integer> getCommunityHasHeart(@RequestParam("communityId") String communityId, @RequestParam("userId") String userId) {
        return CommonResult.success(communityService.getCommunityHasHeart(communityId, userId));
    }

    @PostMapping(path = "/getMyTopic")
    public CommonResult<List<GetCommunityListVo>> getMyTopic(@RequestParam("userId") String userId) {

        return CommonResult.success(communityService.getMyTopic(userId));
    }

//    @PostMapping(path = "/setOfficialTopic")
//    public CommonResult<String> setOfficialTopic(@RequestParam("content") String content) {
//
//        return CommonResult.success(communityService.setOfficialTopic(content));
//    }

    @PostMapping(path = "/getOfficialTopic")
    public CommonResult<GetCommunityListVo> getOfficialTopic(@RequestParam("userId") String userId) {

        return CommonResult.success(communityService.getOfficialTopic(userId));
    }

    @PostMapping(path = "/getCommunityTags")
    public CommonResult<List<String>> getCommunityTags() {

        return CommonResult.success(communityService.getCommunityTags());
    }

    @PostMapping(path = "/getCommunityId")
    public CommonResult<String> getCommunityId() {

        return CommonResult.success(Sid.next());
    }

    @PostMapping("/addCommunityImgs")
    public CommonResult<String> addCommunityImgs(HttpServletRequest request, @RequestParam(value = "uploadImg") MultipartFile uploadImg) {

        if (uploadImg == null) {
            return CommonResult.failed("上传失败，图片不存在!");
        }
        String fileName = Objects.requireNonNull(uploadImg.getOriginalFilename()).toLowerCase();
        String imgExtension;
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            imgExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            imgExtension = "";
        }
        if (!SUPPORTED_IMG_FORMATS.contains(imgExtension)) {
            return CommonResult.failed("上传失败，请选择BMP、JPG、JPEG、PNG、GIF文件！");
        }
        try {
            String id = request.getParameter("id");
            String index = request.getParameter("index");
            byte[] bytes = uploadImg.getBytes();
            File dir = new File("./communityImages");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, String.format("img_%s_%s.%s", id, index, "jpg"));
            OutputStream out = new FileOutputStream(file);
            out.write(bytes);
            out.close();
            Thumbnails.of(file).scale(1f).outputQuality(0.25f).toFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.failed("服务器接收文件失败！");
        }
        return CommonResult.success("success");
    }

    @PostMapping(path = "/searchCommunity")
    public CommonResult<PageResult> searchCommunity(@RequestParam("page") Integer page, @RequestParam("userId") String userId,
                                                    @RequestParam("searchTags") List<String> searchTags) {
        return CommonResult.success(communityService.searchCommunity(page, userId, searchTags));
    }

}
