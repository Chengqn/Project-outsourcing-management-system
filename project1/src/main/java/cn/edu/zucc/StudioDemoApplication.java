package cn.edu.zucc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;


@SpringBootApplication
@EnableAsync
//@EnableScheduling
@Controller
//@EnableCaching
public class StudioDemoApplication {
    @RequestMapping("/")
    public String greeting(ModelMap map) {
        String jreVersion = System.getProperty("java.specification.version");
        map.addAttribute("jreVersion", "v" + jreVersion);
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(StudioDemoApplication.class, args);
    }
}
