package com.sp.llm.ailangchain4jdoctor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Appointment {

    @TableId(type = IdType.AUTO)
    private Long id;
    //客户姓名
    private String username;
    //身份证号
    private String idCard;
    //预约科室
    private String department;
    //预约日期
    private String date;
    //预约时间
    private String time;
    //预约医生
    private String doctorName;
}