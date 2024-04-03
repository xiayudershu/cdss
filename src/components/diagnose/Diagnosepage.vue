<template>
	<div class="main" v-loading="loading">
	<el-menu
	    :default-active="activeIndex"
	    class="el-menu-demo"
	    mode="horizontal"
	    @select="handleSelect"
	  >
	    <el-menu-item index="1">骨关节炎</el-menu-item>
		<el-menu-item index="2">类风湿关节炎</el-menu-item>
	    <el-menu-item index="3">感染关节炎</el-menu-item>
	    <el-menu-item index="4">痛风</el-menu-item>
		
	  </el-menu>
	  <div class="mainbody">
	  	<div class="possible">
		   <div class="maintitle">
		   	&nbsp;&nbsp;诊断可能指数：
		   </div>
	  	   <el-progress :text-inside="true" :stroke-width="26" :percentage="rate"  :status="status"/>
	  	</div>
		<br /><br />
		<div class="reasons">
			<div class="maintitle">
			  	&nbsp;&nbsp;诊断原因：
			</div>
			<br /><br /><br />
			<el-row :gutter="10">
			    <el-col :span="4" v-for="(item, index) in reasons" :key="index">
				  <el-popover
				    placement="bottom-start"
				    :title="item.title"
				    :width="300"
				    trigger="hover"
				    :content="item.description"
				  >
				<template #reference>
			      <el-card shadow="hover" class="card" >
					 {{ item.title}}
				  </el-card>
				</template>
				  </el-popover>
			    </el-col>
 
			</el-row>
		</div>
	  </div>
	  <el-popover
	    placement="top-start"
	    title="诊断"
	    :width="200"
	    trigger="hover"
	    content="跳转到医院诊断系统"
	  >
	    <template #reference>
	       <el-button type="primary" class="affix3" @click="open">诊断</el-button>
	    </template>
	  </el-popover>
	  
	  <!-- 在按钮上方添加一个遮罩层 -->
	  <div class="overlay" v-if="showOverlay"></div>
	  <el-button type="primary" @click="getDiagnosisResult" v-show="showButton" class="overb">获取诊断结果</el-button>
</div> 
</template>
<script lang="ts" setup>
import {onMounted,  ref ,watch} from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { Action } from 'element-plus'
import {defineProps } from 'vue';
import { useRouter } from "vue-router";
const props = defineProps(['responseData']);
const loading = ref(false)
const activeIndex = ref('1')
const reasons = ref()
const rate=ref()
const status=ref("succuess")
const showOverlay=ref(true)
const showButton=ref(true)

const handleSelect = (key: string) => {
  if(key=="1"){
	  reasons.value=props.responseData[0].evidences
	  rate.value=props.responseData[0].rate.toFixed(2);
	  
  }
  else if(key=="2"){
  	  reasons.value=props.responseData[1].evidences
	  rate.value=props.responseData[1].rate.toFixed(2)
  }
  else if(key=="3"){
  	//   reasons.value=reason3
	  // rate.value=rate3
  }
  else if(key=="4"){
  	//   reasons.value=reason4
	  // rate.value=rate4
  }
  else if(key=="5"){
  	//   reasons.value=reason5
	  // rate.value=rate5
  }
  if(rate.value<=30){
	  status.value="success";  
  }
  else if(rate.value>30&&rate.value<=60){
	  status.value="warning";
  }
  if(rate.value>60){
	  status.value="exception";
  }
}

const getDiagnosisResult = () => {
   loading.value = true;
   if (props.responseData && props.responseData.length > 0) {
     loading.value = false; // Hide loading when responseData is available
	 showOverlay.value=false;
	 showButton.value=false;
     handleSelect('1'); // Automatically load data for key == 1
   }
}
const router = useRouter();
const open = () => {
  ElMessageBox.alert('确认是否跳动到医院诊断系统', '诊断', {
    confirmButtonText: '确认',
    callback: (action: Action) => {
      ElMessage({
        type: 'info',
        message: `action: ${action}`,
      })
	  location.reload();
    },
  })
   
}


