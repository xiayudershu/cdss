<template>
	<div class="main">
	  <div class="common-layout" v-loading="loading">
	    <el-container>
			
			
	      <el-main>
			  <el-switch
			        v-model="handvalue"
			  					   size="large"
			        style="--el-switch-on-color: #13ce66; --el-switch-off-color: #6EB5FF"
			  					   active-text="右手"
			  					   inactive-text="左手"
			        active-value=true
			        inactive-value=fasle
			  					   class="handvalue"
			  					   @click="handdata"
			      />
			  
			  
			  
			  <el-select
			       v-model="valuebig"
			       placeholder="选择症状"
			       size="large"
			       style="width: 240px"
				   class="attrivalue"
			     >
			       <el-option
			         v-for="item in optionsbig"
			         :key="item.value"
			         :label="item.label"
			         :value="item.value"
					 @click="attridata"
			       />
			     </el-select> 
			  
			  
			  
			  
			  <br /><br /><br />
			  
			<el-row :gutter="12">
				

			      <el-col :span="12">
			        <el-card shadow="always" > 
					    <div class="demo-image__preview">
					       <el-image
					         style="height: 63vh"
					         :src="url1"
					         :zoom-rate="1.2"
					         :max-scale="7"
					         :min-scale="0.2"
					         :preview-src-list="srcList1"
					         :initial-index="4"
					         fit="cover"
					       />
					     </div>
					 </el-card>
			      </el-col>
				  <el-button size="big" type="primary" class="getx" v-if="ifx==false" @click="getx()">获取检测结果</el-button>
				  
				  
                  <el-col :span="12">
                    <el-card shadow="always">
					<div class="demo-image__preview" > 
					   <el-image
					     style="height: 63vh"
					     :src="url2"
					     :zoom-rate="1.2"
					     :max-scale="7"
					     :min-scale="0.2"
					     :preview-src-list="srcList2"
					     :initial-index="4"
					     fit="cover"
					   />
					 
					    <!-- <el-button size="big" type="primary" class="getcv"  @click="getdatafrompy()">获取数据</el-button> -->
					   
					   
					   <el-popover
					       placement="top-start"
					       title="刷新"
					       :width="200"
					       trigger="hover"
					       content="慎用此项!将重新生成AI结果!需要较长时间!"
					     >
					       <template #reference>
					          <el-button size="big" type="primary" class="getcv"  @click="getcv()">刷新检测结果</el-button>
					       </template>
					     </el-popover>
					 </div>
					 </el-card>
                  </el-col>
				
				  
			</el-row>
		  </el-main>
		  
		  
		  
		  
		  <el-aside width="450px" >
			  <el-menu
			      :default-active="activeIndex"
			      class="el-menu-demo"
			      mode="horizontal"
			      @select="handleSelectresult"
				  background-color="#6EB5FF"
				  active-text-color="#fff"
			    >
			      <el-menu-item index="1">AI结果</el-menu-item>
			  	  <el-menu-item index="2">人工选择</el-menu-item>

			    </el-menu>
			  
			<div class="table"  v-if="ifai===false">
			 <el-table :data="tableData" style="width: 90%"  @selection-change="handleSelectionChange" >
			   <el-table-column type="selection" width="50" align="center"></el-table-column>
			   <el-table-column prop="score" label="分数" width="50" sortable/>
			   <el-table-column prop="description" label="症状或体征" width="170" />
			   <el-table-column label="修改" width="80">
			         <template #default="scope">
			           <el-button size="small" @click="editchecked(scope.row)">修改</el-button>
			         </template>
			       </el-table-column>
			 </el-table>
			 <el-popover
			     placement="top-start"
			     title="增加"
			     :width="200"
			     trigger="hover"
			     content="点击增加症状"
			   >
			     <template #reference>
			        <el-button type="primary" class="affixx" @click="addchecked()">增加</el-button>
			     </template>
			   </el-popover>
			   &nbsp;&nbsp;
			   <el-popover
			       placement="top-start"
			       title="删除"
			       :width="200"
			       trigger="hover"
			       content="慎用此项!将删除选择的症状!"
			     >
			       <template #reference>
			          <el-button type="danger" class="affixx" @click="deletchecked()">删除</el-button>
			       </template>
			     </el-popover>
			 
			 </div>
				 
			 <div class="airesult" v-if="ifai===true" >
	
					 
					 
				<div class="aishow">
				 <el-row :gutter="2">
					 <el-col :span="23">
					 	<el-card shadow="hover" class="aicard" >
							<div class="aititle">
								总体分布:
							</div>
					 	   <div ref="chart" style="width: 300px; height: 250px;"></div>
						   <br />
						   <div class="aititle">
						   	平均置信度:
						   </div>
						   <div ref="chartdata" style="width: 350px; height: 250px;" class="chartdata"></div>
					 	</el-card>
					 </el-col>
					
				     <el-col :span="23">
						<el-card shadow="hover" class="aicard" >
							<div class="aititle">
								严重关节:
								
							</div>
							<br />
							<div class="image-row">
							      <div class="image-column" v-for="(image, index) in seveimages" :key="index">
							        <el-tooltip
							          class="box-item"
							          effect="light"
							          :content="'置信度：'+image.value"
							          placement="bottom"
							        >
							          <img :src="image.url" alt="Image" class="image" />
							        </el-tooltip>
							      </div>
							    </div>
						</el-card>
				     </el-col>
					<el-col :span="23">
						<el-card shadow="hover" class="aicard" >
							<div class="aititle">
								温和关节:
							</div>
							<br />
							<div class="image-row">
							      <div class="image-column" v-for="(image, index) in mildimages" :key="index">
							        <el-tooltip
							          class="box-item"
							          effect="light"
							          :content="'置信度：'+image.value"
							          placement="bottom"
							        >
							          <img :src="image.url" alt="Image" class="image" />
							        </el-tooltip>
							      </div>
							    </div>
						</el-card>
					</el-col>
					<el-col :span="23">
						<el-card shadow="hover" class="aicard" >
							<div class="aititle">
								健康关节:
							</div>
							<br />
							<div class="image-row">
							      <div class="image-column" v-for="(image, index) in normalimages" :key="index">
							        <el-tooltip
							          class="box-item"
							          effect="light"
							          :content="'置信度：'+image.value"
							          placement="bottom"
							        >
							          <img :src="image.url" alt="Image" class="image" />
							        </el-tooltip>
							      </div>
							    </div>
						</el-card>
					</el-col>
				 </el-row>
				</div>	
				
			 </div>
			 
			 
		  </el-aside>
		  
		  
		  
		  
	    </el-container>
	  </div>
	  
	  

		
		
		<el-drawer v-model="draweredit" title="I am the title" :with-header="false">
			<div id="draweredit">
				修改分数：<el-input-number v-model="selecteditchecked.score" :min="1" :max="10" />
			</div>
			
			 <div style="margin: 20px 0" />
			 <el-input
			   v-model="selecteditchecked.description"
			   :autosize="{ minRows: 8, maxRows: 50 }"
			   type="textarea"
			 />
			<el-button type="primary" class="button2" @click="edit()">确定修改</el-button>
			
		</el-drawer>
		
		
		
		<el-drawer v-model="draweradd" title="I am the title" :with-header="false">
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
			 
			<div id="draweredit">
				输入分数：<el-input-number v-model="textarea3" :min="1" :max="10" />
			</div> 
			
		    <div style="margin: 20px 0" />
		    <el-input
		      v-model="textarea2"
		      :autosize="{ minRows: 8, maxRows: 50 }"
		      type="textarea"
		      placeholder="具体描述"
		    />
			<el-button type="primary" class="button2" @click="add()">确定添加</el-button>
		</el-drawer>
		
		<el-popover
		    placement="top-start"
		    title="确认"
		    :width="200"
		    trigger="hover"
		    content="点击确认该患者的临床表现"
		  >
		    <template #reference>
		       <el-button type="primary" class="affix4" @click="handleConfirm" v-if="ifai===false">确认人工结果</el-button>
		    </template>
		  </el-popover>
		
		<el-popover
		    placement="top-start"
		    title="确认"
		    :width="200"
		    trigger="hover"
		    content="点击确认采用AI结果"
		  >
		    <template #reference>
		       <el-button type="primary" class="affix4" @click="handleConfirmai" v-if="ifai===true">确认AI结果</el-button>
		    </template>
		  </el-popover>
	
		

  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted ,computed} from 'vue'
