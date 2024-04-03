<template>
  <div>
    <button @click="fetchImages">Fetch Images</button>
    <div v-for="image in images" :key="image">
      <img :src="image" alt="Image" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';

const images = ref([]);
const tempiamge=ref([]);


const fetchImages = async () => {
  try {
    const folderName = 'checkx_002_L';
    const response = await fetch('http://localhost:8088/get_images', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ folder_name: folderName })
    });
    const responseData = await response.json();
    tempiamge.value = responseData.images;
    for(let i=0;i<tempiamge.value.length;i++){
		//images.value[i]="http://localhost:8088/get_certen_image/"+tempiamge.value[i]
		let filenamel='http://localhost:8088/get_certen_image/'+tempiamge.value[i];
		images.value[i]=filenamel
		//console.log(images.value[i])
	}
	
	
  } catch (error) {
    console.error('Error fetching images:', error);
  }
};
</script>