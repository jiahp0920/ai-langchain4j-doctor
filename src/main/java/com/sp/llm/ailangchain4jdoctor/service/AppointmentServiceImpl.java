package com.sp.llm.ailangchain4jdoctor.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.llm.ailangchain4jdoctor.entity.Appointment;
import com.sp.llm.ailangchain4jdoctor.mapper.AppointmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author jiahongping
 * @version 1.0
 * @className AppointmentServiceImpl
 * @description TODO
 * @date 2025/9/27 15:04
 */
@Service
@Slf4j
public class AppointmentServiceImpl  extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {
    @Override
    public Appointment getOne(Appointment appointment) {
        LambdaQueryWrapper<Appointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Appointment::getUsername, appointment.getUsername());
        queryWrapper.eq(Appointment::getIdCard, appointment.getIdCard());
        queryWrapper.eq(Appointment::getDepartment, appointment.getDepartment());
        queryWrapper.eq(Appointment::getDate, appointment.getDate());
        queryWrapper.eq(Appointment::getTime, appointment.getTime());
        Appointment appointmentDB = baseMapper.selectOne(queryWrapper);
        log.info("查询预约结果：{}", appointmentDB);
        return appointmentDB;
    }
}