import * as echarts from 'echarts';
import { ElMessage, ElMessageBox } from 'element-plus'
import type { Action } from 'element-plus'
import { defineEmits } from 'vue';

const emit = defineEmits(['selectX'])
const selectedData = ref([]);
const tableData = ref([])
const draweredit=ref(false)
const draweradd=ref(false)
const ifcv=ref(false)
const ifx=ref(false)
const activeIndex = ref('1')
const textarea2 = ref('')
const textarea3 = ref()
const value = ref('')
const loading = ref(false)
const ifai=ref(true)
const props = defineProps(['recentPatient']);
const handvalue=ref(true)
const valuebig = ref('oa')
const attridata=()=>{
	console.log(valuebig.value)
	getcvimage();
	fetchImages();
}

const optionsbig = [
  {
    value: 'oa',
    label: '骨关节炎',
  },
  {
    value: 'ra',
    label: '类风湿关节炎',
  },

]
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
const docterid=localStorage.getItem('docterid')


const srcList2=ref([]);
const url2=ref("");

const chart = ref<HTMLElement | null>(null);
const chartdata = ref<HTMLElement | null>(null);

const getchartdata=()=>{
	
	if (!chartdata.value) return;
	
	const myChart = echarts.init(chartdata.value);
	const option: echarts.EChartOption = {
		tooltip: {
		  trigger: 'axis', // 将触发方式改为坐标轴触发
		  axisPointer: { // 设置坐标轴指示器
		    type: 'shadow' // 使用阴影指示器
	      },
		borderColor: '#ccc', // 设置边框颜色
		borderWidth: 1, // 设置边框宽度
		textStyle: { // 设置文本样式
		     color: '#333', // 设置文本颜色
		    fontSize: 18 // 设置文本大小
		},
		formatter: function(params) {
		    return params[0].name +'</br>'+ ' 平均置信度: ' + parseFloat(params[0].value).toFixed(4);
		},
		position: function (point, params, dom, rect, size) {
		            // 设置提示框位置，这里可以根据需要自定义位置逻辑
		            return [point[0] + 10, '10%'];
		        }
		
		},
	    yAxis: {
	        type: 'category',
	        data: ['严重', '温和', '正常']
	    },
	    xAxis: {
	        type: 'value',
		
	    },
	    series: [
	        {
				
			itemStyle: {
				borderColor: '#fff',
				borderWidth: 0.8
				},
			labelLine: {
				    show: false
				},
	          data: [
	            {
	              value: averseve.value,
	              itemStyle: {
	                color: '#FF4500'
	              }
	            },
	            {
	              value: avermild.value,
	              itemStyle: {
	                color: '#FF8C00'
	              }
	            },
	            {
	              value: avernormal.value,
	              itemStyle: {
	                color: '#6EB5FF'
	              }
	            },
	          ],
	          type: 'bar'
	        }
	      ]
	};
	
	myChart.setOption(option);
}

