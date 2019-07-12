package cn.edu.zucc.domain.dao;

import cn.edu.zucc.domain.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission,Long> {
    @Query("from Mission m where m.contractor like %:username%")
    List<Mission> findMissionByName(String username);

    @Query("from Mission m where m.project like %:ProjectName%")
    List<Mission> findMissionByNameLike(String ProjectName);

    @Query("from Mission m where m.puber =:username")
    List<Mission> findMissionByPuber(String username);

    @Query("from Mission m where m.role =:userId")
    List<Mission> findMyMission(String userId);

    @Query("from Mission m where m.project =:pname")
    List<Mission> findMissionByProject(String pname);
}
