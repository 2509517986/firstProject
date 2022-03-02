package com.senxu.omserver.common.constant;

/**
 * 多翼通用常量信息
 *
 * @author ruoyi
 */
public class DyUrlConstants {

    /**
     * 多翼测试环境请求头部ip和端口
     */
    //public static final String header = "http://159.75.216.222:9009";
    //final去掉才能给值
    public static   String header = "http://159.75.216.222:9009";
    /**
     * 多翼登陆用户名
        由于同一时间只能一个用户登陆
        可在配置文件yml中配omserver.DYUserName 来配成其它用户
     */
    public static final String userName= "dyadmin";

    /**
     * 多翼请求头部ip和端口
     */
    public static final String password = "jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=";

    /**
     *  图片路径
     */
    public static final String filepath = "C:\\capPic\\dyImg\\";

    /**
     *  图片路径2
     */
    public static final String filepath2 = "/capPic/dyImg/";


    /**
     * 用户登录v1
     */
    public static final String loginV1 = "/api/v1/login";

    /**
     * 用户登录v2
     */
    public static final String loginV2 = "/api/v2/login";

    /**
     * 1.获取所有用户信息(GET)
     * 2.新增用户(POST)
     */
    public static final String user = "/api/v1/user";

    /**
     * 刷新 token
     */
    public static final String refreshToken = "/api/v1/refresh/token";

    /**
     * 根据图片ID下载图片
     * /api/v1/images/{imageId}
     */
    public static final String images = "/api/v1/images/";

    /**
     * 1.根据任务ID和航线ID下载所有图片,根据任务ID和航线ID查询所有图片ID
     * /api/v1/images/assignment/{assignmentId}/{routeId}(GET)
     * 2.根据任务ID下载所有图片,根据任务ID查询所有图片ID
     * /api/v1/images/assignment/{assignmentId}(GET)
     */
    public static final String  imagesAssignment= "/api/v1/images/assignment/";

    /**
     * 根据videoId获取录制视频
     * /api/v1/video/downloadVideo/{videoId}(GET)
     */
    public static final String videoDownload = "/api/v1/video/downloadVideo/";

    /**
     * 获取机场录制视频记录
     * /api/v1/video/videoList(POST)
     */
    public static final String videoList = "/api/v1/video/videoList";

    /**
     * 打开某一无人机视频流、打开固定机场某一监控摄像头的视频流
     */
    public static final String liveviewStart = "/api/v1/liveview/start";

    /**
     * 关闭某一无人机视频流、关闭固定机场某一监控摄像头的视频流
     */
    public static final String liveviewStop = "/api/v1/liveview/stop";

    /**
     * 获取机场摄像头的推流状态
     */
    public static final String liveviewCamstatus = "/api/v1/liveview/camstatus";

    /**
     * /api/v1/uni/drones/{droneId}/refreshiframe(GET)
     * 无人机刷新 I 帧
     */
    public static final String uniDrones = "/api/v1/uni/drones/";



    /**
     * 喊话播放
     */
    public static final String remoteShout = "/api/v1/remoteShout";

    /**
     * 结束喊话播放
     */
    public static final String stopShout = "/api/v1/stopShout";

    /**
     * 重置机场状态状态为idle
     */
    public static final String stationaryIdle = "/api/v1/stationary/idle/";

    /**
     * 下达胶囊机场降落命令
     */
    public static final String uniAutomaticLand = "/api/v1/uni/automaticLand/";

    /**
     * 下达胶囊机场起飞命令
     */
    public static final String uniAutomatictakeoff = "/api/v1/uni/automatictakeoff/";

    /**
     * 获取胶囊机场状态最新快照、获取固定机场状态最新快照 /api/v1/airport/{airportId}/snapshot
     */
    public static final String airportV1 = "/api/v1/airport/";

    /**
     * 获取固定机场状态最新快照V2 /api/v2/airport/{airportId}/snapshot
     */
    public static final String airportV2 = "/api/v2/airport/";

    /**
     * 关闭某一固定机场执行任务的开关
     */
    public static final String stationaryStop = "/api/v1/stationary/stop/";

    /**
     * 复位某一固定机场
     */
    public static final String stationaryReset = "/api/v1/stationary/reset/";

    /**
     * 对某一固定机场解除软急停
     */
    public static final String stationaryRecover = "/api/v1/stationary/recover/";

    /**
     * 取消固定机场的当前飞行及回收无人机
     */
    public static final String stationaryCancel = "/api/v1/stationary/cancel/";

    /**
     * 软急停某一固定机场
     */
    public static final String stationaryEstop = "/api/v1/stationary/estop/";

    /**
     * 打开某一固定机场执行任务的开关
     */
    public static final String stationaryStart = "/api/v1/stationary/start/";

    /**
     * 获取所有在线的无人机
     */
    public static final String dronesOnLine = "/api/v1/drones/onLine";

    /**
     * 获取所有固定机场
     */
    public static final String stationarys = "/api/v1/stationarys";

    /**
     * 获取所有胶囊机场
     */
    public static final String pickups = "/api/v1/pickups";

    /**
     * 根据固定机场ID查询所属无人机、根据胶囊机场ID查询所属无人机
     */
    public static final String drones = "/api/v1/drones";

    /**
     * 新建精确巡检航线信息、新建通道巡检航线信息
     */
    public static final String createRoute = "/api/v1/createRoute";

    /**
     * 根据设备创建固定机场任务 (需要提前在多翼机场控制系统内存入航线和设备的对应关系)、根据航线创建固定机场任务、创建胶囊机场任务
     */
    public static final String createAssignment = "/api/v1/createAssignment";

    /**
     * 根据任务ID获取任务结果
     */
    public static final String flightsResults = "/api/v1/flights/results";

    /**
     * 根据计划获取计划结果
     */
    public static final String flightsResultsByPlan = "/api/v1/flights/resultsByPlan";

    /**
     * 筛选获取任务
     */
    public static final String assignmentRetrieve = "/api/v1/assignment/retrieve";

    /**
     * 筛选查询航线信息
     */
    public static final String routeRetrieve = "/api/v1/route/retrieve";

    /**
     * 根据航线ID获取通道航线信息、根据航线ID获取精确航线信息、修改精确巡检航线信息、修改通道巡检航线信息
     */
    public static final String route = "/api/v1/route/";

    /**
     * 获取任务详情、删除任务、获取所有任务ID
     */
    public static final String assignment = "/api/v1/assignment/";

    /**
     * 新建巡检航线组
     */
    public static final String createGroup = "/api/v1/createGroup";

    /**
     * 查询所有巡检航线组
     */
    public static final String group = "/api/v1/group";

    /**
     * 查询历史完成的巡检路线
     */
    public static final String targetsInspectRecord = "/api/v1/targets/inspectRecord";

    /**
     * 查询每天计划、任务、航线完成数量
     */
    public static final String doneNumPerDay = "/api/v1/uni/statisticInfo/doneNumPerDay";

    /**
     * 查询无人机当前执行的飞行任务信息
     */
    public static final String flightsCurrent = "/api/v1/flights/current/";

    /**
     * 取消任务
     */
    public static final String cancelAssignment = "/api/v1/cancel/assignment/";
    
}
