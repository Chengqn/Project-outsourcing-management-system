package cn.edu.zucc.service;


import cn.edu.zucc.domain.entity.Leavee;

import java.util.List;

public interface LeaveService {
    /**
     * 保存请假单即发起请假
     * @param leave
     */
    void create(Leavee leave);

    /**
     * 获取当前用户的所有请假单列表
     * @param applicant
     * @return
     */
    List<Leavee> getLeaves(String applicant);

    /**
     * 通过id获取请假单
     * @param id
     * @return
     */
    Leavee getLeaves(Long id);

    /**
     * 获取所有用户的所有的请假单列表
     * @return
     */
    List<Leavee> getAllLeavs();

    /**
     * 获取所有用户的发起请假列表
     */
    List<Leavee> getAllLeaves(String offstatus);
}
