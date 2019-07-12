package cn.edu.zucc.domain.dao;

import cn.edu.zucc.domain.entity.Mission;
import cn.edu.zucc.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query("from Project p where p.contractor like %:contractorName%")
    List<Project> findProjectByContractor(String contractorName);

    @Query("from Mission m where m.project =:projectName")
    List<Mission> getMissionByProject(String projectName);

    @Query("from Project p where p.pname like %:pname%")
    List<Project> findProjectByNameLike(String pname);

    @Query("from Project p where p.pname like %:projectName% and p.contractor =:userName")
    List<Project> findMyProjectByNameLike(String userName, String projectName);

    @Query("from Project p where p.puber =:username")
    List<Project> findProjectByPuber(String username);

    @Query("from Project p where p.pname like %:puberName% and p.puber =:username")
    List<Project> findProjectByPuberName(String username, String puberName);

    @Query("from Project p where p.puber =:username and p.pstatus =:pstatus")
    List<Project> findProjectByPstatus(String username, String pstatus);

    @Query("from Project p where p.pname like %:puberName% and p.puber =:username and p.pstatus =:pstatus")
    List<Project> findProjectByNameAndPstatus(String username, String puberName, String pstatus);

    @Query("select puber from Project p where p.pname =:project")
    String findPuberByProject(String project);

    @Query("from Project p where p.contractor =:username and p.pstatus =:pstatus")
    List<Project> findAcceptedProject(String username, String pstatus);

    @Query("select count(*) from Mission m where m.project =:pname")
    int findNumOfDev(String pname);

    @Query("select sum(actualpersondays) from Mission m where m.project =:pname")
    int findTotalPersonDays(String pname);
}