const getchart=()=>{
	
	if (!chart.value) return;
	
	const myChart = echarts.init(chart.value);
	
	const option: echarts.EChartOption = {
	  tooltip: {
	    trigger: 'item'
	  },
	  legend: {
	    top: '2%',
	    left: 'center'
	  },
	  series: [
		  
	    {
	      type: 'pie',
	      radius: ['30%', '80%'],

	      itemStyle: {
	        borderRadius: 15,
	        borderColor: '#fff',
	        borderWidth: 5
	      },
	      label: {
	        show: false,
	        position: 'right'
	      },
	      emphasis: {
	        label: {
	          show: false,
	          fontSize: 15,
	          fontWeight: 'bold'
	        }
	      },
	      labelLine: {
	        show: false
	      },
	      data: [
	        { 
	          value: numseve.value, 
	          name: '严重',
	          itemStyle: {
	            color: '#FF4500 ' // 举例，为'sever'设置红色
	          }
	        },
	        { 
	          value: nummild.value, 
	          name: '温和',
	          itemStyle: {
	            color: '#FF8C00 ' // 为'mild'设置黄色
	          }
	        },
	        { 
	          value: numnormal.value, 
	          name: '正常',
	          itemStyle: {
	            color: '#6EB5FF' // 为'normal'设置绿色
	          }
	        },
	      ]
	    }
	  ]
	};
	
	myChart.setOption(option);
	

}
const seveimages = ref([]);
const tempiamge=ref([]);
const mildimages = ref([]);
const normalimages = ref([]);
const numseve=ref(0);
const nummild=ref(0);
const numnormal=ref(0);
const folderName=ref();
const averseve=ref(0)
const avermild=ref(0)
const avernormal=ref(0)
onMounted(() => {
	 getcvimage();
	 fetchImages();
	 getx();
})

