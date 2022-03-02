package com.senxu.omserver.web.controller.service;

import java.util.*;
import java.util.stream.Collectors;

import com.senxu.omserver.common.core.domain.entity.SysDept;
import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.TTowerInfo;
import com.senxu.omserver.service.domain.vo.TYuntaiStatistic;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
import com.senxu.omserver.system.service.ISysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.senxu.omserver.common.annotation.Log;
import com.senxu.omserver.common.core.controller.BaseController;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.enums.BusinessType;
import com.senxu.omserver.service.domain.TLineInfo;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITLineInfoService;
import com.senxu.omserver.service.service.ITTowerInfoService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * t_line_info（线路信息）Controller
 * 
 * @author panyc
 * @date 2021-05-26
 */

@Api("线路信息管理")
@RestController
@RequestMapping("/service/lineInfo")
public class TLineInfoController extends BaseController
{
    @Autowired
    private ITLineInfoService tLineInfoService;
    @Autowired
    private ITTowerInfoService tTowerInfoService; //杆塔Service
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService; //
    @Autowired
    private TokenService tokenService;


    /**
     * 查询t_line_info（线路信息）列表
     */
    @ApiOperation("获取线路列表")
    //@PreAuthorize("@ss.hasPermi('service:lineInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(TLineInfo tLineInfo)
    {
        startPage();
        List<TLineInfo> list = tLineInfoService.selectTLineInfoList(tLineInfo);
        return getDataTable(list);
    }

    /**
     * 查询线路及线路下的杆塔和云台树json
     */
    @ApiOperation("获取线路树")
    //@PreAuthorize("@ss.hasPermi('service:lineInfo:getTreeLine')")
    @GetMapping("/getTreeLine")
    public AjaxResult getTreeLine(String towerName,String towerCode,String yuntaiName,String yuntaiCode,TYuntaiInfo tYuntaiInfo)
    {
        List<HashMap<String, Object>> treeMap = new ArrayList<HashMap<String, Object>>();//树状Map
        TTowerInfo info = new TTowerInfo();
        if(towerName != null && !"".equals(towerName)){
            //查询杆塔
            info.setName(towerName);
            return getTreeLine2(info);
        }else if(towerCode != null && !"".equals(towerCode)){
            //查询查询云台
            info.setCode(towerCode);
            return getTreeLine2(info);
        }else if(yuntaiName != null && !"".equals(yuntaiName)){
            //查询查询云台
            TYuntaiInfo yuntai = new TYuntaiInfo();
            yuntai.setName(yuntaiName);
            return getTreeLine1(yuntai);
        }else if(yuntaiCode != null && !"".equals(yuntaiCode)){
            //查询查询云台
            TYuntaiInfo yuntai = new TYuntaiInfo();
            yuntai.setCode(yuntaiCode);
            return getTreeLine1(yuntai);
        }else if(tYuntaiInfo.getFactory() != null || tYuntaiInfo.getInstallState() != null || tYuntaiInfo.getMonitorType() != null
                || tYuntaiInfo.getImageStatus() != null || tYuntaiInfo.getState() != null){
            return getTreeLine3(tYuntaiInfo);
        }
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();


        List<TLineInfo> lineList =tLineInfoService.selectTLineInfoList(new TLineInfo(userId));
        for(TLineInfo obj: lineList){
            treeMap.add(new HashMap<String, Object>(){{
                int i = 0;
                TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
                tYuntaiInfo.setLineId(obj.getId());
                List<TYuntaiInfo> list=tYuntaiInfoService.selectTYuntaiInfoList(tYuntaiInfo);
                for(TYuntaiInfo tYuntaiInfo1 : list){
                    if(tYuntaiInfo1.getState() != -1 && tYuntaiInfo1.getState() != null){
                        i++;
                    }
                }
                put("id",obj.getId());
                put("icon","lineIcon");
                put("code",obj.getCode());
                put("name",obj.getName());
                put("type","line");
                put("children",tTowerInfoService.selectTTowerInfoListByLineId(obj.getId(), towerName, new TYuntaiInfo()));
                put("total",list.size());
                put("online",i);
            }});
        }
//        System.out.println(JSONObject.toJSONString(treeMap));
        AjaxResult ajax = AjaxResult.success();
        ajax.put("tree",treeMap);
        List<Long> list = lineList.stream().map(TLineInfo::getId).collect(Collectors.toList());//获取list单列数据

        ajax.put("total",tYuntaiInfoService.selectTYuntaiTotal(new TYuntaiInfo()));
//        return AjaxResult.success();
        return ajax;
    }

