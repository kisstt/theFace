package com.face.controller.aspect;

import com.face.exception.BaseAppException;
import com.face.page.Page;
import com.face.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ControllerAspect {

    /**
     * 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
     * <p>
     * 注意：当核心业务抛异常后，立即退出，转向AfterAdvice 执行完毕AfterAdvice，再转到ThrowingAdvice
     * org.springframework.controller.bind.annotation
     *
     * @return
     * @parampjp
     * @throwsThrowable
     */
    @Around("within(@org.springframework.web.bind.annotation.RestController *) || within(@org.springframework.stereotype.Controller *))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("-----doAround().invoke-----");
        Object[] args = pjp.getArgs();
        doPageAndSort(args);

        // 方法返回值
        Object retVal = null;
        long begin = System.currentTimeMillis();
        long end = 0l;
        String expCode = null;
        String operResult = "T";
        String expDesc= null;
        try {
            retVal = pjp.proceed();
            end = System.currentTimeMillis();
        } catch (Exception e) {
            end = System.currentTimeMillis();
            operResult = "F";
//            expCode = e instanceof BaseAppException ? ((BaseAppException) e).getCode() : SysErrCode.UNKNOW_EXPCEPTION;
            expDesc = e instanceof BaseAppException ? ((BaseAppException) e).getDesc() : e.getMessage();
            throw e;
        } finally {
            if (isRecordLog(pjp)) {
                saveOperationLog(pjp, operResult, end - begin, expCode, expDesc);
            }
        }
        log.debug("-----End of doAround()------");
        return retVal;
    }

    /**
     *
     * @param args
     */
    private void doPageAndSort(Object[] args){
        if(Utils.isEmpty(args)){
            return;
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Page) {
                Page.threadLocal.set((Page) args[i]);
            }
        }
    }

    /**
     * 记录操作日志
     *
     * @param point
     * @param operResult
     */
    private void saveOperationLog(JoinPoint point, String operResult, Long costTime, String expCode , String expDesc) {
//        OperationLogVo operLogVo = new OperationLogVo();
//        UserSessionInfo userSessionInfo = UserSessionInfo.getUserSessionInfo();

//        Object[] params = point.getArgs();
//        String methodName = point.getSignature().getDeclaringTypeName() + "."
//                + point.getSignature().getName();
//        StringBuilder param = new StringBuilder();
//        for (Object obj : params) {
//            if (obj instanceof IArgVo) {// 只记录次类型对象
//                if (param.length() > 0) {
//                    param.append("||");
//                }
//                param.append(((IArgVo) obj).getOperateArgs());
//            }
//        }
//        operLogVo.setExpCode(expCode);
//        operLogVo.setCostTime(costTime);
//        operLogVo.setSourIp(userSessionInfo.getIPAddress());
//        operLogVo.setOperResult(operResult);
//        operLogVo.setParam(param.toString());
//        operLogVo.setSvcName(WebConstants.OPER_LOG_MAP.get(methodName));
//        operLogVo.setOperTime(DateUtils.getNowDate());
//        operLogVo.setUserId(userSessionInfo.getUserId());
//        operLogVo.setTenantId(userSessionInfo.getTenantId());
//        operLogVo.setExpDesc(expDesc);
//        if("T".equals(operResult)){
//            operLogVo.setStaffCode(userSessionInfo.getUserCode());
//            if(Utils.notEmpty(userSessionInfo.getUserInfoBean()) && userSessionInfo.getUserInfoBean() instanceof  StaffLoginVo ){
//                StaffLoginVo loginVo = (StaffLoginVo) userSessionInfo.getUserInfoBean();
//                operLogVo.setStaffName(loginVo.getStaffPo().getStaffName());
//            }
//        }
//
//        JobUtils.addExcuteJob(new OperationLogJob(operLogVo));
    }

    /**
     * 判断是否需要记录操作日志
     *
     * @param point
     * @return
     */
    private boolean isRecordLog(JoinPoint point) {
//        String methodName = point.getSignature().getDeclaringTypeName() + "."
//                + point.getSignature().getName();
//        log.debug("OPER_LOG_MAP...{}", JSONObject.toJSONString(WebConstants.OPER_LOG_MAP));
//        if (WebConstants.OPER_LOG_MAP.containsKey(methodName)) {
//            return true;
//        }

        return false;
    }
}