<template>
	<div class="clinialbody">
	<div class="cards">
	<el-row :gutter="10">
		
	    <el-col :span="4" v-for="(item, index) in checkboxes" :key="index">
			
		  <el-popover
		    placement="right-end"
		    :title="item.title"
		    :width="300"
		    trigger="hover"
		    :content="item.description"
		  >
		
		<template #reference>
	      <el-card shadow="hover" class="card" style="position: relative;">
	          <el-checkbox v-model="item.checked"  @change="select(index) "/> 
	          {{ item.title }}
	          <div class="clinialscore">
	              分数：{{item.score}}
	              <br />
	          </div> 
	          <div class="editclinial" style="position: absolute; left: 10px; bottom: 20px;">
	              <el-button type="primary" plain @click="editclinial(index)">修改</el-button>
	          </div>
	      </el-card>
		</template>
		  </el-popover>
	    </el-col>
		
	    <el-col :span="4">
	      <el-card shadow="hover" class="card" @click="drawer = true">
			  <br />
	         <el-icon class="avatar-uploader-icon" ><Plus /></el-icon>
	      </el-card>
	    </el-col>
	      
	</el-row>

		
	</div>
    <el-checkbox v-model="value1" label="全选" size="large" border class="affix1" @click="allornull()"/>
	  <el-popover
	    placement="top-start"
	    title="确认"
	    :width="200"
	    trigger="hover"
	    content="点击确认该患者的临床表现"
	  >
	    <template #reference>
	       <el-button type="primary" class="affix2" @click="selectdes()">确认</el-button>
	    </template>
	  </el-popover>
  
	 <el-popover
	    placement="top-start"
	    title="删除表现"
	    :width="200"
	    trigger="hover"
	    content="慎用此项!将删除选择的临床表现!"
	  >
	    <template #reference>
	      <el-button type="danger" class="affix3" @click="delet()" v-if="drawer === false">删除表现</el-button>
	    </template>
	  </el-popover>
	
	<!-- <el-button type="success" :icon="Check" circle class="affix2"></el-button> -->
    <!-- <el-button type="primary" :icon="Edit" circle class="affix2" @click="drawer = true" v-if="drawer === false"/> -->

    <el-drawer v-model="drawer" title="I am the title" :with-header="false">
		<el-select
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
		    </el-select>
		 <div style="margin: 20px 0" />
		
       <el-input
          v-model="textarea1"
          autosize
          type="textarea"
          placeholder="输入表现"
        />
		 <div style="margin: 20px 0" />
		输入分数：<el-input-number v-model="textarea3" :min="1" :max="10" />
        <div style="margin: 20px 0" />
        <el-input
          v-model="textarea2"
          :autosize="{ minRows: 8, maxRows: 50 }"
          type="textarea"
          placeholder="具体描述"
        />
		<el-button type="primary" class="button2" @click="add()">确定添加</el-button>
    </el-drawer>
	
	
	
	
	<el-drawer v-model="draweredit" title="I am the title" :with-header="false">
		<el-input
		   v-model="selecteditclinial.title"
		   autosize
		   type="textarea"
		 />
		<div style="margin: 20px 0" />
		修改分数：<el-input-number v-model="selecteditclinial.score" :min="1" :max="10" />
		 <div style="margin: 20px 0" />
		 <el-input
		   v-model="selecteditclinial.description"
		   :autosize="{ minRows: 8, maxRows: 50 }"
		   type="textarea"
		 />
	    <el-button type="primary" class="button2" @click="edit()">确定修改</el-button>
	</el-drawer>
	
	
	
	
   
   </div>
</template>

<script lang="ts" setup>
import { ElMessage, ElMessageBox } from 'element-plus'
import type { Action } from 'element-plus'
import { ref, onMounted } from 'vue'
import { defineEmits } from 'vue';
import {
  Edit,
  Check,
} from '@element-plus/icons-vue'
const value1 = ref(false)
const checked1 = ref(false)
const checked2 = ref(false)
const checked3 = ref(false)
const checked4 = ref(false)
const checked5 = ref(false)
const drawer = ref(false)
const draweredit=ref(false)
const textarea1 = ref('')
const textarea2 = ref('')
const textarea3 = ref()
const checkboxes = ref([])
const emit = defineEmits(['selectDes'])
const docterid=localStorage.getItem('docterid')
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


const selecteditclinial=ref({
	title:null,
	label:null,
	description:null,
	score:null,
	arthritis:null,
	docterid:docterid
})
const editclinial=(index)=>{
	console.log(checkboxes.value[index])
    draweredit.value=true
	selecteditclinial.value.title=checkboxes.value[index].title;
	selecteditclinial.value.description=checkboxes.value[index].description;
	selecteditclinial.value.label=checkboxes.value[index].label;
	selecteditclinial.value.score=checkboxes.value[index].score;
	selecteditclinial.value.arthritis=checkboxes.value[index].arthritis;
}

