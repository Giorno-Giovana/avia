<template>
  <section id='map' style="height: 400px !important; max-height: 400px"></section>
</template>

<script>
import '@mapbox/mapbox-gl-geocoder/lib/mapbox-gl-geocoder.css';
import 'mapbox-gl/dist/mapbox-gl.css';

export default {
  data() {
    return {
      marker: null
    }
  },
  props: {
    address: {type: Object, required: true},
    navigationControl: {type: Object, required: false, default: () => ({show: true, position: 'top-right'})},
    fullscreenControl: {type: Object, required: false, default: () => ({show: false, position: 'top-right'})},
    scaleControl: {
      type: Object,
      required: false,
      default: () => ({show: true, position: 'top-left', options: {}})
    },
    geoLocControl: {
      type: Object,
      required: false,
      default: () => ({show: true, position: 'top-left', options: {}})
    },
    mapStyle: {
      type: [String, Object],
      required: false,
      default: 'mapbox://styles/mapbox/streets-v11'
    },
    zoom: {
      type: [Number, String],
      required: false,
      default: 10
    },
    pitch: {
      type: [Number, String],
      required: false,
      default: 60
    },
    bearing: {
      type: [Number, String],
      required: false,
      default: 0
    },
  },

  mounted() {
    this.createMap();
    // document.querySelector('.mapboxgl-ctrl-geocoder input').focus();
    document.querySelector('.mapboxgl-ctrl-attrib-inner').remove();
    document.querySelector('.mapboxgl-ctrl-bottom-left [style]').remove();
  },

  methods: {
    async createMap() {
      // TODO: in store?
      const mapboxgl = require('mapbox-gl/dist/mapbox-gl.js');
      const MapboxGeocoder = require('@mapbox/mapbox-gl-geocoder');
      mapboxgl.accessToken = process.env.MAPBOX_API_KEY;

      const mapboxGeocoder = new MapboxGeocoder({
        accessToken: mapboxgl.accessToken,
        mapboxgl,
        countries: 'ru,by',
        language: 'ru-RU',
        placeholder: 'Найти адрес'
      });

      const geolocateControl = new mapboxgl.GeolocateControl({
        positionOptions: {enableHighAccuracy: false, timeout: 4000},
        trackUserLocation: true,
        fitBoundsOptions: {maxZoom: 15}
      });

      const map = new mapboxgl.Map({
        container: 'map',
        style: this.mapStyle,
        center: [this.address.longitude, this.address.latitude],
        zoom: this.zoom,
        pitch: this.pitch,
        bearing: this.bearing
      });

      // const marker = new mapboxgl.Marker()
      //     .setLngLat([this.address.longitude, this.address.latitude])
      //     .addTo(map);

      map
          .addControl(new mapboxgl.NavigationControl(), 'top-left')
          .addControl(mapboxGeocoder, 'top-right')
          .addControl(geolocateControl, 'bottom-left')
          .addControl(new mapboxgl.FullscreenControl(), 'bottom-right');
      let notLoaded = false;
      while (notLoaded) {
        notLoaded = !this.map.loaded()
      }
      this.$emit('map-instantiated', map);
      map.on('remove', () => {
        this.$emit('map-destroyed')
      });

    }
  }
}
</script>

<style scoped lang='scss'>

</style>