    /**
     * 根据传入父部门ID获取子部门HashMap
     */
    private List<HashMap<String, Object>> getDeptListChild(Long deptId,String towerName,TYuntaiInfo yuntai){
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();


        List<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();//树状Map
        //根据部门Id获取子部门列表
        List<SysDept> deptList=deptService.selectDeptListChild(deptId);
        for(SysDept obj:deptList){

            map.add(new HashMap<String, Object>(){
                {
                    int count = 0;
                    int onlineCount = 0;
                    int i = 0;
                    int j = 0;
                    put("id", obj.getDeptId());
                    put("icon", "deptIcon");
                    put("code", obj.getOrderNum());
                    put("name", obj.getDeptName());
                    put("type","dept");
                    put("parentId",obj.getParentId());
                    List<HashMap<String, Object>> tmp = getDeptListChild(obj.getDeptId(),towerName,yuntai);

                    //获取当前角色，所属部门deptId
                    List<Integer> deptIds=deptService.selectDeptListByUserId(userId);
                    //如果部门树状结构已经都最底层，则添加线路树状接口
                    if((tmp.size()==0&& deptIds==null) || (tmp.size()==0&&deptIds!=null&&deptIds.contains(obj.getDeptId().intValue()))){
                        TLineInfo line=new TLineInfo();
                        line.setDeptId(obj.getDeptId());
                        List<TLineInfo> lineList = tLineInfoService.selectTLineInfoList(line);
                        for(TLineInfo obj: lineList){
                            TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
                            tYuntaiInfo.setPowerOrd(0);
                            tYuntaiInfo.setLineIds(String.valueOf(obj.getId()));
                            TYuntaiStatistic static1= tYuntaiInfoService.selectTYuntaiTotal(tYuntaiInfo);
                            tmp.add(new HashMap<String, Object>(){{
                                put("id",obj.getId());
                                put("icon","lineIcon");
                                put("code",obj.getCode());
                                put("name",obj.getName());
                                put("type","line");
                                put("children",tTowerInfoService.selectTTowerInfoListByLineId(obj.getId(), towerName, yuntai));
                                put("total",static1.getTotal());
                                put("online",static1.getOnline());
                            }});
                            i += static1.getTotal().intValue();
                            j += static1.getOnline().intValue();
                        }
                    }else {
                        List<SysDept> deptList=deptService.selectDeptListChild(obj.getDeptId());
                        for(SysDept objDept : deptList){
                            TLineInfo line=new TLineInfo();
                            line.setDeptId(objDept.getDeptId());
                            List<TLineInfo> lineList = tLineInfoService.selectTLineInfoList(line);
                            for(TLineInfo obj: lineList){
                                TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
                                tYuntaiInfo.setPowerOrd(0);
                                tYuntaiInfo.setLineIds(String.valueOf(obj.getId()));
                                TYuntaiStatistic static1= tYuntaiInfoService.selectTYuntaiTotal(tYuntaiInfo);
                                i += static1.getTotal().intValue();
                                j += static1.getOnline().intValue();
                            }
                        }
                    }
                    count = i;
                    onlineCount = j;
                    put("children",tmp);
                    put("total",count);
                    put("online",onlineCount);
                }});
        }
        return map;
    }


