<template>
  <div class="common-layout">
    <el-container>
		
      <el-header width="100vw">
		  <el-menu
		      :default-active="activeIndex"
		      class="el-menu-demo"
		      mode="horizontal"
		      background-color="#6EB5FF"
		      text-color="#fff"
		      active-text-color="#245699"
		      @select="handleSelect"
		    >
			  
		      <el-menu-item index="1">关节炎辅助诊疗系统</el-menu-item>
			  
		      <el-menu-item index="2">辅助诊断</el-menu-item>
			  
			  <el-menu-item index="3">辅助治疗</el-menu-item>
			  
			  <el-popover
			      placement="bottom-start"
			      title="修改"
			      :width="200"
			      trigger="hover"
			      content="修改各决定因素的权重值"
			    >
			      <template #reference>
			         <div class="patient" @click="editstands()">
			         	当前患者：{{selectedPatient}} &nbsp;&nbsp;&nbsp;当前医生：{{doctername}}
			         </div>
			      </template>
			    </el-popover>
			  
			  
			  
		    </el-menu>
		 
		  
	  </el-header>
	  
      <el-container>
		  
        <el-aside width="200px" :style="{ display: isCollapse ? '50px' : '200px' }">
			  <el-menu
			    :default-active="currentTab"
			    class="el-menu-vertical-demo"
			    :collapse="isCollapse"
			    @open="handleOpen"
			    @close="handleClose"
			  >
			    <el-menu-item index="1" @click="currentTab = 1">
			      <el-icon><Document /></el-icon>
			      <template #title>患者信息</template>
			    </el-menu-item>
			  
			    <el-menu-item index="2" @click="changefrom1(currentTab = 2)">
			      <el-icon><DocumentAdd /></el-icon>
			      <template #title>临床表现</template>
			    </el-menu-item>
			  
			    <el-sub-menu index="3">
			      
			      <template #title>
					  <el-icon><TakeawayBox /></el-icon>
					  <span>检查结果</span>
					</template>

				  
				      <el-menu-item index="3-1"  @click="changefrom1(currentTab = 3)"><el-icon><DataAnalysis /></el-icon>x线检查</el-menu-item>
				      <el-menu-item index="3-2"  @click="changefrom1(currentTab = 32)"><el-icon><Monitor /></el-icon>MRT</el-menu-item>
				  	  <el-menu-item index="3-3"  @click="changefrom1(currentTab = 33)"><el-icon><DataBoard /></el-icon>CT</el-menu-item>
					  <el-menu-item index="3-4"  @click="changefrom1(currentTab = 34)"><el-icon><MessageBox /></el-icon>实验室检查</el-menu-item>
			    </el-sub-menu>
			  
			    <el-menu-item index="4" @click="putalltobackend(currentTab = 4)">
			      <el-icon><DocumentChecked /></el-icon>
			      <template #title>诊断结果</template>
			    </el-menu-item>
			  
			  <el-sub-menu index="5">
			   
			    <template #title>
					 <el-icon><Clock /></el-icon>
					<span>诊疗历史</span>
				</template>

				    <el-menu-item index="5-1"  @click="changefrom1(currentTab = 52)"><el-icon><FirstAidKit /></el-icon>患者病历</el-menu-item>
				    <el-menu-item index="5-2"  @click="currentTab = 51"><el-icon><Service /></el-icon>医生历史</el-menu-item>
			

			  </el-sub-menu>
			  
			




			  
			    <el-radio-group v-model="isCollapse" class="radio">
			      <el-radio-button v-if="isCollapse === true" :label="false"><el-icon><DArrowRight /></el-icon></el-radio-button>
			      <el-radio-button v-if="isCollapse === false" :label="true"><el-icon><DArrowLeft /></el-icon></el-radio-button>
			    </el-radio-group>
			  </el-menu>
            
		</el-aside>
		
        <el-main :style="{ marginLeft: isCollapse ? '-150px' : '0px' }">
          <info v-if="currentTab === 1" @selectPatient="handleSelectPatient"></info>
		  <cinical v-if="currentTab === 2" @selectDes="handleSelectDes"></cinical>
          <check v-if="currentTab === 3" @selectChec="handleSelectChec" :recentPatient="recentPatient"></check>
          <result v-if="currentTab === 4" :responseData="responseData"></result>
		  <docterhistory v-if="currentTab === 51"></docterhistory>
		  <patienthistory v-if="currentTab === 52" :recentPatient="recentPatient"></patienthistory>
        </el-main>
		
      </el-container>
	  
    </el-container>
	
	
	<el-drawer v-model="draweredit" title="I am the title" :with-header="false" size="25%">
		 <div class="drawereditstands">