const edit=()=>{
	console.log(selecteditclinial.value)
	fetch('http://localhost:8080/backend/clinial/edit', {
	  method: 'POST',
	  body: JSON.stringify(selecteditclinial.value),
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
			  fetchData()
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

const fetchData = () => {
		
  fetch(`http://localhost:8080/backend/clinical/${docterid}`, {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
  }).then(response => {
      return response.json(); // 将响应转换为 JSON 格式
  }).then(data => {
      checkboxes.value = data 
  	console.log(checkboxes.value);

      
  }).catch(error => {
      console.error('请求出错', error);
  });
}

onMounted(() => {
  fetchData()
})

const select = (index) => {
  console.log(checkboxes.value[index].checked)
}


const delet = () => {
  const deletlist = checkboxes.value.filter(item => item.checked);
  for(let i=0;i<deletlist.length;i++){
	  const deletClinial=ref({
	  	docterid:docterid,
	  	label:null,
		arthritis:null
	  })
	  deletClinial.value.label=deletlist[i].label;
	  deletClinial.value.arthritis=deletlist[i].arthritis
	  
	 console.log(deletClinial.value) 
	 fetch('http://localhost:8080/backend/clinial/delet', {
	   method: 'POST',
	   body: JSON.stringify(deletClinial.value),
	   headers: {
	     'Content-Type': 'application/json'
	   }
	 }).then(response => {
	     return response.json(); // 将响应转换为 JSON 格式
	 }).then(data => {
	     if(data==1){
	 		
	 		 ElMessage({
	 		    message: '删除成功！',
	 		    type: 'success',
	 		  })
	 		  
	 		  fetchData()
	 	}
	 	else{
	 		 ElMessage({
	 		    message: '删除失败！',
	 		    type: 'warning',
	 		  })
	 	}
	 }).catch(error => {
	     console.error('请求出错', error);
	 });
	 
	 
  }
}

const addclinial=ref({
	title:null,
	description:null,
	score:null,
	arthritis:null,
	docterid:docterid
})
 
const add = () => {
  const title = textarea1.value;
  const description = textarea2.value;
  const score=textarea3.value
  const arthritis=value.value
  addclinial.value.arthritis=arthritis;
  addclinial.value.description=description;
  addclinial.value.docterid=docterid;
  addclinial.value.score=score;
  addclinial.value.title=title;
  console.log(addclinial.value)
  
  drawer.value = false
  fetch('http://localhost:8080/backend/clinial/insert', {
    method: 'POST',
    body: JSON.stringify(addclinial.value),
    headers: {
      'Content-Type': 'application/json'
    }
  }).then(response => {
      return response.json(); // 将响应转换为 JSON 格式
  }).then(data => {
      if(data==1){
  		
  		 ElMessage({
  		    message: '添加成功！',
  		    type: 'success',
  		  })
		  
  		  fetchData()
  	}
  	else{
  		 ElMessage({
  		    message: '添加失败！',
  		    type: 'warning',
  		  })
  	}
  }).catch(error => {
      console.error('请求出错', error);
  });
  
}

const open2 = (selectlist) => {
  let message = ``;
  let num=1;
  selectlist.forEach((item) => {
    message += `${num}.${item.title}<br>`;
	num=num+1;
  });
  
  ElMessageBox.alert(message, '是否确定临床表现：', {
    confirmButtonText: '确认',
    dangerouslyUseHTMLString: true,
    callback: (action) => {
      ElMessage({
        type: 'info',
        message: `action: ${action}`,
      });
      if (action === 'confirm') {
		emit('selectDes', selectlist.map(item => item.title))
      }
    },
  });
};


const selectdes = () => {
  const selectlist = checkboxes.value.filter(item => item.checked);
  open2(selectlist);
  //emit('selectDes', selectlist);
  return selectlist;
};

const allornull = () => {
  checkboxes.value.forEach((item) => {
    item.checked = !value1.value;
  });
};
</script>

<style>
	
	.editclinial{
		width: 20px;
		height: 20px;
	}
	
    .tooltip-content {
      white-space: pre-wrap; /* 允许换行 */
      max-width: 200px; /* 根据需求设置最大宽度 */
    }
	.el-icon.avatar-uploader-icon {
	  font-size: 45px;
	  color: #8c939d;
	  text-align: center;
	}
	.checkbox-item {
	  margin-bottom: 10px;
	}
	.clinialbody{
		//width: 81vw;
		height: 84vh;
	}
  .card {
    text-align: center;
    height: 25vh;
	font-size: 25px;
	font-weight: bold;
    margin: 8px;
  }
  .clinialscore{
	  text-align: right;
	  font-size: 20px;
	  font-weight: 100;
	  
  }
  .cards{
	  //background-color: #6EB5FF;
	  background: linear-gradient(to bottom, #6EB5FF, white);
	  min-height: 84vh;
	  
  }
  .affix1{
  	   position: fixed;
  	   left: 300px;
  	   bottom: 100px;
  	   z-index: 99;
  	   width: 100px; /* 设置按钮的宽度 */
  	   height: 60px; /* 设置按钮的高度 */
  	   font-size: 24px; /* 设置按钮的字体大小 */
  }
  .affix2{
	   position: fixed;
	   left: 450px;
	   bottom: 100px;
	   z-index: 99;
	   width: 90px; /* 设置按钮的宽度 */
	   height: 40px; /* 设置按钮的高度 */ 
	   font-size: 20px; /* 设置按钮的字体大小 */
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
  .button2{
	  margin-top: 300px;
  }
</style>