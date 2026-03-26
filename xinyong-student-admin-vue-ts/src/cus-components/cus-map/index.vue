<template>
  <div class="map-check-in">
    <!-- 静态地图容器 -->
    <div 
      class="static-map-container"
      :style="{ width: '100%', height: '100%' }"
    >
      <!-- 新增：调试和控制面板 -->
      <div class="debug-controls">
        <div class="control-group">
          <label>地图宽度: {{ mapWidth }}px</label>
          <input type="range" min="400" max="1024" v-model.number="mapWidth" />
        </div>
        <div class="control-group">
          <label>地图高度: {{ mapHeight }}px</label>
          <input type="range" min="400" max="1024" v-model.number="mapHeight" />
        </div>
        <div class="control-group">
          <label>缩放级别: {{ zoom }}</label>
          <input type="range" min="1" max="17" v-model.number="zoom" />
        </div>
      </div>
      <!-- 静态地图图片 -->
      <img 
        :src="staticMapUrl" 
        alt="静态地图"
        class="static-map-img"
        @click="handleMapClick"
      >
      <!-- 点击标记点（动态定位） -->
      <div 
        class="map-marker"
        :style="{
          left: `${markerX}px`,
          top: `${markerY}px`,
          transform: 'translate(-50%, -100%)' // 标记点底部中心对齐点击位置
        }"
      >
        <img src="https://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png" alt="打卡点" width="32" height="32">
      </div>
    </div>

    <!-- 右侧面板 -->
    <div class="controls-container">
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from 'axios'
import { getCurrentInstance, onMounted, ref, computed, watch, onUnmounted } from 'vue'

const app = getCurrentInstance()?.appContext.config.globalProperties
interface Props {
  defaultCenter?: [number, number]
  defaultZoom?: number
  mapWidth?: number
  mapHeight?: number
}

const props = withDefaults(defineProps<Props>(), {
  defaultCenter: () => [116.481485, 39.990464],
  defaultZoom: 15,
  mapWidth: 640,
  mapHeight: 480
})

const emits = defineEmits<{
  (e: 'dakaEmit', data:any): void
}>()

// 状态管理
const markerX = ref(0)
const markerY = ref(0)
const defaultCenter = ref(props.defaultCenter)
const mapWidth = ref(props.mapWidth)
const mapHeight = ref(props.mapHeight)
const staticMapUrl = ref('')
const zoom = ref(props.defaultZoom)
const mapScale = 2 // 高德静态地图 scale=2 返回2倍图

const AMAP_KEY = 'aaf59dfaf1179efc627abb4651cb0b70'
const STATIC_MAP_BASE_URL = 'https://restapi.amap.com/v3/staticmap'

const initStaticMap = () => {
  const [centerLng, centerLat] = defaultCenter.value
  const params = new URLSearchParams({
    key: AMAP_KEY,
    location: `${centerLng},${centerLat}`,
    zoom: zoom.value.toString(),
    size: `${mapWidth.value}*${mapHeight.value}`,
    scale: mapScale.toString(),
    markers: `mid,,A:${centerLng},${centerLat}`,
  })
  staticMapUrl.value = `${STATIC_MAP_BASE_URL}?${params.toString()}`
}


