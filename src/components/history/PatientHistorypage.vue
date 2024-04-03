<template>
  <el-table :data="filterTableData" style="width: 100%">
	<el-table-column label="诊疗号" prop="clinialid" sortable/>
    <el-table-column label="诊疗时间" prop="date" sortable/>
    <el-table-column label="诊断医生" prop="name" sortable/>
	<el-table-column label="骨关节炎(%)" prop="oarate" sortable/>
	<el-table-column label="类风湿关节炎(%)" prop="rarate" sortable/>
    <el-table-column align="right">
      <template #header>
        <el-input v-model="search" size="small" placeholder="搜索历史" />
      </template>
      <template #default="scope">
        <el-button size="small" @click="getdetail(scope.$index, scope.row)"
          >查看详情</el-button
        >
      </template>
    </el-table-column>
  </el-table>
  
  
    <el-drawer v-model="drawer" :with-header="false" size="90%">
		<div class="drawerback">
			
		
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
      	<el-menu-item index="5">滑膜炎</el-menu-item>
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
          
       </div>
    </el-drawer>
  
  
</template>

<script lang="ts" setup>
import { Ref,onMounted } from 'vue'
import { computed, ref } from 'vue'
const props = defineProps(['recentPatient']);
const drawer = ref(false)
const activeIndex = ref('1')
const search = ref('')
const docterid=localStorage.getItem('docterid')
const reasons = ref();
const responseData = ref(null);
const rate=ref()
const status=ref("succuess")
const filterTableData = computed(() =>
  tableData.value.filter(
    (data) =>
      !search.value ||
      data.name.toLowerCase().includes(search.value.toLowerCase())
  )
)


const getdetail = (index: number, row) => {
  console.log(index, row)
  console.log(row.clinialid)
  const clinialid=ref(row.clinialid)
  drawer.value=true
  fetch(`http://localhost:8080/backend/results/details/${clinialid.value}`, {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
  }).then(response => {
      return response.json(); // 将响应转换为 JSON 格式
  }).then(data => {
	  console.log(activeIndex.value)
      responseData.value=data
      if(activeIndex.value=="1"){
      	  reasons.value=responseData.value[0].evidences
      	  rate.value=responseData.value[0].rate.toFixed(2);
      	  
      }
      else if(activeIndex.value=="2"){
      	  reasons.value=responseData.value[1].evidences
      	  rate.value=responseData.value[1].rate.toFixed(2)
      }
	  
  }).catch(error => {
      console.error('请求出错', error);
  });

}
const handleSelect = (key: string) => {
  if(key=="1"){
	  reasons.value=responseData.value[0].evidences
	  rate.value=responseData.value[0].rate.toFixed(2);
	  
  }
  else if(key=="2"){
  	  reasons.value=responseData.value[1].evidences
	  rate.value=responseData.value[1].rate.toFixed(2)
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

const tableData = ref([]);
const fetchData = () => {
  console.log(props.recentPatient)
  fetch(`http://localhost:8080/backend/patient/results/${props.recentPatient}`, {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
  }).then(response => {
      return response.json(); // 将响应转换为 JSON 格式
  }).then(data => {
      for(let i=0;i<data.length;i++){
      		  data[i].oarate=data[i].oarate.toFixed(2)
      		  data[i].rarate=data[i].rarate.toFixed(2)
      }
      tableData.value = data;
      console.log(tableData.value)
  }).catch(error => {
      console.error('请求出错', error);
  });
};

onMounted(() => {
  fetchData()
})


// const tableData=ref([
//   {
// 	clinialid:'001',
//     date: '2024-03-17',
//     name: '李四',
//     oarate:22.3,
//   	rarate:18.6,
//   },
//   {
// 	clinialid:'004',
//     date: '2024-03-18',
//     name: '张三',
//     oarate:64.8,
// 	rarate:53.3,
//   },

// ])





</script>

<style>
	
	
.drawerback{
	background: linear-gradient(to bottom, #6EB5FF, white);
	
	
}
</style>