<template>
    
</template>

<script>
export default {
    created(){
        this.testRole()
    },
    methods:{
    //获取权限信息
    async testRole() {
    const res = await this.$http.get( `users/getRoles`);
      console.log(res);
      const { code, data, msg } = res.data;
      if (data === 'admin') {
         this.$router.push({path:'/'})
      }else if(data === 'puber'){
           this.$router.push({path:'/pubhome'})
      }else{
          this.$router.push({path:'/userhome'})
      } 
      
    }
    }
    
    
}
</script>


<style>

</style>

