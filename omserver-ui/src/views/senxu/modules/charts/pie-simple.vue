<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
// import echarts from 'echarts'
// require('echarts/theme/macarons') // echarts theme
export default {
    name: 'pieSimpleChart',
    props: {
        className: {
            type: String,
            default: 'chart'
        },
        width: {
            type: String,
            default: '90px'
        },
        height: {
            type: String,
            default: '90px'
        },
        chartData: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            chart: null
        }
    },
    watch: {
        chartData: {
            deep: true,
            handler(val) {
                this.setOptions(val, true)
            }
        }
    },
    mounted() {
        this.$nextTick(() => {
            this.initChart()
        })
    },
    beforeDestroy() {
        if (!this.chart) {
            return
        }
        this.chart.dispose()
        this.chart = null
    },
    methods: {
        initChart() {
        // this.chart = echarts.init(this.$el, 'macarons')
        this.chart = echarts.init(this.$el)
            this.setOptions(this.chartData, true)
        },
        setOptions({ expectedData, actualData } = {}) {
            if (!this.chart) {
                return
            }
            this.chart.setOption({
                series: [
                    {
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        grid:{
                            top: '0%',
                            bottom:'0%',
                            left: '0%',
                            bottom:'0%',
                        },
                        hoverAnimation: false,
                        name: '访问来源',
                        type: 'pie',
                        radius: '100%',
                        data: [
                            {value: expectedData, itemStyle: {normal: {color: '#13ce66'}, emphasis: {color:'#13ce66'}}},
                            {value: actualData, itemStyle: {normal:{ color: '#dcdfe6' }, emphasis:{color:'#dcdfe6'}}}
                        ]
                    }
                ]
            });
        }
    }
}
</script>
