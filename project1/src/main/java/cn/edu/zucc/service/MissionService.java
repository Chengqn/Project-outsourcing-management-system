package cn.edu.zucc.service;


import cn.edu.zucc.domain.entity.Mission;

import java.util.List;

public interface MissionService {

    //创建开发任务
    void create(Mission mission);

    //获取所有开发任务
    List<Mission> getAllMissions();
    //通过id获取某个开发任务
    Mission getMissionById(Long id);
    //通过id删除开发任务
    void deleteMission(Long id);

    List<Mission> getMissionByName(String username);

    List<Mission> getMissionByNameLike(String ProjectName);

    List<Mission> getMissionByPuber(String username);

    List<Mission> getMyMission(String userId);

    List<Mission> getMissionByProject(String pname);
}
