<template>
	<div class="body">
		   <div class="title">
		   	关节炎辅助临床决策支持系统
		   </div>
			<div class="form">
		  <el-form :model="form" label-width="80px">
			<el-form-item label="姓名" required>
			  <el-input v-model="form.name" class="input"/>
			</el-form-item>
			<el-form-item label="医生号" required>
			  <el-input v-model="form.docterid" class="input"/>
			</el-form-item>
			<el-form-item label="密码" required>
			  <el-input v-model="form.password" class="input" type="password"/>
			</el-form-item>
			<el-form-item class="button1">
			  <el-button type="primary" @click="onSubmit">注册</el-button>
			  <el-button type="primary" @click="check">登录</el-button>   
			</el-form-item>
		  </el-form>
		  </div>
	</div>
  
</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import { useRouter } from "vue-router";
// do not use same name with ref
const form = reactive({
  name: '',
  docterid:'',
  password:'',
})
import { ElMessage, ElMessageBox } from 'element-plus'
import type { Action } from 'element-plus'

const inter = () => {
  ElMessageBox.alert('登录成功', '登录', {
    // if you want to disable its autofocus
    // autofocus: false,
    confirmButtonText: 'OK',
    callback: (action: Action) => {
      ElMessage({
        type: 'info',
        message: `action: ${action}`,
      })
	// location.reload();
    },
  })
}
const close = () => {
  ElMessageBox.alert('密码错误！请重新登录！', '登录', {
    // if you want to disable its autofocus
    // autofocus: false,
    confirmButtonText: 'OK',
    callback: (action: Action) => {
      ElMessage({
        type: 'info',
        message: `action: ${action}`,
      })
	location.reload();
    },
  })
}
const close1 = () => {
  ElMessageBox.alert('用户不存在！请注册！', '登录', {
    // if you want to disable its autofocus
    // autofocus: false,
    confirmButtonText: 'OK',
    callback: (action: Action) => {
      ElMessage({
        type: 'info',
        message: `action: ${action}`,
      })
	 router.push("/sin");
    },
  })
}

const close2 = () => {
  ElMessageBox.alert('用户名错误！请重新登录！', '登录', {
    // if you want to disable its autofocus
    // autofocus: false,
    confirmButtonText: 'OK',
    callback: (action: Action) => {
      ElMessage({
        type: 'info',
        message: `action: ${action}`,
      })
	 router.push("/");
    },
  })
}

const onSubmit = () => {
	router.push("/sin");
}


const router = useRouter();
import axios from 'axios';

const check = () => {
	fetch('http://localhost:8080/backend/login', {
	  method: 'POST',
	  body: JSON.stringify(form),
	  headers: {
	    'Content-Type': 'application/json'
	  }
	}).then(response => {
	    return response.json(); // 将响应转换为 JSON 格式
	}).then(data => {
		if(data==1){
			localStorage.setItem('name',form.name)
			localStorage.setItem('docterid',form.docterid)
			inter();
			router.push("/diagnose");
		}
		else if(data==0){
			close();
		}
		else if(data==-1){
			close1();
		}
		else if(data==-2){
			close2();
		}
		
	}).catch(error => {
	    console.error('请求出错', error);
	});
	
	
	
}



</script>
<style>
	.title{
		margin-top: 10vh;
		font-size: 40px;
		font-weight: bold;
		color: white;
	}
	.body{
		width: 100%;
		height: 96vh;
		background: linear-gradient(to bottom, #6EB5FF, white);
		position: fixed;
	}
	.form{
		width: 50vw;
		text-align: center;
		margin-left: 38vw;
		margin-top: 22vh;
		
	}
	.input{
		width: 200px;
	}
	.button1{
     margin-left: 30px;
	}
</style>