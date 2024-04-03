import {createRouter,createWebHashHistory} from "vue-router"
const router = createRouter({
  //内部提供了history模式的实现。为了简单起见，我们在这里使用hash模式。
  history: createWebHashHistory(),
  routes:[
	 
	  {name:"DiagnoseHomepage",path:"/diagnose",component:()=>import("../views/DiagnoseHomepage.vue")},
	  {name:"Sinpage",path:"/sin",component:()=>import("../views/Sinpage.vue")},
	  {name:"Loginpage",path:"/",component:()=>import("../views/Loginpage.vue")},
	   {name:"test",path:"/test",component:()=>import("../views/test.vue")},
    

	
  ]
})
export default router

