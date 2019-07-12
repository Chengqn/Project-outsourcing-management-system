package cn.edu.zucc.domain.dao;


import cn.edu.zucc.domain.entity.Leavee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface LeaveRepository extends JpaRepository<Leavee,Long> {
    /**
     * 根据申请人查找数据
     * @param applicant
     * @return
     */
    @Query("from Leavee l where l.applicant like %:applicant%")
    List<Leavee> findByName(@Param("applicant") String applicant);

    /**
     * 根据请假单的状态查找
     * @param offstatus
     * @return
     */
    @Query("from Leavee l where l.offstatus=:offstatus")
    List<Leavee> findByOffstatus(String offstatus);
}
