package cn.edu.zucc.service.impl;


import cn.edu.zucc.domain.dao.MissionRepository;
import cn.edu.zucc.domain.entity.Mission;
import cn.edu.zucc.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    MissionRepository missionRepository;

    @Override
    public void create(Mission mission) {
        missionRepository.save(mission);
    }

    @Override
    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

    @Override
    public Mission getMissionById(Long id) {
        return missionRepository.findById(id).get();
    }

    @Override
    public void deleteMission(Long id) {
        missionRepository.deleteById(id);
    }

    @Override
    public List<Mission> getMissionByName(String username) {
        return missionRepository.findMissionByName(username);
    }

    @Override
    public List<Mission> getMissionByNameLike(String ProjectName) {
        return missionRepository.findMissionByNameLike(ProjectName);
    }

    @Override
    public List<Mission> getMissionByPuber(String username) {
        return missionRepository.findMissionByPuber(username);
    }

    @Override
    public List<Mission> getMyMission(String userId) {
        return missionRepository.findMyMission(userId);
    }

    @Override
    public List<Mission> getMissionByProject(String pname) {
        return missionRepository.findMissionByProject(pname);
    }


}
