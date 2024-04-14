package com.jumbox.demo.controller.GoodsController;

import com.jumbox.demo.common.idworker.Sid;
import com.jumbox.demo.common.utils.CommonResult;
import com.jumbox.demo.common.utils.PageResult;
import com.jumbox.demo.mbg.vo.DetailedGoodsVo;
import com.jumbox.demo.mbg.vo.GoodsCollectionVo;
import com.jumbox.demo.mbg.vo.GoodsTypeVo;
import com.jumbox.demo.mbg.vo.MyGoodsVo;
import com.jumbox.demo.service.GoodsService;
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
@RequestMapping("/goods")
@Api(value = "物品相关接口", tags = {"物品相关接口"})
public class Goods {

    @Resource
    private GoodsService goodsService;

    @Resource
    private TrackingService trackingService;

    @Resource
    private UserService userService;

    private static final Set<String> SUPPORTED_IMG_FORMATS =
            new HashSet<>(Arrays.asList("bmp", "jpg", "jpeg", "png", "gif"));

    @PostMapping(path = "/getGoodsList")
    public CommonResult<PageResult> getGoodsList(@RequestBody GoodsTypeVo goodsTypeVo) {

        return CommonResult.success(goodsService.getGoodsList(goodsTypeVo));
    }

    @PostMapping(path = "/getGoodsId")
    public CommonResult<String> getGoodsId() {

        return CommonResult.success(Sid.next());
    }

    @PostMapping("/addGoodsImgs")
    public CommonResult<String> addGoodsImgs(HttpServletRequest request, @RequestParam(value = "uploadImg") MultipartFile uploadImg) {

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
            File dir = new File("./goodsImages");
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

    @PostMapping(path = "/addGoods")
    public CommonResult<String> addGoods(@RequestBody com.jumbox.demo.mbg.pojo.Goods goods,
                                         HttpServletRequest request) {
        if(userService.getDisable(goods.getUserId())==1){
            return CommonResult.failed("您已被封号。");
        }
        trackingService.AddTrackingInfo(request, goods.getUserId(), "goods/addGoods (title: " + goods.getName() + ")");
        return CommonResult.success(goodsService.addGoods(goods));
    }

    @PostMapping(path = "/getGoodsDetailed")
    public CommonResult<DetailedGoodsVo> getGoodsDetailed(@RequestParam("goodsId") String goodsId,
                                                          @RequestParam("userId") String userId) {

        return CommonResult.success(goodsService.getGoodsDetailed(goodsId, userId));
    }

    @PostMapping(path = "/changeCollection")
    public CommonResult<String> changeCollection(@RequestParam("goodsId") String goodsId,
                                                 @RequestParam("userId") String userId,
                                                 @RequestParam("type") Integer type,
                                                 HttpServletRequest request) {

        trackingService.AddTrackingInfo(request, userId, "goods/changeCollection (goodsId: " + goodsId + ")");
        return CommonResult.success(goodsService.changeCollection(goodsId, userId, type));
    }

    @PostMapping(path = "/addWanted")
    public CommonResult<Integer> addWanted(@RequestParam("goodsId") String goodsId,
                                           @RequestParam("userId") String userId,
                                           HttpServletRequest request) {

        trackingService.AddTrackingInfo(request, userId, "goods/addWanted (goodsId: " + goodsId + ")");
        return CommonResult.success(goodsService.addWanted(goodsId, userId));
    }

    @PostMapping(path = "/searchGoods")
    public CommonResult<PageResult> searchGoods(@RequestParam("searchValue") String searchValue, @RequestParam("page") Integer page) {

        return CommonResult.success(goodsService.searchGoods(searchValue,page));
    }

    @PostMapping(path = "/cancelGoods/{userId}")
    public CommonResult<String> cancelGoods(@PathVariable String userId,
                                            @RequestParam("id") String id,
                                            HttpServletRequest request) {

        trackingService.AddTrackingInfo(request, userId, "goods/cancelGoods (goodsId: " + id + ")");
        return CommonResult.success(goodsService.cancelGoods(id));
    }

    @PostMapping(path = "/getMyCollection")
    public CommonResult<List<GoodsCollectionVo>> getMyCollection(@RequestParam("userId") String userId) {

        return CommonResult.success(goodsService.getMyCollection(userId));
    }

    @PostMapping(path = "/getMyGoods")
    public CommonResult<List<MyGoodsVo>> getMyGoods(@RequestParam("userId") String userId, @RequestParam("type") String type) {

        return CommonResult.success(goodsService.getMyGoods(userId, type));
    }

    @PostMapping(path = "/deleteGoods/{userId}")
    public CommonResult<String> deleteGoods(@PathVariable String userId,
                                            @RequestParam("id") String id,
                                            HttpServletRequest request) {

        trackingService.AddTrackingInfo(request, userId, "goods/deleteGoods (goodsId: " + id + ")");
        return CommonResult.success(goodsService.deleteGoods(id));
    }

}
