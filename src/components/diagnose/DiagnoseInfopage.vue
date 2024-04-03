<template>
  <el-table :data="filterTableData" style="width: 100%">
	<el-table-column label="编号" prop="patientid" sortable/>
    <el-table-column label="姓名" prop="name" sortable/>
	<el-table-column label="年龄" prop="age" sortable/>
	<el-table-column label="性别" prop="sex" sortable/>
	<el-table-column label="门诊时间" prop="date" sortable/>
	
    <el-table-column align="right">
      <template #header>
        <el-input v-model="search" size="small" placeholder="搜索" />
      </template>
      <template #default="scope">
        <el-button size="small" @click="handleSelect(scope.$index, scope.row)">选择病人</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts" setup>
import { Ref,onMounted } from 'vue'
import { number } from 'echarts/core';
import { computed, ref } from 'vue'
import { defineEmits } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus'
import type { Action } from 'element-plus'

const emit = defineEmits(['selectPatient'])
const search = ref('')
const filterTableData = computed(() =>
  tableData.value.filter(
    (data) =>
      !search.value ||
      data.name.toLowerCase().includes(search.value.toLowerCase())
  )
)
const handleSelect = function(index, row) {
  open(row);
};

const open = (row) => {
  ElMessageBox.alert(`当前患者为${row.name}，是否确认？`, '确认患者', {
    // if you want to disable its autofocus
    // autofocus: false,
    confirmButtonText: '确认',
    callback: (action) => {
      ElMessage({
        type: 'info',
        message: `action: ${action}`,
      });
	if (action === 'confirm') {
	    emit('selectPatient', row);
	  }
    },
  })
}

const tableData = ref([]);
const docterid=localStorage.getItem('docterid')
const fetchData = () => {
  fetch(`http://localhost:8080/backend/patient/${docterid}`, {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
  }).then(response => {
      return response.json(); // 将响应转换为 JSON 格式
  }).then(data => {
      tableData.value = data;
  }).catch(error => {
      console.error('请求出错', error);
  });
};

onMounted(() => {
  fetchData()
})



</script>