<!--            <el-select
                  v-model="value"
                  placeholder="选择病症"
                   style="width: 320px"
                >
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select> -->
				
				
				
				<el-menu
				    :default-active="activeIndex"
				    class="el-menu-demo"
				    mode="horizontal"
				    @select="handleSelectStands"
				  >
				    <el-menu-item index="1">骨关节炎</el-menu-item>
					<el-menu-item index="2">类风湿关节炎</el-menu-item>

				  </el-menu>
	
				
             <div style="margin: 40px 0" />
             
           
            	输入性别权重：<el-input-number v-model="standsedit.sex" :min="0" :max="100" />
				<div style="margin: 40px 0" />
				输入年龄权重：<el-input-number v-model="standsedit.age" :min="0" :max="100" />
				<div style="margin: 40px 0" />
				输入临床症状权重：<el-input-number v-model="standsedit.clinical" :min="0" :max="100" />
				<div style="margin: 40px 0" />
				输入检查结果权重：<el-input-number v-model="standsedit.check" :min="0" :max="100" />
				<div style="margin: 40px 0" />
				当前总和：{{standsedit.age+standsedit.check+standsedit.sex+standsedit.clinical}}
				<div style="margin: 20px 0" />
				目标总和：100
				
            </div> 
			
			<el-button type="primary" class="buttonedit" @click="edit()">确定修改</el-button>
			
		
	</el-drawer>
	
	
	
	
	
	
	
	
	
  </div>
</template>
<script lang="ts" setup>
import info from "../components/diagnose/DiagnoseInfopage.vue"
import check from "../components/diagnose/DiagnoseCheckpage.vue"
import cinical from "../components/diagnose/DiagnoseClinicalpage.vue"
import result from "../components/diagnose/Diagnosepage.vue"
import docterhistory from "../components/history/DocterHistorypage.vue"
import patienthistory from "../components/history/PatientHistorypage.vue"
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { Action } from 'element-plus'
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
const doctername=localStorage.getItem('name')
const docterid=localStorage.getItem('docterid')
const currentTab = ref(1)
const isCollapse = ref(false)
const selectedPatient=ref('')
const responseData = ref(null);
const recentPatient=ref();
const draweredit=ref(false)

const value = ref('')

const options = [
  {
    value: 'oa',
    label: '骨关节炎',
  },
  {
    value: 'ra',
    label: '类风湿关节炎',
  }
]

const standsedit=ref({
	age:null,
	sex:null,
	check:null,
	clinical:null,
	arthritis:null,
	docterid:docterid,

	
})

const stands=ref([
{
	age:null,
	sex:null,
	check:null,
	clinical:null,
	arthritis:null,	
}]
)




const handleSelectStands = (key: string) => {
  if(key=="1"){
	value.value="oa"
	standsedit.value.age=stands.value[0].age
	standsedit.value.sex=stands.value[0].sex
	standsedit.value.check=stands.value[0].check
	standsedit.value.clinical=stands.value[0].clinical
	  
  }
  else if(key=="2"){
  	value.value="ra"
	standsedit.value.age=stands.value[1].age
	standsedit.value.sex=stands.value[1].sex
	standsedit.value.check=stands.value[1].check
	standsedit.value.clinical=stands.value[1].clinical
  }

}




const editstands=()=>{
	draweredit.value=true;
	
	fetch(`http://localhost:8080/backend/stands/${docterid}`, {
	    method: 'GET',
	    headers: {
	        'Content-Type': 'application/json'
	    }
	}).then(response => {
	    return response.json(); // 将响应转换为 JSON 格式
	}).then(data => {
	    
		
		stands.value[0]=data[0];
		stands.value[1]=data[1];
	    console.log(stands.value[0])
		console.log(stands.value[1])
	    
	}).catch(error => {
	    console.error('请求出错', error);
	});
	

	
}
const edit=()=>{
	let all=standsedit.value.age+standsedit.value.check+standsedit.value.sex+standsedit.value.clinical;
	if(all!=100){
		ElMessage({
		   message: '修改失败！数字相加要为100！',
		   type: 'warning',
		 })
		
	}
	else{	
		standsedit.value.arthritis=value.value
		console.log(standsedit.value)
		fetch('http://localhost:8080/backend/stands/edit', {
		  method: 'POST',
		  body: JSON.stringify(standsedit.value),
		  headers: {
		    'Content-Type': 'application/json'
		  }
		}).then(response => {
		    return response.json(); // 将响应转换为 JSON 格式
		}).then(data => {
			draweredit.value=false
		    if(data==1){
				 ElMessage({
				    message: '修改成功！',
				    type: 'success',
				  })
			}
			else{
				 ElMessage({
				    message: '修改失败！',
				    type: 'warning',
				  })
			}
		}).catch(error => {
		    console.error('请求出错', error);
		});
		
		
		
	}
	
}



