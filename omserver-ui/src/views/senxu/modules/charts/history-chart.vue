<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
export default {
    name: 'historyChart',
    props: {
        className: {
            type: String,
            default: 'chart'
        },
        width: {
            type: String,
            default: '100%'
        },
        height: {
            type: String,
            default: '100%'
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
        setOptions(chartData) {
            if (!this.chart) {
                return
            }
            const colors = ['#FF6863', '#6EAB40', '#3BB5F5'];
            const data = chartData.data;
            const time = chartData.time;
            if(data.length<1 || time.length<1) {return;}
            let yAxis = [];
            let series = [];
            if(data.length == 2) {
                yAxis = [
                    {
                        type: 'value',
                        name: data[0].name + '(' + data[0].unit + ')',
                        axisLine: {onZero: false, show: true,lineStyle: { color: '#999999' },},
                        axisLabel: {
                            formatter: '{value}' + data[0].unit
                        },
                        position: 'left',
                        splitLine: { show: true }
                    },
                    {
                        type: 'value',
                        name: data[1].name + '(' + data[1].unit + ')',
                        axisLine: {onZero: false, show: true,lineStyle: { color: '#999999' },},
                        axisLabel: {
                            formatter: '{value}' + data[1].unit
                        },
                        position: 'right',
                        splitLine: { show: false }
                    }
                ]
                series = [
                    {
                        name: data[0].name,
                        type: 'line',
                        data: data[0].list
                    }, {
                        name: data[1].name,
                        type: 'line',
                        yAxisIndex: 1,
                        data: data[1].list
                    }
                ]
            } else if(data.length == 1) {
                yAxis = [
                    {
                        type: 'value',
                        name: data[0].name + '(' + data[0].unit + ')',
                        axisLine: {onZero: false, show: true,lineStyle: { color: '#999999' },},
                        axisLabel: {
                            formatter: '{value}' + data[0].unit
                        },
                        position: 'left',
                        splitLine: { show: true },
                        splitNumber: 8
                    }
                ]
                series = [
                    {
                        name: data[0].name,
                        type: 'line',
                        data: data[0].list
                    }
                ]
            }
            this.chart.setOption({
                color: colors,
                legend: {
                    show:true
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: { type: 'cross' },
                    formatter: function (params, ticket, callback) {
                        let index = params[0].dataIndex,Htm = "";
                        if(data.length == 2) {
                            Htm = `${time.list[index]}<br>
                            ${data[0].name}:${data[0].list[index]}${data[0].unit}<br>
                            ${data[1].name}:${data[1].list[index]}${data[1].unit}`
                        } else {
                            Htm = `${time.list[index]}<br>
                            ${data[0].name}:${data[0].list[index]}${data[0].unit}`
                        }
                        return Htm;
                    }
                },
                 grid: {
                    top: '20%',
                    bottom: '20%',
                    left: '5%',
                    right: '5%',
                    containLabel: true
                },                
                dataZoom: [ {
                    type: 'slider',                    
                    height: '25',
                }],
                xAxis: [{
                    data: time.list,
                    silent: false,
                    splitLine: {
                        show: false
                    },
                    splitArea: {
                        show: false
                    },
                    axisLabel: {
                        interval: time.list.length>5?2:1,
                        // rotate:45,
                        // formatter:function(value){return echarts.format.formatTime('yyyy-MM-dd\nhh:mm:ss', value);}
                    },
                    axisTick: {
                        show: false
                    }
                }],
                yAxis: yAxis,
                series: series
            });
        }
    }
}
</script>