const fetchImages = async () => {
	folderName.value = 'checkx_'+props.recentPatient+'_L';
	let rfilenamel='http://localhost:8088/getcv/checkx_'+props.recentPatient+'_L.png/'+valuebig.value;
	url2.value=rfilenamel
	let lfilenamel='http://localhost:8088/getx/checkx_'+props.recentPatient+'_L.png';
	url1.value=lfilenamel
	///不用管这个报错
	if(handvalue.value=="true"){
		folderName.value = 'checkx_'+props.recentPatient+'_R';
		let rfilenamer='http://localhost:8088/getcv/checkx_'+props.recentPatient+'_R.png/'+valuebig.value;
		url2.value=rfilenamer
		let lfilenamer='http://localhost:8088/getx/checkx_'+props.recentPatient+'_R.png';
		url1.value=lfilenamer
	}
	console.log(folderName.value)
  try {
	
    const response = await fetch('http://localhost:8088/get_images', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ folder_name: folderName.value,atrri:valuebig.value })
    });
    const responseData = await response.json();
    tempiamge.value = responseData.images;
	numseve.value=0;
	nummild.value=0;
	numnormal.value=0;
	for(let i=0;i<tempiamge.value.length;i++){
		if(tempiamge.value[i].split('_')[4]==2){
			numseve.value=numseve.value+1;
		}
		if(tempiamge.value[i].split('_')[4]==1){
			nummild.value=nummild.value+1;
		}
		if(tempiamge.value[i].split('_')[4]==0){
			numnormal.value=numnormal.value+1;
		}
	}
	getchart();
	
	const tempnumseve=ref(0);
	const tempnummild=ref(0);
	const tempnumnormal=ref(0);
	const allseve=ref(0);
	const allmild=ref(0);
	const allnormal=ref(0);
	seveimages.value = [];
	mildimages.value = [];
	normalimages.value = [];
    for(let i=0;i<tempiamge.value.length;i++){
		let filenamel='http://localhost:8088/get_certen_image/'+tempiamge.value[i]+'/'+valuebig.value;
		
		if(tempiamge.value[i].split('_')[4]==2){
			 allseve.value+=Number('0.'+(tempiamge.value[i].split('_')[5]).split('.')[1])
			 seveimages.value.push({
			          url: filenamel,
			          value: '0.'+(tempiamge.value[i].split('_')[5]).split('.')[1]
			        });
			 tempnumseve.value++;
			
		}
		if(tempiamge.value[i].split('_')[4]==1){
			allmild.value+=Number('0.'+(tempiamge.value[i].split('_')[5]).split('.')[1])
			mildimages.value.push({
			         url: filenamel,
			         value:  '0.'+(tempiamge.value[i].split('_')[5]).split('.')[1]
			       });
			tempnummild.value++;
		}
		if(tempiamge.value[i].split('_')[4]==0){
			allnormal.value+=Number('0.'+(tempiamge.value[i].split('_')[5]).split('.')[1])
			normalimages.value.push({
			         url: filenamel,
			         value:  '0.'+(tempiamge.value[i].split('_')[5]).split('.')[1]
			       });
			tempnumnormal.value++;
		}
		
		
	}
	averseve.value=allseve.value/numseve.value
	avermild.value=allmild.value/nummild.value
	avernormal.value=allnormal.value/numnormal.value
	console.log(averseve.value)
	console.log(avermild.value)
	console.log(avernormal.value)
	getchartdata();
	
  } catch (error) {
    console.error('Error fetching images:', error);
  }
};

const handdata=()=>{
	 
	 
	 fetchImages();
}

const handleSelectresult = (key: string) => {
     if(key=="1"){
		 ifai.value=true
		
		 getchartdata();
		 fetchImages();
	 }
	 if(key=="2"){
	 	ifai.value=false
	 }
}

const getcvimage=()=>{
	let filenamel='http://localhost:8088/getcv/checkx_'+props.recentPatient+'_L.png/'+valuebig.value;
	let filenamer='http://localhost:8088/getcv/checkx_'+props.recentPatient+'_R.png/'+valuebig.value;
	url2.value=filenamel
	srcList2.value[0]=filenamel
	srcList2.value[1]=filenamer
	loading.value=false
}

