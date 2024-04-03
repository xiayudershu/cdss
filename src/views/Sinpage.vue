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
	  <el-input v-model="form.id" class="input"/>
	</el-form-item>
	<el-form-item label="身份证号" required>
	  <el-input v-model="form.id1" class="input"/>
	</el-form-item>
	<el-form-item label="密码" required>
	  <el-input v-model="form.code" class="input" type="password"/>
	</el-form-item>
	<el-form-item label="确认密码" required>
	  <el-input v-model="form.code1" class="input" type="password"/>
	</el-form-item>
    <el-form-item label="性别">
      <el-select v-model="form.sex" placeholder="请选择你的性别">
        <el-option label="男" value="男" />
        <el-option label="女" value="女" />
      </el-select>
    </el-form-item>

 
    <el-form-item class="button1">
      <el-button type="primary" @click="onSubmit">注册</el-button>
	  <el-button type="primary" @click="check">返回</el-button>

    </el-form-item>
  </el-form>
  </div>
</div>
  
</template>

<script lang="ts" setup>
import { reactive ,ref} from 'vue'
import { useRouter } from "vue-router";
// do not use same name with ref
const form = reactive({
  name: '',
  sex: '',
  id:'',
  id1:'',
  code:'',
  code1:'',
})
const postform=ref({
	name: '',
	sex: '',
	personalid:'',
	docterid:'',
	password:'',

})
import { ElMessage, ElMessageBox } from 'element-plus'
import type { Action } from 'element-plus'

const open = () => {
  ElMessageBox.alert('恭喜你注册成功！（点击OK后登录）', '注册', {
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
const close = () => {
  ElMessageBox.alert('两次输入密码不一致，请重新输入', '注册', {
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
  ElMessageBox.alert('医生号已存在，请重新输入医生号！', '注册', {
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
import axios from 'axios';


const onSubmit = () => {
	if(form.code!=form.code1){
		close();
	}else{
		postform.value.name=form.name;
		postform.value.sex=form.sex;
		postform.value.docterid=form.id;
		postform.value.personalid=form.id1;
		postform.value.password=form.code;
		
		fetch('http://localhost:8080/backend/singup', {
		  method: 'POST',
		  body: JSON.stringify(postform.value),
		  headers: {
		    'Content-Type': 'application/json'
		  }
		}).then(response => {
		    return response.json(); // 将响应转换为 JSON 格式
		}).then(data => {
			if(data==1){
				open();
			}
			else{
				close1();
			}
		}).catch(error => {
		    console.error('请求出错', error);
		});
	}

	
}

const router = useRouter();
const check = () => {
	router.push("/");
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
		margin-top: 15vh;
		
	}
	.input{
		width: 200px;
	}
	.button1{
	 margin-left: 30px;
	}
</style>