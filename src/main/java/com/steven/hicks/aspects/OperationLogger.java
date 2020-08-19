package com.steven.hicks.aspects;

import com.steven.hicks.models.User;
import org.apache.catalina.session.StandardSessionFacade;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Aspect
public class OperationLogger {

    Logger logger = LoggerFactory.getLogger(OperationLogger.class);

    @Pointcut("@annotation(com.steven.hicks.aspects.Logged)")
    public void pointcut() {}

    @Before("pointcut()")
    public void beforeSearch(JoinPoint joinPoint) {
        StringBuilder logInfo = new StringBuilder();
        logInfo.append("class={} ");
        logInfo.append("method={} ");
        logInfo.append("arguments={} ");
        logInfo.append("date={} ");
        logInfo.append("time={} ");
        logInfo.append("user={} ");
        logInfo.append("sessionid={}");

        String user = " ";
        String session = " ";
        StandardSessionFacade sessionFacade = (StandardSessionFacade)RequestContextHolder.currentRequestAttributes().getSessionMutex();
        if (sessionFacade != null)
            session = ((HttpSession) sessionFacade).getId();

        SecurityContextImpl securityContext = (SecurityContextImpl)sessionFacade.getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            user = ((User)authentication.getPrincipal()).getUsername();
        }

        Object[] args = new Object[]{
                joinPoint.getSignature().getDeclaringType().getName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs(),
                LocalDate.now(),
                LocalTime.now(),
                user,
                session
        };

        logger.trace(logInfo.toString(), args);
    }
}