const getcv=()=>{
	getx();
	loading.value=true
	ifcv.value=true
	const files = ref({
	    left: 'checkx_' + props.recentPatient + '_L.png',
	    right: 'checkx_' + props.recentPatient + '_R.png',
	})
	
	fetch('http://localhost:8088/docv', {
	    method: 'POST',
	    headers: {
	        'Content-Type': 'application/json',
	    },
	    body: JSON.stringify(files.value), // 将对象转换为 JSON 字符串
	})
	.then(response => {
	    getcvimage();
		let datal='http://localhost:8088/getdata/checkx_'+props.recentPatient+'_L.txt';
		let datar='http://localhost:8088/getdata/checkx_'+props.recentPatient+'_R.txt';
		
		fetch(datal)
		  .then(response => response.text())
		  .then(data => {
		    console.log("Data from left file:", data);
		    
		  })
		  .catch(error => console.error('Error fetching left file:', error));
		
		fetch(datar)
		  .then(response => response.text())
		  .then(data => {
		    console.log("Data from right file:", data);
		    
		  })
		  .catch(error => console.error('Error fetching right file:', error));
		getdatafrompy();
		
		
	})
	.catch(error => {
	    console.error('Error:', error);
	});
}

const dataArray = ref({ oa: [], ra: [] });  // Initialize dataArray as an object with empty arrays for oa and ra
const oadataArray = ref({
		'docterid':docterid,
		'patientid':props.recentPatient,
        'aidata':[]
}); 
 
const radataArray = ref({
		'docterid':docterid,
		'patientid':props.recentPatient,
        'aidata':[]
}); 

const getdatafrompy = () => {
    fetch('http://localhost:8088/getdatafrompy/checkx_' + props.recentPatient)
        .then(response => response.json())  // Parse the response as JSON
        .then(data => {
            dataArray.value = data;
            console.log(dataArray.value);
            oadataArray.value.aidata = dataArray.value.oa;
            console.log(oadataArray.value);
            radataArray.value.aidata = dataArray.value.ra;
            console.log(radataArray.value);
			putdatatospring();
        })
        .catch(error => console.error('Error fetching left file:', error));
}

