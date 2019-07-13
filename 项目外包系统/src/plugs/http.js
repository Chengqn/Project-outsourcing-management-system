//自定义插件模块
import axios from 'axios'
const MyHttpServer = {}


MyHttpServer.install = (Vue)=> {
    
    axios.defaults.baseURL = "http://localhost:81"
    
    //发起请求之前拦截
    axios.interceptors.request.use(function (config) {
      // 在发起请求请做一些业务处理
      console.log("拦截器")
      if(config.url !=='login'){
        const AUTH_TOKEN = localStorage.getItem("token")
        //config.headers["Authorization"] = AUTH_TOKEN
        config.headers.token = AUTH_TOKEN
      }
      
      return config;
    }, function (error) {
      // 对请求失败做处理
      return Promise.reject(error);
    });
    // 4. 添加实例方法
    Vue.prototype.$http= axios
    
  }

  export default MyHttpServer