const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const activeIndex = ref('2')
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}




// 定义 selectInfo
const selectInfo = ref({
  info:{
	  name:"",
	  patientid:"",
	  personalid:"",
	  docterid:docterid,
  },
  alldescription: [],
  keypoint:[],
});

// 修改 handleSelectPatient
const handleSelectPatient = (patient) => {
  recentPatient.value= patient.patientid;
  selectedPatient.value = patient.name;
  selectInfo.value.info.patientid = patient.patientid;
  selectInfo.value.info.name = patient.name;
  selectInfo.value.info.personalid = patient.personalid;
  selectInfo.value.alldescription[0]=patient.sex;
  selectInfo.value.alldescription[1]=patient.age;
  selectInfo.value.alldescription[2]=patient.history;
  //console.log(selectInfo.value.patient);
  if (selectedPatient.value !== null) {
    currentTab.value = 2;
  }
};

const handleSelectDes = (selectlistdes) => {
  //console.log(selectlistdes);
  selectInfo.value.alldescription = [...selectInfo.value.alldescription, ...selectlistdes];
  currentTab.value = 3;
 
};

// 修改 handleSelectChec
const handleSelectChec = (selectlistchec) => {
   selectInfo.value.keypoint = [...selectInfo.value.keypoint, ...selectlistchec];
  //currentTab.value = 4;
  //console.log(selectlistchec);
};

// 修改 putalltobackend
const putalltobackend = () => {
  changefrom1();
  console.log(selectInfo.value)
  // 将 selectInfo 的值传递到后端
  sendSelectInfoToBackend(selectInfo.value);
}

// 新增函数，用于将 selectInfo 的值传递到后端
const sendSelectInfoToBackend = (selectInfo) => {
  let message = ``;
  console.log(selectInfo.alldescription.length)
  if(selectInfo.alldescription.length<=3){
	  message+='还没有选择症状！请选择症状！'
  }

  if(message.length>=1){
	  backto(message);
  }
  fetch('http://localhost:8080/backend/saveSelectInfo', {
    method: 'POST',
    body: JSON.stringify(selectInfo),
    headers: {
      'Content-Type': 'application/json'
    }
  }).then(response => {
      return response.json(); // 将响应转换为 JSON 格式
  }).then(data => {
      //console.log(data); // 打印返回的数据，查看其中是否包含 OARate 的值
      responseData.value = data;
  }).catch(error => {
      console.error('请求出错', error);
  });
};

const backto= (message) => {
  ElMessageBox.alert(message, '选择不全！', {
    confirmButtonText: '确认',
    callback: (action) => {
      ElMessage({
        type: 'info',
        message: `action: ${action}`,
      });
      if (action === 'confirm') {
        currentTab.value = 2;
      }
    },
  });
};

const open = () => {
  ElMessageBox.alert(`当前没有选择患者，请选择患者`, '选择患者', {
    confirmButtonText: '确认',
    callback: (action) => {
      ElMessage({
        type: 'info',
        message: `action: ${action}`,
      });
      if (action === 'confirm') {
        currentTab.value = 1;
      }
    },
  });
};

const changefrom1=()=>{
	console.log(selectedPatient.value)
	if(selectedPatient.value==''){
		open();
	}
}

</script>

<style>
	a{
	  text-decoration: none;
	}
	.router-link-active {
	  text-decoration: none;
	}

.buttonedit{
	margin-top: 50px;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
   width: 200px;
   min-height: 200px;
}
.radio{
	
	bottom: 30vh;
	left: 4vw;
}
.patient{
	margin-top: 2vh;
	margin-left:45vw;
	color: white;
	font-size: 20px;
}
.drawereditstands{
	text-align: left;
}
</style>