const putdatatospring = () => {
    fetch('http://localhost:8080/backend/oadataArray', {
      method: 'POST',
      body: JSON.stringify(oadataArray.value),
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(response => {
        return response.json(); // 将响应转换为 JSON 格式
    }).then(data => {
        
    }).catch(error => {
        console.error('请求出错', error);
    });
	
	fetch('http://localhost:8080/backend/radataArray', {
	  method: 'POST',
	  body: JSON.stringify(radataArray.value),
	  headers: {
	    'Content-Type': 'application/json'
	  }
	}).then(response => {
	    return response.json(); // 将响应转换为 JSON 格式
	}).then(data => {
	    
	}).catch(error => {
	    console.error('请求出错', error);
	});
}







const srcList1=ref([]);
const url1=ref("");

const getx=()=>{
	console.log(props.recentPatient)
    let filenamel='http://localhost:8088/getx/checkx_'+props.recentPatient+'_L.png';
	let filenamer='http://localhost:8088/getx/checkx_'+props.recentPatient+'_R.png';
	url1.value=filenamel
	srcList1.value[0]=filenamel
	srcList1.value[1]=filenamer
	ifx.value=true
}



const deletchecked=()=>{
	console.log(selectedData.value)
	const checkeddelet=ref({
		docterid:docterid,
		label:null,
		arthritis:null
		
	})
	for(let i=0;i<selectedData.value.length;i++){
		checkeddelet.value.label=selectedData.value[i].label;
		checkeddelet.value.arthritis=selectedData.value[i].arthritis;
		console.log(checkeddelet.value)
		fetch('http://localhost:8080/backend/checkedx/delet', {
		  method: 'POST',
		  body: JSON.stringify(checkeddelet.value),
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




const addchecked=()=>{
	draweradd.value=true;
	
}
const checkedadd=ref({
	description:null,
	score:null,
	arthritis:null,
	docterid:docterid
})
const add=()=>{
	const description = textarea2.value;
	const score=textarea3.value;
	const arthritis=value.value;
	checkedadd.value.arthritis=arthritis;
	checkedadd.value.score=score;
	checkedadd.value.description=description;
	console.log(checkedadd.value)
	draweradd.value = false
	fetch('http://localhost:8080/backend/checkedx/insert', {
	  method: 'POST',
	  body: JSON.stringify(checkedadd.value),
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



const selecteditchecked=ref({
	label:null,
	description:null,
	score:null,
	arthritis:null,
	docterid:docterid
})

const editchecked=(checked)=>{
	console.log(checked)
	draweredit.value=true;
	selecteditchecked.value.description=checked.description;
	selecteditchecked.value.score=checked.score;
	selecteditchecked.value.arthritis=checked.arthritis;
	selecteditchecked.value.label=checked.label;
}

const edit=()=>{
    console.log(selecteditchecked.value)
	fetch('http://localhost:8080/backend/checkedx/edit', {
	  method: 'POST',
	  body: JSON.stringify(selecteditchecked.value),
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

const open = (desArray) => {
  let message = ``;
  let num=1;
  desArray.forEach((item) => {
    message += `${num}.${desArray[num-1]}<br>`;
  	num=num+1;
  });
  
  ElMessageBox.alert(message, '是否确定诊断结果：', {
    confirmButtonText: '确认',
    dangerouslyUseHTMLString: true,
    callback: (action) => {
      ElMessage({
        type: 'info',
        message: `action: ${action}`,
      });
      if (action === 'confirm') {
         emit('selectX', desArray);
      }
    },
  });
};
const open2 = (desArray) => {

  
  ElMessageBox.alert('是否确定采用AI检测', '是否确定诊断结果：', {
    confirmButtonText: '确认',
    dangerouslyUseHTMLString: true,
    callback: (action) => {
      ElMessage({
        type: 'info',
        message: `action: ${action}`,
      });
      if (action === 'confirm') {
		 console.log(desArray)
         emit('selectX', desArray);
      }
    },
  });
};
function handleSelectionChange(data) {
  selectedData.value = data;
}

function handleConfirm() {
  const desArray = selectedData.value.map((item: any) => item.description);
  open(desArray);
  //console.log(desArray);
}
function handleConfirmai() {
  const desArray = [];
  open2(desArray);
  
}
const fetchData = () => {
  fetch(`http://localhost:8080/backend/checkedx/${docterid}`, {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
  }).then(response => {
      return response.json(); // 将响应转换为 JSON 格式
  }).then(data => {
       tableData.value = data 
  
    
  }).catch(error => {
      console.error('请求出错', error);
  });
}

onMounted(() => {
  fetchData()
})

</script>

<style scoped>

#draweredit{
	font-size: 18px;
	font-weight: 400;
}
.demo-image__error .image-slot {
  font-size: 30px;
}
.demo-image__error .image-slot .el-icon {
  font-size: 30px;
}
.demo-image__error .el-image {
  width: 100%;
  height: 200px;
}
.main{
	background: linear-gradient(to bottom, #6EB5FF, white);
	min-height: 70vh;
	
}
.table{
	width: 94%;
	margin-left: 25px;
	margin-top: 20px;
	height: 63vh;
}
.affix4{
  	   position: fixed;
  	   left: 890px;
  	   bottom: 40px;
  	   z-index: 99;
  	   width: 140px; /* 设置按钮的宽度 */
  	   height: 38px; /* 设置按钮的高度 */ 
  	   font-size: 20px; /* 设置按钮的字体大小 */
  }

	.getcv{
	  	   position: fixed;
	  	   left: 890px;
	  	   top: 100px;
	  	   z-index: 99;
	  	   width: 130px; /* 设置按钮的宽度 */
	  	   height: 50px; /* 设置按钮的高度 */ 
	  	   font-size: 20px; /* 设置按钮的字体大小 */
	  }
	  .getx{
	    	   position: fixed;
	    	   left: 350px;
	    	   top: 370px;
	    	   z-index: 99;
	    	   width: 130px; /* 设置按钮的宽度 */
	    	   height: 50px; /* 设置按钮的高度 */ 
	    	   font-size: 20px; /* 设置按钮的字体大小 */
	    }
.airesult{
    
	width: 28vw;
}
.aititle{
	text-align: left;
	font-size: 20px;
}
.aicard {
    text-align: center;
    //height: 40vh;
	font-size: 25px;
	font-weight: bold;
    margin: 8px;
  }
  .handvalue{
	  position: fixed;
	  left: 530px;
	  top: 100px;

	  z-index: 999;
  }
  .attrivalue{
	  position: fixed;
	  left: 250px;
	  top: 100px;
	  z-index: 999;
  }
  .aishow{  
	 
	  height: 40vh;
	  width: 27vw;
  }
.image-row {
  display: flex;
  flex-wrap: wrap;
}

.image-column {
  width: 50%;
  box-sizing: border-box;
  padding: 0 5px; /* 控制图片之间的间距 */
}

.image {
  width: 100%;
}
.chartdata{
	margin-left: 10px;
}
</style>