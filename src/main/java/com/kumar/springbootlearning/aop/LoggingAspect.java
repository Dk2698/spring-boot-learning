package com.kumar.springbootlearning.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Before("execution(public String com.kumar.springlearning.web.controller.EmployeeController.fetchEmployee())")
    public void beforeMethod() {
        log.info("inside before Method aspect");
    }

    @Before("@within(org.springframework.stereotype.Service)")
    public void beforeMethodWithService() {
        log.info("inside before Service aspect");
    }

    @Before("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void beforeMethodWithAnnotation() {
        log.info("inside before Annotation aspect");
    }

    @Before("args(String, int)")
    public void beforeMethodWithArgs() {
        log.info("inside before Args aspect");
    }

    @Before("target(com.kumar.springlearning.web.controller.EmployeeController)")
    public void beforeMethodWithInstance() {
        log.info("inside before Args aspect");
    }

    @Before("target(com.kumar.springlearning.web.controller.IEmployeeService)")
    public void beforeMethodWithInterface() {
        log.info("inside before Args aspect");
    }

    @Before("execution(public String com.kumar.springlearning.web.controller.EmployeeController.*())"
            +
            "&& @within(org.springframework.web.bind.annotation.RestController)")
    public void beforeAndMethod() {
        log.info("inside before Args aspect");
    }

    @Before("execution(public String com.kumar.springlearning.web.controller.EmployeeController.*())"
            +
            "|| @within(org.springframework.stereotype.Component)")
    public void beforeOrMethod() {
        log.info("inside before Args aspect");
    }

    @Pointcut("execution(* com.kumar.springlearning.web.controller.EmployeeController.*())")
    public void customPointcutName() {
        log.info("inside before Args aspect");
    }

    @Before("customPointcutName()")
    public void beforeMethodWithPointCut() {
        log.info("inside before method aspect in point cut");

    }

    @After("execution(public String com.kumar.springlearning.web.controller.EmployeeController.fetchEmployee())")
    public void afterMethod() {
        log.info("inside after Method aspect");
    }

    @Around("execution(* com.kumar.springlearning.web.controller.EmployeeController.*())")
    public void aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("inside before Method aspect");
        joinPoint.proceed();
        log.info("inside after Method aspect");
    }
}
