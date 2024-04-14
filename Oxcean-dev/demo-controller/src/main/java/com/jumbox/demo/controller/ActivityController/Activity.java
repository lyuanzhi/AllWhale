package com.jumbox.demo.controller.ActivityController;

import com.jumbox.demo.common.utils.CommonResult;
import com.jumbox.demo.common.utils.PageResult;
import com.jumbox.demo.mbg.vo.DetailedActivityVo;
import com.jumbox.demo.mbg.vo.GetActivityVo;
import com.jumbox.demo.service.ActivityService;
import com.jumbox.demo.service.TrackingService;
import com.jumbox.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/activity")
@Api(value = "活动相关接口", tags = {"活动相关接口"})
public class Activity {

    @Resource
    ActivityService activityService;

    @Resource
    private TrackingService trackingService;

    @Resource
    private UserService userService;

    private static final Set<String> SUPPORTED_IMG_FORMATS =
            new HashSet<>(Arrays.asList("bmp", "jpg", "jpeg", "png", "gif"));

    @ApiOperation(value = "添加活动")
    @PostMapping("/addActivity/{userId}")
    public CommonResult<com.jumbox.demo.mbg.pojo.Activity> addActivity(@PathVariable String userId,
                                                                       @RequestBody com.jumbox.demo.mbg.pojo.Activity activity,
                                                                       HttpServletRequest request) {
        if(userService.getDisable(userId)==1){
            return CommonResult.failed("您已被封号。");
        }
        trackingService.AddTrackingInfo(request, userId, "activity/addActivity (title: " + activity.getTitle() + ")");
        return CommonResult.success(activityService.addActivity(activity));
    }

    @ApiOperation(value = "获取活动列表")
    @PostMapping("/getActivity")
    public CommonResult<PageResult> getActivity(@RequestParam("page") Integer page, @RequestParam("activityType") String activityType) {

        return CommonResult.success(activityService.getActivity(page, activityType));
    }

    @PostMapping("/addActivityImgs")
    public CommonResult<String> addActivityImgs(HttpServletRequest request, @RequestParam(value = "uploadImg") MultipartFile uploadImg) {
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
            byte[] bytes = uploadImg.getBytes();
            File dir = new File("./activityImages");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, String.format("img_%s.%s", id, "jpg"));
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

    @PostMapping(path = "/getActivityDetailed")
    public CommonResult<DetailedActivityVo> getActivityDetailed(@RequestParam("activityId") String activityId,
                                                                @RequestParam("userId") String userId) {

        return CommonResult.success(activityService.getActivityDetailed(activityId, userId));
    }

    @PostMapping(path = "/changeCollection")
    public CommonResult<String> changeCollection(@RequestParam("activityId") String activityId,
                                                 @RequestParam("userId") String userId,
                                                 @RequestParam("type") Integer type,
                                                 HttpServletRequest request) {

        trackingService.AddTrackingInfo(request, userId, "activity/changeCollection (activityId: " + activityId + ")");
        return CommonResult.success(activityService.changeCollection(activityId, userId, type));
    }

    @PostMapping(path = "/getMyActivity")
    public CommonResult<List<GetActivityVo>> getMyActivity(@RequestParam("userId") String userId) {

        return CommonResult.success(activityService.getMyActivity(userId));
    }

    @PostMapping(path = "/cancelActivity/{userId}")
    public CommonResult<String> cancelActivity(@PathVariable String userId,
                                               @RequestParam("id") String id,
                                               HttpServletRequest request) {

        trackingService.AddTrackingInfo(request, userId, "activity/cancelActivity (activityId: " + id + ")");
        return CommonResult.success(activityService.cancelActivity(id));
    }

    @PostMapping(path = "/deleteActivity/{userId}")
    public CommonResult<String> deleteActivity(@PathVariable String userId,
                                               @RequestParam("id") String id,
                                               HttpServletRequest request) {

        trackingService.AddTrackingInfo(request, userId, "activity/deleteActivity (activityId: " + id + ")");
        return CommonResult.success(activityService.deleteActivity(id));
    }

    @PostMapping(path = "/getMyCollection")
    public CommonResult<List<GetActivityVo>> getMyCollection(@RequestParam("userId") String userId) {

        return CommonResult.success(activityService.getMyCollection(userId));
    }

}
