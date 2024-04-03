<template>
  
<!--  <el-tabs
    v-model="activeName"
    type="card"
    class="demo-tabs"
    @tab-click="handleClick"
  >
    <el-tab-pane label="骨关节炎" name="first">
		<xchecked @selectX="handleSelectX" :recentPatient="props.recentPatient"></xchecked>
	</el-tab-pane>
    <el-tab-pane label="类风湿关节炎" name="second">类风湿关节炎</el-tab-pane>
    <el-tab-pane label="感染关节炎" name="third">感染关节炎</el-tab-pane>
    <el-tab-pane label="痛风" name="fourth">痛风</el-tab-pane>
  </el-tabs> -->
  <xchecked @selectX="handleSelectX" :recentPatient="props.recentPatient"></xchecked>

</template>
<script lang="ts" setup>
import { ref } from 'vue'
import type { TabsPaneContext } from 'element-plus'
import xchecked from "../checked/XChecked.vue"
import { defineEmits } from 'vue';
const activeName = ref('first')
const props = defineProps(['recentPatient']);
const emit = defineEmits(['selectChec'])
const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event)
}
const handleSelectX=(selectlistx)=> {
	 emit('selectChec', selectlistx);
}




</script>
<style>
.demo-tabs > .el-tabs__content {
  padding: 10px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

</style>
