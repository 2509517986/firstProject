<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
// import echarts from 'echarts'
// require('echarts/theme/macarons') // echarts theme
export default {
    name: 'pieDoughnutChart',
    props: {
        className: {
            type: String,
            default: 'chart'
        },
        width: {
            type: String,
            default: '75px'
        },
        height: {
            type: String,
            default: '75px'
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
                this.setOptions(val)
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
            this.setOptions(this.chartData)
        },
        setOptions({ expectedData, actualData } = {}) {
            if (!this.chart) {
                return
            }
            this.chart.setOption({
                series: [
                    {
                        type: 'pie',
                        radius: ['100%', '85%'],
                        grid:{
                            top: '0%',
                            bottom:'0%',
                            left: '0%',
                            bottom:'0%',
                        },
                        avoidLabelOverlap: false,
                        hoverAnimation:false,   //关闭放大动画
                        selectedOffset:0,     //选中块的偏移量
                        label: {
                            show: true,
                            position: 'center',
                            formatter: function(params) {
                                // var myseries = option.series;
                                // var oneV = myseries && myseries[0] && myseries[0].data && myseries[0].data[0] && myseries[0].data[0].value?myseries[0].data[0].value:0
                                // var twoV = myseries && myseries[0] && myseries[0].data && myseries[0].data[1] && myseries[0].data[1].value?myseries[0].data[1].value:0
                                // return oneV + '/' +  twoV
                                return expectedData + '/' + actualData
                            },
                            fontSize:'12px'
                        },
                        labelLine: {
                            show: false
                        },
                        data: [
                            {
                                value:expectedData,
                                itemStyle:{
                                    normal:{color:'#dcdfe6'}, emphasis:{color:'#dcdfe6'}
                                }
                            },
                            {
                                value:actualData,
                                itemStyle:{
                                    normal:{color:'#13ce66'}, emphasis:{color:'#13ce66'}
                                }
                            }
                        ]
                    }
                ]
            });
        }
    }
}
</script>