// 修改：处理地图点击事件（核心修复）
const handleMapClick = async (e: MouseEvent) => {
  const imgElement = e.currentTarget as HTMLImageElement;
  const container = imgElement.parentElement as HTMLDivElement;

  // 1. 获取容器和图片的真实尺寸信息
  const containerRect = container.getBoundingClientRect();
  const imgNaturalWidth = mapWidth.value * mapScale; // 图片真实宽度（乘scale）
  const imgNaturalHeight = mapHeight.value * mapScale; // 图片真实高度（乘scale）

  // 2. 计算图片在容器中的缩放比例（处理object-fit: cover的适配）
  const scaleX = containerRect.width / imgNaturalWidth;
  const scaleY = containerRect.height / imgNaturalHeight;
  const actualScale = Math.max(scaleX, scaleY); // object-fit: cover会取最大缩放比例

  // 3. 计算图片在容器中的偏移量（处理object-fit: cover的居中裁剪）
  const offsetX = (containerRect.width - imgNaturalWidth * actualScale) / 2;
  const offsetY = (containerRect.height - imgNaturalHeight * actualScale) / 2;

  // 4. 计算点击点在原始图片（未缩放、未裁剪）上的真实像素坐标
  const clickXInContainer = e.clientX - containerRect.left;
  const clickYInContainer = e.clientY - containerRect.top;

  // 修正：减去偏移量，再除以缩放比例，得到原始图片上的坐标
  const originalImgX = (clickXInContainer - offsetX) / actualScale;
  const originalImgY = (clickYInContainer - offsetY) / actualScale;

  // 5. 验证坐标是否在图片范围内（避免裁剪区域外的无效点击）
  if (
    originalImgX < 0 ||
    originalImgX > imgNaturalWidth ||
    originalImgY < 0 ||
    originalImgY > imgNaturalHeight
  ) {
    console.warn('点击位置在地图图片裁剪区域外，忽略');
    return;
  }

  // 6. 保存标记点位置（用于页面显示）
  markerX.value = clickXInContainer;
  markerY.value = clickYInContainer;

  // --- 新增：根据墨卡托投影反算经纬度 ---
  const [centerLng, centerLat] = defaultCenter.value;
  const z = zoom.value;
  
  // 墨卡托投影参数：世界地图总像素宽度 = 256 * 2^zoom
  const C = 256 * Math.pow(2, z);
  
  // 1. 将中心点经纬度转换为世界像素坐标
  const centerPixelX = C * (centerLng + 180) / 360;
  const centerPixelY = C * (1 - Math.log(Math.tan(centerLat * Math.PI / 180) + 1 / Math.cos(centerLat * Math.PI / 180)) / Math.PI) / 2;

  // 2. 计算点击点相对于图片中心的偏移量（单位：zoom级别下的像素）
  // 注意：originalImgX 是基于 scale=2 的大图坐标，需要除以 mapScale 还原
  const deltaX = (originalImgX - imgNaturalWidth / 2) / mapScale;
  const deltaY = (originalImgY - imgNaturalHeight / 2) / mapScale;

  // 3. 计算目标点的世界像素坐标
  const targetPixelX = centerPixelX + deltaX;
  const targetPixelY = centerPixelY + deltaY;

  // 4. 将目标点像素坐标转回经纬度
  const targetLng = targetPixelX / C * 360 - 180;
  const n = Math.PI - 2 * Math.PI * targetPixelY / C;
  const targetLat = 180 / Math.PI * Math.atan(0.5 * (Math.exp(n) - Math.exp(-n)));
  
  const targetLngLat = { lng: targetLng, lat: targetLat };

  console.log('计算得到的经纬度：', targetLngLat);
    emits('dakaEmit', {
        lng: targetLngLat.lng.toFixed(6),
        lat: targetLngLat.lat.toFixed(6),
    });

};


const changeDefaultCenter = (data: any) => {
    defaultCenter.value = data;
    console.log('更改默认中心点为:', data);
    initStaticMap()
}


let debounceTimer: number;
watch([mapWidth, mapHeight, zoom], () => {
  clearTimeout(debounceTimer);
  debounceTimer = window.setTimeout(() => {
    initStaticMap()
  }, 300);
})

watch(() => props.defaultCenter, (newVal) => {
  defaultCenter.value = newVal
  initStaticMap()
}, { deep: true })
watch(() => props.defaultZoom, (newZoom) => { if (newZoom) zoom.value = newZoom })
watch(() => props.mapWidth, (newWidth) => { if (newWidth) mapWidth.value = newWidth })
watch(() => props.mapHeight, (newHeight) => { if (newHeight) mapHeight.value = newHeight })

onMounted(() => initStaticMap())

defineExpose({ initData: initStaticMap,changeDefaultCenter})
</script>

<style lang="scss" scoped>
.map-check-in {
  width: 100%;
  display: flex;
  gap: 16px;
}

.static-map-container {
  flex: 1;
  position: relative;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  cursor: crosshair;
  max-width: 1024px;
  max-height: 1024px;

  .static-map-img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .map-marker {
    position: absolute;
    z-index: 10;
    pointer-events: none;
  }
}

.controls-container {
  width: 460px;
  padding: 6px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.check-in-panel {
  padding: 16px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);

  .info-group {
    display: flex;
    align-items: center;
    margin-bottom: 12px;
    flex-wrap: wrap;

    .info-label {
      font-size: 14px;
      color: #666;
      width: 80px;
      font-weight: 500;
    }

    .info-value {
      font-size: 14px;
      color: #333;
      flex: 1;
      word-break: break-all;
    }
  }

  .check-in-btn {
    width: 100%;
    height: 44px;
    background: #1890ff;
    color: #fff;
    border: none;
    border-radius: 4px;
    font-size: 16px;
    cursor: pointer;
    transition: background 0.3s;

    &:disabled {
      background: #ccc;
      cursor: not-allowed;
    }

    &:not(:disabled):hover {
      background: #096dd9;
    }
  }
}

.debug-controls {
  position: absolute;
  top: 10px;
  left: 10px;
  z-index: 20;
  padding: 10px;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  gap: 8px;

  .control-group {
    display: flex;
    align-items: center;
    gap: 10px;

    label {
      font-size: 12px;
      color: #333;
      width: 80px;
    }

    input[type="range"] {
      flex: 1;
    }
  }
}
</style>