package com.senxu.omserver.framework.aspectj;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.enums.BusinessStatus;
import com.senxu.omserver.common.enums.HttpMethod;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.common.utils.ip.IpUtils;
import com.senxu.omserver.common.utils.spring.SpringUtils;
import com.senxu.omserver.framework.manager.factory.AsyncFactory;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.TLog;
import com.senxu.omserver.service.service.ITLogService;
import com.senxu.omserver.system.domain.SysOperLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import com.alibaba.fastjson.JSON;
import com.senxu.omserver.common.annotation.Log;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.common.utils.StringUtils;
import com.senxu.omserver.framework.manager.AsyncManager;

/**
 * 操作日志记录处理
 * 
 * @author ruoyi
 */
@Aspect
@Component
public class LogAspect
{

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ITLogService logService;

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    // 配置织入点
    @Pointcut("@annotation(com.senxu.omserver.common.annotation.Log)")
    public void logPointCut()
    {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult)
    {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     * 
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e, null);
    }


    /**
     * 增加对新增、修改操作记录的拦截设置相应的新增用户及修改用户及时间记录  pyc 2021-7-23
     *
     * @param joinPoint
     */
    @Around(value = "logPointCut()")
    public Object doReturning(ProceedingJoinPoint joinPoint){
        Object proceed = null;
        try {
            System.out.println("目标方法执行前...");

            Signature  sgnt=joinPoint.getSignature();
            Object[] args = joinPoint.getArgs();

            //获取当前用户
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

            //监听edit方法更新修改时间和修改人的操作
            if(sgnt.getName().equals("edit")) {
                for (int i = 0; i < args.length; i++) {
                    try {
                        Class aClass = args[i].getClass();
                        Method setModifyTime=aClass.getDeclaredMethod("setModifyTime",Date.class);//获取对象set方法
                        setModifyTime.invoke(args[i],DateUtils.getNowDate());//调用对象set方法并赋值

                        Method setModifyUser=aClass.getDeclaredMethod("setModifyUser",String.class);//获取对象set方法
                        setModifyUser.invoke(args[i],loginUser.getUsername());//调用对象set方法并赋值
                    }catch(Exception e){
                        System.out.println("没有找到相应的setmodifyTime或setmodifyUser方法.........................");
                    }

                }
            }


            //监听add方法更新创建记录操作人和创建时间
            if(sgnt.getName().equals("add")) {
                for (int i = 0; i < args.length; i++) {
                    try {
                        Class aClass = args[i].getClass();
                        Method setCreateTime=aClass.getDeclaredMethod("setCreateTime",Date.class);//获取对象set方法
                        setCreateTime.invoke(args[i],DateUtils.getNowDate());//调用对象set方法并赋值

                        Method setCreateUser=aClass.getDeclaredMethod("setCreateUser",String.class);//获取对象set方法
                        setCreateUser.invoke(args[i],loginUser.getUsername());//调用对象set方法并赋值
                    }catch(Exception e){
                        System.out.println("没有找到相应的setCreateTime或setCreateUser方法.........................");
                    }

                }
            }
            proceed = joinPoint.proceed(args);//从新将参数出入方法并执行
            saveLog(joinPoint,null);

        } catch (Throwable e) {
            //异常通知
            System.out.println("执行目标方法异常后...");
            saveLog(joinPoint, (Exception) e);
            if(e instanceof java.lang.reflect.UndeclaredThrowableException){
               throw new RuntimeException (((UndeclaredThrowableException) e).getUndeclaredThrowable().getMessage());
            }
            else {
                throw new RuntimeException(e);
            }

        }
        //后置通知
        System.out.println("目标方法执行后...");
        return proceed;
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult)
    {
        try
        {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null)
            {
                return;
            }

            // 获取当前的用户
            LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());

            // *========数据库日志=========*//
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            // 返回参数
            operLog.setJsonResult(JSON.toJSONString(jsonResult));

            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (loginUser != null)
            {
                operLog.setOperName(loginUser.getUsername());
            }

            if (e != null)
            {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog);
            // 保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        }
        catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 保存日志到t_log表
     * @param joinPoint
     * @param e
     */
    protected void saveLog(final JoinPoint joinPoint, final Exception e) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            // 获取当前的用户
            LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
            // *========保存数据库日志=========*//
            TLog log=new TLog();
            log.setRelname(loginUser.getUsername());
            log.setUsername(loginUser.getUser().getUserId().toString());
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());// 请求的地址
            log.setUserIp(ip);
            log.setOperaTime(DateUtils.getNowDate());


            //获取执行方法名
            Signature  sgnt=joinPoint.getSignature();


            //监听add方法更新创建记录操作人和创建时间
            if(sgnt.getName().equals("add")) {
                log.setOperaStlye("新增");//日志类型
            }else if(sgnt.getName().equals("edit")) {
                log.setOperaStlye("编辑");//日志类型
            }else if(sgnt.getName().equals("remove")) {
                log.setOperaStlye("删除");//日志类型
            }else if(sgnt.getName().equals("remove")) {
                log.setOperaStlye("删除");//日志类型
            }else if(sgnt.getName().equals("export")) {
                log.setOperaStlye("导出");//日志类型
            }else if(sgnt.getName().equals("list")) {
                log.setOperaStlye("查询");//日志类型
            }

            Object[] args = joinPoint.getArgs();
            String sOperaRecord="";
            for(int i = 0; i < args.length; i++){
//                Class aClass = args[i].getClass();
//                Method toString=aClass.getDeclaredMethod("toString");//获取toString方法
                sOperaRecord+=controllerLog.title()+"："+log.getOperaStlye()+"参数"+(i+1)+"="+args[i]+"，";
            }
            log.setOperaRecord(sOperaRecord);
            log.setOperaModel(controllerLog.title());

            if (e != null)
            {
                log.setOperaResult("操作失败");

            }else{
                log.setOperaResult("操作成功");
            }

            logService.insertTLog(log);//保存操作日志

        } catch (Exception exception) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * 
     * @param log 日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog) throws Exception
    {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData())
        {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     * 
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) throws Exception
    {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod))
        {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        }
        else
        {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray)
    {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0)
        {
            for (int i = 0; i < paramsArray.length; i++)
            {
                if (!isFilterObject(paramsArray[i]))
                {
                    Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     * 
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o)
    {
        Class<?> clazz = o.getClass();
        if (clazz.isArray())
        {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        else if (Collection.class.isAssignableFrom(clazz))
        {
            Collection collection = (Collection) o;
            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                return iter.next() instanceof MultipartFile;
            }
        }
        else if (Map.class.isAssignableFrom(clazz))
        {
            Map map = (Map) o;
            for (Iterator iter = map.entrySet().iterator(); iter.hasNext();)
            {
                Map.Entry entry = (Map.Entry) iter.next();
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }


}
