package cn.edu.zucc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
@EnableAsync
//@EnableScheduling
@Controller
//@EnableCaching
public class StudioDemoApplication extends WebMvcConfigurerAdapter {
    //login的路由
    @RequestMapping("/login")
    public String login(ModelMap map) {
        String jreVersion = System.getProperty("java.specification.version");
        map.addAttribute("jreVersion", "v" + jreVersion);
        return "login";
    }
    @RequestMapping("/")  
    public String greeting(ModelMap map) {  
        String jreVersion = System.getProperty("java.specification.version");
        map.addAttribute("jreVersion", "v" + jreVersion);
        return "index";
    }

    @RequestMapping("/regis")
    public String regisUser(ModelMap map) {
        String jreVersion = System.getProperty("java.specification.version");
        map.addAttribute("jreVersion", "v" + jreVersion);
        return "register_1";
    }
    //允许跨域的设置
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

	public static void main(String[] args) {
		SpringApplication.run(StudioDemoApplication.class, args);
	}
}
