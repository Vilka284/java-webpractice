package com.springapp.andrii.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class SecurityLoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.springapp.andrii.security.jwt.TokenProvider.validateToken(String))")
    public void callAtTokenValidation() {

    }

    @Pointcut("execution(public * com.springapp.andrii.security.jwt.JWTFilter.setAuthentication(String, String))")
    public void callAfterSetAuthentication() {

    }

    @Pointcut("execution(public * com.springapp.andrii.security.SecurityUtils.getCurrentUsername())")
    public void callAfterGetCurrentUsername() {

    }

    @AfterThrowing(pointcut = "callAtTokenValidation()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }

    @AfterReturning(pointcut = "callAfterSetAuthentication()", returning = "setAuth")
    public void logAfterSetAuthentication(JoinPoint joinPoint, boolean setAuth) {
        Object[] signatureArgs = joinPoint.getArgs();
        if (setAuth) {
            log.debug("set Authentication to security context:");
            for (Object arg : signatureArgs) {
                log.debug(arg.toString());
            }
        } else {
            log.debug("no valid JWT token found, uri: {}", signatureArgs[1].toString());
        }
    }

    @AfterReturning(pointcut = "callAfterGetCurrentUsername()", returning = "username")
    public void logAfterGetCurrentUsername(JoinPoint joinPoint, Optional<String> username) {
        log.debug("found username '{}' in security context", username);
    }
}
