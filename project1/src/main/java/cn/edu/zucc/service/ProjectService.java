package cn.edu.zucc.service;

import cn.edu.zucc.common.R;
import cn.edu.zucc.domain.entity.Mission;
import cn.edu.zucc.domain.entity.Project;

import java.util.List;

public interface ProjectService {
    //创建项目
    void create(Project project);

    //获取所有的项目信息
    List<Project> getAllProjects();

    //通过id获取单个项目信息
    Project getProjectById(Long id);

    void deleteProject(Long id);

    List<Project> getProjectByContractorName(String contractorName);

    List<Mission> getMissionByProjectName(String projectName);

    List<Project> getProjectByNameLike(String pname);

    List<Project> getMyProjectByNameLike(String userName,String projectName);

    List<Project> getProjectByPuber(String username);

    List<Project> getProjectByPuberName(String username, String puberName);

    List<Project> getProjectByPstatus(String username, String pstatus);

    List<Project> getProjectByPuberNameAndPstatus(String username, String puberName, String pstatus);

    String selectPuberByProject(String project);

    List<Project> getAcceptProject(String username, String pstatus);

    int  getNumOfDev(String pname);

    int getTotalPersonDays(String pname);
}
