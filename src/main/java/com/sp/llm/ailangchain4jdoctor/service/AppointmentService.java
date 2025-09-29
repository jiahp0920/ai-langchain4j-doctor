package com.sp.llm.ailangchain4jdoctor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.llm.ailangchain4jdoctor.entity.Appointment;

/**
 * @author jiahongping
 * @version 1.0
 * @className AppointmentService
 * @description TODO
 * @date 2025/9/27 15:04
 */
public interface AppointmentService extends IService<Appointment> {
    Appointment getOne(Appointment appointment);
}