    /**
     * 查询组织机构及线路下的杆塔和云台树json
     */
    @ApiOperation("获取组织机构线路树")
    //@PreAuthorize("@ss.hasPermi('service:lineInfo:getTreeDept')")
    @GetMapping("/getTreeDept")
    public AjaxResult getTreeDept(String towerName,String towerCode,String yuntaiName,String yuntaiCode,TYuntaiInfo yuntai)
    {
        List<HashMap<String, Object>> treeMap = new ArrayList<HashMap<String, Object>>();//树状Map
        //增加根目录
        if(towerName != null || towerCode != null || yuntaiCode != null || yuntaiName != null){
            AjaxResult ajax = AjaxResult.success();
            ajax.put("tree",deptDemo(null,towerName,towerCode,yuntaiName,yuntaiCode,yuntai));
            ajax.put("total",tYuntaiInfoService.selectTYuntaiTotal(new TYuntaiInfo()));
            return ajax;
        }
        SysDept dept =deptService.selectDeptRoot();
        //查询所有云台数量
        List<TYuntaiInfo> tYuntaiInfoServices = tYuntaiInfoService.selectTYuntaiInfoList(new TYuntaiInfo());
        //查询在线设备
        TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
        tYuntaiInfo.setPowerOrd(0);
        TYuntaiStatistic static1= tYuntaiInfoService.selectTYuntaiTotal(tYuntaiInfo);
        treeMap.add(new HashMap<String, Object>(){
                        {
                            put("id", dept.getDeptId());
                            put("icon", "deptIcon");
                            put("code", dept.getOrderNum());
                            put("name", dept.getDeptName());
                            put("type","dept");
                            put("parentId",dept.getParentId());
                            put("children",getDeptListChild(dept.getDeptId(),towerName,yuntai));
                            put("total",static1.getTotal());
                            put("online",static1.getOnline());
                          }});

//        return AjaxResult.success(treeMap);
        //查询云台统计数据
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest()); //获取当前用户
        Long userId = loginUser.getUser().getUserId();
        List<TLineInfo> lineList =tLineInfoService.selectTLineInfoList(new TLineInfo(userId));
        List<Long> list = lineList.stream().map(TLineInfo::getId).collect(Collectors.toList());//获取list单列数据
        AjaxResult ajax = AjaxResult.success();
        ajax.put("tree",treeMap);
        ajax.put("total",tYuntaiInfoService.selectTYuntaiTotal(new TYuntaiInfo()));
        return ajax;

    }



    /**
     * 导出t_line_info（线路信息）列表
     */
    @ApiOperation("导出线路列表")
    //@PreAuthorize("@ss.hasPermi('service:lineInfo:export')")
    @Log(title = "t_line_info（线路信息）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TLineInfo tLineInfo)
    {
        List<TLineInfo> list = tLineInfoService.selectTLineInfoList(tLineInfo);
        ExcelUtil<TLineInfo> util = new ExcelUtil<TLineInfo>(TLineInfo.class);
        return util.exportExcel(list, "t_line_info（线路信息）数据");
    }

    /**
     * 获取t_line_info（线路信息）详细信息
     */
    @ApiOperation("获取线路详细")
    //@PreAuthorize("@ss.hasPermi('service:lineInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tLineInfoService.selectTLineInfoById(id));
    }

    /**
     * 新增t_line_info（线路信息）
     */
    @ApiOperation("新增线路信息")
    //@PreAuthorize("@ss.hasPermi('service:lineInfo:add')")
    @Log(title = "t_line_info（线路信息）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TLineInfo tLineInfo)
    {
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        tLineInfo.setCreateUser(loginUser.getUser().getUserName());
        tLineInfo.setModifyUser(loginUser.getUser().getUserName());
        tLineInfo.setModifyTime(new Date());
        return toAjax(tLineInfoService.insertTLineInfo(tLineInfo));
    }

    /**
     * 修改t_line_info（线路信息）
     */
    @ApiOperation("修改线路信息")
    //@PreAuthorize("@ss.hasPermi('service:lineInfo:edit')")
    @Log(title = "t_line_info（线路信息）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TLineInfo tLineInfo)
    {
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        tLineInfo.setModifyUser(loginUser.getUser().getUserName());
        tLineInfo.setModifyTime(new Date());
        return toAjax(tLineInfoService.updateTLineInfo(tLineInfo));
    }

    /**
     * 删除t_line_info（线路信息）
     */
    @ApiOperation("删除线路信息")
    //@PreAuthorize("@ss.hasPermi('service:lineInfo:remove')")
    @Log(title = "t_line_info（线路信息）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        for (int i = 0; i < ids.length ; i++) {
            Long id = ids[i];
            TLineInfo tLineInfo = tLineInfoService.selectTLineInfoById(id);
            TTowerInfo towerInfo = new TTowerInfo();
            towerInfo.setLineId(id);
            List<TTowerInfo> towerInfoList = tTowerInfoService.selectTTowerInfoList(towerInfo);
            if(towerInfoList.size() > 0){
                return AjaxResult.error("存在下级部门,不允许删除");
            }
        }

        return toAjax(tLineInfoService.deleteTLineInfoByIds(ids));
    }

    /**
     * 查询线路及线路下的杆塔和云台树json
     */
    @ApiOperation("获取线路树")
    //@PreAuthorize("@ss.hasPermi('service:lineInfo:getTreeLine')")
    @GetMapping("/getTreeLine1")
    public AjaxResult getTreeLine1(TYuntaiInfo yuntai)
    {

        List<HashMap<String, Object>> treeMap = new ArrayList<HashMap<String, Object>>();//树状Map
        List<TYuntaiInfo> yuntaiList=tYuntaiInfoService.selectTYuntaiInfoList(yuntai);

        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();

        Set<Long> tLineInfos = new HashSet<>();
        Set<Long> towerInfos = new HashSet<>();

        List<TLineInfo> lineList =tLineInfoService.selectTLineInfoList(new TLineInfo(userId));

        for (int i = 0; i < lineList.size(); i++) {
            TLineInfo tLineInfo = lineList.get(i);
            for (int j = 0;j<yuntaiList.size();j++){
                if(tLineInfo.getId().equals(yuntaiList.get(j).getLineId())){
                    tLineInfos.add(tLineInfo.getId());
                    List<TTowerInfo> towerInfoList = tTowerInfoService.selectTTowerInfoListByLineId(tLineInfo.getId());
                    for(int a = 0; a < towerInfoList.size();a++){
                        if(towerInfoList.get(a).getId().equals(yuntaiList.get(j).getTowerId())){
                            towerInfos.add(towerInfoList.get(a).getId());
                        }
                    }
                }
            }
        }

        System.out.println();
        System.out.println("tLineInfos"+tLineInfos);
        System.out.println();
        System.out.println("towerInfos"+towerInfos);
        System.out.println();
        System.out.println("yuntaiList"+yuntaiList);

        for(Long lineId: tLineInfos){
            TLineInfo obj = tLineInfoService.selectTLineInfoById(lineId);
            treeMap.add(new HashMap<String, Object>(){{
                int i = 0;
                TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
                tYuntaiInfo.setLineId(obj.getId());
                List<TYuntaiInfo> list=tYuntaiInfoService.selectTYuntaiInfoList(tYuntaiInfo);
                for(TYuntaiInfo tYuntaiInfo1 : list){
                    if(tYuntaiInfo1.getState() != -1 && tYuntaiInfo1.getState() != null){
                        i++;
                    }
                }
                put("id",obj.getId());
                put("icon","lineIcon");
                put("code",obj.getCode());
                put("name",obj.getName());
                put("type","line");
                put("children",tTowerInfoService.selectTTowerInfoListByLineId(obj.getId(), towerInfos, yuntaiList));
                put("total",list.size());
                put("online",i);
            }});
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("tree",treeMap);
        ajax.put("total",tYuntaiInfoService.selectTYuntaiTotal(new TYuntaiInfo()));
        return ajax;
    }

        /**
     * 查询线路及线路下的杆塔和云台树json
     */
    @ApiOperation("获取线路树")
    //@PreAuthorize("@ss.hasPermi('service:lineInfo:getTreeLine')")
    @GetMapping("/getTreeLine2")
    public AjaxResult getTreeLine2(TTowerInfo tower)
    {

        List<HashMap<String, Object>> treeMap = new ArrayList<HashMap<String, Object>>();//树状Map
        //获取当前用户
        TTowerInfo towerInfo = new TTowerInfo();
        if(tower.getDeptId() != null){
            towerInfo.setDeptId(tower.getDeptId());
        }
        if(tower.getName() != null && !"".equals(tower.getName())){
            towerInfo.setName(tower.getName());
        }else {
            towerInfo.setCode(tower.getCode());
        }
        List<TTowerInfo> list=tTowerInfoService.selectTTowerInfoList(towerInfo);
        Set<Long> tLineInfos = new HashSet<>();
        Set<Long> towerInfos = new HashSet<>();
        for (TTowerInfo info : list){
            towerInfos.add(info.getId());
            tLineInfos.add(info.getLineId());
        }

        for(Long lineId: tLineInfos){
            TLineInfo obj = tLineInfoService.selectTLineInfoById(lineId);
            treeMap.add(new HashMap<String, Object>(){{
                int i = 0;
                TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
                tYuntaiInfo.setLineId(obj.getId());
                List<TYuntaiInfo> list=tYuntaiInfoService.selectTYuntaiInfoList(tYuntaiInfo);
                for(TYuntaiInfo tYuntaiInfo1 : list){
                    if(tYuntaiInfo1.getState() != -1 && tYuntaiInfo1.getState() != null){
                        i++;
                    }
                }
                put("id",obj.getId());
                put("icon","lineIcon");
                put("code",obj.getCode());
                put("name",obj.getName());
                put("type","line");
                put("children",tTowerInfoService.selectTTowerInfoListByLineId(obj.getId(), towerInfos, null));
                put("total",list.size());
                put("online",i);
            }});
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("tree",treeMap);
        ajax.put("total",tYuntaiInfoService.selectTYuntaiTotal(new TYuntaiInfo()));
        return ajax;
    }

    /**
     * 查询线路及线路下的杆塔和云台树json
     */
    @ApiOperation("获取线路树")
    //@PreAuthorize("@ss.hasPermi('service:lineInfo:getTreeLine')")
    @GetMapping("/getTreeLine3")
    public AjaxResult getTreeLine3(TYuntaiInfo tYuntaiInfo)
    {

        List<HashMap<String, Object>> treeMap = new ArrayList<HashMap<String, Object>>();//树状Map
        List<TYuntaiInfo> yuntaiList=tYuntaiInfoService.selectTYuntaiInfoList(tYuntaiInfo);
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();

        Set<Long> tLineInfos = new HashSet<>();
        Set<Long> towerInfos = new HashSet<>();

        List<TLineInfo> lineList =tLineInfoService.selectTLineInfoList(new TLineInfo(userId));

        for (int i = 0; i < lineList.size(); i++) {
            TLineInfo tLineInfo = lineList.get(i);
            for (int j = 0;j<yuntaiList.size();j++){
                if(tLineInfo.getId().equals(yuntaiList.get(j).getLineId())){
                    tLineInfos.add(tLineInfo.getId());
                    List<TTowerInfo> towerInfoList = tTowerInfoService.selectTTowerInfoListByLineId(tLineInfo.getId());
                    for(int a = 0; a < towerInfoList.size();a++){
                        if(towerInfoList.get(a).getId().equals(yuntaiList.get(j).getTowerId())){
                            towerInfos.add(towerInfoList.get(a).getId());
                        }
                    }
                }
            }
        }
        for(Long lineId: tLineInfos){
            TLineInfo obj = tLineInfoService.selectTLineInfoById(lineId);
            treeMap.add(new HashMap<String, Object>(){{
                int i = 0;
                TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
                tYuntaiInfo.setLineId(obj.getId());
                List<TYuntaiInfo> list=tYuntaiInfoService.selectTYuntaiInfoList(tYuntaiInfo);
                for(TYuntaiInfo tYuntaiInfo1 : list){
                    if(tYuntaiInfo1.getState() != -1 && tYuntaiInfo1.getState() != null){
                        i++;
                    }
                }
                put("id",obj.getId());
                put("icon","lineIcon");
                put("code",obj.getCode());
                put("name",obj.getName());
                put("type","line");
                put("children",tTowerInfoService.selectTTowerInfoListByLineId(obj.getId(), towerInfos, yuntaiList));
                put("total",list.size());
                put("online",i);
            }});
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("tree",treeMap);
        ajax.put("total",tYuntaiInfoService.selectTYuntaiTotal(new TYuntaiInfo()));
        return ajax;
    }

    /**
     * 查询线路及线路下的杆塔和云台树json
     */
    @ApiOperation("获取线路树")
    //@PreAuthorize("@ss.hasPermi('service:lineInfo:getTreeLine')")
    @GetMapping("/getTreeDept1")
    public List<HashMap<String, Object>> deptDemo(Long deptId,String towerName,String towerCode,String yuntaiName,String yuntaiCode,TYuntaiInfo yuntai){

        Set<Long> longs = new HashSet<>();
        AjaxResult result = null;
        List<TYuntaiInfo> list = new ArrayList<>();
        List<TTowerInfo> towerInfos = new ArrayList<>();
        if(towerName != null && !"".equals(towerName)){
            //查询杆塔
            TTowerInfo towerInfo = new TTowerInfo();
            towerInfo.setName(towerName);
            towerInfos = tTowerInfoService.selectTTowerInfoList(towerInfo);
            longs = getTTowerInfoAncestors(towerInfos);
            result =  getTreeLine2(towerInfo);
        }
        if(towerCode != null && !"".equals(towerCode)){
            //查询杆塔
            TTowerInfo towerInfo = new TTowerInfo();
            towerInfo.setCode(towerCode);
            towerInfos = tTowerInfoService.selectTTowerInfoList(towerInfo);
            longs = getTTowerInfoAncestors(towerInfos);
            result = getTreeLine2(towerInfo);
        }
        if(yuntaiName != null && !"".equals(yuntaiName)){
            //查询查询云台
            TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
            tYuntaiInfo.setName(yuntaiName);
            list = tYuntaiInfoService.selectTYuntaiInfoList(tYuntaiInfo);
            longs = getTYuntaiInfoAncestors(list);
             result = getTreeLine1(tYuntaiInfo);
        }
        if(yuntaiCode != null && !"".equals(yuntaiCode)){
            //查询查询云台
            TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
            tYuntaiInfo.setCode(yuntaiCode);
            list = tYuntaiInfoService.selectTYuntaiInfoList(tYuntaiInfo);
            longs = getTYuntaiInfoAncestors(list);
            result = getTreeLine1(tYuntaiInfo);
        }
//        if(yuntai.getFactory() != null || yuntai.getInstallDate() != null || yuntai.getMonitorType() != null
//                || yuntai.getImageStatus() != null || yuntai.getState() != null){
//            //查询查询云台
//            list = tYuntaiInfoService.selectTYuntaiInfoList(yuntai);
//            longs = getTYuntaiInfoAncestors(list);
//            result = getTreeLine3(yuntai);
//        }
        List<HashMap<String, Object>> tree = (List<HashMap<String, Object>>)result.get("tree");
        SysDept dept = null;
        List<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();//树状Map
        if(deptId == null){
            dept = deptService.selectDeptRoot();
            deptId = dept.getDeptId();
        }
        List<SysDept> deptList=deptService.selectDeptListChild(deptId);
        if(dept != null && dept.getDeptId() == 100){
            SysDept deptParent = dept;
            //查询在线设备
            TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
            tYuntaiInfo.setPowerOrd(0);
            TYuntaiStatistic static1= tYuntaiInfoService.selectTYuntaiTotal(tYuntaiInfo);
            map.add(new HashMap<String, Object>() {
                {
                    put("id", deptParent.getDeptId());
                    put("icon", "deptIcon");
                    put("code", deptParent.getOrderNum());
                    put("name", deptParent.getDeptName());
                    put("type", "dept");
                    put("parentId", deptParent.getParentId());
                    put("children", deptDemo(deptParent.getDeptId(),towerName, towerCode, yuntaiName, yuntaiCode, yuntai));
                    put("total",static1.getTotal());
                    put("online",static1.getOnline());
                }
            });
        }else {
            Iterator<Long> it = longs.iterator();
            while (it.hasNext()) {
                Long aLong = it.next();
                for(int i = 0; i < deptList.size(); i++){
                        if(deptList.get(i).getDeptId().longValue() == aLong.longValue()){
                            SysDept sysDept = deptList.get(i);
                                List<TYuntaiInfo> yuntaiInfoList = list;
                                List<TTowerInfo> tTowerInfoList = towerInfos;
                                Map<String,Object> map1 = getYuntaiOnline(sysDept.getDeptId());
                                map.add(new HashMap<String, Object>() {
                                    {
                                        put("id", sysDept.getDeptId());
                                        put("icon", "deptIcon");
                                        put("code", sysDept.getOrderNum());
                                        put("name", sysDept.getDeptName());
                                        put("type", "dept");
                                        put("parentId", sysDept.getParentId());
                                        put("total",map1.get("total"));
                                        put("online",map1.get("online"));
                                        if(deptDemo(sysDept.getDeptId(),towerName, towerCode, yuntaiName, yuntaiCode, yuntai).size() > 0){
                                            put("children", deptDemo(sysDept.getDeptId(),towerName, towerCode, yuntaiName, yuntaiCode, yuntai));
                                        }
                                        else {
                                             if(yuntaiInfoList.size() > 0){
                                                for (int j = 0; j < yuntaiInfoList.size(); j++) {
                                                    if(sysDept.getDeptId().longValue() == yuntaiInfoList.get(j).getDeptId().longValue() ){
                                                        TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
                                                        if(yuntaiName != null){
                                                            tYuntaiInfo.setName(yuntaiName);
                                                            tYuntaiInfo.setDeptId(yuntaiInfoList.get(j).getDeptId());
                                                        }
                                                        if(yuntaiCode != null){
                                                            tYuntaiInfo.setCode(yuntaiCode);
                                                            tYuntaiInfo.setDeptId(yuntaiInfoList.get(j).getDeptId());
                                                        }
                                                        AjaxResult result = getTreeLine1(tYuntaiInfo);
                                                        List<HashMap<String, Object>> tree = (List<HashMap<String, Object>>)result.get("tree");
                                                        put("children",tree);
                                                        put("total",map1.get("total"));
                                                        put("online",map1.get("online"));
                                                    }
                                                }
                                            }
                                            if(tTowerInfoList.size() > 0) {
                                                for (int j = 0; j < tTowerInfoList.size(); j++) {
                                                    if (sysDept.getDeptId().longValue() == tTowerInfoList.get(j).getDeptId().longValue()) {
                                                        TTowerInfo info = new TTowerInfo();
                                                        if(towerCode != null){
                                                            info.setCode(towerCode);
                                                            info.setDeptId(tTowerInfoList.get(j).getDeptId());
                                                        }
                                                        if(towerName != null){
                                                            info.setName(towerName);
                                                            info.setDeptId(tTowerInfoList.get(j).getDeptId());
                                                        }
                                                        AjaxResult result = getTreeLine2(info);
                                                        List<HashMap<String, Object>> tree = (List<HashMap<String, Object>>) result.get("tree");
                                                        put("children", tree);
                                                        put("total", map1.get("total"));
                                                        put("online", map1.get("online"));
                                                    }
                                                }
                                            }
                                            put("total", map1.get("total"));
                                            put("online", map1.get("online"));
                                        }
                                    }
                                });
                            }

                        }
            }

        }
        return map;
    }

    public Set<Long> getTTowerInfoAncestors(List<TTowerInfo> towerInfoList){
        Set<Long> longs = new HashSet<>();
        longs.add(Long.parseLong("100"));
        for (TTowerInfo towerInfo1 : towerInfoList){
            String ancestors = deptService.selectDeptById(towerInfo1.getDeptId()).getAncestors();
            String[] array = ancestors.split(",");
            for (int i = 0; i < array.length ; i++) {
                if(array[i].equals("0") || array[i].equals("100")){
                    continue;
                }
                longs.add(Long.parseLong(array[i]));
            }
            longs.add(towerInfo1.getDeptId());
        }
        return longs;
    }

    public Set<Long> getTYuntaiInfoAncestors(List<TYuntaiInfo> yuntaiInfoList){
        Set<Long> longs = new HashSet<>();
        longs.add(Long.parseLong("100"));
        for (TYuntaiInfo tYuntaiInfo : yuntaiInfoList){
            String ancestors = deptService.selectDeptById(tYuntaiInfo.getDeptId()).getAncestors();
            String[] array = ancestors.split(",");
            for (int i = 0; i < array.length ; i++) {
                if(array[i].equals("0") || array[i].equals("100")){
                    continue;
                }
                longs.add(Long.parseLong(array[i]));
            }
            longs.add(tYuntaiInfo.getDeptId());
        }
        return longs;
    }

    public Map<String,Object> getYuntaiOnline(Long depiId){

        int i = 0;
        int j = 0;
        Map<String,Object> map = new HashMap<>();
        List<SysDept> deptList=deptService.selectDeptListChild(depiId);
        if(deptList.size() > 0){
            for(SysDept objDept : deptList){
                TLineInfo line=new TLineInfo();
                line.setDeptId(objDept.getDeptId());
                List<TLineInfo> lineList = tLineInfoService.selectTLineInfoList(line);
                for(TLineInfo obj: lineList){
                    TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
                    tYuntaiInfo.setPowerOrd(0);
                    tYuntaiInfo.setLineIds(String.valueOf(obj.getId()));
                    TYuntaiStatistic static1= tYuntaiInfoService.selectTYuntaiTotal(tYuntaiInfo);
                    i += static1.getTotal().intValue();
                    j += static1.getOnline().intValue();
                }
            }
        }else {
            TLineInfo line=new TLineInfo();
            line.setDeptId(depiId);
            List<TLineInfo> lineList = tLineInfoService.selectTLineInfoList(line);
            for(TLineInfo obj: lineList){
                TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
                tYuntaiInfo.setPowerOrd(0);
                tYuntaiInfo.setLineIds(String.valueOf(obj.getId()));
                TYuntaiStatistic static1= tYuntaiInfoService.selectTYuntaiTotal(tYuntaiInfo);
                i += static1.getTotal().intValue();
                j += static1.getOnline().intValue();
            }
        }
        map.put("total",i);
        map.put("online",j);
        return map;
    }

}