// const rate1="80"
// const rate2="60"
// const rate3="75"
// const rate4="20"
// const rate5="40"
// const reason1=[
// 	{text:"年龄：45岁",description:"OA高危人群：年龄在40岁及以上、女性、肥胖或超重、有创伤史（推荐强度：强推荐，证据等级：B）"},
// 	{text:"性别：女",description:"OA高危人群：年龄在40岁及以上、女性、肥胖或超重、有创伤史（推荐强度：强推荐，证据等级：B）"},
// 	{text:"关节疼痛和关节活动受限",description:"关节疼痛和关节活动受限是 OA 最常见的临床症状（推荐强度：强推荐，证据等级：C）"},
// 	{text:"压痛和关节畸形",description:"压痛和关节畸形是手部 OA 和膝关节OA 最常见的体征，骨摩擦音（感）和肌肉萎缩常见于膝关节OA（推荐强度：强推荐，证据等级：C）"}
// ]
// const reason2=[
// 	{text:"关节疼痛和关节活动受限",description:"关节疼痛和关节活动受限是 OA 最常见的临床症状（推荐强度：强推荐，证据等级：C）"},
// 	{text:"压痛和关节畸形",description:"压痛和关节畸形是手部 OA 和膝关节OA 最常见的体征，骨摩擦音（感）和肌肉萎缩常见于膝关节OA（推荐强度：强推荐，证据等级：C）"}
// ]
// const reason3=[
// 	{text:"年龄：45岁",description:"OA高危人群：年龄在40岁及以上、女性、肥胖或超重、有创伤史（推荐强度：强推荐，证据等级：B）"},
// 	{text:"性别：女",description:"OA高危人群：年龄在40岁及以上、女性、肥胖或超重、有创伤史（推荐强度：强推荐，证据等级：B）"},
// 	{text:"关节疼痛和关节活动受限",description:"关节疼痛和关节活动受限是 OA 最常见的临床症状（推荐强度：强推荐，证据等级：C）"},
// 	{text:"压痛和关节畸形",description:"压痛和关节畸形是手部 OA 和膝关节OA 最常见的体征，骨摩擦音（感）和肌肉萎缩常见于膝关节OA（推荐强度：强推荐，证据等级：C）"}
// ]
// const reason4=[
// 	{text:"压痛和关节畸形",description:"压痛和关节畸形是手部 OA 和膝关节OA 最常见的体征，骨摩擦音（感）和肌肉萎缩常见于膝关节OA（推荐强度：强推荐，证据等级：C）"}
// ]
// const reason5=[
// 	{text:"关节疼痛和关节活动受限",description:"关节疼痛和关节活动受限是 OA 最常见的临床症状（推荐强度：强推荐，证据等级：C）"},
// 	{text:"压痛和关节畸形",description:"压痛和关节畸形是手部 OA 和膝关节OA 最常见的体征，骨摩擦音（感）和肌肉萎缩常见于膝关节OA（推荐强度：强推荐，证据等级：C）"}
// ]

</script>
<style>
.main{
	background: linear-gradient(to bottom, #6EB5FF, white);
	min-height: 80vh;
}
.maintitle{
	font-size: 20px;
	font-weight: bold;
	float: left;
}
.mainbody{
	margin-top: 30px;
}
.possible{
	width: 30vw;
}
.affix3{
  	   position: fixed;
  	   right: 150px;
  	   bottom: 100px;
  	   z-index: 99;
  	   width: 90px; /* 设置按钮的宽度 */
  	   height: 40px; /* 设置按钮的高度 */ 
  	   font-size: 20px; /* 设置按钮的字体大小 */
  }
  /* 添加样式控制遮罩层和按钮位置 */
  .overlay {
    position: fixed;
    top: 9vh;
    left: 14vw;
    width: 84vw;
    height: 90vh;
    background-color: rgba(0, 0, 0, 0.5); /* 半透明黑色 */
    z-index: 9999; /* 确保遮罩层在最上层 */
  }
  
  .overb {
    position: relative;
    z-index: 10000; /* 设置按钮在最上层 */
	width: 150px;
	height: 70px;
	font-size: 20px;
  }
</style>
